/*
 * (#) net.brainage.app.apkinstaller.ApplicationArrayList
 * Created on 2010. 10. 11.
 */
package net.brainage.app.apkinstaller;

import java.util.ArrayList;

/**
 * 
 * 
 * @author ntmyoungseok.seo@lge.com
 */
public class ApplicationArrayList
{

    /**
     * 
     */
    private static ApplicationArrayList _instance;

    /**
     * 
     */
    private final ArrayList<AppInfo> list = new ArrayList<AppInfo>();

    /**
     * @return
     */
    public static ApplicationArrayList getInstance() {
        if ( _instance == null ) {
            _instance = new ApplicationArrayList();
        }
        return _instance;
    }

    /**
     * 
     */
    private ApplicationArrayList() {
    }

    /**
     * @return
     */
    public ArrayList<AppInfo> getList() {
        return list;
    }

}
