/*
 * (#) net.brainage.app.apkinstaller.ApplicationPackageScanService
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
package net.brainage.app.apkinstaller;

import java.io.File;

import net.brainage.app.apkinstaller.util.PackageUtil;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

/**
 * 
 * 
 * @author ms29.seo@gmail.com
 * @version 1.0
 */
public class ApplicationPackageScanService extends Service
{

    /**
     * LOGCAT TAG
     */
    private static final String TAG = "ApplicationPackageScanService";

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
    private ApplicationArrayList appList;

    /**
     * 
     * @see android.app.Service#onCreate()
     */
    @Override
    public void onCreate() {
        if ( AppConstants.DEBUG ) {
            Log.d(TAG, "start onCreate() ...");
        }

        appList = ApplicationArrayList.getInstance();

        rootDirectory = new File(getResources().getString(R.string.base_app_directory));
        if ( !rootDirectory.exists() ) {
            if ( AppConstants.DEBUG ) {
                Log.d(TAG, "    - " + rootDirectory.getAbsolutePath()
                        + " dosenot exist...");
            }
            rootDirectory.mkdirs();
        }
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
        return Service.START_NOT_STICKY;
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
                    if ( f.getName().toLowerCase().endsWith(
                            AppConstants.ANDROID_PACKAGE_FILE_EXT) ) {
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
            File apkFile = values[0];

            // PackageManager pm = getPackageManager();
            // PackageInfo packageInfo = pm.getPackageArchiveInfo(apkFile.getAbsolutePath(), 0);

            Uri packageUri = Uri.fromFile(apkFile);
            AppInfo appInfo = PackageUtil.parse(ApplicationPackageScanService.this,
                    packageUri.getPath(), PackageManager.GET_UNINSTALLED_PACKAGES);
            appInfo.setFileUri(packageUri);

            if ( AppConstants.DEBUG ) {
                Log.d(TAG, "    - package uri   : " + appInfo.getFileUri().getPath());
                Log.d(TAG, "    - package name  : " + appInfo.getPackageName());
                Log.d(TAG, "    - name          : " + appInfo.getName());
                Log.d(TAG, "    - version code  : " + appInfo.getVersionCode());
                Log.d(TAG, "    - version name  : " + appInfo.getVersionName());
                Log.d(TAG, "    - was installed : " + appInfo.wasInstalled());
                Log.d(TAG, "    - updatable     : " + appInfo.isUpdatable());
            }

            /*
            appInfo.setFileUri(Uri.fromFile(apkFile));
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setName("");
            appInfo.setVersionCode(packageInfo.versionCode);
            appInfo.setVersionName(packageInfo.versionName);
            appInfo.setIcon(packageInfo.applicationInfo.loadIcon(pm));
            if ( AppConstants.DEBUG ) {
                Log.d(TAG, "    - package name  : " + appInfo.getPackageName());
                Log.d(TAG, "    - name          : " + appInfo.getName());
                Log.d(TAG, "    - version code  : " + appInfo.getVersionCode());
                Log.d(TAG, "    - version name  : " + appInfo.getVersionName());
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
                Log.d(TAG, "    - was installed : " + appInfo.wasInstalled());
                Log.d(TAG, "    - updatable     : " + appInfo.isUpdatable());
            }
            */

            appList.add(appInfo);
        }

        /**
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
