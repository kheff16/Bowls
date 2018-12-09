package edu.ucsb.cs.cs184.kheffernan.bowls.ManagerDashboardActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseCallback;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.Order;
import edu.ucsb.cs.cs184.kheffernan.bowls.R;

public class OrderDetailActivity extends AppCompatActivity {

    private BowlsFirebase bowlsFirebase;
    private BowlsUser orderOwner;
    private Order order;

    //UI Vars
    private Button moveToNextBtn;
    private TextView items;
    private TextView orderIDTextView;
    private TextView customerID;

    //Order values
    private String ownerID;
    private String status;
    private String itemList;
    private String orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        moveToNextBtn = (Button) findViewById(R.id.move_to_next_stage_btn);

        bowlsFirebase = new BowlsFirebase();
        orderOwner = new BowlsUser();

        items = (TextView) findViewById(R.id.order_detail_items);
        customerID = (TextView) findViewById(R.id.order_detail_customerID);
        orderIDTextView = (TextView) findViewById(R.id.order_detail_orderID);

        // Get intent and extras
        Intent intent = getIntent();
        String orderID = intent.getStringExtra("orderID");

        //get order using info from intent
        bowlsFirebase.getOrder(orderID, new BowlsFirebaseCallback<Order>() {
            @Override
            public void callback(Order data) {
                if (data!=null) {
                    order = data;
                    items.setText(data.getItems());
                    customerID.setText(data.getOwnerID());
                    orderIDTextView.setText(data.getOrderID());


                }
            }
        });


    }


}


