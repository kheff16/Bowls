package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import java.io.IOException;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseCallback;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;

import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ACCOUNT_TYPE_BUSINESS;
import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ACCOUNT_TYPE_CUSTOMER;
import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.PROFILE_EDITED;

public class EditProfile extends AppCompatActivity {

    private BowlsUser user;
    private String currentSCUserID;
    private EditText editName;
    private Switch editAcctTypeSwitch;
    private BowlsFirebase bowlsFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        bowlsFirebase = new BowlsFirebase();

        editName = findViewById(R.id.edit_user_name);
        editAcctTypeSwitch = findViewById(R.id.account_type_toggle);

        Intent intent = getIntent();
        currentSCUserID = intent.getStringExtra("currentSCUserID");

        bowlsFirebase.getBowlsUser(currentSCUserID, new BowlsFirebaseCallback<BowlsUser>() {
            @Override
            public void callback(BowlsUser data) {
                if (data != null) {
                    user = data;

                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            editName.setHint(user.getFullname());
                            editAcctTypeSwitch.setChecked(user.getAccountType()== ACCOUNT_TYPE_BUSINESS);

                        }
                    });
                }
            }
        });
    }


    public void cancel(View view) {
        finish();
    }

    public void confirm(View view) {
        //Change user's screen name, profile pic, and location in firebase
        String rawNewName = editName.getText().toString();

        boolean newNameSet = false;


        if (((rawNewName.length()) > 0) && (rawNewName != user.getFullname())) {
            newNameSet = true;
            user.setFullname(rawNewName);
            bowlsFirebase.uploadUser(user);
        }

        if(newNameSet) {
            setResult(PROFILE_EDITED); }
            finish();
        }


}
