package com.example.phonelist.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.phonelist.R;
import com.example.phonelist.database.SQLiteManager;
import com.example.phonelist.models.Contact;
import com.example.phonelist.models.Notification;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ContactService {

    private final static String TABLE_NAME = "CONTACT";
    private final static String ID_FIELD = "id";
    private final static String NAME_FIELD = "name";
    private final static String ADDRESS_FIELD = "address";
    private final static String TELEPHONE_FIELD = "telephone";
    private final static String CELLPHONE_FIELD = "cellphone";

    private final Context context;
    private Notification notification;

    public ContactService(Context context) {
        this.context = context;
        this.notification = new Notification(context);
    }

    public void list() {
        SQLiteManager sqLiteManager = SQLiteManager.insanceOfDatabse(this.context, TABLE_NAME);
        Cursor result = sqLiteManager.select(null);
        if (result != null && result.getCount() > 0) {
            result.moveToFirst();
            do {
                Integer id = result.getInt(0);
                String name = result.getString(1);
                String address = result.getString(2);
                String telephone = result.getString(3);
                String cellphone = result.getString(4);
                Contact contact = new Contact(id, name, address, telephone, cellphone);
                Contact.contacts.add(contact);
            } while (result.moveToNext());
            result.close();
        }
        sqLiteManager.close();
    }

    public int newContact(Contact contact) {
        SQLiteManager sqLiteManager = SQLiteManager.insanceOfDatabse(this.context, TABLE_NAME);
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME_FIELD, contact.getName());
        contentValues.put(ADDRESS_FIELD, contact.getAddress());
        contentValues.put(TELEPHONE_FIELD, contact.getTelephone());
        contentValues.put(CELLPHONE_FIELD, contact.getCellphone());

        long savedId = sqLiteManager.insert(contentValues);
        sqLiteManager.close();

        if (savedId != -1) notification
                            .showNotification("Novo contato adicionado",
                                    "O contato " + contact.getName() + " foi adicionado",
                                    R.drawable.add_contact
                        );
        return (int) savedId;
    }

    public boolean updateContact(Contact contact) {
        SQLiteManager sqLiteManager = SQLiteManager.insanceOfDatabse(this.context, TABLE_NAME);
        ContentValues contentValues = new ContentValues();
        String where = "id =?";
        String[] whereValues = {Integer.toString(contact.getId())};

        contentValues.put(ID_FIELD, contact.getId());
        contentValues.put(NAME_FIELD, contact.getName());
        contentValues.put(ADDRESS_FIELD, contact.getAddress());
        contentValues.put(TELEPHONE_FIELD, contact.getTelephone());
        contentValues.put(CELLPHONE_FIELD, contact.getCellphone());

        Integer rows = sqLiteManager.update(contentValues, where, whereValues);
        sqLiteManager.close();

        return rows > 0;
    }

    public boolean deleteContact(Contact contact) {
        SQLiteManager sqLiteManager = SQLiteManager.insanceOfDatabse(this.context, TABLE_NAME);

        String where = "id =?";
        String[] whereValues = {Integer.toString(contact.getId())};

        int rows = sqLiteManager.delete(where, whereValues);
        sqLiteManager.close();

        if (rows > 0) notification.showNotification("Contato deletado",
                "O contato " + contact.getName() + " foi deletado",
                R.drawable.delete_icon
        );

        return rows > 0;
    }

}
