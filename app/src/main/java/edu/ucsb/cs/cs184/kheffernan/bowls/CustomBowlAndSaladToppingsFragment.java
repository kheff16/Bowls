package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CustomBowlAndSaladToppingsFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.custom_toppings_fragment, container, false);

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




}
