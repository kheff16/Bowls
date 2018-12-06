package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseConstants.USER_PATH;

public class BowlsFirebase {

    private DatabaseReference bowlsDatabase;
    private StorageReference bowlsStorage;

    public BowlsFirebase() {
        bowlsDatabase = FirebaseDatabase.getInstance().getReference();
        bowlsStorage = FirebaseStorage.getInstance().getReference();
    }


    // Create or modify user object on database
    public void uploadUser(UserObject user) {
        bowlsDatabase.child(USER_PATH).child(user.getUserID()).setValue(user);
    }

    // Get a user from the database
    public void getBowlsUser(final String userID,
                          @NonNull final BowlsFirebaseCallback<UserObject> finishedCallback) {

        DatabaseReference myRef = bowlsDatabase.child(USER_PATH);

        myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserObject user = dataSnapshot.getValue(UserObject.class);

                if(user != null) {
                    user.setUserID(userID);
                }

                finishedCallback.callback(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }
}
