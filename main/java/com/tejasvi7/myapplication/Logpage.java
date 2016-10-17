package com.tejasvi7.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Logpage extends AppCompatActivity {
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logpage);
        Add fragmentAdd = new Add();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flayout, fragmentAdd, "My fragment1").commit();

        Button add = (Button) findViewById(R.id.add);


        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Add fragmentAdd = new Add();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flayout, fragmentAdd, "My fragment1").commit();

            }
        });

        Button list = (Button) findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Pending fragmentList = new Pending();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flayout, fragmentList, "My fragment2").commit();

            }
        });

        Button done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Done fragmentDone = new Done();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flayout, fragmentDone, "My fragment3").commit();

            }
        });

    }


}

