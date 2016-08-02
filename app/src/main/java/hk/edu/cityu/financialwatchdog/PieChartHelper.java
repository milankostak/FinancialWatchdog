package hk.edu.cityu.financialwatchdog;

import android.app.Activity;
import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
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

/**
 * Created by Milan on 2.8.2016.
 * Helper class for setup of everything about pie chart
 */
public class PieChartHelper {

    /**
     * Only public method calling other methods
     * @param pieChart pie chart component
     * @param activity activity for purpose of accessing data in settings
     * @param cal1 calendar from
     * @param cal2 calendar to
     */
    public static void set(PieChart pieChart, Activity activity, Calendar cal1, Calendar cal2) {
        Map<Category, Long> categoriesMap = getChartDataByDate(cal1, cal2);

        List<Entry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        setDataToChart(categoriesMap, entries, labels, colors, activity);

        // set charts objects
        setupPieChart(pieChart, entries, labels, colors);
    }

    /**
     * Loads data from database a transforms it to map where each category has its spend money
     * @param cal1 calendar from
     * @param cal2 calendar to
     * @return map of categories and spend money
     */
    private static Map<Category, Long> getChartDataByDate(Calendar cal1, Calendar cal2) {
        // get data
        List<Item> items = Item.findWithQuery(Item.class,
                "select *" +
                        "from category c join item i on category = i.category " +
                        "where time > ? and time < ? " +
                        "group by i.id",
                new String[]{Long.toString(cal1.getTime().getTime()), Long.toString(cal2.getTime().getTime())});
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
     * @param activity activity for settings
     */
    private static void setDataToChart(Map<Category, Long> categoriesMap, List<Entry> entries, List<String> labels, List<Integer> colors, Activity activity) {
        int i = 0;
        int sumMoney = 0;
        for (Map.Entry<Category, Long> mapEntry : categoriesMap.entrySet()) {
            long expenses = mapEntry.getValue();
            sumMoney += expenses;

            entries.add(new Entry(expenses, i++));
            labels.add(mapEntry.getKey().getName());
            colors.add(Color.parseColor(mapEntry.getKey().getColor()));
        }

        // set remaining money
        Settings settings = new Settings(activity);
        //settings.setTotalLimit(500);
        long remainingMoney = settings.getTotalLimit() - sumMoney;
        entries.add(new Entry(remainingMoney, i++));
        labels.add("Remaining money");
        colors.add(Color.rgb(100, 100, 100));
    }

    /**
     * Setup pie charts with prepared data
     * @param pieChart pie chart component
     * @param entries entries
     * @param labels labels
     * @param colors colors
     */
    private static void setupPieChart(PieChart pieChart, List<Entry> entries, List<String> labels, List<Integer> colors) {
        PieDataSet dataset = new PieDataSet(entries, "# of Calls");
        PieData data = new PieData(labels, dataset);
        dataset.setColors(colors);
        pieChart.setDescription("Description");
        pieChart.setData(data);
        pieChart.animateY(1000);
    }

}
