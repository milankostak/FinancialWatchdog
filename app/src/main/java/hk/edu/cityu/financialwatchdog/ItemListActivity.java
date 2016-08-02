package hk.edu.cityu.financialwatchdog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import hk.edu.cityu.financialwatchdog.entity.Item;

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

    private List<Item> items;
    private ListView listView;
    private ItemListAdapter adapter;

    private static final int RESULT_PARAMETER = 31;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_PARAMETER) {
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
                startActivityForResult(i, RESULT_PARAMETER);
            }
        });

        update();
    }

    private void update() {
        items = Item.listAll();
        adapter = new ItemListAdapter(this, items);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
