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
 * Fragment displaying pie chart of all data
 */
public class PageFragmentTotal extends PieChartFragment {

    public PageFragmentTotal() {
        super(SHOW_TOTAL_PARAM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_fragment_total, container, false);
        pieChart = (PieChart) rootView.findViewById(R.id.pieChartTotal);
        overLimitText = (TextView) rootView.findViewById(R.id.overLimitTotal);
        initPieChart();
        return rootView;
    }

    @Override
    void initPieChart() {
        List<Calendar> calendars = CalendarHelper.getCalendarsForTotal();
        setupPieChart(calendars, 0);
    }
}