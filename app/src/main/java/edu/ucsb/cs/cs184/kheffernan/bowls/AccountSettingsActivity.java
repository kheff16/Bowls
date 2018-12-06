package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import static android.Manifest.permission.READ_CONTACTS;

public class AccountSettingsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private BowlsFirebase mBowlsFirebase;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private UserObject user;

    private EditText mFullNameView;
    private EditText mPhoneNumberView;
    private Switch mAccountTypeSwitch;
    private View mProgressView;
    private View mLoginFormView;
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mBowlsFirebase = new BowlsFirebase();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //TODO: change this to be if user is signed in and account is setup
                    //then you would go to the ordering page or manager page
                    //otherwise, you need to go stay here and input the forms
                    goToOrderingActivity();
                }
            }
        };


        mFullNameView = findViewById(R.id.full_name);



        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

}

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


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {

        // Reset errors.
        mFullNameView.setError(null);
        mPhoneNumberView.setError(null);
        mAccountTypeSwitch.setError(null);


        // Store values at the time of the register attempt.
        String fullName = mFullNameView.getText().toString();
        String phoneNumber = mPhoneNumberView.getText().toString();
        Boolean accountTypeIsManager = mAccountTypeSwitch.isChecked();

        boolean cancel = false;
        View focusView = null;

        // Check full name field
        if (TextUtils.isEmpty(fullName)) {
            mFullNameView.setError(getString(R.string.error_field_required));
            focusView = mFullNameView;
            cancel = true;
        }
        if(!isFullNameValid(fullName)) {
            mFullNameView.setError("Error: invalid full name");
            focusView = mFullNameView;
            cancel = true;
        }

        // Check for a valid phone number, if the user entered one.
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberView.setError(getString(R.string.error_field_required));
            focusView = mPhoneNumberView;
            cancel = true;
        }



        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            performRegister(fullName, phoneNumber, accountTypeIsManager.toString());
        }
    }


    //Adds the updated account information to the firebase database
    private void performRegister(final String fullName, String phoneNumber, String accountTypeIsManager) {

        mBowlsFirebase.uploadUser();


    }
    //TODO: fix perform register to be able to upload users
//    private void performRegister(final String fullName, String phoneNumber, String accountTypeIsManager) {
//
//        mAuth.register(email, password)
//                .addOnCompleteListener(AccountSettingActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        showProgress(false);
//
//                        if (task.isSuccessful()) {
//                            // User successfully registered
//                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//                            if (currentUser != null) {
//                                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
//                                        .setDisplayName(fullName).build();
//                                currentUser.updateProfile(profileUpdate);
//
//                                SpotCheckUser newRegisteredUser = new SpotCheckUser(
//                                        currentUser.getUid(),
//                                        currentUser.getEmail(),
//                                        fullName,
//                                        ""
//                                );
//
//                                scFirebase.uploadUser(newRegisteredUser);
//                            }
//
//                        }
//
//                    }
//                });
//    }

    private boolean isFullNameValid(String fullName) {
        return fullName.contains(" ") && fullName.length() > 3;
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), RegisterActivity.ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(RegisterActivity.ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegisterActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }


    public void goToOrderingActivity(){
        //TODO: change the activity to be the ordering activity
            Intent i = new Intent(this, AccountSettingsActivity.class);
            startActivity(i);
        }

}
