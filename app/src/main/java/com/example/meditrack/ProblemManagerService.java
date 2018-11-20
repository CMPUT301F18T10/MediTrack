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

    public static void AddProblem(Problem problem) throws ObjectIdAlreadyExists
    {
        if (DataRepositorySingleton.GetInstance().DoesProblemExist(problem.getId())){

            throw new ObjectIdAlreadyExists("Problem Id already exists");
        }

        else{
            DataRepositorySingleton.GetInstance().AddProblem(problem);
        }
    }

    public static void EditProblem(Problem problem) throws ObjectNotFound
    {
        if (!DataRepositorySingleton.GetInstance().DoesProblemExist(problem.getId())){

            throw new ObjectNotFound("Problem does not exist");
        }

        else{
            DataRepositorySingleton.GetInstance().EditProblem(problem);
        }
    }

    public static void DeleteProblem(String problemId) throws ObjectNotFound
    {
        if (!DataRepositorySingleton.GetInstance().DoesProblemExist(problemId)){

            throw new ObjectNotFound("Problem does not exist");
        }

        else{
            DataRepositorySingleton.GetInstance().DeleteProblem(problemId);
        }
    }

    public static void AddPatientRecord(PatientRecord record) throws ObjectIdAlreadyExists
    {
        if (DataRepositorySingleton.GetInstance().DoesPatientRecordExist(record.getProblemId(), record.getId())){

            throw new ObjectIdAlreadyExists("Patient Record Id already exists");
        }

        else{
            DataRepositorySingleton.GetInstance().AddPatientRecord(record);
        }
    }

    public static void AddCareProviderRecord(CareProviderRecord record) throws ObjectIdAlreadyExists
    {
        if (DataRepositorySingleton.GetInstance().DoesCareGiverRecordExist(record.getProblemId(), record.getId())){

            throw new ObjectIdAlreadyExists("Care Provider Record Id already exists");
        }

        else{
            DataRepositorySingleton.GetInstance().AddCareProviderRecord(record);
        }
    }

    public static void DeletePatientRecord(String problemId, String recordId) throws ObjectNotFound
    {
        if (!DataRepositorySingleton.GetInstance().DoesPatientRecordExist(problemId, recordId)){

            throw new ObjectNotFound("Patient Record does not exist");

        }

        else{
            DataRepositorySingleton.GetInstance().DeletePatientRecord(recordId);
        }
    }

    public static void DeleteCareProviderRecord(String problemId, String recordId) throws ObjectNotFound
    {
        if (!DataRepositorySingleton.GetInstance().DoesCareGiverRecordExist(problemId, recordId)){

            throw new ObjectNotFound("Care Provider Record does not exist");
        }

        else{
            DataRepositorySingleton.GetInstance().DeleteCareProviderRecord(recordId);
        }
    }
    public static class ObjectIdAlreadyExists extends Exception
    {
        public ObjectIdAlreadyExists(String message) { super(message); }
    }

    public static class ObjectNotFound extends Exception
    {
        public ObjectNotFound(String message) { super(message); }
    }
}
