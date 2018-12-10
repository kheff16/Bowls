package edu.ucsb.cs.cs184.kheffernan.bowls.ManagerDashboardActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseCallback;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.Order;
import edu.ucsb.cs.cs184.kheffernan.bowls.R;

import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_STATUS_COMPLETED;
import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_STATUS_CREATED;
import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_STATUS_IN_PROGRESS;
import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_STATUS_READY_FOR_PICK_UP;

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


        bowlsFirebase = new BowlsFirebase();
        orderOwner = new BowlsUser();

        items = (TextView) findViewById(R.id.order_detail_items);
        customerID = (TextView) findViewById(R.id.order_detail_customerID);
        orderIDTextView = (TextView) findViewById(R.id.order_detail_orderID);

        // Get intent and extras
        Intent intent = getIntent();
        orderID = intent.getStringExtra("orderID");

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

        moveToNextBtn = (Button) findViewById(R.id.move_to_next_stage_button);
        moveToNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoveNextBtnClicked();
            }
        });


    }

    private void onMoveNextBtnClicked(){

        bowlsFirebase.getOrder(orderID, new BowlsFirebaseCallback<Order>() {
            @Override
            public void callback(Order data) {
                if (data!=null){
                    order=data;
                    switch (order.getOrderStatus()){
                        case ORDER_STATUS_CREATED:{
                            order.setOrderStatus(ORDER_STATUS_IN_PROGRESS);
                            Log.d("OrderDetailActivity", "setting order status from created to in prog");
                            bowlsFirebase.updateorder(orderID, order);
                            break;
                        }
                        case ORDER_STATUS_IN_PROGRESS:{
                            order.setOrderStatus(ORDER_STATUS_READY_FOR_PICK_UP);
                            bowlsFirebase.updateorder(orderID, order);
                            Log.d("OrderDetailActivity", "setting order status from in prog to ready for pickup");

                            break;
                        }
                        case ORDER_STATUS_READY_FOR_PICK_UP:{
                            order.setOrderStatus(ORDER_STATUS_COMPLETED);
                            bowlsFirebase.updateorder(orderID, order);
                            Log.d("OrderDetailActivity", "setting order status from ready for pickup to in completed");

                            break;
                        }
                        default:{
                            Log.d("OrderDetailActivity", "default case, order is complete");
                            break;
                        }
                    }
                }
            }
        });
    }



}


