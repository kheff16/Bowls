package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PickItemFragment extends Fragment {
    ImageView bowlImage, saladImage, soupImage, breadImage, drinkImage, checkoutImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pick_item_fragment, container, false);
        bowlImage = (ImageView) rootView.findViewById(R.id.bowlImage);
        bowlImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        saladImage = (ImageView) rootView.findViewById(R.id.saladImage);
        bowlImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        soupImage = (ImageView) rootView.findViewById(R.id.soupImage);
        bowlImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        breadImage = (ImageView) rootView.findViewById(R.id.breadImage);
        bowlImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        drinkImage = (ImageView) rootView.findViewById(R.id.drinkImage);
        bowlImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        checkoutImage = (ImageView) rootView.findViewById(R.id.checkoutImage);
        bowlImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });


    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        setContentView(R.layout.main);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(HelloGridView.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        */
    }
    mImageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // do stuff
        }

    });

}
