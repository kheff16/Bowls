package edu.ucsb.cs.cs184.kheffernan.bowls.CreateOrder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseAuth;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.MenuItem;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.Order;
import edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.CreateOrderListPagerAdapter;
import edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.ManagerOrderListPagerAdapter;
import edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.MenuItemAdapter;

import edu.ucsb.cs.cs184.kheffernan.bowls.R;

import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_DELETED;

public class CreateOrderActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_items_tab_layout);
        getSupportActionBar().setElevation(0);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_create_order);


        View view =getSupportActionBar().getCustomView();

        ImageButton addToCartBtn;


        TabLayout tabLayout = findViewById(R.id.menu_items_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Custom Bowls"));
        tabLayout.addTab(tabLayout.newTab().setText("Bowls/Salads"));
        tabLayout.addTab(tabLayout.newTab().setText("Soups"));
        tabLayout.addTab(tabLayout.newTab().setText("Drinks and Extras"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.menu_items_view_pager);

        final CreateOrderListPagerAdapter adapter = new CreateOrderListPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

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

        addToCartBtn = (ImageButton) view.findViewById(R.id.action_bar_back);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "clicked!",
                        Toast.LENGTH_SHORT);

                toast.show();

//
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

//public class CreateOrderActivity extends AppCompatActivity {
//
//    //TAG
//    private final String LOG_TAG = "CreateOrderActivity";
//
//    //UI Vars
//    private ListView listView;
//    private Button submitOrderButton;
//
//    //Firebase Vars
//    private BowlsFirebaseAuth bowlsAuth;
//    private BowlsFirebase bowlsFirebase;
//
//    //Local Object Vars
//    private BowlsUser bowlsUser;
//
//
//    private HashSet<String> menuItemHashSet = new HashSet<>();
//    private List<MenuItem> menuItemLinkedList = new LinkedList<>();
//    private  LinkedList<String> selectdmenuItem = new LinkedList<>();
//    private MenuItemAdapter adapter;
//
//    @Override
//    protected void onCreate(final Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_order);
//
//        listView = (ListView)findViewById(R.id.list_view);
//        submitOrderButton = (Button)findViewById(R.id.submit_order_btn);
//
//        bowlsAuth = new BowlsFirebaseAuth();
//        bowlsFirebase = new BowlsFirebase();
//
//
//        for (int i=0; i < 10; i++) {
//            menuItemHashSet.add("menuItem" +" "+ String.valueOf(i + 1));
//        }
//
//        getData();
//
//        submitOrderButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectdmenuItem = adapter.getSelectedMenuItem();
//                StringBuilder str = new StringBuilder();
//                if(selectdmenuItem.size() != 0){
//                    Iterator iterator = selectdmenuItem.iterator();
//                    while (iterator.hasNext()){
//                        str.append(iterator.next().toString());
//                    }
//
//                    Toast.makeText(CreateOrderActivity.this, str, Toast.LENGTH_SHORT).show();
//                    FirebaseUser user = bowlsAuth.getCurrentUser();
////                    final ProgressDialog dialog = ProgressDialog.show(CreateOrderActivity.this, "",
////                            "Creating order...", true, true);
//                    showOrderConfirmedDialog();
//
//
//                    Order newOrder = new Order(user.getUid(), user.getUid(), str.toString(), 4.20);
//
//                    bowlsFirebase.createNewOrder(newOrder);
//
//
//                }
//            }
//        });
//    }
//
//    public void getData(){
//        for(String s : menuItemHashSet){
//            MenuItem menuItem = new MenuItem();
//            menuItem.setName(s);
//            menuItemLinkedList.add(menuItem);
//        }
//
//        adapter = new MenuItemAdapter(this, menuItemLinkedList);
//        listView.setAdapter(adapter);
//
//    }
//
//    private void showOrderConfirmedDialog(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Order Confirmed")
//                .setMessage(("Thanks for placing an order! We'll let you know when it's ready for pickup."))
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        //go to homepage
//                        finish();
//                    }
//                })
//                .show();
//    }
//
//
//
//
//
//}
