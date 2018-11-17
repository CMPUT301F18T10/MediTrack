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

            Log.e("Failure", "Problem Id does not exist");
        }

        else{
            DataRepositorySingleton.GetInstance().EditProblem(problem);
        }
    }

    public static void DeleteProblem(String problemId)
    {
        if (!DataRepositorySingleton.GetInstance().DoesProblemExist(problemId)){

            Log.e("Failure", "Problem Id does not exist");
        }

        else{
            DataRepositorySingleton.GetInstance().DeleteProblem(problemId);
        }
    }

    public static void AddPatientRecord(Integer problemId, AbstractRecord record)
    {
        // TODO: Finish the method
    }

    public static void DeleteRecord(Integer recordId)
    {
        // TODO: Finish the method
    }
}
