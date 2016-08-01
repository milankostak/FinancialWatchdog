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
import android.widget.Button;

import java.util.Iterator;

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

        //////////// testing START
        Button btnTestDB = (Button) findViewById(R.id.testDB);
        btnTestDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDatabase();
                testSettings();
            }
        });
        Category.createMockCategories();
        /////////// testing END
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

    private void testDatabase() {
        Category.createMockCategories();
        //Category cat = Category.findById(Category.class, 1);

        //Item item = new Item("Dumplings", new Date(), 22.165468f, 95.961654f, 59, cat);
        //item.save();

        /*List<Item> items = cat.getItems();
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).toString());
        }*/
        Iterator<Item> items = Item.findAll(Item.class);
        while (items.hasNext()) {
            System.out.println(items.next().toString());
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
        startActivity(i);
    }

    public void detail(View v){
        Log.i("clicks", "see detail");
        Intent i = new Intent(this, DetailActivity.class);
        startActivity(i);
    }



}
