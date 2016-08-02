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

import hk.edu.cityu.financialwatchdog.entity.Category;
import hk.edu.cityu.financialwatchdog.entity.Item;

class CategoryListAdapter extends ArrayAdapter<Category> {

    public CategoryListAdapter(Context context, List<Category> categories) {
        super(context, R.layout.category_list_row, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.category_list_row, parent, false);
        }

        Category category = getItem(position);

        TextView tvCategoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);
        tvCategoryName.setText(category.getName());

        TextView tvCategoryColor = (TextView) convertView.findViewById(R.id.tvCategoryColor);
        tvCategoryColor.setText(category.getColor());

        TextView tvCategoryLimit = (TextView) convertView.findViewById(R.id.tvCategoryLimit);
        tvCategoryLimit.setText(Integer.toString(category.getMoneyLimit()));

        return convertView;
    }
}

public class CategoryListActivity extends AppCompatActivity {

    private List<Category> categories;
    private ListView listView;
    private CategoryListAdapter adapter;

    private static final int RESULT_PARAMETER = 33;

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
        setContentView(R.layout.category_list_activity);
        listView = (ListView) findViewById(R.id.categoryListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Item item = adapter.getItem(position);
                Intent i = new Intent(CategoryListActivity.this, AddActivity.class);
                i.putExtra(AddActivity.EDIT_PARAMETER, true);
                i.putExtra(AddActivity.ID_PARAMETER, item.getId());
                startActivityForResult(i, RESULT_PARAMETER);*/
            }
        });

        update();
    }

    private void update() {
        categories = Category.listAll();
        adapter = new CategoryListAdapter(this, categories);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
