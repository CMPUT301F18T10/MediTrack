package com.example.meditrack;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ListIterator;

public class DataRepositorySingleton
{
    /*
        DataRepositorySingleton (DRS):

        This singleton class will hold all the "business" data for the application
        and will in a very loose sense act as the model. Making it a singleton ensures
        there's only one copy of it which is useful since the amount of data needed
        might be large

        Only the ApplicationManager and the static services are allowed to interact with
        this and the ApplicationManager will initialize it when the user is logged in
        according to the userMode

        DRS is responsible for populating itself upon initialization, and updating itself
        when told to do so. It will manage how the data is held for the application and
        keep track of changes to the data. It is the only class that will directly talk
        to ElasticSearchManager.

        Finally it will be responsible for providing data to the Service classes
        who will query DRS for the specific data it needs. They will also push
        new/edited data to DRS. It will try to only provide data from itself
        whenever possible and only interact ElasticSearchManager when necessary
     */

    private static DataRepositorySingleton instance = null;
    private final String tag = "DRS";
    private ElasticsearchManager mESM;

    private boolean mDirty;

    // mProblemList will contain all relevant problems
    // whether new, edited or otherwise, it will model the server
    // it will get dumped and repopulated on updates
    private ArrayList<Problem> mProblemList;
    private Deque<Problem> mNewProblems;
    private Deque<Problem> mEditedProblems;
    private Deque<String> mDeletedProblemIds;

    private ArrayList<PatientRecord> mPatientRecordList;
    private Deque<PatientRecord> mNewPatientRecords;
    private Deque<String> mDeletedPatientRecordIds;

    private ArrayList<CareProviderRecord> mCareProviderRecordsList;
    private Deque<CareProviderRecord> mNewCareProviderRecords;
    private Deque<String> mDeletedCareProviderRecordIds;

    private Patient mPatientUser = null;
    private CareProvider mCareProvider = null;

    private ApplicationManager.UserMode mUserMode = ApplicationManager.UserMode.Invalid;
    private String mUserName;

    protected DataRepositorySingleton() { }


    // lazy construction of the instance
    public static DataRepositorySingleton GetInstance()
    {
        if (instance == null) instance = new DataRepositorySingleton();
        return instance;
    }

    private boolean IsDirty() { return mDirty; }

    private void SetDirty(boolean dirtyFlag) { mDirty = dirtyFlag; }

    private void PopulateUser(ApplicationManager.UserMode userMode, String userName)
    {
        // Need to create a temporary user object so that we can call
        // getElasticsearchtype method
        // Making the method static would've been a potential solution
        // but ElasticSearchManager has generic code and static methods
        // can't be called on generic types
        try
        {
            if (userMode == ApplicationManager.UserMode.Patient)
            {
                Patient user;
                ContactInfo tempContact = new ContactInfo("", "");
                user = new Patient("temp", new ArrayList<String>(), tempContact);
                mPatientUser = mESM.getObjectFromId(userName, user.getElasticsearchType(), user.getClass());
            }
            else if (userMode == ApplicationManager.UserMode.CareGiver)
            {
                CareProvider user;
                user = new CareProvider("tempid", new ArrayList<String>());
                mCareProvider = mESM.getObjectFromId(userName, user.getElasticsearchType(), user.getClass());
            }
        }
        catch(ElasticsearchManager.ObjectNotFoundException e)
        {
            Log.e(tag, "User object was not found by ESM");
            e.printStackTrace();
        }
        catch(ElasticsearchManager.OperationFailedException e)
        {
            Log.e(tag, "Get operation in ESM failed");
            e.printStackTrace();
        }
    }

    private void PopulateProblemList()
    {
        // if user is Patient pull all problems for patient id
        if (mUserMode == ApplicationManager.UserMode.Patient)
        {
            try
            {
                mProblemList.addAll(mESM.getProblemsByPatientId(mPatientUser.getId()));
            }
            catch(ElasticsearchManager.OperationFailedException e)
            {
                Log.e(tag, "mESM.getProblemsByPatientId for Patient failed");
                e.printStackTrace();
            }
        }
        // if user is CareProvider pull all problems for all patient ids
        else if (mUserMode == ApplicationManager.UserMode.CareGiver)
        {
            for (String patientId : mCareProvider.getPatientIds())
            {
                try
                {
                    mProblemList.addAll(mESM.getProblemsByPatientId(patientId));
                }
                catch(ElasticsearchManager.OperationFailedException e)
                {
                    Log.e(tag, "mESM.getProblemsByPatientId for CareProvider failed");
                    e.printStackTrace();
                }
            }
        }
    }

