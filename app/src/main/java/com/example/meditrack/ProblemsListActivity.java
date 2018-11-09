package com.example.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ProblemsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton addFAB = (FloatingActionButton) findViewById(R.id.problemsListAddFAB);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** this should take user to viewproblem page with default information created, it can then be edited.
                 * ONLY AVAILABLE TO PATIENT
                 */
                Intent intent = new Intent(ProblemsListActivity.this, viewProblemActivity.class);
                startActivity(intent);
            }
        });
        ListView listView = findViewById(R.id.problemsListListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProblemsListActivity.this, viewProblemActivity.class);
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
        /** need to add the profile FAB */

    }

}
