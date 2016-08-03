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

import hk.edu.cityu.financialwatchdog.helpers.PieChartHelper;
import hk.edu.cityu.financialwatchdog.R;

/**
 * Fragment displaying pie chart for today
 */
public class PageFragmentToday extends PieChartFragment {

    public PageFragmentToday() {
        super(SHOW_TODAY_PARAM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_fragment_today, container, false);
        pieChart = (PieChart) rootView.findViewById(R.id.pieChartToday);
        overLimitText = (TextView) rootView.findViewById(R.id.overLimitToday);
        initPieChart();
        return rootView;
    }

    @Override
    void initPieChart() {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 59);

        boolean isOverLimit = PieChartHelper.set(pieChart, getActivity(), cal1, cal2, 1);

        if (isOverLimit) {
            overLimitText.setVisibility(View.VISIBLE);
        } else {
            overLimitText.setVisibility(View.GONE);
        }
    }

}