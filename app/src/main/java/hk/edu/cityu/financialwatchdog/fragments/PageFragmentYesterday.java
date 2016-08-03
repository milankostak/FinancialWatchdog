package hk.edu.cityu.financialwatchdog.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;

import java.util.Calendar;
import java.util.List;

import hk.edu.cityu.financialwatchdog.R;
import hk.edu.cityu.financialwatchdog.helpers.CalendarHelper;

/**
 * Fragment displaying pie chart for yesterday
 */
public class PageFragmentYesterday extends PieChartFragment {

    public PageFragmentYesterday() {
        super(SHOW_YESTERDAY_PARAM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_fragment_yesterday, container, false);
        pieChart = (PieChart) rootView.findViewById(R.id.pieChartYesterday);
        overLimitText = (TextView) rootView.findViewById(R.id.overLimitYesterday);
        initPieChart();
        return rootView;
    }

    @Override
    void initPieChart() {
        List<Calendar> calendars = CalendarHelper.getCalendarsForYesterday();
        setupPieChart(calendars, 1);
    }

}