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
    private static final ApplicationArrayList INSTANCE = new ApplicationArrayList();

    /**
     * 
     */
    private ArrayList<AppInfo> list = new ArrayList<AppInfo>();

    /**
     * @return
     */
    public static ApplicationArrayList getInstance() {
        return INSTANCE;
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

    /**
     * 
     * @see java.util.ArrayList#clear()
     */
    public void clear() {
        list.clear();
    }

    /**
     * @param appInfo
     */
    public void add(AppInfo appInfo) {
        list.add(appInfo);
    }

}
