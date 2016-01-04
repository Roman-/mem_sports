package com.Roman.memorysportssim.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.Roman.memorysportssim.R;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);
        Log.i("MY", "onCreateActivity");
        //temp. - big button
    }

    public void startNumbers(View view) {
        Intent intent = new Intent(MainActivity.this, NumbersSettings.class);
        startActivity(intent);
    }

    public void showStat(View view) {
        Intent intent = new Intent(MainActivity.this, StatActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
