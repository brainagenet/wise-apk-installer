/*
 * (#) net.brainage.app.apkinstaller.ApplicationListActivity
 * Created on 2010. 10. 11.
 */
package net.brainage.app.apkinstaller;

import java.io.File;
import java.util.ArrayList;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * 
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
    private ArrayList<AppInfo> appList;

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

        appList = new ArrayList<AppInfo>();

        adapter = new ApplicationListAdapter(this, R.layout.application_list_item, appList);
        setListAdapter(adapter);

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

    /**
     * 
     * 
     * @author ntmyoungseok.seo@lge.com
     */
    class ApplicationPackageScanService extends Service
    {

        /**
         * 
         */
        private static final String STAG = "ApplicationPackageScanService";

        /**
         * 
         */
        private File rootDirectory;

        /**
         * 
         */
        private ApplicationPackageScanTask task;

        /**
         * 
         * @see android.app.Service#onCreate()
         */
        @Override
        public void onCreate() {
            if ( AppConstants.DEBUG ) {
                Log.d(STAG, "start onCreate() ...");
            }
            rootDirectory = new File(AppConstants.SCAN_ROOT_DIRECTORY);
            if ( !rootDirectory.exists() ) {
                if ( AppConstants.DEBUG ) {
                    Log.d(STAG, "    - " + rootDirectory.getAbsolutePath() + " dosenot exist...");
                }
                rootDirectory.mkdirs();
            }
        }

        /**
         * 
         * @param intent
         * @param flags
         * @param startId
         * @return
         * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
         */
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            if ( AppConstants.DEBUG ) {
                Log.d(STAG, "start onStartCommand() ...");
            }
            refreshApplications();
            return Service.START_NOT_STICKY;
        }

        /**
         * 
         * @param intent
         * @return
         * @see android.app.Service#onBind(android.content.Intent)
         */
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        /**
         * 
         */
        private void refreshApplications() {
            if ( AppConstants.DEBUG ) {
                Log.d(STAG, "start refreshApplications() ...");
            }
            if ( task == null || AsyncTask.Status.FINISHED.equals(task.getStatus()) ) {
                task = new ApplicationPackageScanTask(this.rootDirectory);
                if ( AppConstants.DEBUG ) {
                    Log.d(STAG, "    - execute ApplicationPackageScanTask...");
                }
                task.execute((Void[]) null);
            }
        }

    };

    /**
     * 
     */
    class ApplicationPackageScanTask extends AsyncTask<Void, File, Void>
    {

        private static final String TTAG = "ApplicationPackageScanTask";

        /**
         * 
         */
        private File rootDirectory;

        /**
         * @param rootDirectory
         */
        public ApplicationPackageScanTask(File rootDirectory) {
            this.rootDirectory = rootDirectory;
        }

        /**
         * 
         * @param params
         * @return
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Void doInBackground(Void... params) {
            if ( AppConstants.DEBUG ) {
                Log.d(TTAG, "start doInBackground() ...");
            }
            File[] fileList = rootDirectory.listFiles();
            for ( int i = 0, l = fileList.length ; i < l ; i++ ) {
                File f = fileList[i];
                if ( f.getName().toLowerCase().endsWith(".apk") ) {
                    if ( AppConstants.DEBUG ) {
                        Log.d(TTAG, "    - " + f.getName());
                    }
                    publishProgress(f);
                }
            }
            return null;
        }

        /**
         * 
         * @param values
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(File... values) {
            if ( AppConstants.DEBUG ) {
                Log.d(TTAG, "start onProgressUpdate() ...");
            }
            File apkFile = values[0];

            PackageManager pm = getPackageManager();
            PackageInfo packageInfo = pm.getPackageArchiveInfo(apkFile.getAbsolutePath(), 0);

            AppInfo appInfo = new AppInfo();
            appInfo.setFileUri(Uri.fromFile(apkFile));
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setName(packageInfo.applicationInfo.name);
            appInfo.setVersionCode(packageInfo.versionCode);
            appInfo.setVersionName(packageInfo.versionName);
            appInfo.setIcon(packageInfo.applicationInfo.loadIcon(pm));
            if ( AppConstants.DEBUG ) {
                Log.d(TTAG, "    - package name  : " + appInfo.getPackageName());
                Log.d(TTAG, "    - name          : " + appInfo.getName());
                Log.d(TTAG, "    - version code  : " + appInfo.getVersionCode());
                Log.d(TTAG, "    - version name  : " + appInfo.getVersionName());
            }

            // 이미 설치되어 있는지 확인
            // Install 여부 확인
            PackageInfo installedPackageInfo;
            try {
                installedPackageInfo = pm.getPackageInfo(appInfo.getPackageName(), 0);
                if ( installedPackageInfo != null ) {
                    appInfo.setWasInstalled(true);
                }

                // 이미 설치되어 있는 경우 버전 코드 확인
                // update 여부 확인
                if ( appInfo.getVersionCode() < installedPackageInfo.versionCode ) {
                    appInfo.setUpdatable(true);
                }
            } catch ( NameNotFoundException e ) {
            }

            if ( AppConstants.DEBUG ) {
                Log.d(TTAG, "    - was installed : " + appInfo.wasInstalled());
                Log.d(TTAG, "    - updatable     : " + appInfo.isUpdatable());
            }

            appList.add(appInfo);
        }

        /**
         * 
         * @param result
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(Void result) {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(AppConstants.ACTION_REFRESHED_APPLIST);
            sendBroadcast(broadcastIntent);
        }

    }

}