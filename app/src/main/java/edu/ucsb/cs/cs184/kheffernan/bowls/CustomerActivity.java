package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class CustomerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    @Override
    public void changeFragment(int id){
        switch(id){
            case 1:
                BowlToppingsFragment fragment1 = new BowlToppingsFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.customerOrderFrame, fragment1);
                ft.commit();
                //commit
            case 2:
                SaladToppingsFragment fragment2 = new SaladToppingsFragment();
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ft2.replace(R.id.customerOrderFrame, fragment2);
                ft2.commit();

            case 3:
                SoupToppingsFragment fragment3 = new SoupToppingsFragment();
                FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                ft3.replace(R.id.customerOrderFrame, fragment3);
                ft3.commit();

            case 4:
                BreadOptionsFragment fragment4 = new BreadOptionsFragment();
                FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                ft4.replace(R.id.customerOrderFrame, fragment4);
                ft4.commit();

            case 5:
                DrinkOptionsFragment fragment5 = new DrinkOptionsFragment();
                FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();
                ft5.replace(R.id.customerOrderFrame, fragment5);
                ft5.commit();

            case 6:
                PickItemFragment fragment6 = new PickItemFragment();
                FragmentTransaction ft6 = getSupportFragmentManager().beginTransaction();
                ft6.replace(R.id.customerOrderFrame, fragment6);
                ft6.commit();
        }

        //Cart is an activity so don't include here
    }



}
