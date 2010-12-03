/*
 * (#) net.brainage.app.apkinstaller.ApplicationListActivity
 * Created on 2010. 10. 11.
 */
package net.brainage.app.apkinstaller;

import net.brainage.apkinstaller.R;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

/**
 * 설치할 APK 파일의 목록을 표시한다.
 * 
 * @author ntmyoungseok.seo@lge.com
 */
public class ApplicationListActivity extends ListActivity
{

    /**
     * 
     */
    private static final String TAG = "ApplicationListActivity";

    /**
     * 
     */
    private static final int DIALOG_LOADING = 10100;

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
     * @param savedInstanceState
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_list);

        appList = ApplicationArrayList.getInstance();

        adapter = new ApplicationListAdapter(this, R.layout.application_list_item,
                appList.getList());
        setListAdapter(adapter);

        refreshAppList();
    }
    
    /**
     * 
     * @see android.app.Activity#onRestart()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        
        refreshAppList();
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

    /**
     * 
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * @param id
     * @return
     * @see android.app.Activity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch ( id ) {
            case DIALOG_LOADING:
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Loading...");
                return dialog;
        }

        return null;
    }

    /**
     * 
     */
    private void refreshAppList() {
        if ( AppConstants.DEBUG ) {
            Log.d(TAG, "Refresh Application List...");
        }

        showDialog(DIALOG_LOADING);

        appList.clear();

        Intent serviceIntent = new Intent(this, ApplicationPackageScanService.class);
        startService(serviceIntent);
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

            dismissDialog(DIALOG_LOADING);
        }
    };

}
