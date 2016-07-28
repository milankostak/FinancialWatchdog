package hk.edu.cityu.financialwatchdog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Weida on 2016/7/28.
 */
public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        category_spinner();
        currencySpinner();
    }


    public void category_spinner() {
        Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);
        //Create an ArrayAdapter object, and place contents in the dropdown menu
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Clothes","Fun","Food","Transportation"});
        //set the template of dropdown menu
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        //reaction after select something
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                Toast.makeText(AddActivity.this, "you choose "+adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(AddActivity.this, "you don't choose anything", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void currencySpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.currencySpinner);

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                new String[]{"HKD", "TWD", "CZK", "USD", "CNY"});

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

    }




    public void saveRecord(View v) {
        Log.i("clicks","You save a record");
        Intent i=new Intent(AddActivity.this, HomeActivity.class);
        startActivity(i);
    }


}

