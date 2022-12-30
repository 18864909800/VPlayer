package com.ashin.vplayer.syncAdapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.ashin.vplayer.MyApplication;
import com.ashin.vplayer.syncAdapter.db.DatabaseHelper;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = "VP-SyncAdapter";

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public boolean onUnsyncableAccount() {
        Log.d(TAG, "onUnsyncableAccount");
        return super.onUnsyncableAccount();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(TAG, "onPerformSync");
        ContentValues cn = new ContentValues();
        cn.put(DatabaseHelper.COLUMNS_AUTHOR, "莫言");
        cn.put(DatabaseHelper.COLUMNS_NAME, "红高粱");
        cn.put(DatabaseHelper.COLUMNS_PRICE, "100");
        cn.put(DatabaseHelper.COLUMNS_PAGE, "300");
        MyApplication.getContextObject().getContentResolver().insert(StubProvider.BOOK_URI, cn);
    }

    @Override
    public void onSecurityException(Account account, Bundle extras, String authority, SyncResult syncResult) {
        super.onSecurityException(account, extras, authority, syncResult);
        Log.d(TAG, "onSecurityException");
    }

    @Override
    public void onSyncCanceled() {
        super.onSyncCanceled();
        Log.d(TAG, "onSyncCanceled");
    }

    @Override
    public void onSyncCanceled(Thread thread) {
        super.onSyncCanceled(thread);
        Log.d(TAG, "onSyncCanceled thread");
    }
}
