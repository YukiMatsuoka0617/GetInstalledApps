package com.example.getinstalledapps;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public class MakeDataForExpandableList {
    private static MakeDataForExpandableList mSingleton;
    private List<ApplicationInfo> mApplicationInfoList;
    private List<ApplicationInfo> mParentList;
    private List<List<String>> mChildList;

    private MakeDataForExpandableList(Context context) {
        mSingleton = null;
        mApplicationInfoList =
                context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        mParentList = MakeParentList();
        mChildList = MakeChildList();
    }

    public static MakeDataForExpandableList getInstance(Context context) {
        if (mSingleton == null) {
            return new MakeDataForExpandableList(context);
        }
        return mSingleton;
    }

    private List<ApplicationInfo> MakeParentList() {
        List<ApplicationInfo> applicationInfoList = new ArrayList<>();
        for (ApplicationInfo info : mApplicationInfoList) {
            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
                continue;
            }
            applicationInfoList.add(info);
        }
        return applicationInfoList;
    }

    private List<List<String>> MakeChildList() {
        List<List<String>> arrayList = new ArrayList<>();
        for (ApplicationInfo info : mApplicationInfoList) {
            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
                continue;
            }
            List<String> childItem = new ArrayList<>();
            childItem.add("Package Name :\n" + info.packageName);
            childItem.add("Class Name :\n" + info.name);
            arrayList.add(childItem);
        }
        return arrayList;
    }

    public List<ApplicationInfo> getParentList() {
        return mParentList;
    }

    public List<List<String>> getChildList() {
        return mChildList;
    }
}
