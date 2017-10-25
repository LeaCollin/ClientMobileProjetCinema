package com.example.leamelanie.clientmobileprojetcinema;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

    private void showFragment(final Fragment fragment) {
        if (fragment == null) {
            return;
        }

        // Begin a fragment transaction.
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        // We can also animate the changing of fragment.
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        // Replace current fragment by the new one.
        ft.replace(R.id.fragment, fragment);


        // Commit changes.
        ft.commit();
    }
}
