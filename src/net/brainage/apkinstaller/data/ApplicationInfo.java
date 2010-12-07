/**
 * 
 */
package net.brainage.apkinstaller.data;

import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * @author ms29seo
 *
 */
public class ApplicationInfo
{

    private String packageName;

    private String name;

    private int versionCode;

    private String versionName;

    private int status;

    private Drawable icon;

    private Uri fileUri;

    /**
     * 
     */
    public ApplicationInfo() {
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

}
