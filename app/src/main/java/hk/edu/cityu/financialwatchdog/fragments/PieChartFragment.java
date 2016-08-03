package hk.edu.cityu.financialwatchdog.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;

public abstract class PieChartFragment extends Fragment {

    public static final int SHOW_TODAY_PARAM = 1;
    public static final int SHOW_YESTERDAY_PARAM = 2;
    public static final int SHOW_WEEK_PARAM = 3;
    public static final int SHOW_MONTH_PARAM = 4;
    public static final int SHOW_TOTAL_PARAM = 5;

    private int myFragmentId;

    PieChart pieChart;
    TextView overLimitText;

    public PieChartFragment(int myFragmentId) {
        this.myFragmentId = myFragmentId;
    }

    public void update() {
        initPieChart();
    }

    abstract void initPieChart();

    public int getMyFragmentId() {
        return myFragmentId;
    }
}