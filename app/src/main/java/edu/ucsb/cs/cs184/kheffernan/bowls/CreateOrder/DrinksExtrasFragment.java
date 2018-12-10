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

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface.BowlsFirebase;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.Order;
import edu.ucsb.cs.cs184.kheffernan.bowls.R;

public class DrinksExtrasFragment extends Fragment {
    private ListView drinksExtrasListView;
    private BowlsFirebase bowlsFirebase;
    private SwipeRefreshLayout refreshLayout;
    public ArrayList<Long> selected;


    private ArrayList<Order> Orders;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.drinks_extras_fragment, container, false);
        drinksExtrasListView = view.findViewById(R.id.list);

//        bowlsFirebase = new BowlsFirebase();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshDrinksExtras);
        selected = new ArrayList<Long>();



//        customBowlsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(getActivity().getApplicationContext(), OrderDetailActivity.class);
//                i.putExtra("orderID", Orders.get(position).getOrderID());
//                startActivityForResult(i, REQUEST_ORDER_DETAILS);
//            }
//        });

        updateUIFromDatabase();

        drinksExtrasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        String[] allCustomBowls = new String[11];
        allCustomBowls[0]="Sprite";
        allCustomBowls[1]="Coke";
        allCustomBowls[2]="Lemonade";
        allCustomBowls[3]="Water";
        allCustomBowls[4] ="BBQ Sauce";
        allCustomBowls[5] ="Pesto Sauce";
        allCustomBowls[6] ="Marinara Sauce";
        allCustomBowls[7] ="Homemade Hummus";
        allCustomBowls[8] ="Avocado";
        allCustomBowls[9] ="Egg";
        allCustomBowls[10] ="Bacon";

        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.activity_list_view,allCustomBowls);
        drinksExtrasListView.setAdapter(adapter);

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
