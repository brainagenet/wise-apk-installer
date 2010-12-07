/**
 * 
 */
package net.brainage.apkinstaller.util;

import android.content.Context;
import android.content.res.Resources;

/**
 * @author ms29seo
 *
 */
public final class ResourcesUtils
{

    /**
     * 
     */
    private ResourcesUtils() {
    }

    /**
     * @param context
     * @param resourceId
     * @return
     */
    public static String getText(Context context, int resourceId) {
        final Resources res = context.getResources();
        CharSequence text = res.getText(resourceId);
        return text.toString();
    }

}
