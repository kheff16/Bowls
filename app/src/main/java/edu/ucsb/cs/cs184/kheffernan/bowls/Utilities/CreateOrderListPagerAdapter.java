package edu.ucsb.cs.cs184.kheffernan.bowls.Utilities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.ucsb.cs.cs184.kheffernan.bowls.CreateOrder.BowlsSaladsFragment;
import edu.ucsb.cs.cs184.kheffernan.bowls.CreateOrder.CustomBowlsFragment;
import edu.ucsb.cs.cs184.kheffernan.bowls.CreateOrder.DrinksExtrasFragment;
import edu.ucsb.cs.cs184.kheffernan.bowls.CreateOrder.SoupFragment;
import edu.ucsb.cs.cs184.kheffernan.bowls.ManagerDashboardActivities.CompletedOrdersFragment;
import edu.ucsb.cs.cs184.kheffernan.bowls.ManagerDashboardActivities.InProgressOrdersFragment;
import edu.ucsb.cs.cs184.kheffernan.bowls.ManagerDashboardActivities.ReadyForPickupOrderFragment;
import edu.ucsb.cs.cs184.kheffernan.bowls.ManagerDashboardActivities.WaitingOrdersFragment;

public class CreateOrderListPagerAdapter extends FragmentStatePagerAdapter {
    public BowlsSaladsFragment bowlsSaladsFragment;
    public CustomBowlsFragment customBowlsFragment;
    public SoupFragment soupFragment;
    public DrinksExtrasFragment drinksExtrasFragment;

    private int numTabs;

    public CreateOrderListPagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CustomBowlsFragment();

            case 1:
                return new BowlsSaladsFragment();

            case 2:
                return new SoupFragment();

            case 3:
                return new DrinksExtrasFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
