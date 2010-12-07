/*
 * (#) net.brainage.apkinstaller.ui.ApplicationsActivity
 * Created on 2010. 12. 3
 */
package net.brainage.apkinstaller.ui;

import java.util.List;

import net.brainage.apkinstaller.R;
import net.brainage.apkinstaller.data.ApplicationInfo;
import net.brainage.apkinstaller.service.ScanService;
import net.brainage.apkinstaller.util.DetachableResultReceiver;
import net.brainage.apkinstaller.util.DetachableResultReceiver.Receiver;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * 
 * @author ms29.seo@gmail.com
 * @version 1.0
 */
public class ApplicationsActivity extends ListActivity implements Receiver
{

    /**
     * 
     */
    private static final String TAG = "ApplicationsActivity";

    /**
     * 
     */
    private State mState;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);

        mState = (State) getLastNonConfigurationInstance();
        final boolean previousState = mState != null;

        if ( previousState ) {
            mState.mReceiver.setReceiver(this);
        } else {
            mState = new State();
            mState.mReceiver.setReceiver(this);
        }
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /* (non-Javadoc)
     * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    /* (non-Javadoc)
     * @see net.brainage.apkinstaller.util.DetachableResultReceiver.Receiver#onReceiveResult(int, android.os.Bundle)
     */
    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch ( resultCode ) {
            case ScanService.STATUS_RUNNING:
            break;

            case ScanService.STATUS_ERROR:
            break;

            case ScanService.STATUS_FINISHED:
            break;
        }
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onRetainNonConfigurationInstance()
     */
    @Override
    public Object onRetainNonConfigurationInstance() {
        // Clear any strong references to this Activity, we'll reattach to
        // handle events on the other side.
        mState.mReceiver.clearReceiver();
        return mState;
    }

    /**
     * 
     * 
     * @author ms29.seo@gmail.com
     * @version 1.0
     */
    private class ApplicationsAdapter extends ArrayAdapter<ApplicationInfo>
    {

        /**
         * 
         */
        private int itemLayoutResourceId;

        /**
         * @param context
         * @param itemLayoutResourceId
         * @param objects
         */
        public ApplicationsAdapter(Context context, int itemLayoutResourceId,
                List<ApplicationInfo> objects) {
            super(context, itemLayoutResourceId, objects);
            this.itemLayoutResourceId = itemLayoutResourceId;
        }

        /* (non-Javadoc)
         * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            return row;
        }

    }

    /**
     * 
     * 
     * @author ms29.seo@gmail.com
     * @version 1.0
     */
    private static class State
    {

        /**
         * 
         */
        public DetachableResultReceiver mReceiver;

        /**
         * 
         */
        public boolean mScanning = false;

        /**
         * 
         */
        public boolean mNoResults = false;

        /**
         * 
         */
        private State() {
            mReceiver = new DetachableResultReceiver(new Handler());
        }
    }

    /**
     * 
     * 
     * @author ms29.seo@gmail.com
     * @version 1.0
     */
    private class ViewHolder
    {
        public ImageView icon;
        public TextView packageName;
        public TextView packageVersion;
    }
}
