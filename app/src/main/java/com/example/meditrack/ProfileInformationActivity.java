package com.example.meditrack;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ProfileInformationActivity extends AppCompatActivity {
    private String CaretakerAdd;
    public EditText userIDEdit = (EditText) findViewById(R.id.profileUserIDInput);
    public EditText phoneNumberEdit = (EditText) findViewById(R.id.profilePhoneInput);
    public EditText emailAddressEdit = (EditText) findViewById(R.id.profileEmailInput);

    private UserManager userManager = new UserManager();

    public ProfileInformationActivity() throws DataRepositorySingleton.InvalidUserMode, DataRepositorySingleton.DataRepositorySingletonNotInitialized {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView caretakerList = findViewById(R.id.profileCaretakerListListView);


        ArrayList<String> cpIds;
        cpIds = userManager.patient.getCareProviderId();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cpIds);
        caretakerList.setAdapter(adapter);

        caretakerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /** remove item from data and update listView */
                AlertDialog.Builder adb = new AlertDialog.Builder(getApplicationContext());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);
                final int pos = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        cpIds.remove(pos);
                        userManager.patient.setCareProviderId(cpIds);
                        adapter.notifyDataSetChanged();
                    }});
                adb.show();

            }
        });
        ListView imageList = findViewById(R.id.profileImageListListView);
        imageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /** remove and update */

            }
        });

    }
    public void addBodyImage(View v){
        /** same as uploading pictures of record, implement in later part
         */
    }
    public void addCaretaker(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext()); /** actually not sure if this is required or not, we can probably skip it*/
        builder.setTitle("Add Caretaker");
        final EditText input = new EditText(getApplicationContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        ListView caretakerList = findViewById(R.id.profileCaretakerListListView);
        ArrayList<String> cpIds;
        cpIds = userManager.patient.getCareProviderId();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cpIds);
        caretakerList.setAdapter(adapter);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CaretakerAdd=input.getText().toString();
                /** check if caretaker exists, if so, add this patient to caretaker list
                 */
                ApplicationManager.UserMode userMode = ApplicationManager.UserMode.CareGiver;
                ApplicationManager applicationManager = new ApplicationManager(userMode);
                if(applicationManager.DoesUserExist(CaretakerAdd) == true){
                    cpIds.add(CaretakerAdd);
                    userManager.patient.setCareProviderId(cpIds);
                    adapter.notifyDataSetChanged();
                };
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }
    public void profileSave(View v) {
        String phoneNumber = phoneNumberEdit.getText().toString();
        String emailAddress = emailAddressEdit.getText().toString();
        /** update these values data repository */
        ContactInfo contactInfo = new ContactInfo(emailAddress,phoneNumber);
        userManager.patient.setContactInfo(contactInfo);
    }

}
