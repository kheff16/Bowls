package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class PickItemFragment extends Fragment {
    ImageView bowlImage, saladImage, soupImage, breadImage, drinkImage, checkoutImage;
    private onFragmentInteractionListener mListener;

    public PickItemFragment(){

    }

    View userView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        userView = inflater.inflate(R.layout.pick_item_fragment, container, false);
        bowlImage = (ImageView) userView.findViewById(R.id.bowlImage);
        bowlImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View userView) {
                mListener.changeFragment(1);
            }
        });
        saladImage = (ImageView) userView.findViewById(R.id.saladImage);
        bowlImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View userView) {
                mListener.changeFragment(2);
            }
        });
        soupImage = (ImageView) userView.findViewById(R.id.soupImage);
        bowlImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View userView) {
                mListener.changeFragment(3);
            }
        });
        breadImage = (ImageView) userView.findViewById(R.id.breadImage);
        bowlImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View userView) {
                mListener.changeFragment(4);
            }
        });
        drinkImage = (ImageView) userView.findViewById(R.id.drinkImage);
        bowlImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View userView) {
                mListener.changeFragment(5);
            }
        });
        checkoutImage = (ImageView) userView.findViewById(R.id.checkoutImage);
        bowlImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View userView) {
                mListener.changeFragment(6);
            }
        });
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
