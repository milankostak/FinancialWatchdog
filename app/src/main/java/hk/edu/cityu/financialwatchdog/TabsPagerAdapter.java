package hk.edu.cityu.financialwatchdog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import hk.edu.cityu.financialwatchdog.fragments.PageFragmentDay;
import hk.edu.cityu.financialwatchdog.fragments.PageFragmentMonth;
import hk.edu.cityu.financialwatchdog.fragments.PageFragmentWeek;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private final String[] title = {
            "Day",
            "Week",
            "Month"
    };

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>(3);
        fragments.add(new PageFragmentDay());
        fragments.add(new PageFragmentWeek());
        fragments.add(new PageFragmentMonth());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
