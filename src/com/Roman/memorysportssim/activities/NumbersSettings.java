package com.Roman.memorysportssim.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.Roman.memorysportssim.R;

public class NumbersSettings extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_numbers_settings);
		// big button
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.numbers_settings, menu);
		return true;
	}
	
	public void onClickGo(View view) {
		
	    Intent intent = new Intent(NumbersSettings.this, NumbersMemo.class);

	    intent.putExtra("amountOfDigits", Integer.parseInt(((EditText)(findViewById(R.id.totalDigits))).getText().toString()));
	    intent.putExtra("groupBy", Integer.parseInt(((EditText)findViewById(R.id.groupBy)).getText().toString()));
	    intent.putExtra("digitsPerRow", Integer.parseInt(((EditText)findViewById(R.id.digitsPerRow)).getText().toString()));
	    startActivity(intent);
	    //finish();
	}

}
