package hk.edu.cityu.financialwatchdog.entity;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Milan on 28.7.2016.
 * Object for managing settings data
 * Uses shared preferences
 */
// https://developer.android.com/guide/topics/data/data-storage.html#pref
public class Settings {
    private static final String SETTINGS_NAME = "AppSettings";
    private Activity activity;

    //preferences objects
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    private static final String TOTAL_LIMIT_NAME = "TotalLimit";
    private long totalLimit;

    private static final String LOCATION_PERMISSION_NAME = "LocationPermission";
    private boolean locationPermissionDenied;

    /**
     * Constructor requires activity to be set,
     * because SharedPreference are functionality over activities
     * @param activity set from activity that is calling constructor
     */
    public Settings(Activity activity) {
        this.activity = activity;
        restore();
    }

    /**
     * Restores data when object is instantiated
     */
    private void restore() {
        settings = activity.getSharedPreferences(SETTINGS_NAME, 0);
        editor = settings.edit();

        totalLimit = settings.getLong(TOTAL_LIMIT_NAME, 0);
        locationPermissionDenied = settings.getBoolean(LOCATION_PERMISSION_NAME, false);
    }

    public long getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(long totalLimit) {
        this.totalLimit = totalLimit;
        editor.putLong(TOTAL_LIMIT_NAME, totalLimit);
        editor.apply();
    }

    public boolean isLocationPermissionDenied() {
        return locationPermissionDenied;
    }

    public void setLocationPermissionDenied(boolean locationPermissionDenied) {
        this.locationPermissionDenied = locationPermissionDenied;
        editor.putBoolean(LOCATION_PERMISSION_NAME, locationPermissionDenied);
        editor.apply();
    }
}