    private void PopulateRecordList()
    {
        // pull all records for all problem ids
        for (Problem currentProblem : mProblemList)
        {
            try
            {
                mPatientRecordList.addAll(mESM.getPatientRecordByProblemId(currentProblem.getId()));
                mCareProviderRecordsList.addAll(mESM.getCareProviderRecordByProblemId(currentProblem.getId()));
            }
            catch(ElasticsearchManager.OperationFailedException e)
            {
                Log.e(tag, "Getting records by ProblemId from ESM failed");
                e.printStackTrace();
            }
        }

    }

    private void ClearDataRepository()
    {
        mProblemList.clear();
        mPatientRecordList.clear();
        mCareProviderRecordsList.clear();
    }

    public void Initialize(ApplicationManager.UserMode userMode, String userName, ElasticsearchManager esm)
    {
        mDirty = false;
        mUserMode = userMode;
        mUserName = userName;
        mESM = esm;

        mProblemList = new ArrayList<>();
        mNewProblems = new ArrayDeque<>();
        mEditedProblems = new ArrayDeque<>();
        mDeletedProblemIds = new ArrayDeque<>();

        mPatientRecordList = new ArrayList<>();
        mNewPatientRecords = new ArrayDeque<>();
        mDeletedPatientRecordIds = new ArrayDeque<>();

        mCareProviderRecordsList = new ArrayList<>();
        mNewCareProviderRecords = new ArrayDeque<>();
        mDeletedCareProviderRecordIds = new ArrayDeque<>();

        DownloadData(userMode, userName);
    }

    private void UploadData()
    {
        // Add the new problems
        while (!mNewProblems.isEmpty())
        {
            try { mESM.addObject(mNewProblems.pop()); }
            catch (ElasticsearchManager.ObjectAlreadyExistsException e)
            {
                Log.e(tag, "Trying to add a problem that already exists");
                e.printStackTrace();
            }
            catch (ElasticsearchManager.OperationFailedException e)
            {
                Log.e(tag, "AddProblem Operation failed");
                e.printStackTrace();
            }
        }

        // Update the edited problems
        while (!mEditedProblems.isEmpty())
        {
            try
            {
                Problem currentProblem = mEditedProblems.pop();
                mESM.updateObject(currentProblem.getId(), currentProblem.getElasticsearchType(), currentProblem);
            }
            catch (ElasticsearchManager.ObjectNotFoundException e)
            {
                Log.e(tag, "Editing a problem that doesn't exist");
                e.printStackTrace();
            }
            catch (ElasticsearchManager.OperationFailedException e)
            {
                Log.e(tag, "EditProblem operation failed");
                e.printStackTrace();
            }
        }

        // Delete the problems
        Problem tempProblem = new Problem("tempTitle", "tempDescription", "tempPatientId");
        while (!mDeletedProblemIds.isEmpty())
        {
            try { mESM.deleteObject(mDeletedProblemIds.pop(), tempProblem.getElasticsearchType(), tempProblem.getClass()); }
            catch (ElasticsearchManager.ObjectNotFoundException e)
            {
                Log.e(tag, "Attempting to delete non-existent problem");
                e.printStackTrace();
            }
            catch (ElasticsearchManager.OperationFailedException e)
            {
                Log.e(tag, "DeleteProblem operation failed");
                e.printStackTrace();
            }
        }

        // Add the new CareProvider Records
        CareProviderRecord tempCPRecord = new CareProviderRecord("TempProblemId", "TempComment", "tempCareProviderId");
        while (!mNewCareProviderRecords.isEmpty())
        {
            try { mESM.addObject(mNewCareProviderRecords.pop()); }
            catch (ElasticsearchManager.ObjectAlreadyExistsException e)
            {
                Log.e(tag, "Trying to add a CareProviderRecord that already exists");
                e.printStackTrace();
            }
            catch (ElasticsearchManager.OperationFailedException e)
            {
                Log.e(tag, "AddCareProviderRecord Operation failed");
                e.printStackTrace();
            }
        }

        // Delete the CareProvider Records
        while (!mDeletedCareProviderRecordIds.isEmpty())
        {
            try { mESM.deleteObject(mDeletedCareProviderRecordIds.pop(), tempCPRecord.getElasticsearchType(), tempCPRecord.getClass()); }
            catch (ElasticsearchManager.ObjectNotFoundException e)
            {
                Log.e(tag, "Attempting to delete non-existent CareProviderRecord");
                e.printStackTrace();
            }
            catch (ElasticsearchManager.OperationFailedException e)
            {
                Log.e(tag, "DeleteCareProvider operation failed");
                e.printStackTrace();
            }
        }

        // Add the new Patient Records
        PatientRecord tempPatientRecord = new PatientRecord("tempProblemId");
        while (!mNewPatientRecords.isEmpty())
        {
            try { mESM.addObject(mNewPatientRecords.pop());}
            catch (ElasticsearchManager.ObjectAlreadyExistsException e)
            {
                Log.e(tag, "Trying to add a PatientRecord that already exists");
                e.printStackTrace();
            }
            catch (ElasticsearchManager.OperationFailedException e)
            {
                Log.e(tag, "AddPatientRecord Operation failed");
                e.printStackTrace();
            }
        }

        while (!mDeletedPatientRecordIds.isEmpty())
        {
            try { mESM.deleteObject(mDeletedPatientRecordIds.pop(), tempPatientRecord.getElasticsearchType(), tempPatientRecord.getClass());}
            catch (ElasticsearchManager.ObjectNotFoundException e)
            {
                Log.e(tag, "Attempting to delete non-existent PatientRecord");
                e.printStackTrace();
            }
            catch (ElasticsearchManager.OperationFailedException e)
            {
                Log.e(tag, "DeletePatientRecord operation failed");
                e.printStackTrace();
            }
        }
        SetDirty(false);
    }

