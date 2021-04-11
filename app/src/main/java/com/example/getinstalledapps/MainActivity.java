package com.example.getinstalledapps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LauncherApps launcherApps;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);
        listView.setOnItemClickListener(onItemClickListener);

        ArrayList<listItem> arrayList = new ArrayList<listItem>();

        launcherApps = (LauncherApps) getSystemService(Context.LAUNCHER_APPS_SERVICE);
        if (!launcherApps.hasShortcutHostPermission()) {
            Log.e("test", "hasShortcutHostPermission is false");
            return;
        }

        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        int i = 0;
        for (ApplicationInfo info : applicationInfoList) {
            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
                continue;
            }
            Log.d("test", "---------" + i + "---------");
            Log.d("test", "packagename: " + info.packageName);
            Log.d("test", "classname: " + info.name);
            Log.d("test", "appname: " + info.loadLabel(packageManager));
            i++;

            if (info.packageName.equals("jp.auone.wallet")) {
                LauncherApps.ShortcutQuery shortcutQuery = new LauncherApps.ShortcutQuery();
                int flag = LauncherApps.ShortcutQuery.FLAG_MATCH_DYNAMIC |
                        LauncherApps.ShortcutQuery.FLAG_MATCH_PINNED |
                        LauncherApps.ShortcutQuery.FLAG_MATCH_MANIFEST;
                shortcutQuery.setQueryFlags(flag);
                shortcutQuery.setPackage(info.packageName);

                List<ShortcutInfo> shortcutInfoList = launcherApps.getShortcuts(
                        shortcutQuery,
                        UserHandle.getUserHandleForUid(info.uid));

                for (ShortcutInfo shortcutInfo : shortcutInfoList) {
                    Log.d("test", "appname: " + shortcutInfo.getShortLabel());
                    Log.d("test", "getExtra: " + shortcutInfo.getExtras());
                    Log.d("test", "getId: " + shortcutInfo.getId());
                    Log.d("test", "getPackage: " + shortcutInfo.getPackage());
                    Log.d("test", "getCategories: " + shortcutInfo.getCategories());
                    Log.d("test", "isCached: " + shortcutInfo.isCached());

                    listItem item = new listItem(
                            ((BitmapDrawable) launcherApps.getShortcutIconDrawable(shortcutInfo, getResources().getDisplayMetrics().densityDpi)).getBitmap(),
                            shortcutInfo);
                    arrayList.add(item);
                }
            }
        }

        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.list_view_layout, arrayList);
        listView.setAdapter(adapter);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ListView listView = (ListView) adapterView;
            listItem item = (listItem) listView.getItemAtPosition(i);

            launcherApps.startShortcut(item.getShortcutInfo(), null, null);
        }
    };
}