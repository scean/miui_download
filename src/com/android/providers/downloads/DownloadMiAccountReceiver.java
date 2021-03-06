package com.android.providers.downloads;

import java.io.File;

import android.accounts.Account;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Downloads;
import android.util.Log;
import android.os.Bundle;

import miui.accounts.ExtraAccountManager;

import com.google.common.annotations.VisibleForTesting;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * Receives system broadcasts (boot, network connectivity)
 */
public class DownloadMiAccountReceiver extends BroadcastReceiver {

    public static final String ACTION_LOGIN_ACCOUNTS_POST_CHANGED = ExtraAccountManager.LOGIN_ACCOUNTS_POST_CHANGED_ACTION;
    private static final String TAG = "DownloadMiAccountReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(ACTION_LOGIN_ACCOUNTS_POST_CHANGED.equals(action)) {
            Account account = ExtraAccountManager.getXiaomiAccount(context);
            if (account != null) {//Xiaomi Account logged in
                MiPushClient.registerPush(context, DownloadApplication.APP_ID, DownloadApplication.APP_KEY);
            } else {
                MiPushClient.unregisterPush(context);
            }
        }
    }
}
