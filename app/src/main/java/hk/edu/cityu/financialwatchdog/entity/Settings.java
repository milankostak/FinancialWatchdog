package hk.edu.cityu.financialwatchdog.entity;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Milan on 28.7.2016.
 */
public class Settings {
    private static final String SETTINGS_NAME = "AppSettings";
    private Activity activity;

    private static final String TOTAL_LIMIT_NAME = "TotalLimitName";
    public long totalLimit;

    /**
     * Constructor requires activity to be set,
     * because SharedPreference are functionality over activities
     * @param activity
     */
    public Settings(Activity activity) {
        this.activity = activity;
        restore();
    }

    /**
     * Restores data when object is instantiated
     */
    public void restore() {
        SharedPreferences settings = activity.getSharedPreferences(SETTINGS_NAME, 0);
        totalLimit = settings.getLong(TOTAL_LIMIT_NAME, 0);
    }

    /**
     * Save made changes
     * Important to call for changes to save
     */
    public void save() {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = activity.getSharedPreferences(SETTINGS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(TOTAL_LIMIT_NAME, totalLimit);

        // Commit the edits!
        editor.apply();
    }
}
