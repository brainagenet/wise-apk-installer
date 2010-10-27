/*
 * (#) net.brainage.apkinstaller.AppArrayList
 * Created on 2010. 10. 11.
 */
package net.brainage.apkinstaller.util;

import java.util.ArrayList;

import net.brainage.apkinstaller.ui.adapter.AppInfo;

/**
 * 
 * 
 * @author ntmyoungseok.seo@lge.com
 */
public class AppArrayList
{

    /**
     * 
     */
    private static final AppArrayList INSTANCE = new AppArrayList();

    /**
     * 
     */
    private ArrayList<AppInfo> list = new ArrayList<AppInfo>();

    /**
     * @return
     */
    public static AppArrayList getInstance() {
        return INSTANCE;
    }

    /**
     * 
     */
    private AppArrayList() {
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
