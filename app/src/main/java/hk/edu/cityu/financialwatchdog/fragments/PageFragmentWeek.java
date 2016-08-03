package hk.edu.cityu.financialwatchdog.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;

import java.util.Calendar;
import java.util.Date;

import hk.edu.cityu.financialwatchdog.R;
import hk.edu.cityu.financialwatchdog.helpers.PieChartHelper;

/**
 * Fragment displaying pie chart for last 7 days
 */
public class PageFragmentWeek extends Fragment {

    private PieChart pieChart;
    private TextView overLimitText;

    public PageFragmentWeek() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_fragment_week, container, false);
        pieChart = (PieChart) rootView.findViewById(R.id.pieChartWeek);
        overLimitText = (TextView) rootView.findViewById(R.id.overLimitWeek);
        initPieChart();
        return rootView;
    }

    public void update() {
        initPieChart();
    }

    private void initPieChart() {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.add(Calendar.DAY_OF_YEAR, -6);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 59);

        boolean isOverLimit = PieChartHelper.set(pieChart, getActivity(), cal1, cal2, 7);

        if (isOverLimit) {
            overLimitText.setVisibility(View.VISIBLE);
        } else {
            overLimitText.setVisibility(View.GONE);
        }
    }
}
