/*
 * (#) net.brainage.app.apkinstaller.ViewHolder
 * Created on 2010. 10. 11.
 */
package net.brainage.app.apkinstaller;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * 
 * @author ntmyoungseok.seo@lge.com
 */
public class ViewHolder
{

    /**
     * 
     */
    private ImageView icon;

    /**
     * 
     */
    private TextView name;

    /**
     * 
     */
    private TextView version;

    /**
     * 
     */
    private Button actionButton;

    /**
     * 
     */
    public ViewHolder() {
    }

    /**
     * @param icon
     * @param name
     * @param version
     * @param actionButton
     */
    public ViewHolder(ImageView icon, TextView name, TextView version, Button actionButton) {
        this.icon = icon;
        this.name = name;
        this.version = version;
        this.actionButton = actionButton;
    }

    /**
     * @return the icon
     */
    public ImageView getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    /**
     * @return the name
     */
    public TextView getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(TextView name) {
        this.name = name;
    }

    /**
     * @return the version
     */
    public TextView getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(TextView version) {
        this.version = version;
    }

    /**
     * @return the actionButton
     */
    public Button getActionButton() {
        return actionButton;
    }

    /**
     * @param actionButton the actionButton to set
     */
    public void setActionButton(Button actionButton) {
        this.actionButton = actionButton;
    }

}
