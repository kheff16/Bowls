package edu.ucsb.cs.cs184.kheffernan.bowls.Utilities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.ucsb.cs.cs184.kheffernan.bowls.ManagerDashboardActivities.CompletedOrdersFragment;
import edu.ucsb.cs.cs184.kheffernan.bowls.ManagerDashboardActivities.InProgressOrdersFragment;
import edu.ucsb.cs.cs184.kheffernan.bowls.ManagerDashboardActivities.ReadyForPickupOrderFragment;
import edu.ucsb.cs.cs184.kheffernan.bowls.ManagerDashboardActivities.WaitingOrdersFragment;
import edu.ucsb.cs.cs184.kheffernan.bowls.ViewOrders.CurrentOrdersFragment;
import edu.ucsb.cs.cs184.kheffernan.bowls.ViewOrders.PastOrdersFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ManagerOrderListPagerAdapter extends FragmentStatePagerAdapter {

    public CompletedOrdersFragment completedOrdersFragment;
    public ReadyForPickupOrderFragment readyForPickupOrderFragment;
    public WaitingOrdersFragment waitingOrdersFragment;
    public InProgressOrdersFragment inProgressOrdersFragment;

    private int numTabs;

    public ManagerOrderListPagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WaitingOrdersFragment();

            case 1:
                return new InProgressOrdersFragment();

            case 2:
                return new ReadyForPickupOrderFragment();

            case 3:
                return new CompletedOrdersFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}

