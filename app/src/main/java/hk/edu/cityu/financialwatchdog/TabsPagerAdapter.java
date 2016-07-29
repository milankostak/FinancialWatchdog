package hk.edu.cityu.financialwatchdog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * Created by Matt on 29. 7. 2016.
 */
public class TabsPagerAdapter  extends FragmentPagerAdapter {
    private String[] title = {
            "Day",
            "Week",
            "Month"
    };

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
