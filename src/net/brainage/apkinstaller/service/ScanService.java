/*
 * (#) net.brainage.apkinstaller.service.ScanService
 * Created on 2010. 12. 3
 */
package net.brainage.apkinstaller.service;

import net.brainage.apkinstaller.ApkInstallerApplication;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * 
 * 
 * @author ms29.seo@gmail.com
 * @version 1.0
 */
public class ScanService extends IntentService
{

    /**
     * 
     */
    private static final boolean DEBUG = ApkInstallerApplication.DEBUG;

    /**
     * 
     */
    private static final String TAG = "ScanService";

    public static final String EXTRA_STATUS_RECEIVER = "net.brainage.apkinstaller.extra.STATUS_RECEIVER";

    public static final int STATUS_RUNNING = 0x1;
    public static final int STATUS_ERROR = 0x2;
    public static final int STATUS_FINISHED = 0x3;

    /**
     * 
     */
    public ScanService() {
        super(TAG);
    }

    /* (non-Javadoc)
     * @see android.app.IntentService#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if ( DEBUG ) {
            Log.d(TAG, "onCreate() --------------------");
        }
    }

    /* (non-Javadoc)
     * @see android.app.IntentService#onHandleIntent(android.content.Intent)
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_STATUS_RECEIVER);
        // Announce running
        Log.d(TAG, "scan start");
        if ( receiver != null ) {
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);
        }
        
        // scan directory

        // Announce success to any surface listener
        Log.d(TAG, "scan finished");
        if ( receiver != null ) {
            receiver.send(STATUS_FINISHED, Bundle.EMPTY);
        }
    }

}
