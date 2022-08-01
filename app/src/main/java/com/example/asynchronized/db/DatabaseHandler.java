package com.example.asynchronized.db;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.asynchronized.helpers.ContactStoreCallback;
import com.example.asynchronized.models.Category;
import com.example.asynchronized.models.Contact;
import com.example.asynchronized.models.Product;
import com.example.asynchronized.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private ContactStoreCallback contactStoreCallback;

    public DatabaseHandler(Context context, ContactStoreCallback contactStoreCallback) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.contactStoreCallback = contactStoreCallback;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + Constants.TABLE_CONTACTS + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY," + Constants.KEY_NAME + " TEXT,"
                + Constants.KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + Constants.TABLE_PRODUCTS + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY," + Constants.KEY_NAME + " TEXT,"
                + Constants.KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + Constants.TABLE_CATEGORIES + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY," + Constants.KEY_NAME + " TEXT,"
                + Constants.KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CATEGORIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_CATEGORIES);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public boolean addContacts(List<Contact> contacts) {

        SQLiteDatabase db = this.getWritableDatabase();

//        ContentValues values = new ContentValues();
//        values.put(Constants.KEY_NAME, contact.get_name());
//        values.put(Constants.KEY_PH_NO, contact.get_phone_number());
//
//        db.insert(Constants.TABLE_CONTACTS, null, values);
//        db.close();

        StringBuilder sb = new StringBuilder();
        String preFix = "";
        for (Contact contact: contacts) {
            sb.append(preFix);
            preFix = ",";
            sb.append("(");
            sb.append(DatabaseUtils.sqlEscapeString(contact.get_name()));
            sb.append(",");
            sb.append(DatabaseUtils.sqlEscapeString(contact.get_phone_number()));
            sb.append(")");
        }

        String query = "INSERT INTO contacts (name,phone_number) VALUES ";

        query = query + sb.toString();

        System.out.println(query);

        boolean insert = false;
        try {
            db.execSQL(query);
            System.out.println("___backupTable__ "+query);
            insert = true;
            contactStoreCallback.responseStatus(true);
        } catch (SQLException e) {
            System.out.println("___backupTable__ "+e.toString());
        }
        db.close();
        return insert;
    }


    // code to add the new product
    public boolean addProducts(List<Product> products) {

        SQLiteDatabase db = this.getWritableDatabase();

        StringBuilder sb = new StringBuilder();
        String preFix = "";
        for (Product product: products) {
            sb.append(preFix);
            preFix = ",";
            sb.append("(");
            sb.append(DatabaseUtils.sqlEscapeString(product.get_name()));
            sb.append(",");
            sb.append(DatabaseUtils.sqlEscapeString(product.get_phone_number()));
            sb.append(")");
        }

        String query = "INSERT INTO products (name,phone_number) VALUES ";

        query = query + sb.toString();

        System.out.println(query);

        boolean insert = false;
        try {
            db.execSQL(query);
            System.out.println("___backupTable__ "+query);
            insert = true;
            contactStoreCallback.responseStatus(true);
        } catch (SQLException e) {
            System.out.println("___backupTable__ "+e.toString());
        }
        db.close();
        return insert;
    }

    // code to add the new product
    public boolean addCategories(List<Category> categories) {

        SQLiteDatabase db = this.getWritableDatabase();

        StringBuilder sb = new StringBuilder();
        String preFix = "";
        for (Category category: categories) {
            sb.append(preFix);
            preFix = ",";
            sb.append("(");
            sb.append(DatabaseUtils.sqlEscapeString(category.get_name()));
            sb.append(",");
            sb.append(DatabaseUtils.sqlEscapeString(category.get_phone_number()));
            sb.append(")");
        }

        String query = "INSERT INTO categories (name,phone_number) VALUES ";

        query = query + sb.toString();

        System.out.println(query);

        boolean insert = false;
        try {
            db.execSQL(query);
            System.out.println("___backupTable__ "+query);
            insert = true;
            contactStoreCallback.responseStatus(true);
        } catch (SQLException e) {
            System.out.println("___backupTable__ "+e.toString());
        }
        db.close();
        return insert;
    }





    // code to get all contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Constants.TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_name(cursor.getString(1));
                contact.set_phone_number(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
}
