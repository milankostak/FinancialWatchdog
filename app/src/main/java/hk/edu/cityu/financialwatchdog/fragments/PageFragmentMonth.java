package hk.edu.cityu.financialwatchdog.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import hk.edu.cityu.financialwatchdog.R;

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
        PieChart pieChart = (PieChart) rootView.findViewById(R.id.pieChartMonth);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1f, 0));
        entries.add(new Entry(2f, 1));
        entries.add(new Entry(3f, 2));
        entries.add(new Entry(4f, 3));
        entries.add(new Entry(5f, 4));
        entries.add(new Entry(90f, 5));

        PieDataSet dataset = new PieDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setDescription("Description");
        pieChart.setData(data);

        pieChart.animateY(1000);
    }
}