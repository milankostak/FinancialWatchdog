package hk.edu.cityu.financialwatchdog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import hk.edu.cityu.financialwatchdog.entity.Item;
import hk.edu.cityu.financialwatchdog.helpers.CalendarHelper;

import static hk.edu.cityu.financialwatchdog.fragments.PieChartFragment.*;
import static hk.edu.cityu.financialwatchdog.ResultConstants.*;

/**
 * Created by Weida on 2016/7/29.
 * show detail about each spend record
 */
class ItemListAdapter extends ArrayAdapter<Item> {

    public ItemListAdapter(Context context, List<Item> items) {
        super(context, R.layout.item_list_row, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_list_row, parent, false);
        }

        Item item = getItem(position);

        TextView tvItemWhat = (TextView) convertView.findViewById(R.id.tvItemWhat);
        tvItemWhat.setText(item.getSubject());

        TextView tvItemCategory = (TextView) convertView.findViewById(R.id.tvItemCategory);
        tvItemCategory.setText(item.getCategory().getName());

        TextView tvItemTime = (TextView) convertView.findViewById(R.id.tvItemTime);
        java.text.DateFormat timeFormat = DateFormat.getDateFormat(getContext());
        timeFormat.setNumberFormat(NumberFormat.getNumberInstance(Locale.getDefault()));
        String time = timeFormat.format(item.getTime());
        tvItemTime.setText(time);

        TextView tvItemPrice = (TextView) convertView.findViewById(R.id.tvItemPrice);
        tvItemPrice.setText(String.valueOf(item.getPrice()));

        return convertView;
    }
}

public class ItemListActivity extends AppCompatActivity {

    public static final String ID_PARAMETER = "idParameter";
    private List<Item> items;
    private ListView listView;
    private ItemListAdapter adapter;

    private int idOfDetail;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BACK_ITEM_DETAIL_PARAM) {
            update();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_activity);
        listView = (ListView) findViewById(R.id.itemListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = adapter.getItem(position);
                Intent i = new Intent(ItemListActivity.this, AddActivity.class);
                i.putExtra(AddActivity.EDIT_PARAMETER, true);
                i.putExtra(AddActivity.ID_PARAMETER, item.getId());
                startActivityForResult(i, BACK_ITEM_DETAIL_PARAM);
            }
        });

        idOfDetail = getIntent().getExtras().getInt(ID_PARAMETER);

        update();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_item) {
            Intent i = new Intent(ItemListActivity.this, AddActivity.class);
            i.putExtra(AddActivity.EDIT_PARAMETER, false);
            startActivityForResult(i, BACK_ITEM_DETAIL_PARAM);
        }

        return super.onOptionsItemSelected(item);
    }

    private void update() {
        String[] timeArray;
        switch (idOfDetail) {
            case SHOW_TODAY_PARAM:
                timeArray = CalendarHelper.getStringArray(CalendarHelper.getCalendarsForToday());
                break;
            case SHOW_YESTERDAY_PARAM:
                timeArray = CalendarHelper.getStringArray(CalendarHelper.getCalendarsForYesterday());
                break;
            case SHOW_WEEK_PARAM:
                timeArray = CalendarHelper.getStringArray(CalendarHelper.getCalendarsForWeek());
                break;
            case SHOW_MONTH_PARAM:
                timeArray = CalendarHelper.getStringArray(CalendarHelper.getCalendarsForMonth());
                break;
            default:
                timeArray = CalendarHelper.getStringArray(CalendarHelper.getCalendarsForTotal());
                break;
        }
        items = Item.find(Item.class, "time > ? and time < ?", timeArray, "", "time desc", "");

        adapter = new ItemListAdapter(this, items);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
