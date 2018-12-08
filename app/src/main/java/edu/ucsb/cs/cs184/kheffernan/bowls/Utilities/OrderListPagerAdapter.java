package edu.ucsb.cs.cs184.kheffernan.bowls.Utilities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.ucsb.cs.cs184.kheffernan.bowls.ViewOrders.CurrentOrdersFragment;
import edu.ucsb.cs.cs184.kheffernan.bowls.ViewOrders.PastOrdersFragment;

public class OrderListPagerAdapter extends FragmentStatePagerAdapter {

    public PastOrdersFragment pastOrdersFragment;
    public CurrentOrdersFragment currentOrdersFragment;

    private int numTabs;

    public OrderListPagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PastOrdersFragment();

            case 1:
                return new CurrentOrdersFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}

