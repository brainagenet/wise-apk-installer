/*
 * (#) net.brainage.apkinstaller.AppScanService
 * Created on 2010. 10. 11.
 *
 * 상기 프로그램에 대한 저작권을 포함한 지적재산권은 "와이즈스톤닷넷"에 있으며,
 * "와이즈스톤닷넷"이 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 
 * 배포는 엄격히 금지되며, "와이즈스톤닷넷"의 지적재산권 침해에 해당됩니다.
 * 
 * You are strictly prohibited to copy, disclose, distribute, modify, or use
 * this program in part or as a whole without the prior written consent of 
 * wisestone.net. wisestone.net, owns the intellectual property rights in 
 * and to this program.
 * 
 * (Copyright ⓒ 1997-2010 wisestone.net. All Rights Reserved| Confidential)
 */
package net.brainage.apkinstaller.service;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import net.brainage.apkinstaller.AppConstants;
import net.brainage.apkinstaller.R;
import net.brainage.apkinstaller.ui.SettingsActivity;
import net.brainage.apkinstaller.ui.adapter.AppInfo;
import net.brainage.apkinstaller.util.AppArrayList;
import net.brainage.apkinstaller.util.PackageUtil;

/**
 * 
 * 
 * @author ms29.seo@gmail.com
 * @version 1.0
 */
public class AppScanService extends Service
{

    /**
     * LOGCAT TAG
     */
    private static final String TAG = "AppScanService";

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
     */
    private AppArrayList appList;

    /**
     * 
     * @see android.app.Service#onCreate()
     */
    @Override
    public void onCreate() {
        if ( AppConstants.DEBUG ) {
            Log.d(TAG, "start onCreate() ...");
        }

        appList = AppArrayList.getInstance();

        String baseDirectory = getBaseDirectory();
        if ( AppConstants.DEBUG ) {
            Log.d(TAG, "    base directory = " + baseDirectory);
        }

        rootDirectory = new File(baseDirectory);
        if ( !rootDirectory.exists() ) {
            if ( AppConstants.DEBUG ) {
                Log.d(TAG, "    - " + rootDirectory.getAbsolutePath() + " dosenot exist...");
            }
            rootDirectory.mkdirs();
        }
    }

    /**
     * @return
     */
    private String getBaseDirectory() {
        SharedPreferences pref = getSharedPreferences(SettingsActivity.SETTINGS_NAME, 0);
        String defaultBaseDirectory = getResources().getString(R.string.default_base_directory);
        String baseDirectory = pref.getString(getResources().getText(R.string.base_directory_key)
                .toString(), defaultBaseDirectory);
        return baseDirectory;
    }

    /**
     * @param intent
     * @param flags
     * @param startId
     * @return
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if ( AppConstants.DEBUG ) {
            Log.d(TAG, "start onStartCommand() ...");
        }
        refreshApplications();
        return Service.START_STICKY;
    }

    /**
     * 
     */
    private void refreshApplications() {
        if ( AppConstants.DEBUG ) {
            Log.d(TAG, "start refreshApplications() ...");
        }
        if ( task == null || AsyncTask.Status.FINISHED.equals(task.getStatus()) ) {
            task = new ApplicationPackageScanTask();
            if ( AppConstants.DEBUG ) {
                Log.d(TAG, "    - execute ApplicationPackageScanTask...");
            }
            task.execute((Void[]) null);
        }
    }

    /**
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
     * 
     * @author ms29.seo@gmail.com
     * @version 1.0
     */
    private class ApplicationPackageScanTask extends AsyncTask<Void, File, Void>
    {

        /**
         * 
         */
        private static final String TAG = "ApplicationPackageScanTask";

        /**
         * @param params
         * @return
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Void doInBackground(Void... params) {
            if ( AppConstants.DEBUG ) {
                Log.d(TAG, "start doInBackground() ...");
            }
            scan(rootDirectory);
            return null;
        }

        /**
         * @param dir
         */
        private void scan(File dir) {
            if ( !dir.exists() || !dir.isDirectory() ) {
                throw new RuntimeException();
            }

            File[] fileList = dir.listFiles();
            for ( int i = 0, l = fileList.length ; i < l ; i++ ) {
                File f = fileList[i];
                if ( f.isDirectory() ) {
                    scan(f);
                } else {
                    if ( f.getName().toLowerCase().endsWith(AppConstants.ANDROID_PACKAGE_FILE_EXT) ) {
                        if ( AppConstants.DEBUG ) {
                            Log.d(TAG, "    - " + f.getName());
                        }
                        publishProgress(f);
                    }
                }
            }
        }

        /**
         * @param values
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(File... values) {
            if ( AppConstants.DEBUG ) {
                Log.d(TAG, "start onProgressUpdate() ...");
            }

            try {
                File apkFile = values[0];

                Uri packageUri = Uri.fromFile(apkFile);
                AppInfo appInfo = PackageUtil.parse(AppScanService.this, packageUri.getPath(),
                        PackageManager.GET_UNINSTALLED_PACKAGES);
                appInfo.setFileUri(packageUri);
                appList.add(appInfo);
            } catch ( Exception e ) {
                // TODO 오류가 발생한 파일에 대하여 정보를 Toast로 화면에 표시한다.
            }
        }

        /**
         * @param result
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(Void result) {
            Intent broadcastIntent = new Intent(AppConstants.ACTION_REFRESHED_APPLIST);
            sendBroadcast(broadcastIntent);
            stopSelf();
        }

    }

}
