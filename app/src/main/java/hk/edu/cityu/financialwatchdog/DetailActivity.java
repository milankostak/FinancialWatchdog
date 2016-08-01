package hk.edu.cityu.financialwatchdog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hk.edu.cityu.financialwatchdog.entity.Item;

/**
 * Created by Weida on 2016/7/29.
 * show detail about each spend record
 */
class DetailListAdapter extends ArrayAdapter<Item> {
    public DetailListAdapter(Context context, List<Item> items) {
        super(context, R.layout.detail_row, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.detail_row, parent, false);
        }

        Item item = getItem(position);

        TextView indexView = (TextView) convertView.findViewById(R.id.tvIndex);
        indexView.setText(String.valueOf(position + 1));

        TextView whatView = (TextView) convertView.findViewById(R.id.tvWhat);
        whatView.setText(item.getName());

        TextView whenView = (TextView) convertView.findViewById(R.id.tvWhen);
        whenView.setText(item.getTime().toString());

        TextView whereView = (TextView) convertView.findViewById(R.id.tvWhere);
        whereView.setText("(" + item.getLongitude() + "," + item.getLatitude() + ")");

        TextView priceView = (TextView) convertView.findViewById(R.id.tvPrice);
        priceView.setText(String.valueOf(item.getPrice()));

        return convertView;
    }
}

public class DetailActivity extends AppCompatActivity {
    List<Item> items;
    ListView listView;
    DetailListAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        listView = (ListView) findViewById(R.id.listView);
        items = new ArrayList<>();

        adapter = new DetailListAdapter(this, items);
        listView.setAdapter(adapter);

        retrieveData();
    }


    public void retrieveData() {
        Iterator<Item> items = Item.findAll(Item.class);
        while (items.hasNext()) {
            this.items.add(items.next());
        }

        adapter.notifyDataSetChanged();
    }

}
