package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseCallback;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;

public class HomePageActivity extends Activity {

    // SC Interface Vars
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private BowlsFirebase bowlsFirebase;

    // Local Vars
    private BowlsUser user;

    private TextView currentUserText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(android.R.style.Theme_Material_NoActionBar_Fullscreen);
        setContentView(R.layout.activity_home_page);

        currentUserText = (TextView) findViewById(R.id.current_user_id);

        bowlsFirebase = new BowlsFirebase();
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser userFirebase = firebaseAuth.getCurrentUser();
                if (userFirebase != null) {
                    // User is signed in - do nothing, stay in main view
                    currentUserText.setText("Email: " +userFirebase.getEmail() + "\nFirebase User: "+userFirebase.getUid());

                    bowlsFirebase.getBowlsUser(userFirebase.getUid(), new BowlsFirebaseCallback<BowlsUser>() {
                        @Override
                        public void callback(BowlsUser data) {
                            if(data != null) {
                                user = data;

                            }
                        }
                    });


                } else {
                    // No user is signed in, go to splash screen
                    goToSplashScreen();
                }
            }
        };


    }//[END OnCreate]


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void goToSplashScreen() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }




}
