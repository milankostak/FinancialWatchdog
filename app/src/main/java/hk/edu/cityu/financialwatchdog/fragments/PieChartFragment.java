package hk.edu.cityu.financialwatchdog.fragments;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hk.edu.cityu.financialwatchdog.entity.Category;
import hk.edu.cityu.financialwatchdog.entity.Item;
import hk.edu.cityu.financialwatchdog.entity.Settings;
import hk.edu.cityu.financialwatchdog.helpers.CalendarHelper;

/**
 * Abstract class for pie chart handling, implements Fragment for inserting into tab layout
 * Chart from: https://github.com/PhilJay/MPAndroidChart
 */
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

    ////////////////////////////////////////////////
    /**
     * Only public method calling other methods
     * @param calendars list of two calendars - from and to
     * @param numberOfDays number of days that the chart is displaying, 0 means total
     * @return boolean value if the limit was exceeded for given time period
     */
    public void setupPieChart(List<Calendar> calendars, int numberOfDays) {
        // get data
        Map<Category, Long> categoriesMap = getChartDataByDate(calendars);

        // prepare object
        List<Entry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        // set data into chart and get overLimit
        boolean isOverLimit = setDataToChart(categoriesMap, entries, labels, colors, numberOfDays);

        // set charts objects
        setupPieChart(pieChart, entries, labels, colors);

        // set warning text view
        setWarnings(isOverLimit);
    }

    /**
     * Sets visibility of warning
     * @param isOverLimit if chart is over limit
     */
    private void setWarnings(boolean isOverLimit) {
        if (isOverLimit) {
            overLimitText.setVisibility(View.VISIBLE);
        } else {
            overLimitText.setVisibility(View.GONE);
        }
    }


    /**
     * Loads data from database a transforms it to map where each category has its spend money
     * @param calendars calendar from and to
     * @return map of categories and spend money
     */
    private Map<Category, Long> getChartDataByDate(List<Calendar> calendars) {
        // get data
        List<Item> items = Item.find(Item.class, "time > ? and time < ?", CalendarHelper.getStringArray(calendars));
        List<Category> cats = Category.listAll();

        // init
        Map<Category, Long> catsMap = new HashMap<>();
        for (Category cat : cats) {
            catsMap.put(cat, 0L);
        }

        // fill
        for (Item item : items) {
            long currentSum = catsMap.get(item.getCategory());
            currentSum += item.getPrice();
            catsMap.put(item.getCategory(), currentSum);
        }
        return catsMap;
    }

    /**
     * Divide data from Map object into Lists
     * @param categoriesMap map with data
     * @param entries entries
     * @param labels labels
     * @param colors colors
     * @param numberOfDays number of days that the chart is displaying, 0 means total
     */
    private boolean setDataToChart(Map<Category, Long> categoriesMap,
                                          List<Entry> entries, List<String> labels, List<Integer> colors,
                                          int numberOfDays) {
        int i = 0;
        int sumMoney = 0;
        for (Map.Entry<Category, Long> mapEntry : categoriesMap.entrySet()) {
            long expenses = mapEntry.getValue();
            sumMoney += expenses;

            entries.add(new Entry(expenses, i++));
            labels.add(mapEntry.getKey().getName());
            colors.add(mapEntry.getKey().getColor());
        }

        Settings settings = new Settings(getActivity());
        // setupPieChart remaining money
        long totalLimit = settings.getTotalLimit();
        long currentLimit;
        if (numberOfDays == 0) {
            currentLimit = totalLimit;
        } else {
            int totalDays = settings.getTotalDays();
            currentLimit = (totalLimit / totalDays) * numberOfDays;
        }

        boolean isOverLimit;
        long remainingMoney = currentLimit - sumMoney;
        if (remainingMoney < 0) {
            isOverLimit = true;
            entries.add(new Entry(0, i++));
        } else {
            isOverLimit = false;
            entries.add(new Entry(remainingMoney, i++));
        }
        labels.add("Remaining money");
        colors.add(Color.rgb(100, 100, 100));

        return isOverLimit;
    }

    /**
     * Setup pie charts with prepared data
     * @param pieChart pie chart component
     * @param entries entries
     * @param labels labels
     * @param colors colors
     */
    private void setupPieChart(PieChart pieChart, List<Entry> entries, List<String> labels, List<Integer> colors) {
        PieDataSet dataset = new PieDataSet(entries, "");
        PieData data = new PieData(labels, dataset);
        dataset.setColors(colors);
        pieChart.setDescription("");
        pieChart.setData(data);
        pieChart.animateY(1000);

        Legend legend = pieChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
    }

}