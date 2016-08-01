package hk.edu.cityu.financialwatchdog;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import hk.edu.cityu.financialwatchdog.entity.Category;
import hk.edu.cityu.financialwatchdog.entity.Item;
import hk.edu.cityu.financialwatchdog.entity.Settings;

/**
 * Activity implementing page for inserts information about a single purchase and saving it with necessary data into database
 */
public class AddActivity extends AppCompatActivity {

    public static final String EDIT_PARAMETER = "editParameter";
    public static final String ID_PARAMETER = "idParameter";
    private boolean isEditing;

    //work with permissions
    private final int PERMISSIONS_REQUEST_LOCATION = 5;
    private boolean isLocationCheckingPermission = false;

    // components
    private Spinner categorySpinner;
    private EditText etDate, etTime;

    //Item and calendars
    private Item item;
    private Calendar dateCalendar, timeCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        isEditing = getIntent().getExtras().getBoolean(EDIT_PARAMETER);
        initComponents();
    }

    private void initComponents() {
        initCategorySpinner();
        initCurrencySpinner();
        initEditTexts();
        initDateTime();
        initDatePicker();
        initTimePicker();
        isEditing = getIntent().getExtras().getBoolean(EDIT_PARAMETER);
        if (isEditing) initEditing();
    }

    private void initEditing() {

        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setVisibility(View.VISIBLE);
        long id = getIntent().getExtras().getLong(ID_PARAMETER);
        item = Item.findById(Item.class, id);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.delete();
                finish();
            }
        });

        //Object obj = categorySpinner.getSelectedItem();
        //Category category = Category.findByName(obj.toString());
        //categorySpinner.set

        EditText nameText = (EditText) findViewById(R.id.etName);
        nameText.setText(item.getName());

        EditText priceText = (EditText) findViewById(R.id.etPrice);
        priceText.setText(Double.toString(item.getPrice()));

        dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(item.getTime());
        writeDateToInput(dateCalendar);

        timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(item.getTime());
        writeTimeToInput(timeCalendar);
    }

    private void initEditTexts() {
        etDate = (EditText) findViewById(R.id.etDate);
        etTime = (EditText) findViewById(R.id.etTime);
    }

    /**
     * Initialize content of date and time EditTexts
     */
    private void initDateTime() {
        dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(new Date());
        writeDateToInput(dateCalendar);

        timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(new Date());
        writeTimeToInput(timeCalendar);
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

                dateCalendar = Calendar.getInstance();
                dateCalendar.set(year, month, day);
                writeDateToInput(dateCalendar);

                alertDateDialog.dismiss();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDateDialog.setView(dialogDateView);
                alertDateDialog.show();
            }
        });
    }

    /**
     * Initialize time picker and its dialog
     */
    private void initTimePicker() {
        final View dialogTimeView = View.inflate(this, R.layout.pick_time_dialog, null);
        final AlertDialog alertTimeDialog = new AlertDialog.Builder(this).create();

        dialogTimeView.findViewById(R.id.btnTimeSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePicker timePicker = (TimePicker) dialogTimeView.findViewById(R.id.addTimePicker);
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                timeCalendar = Calendar.getInstance();
                timeCalendar.set(0, 0, 0, hour, minute);
                writeTimeToInput(timeCalendar);

                alertTimeDialog.dismiss();
            }});
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertTimeDialog.setView(dialogTimeView);
                alertTimeDialog.show();
            }
        });
    }

    private void initCategorySpinner() {
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);

        // get data
        List<String> categoryNames = Category.getAllNames();
        String[] categoryNamesArray = categoryNames.toArray(new String[categoryNames.size()]);

        //Create an ArrayAdapter object, and place contents in the dropdown menu
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNamesArray);

        //set the template of dropdown menu
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categorySpinner.setAdapter(adapter);
    }

    private void initCurrencySpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.currencySpinner);

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                new String[]{"HKD", "TWD", "CZK", "USD", "CNY"});

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    /**
     * Saves inserted information about an item into database
     * @param v
     */
    public void saveItem(View v) {
        boolean problem = false;
        // name
        EditText nameText = (EditText) findViewById(R.id.etName);
        String name = nameText.getText().toString();
        if (name.equals("")) {
            problem = true;
            makeToast(getResources().getString(R.string.toast_insert_name));
        }
        //category
        Object obj = categorySpinner.getSelectedItem();
        Category category = Category.findByName(obj.toString());
        if (category == null) {
            problem = true;
            makeToast(getResources().getString(R.string.toast_select_category));
        }
        //price
        EditText priceText = (EditText) findViewById(R.id.etPrice);
        String priceString = priceText.getText().toString();
        double price = 0;
        if (priceString == null || priceString.equals("")) {
            problem = true;
            makeToast(getResources().getString(R.string.toast_insert_price));
        } else {
            price = Double.parseDouble(priceString);
            if (price < 0) {
                problem = true;
                makeToast(getResources().getString(R.string.toast_insert_positive_price));
            }
        }
        if (!problem) {
            // location
            Location location = getLocation();
            // date, time
            Date dateTime = getDateTime();
            // create item
            if (isEditing) {
                item.setName(name);
                item.setTime(dateTime);
                item.setPrice(price);
                item.setCategory(category);
            } else {
                item = new Item(name, dateTime, location, price, category);
            }

            // if not showing dialog with asking for permission
            if (!isLocationCheckingPermission) {
                saveAndFinish();
            }
        }
    }

    /**
     * Saves item and navigates back
     */
    private void saveAndFinish() {
        item.save();
        finish();
    }

    /**
     * Shows plain toast message
     * @param msg message to show
     */
    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * Simplifies checking for permission
     * @param permission permission to test
     * @return true or false it permissions is given
     */
    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * http://stackoverflow.com/questions/4735942/android-get-location-only-one-time
     */
    private Location getLocation() {
        Location location;
        boolean accessFineLocation = checkPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        boolean accessCoarseLocation = checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION);

        if (accessFineLocation && accessCoarseLocation) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            setLocationListener(locationManager);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        } else {
            Settings settings = new Settings(this);
            if (!settings.isLocationPermissionDenied()) {
                isLocationCheckingPermission = true;
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);
            }
            location = null;
        }

        return location;
    }

    /**
     * Returns combined date and time into one Date object for saving into database
     * @return combined date and time
     */
    private Date getDateTime() {
        int day = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int month = dateCalendar.get(Calendar.MONTH);
        int year = dateCalendar.get(Calendar.YEAR);

        int hour = timeCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = timeCalendar.get(Calendar.MINUTE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        calendar.setTimeZone(TimeZone.getDefault());
        return calendar.getTime();
    }

    /**
     * Writes date into date EditText with taking localization into consideration
     * @param calendar calendar
     */
    private void writeDateToInput(Calendar calendar) {
        java.text.DateFormat dateFormat = DateFormat.getDateFormat(getApplicationContext());
        String date = dateFormat.format(calendar.getTime());
        etDate.setText(date);
    }

    /**
     * Writes time into time EditText with taking localization into consideration
     * @param calendar calendar
     */
    private void writeTimeToInput(Calendar calendar) {
        java.text.DateFormat timeFormat = DateFormat.getTimeFormat(getApplicationContext());
        String time = timeFormat.format(calendar.getTime());
        etTime.setText(time);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Location location = getLocation();
                    if (location != null) {
                        item.setLatitude(location.getLatitude());
                        item.setLongitude(location.getLongitude());
                    }
                } else {
                    // mark that the user denied permission
                    // used for not asking again
                    new Settings(this).setLocationPermissionDenied(true);
                }
                saveAndFinish();
                return;
            }
        }
    }

    /**
     * Adds location updates listener to catch latest changes in location
     * Necessary when running on emulator
     * http://stackoverflow.com/questions/14784398/android-emulator-geo-fix-is-unable-to-set-gps-location
     * @param lm location manager
     */
    @SuppressWarnings("MissingPermission")
    private void setLocationListener(LocationManager lm) {
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {

            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
            }
            @Override
            public void onProviderEnabled(String arg0) {
            }
            @Override
            public void onProviderDisabled(String arg0) {
            }
            @Override
            public void onLocationChanged(Location loc) {
                //System.out.println("Lat: " + loc.getLatitude() + "; Long: " + loc.getLongitude());
            }
        });
    }

}
