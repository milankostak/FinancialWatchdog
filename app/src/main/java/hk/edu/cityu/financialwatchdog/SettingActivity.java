package hk.edu.cityu.financialwatchdog;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import hk.edu.cityu.financialwatchdog.entity.Settings;

public class SettingActivity extends AppCompatActivity {

    private EditText etTotalBudget;
    private EditText etBudgetTimeFrom, etBudgetTimeTo;
    private Settings settings;
    private Calendar dateFromCalendar, dateToCalendar;
    private byte whichDialog = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        settings = new Settings(this);
        initTotalBudget();
        initDateComponents();
        initDatePicker();
    }

    private void initTotalBudget() {
        etTotalBudget = (EditText) findViewById(R.id.etTotalBudget);
        etTotalBudget.setText(Long.toString(settings.getTotalLimit()));
    }

    private void initDateComponents() {
        // time from
        etBudgetTimeFrom = (EditText) findViewById(R.id.etBudgetTimeFrom);
        long timeFrom = settings.getTimeFrom();
        dateFromCalendar = Calendar.getInstance();
        if (timeFrom == 0) {
            dateFromCalendar.setTime(new Date());
        } else {
            dateFromCalendar.setTimeInMillis(timeFrom);
        }
        writeDateToInput(dateFromCalendar, etBudgetTimeFrom);

        // time to
        etBudgetTimeTo = (EditText) findViewById(R.id.etBudgetTimeTo);
        long timeTo = settings.getTimeTo();
        dateToCalendar = Calendar.getInstance();
        if (timeTo == 0) {
            dateToCalendar.setTime(new Date());
        } else {
            dateToCalendar.setTimeInMillis(timeTo);
        }
        writeDateToInput(dateToCalendar, etBudgetTimeTo);
    }

    /**
     * Initialize date picker and its dialog
     */
    private void initDatePicker() {
        final View dialogDateView = View.inflate(this, R.layout.pick_date_dialog, null);
        final AlertDialog alertDateDialog = new AlertDialog.Builder(this).create();

        dialogDateView.findViewById(R.id.btnDateSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = (DatePicker) dialogDateView.findViewById(R.id.addDatePicker);
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                if (whichDialog == 1) {
                    dateFromCalendar = Calendar.getInstance();
                    dateFromCalendar.set(year, month, day);
                    writeDateToInput(dateFromCalendar, etBudgetTimeFrom);
                } else if (whichDialog == 2) {
                    dateToCalendar = Calendar.getInstance();
                    dateToCalendar.set(year, month, day);
                    writeDateToInput(dateToCalendar, etBudgetTimeTo);
                }

                alertDateDialog.dismiss();
            }
        });

        etBudgetTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichDialog = 1;
                alertDateDialog.setView(dialogDateView);
                alertDateDialog.show();
            }
        });

        etBudgetTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichDialog = 2;
                alertDateDialog.setView(dialogDateView);
                alertDateDialog.show();
            }
        });
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
        settings.setTimeFrom(dateFromCalendar.getTimeInMillis());
        settings.setTimeTo(dateToCalendar.getTimeInMillis());
        finish();
    }

    /**
     * Writes date into date EditText with taking localization into consideration
     * @param calendar calendar
     * @param editText edit text to insert to
     */
    private void writeDateToInput(Calendar calendar, EditText editText) {
        java.text.DateFormat dateFormat = DateFormat.getDateFormat(getApplicationContext());
        String date = dateFormat.format(calendar.getTime());
        editText.setText(date);
    }

    /**
     * Shows plain toast message
     * @param msg message to show
     */
    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
