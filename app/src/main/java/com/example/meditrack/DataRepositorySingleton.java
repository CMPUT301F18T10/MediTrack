package com.example.meditrack;

import android.util.Log;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
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

    private AbstractUser mUser = null;

    private ApplicationManager.UserMode mUserMode = ApplicationManager.UserMode.Invalid;
    private String mUserName;

    private static Patient patient;
    private static CareProvider careProvider;

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
        AbstractUser user = null;
        ContactInfo tempContact = new ContactInfo("", "");
        if (userMode == ApplicationManager.UserMode.Patient) user = new Patient("temp", new ArrayList<String>(), tempContact);
        else if (userMode == ApplicationManager.UserMode.CareGiver) user = new CareProvider("tempid", new ArrayList<String>());

        try
        {
            mUser = mESM.getObjectFromId(userName, user.getElasticsearchType());
        }
        catch(ElasticsearchManager.ObjectNotFoundException e)
        {
            e.printStackTrace();
        } catch (ElasticsearchManager.OperationFailedException e) {
            e.printStackTrace();
        }
    }

    private void PopulateProblemList()
    {

    }

    private void PopulateRecordList()
    {

    }

    public void Initialize(ApplicationManager.UserMode userMode, String userName, ElasticsearchManager esm)
    {
        mDirty = false;
        mUserMode = userMode;
        mUserName = userName;
        mESM = esm;
        DownloadData(userMode, userName);
    }

    private void UploadData()
    {
        // TODO: Finish the method
        // Go through all the deques and post them to ElasticSearch
        // Send the new Problems
        // Send the new Records
        // Send the edited Problems
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
    public AbstractUser GetUser() throws DataRepositorySingletonNotInitialized
    {
        if (mUser == null) throw new DataRepositorySingletonNotInitialized();

        return mUser;
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
        // TODO: check ElasticSearch object for the user
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
    public static Patient GetPatient(){
        return patient;
    }
    public static CareProvider GetCareProvider(){
        return careProvider;
    }
}
