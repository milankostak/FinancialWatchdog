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
import hk.edu.cityu.financialwatchdog.entity.Settings;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initTabs();
        testDatabase();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Initialization of tab layout with pie charts
     */
    private void initTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager(), getResources());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // remembers 2 pages that are not visible
        viewPager.setOffscreenPageLimit(2);
    }

    private void testSettings() {
        Settings settings = new Settings(this);
        System.out.println("Total Limit: " + settings.getTotalLimit());
        settings.setTotalLimit(123456);
    }

    public static void testDatabase() {
        if (Category.findAll().size() == 0) {
            Category.createMockCategories();
            Category cat = Category.findByName("Food");
            Calendar cal = Calendar.getInstance();

            cal.set(2016, 8, 2, 8, 23);
            new Item("Breakfast", cal.getTime(), 22f, 95f, 20, cat).save();
            cal.set(2016, 8, 2, 14, 23);
            new Item("Lunch", cal.getTime(), 22f, 95f, 18, cat).save();
            cal.set(2016, 8, 2, 19, 23);
            new Item("Dinner", cal.getTime(), 22f, 95f, 27, cat).save();
            cal.set(2016, 8, 1, 7, 23);
            new Item("Breakfast", cal.getTime(), 22f, 95f, 10, cat).save();
            cal.set(2016, 8, 1, 13, 23);
            new Item("Lunch", cal.getTime(), 22f, 95f, 39, cat).save();
            cal.set(2016, 8, 1, 16, 23);
            new Item("Banana", cal.getTime(), 22f, 95f, 2.5, cat).save();
            cal.set(2016, 8, 1, 18, 23);
            new Item("Dinner", cal.getTime(), 22f, 95f, 26, cat).save();
            cal.set(2016, 7, 31, 13, 23);
            new Item("Lunch", cal.getTime(), 22f, 95f, 28, cat).save();
            cal.set(2016, 7, 31, 18, 23);
            new Item("Dinner", cal.getTime(), 22f, 95f, 32, cat).save();

            cat = Category.findByName("Transportation");
            cal.set(2016, 8, 1, 18, 23);
            new Item("MTR", cal.getTime(), 22f, 95f, 5.5, cat).save();
            new Item("MTR", cal.getTime(), 22f, 95f, 6, cat).save();

            cat = Category.findByName("Fun");
            cal.set(2016, 8, 1, 18, 23);
            new Item("Beer", cal.getTime(), 22f, 95f, 9, cat).save();
            new Item("Ice cream", cal.getTime(), 22f, 95f, 9, cat).save();

            cat = Category.findByName("Clothes");
            cal.set(2016, 7, 31, 18, 23);
            new Item("Flip flops", cal.getTime(), 22f, 95f, 50, cat).save();

            //cat = Category.findByName("Culture");

            cat = Category.findByName("Household");
            cal.set(2016, 8, 2, 18, 23);
            new Item("Laundry", cal.getTime(), 22f, 95f, 10, cat).save();
            cal.set(2016, 7, 29, 18, 23);
            new Item("Laundry", cal.getTime(), 22f, 95f, 10, cat).save();
            cal.set(2016, 8, 1, 18, 23);
            new Item("Air-con", cal.getTime(), 22f, 95f, 20, cat).save();
            cal.set(2016, 7, 30, 18, 23);
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
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void addThing(View v) {
        Log.i("clicks", "You add a record.");
        Intent i = new Intent(this, AddActivity.class);
        i.putExtra(AddActivity.EDIT_PARAMETER, false);
        startActivity(i);
    }

    public void detail(View v){
        Log.i("clicks", "see detail");
        Intent i = new Intent(this, DetailActivity.class);
        startActivity(i);
    }



}
