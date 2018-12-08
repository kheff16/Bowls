package edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BowlsFirebaseAuth {

    private FirebaseAuth bowlsAuth;

    public BowlsFirebaseAuth() {
        bowlsAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> signIn(String email, String password) {
        return bowlsAuth.signInWithEmailAndPassword(email, password);
    }

    public void signOut(){
        bowlsAuth.signOut();
    }

    public Task<AuthResult> register(String email, String password) {
        return bowlsAuth.createUserWithEmailAndPassword(email, password);
    }

    public void addAuthListener(FirebaseAuth.AuthStateListener listener) {
        bowlsAuth.addAuthStateListener(listener);
    }

    public void removeAuthListener(FirebaseAuth.AuthStateListener listener) {
        bowlsAuth.removeAuthStateListener(listener);
    }

    public FirebaseUser getCurrentUser() {
        return bowlsAuth.getCurrentUser();
    }


}
