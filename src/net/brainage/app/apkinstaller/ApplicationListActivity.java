/*
 * (#) net.brainage.app.apkinstaller.ApplicationListActivity
 * Created on 2010. 10. 11.
 */
package net.brainage.app.apkinstaller;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 설치할 APK 파일의 목록을 표시한다.
 * 
 * @author ntmyoungseok.seo@lge.com
 */
public class ApplicationListActivity extends ListActivity {

    /**
     * 
     */
    private static final String TAG = "ApplicationListActivity";

    /**
     * 
     */
    private ApplicationArrayList appList;

    /**
     * 
     */
    private ApplicationListAdapter adapter;

    /**
     * 
     */
    private ProgressBar refreshProgress;

    /**
     * 
     */
    private ImageButton refreshButton;

    /**
     * 
     * @param savedInstanceState
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
    }

    /**
     * 
     */
    private void initActivity() {
        setContentView(R.layout.application_list);

        ( (TextView) findViewById(R.id.title_text) ).setText(getTitle());

        refreshProgress = (ProgressBar) findViewById(R.id.title_refresh_progress);
        refreshButton = (ImageButton) findViewById(R.id.btn_title_refresh);

        appList = ApplicationArrayList.getInstance();

        adapter = new ApplicationListAdapter(this, R.layout.application_list_item,
                appList.getList());
        setListAdapter(adapter);
    }

    /**
     * 
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(AppConstants.ACTION_REFRESHED_APPLIST);
        registerReceiver(applicationRefreshReceiver, filter);

        refreshAppList();
    }

    /**
     * 
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        unregisterReceiver(applicationRefreshReceiver);

        super.onPause();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop() {
        if ( AppConstants.DEBUG ) {
            Log.d(TAG, "onStop() ---------------------------");
        }
        stopService(new Intent(this, ApplicationPackageScanService.class));
        super.onStop();
    }

    /**
     * @param newConfig
     * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if ( newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ) {
            if ( AppConstants.DEBUG ) {
                Log.d(TAG, "onConfigurationChanged() --------------");
                Log.d(TAG, "    Configuration.ORIENTATION_LANDSCAPE");
            }
            initActivity();
        }
    }

    /**
     * @param v
     */
    public void onRefreshClick(View v) {
        refreshAppList();
    }

    /**
     * 
     */
    private void refreshAppList() {
        if ( AppConstants.DEBUG ) {
            Log.d(TAG, "Refresh Application List...");
        }

        reloadNowPlaying(true);

        appList.clear();

        Intent serviceIntent = new Intent(this, ApplicationPackageScanService.class);
        startService(serviceIntent);
    }

    /**
     * @param flag
     */
    private void reloadNowPlaying(boolean flag) {
        if ( flag ) {
            refreshProgress.setVisibility(View.VISIBLE);
            refreshButton.setVisibility(View.GONE);
        } else {
            refreshProgress.setVisibility(View.GONE);
            refreshButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 
     */
    BroadcastReceiver applicationRefreshReceiver = new BroadcastReceiver() {
        /**
         * 
         * @param context
         * @param intent
         * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.notifyDataSetChanged();

            reloadNowPlaying(false);
        }
    };

}
