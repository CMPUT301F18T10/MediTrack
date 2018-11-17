package com.example.meditrack;

import android.util.Log;

public class ProblemManagerService
{
    /* This class is responsible for operating on Problems and Records
       This would include Add/Edit/Delete Problem and
       Add/Delete Record

       All Activities that needs to manipulate problems/records would
       collect the necessary info from user and call this service to
       update the DataRepositorySingleton instance holding the information
     */
    public ProblemManagerService(){}

    public static void AddProblem(Problem problem)
    {
        if (DataRepositorySingleton.GetInstance().DoesProblemExist(problem.getId())){

            Log.e("Failure", "Problem Id already exists");
        }

        else{
            DataRepositorySingleton.GetInstance().AddProblem(problem);
        }
    }

    public static void EditProblem(Problem problem)
    {
        if (!DataRepositorySingleton.GetInstance().DoesProblemExist(problem.getId())){

            Log.e("Failure", "Problem does not exist");
        }

        else{
            DataRepositorySingleton.GetInstance().EditProblem(problem);
        }
    }

    public static void DeleteProblem(String problemId)
    {
        if (!DataRepositorySingleton.GetInstance().DoesProblemExist(problemId)){

            Log.e("Failure", "Problem does not exist");
        }

        else{
            DataRepositorySingleton.GetInstance().DeleteProblem(problemId);
        }
    }

    public static void AddPatientRecord(PatientRecord record)
    {
        if (DataRepositorySingleton.GetInstance().DoesPatientRecordExist(record.getProblemId(), record.getId())){

            Log.e("Failure", "Record Id already exists");
        }

        else{
            DataRepositorySingleton.GetInstance().AddPatientRecord(record);
        }
    }

    public static void AddCareProviderRecord(CareProviderRecord record)
    {
        if (DataRepositorySingleton.GetInstance().DoesCareGiverRecordExist(record.getProblemId(), record.getId())){

            Log.e("Failure", "Record Id already exists");
        }

        else{
            DataRepositorySingleton.GetInstance().AddCareProviderRecord(record);
        }
    }

    public static void DeletePatientRecord(String problemId, String recordId)
    {
        if (!DataRepositorySingleton.GetInstance().DoesPatientRecordExist(problemId, recordId)){

            Log.e("Failure", "Patient Record does not exist");
        }

        else{
            DataRepositorySingleton.GetInstance().DeletePatientRecord(recordId);
        }
    }

    public static void DeleteCareProviderRecord(String problemId, String recordId)
    {
        if (!DataRepositorySingleton.GetInstance().DoesCareGiverRecordExist(problemId, recordId)){

            Log.e("Failure", "Care Provider Record does not exist");
        }

        else{
            DataRepositorySingleton.GetInstance().DeleteCareProviderRecord(recordId);
        }
    }
}
