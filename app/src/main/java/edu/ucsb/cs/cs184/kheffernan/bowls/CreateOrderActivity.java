package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
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
import edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.MenuItemAdapter;

public class CreateOrderActivity extends AppCompatActivity {

    //TAG
    private final String LOG_TAG = "CreateOrderActivity";

    //UI Vars
    private ListView listView;
    private Button submitOrderButton;

    //Firebase Vars
    private BowlsFirebaseAuth bowlsAuth;
    private BowlsFirebase bowlsFirebase;

    //Local Object Vars
    private BowlsUser bowlsUser;

    private HashSet<String> menuItemHashSet = new HashSet<>();
    private List<MenuItem> menuItemLinkedList = new LinkedList<>();
    private  LinkedList<String> selectdmenuItem = new LinkedList<>();
    private MenuItemAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_order_menu_fragment);
        //setContentView(R.layout.activity_create_order);
        CardView custombowl = (CardView) findViewById(R.id.cardviewcustombowl); // creating a CardView and assigning a value.

        custombowl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomBowlAndSaladToppingsFragment fragment1 = new CustomBowlAndSaladToppingsFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.customerOrderFrame, fragment1);
                ft.commit();
            }
        });

        CardView bowl1 = (CardView) findViewById(R.id.cardviewbowl1); // creating a CardView and assigning a value.

        bowl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        CardView bowl2 = (CardView) findViewById(R.id.cardviewbowl2); // creating a CardView and assigning a value.

        bowl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        CardView bowl3= (CardView) findViewById(R.id.cardviewbowl3); // creating a CardView and assigning a value.

        bowl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        CardView bowl4= (CardView) findViewById(R.id.cardviewbowl4); // creating a CardView and assigning a value.

        bowl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        CardView bowl5 = (CardView) findViewById(R.id.cardviewbowl5); // creating a CardView and assigning a value.

        bowl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        CardView soup1 = (CardView) findViewById(R.id.cardviewsoup1); // creating a CardView and assigning a value.

        soup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        CardView soup2 = (CardView) findViewById(R.id.cardviewsoup2); // creating a CardView and assigning a value.

        soup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        CardView soup3 = (CardView) findViewById(R.id.cardviewsoup3); // creating a CardView and assigning a value.

        soup3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        CardView salad1= (CardView) findViewById(R.id.cardviewsalad1); // creating a CardView and assigning a value.

        salad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        CardView extras1 = (CardView) findViewById(R.id.cardviewextras1); // creating a CardView and assigning a value.

        extras1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        CardView drink1 = (CardView) findViewById(R.id.cardviewdrink1); // creating a CardView and assigning a value.

        drink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        CardView drink2 = (CardView) findViewById(R.id.cardviewdrink2); // creating a CardView and assigning a value.

        drink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        CardView drink3= (CardView) findViewById(R.id.cardviewdrink3); // creating a CardView and assigning a value.

        drink3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        CardView drink4 = (CardView) findViewById(R.id.cardviewdrink4; // creating a CardView and assigning a value.

        drink4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        Button addtocart= (Button) findViewById(R.id.addtocart); // creating a CardView and assigning a value.

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });

        Button checkout= (Button) findViewById(R.id.checkout); // creating a CardView and assigning a value.

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });















        listView = (ListView)findViewById(R.id.list_view);
        submitOrderButton = (Button)findViewById(R.id.submit_order_btn);

        bowlsAuth = new BowlsFirebaseAuth();
        bowlsFirebase = new BowlsFirebase();

        for (int i=0; i < 10; i++) {
            menuItemHashSet.add("menuItem" +" "+ String.valueOf(i + 1));
        }

        getData();

        submitOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectdmenuItem = adapter.getSelectedMenuItem();
                StringBuilder str = new StringBuilder();
                if(selectdmenuItem.size() != 0){
                    Iterator iterator = selectdmenuItem.iterator();
                    while (iterator.hasNext()){
                        str.append(iterator.next().toString());
                    }

                    Toast.makeText(CreateOrderActivity.this, str, Toast.LENGTH_SHORT).show();
                    FirebaseUser user = bowlsAuth.getCurrentUser();
//                    final ProgressDialog dialog = ProgressDialog.show(CreateOrderActivity.this, "",
//                            "Creating order...", true, true);
                    showOrderConfirmedDialog();


                    Order newOrder = new Order(user.getUid(), user.getUid(), str.toString(), 4.20);

                    bowlsFirebase.createNewOrder(newOrder);


                }
            }
        });
    }

    public void getData(){
        for(String s : menuItemHashSet){
            MenuItem menuItem = new MenuItem();
            menuItem.setName(s);
            menuItemLinkedList.add(menuItem);
        }

        adapter = new MenuItemAdapter(this, menuItemLinkedList);
        listView.setAdapter(adapter);

    }

    private void showOrderConfirmedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order Confirmed")
                .setMessage(("Thanks for placing an order! We'll let you know when it's ready for pickup."))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //go to homepage
                        finish();
                    }
                })
                .show();
    }





}
