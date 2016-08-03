package hk.edu.cityu.financialwatchdog;

import android.content.DialogInterface;
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

import hk.edu.cityu.financialwatchdog.entity.Category;
import hk.edu.cityu.financialwatchdog.entity.Item;
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
            setCalendarToToday(dateFromCalendar);
        } else {
            dateFromCalendar.setTimeInMillis(timeFrom);
        }
        writeDateToInput(dateFromCalendar, etBudgetTimeFrom);

        // time to
        etBudgetTimeTo = (EditText) findViewById(R.id.etBudgetTimeTo);
        long timeTo = settings.getTimeTo();
        dateToCalendar = Calendar.getInstance();
        if (timeTo == 0) {
            setCalendarToToday(dateToCalendar);
        } else {
            dateToCalendar.setTimeInMillis(timeTo);
        }
        writeDateToInput(dateToCalendar, etBudgetTimeTo);
    }

    private void setCalendarToToday(Calendar cal) {
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(new Date());
        int year = tempCal.get(Calendar.YEAR);
        int month = tempCal.get(Calendar.MONTH);
        int day = tempCal.get(Calendar.DAY_OF_MONTH);
        cal.set(year, month, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
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
                    dateFromCalendar.set(year, month, day, 0, 0, 0);
                    dateFromCalendar.set(Calendar.MILLISECOND, 0);
                    writeDateToInput(dateFromCalendar, etBudgetTimeFrom);
                } else if (whichDialog == 2) {
                    dateToCalendar = Calendar.getInstance();
                    dateToCalendar.set(year, month, day, 0, 0, 0);
                    dateToCalendar.set(Calendar.MILLISECOND, 0);
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

    /**
     * http://stackoverflow.com/a/12213536
     * @param v view
     */
    public void resetSettings(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure? This will delete ALL your data in this application.");

        builder.setPositiveButton("Yes, delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                reset();
            }
        });

        builder.setNegativeButton("No, go back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void reset() {
        Item.deleteAll(Item.class);
        Category.deleteAll(Category.class);
        new Settings(this).reset();
        finish();
    }

    public void saveSettings(View v) {
        boolean problem = false;
        String totalLimitString = etTotalBudget.getText().toString();
        if (totalLimitString.equals("")) {
            problem = true;
            makeToast("Insert limit.");
        } else {
            long totalLimit = Long.parseLong(totalLimitString);
            settings.setTotalLimit(totalLimit);
        }
        long timeFrom = dateFromCalendar.getTimeInMillis();
        long timeTo = dateToCalendar.getTimeInMillis();
        if (timeFrom > timeTo) {
            problem = true;
            makeToast("\"Date from\" must be before \"date to\".");
        } else {
            settings.setTimeFrom(dateFromCalendar.getTimeInMillis());
            settings.setTimeTo(dateToCalendar.getTimeInMillis());
            long time = timeTo - timeFrom;
            int totalDays = (int) (time/(1000*3600*24)); // to milis, hours, days
            totalDays += 1; // include starting and ending date
            settings.setTotalDays(totalDays);
        }
        if (!problem) {
            finish();
        }
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
