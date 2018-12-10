package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseAuth;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseCallback;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;

import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.REQUEST_EDIT_PROFILE;


public class ProfilePage extends AppCompatActivity {


    private BowlsUser user;
    private TextView userInfoTextView;
    private TextView nameHeader;
    private TextView nameValue;
    private TextView emailHeader;
    private TextView emailValue;
    private TextView userHeader;
    private TextView userValue;
    private TextView typeHeader;
    private TextView typeValue;
    private BowlsFirebase bowlsFirebase;
    private BowlsFirebaseAuth bowlsFirebaseAuth;
    private Button editProfileBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize interface objects
        bowlsFirebase = new BowlsFirebase();
        bowlsFirebaseAuth = new BowlsFirebaseAuth();

        nameHeader = (TextView) findViewById(R.id.nameHeader);
        nameValue = (TextView) findViewById(R.id.nameValue);

        emailHeader = (TextView) findViewById(R.id.emailHeader);
        emailValue = (TextView) findViewById(R.id.emailValue);

        userHeader = (TextView) findViewById(R.id.idHeader);
        userValue = (TextView) findViewById(R.id.idValue);

        typeHeader = (TextView) findViewById(R.id.typeHeader);
        typeValue = (TextView) findViewById(R.id.typeValue);



        //userInfoTextView = (TextView) findViewById(R.id.profile_info_text);
        editProfileBtn = (Button) findViewById(R.id.edit_profile_btn);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });

        if (bowlsFirebaseAuth.getCurrentUser()!= null){
            final String currentUserID = bowlsFirebaseAuth.getCurrentUser().getUid();

            final ProgressDialog dialog = ProgressDialog.show(
                    ProfilePage.this,
                    "",
                    "Fetching profile details...",
                    true
            );
            bowlsFirebase.getBowlsUser(currentUserID, new BowlsFirebaseCallback<BowlsUser>() {
                @Override
                public void callback(BowlsUser data) {
                    dialog.dismiss();
                    if(data != null) {
                        user = data;



                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                nameValue.setText("\t\t\t" + user.getFullname() + '\n');
                                emailValue.setText("\t\t\t" + user.getEmail()+ '\n');
                                userValue.setText("\t\t\t" + user.getUserID()+ '\n');
                                typeValue.setText("\t\t\t" + user.getAccountType()+ '\n');
                            }



                        });
                    }

                }
            });
        }

    }//-------[END OnCreate]------


    public void editProfile(){
        if(user != null) {
            Intent intent = new Intent(this, EditProfile.class);
            intent.putExtra("currentSCUserID", user.getUserID());
            startActivityForResult(intent, REQUEST_EDIT_PROFILE);
        }
    }



}
