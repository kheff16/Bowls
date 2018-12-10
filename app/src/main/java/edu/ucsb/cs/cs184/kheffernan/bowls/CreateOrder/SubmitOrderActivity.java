package edu.ucsb.cs.cs184.kheffernan.bowls.CreateOrder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseAuth;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseCallback;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.Order;
import edu.ucsb.cs.cs184.kheffernan.bowls.R;

public class SubmitOrderActivity extends AppCompatActivity {
    private final String LOG_TAG = "SubmitOrderActivity";

    //UI Vars
    private TextView textView;
    private Button submitOrderButton;
    private Button clearCartBtn;
    TextView costText;

    //Local user vars
    ArrayList<String> orderItems = new ArrayList<String>();
    ArrayList<Double> orderPrices = new ArrayList<Double>();
    Map<String,String> orderMap = new HashMap<String, String>();
    String userID;
    Double cost=0.0;

    //Firebase Vars
    private BowlsFirebase bowlsFirebase;
    private BowlsUser bowlsUser;
    private BowlsFirebaseAuth bowlsAuth;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_order_activity);

        submitOrderButton = (Button) findViewById(R.id.submit_order_btn);

        bowlsFirebase = new BowlsFirebase();
        bowlsAuth = new BowlsFirebaseAuth();
        // Get current, logged in user
        final FirebaseUser currentUser = bowlsAuth.getCurrentUser();
        //get the current user from firebase
        bowlsFirebase.getBowlsUser(bowlsAuth.getCurrentUser().getUid(), new BowlsFirebaseCallback<BowlsUser>() {
            @Override
            public void callback(BowlsUser data) {
                if (data!=null){
                    bowlsUser = data;
                    Log.d(LOG_TAG, "bowls user"+bowlsUser.getFullname());
                    orderMap = data.getCurrentOrdersCart();
                    userID = data.getUserID();
                    orderMap.remove("No items");

                    for (String item: orderMap.keySet()){
                        orderItems.add(item+"\n");
                    }

                    for (String price: orderMap.values()){
                        Double convertedPrice = Double.parseDouble(price.substring(1));
                        orderPrices.add(convertedPrice);
                    }


                    costText = (TextView) findViewById(R.id.textViewCost);
                    for (Double price: orderPrices){
                        cost+=price;
                    }
                    costText.setText(cost.toString().substring(0,cost.toString().indexOf(".")+2));


                    textView = (TextView) findViewById(R.id.submit_order_text_view);
                    textView.setTextSize(24);
                    textView.setText(orderItems.toString());


                    submitOrderButton = (Button) findViewById(R.id.submit_order_btn);
                    submitOrderButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Order newOrder = new Order(bowlsUser.getUserID(), bowlsUser.getUserID(), orderItems.toString(), cost);
                            bowlsFirebase.createNewOrder(newOrder);
                            showOrderSubmittedDialog();
                            //clear cart after order submitted
                            HashMap<String, String> emptyCart = new HashMap<String, String>();
                            emptyCart.put("No items","$0.00");
                            bowlsUser.setCurrentOrdersCart(emptyCart);
                            bowlsFirebase.uploadUser(bowlsUser);
                            showCartClearedDialog();

                        }

                    });

                    clearCartBtn = (Button) findViewById(R.id.button_clear_cart);
                    clearCartBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HashMap<String, String> emptyCart = new HashMap<String, String>();
                            emptyCart.put("No items","$0.00");
                            bowlsUser.setCurrentOrdersCart(emptyCart);
                            bowlsFirebase.uploadUser(bowlsUser);
                            showCartClearedDialog();
                        }
                    });



                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "User not found!!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });





    }

    private void showOrderSubmittedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order Confirmed")
                .setMessage(("Thanks for placing an order! Check back soon on the status of your order"))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //go to homepage
                        finish();
                    }
                })
                .show();
    }

    private void showCartClearedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cart Cleared")
                .setMessage(("Your cart is empty."))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //go to homepage
                        finish();
                    }
                })
                .show();
    }


}
