/*
 * (#) net.brainage.apkinstaller.util.DetachableResultReceiver
 * Created on 2010. 12. 3
 */
package net.brainage.apkinstaller.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * 
 * 
 * @author ms29.seo@gmail.com
 * @version 1.0
 */
public class DetachableResultReceiver extends ResultReceiver
{

    /**
     * 
     */
    private static final String TAG = "DetachableResultReceiver";

    /**
     * 
     */
    private Receiver mReceiver;

    /**
     * @param handler
     */
    public DetachableResultReceiver(Handler handler) {
        super(handler);
    }

    /**
     * @param receiver
     */
    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    /**
     * 
     */
    public void clearReceiver() {
        mReceiver = null;
    }

    /* (non-Javadoc)
     * @see android.os.ResultReceiver#onReceiveResult(int, android.os.Bundle)
     */
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if ( mReceiver != null ) {
            mReceiver.onReceiveResult(resultCode, resultData);
        } else {
            Log.w(TAG,
                    "Dropping result on floor for code " + resultCode + ": "
                            + resultData.toString());
        }
    }

    /**
     * 
     * 
     * @author ms29.seo@gmail.com
     * @version 1.0
     */
    public interface Receiver
    {
        /**
         * @param resultCode
         * @param resultData
         */
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

}
