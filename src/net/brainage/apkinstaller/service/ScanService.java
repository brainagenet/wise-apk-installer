/*
 * (#) net.brainage.apkinstaller.service.ScanService
 * Created on 2010. 12. 3
 */
package net.brainage.apkinstaller.service;

import java.io.File;
import java.util.List;

import net.brainage.apkinstaller.ApkInstallerApplication;
import net.brainage.apkinstaller.R;
import net.brainage.apkinstaller.data.ApplicationInfo;
import net.brainage.apkinstaller.data.ApplicationList;
import net.brainage.apkinstaller.ui.SettingsActivity;
import net.brainage.apkinstaller.util.PreferencesUtils;
import net.brainage.apkinstaller.util.ResourcesUtils;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
     * logging tag
     */
    private static final String TAG = "ScanService";

    /**
     * intent extra key
     */
    public static final String EXTRA_STATUS_RECEIVER = "net.brainage.apkinstaller.extra.STATUS_RECEIVER";

    public static final int STATUS_RUNNING = 0x1;
    public static final int STATUS_ERROR = 0x2;
    public static final int STATUS_FINISHED = 0x3;

    /**
     * 
     */
    private String applicationExtension;

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

        applicationExtension = ResourcesUtils.getText(this, R.string.application_extension);
    }

    /* (non-Javadoc)
     * @see android.app.IntentService#onHandleIntent(android.content.Intent)
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if ( DEBUG ) {
            Log.d(TAG, "onHandleIntent() --------------------");
        }
        final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_STATUS_RECEIVER);
        // Announce running
        Log.d(TAG, "scan start");
        if ( receiver != null ) {
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);
        }

        // scan directory
        final Context context = this;
        String strBaseDirectory = PreferencesUtils.getString(context,
                SettingsActivity.SETTINGS_NAME, R.string.base_directory_key,
                R.string.default_base_directory);
        if ( DEBUG ) {
            Log.d(TAG, " - base directory : " + strBaseDirectory);
        }
        File baseDirectory = initializeBaseDirectory(strBaseDirectory);
        scanDirectory(baseDirectory, receiver);

        // Announce success to any surface listener
        Log.d(TAG, "scan finished");
        if ( receiver != null ) {
            receiver.send(STATUS_FINISHED, Bundle.EMPTY);
        }
    }

    /**
     * @param dir
     * @param receiver
     */
    private void scanDirectory(File dir, ResultReceiver receiver) {
        if ( !dir.exists() || !dir.isDirectory() ) {
            String errorMessage = "Invalid directory: " + dir.getAbsolutePath();
            if ( receiver != null ) {
                final Bundle bundle = new Bundle();
                bundle.putString(Intent.EXTRA_TEXT, errorMessage);
                receiver.send(STATUS_ERROR, bundle);
            } else {
                throw new IllegalArgumentException(errorMessage);
            }
        }

        File[] files = dir.listFiles();
        for ( int i = 0, l = files.length ; i < l ; i++ ) {
            File file = files[i];
            if ( file.isDirectory() ) {
                scanDirectory(file, receiver);
            } else {
                if ( !file.getName().toLowerCase().endsWith(applicationExtension) ) {
                    continue;
                }

                try {
                    ApplicationInfo appInfo = parse(file);
                    ApplicationList.getInstance().add(appInfo);
                } catch ( Exception e ) {
                    if ( receiver != null ) {
                        final Bundle bundle = new Bundle();
                        bundle.putString(Intent.EXTRA_TEXT, "");
                        receiver.send(STATUS_ERROR, bundle);
                    }
                }
            }
        }
    }

    /**
     * @param file
     * @param receiver
     * @return
     */
    private ApplicationInfo parse(File file) {
        Uri packageUri = Uri.fromFile(file);
        ApplicationInfo appInfo = parse(this, packageUri.getPath(),
                PackageManager.GET_UNINSTALLED_PACKAGES);
        appInfo.setFileUri(packageUri);
        return appInfo;
    }

    /**
     * @param context
     * @param archiveFilePath
     * @param flags
     * @return
     */
    public ApplicationInfo parse(Context context, String archiveFilePath, int flags) {
        PackageManager pm = context.getPackageManager();

        PackageInfo pi = pm.getPackageArchiveInfo(archiveFilePath, flags);

        ApplicationInfo appInfo = new ApplicationInfo();

        // get packageName
        String packageName = pi.packageName;
        appInfo.setPackageName(packageName);

        // get version informations
        int versionCode = pi.versionCode;
        appInfo.setVersionCode(versionCode);
        appInfo.setVersionName(pi.versionName);

        // get package status
        appInfo.setStatus(0); // installable
        if ( isPackageAlreadyInstalled(context, packageName) ) {
            appInfo.setStatus(1); // installed
            try {
                PackageInfo pkgInfo = pm.getPackageInfo(packageName,
                        PackageManager.GET_UNINSTALLED_PACKAGES);
                if ( versionCode > pkgInfo.versionCode ) {
                    appInfo.setStatus(2); // updatable
                }
            } catch ( NameNotFoundException e ) {
            }
        }

        Resources ctxRes = context.getResources();

        AssetManager assetManager = new AssetManager();
        assetManager.addAssetPath(archiveFilePath);

        Resources pkgRes = new Resources(assetManager, ctxRes.getDisplayMetrics(),
                ctxRes.getConfiguration());

        // get Package label
        CharSequence label = null;
        if ( pi.applicationInfo.labelRes != 0 ) {
            try {
                label = pkgRes.getText(pi.applicationInfo.labelRes);
            } catch ( Resources.NotFoundException e ) {
            }
        }

        if ( label == null ) {
            label = (pi.applicationInfo.nonLocalizedLabel != null) ? pi.applicationInfo.nonLocalizedLabel
                    : pi.applicationInfo.packageName;
        }
        appInfo.setName(label.toString());

        // get package icon drawable
        Drawable icon = null;
        if ( pi.applicationInfo.icon != 0 ) {
            try {
                icon = pkgRes.getDrawable(pi.applicationInfo.icon);
            } catch ( Resources.NotFoundException e ) {
            }
        }

        if ( icon == null ) {
            icon = context.getPackageManager().getDefaultActivityIcon();
        }
        appInfo.setIcon(icon);

        return appInfo;
    }

    /**
     * @param context
     * @param packageName
     * @return
     */
    public boolean isPackageAlreadyInstalled(Context context, String packageName) {
        List<PackageInfo> installedList = context.getPackageManager().getInstalledPackages(
                PackageManager.GET_UNINSTALLED_PACKAGES);
        int installedListSize = installedList.size();
        for ( int i = 0 ; i < installedListSize ; i++ ) {
            PackageInfo tmp = installedList.get(i);
            if ( packageName.equalsIgnoreCase(tmp.packageName) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param strBaseDirectory
     * @return
     */
    private File initializeBaseDirectory(String strBaseDirectory) {
        File dir = new File(strBaseDirectory);
        if ( !dir.exists() ) {
            dir.mkdirs();
        }
        return dir;
    }

}
