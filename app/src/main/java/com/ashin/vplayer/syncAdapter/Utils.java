package com.ashin.vplayer.syncAdapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.ashin.vplayer.R;

public class Utils {
    private static final String TAG = "VP-SyncUtils";
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.ashin.stubprovider";

    public static void syncImmediately(Context context) {
        Account syncAccount = getSyncAccount(context);
        boolean syncAutomatically = ContentResolver.getSyncAutomatically(syncAccount, AUTHORITY);
        if (!syncAutomatically && syncAccount != null) {
            Log.d(TAG, "syncAutomatically false");
            ContentResolver.setSyncAutomatically(syncAccount, AUTHORITY, true);
        }
        Bundle bundle = new Bundle();

        //将此同步放在同步请求队列前面，立即进行同步而不延迟
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        //忽略当前设置强制发起同步
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(syncAccount, AUTHORITY, bundle);
    }

    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), "ashin.com");

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

            /*
             * Add the account and account type, no password or user data
             * If successful, return the Account object, otherwise report an error.
             */
            if (!accountManager.addAccountExplicitly(newAccount, null, null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

        }
        return newAccount;
    }


}
