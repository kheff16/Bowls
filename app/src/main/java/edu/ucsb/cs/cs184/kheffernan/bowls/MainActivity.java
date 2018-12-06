package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.ucsb.cs.cs184.kheffernan.bowls.UserAuthentication.LoginActivity;
import edu.ucsb.cs.cs184.kheffernan.bowls.UserAuthentication.RegisterActivity;
import edu.ucsb.cs.cs184.kheffernan.bowls.AccessBowls;

public class MainActivity  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(android.R.style.Theme_Material_NoActionBar_Fullscreen);
        setContentView(R.layout.activity_main);

    }

    //Intent register = new Intent(this, RegisterActivity.class);
    public void goToRegisterActivity(View view) {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }

    public void goToLoginActivity(View view) {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }
}
