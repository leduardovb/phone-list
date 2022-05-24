package com.example.phonelist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "CONTACTS";
    private static final Integer DATABASE_VERSION = 3;
    private static String TABLE_NAME;
    private static final String COUNTER = "COUNTER";

    public SQLiteManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager insanceOfDatabse(Context context, String tableName) {
        if (sqLiteManager == null) sqLiteManager = new SQLiteManager(context);
        SQLiteManager.TABLE_NAME = tableName;
        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS CONTACT ")
                .append("(")
                .append("id INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append("name VARCHAR(200), ")
                .append("address VARCHAR(200), ")
                .append("telephone VARCHAR(200), ")
                .append("cellphone VARCHAR(200)")
                .append(")");
        sqLiteDatabase.execSQL(builder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void close() {
        sqLiteManager = null;
    }

    public Cursor select(@Nullable String fields) {
        if (fields == null) fields = "*";
        SQLiteDatabase sqLiteDatabase = sqLiteManager.getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT " + fields + " FROM " + TABLE_NAME, null);
    }

    public long insert(ContentValues contentValues) {
        SQLiteDatabase sqliteDatabase = sqLiteManager.getWritableDatabase();
        try {
            long savedId = sqliteDatabase.insert(TABLE_NAME, null, contentValues);
            return savedId;
        } catch (SQLException e) {
            onCreate(sqliteDatabase);
            long savedId = sqliteDatabase.insert(TABLE_NAME, null, contentValues);
            return savedId;
        }
    }

    public int update(ContentValues contentValues, String where, String[] values) {
        SQLiteDatabase sqliteDatabase = sqLiteManager.getWritableDatabase();
        return sqliteDatabase.update(TABLE_NAME, contentValues, where, values);
    }

}
