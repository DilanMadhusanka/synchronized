package com.example.asynchronized;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;

    private GunFight gf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        WaitDemo waitDemo = new WaitDemo();
//        waitDemo.startExec();

        initViews();
        initObjects();
        initListeners();
    }

    private void initViews() {
        button = (Button) findViewById(R.id.buttonClick);
    }

    private void initObjects() {
        gf = new GunFight();

        // Creating a new thread and invoking
        // our fire() method on it
        new Thread() {
            @Override public void run() { gf.fire(100); }
        }.start();
    }

    private void initListeners() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating a new thread and invoking
                // our reload method on it
                new Thread() {
                    @Override public void run() { gf.reload(); }
                }.start();
            }
        });
    }
}