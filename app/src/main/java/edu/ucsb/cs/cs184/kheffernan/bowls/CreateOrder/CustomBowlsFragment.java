package edu.ucsb.cs.cs184.kheffernan.bowls.CreateOrder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseAuth;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseCallback;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.Order;
import edu.ucsb.cs.cs184.kheffernan.bowls.R;

public class CustomBowlsFragment extends Fragment {
    private ListView customBowlsListView;
    private BowlsFirebase bowlsFirebase;
    private BowlsUser bowlsUser;
    private BowlsFirebaseAuth bowlsAuth;
    private SwipeRefreshLayout refreshLayout;

    private ImageButton addToCartBtn;
    private ImageButton checkoutBtn;


    private ArrayList<Order> Orders;
    public ArrayList<Long> selected;
    String[] allCustomBowls = new String[4];



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.custom_bowls_fragment, container, false);

        customBowlsListView = view.findViewById(R.id.list);
        selected = new ArrayList<Long>();


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
                }
            }
        });



        allCustomBowls[0]="Custom Bread Bowl - $8.99";
        allCustomBowls[1] = "Custom Salad Bowl - $7.99";
        allCustomBowls[2] = "Add Items to Cart";
        allCustomBowls[3] = "Submit My Order";

//        bowlsFirebase = new BowlsFirebase();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshCustomBowls);


        updateUIFromDatabase();

        customBowlsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                if(id==allCustomBowls.length-1){
                    //submit order
                    if (!selected.isEmpty()){
                        Log.d("CustomBowls","selected is: "+selected.toString());
                        Log.d("CustomBowls","bowls uer is: "+bowlsUser.toString());
                    //add the items from 'selected' to the BowlUser's cart as long as there are some items inside selected
                        for (long item: selected) {
                          String itemAndCost = allCustomBowls[(int)item];
                          String title = itemAndCost.substring(0, itemAndCost.indexOf("$"));
                          String cost = itemAndCost.substring(itemAndCost.indexOf("$"));
                         bowlsUser.addOrderItemToUser(title,cost);
                            Log.d("CustomBowls","bowls user order cart: "+bowlsUser.getCurrentOrdersCart().toString());

                            bowlsFirebase.uploadUser(bowlsUser);
                            Intent intent = new Intent(getActivity(), SubmitOrderActivity.class);
                            startActivity(intent);

                     }
                    }
                    Toast toast = Toast.makeText(getActivity(),
                            "Submitting!",
                            Toast.LENGTH_SHORT);
                    toast.show();

                }
                else if (id ==allCustomBowls.length-2){
                    //add to cart
                    Toast toast = Toast.makeText(getActivity(),
                            "Adding to Cart!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    for (long item: selected) {
                        String itemAndCost = allCustomBowls[(int) item];
                        String title = itemAndCost.substring(0, itemAndCost.indexOf("$"));
                        String cost = itemAndCost.substring(itemAndCost.indexOf("$"));
                        bowlsUser.addOrderItemToUser(title, cost);
                        Log.d("CustomBowls", "bowls user order cart: " + bowlsUser.getCurrentOrdersCart().toString());

                        bowlsFirebase.uploadUser(bowlsUser);
                    }

                    //clear all items in selected and change color back to white
                }
                else {
                    //menu item selected
                    if (!selected.contains(id)) {
                        view.setBackgroundColor(Color.DKGRAY);
                        selected.add(id);
                        Log.d("CustomBowls","selected to string after clicking item:"+selected.toString());


                    } else {
                        view.setBackgroundColor(Color.WHITE);
                        selected.remove(id);
                        Log.d("CustomBowls","selected to string after clicking item:"+selected.toString());

                    }
                }
            }
        });



        return view;
    }

    @Override
    public  void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setSwipeListener();
    }

    private void updateUIFromDatabase(){
        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.activity_list_view,allCustomBowls);
        customBowlsListView.setAdapter(adapter);

    }

    private void setSwipeListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateUIFromDatabase();

                Toast toast = Toast.makeText(getActivity(),
                        "Refreshing!",
                        Toast.LENGTH_SHORT);

                toast.show();

                refreshLayout.setRefreshing(false);
            }
        });
    }
}
