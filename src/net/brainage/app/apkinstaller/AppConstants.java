/*
 * (#) net.brainage.app.apkinstaller.AppConstants
 * Created on 2010. 10. 11.
 */
package net.brainage.app.apkinstaller;

/**
 * 
 * 
 * @author ntmyoungseok.seo@lge.com
 */
public final class AppConstants
{

    /**
     * 
     */
    private AppConstants() {
    }

    /**
     * 디버그 여부 상수
     */
    public static final boolean DEBUG = true;

    /**
     * 
     */
    public static final String SCAN_ROOT_DIRECTORY = "/mnt/sdcard/apps";
    
    
    /**
     * 
     */
    public static final String ACTION_NEW_APPLICATION_FOUND = "net.brainage.apkinstaller.action.NEW_APPLICATION_FOUND";
    
    public static final String ACTION_REFRESHED_APPLIST = "net.brainage.apkinstaller.action.REFRESHED_APPLIST";

}
