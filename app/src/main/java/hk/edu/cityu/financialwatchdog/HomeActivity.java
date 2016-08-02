package hk.edu.cityu.financialwatchdog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;

import hk.edu.cityu.financialwatchdog.entity.Category;
import hk.edu.cityu.financialwatchdog.entity.Item;
import hk.edu.cityu.financialwatchdog.fragments.PageFragmentMonth;
import hk.edu.cityu.financialwatchdog.fragments.PageFragmentToday;
import hk.edu.cityu.financialwatchdog.fragments.PageFragmentWeek;
import hk.edu.cityu.financialwatchdog.fragments.PageFragmentYesterday;
import hk.edu.cityu.financialwatchdog.tabs.TabsPagerAdapter;

public class HomeActivity extends AppCompatActivity {

    private static final int UPDATE_PARAMETER = 32;
    private TabsPagerAdapter tabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initTabs();
        createMockDatabaseData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_PARAMETER) {
            ((PageFragmentToday) tabsAdapter.getFragments().get(0)).update();
            ((PageFragmentYesterday) tabsAdapter.getFragments().get(1)).update();
            ((PageFragmentWeek) tabsAdapter.getFragments().get(2)).update();
            ((PageFragmentMonth) tabsAdapter.getFragments().get(3)).update();
        }
    }

    /**
     * Initialization of tab layout with pie charts
     */
    private void initTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        tabsAdapter = new TabsPagerAdapter(getSupportFragmentManager(), getResources());

        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // remembers 3 pages that are not visible
        viewPager.setOffscreenPageLimit(tabsAdapter.getFragments().size() - 1);
    }

    public static void createMockDatabaseData() {
        if (Category.listAll().size() == 0) {

            new Category("Food", "#00FF00", 5000).save();
            new Category("Transportation", "#FF0000", 2000).save();
            new Category("Fun", "#0000FF", 1000).save();
            new Category("Clothes", "#FFFF00", 1000).save();
            new Category("Culture", "#00FFFF", 500).save();
            new Category("Household", "#FF00FF", 1500).save();

            Category cat = Category.findByName("Food");
            Calendar cal = Calendar.getInstance();

            cal.set(2016, 7, 2, 8, 23);
            new Item("Breakfast", cal.getTime(), 22f, 95f, 20, cat).save();
            cal.set(2016, 7, 2, 14, 23);
            new Item("Lunch", cal.getTime(), 22f, 95f, 18, cat).save();
            cal.set(2016, 7, 2, 19, 23);
            new Item("Dinner", cal.getTime(), 22f, 95f, 27, cat).save();
            cal.set(2016, 7, 1, 7, 23);
            new Item("Breakfast", cal.getTime(), 22f, 95f, 10, cat).save();
            cal.set(2016, 7, 1, 13, 23);
            new Item("Lunch", cal.getTime(), 22f, 95f, 39, cat).save();
            cal.set(2016, 7, 1, 16, 23);
            new Item("Banana", cal.getTime(), 22f, 95f, 2.5, cat).save();
            cal.set(2016, 7, 1, 18, 23);
            new Item("Dinner", cal.getTime(), 22f, 95f, 26, cat).save();
            cal.set(2016, 6, 31, 13, 23);
            new Item("Lunch", cal.getTime(), 22f, 95f, 28, cat).save();
            cal.set(2016, 6, 31, 18, 23);
            new Item("Dinner", cal.getTime(), 22f, 95f, 32, cat).save();

            cat = Category.findByName("Transportation");
            cal.set(2016, 7, 1, 18, 23);
            new Item("MTR", cal.getTime(), 22f, 95f, 5.5, cat).save();
            new Item("MTR", cal.getTime(), 22f, 95f, 6, cat).save();

            cat = Category.findByName("Fun");
            cal.set(2016, 7, 1, 18, 23);
            new Item("Beer", cal.getTime(), 22f, 95f, 9, cat).save();
            new Item("Ice cream", cal.getTime(), 22f, 95f, 9, cat).save();

            cat = Category.findByName("Clothes");
            cal.set(2016, 6, 31, 18, 23);
            new Item("Flip flops", cal.getTime(), 22f, 95f, 50, cat).save();

            //cat = Category.findByName("Culture");

            cat = Category.findByName("Household");
            cal.set(2016, 7, 2, 18, 23);
            new Item("Laundry", cal.getTime(), 22f, 95f, 10, cat).save();
            cal.set(2016, 6, 29, 18, 23);
            new Item("Laundry", cal.getTime(), 22f, 95f, 10, cat).save();
            cal.set(2016, 7, 1, 18, 23);
            new Item("Air-con", cal.getTime(), 22f, 95f, 20, cat).save();
            cal.set(2016, 6, 30, 18, 23);
            new Item("Air-con", cal.getTime(), 22f, 95f, 20, cat).save();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_category) {
            Intent i = new Intent(this, CategoryListActivity.class);
            startActivity(i);
        } else if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
        } else if (id == R.id.action_about) {
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void addThing(View v) {
        Log.i("clicks", "You add a record.");
        Intent i = new Intent(this, AddActivity.class);
        i.putExtra(AddActivity.EDIT_PARAMETER, false);
        startActivityForResult(i, UPDATE_PARAMETER);
    }

    public void detail(View v){
        Log.i("clicks", "see detail");
        Intent i = new Intent(this, ItemListActivity.class);
        startActivityForResult(i, UPDATE_PARAMETER);
    }



}
