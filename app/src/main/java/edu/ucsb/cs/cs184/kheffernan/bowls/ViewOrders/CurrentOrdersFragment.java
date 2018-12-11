package edu.ucsb.cs.cs184.kheffernan.bowls.ViewOrders;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseAuth;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseCallback;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.Order;
import edu.ucsb.cs.cs184.kheffernan.bowls.R;

public class CurrentOrdersFragment extends android.support.v4.app.Fragment  {

    private ListView pastOrdersListView;
    private BowlsFirebase bowlsFirebase;
    private BowlsFirebaseAuth bowlsFirebaseAuth;
    private SwipeRefreshLayout refreshLayout;

    private FirebaseUser currentUser;

    private ArrayList<Order> usersOrders;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_past_orders, container, false);
        pastOrdersListView = view.findViewById(R.id.list);

        bowlsFirebase = new BowlsFirebase();
        bowlsFirebaseAuth = new BowlsFirebaseAuth();
        currentUser = bowlsFirebaseAuth.getCurrentUser();

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshPastOrders);

//        pastOrdersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(getActivity().getApplicationContext(), SpotDetailActivity.class);
//                i.putExtra("orderID", usersOrders.get(position).getOrderID());
//                startActivityForResult(i, REQUEST_SPOT_DETAILS);
//            }
//        });

       updateUIFromDatabase();
        return view;
    }


    @Override
    public  void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setSwipeListener();
    }

        private void updateUIFromDatabase(){
        bowlsFirebase.getUsersCurrentOrders(currentUser.getUid(), new BowlsFirebaseCallback<ArrayList<Order>>() {
            @Override
            public void callback(ArrayList<Order> data) {
                if(data != null) {
                    usersOrders = data;

                    final Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] allOrders = new String[usersOrders.size()];
                            for (int i=0; i < usersOrders.size(); i++)
                            {
                                String[] mine;
                                mine = (usersOrders.get(i).getItems()).split(",");
                                allOrders[i] = usersOrders.get(i).getOrderStatus()+": ";


                                for(int j=0; j < mine.length; j++){
                                    String yours = mine[j].split("-")[0];

                                    if(yours.charAt(0) == '['){
                                        allOrders[i] += yours.substring(1,yours.length());
                                    }
                                    else if (yours.charAt(yours.length()-1) == ']'){
                                        allOrders[i] += ", ";
                                        allOrders[i] += yours.substring(0,yours.length()-1);
                                    }
                                    else{
                                        allOrders[i] += ", ";
                                        allOrders[i] += yours;
                                    }
                                }
                            }

                            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(),
                                    R.layout.activity_list_view, allOrders);
                            pastOrdersListView.setAdapter(adapter);
                        }
                    });
                }
            }
        });
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
