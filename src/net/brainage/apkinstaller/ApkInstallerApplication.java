/*
 * (#) net.brainage.apkinstaller.ApkInstallerApplication
 * Created on 2010. 10. 27.
 */
package net.brainage.apkinstaller;

import android.app.Application;
import android.util.Log;

/**
 * 
 * 
 * @author ntmyoungseok.seo@lge.com
 * @version 1.0
 */
public class ApkInstallerApplication extends Application
{

    /**
     * 
     */
    private static final boolean DEBUG = AppConstants.DEBUG;

    /**
     * 
     */
    private static final String TAG = "ApkInstallerApplication";

    /**
     * 
     */
    private static ApkInstallerApplication instance;

    /**
     * 
     */
    public static ApkInstallerApplication getInstance() {
        return instance;
    }

    /**
     * 
     * @see android.app.Application#onCreate()
     */
    @Override
    public final void onCreate() {
        super.onCreate();
        if ( DEBUG ) {
            Log.d(TAG, "onCreate() -----------------------");
        }
        instance = this;
    }

}
