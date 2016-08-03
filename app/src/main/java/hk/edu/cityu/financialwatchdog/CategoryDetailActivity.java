package hk.edu.cityu.financialwatchdog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import hk.edu.cityu.financialwatchdog.entity.Category;

public class CategoryDetailActivity extends AppCompatActivity {

    protected static final String EDIT_PARAMETER = "editParameter";
    protected static final String ID_PARAMETER = "idParameter";

    private boolean isEditing;

    private Category category;
    private ColorPicker colorPicker;

    private View colorInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_detail_activity);
        initCategory();
        initComponents();
    }

    private void initCategory() {
        isEditing = getIntent().getExtras().getBoolean(EDIT_PARAMETER);
        if (isEditing) {
            long id = getIntent().getExtras().getLong(ID_PARAMETER);
            category = Category.findById(Category.class, id);
        } else {
            category = new Category();
        }
    }

    private void initComponents() {
        if (isEditing) {
            fillComponents();
        }
        initColorInput();
        initDeleteButton();
    }

    private void fillComponents() {
        TextView etCategoryName = (TextView) findViewById(R.id.etCategoryName);
        etCategoryName.setText(category.getName());

        TextView etCategoryLimit = (TextView) findViewById(R.id.etCategoryLimit);
        etCategoryLimit.setText(Long.toString(category.getMoneyLimit()));
    }

    private void initColorInput() {
        colorInput = findViewById(R.id.vCategoryColorDetail);
        if (isEditing) {
            colorInput.setBackgroundColor(category.getColor());
        } else {
            int color = Color.parseColor("#888888");
            colorInput.setBackgroundColor(color);
            category.setColor(color);
        }
        colorInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPicker();
            }
        });
    }

    private void initDeleteButton() {
        Button btnDelete = (Button) findViewById(R.id.btnDeleteCategory);
        btnDelete.setVisibility(View.VISIBLE);
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (category.getItems().size() == 0) {
                    category.delete();
                    finish();
                } else {
                    makeToast("This category has assigned items. It is not possible to delete such category. Delete all items first.");
                }
            }
        });
    }

    private void showColorPicker() {
        colorPicker = new ColorPicker(this, Color.red(category.getColor()), Color.green(category.getColor()), Color.blue(category.getColor()));
        colorPicker.show();

        Button okColor = (Button) colorPicker.findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = colorPicker.getColor();
                category.setColor(color);
                colorInput.setBackgroundColor(color);

                colorPicker.dismiss();
            }
        });
    }

    public void saveCategory(View v) {
        boolean problem = false;
        TextView etCategoryName = (TextView) findViewById(R.id.etCategoryName);
        String name = etCategoryName.getText().toString();
        if (name.equals("")) {
            problem = true;
            makeToast("Insert name.");
        } else {
            category.setName(name);
        }

        TextView etCategoryLimit = (TextView) findViewById(R.id.etCategoryLimit);
        String limitLongString = etCategoryLimit.getText().toString();
        if (limitLongString.equals("")) {
            problem = true;
            makeToast("Insert money limit.");
        } else {
            Long limitLong = Long.parseLong(limitLongString);
            category.setMoneyLimit(limitLong);
        }
        if (!problem) {
            saveAndFinish();
        }

    }

    /**
     * Saves item and navigates back
     */
    private void saveAndFinish() {
        category.save();
        finish();
    }

    /**
     * Shows plain toast message
     * @param msg message to show
     */
    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
