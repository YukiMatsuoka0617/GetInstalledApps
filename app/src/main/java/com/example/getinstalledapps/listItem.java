package com.example.getinstalledapps;

import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;

public class listItem {
    Bitmap mBitmap = null;
    String mTitle = null;
    ShortcutInfo mShortcutInfo = null;

    public listItem() {
    }

    public listItem(Bitmap bitmap,  ShortcutInfo shortcutInfo) {
        mBitmap = bitmap;
        mTitle = shortcutInfo.getShortLabel().toString();
        mShortcutInfo = shortcutInfo;
    }

    public void setImage(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setShortcutInfo(ShortcutInfo shortcutInfo) {
        mShortcutInfo = shortcutInfo;
    }

    public Bitmap getThumbnail() {
        return mBitmap;
    }

    public String getTitle() {
        return mTitle;
    }

    public ShortcutInfo getShortcutInfo() {
        return mShortcutInfo;
    }
}
