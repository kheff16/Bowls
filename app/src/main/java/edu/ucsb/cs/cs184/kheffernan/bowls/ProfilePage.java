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

        userInfoTextView = (TextView) findViewById(R.id.profile_info_text);
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
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "User was not null!",
                                Toast.LENGTH_SHORT);

                        toast.show();
                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                userInfoTextView.setText("My user info:\nfull name = "+user.getFullname()+"\nemail = "+user.getEmail()
                                +"\nuserID = "+user.getUserID()
                                +"\nacctType ="+ user.getAccountType());
                            }



                        });
                    }
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "User was null!",
                                Toast.LENGTH_SHORT);

                        toast.show();
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