    private void DownloadData(ApplicationManager.UserMode userMode, String userName)
    {
        PopulateUser(userMode, userName);
        PopulateProblemList();
        PopulateRecordList();
    }

    public void RefreshDataRepositorySingleton()
    {
        if (IsDirty())
        {
            UploadData();
            SetDirty(false);
        }
        ClearDataRepository();
        DownloadData(mUserMode, mUserName);
    }

    // Mutating Methods
    public void AddProblem(Problem problem)
    {
        mProblemList.add(problem);
        mNewProblems.add(problem);
        SetDirty(true);
    }

    public void EditProblem(Problem problem)
    {
        mEditedProblems.add(problem);
        ListIterator<Problem> iter = mProblemList.listIterator();
        while (iter.hasNext())
        {
            Problem currentProblem = iter.next();
            if (currentProblem.getId().equals(problem.getId()))
            {
                iter.set(problem);
            }
        }
        SetDirty(true);
    }

    public void DeleteProblem(String problemId)
    {
        mDeletedProblemIds.add(problemId);
        for (Problem currentProblem : mProblemList)
        {
            if (currentProblem.getId().equals(problemId))
            {
                mProblemList.remove(currentProblem);
            }
        }
        SetDirty(true);
    }

    public void AddCareProviderRecord(CareProviderRecord record)
    {
        mCareProviderRecordsList.add(record);
        mNewCareProviderRecords.add(record);
        SetDirty(true);
    }

    public void DeleteCareProviderRecord(String recordId)
    {
        mDeletedCareProviderRecordIds.add(recordId);
        for (CareProviderRecord currentRecord : mCareProviderRecordsList)
        {
            if (currentRecord.getId().equals(recordId))
            {
                mCareProviderRecordsList.remove(currentRecord);
            }
        }
        SetDirty(true);
    }

    public void AddPatientRecord(PatientRecord record)
    {
        mPatientRecordList.add(record);
        mNewPatientRecords.add(record);
        SetDirty(true);
    }

    public void DeletePatientRecord(String recordId)
    {
        mDeletedPatientRecordIds.add(recordId);
        for (PatientRecord currentRecord : mPatientRecordList)
        {
            if (currentRecord.getId().equals(recordId))
            {
                mPatientRecordList.remove(currentRecord);
            }
        }
        SetDirty(true);
    }

