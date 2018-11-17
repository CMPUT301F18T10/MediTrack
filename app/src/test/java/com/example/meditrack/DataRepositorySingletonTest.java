package com.example.meditrack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DataRepositorySingletonTest
{
    public class MockElasticSearchManager extends ElasticsearchManager
    {
        public ArrayList<Problem> mProblems;
        public ArrayList<CareProviderRecord> mCareProviderRecords;
        public ArrayList<PatientRecord> mPatientRecords;
        public ArrayList<CareProvider> mCareProviders;
        public ArrayList<Patient> mPatients;

        public MockElasticSearchManager()
        {
            initElasticsearch();
        }

        @Override
        protected void initElasticsearch()
        {
            mProblems = new ArrayList<>();
            mCareProviderRecords = new ArrayList<>();
            mPatientRecords = new ArrayList<>();
            mCareProviders = new ArrayList<>();
            mPatients = new ArrayList<>();
        }

        @Override
        public <T extends ElasticsearchStorable> void addObject (T t) throws ObjectAlreadyExistsException, OperationFailedException
        {
            if (existObject(t.getId(), t.getElasticsearchType(), t.getClass())) throw new ObjectAlreadyExistsException();

            switch(t.getElasticsearchType())
            {
                case "problems":
                    Problem problem = (Problem) t;
                    mProblems.add(problem);
                    break;
                case "care_providers":
                    CareProvider user = (CareProvider) t;
                    mCareProviders.add(user);
                    break;
                case "patients":
                    Patient patient = (Patient) t;
                    mPatients.add(patient);
                    break;
                case "care_provider_records":
                    CareProviderRecord CPRecord = (CareProviderRecord) t;
                    mCareProviderRecords.add(CPRecord);
                    break;
                case "patient_records":
                    PatientRecord patientRecord = (PatientRecord) t;
                    mPatientRecords.add(patientRecord);
                    break;
                default:
                    throw new OperationFailedException();
            }
        }

        @Override
        public void deleteObject(String id, String type, Class<? extends ElasticsearchStorable> cls) throws ObjectNotFoundException, OperationFailedException
        {
            if (!existObject(id, type, cls)) throw new ObjectNotFoundException();

            if (cls == Problem.class)
            {
                for (Problem currentProblem : mProblems)
                {
                    if (currentProblem.getId().equals(id)) mProblems.remove(currentProblem);
                }
            }
            else if (cls == CareProvider.class)
            {
                for (CareProvider currentUser : mCareProviders)
                {
                    if (currentUser.getId().equals(id)) mCareProviders.remove(currentUser);
                }
            }
            else if (cls == Patient.class)
            {
                for (Patient currentUser : mPatients)
                {
                    if (currentUser.getId().equals(id)) mPatients.remove(currentUser);
                }
            }
            else if (cls == CareProviderRecord.class)
            {
                for (CareProviderRecord currentRecord : mCareProviderRecords)
                {
                    if (currentRecord.getId().equals(id)) mCareProviderRecords.remove(currentRecord);
                }
            }
            else if (cls == PatientRecord.class)
            {
                for (PatientRecord currentRecord : mPatientRecords)
                {
                    if (currentRecord.getId().equals(id)) mPatientRecords.remove(currentRecord);
                }
            }
            else throw new OperationFailedException();
        }

        @Override
        public <T extends ElasticsearchStorable> void updateObject(String id, String type, T obj) throws ObjectNotFoundException, OperationFailedException
        {
            deleteObject(obj.getId(), obj.getElasticsearchType(), obj.getClass());
            try {
                addObject(obj);
            } catch (ObjectAlreadyExistsException e) {
                throw new OperationFailedException();
            }
        }

        @Override
        public <T extends ElasticsearchStorable> T getObjectFromId(String id, String type, Class<? extends ElasticsearchStorable> cls) throws ObjectNotFoundException, OperationFailedException
        {
            if (cls == Problem.class)
            {
                for (Problem currentProblem : mProblems)
                {
                    if (currentProblem.getId().equals(id)) return (T)currentProblem;
                }
                throw new ObjectNotFoundException();
            }
            else if (cls == CareProvider.class)
            {
                for (CareProvider currentUser : mCareProviders)
                {
                    if (currentUser.getId().equals(id)) return (T)currentUser;
                }
                throw new ObjectNotFoundException();
            }
            else if (cls == Patient.class)
            {
                for (Patient currentUser : mPatients)
                {
                    if (currentUser.getId().equals(id)) return (T)currentUser;
                }
                throw new ObjectNotFoundException();
            }
            else if (cls == CareProviderRecord.class)
            {
                for (CareProviderRecord currentRecord : mCareProviderRecords)
                {
                    if (currentRecord.getId().equals(id)) return (T) currentRecord;
                }
                throw new ObjectNotFoundException();
            }
            else if (cls == PatientRecord.class)
            {
                for (PatientRecord currentRecord : mPatientRecords)
                {
                    if (currentRecord.getId().equals(id)) return (T) currentRecord;
                }
                throw new ObjectNotFoundException();
            }
            else throw new OperationFailedException();
        }

        @Override
        public ArrayList<Problem> getProblemsByPatientId(String patientId)
        {
            ArrayList<Problem> matchingProblems = new ArrayList<>();
            for (Problem currentProblem : mProblems)
            {
                if (currentProblem.getPatientId().equals(patientId)) matchingProblems.add(currentProblem);
            }
            return matchingProblems;
        }

        @Override
        public ArrayList<PatientRecord> getPatientRecordByProblemId(String problemId)
        {
            ArrayList<PatientRecord> matchingRecords = new ArrayList<>();
            for (PatientRecord currentRecord : mPatientRecords)
            {
                if (currentRecord.getProblemId().equals(problemId)) matchingRecords.add(currentRecord);
            }
            return matchingRecords;
        }

        @Override
        public ArrayList<CareProviderRecord> getCareProviderRecordByProblemId(String problemId)
        {
            ArrayList<CareProviderRecord> matchingRecords = new ArrayList<>();
            for (CareProviderRecord currentRecord : mCareProviderRecords)
            {
                if (currentRecord.getProblemId().equals(problemId)) matchingRecords.add(currentRecord);
            }
            return matchingRecords;
        }


        @Override
        public boolean existObject(String id, String type, Class<? extends ElasticsearchStorable> cls) throws OperationFailedException
        {
            try {
                getObjectFromId(id, type, cls);
            } catch (ObjectNotFoundException e) {
                return false;
            }
            return true;
        }
    }
    @Before
    public void SetUp()
    {

    }

    @After
    public void TearDown()
    {

    }

    @Test
    public void MockTest()
    {
        MockElasticSearchManager mESM = new MockElasticSearchManager();
    }


}
