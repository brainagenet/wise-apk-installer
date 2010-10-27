/*
 * (#) net.brainage.apkinstaller.ui.SettingsActivity
 * Created on 2010. 10. 27.
 */
package net.brainage.apkinstaller.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import net.brainage.apkinstaller.AppConstants;
import net.brainage.apkinstaller.R;

/**
 * 
 * 
 * @author ntmyoungseok.seo@lge.com
 */
public class SettingsActivity extends PreferenceActivity
{

    /**
     * 
     */
    private static final boolean DEBUG = AppConstants.DEBUG;

    /**
     * 
     */
    private static final String TAG = "SettingsActivity";

    /**
     * 
     */
    public static final String SETTINGS_NAME = "wise_apk_installer_settings";

    /**
     * 
     * @param savedInstanceState
     * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ( DEBUG ) {
            Log.d(TAG, "onCreate() -----------------------------------");
        }
        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.setSharedPreferencesName(SETTINGS_NAME);
        preferenceManager.setSharedPreferencesMode(MODE_PRIVATE);
        if ( DEBUG ) {
            Log.d(TAG, "    - preference name : " + SETTINGS_NAME);
            Log.d(TAG, "    - preference mode : MODE_PRIVATE[" + MODE_PRIVATE + "]");
        }

        addPreferencesFromResource(R.xml.preferences);
    }

}
