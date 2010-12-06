/**
 * 
 */
package net.brainage.apkinstaller;

import android.app.Application;
import android.util.Log;

/**
 * @author ms29seo
 *
 */
public class ApkInstallerApplication extends Application
{

    /**
     * 
     */
    public static final boolean DEBUG = true;

    /**
     * 
     */
    private static final String TAG = "ApkInstallerApplication";

    /**
     * 
     */
    private static ApkInstallerApplication singleton;

    /**
     * @return
     */
    public static ApkInstallerApplication getInstance() {
        return singleton;
    }

    /* (non-Javadoc)
     * @see android.app.Application#onCreate()
     */
    @Override
    public final void onCreate() {
        super.onCreate();
        if ( DEBUG ) {
            Log.d(TAG, "onCreate() --------------------");
        }
        singleton = this;
    }

}
