/*
 * (#) net.brainage.app.apkinstaller.AppInfo
 * Created on 2010. 10. 11.
 */
package net.brainage.app.apkinstaller;

import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * 
 * 
 * @author ntmyoungseok.seo@lge.com
 */
public class AppInfo
{

    /**
     * 
     */
    private String packageName;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private int versionCode;

    /**
     * 
     */
    private String versionName;

    /**
     * 
     */
    private Drawable icon;

    /**
     * 
     */
    private boolean wasInstalled = false;

    /**
     * 
     */
    private boolean updatable = false;

    /**
     * package status : 0 - installable, 1 - installed, 2 - updatable
     */
    private int status;

    /**
     * 
     */
    private Uri fileUri;

    /**
     * 
     */
    public AppInfo() {
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the versionCode
     */
    public int getVersionCode() {
        return versionCode;
    }

    /**
     * @param versionCode the versionCode to set
     */
    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    /**
     * @return the versionName
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * @param versionName the versionName to set
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     * @return the icon
     */
    public Drawable getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    /**
     * @return the wasInstalled
     */
    public boolean wasInstalled() {
        return wasInstalled;
    }

    /**
     * @param wasInstalled the wasInstalled to set
     */
    public void setWasInstalled(boolean wasInstalled) {
        this.wasInstalled = wasInstalled;
    }

    /**
     * @return the updatable
     */
    public boolean isUpdatable() {
        return updatable;
    }

    /**
     * @param updatable the updatable to set
     */
    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    /**
     * @return the fileUri
     */
    public Uri getFileUri() {
        return fileUri;
    }

    /**
     * @param fileUri the fileUri to set
     */
    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

}
