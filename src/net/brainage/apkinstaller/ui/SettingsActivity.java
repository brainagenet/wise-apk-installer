/*
 * (#) net.brainage.apkinstaller.ui.SettingsActivity
 * Created on 2010. 10. 27.
 */
package net.brainage.apkinstaller.ui;

import java.io.File;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.text.TextUtils;
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

        initializePreferenceScreen();
    }

    /**
     * 
     */
    private void initializePreferenceScreen() {
        PreferenceScreen ps = getPreferenceScreen();

        // base directory preference
        final CharSequence baseDirPreferenceKey = getResources().getText(
                R.string.base_directory_key);
        final EditTextPreference baseDirectoryPreference;
        baseDirectoryPreference = (EditTextPreference) ps.findPreference(baseDirPreferenceKey);
        baseDirectoryPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String oldBaseDirectory = baseDirectoryPreference.getText();
                String newBaseDirectory = newValue.toString();

                if ( DEBUG ) {
                    Log.d(TAG, "onPreferenceChange(" + baseDirPreferenceKey
                            + ") -----------------------------------");
                    Log.d(TAG, "    - old value : " + oldBaseDirectory);
                    Log.d(TAG, "    - new value : " + newBaseDirectory);
                }

                if ( oldBaseDirectory.equals(newBaseDirectory) ) {
                    if ( DEBUG ) {
                        Log.d(TAG, "    - same old value and new value");
                    }
                    return true;
                }

                if ( !newBaseDirectory.startsWith("/mnt/sdcard/") ) {
                    String tmp = "/mnt/sdcard";
                    if ( !newBaseDirectory.startsWith("/") ) {
                        tmp += "/";
                    }
                    tmp += newBaseDirectory;
                    newBaseDirectory = tmp;
                }

                File baseDir = new File(newBaseDirectory);
                if ( !baseDir.exists() ) {
                    if ( DEBUG ) {
                        Log.d(TAG, "    - new dir was not exist and then create mkdirs.");
                    }
                    baseDir.mkdirs();
                }

                /* save new value to preference */
                baseDirectoryPreference.setText(newBaseDirectory);

                return false;
            }
        });

        String value = baseDirectoryPreference.getText();
        if ( TextUtils.isEmpty(value) ) {
            String defaultValue = getResources().getText(R.string.default_base_directory)
                    .toString();
            baseDirectoryPreference.setText(defaultValue);
        }
    }

}
