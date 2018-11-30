package com.example.meditrack;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.UUID;

import static com.example.meditrack.ApplicationManager.UserMode.CareGiver;

public class LoginActivity extends AppCompatActivity {
    private Context mContext;
    private EditText mUserEmailEdit;
    private String securityCodeText = null;
    private String mUserEmail;
    private RadioButton mCaretakerRadio;
    private ApplicationManager mApplicationManager;
    private ArrayList<String> approvedDeviceIDs = new ArrayList<String>();
    private ArrayList<String> GivenApprovedDeviceIDs;
    final String deviceId = UUID.randomUUID().toString();
    private boolean isCorrect =false;
    private boolean userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        ImageView imgView = (ImageView) findViewById(R.id.loginImageView);
        imgView.setImageResource(R.drawable.projectlogo);



    }
    public void loginClick(View v){
        boolean isContain;
        mUserEmailEdit = (EditText) findViewById(R.id.loginEmail);
        mUserEmail = mUserEmailEdit.getText().toString();
        mCaretakerRadio = (RadioButton) findViewById(R.id.caretakerRadioButton);
        userType = mCaretakerRadio.isChecked();
        String shortCode;
        // TODO: We need exception handling at this stage
        if(userType){
            mApplicationManager = new ApplicationManager(ApplicationManager.UserMode.CareGiver);
            Intent caretakerIntent = new Intent(LoginActivity.this, PatientsListActivity.class);
            if (mApplicationManager.DoesUserExist(mUserEmail))
            {   // Log in
                mApplicationManager.LogIn(mUserEmail, DataRepositorySingleton.GetInstance());

                GivenApprovedDeviceIDs = mApplicationManager.getapprovedDevicesID(mUserEmail,DataRepositorySingleton.GetInstance());
                isContain = GivenApprovedDeviceIDs.contains(deviceId);
                shortCode = mApplicationManager.getUserShortCode(mUserEmail,DataRepositorySingleton.GetInstance());
                //The Device is not In ApprovedDevicesIDs ArrayList, We need to user to input security code to access its authority
                if (!isContain){
                    BuildAlertDialog(shortCode);

                }
                else{
                    caretakerIntent.putExtra("caretakerID", mUserEmail);
                    startActivity(caretakerIntent);
                    }
            }
            else{
                Toast.makeText(this, "This User Email Is Not Registered, Please Register it!",Toast.LENGTH_LONG).show();
            }

        }
        else{
            mApplicationManager = new ApplicationManager(ApplicationManager.UserMode.Patient);
            Intent patientIntent = new Intent(LoginActivity.this,ProblemsListActivity.class);
            if(mApplicationManager.DoesUserExist(mUserEmail))
            {   // Log in
                //
                mApplicationManager.LogIn(mUserEmail, DataRepositorySingleton.GetInstance());
                GivenApprovedDeviceIDs = mApplicationManager.getapprovedDevicesID(mUserEmail,DataRepositorySingleton.GetInstance());
                Log.e("DeviceCode",deviceId.toString());
                isContain = GivenApprovedDeviceIDs.contains(deviceId);
                shortCode = mApplicationManager.getUserShortCode(mUserEmail,DataRepositorySingleton.GetInstance());
                if (!isContain){
                    BuildAlertDialog(shortCode);

                }
                else{
                    Log.e("DeviceCode","This is authorized device");
                    patientIntent.putExtra("patientID", mUserEmail);
                    startActivity(patientIntent);
                }

            }
            else
            {// Register user and then log in
                Toast.makeText(this, "This User Email Is Not Registered, Please Register it!",Toast.LENGTH_LONG).show();
            }

        }


    }
    public void RegisterClick(View v){
        mUserEmailEdit = (EditText) findViewById(R.id.loginEmail);
        mUserEmail = mUserEmailEdit.getText().toString();
        mCaretakerRadio = (RadioButton) findViewById(R.id.caretakerRadioButton);
        String shortCode;
        if (mCaretakerRadio.isChecked()) {
            mApplicationManager = new ApplicationManager(ApplicationManager.UserMode.CareGiver);
            // Register user and then log in
            if (mApplicationManager.DoesUserExist(mUserEmail)){
                Toast.makeText(this, "This User Email Has Registered, Please Login it!",Toast.LENGTH_LONG).show();
            }
            else
            {
                approvedDeviceIDs.add(deviceId);
                mApplicationManager.RegisterUser(mUserEmail,approvedDeviceIDs);

                // TODO: Implement timeout in case registration fails due to IO exceptions.
                // TODO: ApplicationManager should provide a boolean output so the app knows about failed registrations
                // TODO: Alternatively, don't launch login after registration, let user login themselves
                
                // Wait until registration finishes before logging in
                while (!mApplicationManager.DoesUserExist(mUserEmail)) {}
                mApplicationManager.LogIn(mUserEmail, DataRepositorySingleton.GetInstance());
                shortCode = mApplicationManager.getUserShortCode(mUserEmail,DataRepositorySingleton.GetInstance());
                BuildDisplayAlertDialog(shortCode);

            }
        }
        else{
            mApplicationManager = new ApplicationManager(ApplicationManager.UserMode.Patient);
            if(mApplicationManager.DoesUserExist(mUserEmail)){
                Toast.makeText(this, "This User Email Has Registered, Please Login it!",Toast.LENGTH_LONG).show();
            }
            else{
                approvedDeviceIDs.add(deviceId);
                mApplicationManager.RegisterUser(mUserEmail,approvedDeviceIDs);
                // TODO: Implement timeout in case registration fails due to IO exceptions.
                // TODO: ApplicationManager should provide a boolean output so the app knows about failed registrations
                // TODO: Alternatively, don't launch login after registration, let user login themselves
                // Wait until registration finishes before logging in
                while (!mApplicationManager.DoesUserExist(mUserEmail)) {}
                mApplicationManager.LogIn(mUserEmail, DataRepositorySingleton.GetInstance());
                shortCode = mApplicationManager.getUserShortCode(mUserEmail,DataRepositorySingleton.GetInstance());
                BuildDisplayAlertDialog(shortCode);
                }

        }

    }


    public boolean IsCorrectSecurityCode(String givenSecurityCode, String inputSecurityCode){
        if (givenSecurityCode == null || inputSecurityCode ==null){
            return false;
        }
        else{
            if (givenSecurityCode.equals(inputSecurityCode)){
                return true;
            }
            else{
                return false;
            }

        }
    }
    public void GoToNextActivity(boolean RadioClick){
        Patient patient = null;
        CareProvider careProvider = null;
        if (!RadioClick){
            GivenApprovedDeviceIDs.add(deviceId);
            try {
                patient =  DataRepositorySingleton.GetInstance().GetPatientForId(mUserEmail);
            } catch (ItemNotFound itemNotFound) {
                itemNotFound.printStackTrace();
            }
            patient.setApprovedDeviceID(GivenApprovedDeviceIDs);
            try {
                DataRepositorySingleton.GetInstance().EditPatient(patient);
            } catch (DataRepositorySingleton.InvalidUserMode invalidUserMode) {
                invalidUserMode.printStackTrace();
            }
            Intent patinetIntent = new Intent(LoginActivity.this,ProblemsListActivity.class);
            patinetIntent.putExtra("patientID", mUserEmail);
            startActivity(patinetIntent);
        }
        else{
            GivenApprovedDeviceIDs.add(deviceId);
            try {
                careProvider = DataRepositorySingleton.GetInstance().GetCareProviderForId(mUserEmail);
            } catch (ItemNotFound itemNotFound) {
                itemNotFound.printStackTrace();
            }
            careProvider.setApprovedDeviceIDs(GivenApprovedDeviceIDs);
            try {
                DataRepositorySingleton.GetInstance().EditCareProvider(careProvider);
            } catch (DataRepositorySingleton.InvalidUserMode invalidUserMode) {
                invalidUserMode.printStackTrace();
            }
            Intent caretakerIntent = new Intent(LoginActivity.this,PatientsListActivity.class);
            caretakerIntent.putExtra("caretakerID", mUserEmail);
            startActivity(caretakerIntent);

        }
    }
    public void BuildDisplayAlertDialog(String shortCode){
        AlertDialog.Builder displaybuilder = new AlertDialog.Builder(mContext);
        displaybuilder.setTitle("Security Code");
        View viewInflated = LayoutInflater.from(mContext).inflate(R.layout.item_popio,findViewById(android.R.id.content), false);
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        input.setText(shortCode);
        input.setTextSize(40);
        input.setEnabled(false);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("There is Your Short Code:");
        input.setGravity(Gravity.CENTER);
        displaybuilder.setView(viewInflated);
        displaybuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                GoToNextActivity(userType);

            }
        });


        displaybuilder.show();
    }

    public void BuildAlertDialog(String shortCode){
        //Reference:https://stackoverflow.com/questions/10903754/input-text-dialog-android
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Security Code");
        View viewInflated = LayoutInflater.from(mContext).inflate(R.layout.item_popio,findViewById(android.R.id.content), false);
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                securityCodeText = input.getText().toString();
                isCorrect =  IsCorrectSecurityCode(securityCodeText,shortCode);
                if (!isCorrect){
                    Toast.makeText(mContext, "Wrong Security Code, Please Try Again",Toast.LENGTH_LONG).show();
                }
                else{
                    GoToNextActivity(userType);
                }
                dialog.dismiss();

            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

}
