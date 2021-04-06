package com.example.getinstalledapps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        LauncherApps launcherApps = (LauncherApps) getSystemService(Context.LAUNCHER_APPS_SERVICE);

        if (!launcherApps.hasShortcutHostPermission()) {
            Log.e("test", "hasShortcutHostPermission is false");
            return;
        }

        int i = 0;

        for(ApplicationInfo applicationInfo : applicationInfoList){
            if(packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null &&
            !packageManager.getLaunchIntentForPackage(applicationInfo.packageName).equals("")){
                Log.d("test","---------" + i + "---------");
                Log.d("test","packageName :" + applicationInfo.packageName);
                Log.d("test","Launch Intent For Package :" + applicationInfo.packageName);
                Log.d("test","Application Label :" + packageManager.getApplicationLabel(applicationInfo));
                i++;

                if(applicationInfo.packageName.equals("com.android.settings")){
                    Log.d("test","find");
                    int queryFlags = LauncherApps.ShortcutQuery.FLAG_MATCH_DYNAMIC | LauncherApps.ShortcutQuery.FLAG_MATCH_PINNED | LauncherApps.ShortcutQuery.FLAG_MATCH_MANIFEST;
                    List<ShortcutInfo> shortcutInfoList = launcherApps.getShortcuts(new LauncherApps.ShortcutQuery().setPackage(applicationInfo.packageName).setQueryFlags(queryFlags), UserHandle.getUserHandleForUid(applicationInfo.uid));
                    for (final ShortcutInfo shortcutInfo : shortcutInfoList) {
                        Log.d("test","getLongLabel" + shortcutInfo.getLongLabel());
                    }

                }
            }

        }
    }
}