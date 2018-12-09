package edu.ucsb.cs.cs184.kheffernan.bowls.ManagerDashboardActivities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebaseCallback;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.Order;
import edu.ucsb.cs.cs184.kheffernan.bowls.R;

import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_STATUS_IN_PROGRESS;

public class InProgressOrdersFragment extends Fragment {

    private ListView inProgressOrdersListView;
    private BowlsFirebase bowlsFirebase;
    private SwipeRefreshLayout refreshLayout;

    private ArrayList<Order> Orders;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_in_progress_orders, container, false);
        inProgressOrdersListView = view.findViewById(R.id.list);

        bowlsFirebase = new BowlsFirebase();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshInProgress);


//        waitingOrdersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


    private void updateUIFromDatabase(){
        bowlsFirebase.getAllOrdersWithStatus(ORDER_STATUS_IN_PROGRESS, new BowlsFirebaseCallback<ArrayList<Order>>() {
            @Override
            public void callback(ArrayList<Order> data) {
                if(data != null) {
                    Orders = data;


                    final Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] allOrders = new String[Orders.size()];
                            for (int i=0; i < Orders.size(); i++)
                                allOrders[i] = Orders.get(i).getItems();

                            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(),
                                    R.layout.activity_list_view, allOrders);
                            inProgressOrdersListView.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }
}
