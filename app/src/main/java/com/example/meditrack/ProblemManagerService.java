package com.example.meditrack;

public class ProblemManagerService
{
    /* This class is responsible for operating on Problems and Records
       This would include Add/Edit/Delete Problem and
       Add/Delete Record

       All Activities that needs to manipulate problems/records would
       collect the necessary info from user and call this service to
       update the DataRepositorySingleton instance holding the information
     */
    ProblemManagerService(){}

    public static void AddProblem(Problem problem)
    {
        // TODO: Finish the method
    }

    public static void EditProblem(Problem problem)
    {
        // TODO: Finish the method
    }

    public static void DeleteProblem(Integer problemId)
    {
        // TODO: Finish the method
    }

    public static void AddRecord(Integer problemId, AbstractRecord record)
    {
        // TODO: Finish the method
    }

    public static void DeleteRecord(Integer recordId)
    {
        // TODO: Finish the method
    }
}
