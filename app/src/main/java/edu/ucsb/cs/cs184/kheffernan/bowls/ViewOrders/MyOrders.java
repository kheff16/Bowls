package edu.ucsb.cs.cs184.kheffernan.bowls.ViewOrders;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseAuth;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.Order;
import edu.ucsb.cs.cs184.kheffernan.bowls.R;
import edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.OrderListPagerAdapter;

import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_DELETED;

public class MyOrders extends AppCompatActivity {

    private final String LOG_TAG = "MyOrdersActivity";
    //UI Vars
    private BowlsUser user;
    private BowlsFirebase scFirebase;
    private BowlsFirebaseAuth scFirebaseAuth;
    private FirebaseUser currentUser;

    private ListView ownedOrdersLV;

    private ArrayList<Order> usersOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_orders);
        getSupportActionBar().setElevation(0);



        scFirebase = new BowlsFirebase();
        scFirebaseAuth = new BowlsFirebaseAuth();
        currentUser = scFirebaseAuth.getCurrentUser();

        TabLayout tabLayout = findViewById(R.id.order_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Past Orders"));
        tabLayout.addTab(tabLayout.newTab().setText("In Progress"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.order_view_pager);

        final OrderListPagerAdapter adapter = new OrderListPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == ORDER_DELETED) {
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0,0);
        }
    }

}