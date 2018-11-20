package com.example.meditrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ProblemsListActivity extends AppCompatActivity {

    private ArrayList<Problem> mProblemList;
    private String mPatientId;
    private DataRepositorySingleton mDRS;
    private ApplicationManager.UserMode mUserMode = ApplicationManager.UserMode.Invalid;
    private static final String tag = "ProblemsListActivity";

    public class ProblemListAdapter extends ArrayAdapter<Problem> {
        private ArrayList<Problem> mProblemList;
        Context mContext;

        public ProblemListAdapter(ArrayList<Problem> problemList, Context context)
        {
            super(context, R.layout.problem_list_row_item, problemList);
            mProblemList = problemList;
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Problem problem = mProblemList.get(position);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.problem_list_row_item, parent, false);

            TextView problemTitle = (TextView) rowView.findViewById(R.id.problemTitle);
            TextView problemDescription = (TextView) rowView.findViewById(R.id.problemDescription);
            TextView problemTimeStamp = (TextView) rowView.findViewById(R.id.timeStamp);

            problemTitle.setText(problem.getTitle());
            problemDescription.setText(problem.getDescription());
            problemTimeStamp.setText(problem.getDate().toString());
            return rowView;
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mPatientId = intent.getStringExtra("patientID");
        mDRS = DataRepositorySingleton.GetInstance();
        try { mUserMode = mDRS.GetUserMode(); }
        catch (DataRepositorySingleton.DataRepositorySingletonNotInitialized e)
        {
            Log.e(tag, "DataRepositorySingleton not yet initialized. It is expected to be at this point");
        }
        mProblemList = mDRS.GetProblemsForPatientId(mPatientId);

        TextView ProblemListActivityTitle = (TextView) findViewById(R.id.problemListTitle);
        ProblemListActivityTitle.setText(mPatientId);

        FloatingActionButton addFAB = (FloatingActionButton) findViewById(R.id.problemsListAddFAB);
        View fabButtonView = findViewById(R.id.problemsListAddFAB);
        if (!ApplicationManager.IsFeatureAllowed("AddProblem", mUserMode)) fabButtonView.setVisibility(View.GONE);

        // When Add Problem is clicked, we create a new Problem with default values,
        // save it locally and in the server and then pass the problemId to viewProblemActivity
        // viewProblemActivity will then display the default and let the user fill it in with "real" data
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Problem problem = new Problem("Default Title", "Default Description", mPatientId);
                String problemId = problem.getId();
                try{
                    ProblemManagerService.AddProblem(problem);}
                catch(Exception ObjectAlreadyExists){
                    System.err.println("ObjectAlreadyException Csught"+ObjectAlreadyExists);}
                ApplicationManager.UpdateDataRepository();
                Intent intent = new Intent(ProblemsListActivity.this, viewProblemActivity.class);
                intent.putExtra("problemId", problemId);
                startActivity(intent);
            }
        });
        ListView listView = findViewById(R.id.problemsListListView);
        ProblemListAdapter adapter = new ProblemListAdapter(mProblemList, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProblemsListActivity.this, viewProblemActivity.class);
                intent.putExtra("problemId", mProblemList.get(position).getId());
                startActivity(intent);
            }
        });
        FloatingActionButton searchFAB = (FloatingActionButton) findViewById(R.id.problemsListSearchFAB);
        searchFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(ProblemsListActivity.this, searchActivity.class);
                startActivity(searchIntent);
            }
        });
        FloatingActionButton profileFAB = (FloatingActionButton) findViewById(R.id.problemsListProfileFAB);
        profileFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(ProblemsListActivity.this, ProfileInformationActivity.class);
                profileIntent.putExtra("patientID", mPatientId);
                startActivity(profileIntent);
            }
        });

    }

}
