package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseAuth;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseCallback;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;
import edu.ucsb.cs.cs184.kheffernan.bowls.CreateOrder.CreateOrderActivity;
import edu.ucsb.cs.cs184.kheffernan.bowls.ManagerDashboardActivities.ManagerOrderView;
import edu.ucsb.cs.cs184.kheffernan.bowls.ViewOrders.MyOrders;

import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ACCOUNT_TYPE_BUSINESS;
import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_STATUS_COMPLETED;
import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_STATUS_CREATED;
import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_STATUS_IN_PROGRESS;
import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_STATUS_READY_FOR_PICK_UP;

public class HomePageActivity extends Activity implements   NavigationView.OnNavigationItemSelectedListener{

    // SC Interface Vars
    private FirebaseAuth.AuthStateListener mAuthListener;
    private BowlsFirebaseAuth bowlsAuth;
    private BowlsFirebase bowlsFirebase;

    // Local Vars
    private BowlsUser user;

    //UI Vars for Customer UI
    private TextView currentUserText;
    private Button btnAddToDatabase;
    private DrawerLayout mDrawerLayout;

    //UI Vars for Business UI
    private Button waitingToStart;
    private Button inProgress;
    private Button readyForPickUp;
    private Button completed;
    private Button refreshBtn;
    private Button logOutBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bowlsFirebase = new BowlsFirebase();
        bowlsAuth = new BowlsFirebaseAuth();
        // Get current, logged in user
        final FirebaseUser currentUser = bowlsAuth.getCurrentUser();

//        if (bowlsFirebase.getBowlsUser();
//        setContentView(R.layout.activity_home_page);
//
//        currentUserText = (TextView) findViewById(R.id.current_user_id);
//        mDrawerLayout = findViewById(R.id.drawer_layout);

//        setNavigationViewListner();





        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser userFirebase = firebaseAuth.getCurrentUser();
                if (userFirebase != null) {


                    // User is signed in - do nothing, stay in main view
//                    //sets the text based on info in Firebase Authentication
//                    currentUserText.setText("Your information:\nEmail: " +userFirebase.getEmail()
//                                    + "\nFirebase User Uid: "+userFirebase.getUid());


                    //sets the text using User object in Firebase Database
                    bowlsFirebase.getBowlsUser(userFirebase.getUid(), new BowlsFirebaseCallback<BowlsUser>() {
                        @Override
                        public void callback(BowlsUser data) {
                            if(data != null) {
                                user = data;

                                if (user.getAccountType().equals(ACCOUNT_TYPE_BUSINESS)){
                                    setContentView(R.layout.activity_home_page_manager);
                                    assignDashBoardButtons();
                                    updateBusinessUI();
                                }
                                else {
                                    setContentView(R.layout.activity_home_page);
                                    currentUserText = (TextView) findViewById(R.id.current_user_id);
                                    mDrawerLayout = findViewById(R.id.drawer_layout);
                                    currentUserText.append(" " + user.getFullname() + '\n');
                                    //currentUserText.setText("bowlsFirebase.getBowlsUser: \nFullname = " +user.getFullname());
                                    setNavigationViewListner();
                                }

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



    public void viewProfileButtonClicked() {
        Intent i = new Intent(this, ProfilePage.class);
        startActivity(i);
    }

    public void menuButtonClicked(View view) {
        mDrawerLayout.openDrawer(Gravity.START);
    }

    public void logOutBtnClicked(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign Out")
                .setMessage(("Are you sure you want to sign out?"))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        bowlsAuth.signOut();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.profile_page_btn: {
                viewProfileButtonClicked();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Navigating to your Profile",
                        Toast.LENGTH_SHORT);

                toast.show();
                break;
            }

            case R.id.logout_btn: {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Log out button clicked!",
                        Toast.LENGTH_SHORT);

                toast.show();
                logOutBtnClicked();
                break;
            }

            case R.id.my_orders_btn: {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Taking you to your recent and previous orders",
                        Toast.LENGTH_SHORT);

                toast.show();
                Intent i = new Intent(this, MyOrders.class);
                startActivity(i);
                break;
            }

            case R.id.view_menu_btn: {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Loading the Menu",
                        Toast.LENGTH_SHORT);

                toast.show();

                break;
            }

            case R.id.create_order_btn: {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Prepare your tastebuds...",
                        Toast.LENGTH_SHORT);
                toast.show();
                Intent i = new Intent(this, CreateOrderActivity.class);
                startActivity(i);

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

    private void updateBusinessUI(){

        bowlsFirebase.getTotalAllOrdersWithStatus(ORDER_STATUS_COMPLETED, new BowlsFirebaseCallback<Integer>() {
            @Override
            public void callback(Integer data) {
                completed.setText("Completed: ("+data+")");
            }
        });

        bowlsFirebase.getTotalAllOrdersWithStatus(ORDER_STATUS_CREATED, new BowlsFirebaseCallback<Integer>() {
            @Override
            public void callback(Integer data) {
                waitingToStart.setText("Waiting to Start: ("+data+")");
            }
        });

        bowlsFirebase.getTotalAllOrdersWithStatus(ORDER_STATUS_IN_PROGRESS, new BowlsFirebaseCallback<Integer>() {
            @Override
            public void callback(Integer data) {
                inProgress.setText("In Progress: ("+data+")");
            }
        });

        bowlsFirebase.getTotalAllOrdersWithStatus(ORDER_STATUS_READY_FOR_PICK_UP, new BowlsFirebaseCallback<Integer>() {
            @Override
            public void callback(Integer data) {
                readyForPickUp.setText("Ready for Pickup: ("+data+")");
            }
        });

    }

    public void assignDashBoardButtons(){
        waitingToStart = (Button) findViewById(R.id.waiting_to_start_btn);
        inProgress = (Button) findViewById(R.id.in_progress_btn);
        readyForPickUp = (Button) findViewById(R.id.ready_for_pickup_btn);
        completed = (Button) findViewById(R.id.completed_btn);
        refreshBtn = (Button) findViewById(R.id.refresh_btn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBusinessUI();
            }
        });
        logOutBtn = (Button) findViewById(R.id.log_out_manager_btn);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutBtnClicked();
            }
        });
        waitingToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToManagerOrderStatus();
            }
        });
        inProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToManagerOrderStatus();
            }
        });
        readyForPickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToManagerOrderStatus();
            }
        });
        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToManagerOrderStatus();
            }
        });

    }

    public void goToManagerOrderStatus(){
        Intent i = new Intent(this, ManagerOrderView.class);
        startActivity(i);
    }




}
