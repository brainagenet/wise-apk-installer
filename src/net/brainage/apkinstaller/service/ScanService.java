/*
 * (#) net.brainage.apkinstaller.service.ScanService
 * Created on 2010. 12. 3
 */
package net.brainage.apkinstaller.service;

import android.app.IntentService;
import android.content.Intent;

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
    private static final String TAG = "ScanService";

    /**
     * 
     */
    public ScanService() {
        super(TAG);
    }

    /* (non-Javadoc)
     * @see android.app.IntentService#onHandleIntent(android.content.Intent)
     */
    @Override
    protected void onHandleIntent(Intent intent) {
    }

}
