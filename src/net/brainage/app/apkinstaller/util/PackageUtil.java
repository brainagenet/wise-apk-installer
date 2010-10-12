/*
 * (#) net.brainage.app.apkinstaller.util.PackageUtil
 * Created on 2010. 10. 12.
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
package net.brainage.app.apkinstaller.util;

import java.util.List;

import net.brainage.app.apkinstaller.AppInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;

/**
 * 
 * 
 * @author ms29.seo@gmail.com
 * @version 1.0
 */
public class PackageUtil
{

    /**
     * 
     */
    private PackageUtil() {
    }

    /**
     * @param context
     * @param archiveFilePath
     * @param flags
     * @return
     */
    public static AppInfo parse(Context context, String archiveFilePath, int flags) {
        PackageManager pm = context.getPackageManager();

        PackageInfo pi = pm.getPackageArchiveInfo(archiveFilePath, flags);

        AppInfo appInfo = new AppInfo();

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

        // AssetManager assetManager = new AssetManager();
        // package installer 참조

        return appInfo;
    }

    /**
     * @param context
     * @param pkgName
     * @return
     */
    public static boolean isPackageAlreadyInstalled(Context context, String pkgName) {
        List<PackageInfo> installedList = context.getPackageManager().getInstalledPackages(
                PackageManager.GET_UNINSTALLED_PACKAGES);
        int installedListSize = installedList.size();
        for ( int i = 0 ; i < installedListSize ; i++ ) {
            PackageInfo tmp = installedList.get(i);
            if ( pkgName.equalsIgnoreCase(tmp.packageName) ) {
                return true;
            }
        }
        return false;
    }

}
