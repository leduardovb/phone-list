package com.example.phonelist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "CONTACTS";
    private static final Integer DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "CONTACT";
    private static final String COUNTER = "COUNTER";

    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String ADDRESS = "address";
    private static final String TELEPHONE_FIELD = "telephone";
    private static final String CELLPHONE_FIELD = "cellphone";

    public SQLiteManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager insanceOfDatabse(Context context) {
        if (sqLiteManager == null) sqLiteManager = new SQLiteManager(context);
        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(CELLPHONE_FIELD)
                .append(" TEXT, ")
                .append(TELEPHONE_FIELD)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addContact(Contact contact) {
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, contact.getId());
        contentValues.put(NAME_FIELD, contact.getName());
        contentValues.put(ADDRESS, contact.getName());
        contentValues.put(TELEPHONE_FIELD, contact.getTelephone());
        contentValues.put(CELLPHONE_FIELD, contact.getCellphone());

        sqliteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void updateContactList() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    Integer id = result.getInt(1);
                    String name = result.getString(2);
                    String address = result.getString(3);
                    String telephone = result.getString(4);
                    String cellphone = result.getString(5);
                    Contact.contacts.add(new Contact(id, name, address, telephone, cellphone));
                }
            }
        }
    }

    public void  updateContact(Contact contact) {
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, contact.getId());
        contentValues.put(NAME_FIELD, contact.getName());
        contentValues.put(TELEPHONE_FIELD, contact.getTelephone());
        contentValues.put(CELLPHONE_FIELD, contact.getCellphone());

        //sqliteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =?", new String[](String.valueOf(contact.getId())));
    }

}
