package com.example.getinstalledapps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    Context mContext;
    PackageManager mPackageManager;
    List<ApplicationInfo> mParentList;
    List<List<String>> mChildList;

    public CustomExpandableListAdapter(Context context,
                                       PackageManager packageManager,
                                       List<ApplicationInfo> parentList,
                                       List<List<String>> childList) {
        mContext = context;
        mPackageManager = packageManager;
        mParentList = parentList;
        mChildList = childList;
    }

    @Override
    public int getGroupCount() {
        return mParentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.parent_layout, parent, false);
        ImageView icon = convertView.findViewById(R.id.icon);
        TextView appName = convertView.findViewById(R.id.app_name);

        icon.setImageDrawable(mParentList.get(groupPosition).loadIcon(mPackageManager));
        appName.setText(mParentList.get(groupPosition).loadLabel(mPackageManager));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.child_layout, parent, false);
        TextView text = convertView.findViewById(R.id.text);
        text.setText(mChildList.get(groupPosition).get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
