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
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseAuth;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;


public class ProfilePage extends AppCompatActivity {

//        private BowlsUser user;
//        private TextView userNameTextView;
//        private TextView userLocationTextView;
//        private BowlsFirebase scFirebase;
//        private BowlsFirebaseAuth scFirebaseAuth;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_profile);
//
//            // Initialize interface objects
//            scFirebase = new SCFirebase();
//            scFirebaseAuth = new SCFirebaseAuth();
//
//            // Initialize UI objects
//            userNameTextView = findViewById(R.id.user_name_view);
//            userLocationTextView = findViewById(R.id.user_location_view);
//
//            profileImage = findViewById(R.id.profileImage);
//            profileImageProgress = findViewById(R.id.profileImageProgress);
//
//            if(scFirebaseAuth.getCurrentUser() != null) {
//                final String currentUserID = scFirebaseAuth.getCurrentUser().getUid();
//
//                final ProgressDialog dialog = ProgressDialog.show(
//                        ProfilePage.this,
//                        "",
//                        "Fetching profile details...",
//                        true
//                );
//
//                scFirebase.getSCUser(currentUserID, new SCFirebaseCallback<SpotCheckUser>() {
//                    @Override
//                    public void callback(SpotCheckUser data) {
//                        dialog.dismiss();
//                        if(data != null) {
//                            user = data;
//
//                            Handler mainHandler = new Handler(Looper.getMainLooper());
//                            mainHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    userLocationTextView.setText(user.getLocation());
//                                    userNameTextView.setText(user.getFullname());
//
//                                    if (user.getImageUrl() != null) {
//                                        Uri profileImageUri = Uri.parse(user.getImageUrl());
//                                        Glide.with(ProfilePage.this).load(profileImageUri).apply(new RequestOptions()
//                                                .fitCenter()).listener(new RequestListener<Drawable>() {
//                                            @Override
//                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                                profileImage.setImageResource(R.mipmap.spot_marker_icon);
//                                                profileImageProgress.setVisibility(View.GONE);
//                                                return false;
//                                            }
//
//                                            @Override
//                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                                profileImageProgress.setVisibility(View.GONE);
//                                                return false;
//                                            }
//                                        }).into(profileImage);
//                                    } else {
//                                        profileImage.setImageResource(R.mipmap.spot_marker_icon);
//                                        profileImageProgress.setVisibility(View.GONE);
//                                    }
//                                }
//
//
//
//                            });
//                        }
//                    }
//                });
//            }
//        }
//
//        @Override
//        public void onActivityResult(int requestCode, int resultCode, Intent data) {
//            if((requestCode == REQUEST_EDIT_PROFILE) && (resultCode == PROFILE_EDITED)) {
//                // Refresh info
//                finish();
//                overridePendingTransition(0, 0);
//                startActivity(getIntent());
//                overridePendingTransition(0,0);
//            }
//        }
//
//        public void editProfile(View view){
//            if(user != null) {
//                Intent intent = new Intent(this, EditProfile.class);
//                intent.putExtra("currentSCUserID", user.getUserID());
//                startActivityForResult(intent, REQUEST_EDIT_PROFILE);
//            }
//        }
//    }

}
