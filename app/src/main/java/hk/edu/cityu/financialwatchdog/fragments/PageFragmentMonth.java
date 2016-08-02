package hk.edu.cityu.financialwatchdog.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;

import java.util.Calendar;
import java.util.Date;

import hk.edu.cityu.financialwatchdog.PieChartHelper;
import hk.edu.cityu.financialwatchdog.R;

/**
 * Fragment displaying pie chart for last 30 days
 */
public class PageFragmentMonth extends Fragment {

    public PageFragmentMonth() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_fragment_month, container, false);
        initPieChart(rootView);
        return rootView;
    }

    private void initPieChart(View rootView) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.add(Calendar.DAY_OF_YEAR, -29);
        cal1.set(Calendar.HOUR, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 59);

        PieChart pieChart = (PieChart) rootView.findViewById(R.id.pieChartMonth);
        PieChartHelper.set(pieChart, getActivity(), cal1, cal2);
    }
}