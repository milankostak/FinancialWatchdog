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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Date;
import java.util.List;

import hk.edu.cityu.financialwatchdog.entity.Category;
import hk.edu.cityu.financialwatchdog.entity.Item;
import hk.edu.cityu.financialwatchdog.entity.Settings;

public class AddActivity extends AppCompatActivity {

    private Spinner categorySpinner;

    private final int PERMISSIONS_REQUEST_LOCATION = 5;
    private boolean isLocationCheckingPermission = false;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        initComponents();
    }

    private void initComponents() {
        initCategorySpinner();
        initCurrencySpinner();
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
        if (name == null || name.equals("")) {
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
            //location
            Location location = getLocation();
            //TODO date
            item = new Item(name, new Date(), location, price, category);

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
     * Necessary when runnning on emulator
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

