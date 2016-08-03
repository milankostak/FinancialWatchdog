package hk.edu.cityu.financialwatchdog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import hk.edu.cityu.financialwatchdog.entity.Settings;

public class SettingActivity extends AppCompatActivity {

    private EditText etTotalBudget;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setAndFillComponents();
    }

    private void setAndFillComponents() {
        settings = new Settings(this);
        etTotalBudget = (EditText) findViewById(R.id.etTotalBudget);
        etTotalBudget.setText(Long.toString(settings.getTotalLimit()));
    }

    public void resetSettings(View v) {

    }

    public void saveSettings(View v) {
        String totalLimitString = etTotalBudget.getText().toString();
        if (totalLimitString.equals("")) {
            makeToast("Insert limit.");
        } else {
            long totalLimit = Long.parseLong(totalLimitString);
            settings.setTotalLimit(totalLimit);
        }
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