    // Query Methods

    public Patient GetPatient() throws DataRepositorySingletonNotInitialized, InvalidUserMode
    {
        if ((mPatientUser == null && mCareProvider == null) || mUserMode == ApplicationManager.UserMode.Invalid) { throw new DataRepositorySingletonNotInitialized(); }
        else if (mUserMode != ApplicationManager.UserMode.Patient) { throw new InvalidUserMode("Not in patient user mode"); }

        else { return mPatientUser; }
    }

    public CareProvider GetCareProvider() throws DataRepositorySingletonNotInitialized, InvalidUserMode
    {
        if ((mPatientUser == null && mCareProvider == null) || mUserMode == ApplicationManager.UserMode.Invalid) { throw new DataRepositorySingletonNotInitialized(); }
        else if (mUserMode != ApplicationManager.UserMode.CareGiver) { throw new InvalidUserMode("Not in CareProviderMode"); }

        else { return mCareProvider; }
    }

    public ApplicationManager.UserMode GetUserMode() throws DataRepositorySingletonNotInitialized
    {
        if (mUserMode == ApplicationManager.UserMode.Invalid) throw new DataRepositorySingletonNotInitialized();

        return mUserMode;
    }

    public ArrayList<Problem> GetProblemsForPatientId(String patientId)
    {
        ArrayList<Problem> matchingProblems = new ArrayList<>();
        for (Problem problem : mProblemList)
        {
            if (problem.getPatientId().equals(patientId)) matchingProblems.add(problem);
        }
        return matchingProblems;
    }

    public ArrayList<CareProviderRecord> GetCareGiverRecordsForProblemId(String problemId)
    {
        ArrayList<CareProviderRecord> matchingRecords = new ArrayList<>();
        for (CareProviderRecord record : mCareProviderRecordsList)
        {
            if (record.getProblemId().equals(problemId)) matchingRecords.add(record);
        }

        return matchingRecords;
    }

    public ArrayList<PatientRecord> GetPatientRecordsForProblem(String problemId)
    {
        ArrayList<PatientRecord> matchingRecords = new ArrayList<>();
        for (PatientRecord record : mPatientRecordList)
        {
            if (record.getProblemId().equals(problemId)) matchingRecords.add(record);
        }

        return matchingRecords;
    }

    public boolean DoesUserExist(String userName, ApplicationManager.UserMode userMode)
    {
        try
        {
            if (userMode == ApplicationManager.UserMode.Patient)
            {
                Patient user;
                ContactInfo tempContact = new ContactInfo("", "");
                user = new Patient("temp", new ArrayList<String>(), tempContact);
                return mESM.existObject(userName, user.getElasticsearchType(), Patient.class);
            }
            else if (userMode == ApplicationManager.UserMode.CareGiver)
            {
                CareProvider user;
                user = new CareProvider("tempid", new ArrayList<String>());
                return mESM.existObject(userName, user.getElasticsearchType(), CareProvider.class);
            }
        }
        catch(ElasticsearchManager.OperationFailedException e)
        {
            Log.e(tag, "Get operation in ESM failed");
            e.printStackTrace();
        }
        return false;
    }

    public boolean DoesProblemExist(String problemId)
    {
        for (Problem currentProblem : mProblemList)
        {
            if (currentProblem.getId().equals(problemId)) return true;
        }
        return false;
    }

    public boolean DoesCareGiverRecordExist(String problemId, String recordId)
    {
        for (CareProviderRecord currentRecord : mCareProviderRecordsList)
        {
            if (currentRecord.getProblemId().equals(problemId) && currentRecord.getId().equals(recordId)) return true;
        }
        return false;
    }

    public boolean DoesPatientRecordExist(String problemId, String recordId)
    {
        for (PatientRecord currentRecord : mPatientRecordList)
        {
            if (currentRecord.getProblemId().equals(problemId) && currentRecord.getId().equals(recordId)) return true;
        }
        return false;
    }

    public class DataRepositorySingletonNotInitialized extends Exception
    {
        public DataRepositorySingletonNotInitialized() { super("DataRepositorySingleton has not been initialized yet!"); }
    }

    public class InvalidUserMode extends Exception
    {
        public InvalidUserMode(String message) { super(message); }
    }
}
