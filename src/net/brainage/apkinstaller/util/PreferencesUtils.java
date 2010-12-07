/**
 * 
 */
package net.brainage.apkinstaller.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

/**
 * @author ms29seo
 *
 */
public final class PreferencesUtils
{

    /**
     * 
     */
    private PreferencesUtils() {
    }

    /**
     * @param context
     * @param prefName
     * @param keyResourceId
     * @param defaultValueResourceId
     * @return
     */
    public static String getString(Context context, String prefName, int keyResourceId,
            int defaultValueResourceId) {
        final Resources res = context.getResources();
        String key = res.getText(keyResourceId).toString();
        String defaultValue = res.getText(defaultValueResourceId).toString();
        return getString(context, prefName, key, defaultValue);
    }

    /**
     * @param context
     * @param prefName
     * @param key
     * @param defaultValueResourceId
     * @return
     */
    public static String getString(Context context, String prefName, String key,
            int defaultValueResourceId) {
        final Resources res = context.getResources();
        String defaultValue = res.getText(defaultValueResourceId).toString();
        return getString(context, prefName, key, defaultValue);
    }

    /**
     * @param context
     * @param prefName
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Context context, String prefName, String key, String defaultValue) {
        SharedPreferences prefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return prefs.getString(key, defaultValue);
    }

}
