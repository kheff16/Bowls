package edu.ucsb.cs.cs184.kheffernan.bowls.CreateOrder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.Order;
import edu.ucsb.cs.cs184.kheffernan.bowls.R;

public class BowlsSaladsFragment extends Fragment {
    private ListView bowlsSaldsListView;
    private BowlsFirebase bowlsFirebase;
    private SwipeRefreshLayout refreshLayout;


    private ArrayList<Order> Orders;
    public ArrayList<Long> selected;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.bowls_salads_fragment, container, false);
        bowlsSaldsListView = view.findViewById(R.id.list);
        selected = new ArrayList<Long>();

//        bowlsFirebase = new BowlsFirebase();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshBowlsSalads);


//        customBowlsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(getActivity().getApplicationContext(), OrderDetailActivity.class);
//                i.putExtra("orderID", Orders.get(position).getOrderID());
//                startActivityForResult(i, REQUEST_ORDER_DETAILS);
//            }
//        });

        updateUIFromDatabase();

        bowlsSaldsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                if (selected.contains(id)){
                    view.setBackgroundColor(Color.DKGRAY);
                    selected.remove(id);
                }else {
                    view.setBackgroundColor(Color.LTGRAY);
                    selected.add(id);
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
        String[] allCustomBowls = new String[2];
        allCustomBowls[0]="bread";
        allCustomBowls[1] = "mushrooms";

        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.activity_list_view,allCustomBowls);
        bowlsSaldsListView.setAdapter(adapter);

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
