package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseAuth;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseCallback;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.Order;

public class HomePageActivity extends Activity implements   NavigationView.OnNavigationItemSelectedListener{

    // SC Interface Vars
    private FirebaseAuth.AuthStateListener mAuthListener;
    private BowlsFirebaseAuth bowlsAuth;
    private BowlsFirebase bowlsFirebase;

    // Local Vars
    private BowlsUser user;

    //UI Vars
    private TextView currentUserText;
    private Button btnAddToDatabase;
    private DrawerLayout mDrawerLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setTheme(android.R.style.Theme_Material_NoActionBar_Fullscreen);
        setContentView(R.layout.activity_home_page);

        currentUserText = (TextView) findViewById(R.id.current_user_id);
        btnAddToDatabase = (Button) findViewById(R.id.btn_create_order);
        mDrawerLayout = findViewById(R.id.drawer_layout);



        bowlsFirebase = new BowlsFirebase();
        bowlsAuth = new BowlsFirebaseAuth();
        // Get current, logged in user
        final FirebaseUser currentUser = bowlsAuth.getCurrentUser();




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
        bowlsAuth.addAuthListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            bowlsAuth.removeAuthListener(mAuthListener);
        }
    }

    public void goToSplashScreen() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void createAnOrderBtnClicked(View view){
        Order newOrder = new Order("first order ever", bowlsAuth.getCurrentUser().getUid(), "address here", 12.99);
        bowlsFirebase.createNewOrder(newOrder);


    }


    public void viewProfileButtonClicked(){

    }

    public void menuButtonClicked(View view) {
        mDrawerLayout.openDrawer(Gravity.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.profile_page_button: {
                viewProfileButtonClicked();
                break;
            }

            case R.id.logout_button: {
                bowlsAuth.signOut();
                break;
            }

            case R.id.create_spot_entry_button: {

                break;
            }

            case R.id.option_get_place: {

                break;
            }

            case R.id.my_parking_spots_button: {

            }

        }
        //close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewListner() {
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }




}