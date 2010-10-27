/*
 * (#) net.brainage.apkinstaller.util.UiUtil
 * Created on 2010. 10. 27.
 */
package net.brainage.apkinstaller.util;

import android.content.Context;
import android.content.Intent;

/**
 * 
 * 
 * @author ntmyoungseok.seo@lge.com
 */
public final class UiUtil
{

    /**
     * 
     */
    private UiUtil() {
    }

    /**
     * @param context
     */
    public static void goSettings(Context context) {
        Intent i = new Intent("net.brainage.apkinstaller.action.SETTINGS");
        context.startActivity(i);
    }

}
