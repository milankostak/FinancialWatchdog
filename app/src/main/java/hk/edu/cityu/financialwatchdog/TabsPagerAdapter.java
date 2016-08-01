package hk.edu.cityu.financialwatchdog;

import android.content.res.Resources;
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
    private final String[] title = new String[3];

    public TabsPagerAdapter(FragmentManager fm, Resources resources) {
        super(fm);
        setTitles(resources);
        fragments = new ArrayList<>(3);
        fragments.add(new PageFragmentDay());
        fragments.add(new PageFragmentWeek());
        fragments.add(new PageFragmentMonth());
    }

    private void setTitles(Resources resources) {
        title[0] = resources.getString( R.string.day );
        title[1] = resources.getString( R.string.week );
        title[2] = resources.getString( R.string.month );
        //title[3] = resources.getString( R.string.total );
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
