/*
 * (#) net.brainage.app.apkinstaller.ApplicationListAdapter
 * Created on 2010. 10. 11.
 */
package net.brainage.app.apkinstaller;

import java.util.List;

import net.brainage.apkinstaller.R;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * 
 * @author ntmyoungseok.seo@lge.com
 */
public class ApplicationListAdapter extends ArrayAdapter<AppInfo>
{

    /**
     * 
     */
    private Context context;

    /**
     * 
     */
    private int itemLayoutResourceId;

    /**
     * @param context
     * @param itemLayoutResourceId
     * @param objects
     */
    public ApplicationListAdapter(Context context, int itemLayoutResourceId,
                                  List<AppInfo> objects) {
        super(context, itemLayoutResourceId, objects);

        this.context = context;
        this.itemLayoutResourceId = itemLayoutResourceId;
    }

    /**
     * 
     * @param position
     * @param convertView
     * @param parent
     * @return
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if ( row == null ) {
            row = LayoutInflater.from(context).inflate(itemLayoutResourceId, null);

            ImageView icon = (ImageView) row.findViewById(R.id.appIcon);
            TextView name = (TextView) row.findViewById(R.id.appName);
            TextView version = (TextView) row.findViewById(R.id.appVersion);
            Button action = (Button) row.findViewById(R.id.actionButton);

            ViewHolder holder = new ViewHolder(icon, name, version, action);
            row.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) row.getTag();

        final AppInfo info = getItem(position);

        holder.getIcon().setImageDrawable(info.getIcon());
        holder.getName().setText(info.getName());
        holder.getVersion().setText(info.getVersionName());

        holder.getActionButton().setEnabled(true);
        switch ( info.getStatus() ) {
            case 0:
                // installable
                holder.getActionButton().setText("Install");
            break;

            case 1:
                // installed
                holder.getActionButton().setEnabled(false);
                holder.getActionButton().setText("Installed");
            break;

            case 2:
                // updatable
                holder.getActionButton().setText("Update");
            break;
        }
        
        holder.getActionButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(info.getFileUri(),
                        "application/vnd.android.package-archive");
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setClassName("com.android.packageinstaller",
                        "com.android.packageinstaller.PackageInstallerActivity");
                context.startActivity(i);
            }
        });

        return row;
    }

}
