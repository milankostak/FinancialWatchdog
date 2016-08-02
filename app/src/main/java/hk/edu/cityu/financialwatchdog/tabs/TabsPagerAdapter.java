package hk.edu.cityu.financialwatchdog.tabs;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import hk.edu.cityu.financialwatchdog.R;
import hk.edu.cityu.financialwatchdog.fragments.*;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_OF_TABS = 4;
    private final List<Fragment> fragments = new ArrayList<>(NUMBER_OF_TABS);
    private final String[] title = new String[NUMBER_OF_TABS];

    public TabsPagerAdapter(FragmentManager fm, Resources resources) {
        super(fm);
        setTitles(resources);
        fragments.add(new PageFragmentToday());
        fragments.add(new PageFragmentYesterday());
        fragments.add(new PageFragmentWeek());
        fragments.add(new PageFragmentMonth());
    }

    private void setTitles(Resources resources) {
        title[0] = resources.getString( R.string.day );
        title[1] = "yesterday";//resources.getString( R.string.yesterday );
        title[2] = resources.getString( R.string.week );
        title[3] = resources.getString( R.string.month );
        //title[3] = resources.getString( R.string.total );
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return NUMBER_OF_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    public List<Fragment> getFragments() {
        return fragments;
    }
}
