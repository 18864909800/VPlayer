package com.ashin.vplayer.syncAdapter;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.ashin.vplayer.MyApplication;
import com.ashin.vplayer.syncAdapter.db.DatabaseHelper;

public class StubProvider extends ContentProvider {
    private static final String TAG = "VP-StubProvider";
    private DatabaseHelper dbHelper;
    public static final String AUTHORITY = "com.ashin.stubprovider";
    private static final UriMatcher sUriMatcher;

    private static final int BOOK_CODE = 1;

    public static final Uri URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri BOOK_URI = URI.buildUpon().appendPath(DatabaseHelper.TABLE_BOOK).build();

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_CODE);
    }

    public StubProvider() {
    }

    private static String matchTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_CODE:
                tableName = DatabaseHelper.TABLE_BOOK;
                break;
            default:
                break;
        }
        return tableName;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //DatabaseHelper的参数分别为:上下文, 数据库名为BookStore.db, CursorFactory为null, 版本号为1
        dbHelper = new DatabaseHelper(MyApplication.getContextObject(), "BookStore.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName = matchTableName(uri);
        db.insert(tableName, null, values);
        return null;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        return 0;
    }
}