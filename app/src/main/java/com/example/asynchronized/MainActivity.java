package com.example.asynchronized;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.asynchronized.db.DatabaseHandler;
import com.example.asynchronized.helpers.ContactStoreCallback;
import com.example.asynchronized.models.Category;
import com.example.asynchronized.models.Contact;
import com.example.asynchronized.models.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactStoreCallback {

    private Button button;

    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initObjects();
        initListeners();
        new Thread() {
            @Override public void run() { storeDataInDb(); }
        }.start();

    }

    private void initViews() {
        button = (Button) findViewById(R.id.buttonClick);
    }

    private void initObjects() {
        db = new DatabaseHandler(this, this);
    }

    private void initListeners() {
        db = new DatabaseHandler(this, this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Contact> allContacts = db.getAllContacts();
                System.out.println("Name: "+allContacts.size());
            }
        });
    }

    @Override
    public void responseStatus(boolean status) {

        System.out.println("Resuming the thread using notify()");

        new Thread() {
            @Override public void run() { restartThread(); }
        }.start();
    }

    private synchronized void storeDataInDb() {
        for (int i=0;i<3;i++) {

            executeMethodsInQueue(i+1);
            try {
                System.out.println(
                        "Invoking the wait() method");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    private synchronized void restartThread() {
        notify();
    }

    private void executeMethodsInQueue(int method) {
        switch (method) {
            case 1:
                storeContacts();
                break;
            case 2:
                storeProducts();
                break;
            case 3:
                storeCategories();
                break;
            default:
                break;
        }
    }

    private void storeContacts() {
        db = new DatabaseHandler(this, this);

        List<Contact> contacts = new ArrayList<>();

        for (int i=0; i<10000; i++) {
            Contact contact = new Contact();
            contact.set_name("John Deo");
            contact.set_phone_number("+94 715556666");
            contacts.add(contact);
        }

        boolean b = db.addContacts(contacts);
        Log.d("DB_Status", String.valueOf(b));
    }

    private void storeProducts() {
        db = new DatabaseHandler(this, this);

        List<Product> products = new ArrayList<>();

        for (int i=0; i<10000; i++) {
            Product product = new Product();
            product.set_name("John Deo");
            product.set_phone_number("+94 715556666");
            products.add(product);
        }

        boolean b = db.addProducts(products);
        Log.d("DB_Status", String.valueOf(b));
    }

    private void storeCategories() {
        db = new DatabaseHandler(this, this);

        List<Category> categories = new ArrayList<>();

        for (int i=0; i<10000; i++) {
            Category category = new Category();
            category.set_name("John Deo");
            category.set_phone_number("+94 715556666");
            categories.add(category);
        }

        boolean b = db.addCategories(categories);
        Log.d("DB_Status", String.valueOf(b));
    }


}