package com.example.meditrack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class ProblemManagerServiceTest
{
    Problem problem;
    Problem problemToBeDeleted;
    Problem problemToBeEdited;
    CareProviderRecord record;
    CareProviderRecord record2;
    @Before
    public void SetUp()
    {
        problem = new Problem("Test Title", "Test Description", new Date(), 1);
        problemToBeDeleted = new Problem("Test Title 2", "Test Description 2", new Date(), 100);
        problemToBeEdited = new Problem("Test Title", "Test Description", new Date());
        record = new CareProviderRecord(new Date(), "Test Comment", "Test ID");
    }

    @After
    public void TearDown()
    {

    }

    @Test
    public void TestAddProblem() throws Exception
    {
        ProblemManagerService.AddProblem(problem);
        assert(SearchManager.getProblemForTitle("Test Title").getDescription() == problem.getDescription());
    }

    @Test(expected = Exception.class)
    public void TestDeleteProblem() throws Exception
    {
        ProblemManagerService.AddProblem(problemToBeDeleted);
        ProblemManagerService.DeleteProblem(100);
        SearchManager.getProblemForTitle(problemToBeDeleted.getTitle());
    }

    @Test
    public void TestEditProblem() throws Exception
    {
        ProblemManagerService.AddProblem(problemToBeEdited);
        problemToBeEdited.setDescription("Edited Description");
        ProblemManagerService.EditProblem(problemToBeEdited);

        assert(SearchManager.getProblemForTitle(problemToBeEdited.getTitle()).getDescription() == problemToBeEdited.getDescription());
    }

    @Test
    public void TestAddAndDeleteRecord() throws Exception
    {
        assert(SearchManager.GetRecordCountForProblemId(1) == 0);

        ProblemManagerService.AddRecord(1, record);
        assert(SearchManager.GetRecordCountForProblemId(1) ==  1);

        ProblemManagerService.AddRecord(1, record2);
        assert(SearchManager.GetRecordCountForProblemId(1) ==  2);

        ProblemManagerService.DeleteRecord(1, record);
        assert(SearchManager.GetRecordCountForProblemId(1) == 1);
    }

}
