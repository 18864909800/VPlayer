package com.ashin.vplayer.syncAdapter.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "VP-DatabaseHelper";
    private Context mContext;

    public static final String TABLE_BOOK = "book";


    public static final String COLUMNS_AUTHOR = "author";
    public static final String COLUMNS_PRICE = "price";
    public static final String COLUMNS_PAGE = "page";
    public static final String COLUMNS_NAME = "name";

    //我们希望创建一个名为BookStore.db的数据库, 然后在数据库中新建一张Book表, 其中有id(主键), 作者, 价格,页数和书名. SQL建表语句如下:
    /****
     *  integer: 表示整型;
     *  real: 表示浮点型;
     *  text: 表示文本型;
     *  blob:表示二进制类型
     *  primary key: 表示主键;
     *  autoincrement: 表示自增长
     */
    public static final String CREATE_BOOK = "create table book (" +
            "id integer primary key autoincrement, " +
            "author text, " +
            "price real, " +
            "page integer, " +
            "name text)";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DatabaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
