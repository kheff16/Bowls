package edu.ucsb.cs.cs184.kheffernan.bowls.ViewOrders;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseAuth;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseCallback;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.Order;
import edu.ucsb.cs.cs184.kheffernan.bowls.R;

public class PastOrdersFragment extends android.support.v4.app.Fragment  {


    private ListView pastOrdersListView;
    private BowlsFirebase bowlsFirebase;
    private BowlsFirebaseAuth bowlsFirebaseAuth;

    private FirebaseUser currentUser;

    private ArrayList<Order> usersOrders;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_past_orders, container, false);
        pastOrdersListView = view.findViewById(R.id.list);

        bowlsFirebase = new BowlsFirebase();
        bowlsFirebaseAuth = new BowlsFirebaseAuth();
        currentUser = bowlsFirebaseAuth.getCurrentUser();

//        pastOrdersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(getActivity().getApplicationContext(), SpotDetailActivity.class);
//                i.putExtra("orderID", usersOrders.get(position).getOrderID());
//                startActivityForResult(i, REQUEST_SPOT_DETAILS);
//            }
//        });

        bowlsFirebase.getUsersOrders(currentUser.getUid(), new BowlsFirebaseCallback<ArrayList<Order>>() {
            @Override
            public void callback(ArrayList<Order> data) {
                if(data != null) {
                    usersOrders = data;


                    final Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] allSpots = new String[usersOrders.size()];
                            for (int i=0; i < usersOrders.size(); i++)
                                allSpots[i] = usersOrders.get(i).getOrderStatus();

                            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(),
                                    R.layout.activity_list_view, allSpots);
                            pastOrdersListView.setAdapter(adapter);
                        }
                    });
                }
            }
        });

        return view;
    }
}
