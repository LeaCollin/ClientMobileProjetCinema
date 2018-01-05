package com.example.leamelanie.clientmobileprojetcinema;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.leamelanie.clientmobileprojetcinema.fragments.HomepageFrag;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment) != null) {
            final HomepageFrag homePage = new HomepageFrag();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment,homePage).commit();
        }
    }
}
