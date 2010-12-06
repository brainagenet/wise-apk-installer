/*
 * (#) net.brainage.apkinstaller.ui.SettingsActivity
 * Created on 2010. 12. 3
 */
package net.brainage.apkinstaller.ui;

import java.io.File;

import net.brainage.apkinstaller.ApkInstallerApplication;
import net.brainage.apkinstaller.R;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.util.Log;

/**
 * 
 * 
 * @author ms29.seo@gmail.com
 * @version 1.0
 */
public class SettingsActivity extends PreferenceActivity
{

    /**
     * 
     */
    private static final boolean DEBUG = ApkInstallerApplication.DEBUG;

    /**
     * 
     */
    private static final String TAG = "SettingsActivity";

    /**
     * 
     */
    public static final String SETTINGS_NAME = "apk_installer_settings";

    /* (non-Javadoc)
     * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( DEBUG ) {
            Log.d(TAG, "onCreate() --------------------");
        }

        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.setSharedPreferencesName(SETTINGS_NAME);
        preferenceManager.setSharedPreferencesMode(MODE_PRIVATE);

        addPreferencesFromResource(R.xml.preferences);

        initPreferenceScreen();
    }

    /**
     * 
     */
    private void initPreferenceScreen() {
        if ( DEBUG ) {
            Log.d(TAG, "initPreferenceScreen() --------------------");
        }
        PreferenceScreen ps = getPreferenceScreen();
        Resources res = getResources();

        // base directory preference
        final CharSequence baseDirPreferenceKey = res.getText(R.string.base_directory_key);
        if ( DEBUG ) {
            Log.d(TAG, " - baseDirPreferenceKey : " + baseDirPreferenceKey);
        }
        final EditTextPreference baseDirectoryPreference;
        baseDirectoryPreference = (EditTextPreference) ps.findPreference(baseDirPreferenceKey);
        baseDirectoryPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String oldBaseDirectory = baseDirectoryPreference.getText();
                String newBaseDirectory = newValue.toString();

                if ( DEBUG ) {
                    Log.d(TAG, "onPreferenceChange() --------------------");
                    Log.d(TAG, " - old value : " + oldBaseDirectory);
                    Log.d(TAG, " - new value : " + newBaseDirectory);
                }

                if ( oldBaseDirectory.equals(newBaseDirectory) ) {
                    if ( DEBUG ) {
                        Log.d(TAG, " - same old value and new value");
                    }
                    return false;
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
                        Log.d(TAG, " - new dir was not exist and then create mkdirs.");
                    }
                    baseDir.mkdirs();
                }

                /* save new value to preference */
                baseDirectoryPreference.setText(newBaseDirectory);

                return true;
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
