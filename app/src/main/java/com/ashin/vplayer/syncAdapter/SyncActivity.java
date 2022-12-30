package com.ashin.vplayer.syncAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ashin.vplayer.R;
import com.ashin.vplayer.syncAdapter.db.DatabaseHelper;

public class SyncActivity extends AppCompatActivity {
    private static final String TAG = "VP-SyncActivity";

    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.ashin.stubprovider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "ashin.com";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);

        Button syncButton = (Button) findViewById(R.id.sync_button);
        Button createDatabase =  (Button)findViewById(R.id.create_sql);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "syncButton onClick");
                Utils.syncImmediately(SyncActivity.this);
            }
        });
    }
}