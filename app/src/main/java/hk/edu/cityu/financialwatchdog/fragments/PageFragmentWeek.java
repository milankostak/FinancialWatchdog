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
import hk.edu.cityu.financialwatchdog.helpers.PieChartHelper;

/**
 * Fragment displaying pie chart for last 7 days
 */
public class PageFragmentWeek extends PieChartFragment {

    public PageFragmentWeek() {
        super(SHOW_WEEK_PARAM);
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

    @Override
    void initPieChart() {
        List<Calendar> calendars = CalendarHelper.getCalendarsForWeek();

        boolean isOverLimit = PieChartHelper.set(pieChart, getActivity(), calendars, 7);

        if (isOverLimit) {
            overLimitText.setVisibility(View.VISIBLE);
        } else {
            overLimitText.setVisibility(View.GONE);
        }
    }
}
