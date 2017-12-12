package com.Roman.memorysportssim.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.Roman.memorysportssim.R;

public class NumbersSettings extends Activity {
	String event;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_numbers_settings);
		// default values
		event = getIntent().getStringExtra("event");
		((TextView)findViewById(R.id.textView1)).setText("Amount of " + event);
		if (event.equals("numbers")) {
			((EditText)findViewById(R.id.totalDigits)).setText("40");
			((EditText)findViewById(R.id.groupBy)).setText("3");
			((EditText)findViewById(R.id.digitsPerRow)).setText("30");
		} else if (event.equals("letters")) {
			((EditText)findViewById(R.id.totalDigits)).setText("60");
			((EditText)findViewById(R.id.groupBy)).setText("2");
			((EditText)findViewById(R.id.digitsPerRow)).setText("20");
			Log.i("SET FOR LETTERS! ", event);
		} else if (event.equals("binaries")) {
			((EditText)findViewById(R.id.totalDigits)).setText("240");
			((EditText)findViewById(R.id.groupBy)).setText("4");
			((EditText)findViewById(R.id.digitsPerRow)).setText("40");
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.numbers_settings, menu);
		return true;
	}
	
	public void onClickGo(View view) {
		
	    Intent intent = new Intent(NumbersSettings.this, NumbersMemo.class);

	    intent.putExtra("amountOfItems", Integer.parseInt(((EditText)(findViewById(R.id.totalDigits))).getText().toString()));
	    intent.putExtra("groupBy", Integer.parseInt(((EditText)findViewById(R.id.groupBy)).getText().toString()));
	    intent.putExtra("itemsPerRow", Integer.parseInt(((EditText)findViewById(R.id.digitsPerRow)).getText().toString()));
		intent.putExtra("event", event);
	    startActivity(intent);
	    //finish();
	}

}
