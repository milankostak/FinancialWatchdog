package hk.edu.cityu.financialwatchdog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import hk.edu.cityu.financialwatchdog.entity.Category;
import hk.edu.cityu.financialwatchdog.entity.Item;

/**
 * Created by Weida on 2016/7/28.
 */
public class AddActivity extends AppCompatActivity {
    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        initComponents();
    }

    private void initComponents() {
        initCategorySpinner();
        initCurrencySpinner();
    }

    private void initCategorySpinner() {
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);

        //Create an ArrayAdapter object, and place contents in the dropdown menu
        List<String> categoryNames = Category.getAllNames();
        String[] categoryNamesArray = categoryNames.toArray(new String[categoryNames.size()]);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNamesArray);

        //set the template of dropdown menu
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categorySpinner.setAdapter(adapter);
        //reaction after select something
        categorySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                Toast.makeText(AddActivity.this, "you chose " + adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(AddActivity.this, "you don't choose anything", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initCurrencySpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.currencySpinner);

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                new String[]{"HKD", "TWD", "CZK", "USD", "CNY"});

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

    }

    public void saveItem(View v) {

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        EditText nameText = (EditText) findViewById(R.id.etName);
        String name = nameText.getText().toString();
        System.out.println(name);

        Object obj = categorySpinner.getSelectedItem();
        Category category = Category.findByName(obj.toString());

        EditText priceText = (EditText) findViewById(R.id.etPrice);
        double price = Double.parseDouble(priceText.getText().toString());
        //public Item(String name, Date time, float latitude, float longitude, double price, Category category) {
        //TODO save time and location
        Item item = new Item(name, new Date(), 10, 10, price, category);
        item.save();

        //go back to HOME activity
        finish();
    }

}

