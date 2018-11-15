package com.example.meditrack;

import java.util.ArrayList;
import java.util.Deque;

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

    private boolean mDirty;

    // mProblemList will contain all relevant problems
    // whether new, edited or otherwise, it will model the server
    // it will get dumped and repopulated on updates
    private ArrayList<Problem> mProblemList;
    private Deque<Problem> mNewProblems;
    private Deque<Problem> mEditedProblems;
    private Deque<Problem> mDeletedProblems;

    private ArrayList<PatientRecord> mPatientRecordList;
    private Deque<PatientRecord> mNewPatientRecords;
    private Deque<PatientRecord> mDeletedPatientRecords;

    private ArrayList<CareGiverRecord> mCareGiverRecordList;
    private Deque<CareGiverRecord> mNewCareGiverRecords;
    private Deque<CareGiverRecord> mDeletedCareGiverRecords;

    private AbstractUser mUser = null;

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

    }

    private void PopulateProblemList()
    {

    }

    private void PopulateRecordList()
    {

    }

    public void Initialize(ApplicationManager.UserMode userMode, String userName)
    {
        // TODO: This function would populate all data in the class
        mDirty = false;
        mUserMode = userMode;
        mUserName = userName;
        DownloadData(userMode, userName);
    }

    private void UploadData()
    {
        // TODO: Finish the method
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

    public ArrayList<CareGiverRecord> GetCareGiverRecordsForProblemId(String problemId)
    {
        ArrayList<CareGiverRecord> matchingRecords = new ArrayList<>();
        for (CareGiverRecord record : mCareGiverRecordList)
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
        for (CareGiverRecord currentRecord : mCareGiverRecordList)
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
}
