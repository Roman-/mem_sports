package com.Roman.memorysportssim.activities;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.Roman.memorysportssim.R;

public class NumbersReview extends Activity {
	String[] Numbers;
	String[] RecalledNumbers;
	int totalNumbers, score, correctNumbers;//stats
	long timeInMilliseconds;
	
	public String ShowTimeByMills(boolean upToMillis) {//when upToMillis == true, time is shown in mm:ss.tt format. Otherwise mm:ss
		int secs = (int) (timeInMilliseconds / 1000);
		            int mins = secs / 60;
		            int hrs = secs / 3600;
		            secs = secs % 60;
		            int milliseconds = (int) ((timeInMilliseconds % 1000) / 10);
		String r = "";
		if (hrs != 0)
			r += hrs + ":";
		if (mins != 0 || hrs != 0) {
			if (hrs == 0 && mins < 10)
				 r += mins + ":";
			else
				r += String.format("%02d", mins) + ":";
		}
		if (mins == 0 && hrs == 0 && secs < 10)
			 r += secs;
		else
			r += String.format("%02d", secs);
		if (upToMillis)
		     r +=  "." + String.format("%02d", milliseconds);
		return r;
	}

	
	TableRow NewTableRow() {
		TableRow tr = new TableRow(this);// Create a new row to be added
		tr.setBackgroundColor(getResources().getColor(R.color.MemoBackground));
		tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
		return tr;		
	}
	void CreateTable() {
		totalNumbers = Numbers[0].length() * (Numbers.length - 1) + Numbers[Numbers.length - 1].length();
		score = correctNumbers = 0;
		int numberOfColumns = Numbers[0].length();
		TextView[][] TVs = new TextView[Numbers.length][numberOfColumns];
		TableLayout tl = (TableLayout) findViewById(R.id.TableLayout4);/* Find Tablelayout defined in xml */	
		TextView TimeTv = new TextView(this);
		TimeTv.setTextColor(Color.GRAY);
		TimeTv.setText("Time: " + ShowTimeByMills(true));
		tl.addView(TimeTv, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
		for (int i = 0; i < Numbers.length; i++) {
			TableRow tr = NewTableRow();
			TextView rowNumberTv = new TextView(this);
			rowNumberTv.setText("#" + Integer.toString(i + 1)); // till the end of the string
			rowNumberTv.setBackgroundColor(Color.rgb(62, 12, 12));
			rowNumberTv.setTextColor(getResources().getColor(R.color.memoTextColor));
			//TVs[i][j].setTextSize(R.dimen.tableTextSize);
			rowNumberTv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
			tr.addView(rowNumberTv);
			if (i == Numbers.length - 1) // last row have less columns
				numberOfColumns = Numbers[i].length();
			for (int j = 0; j < numberOfColumns; j++) {					
				TVs[i][j] = new TextView(this);
				TVs[i][j].setBackgroundColor(Color.rgb(88, 4, 4));
				if (RecalledNumbers[i].length() > j) {
					TVs[i][j].setText(Numbers[i].substring(j, j+1) + "\n" + RecalledNumbers[i].substring(j, j+1));
					if (Numbers[i].substring(j, j+1).contains(RecalledNumbers[i].substring(j, j+1))) {
						TVs[i][j].setBackgroundColor(Color.rgb(4, 88, 4));
						correctNumbers++;
					}
				}
				else
					TVs[i][j].setText(Numbers[i].substring(j, j+1) + "\n" + " ");
				//TVs[i][j].setPadding(3, 3, 3, 3);
				TVs[i][j].setGravity(Gravity.CENTER);
				TVs[i][j].setTextColor(getResources().getColor(R.color.memoTextColor));
				//TVs[i][j].setTextSize(R.dimen.tableTextSize);
				TVs[i][j].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
				tr.addView(TVs[i][j]);
			}
			tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
		}
		// TODO button menu and try again
		TextView scoreTv = new TextView(this);
		scoreTv.setText("Correctly recalled: " + Integer.toString(correctNumbers) + "/" + Integer.toString(totalNumbers));
		scoreTv.setTextColor(Color.GRAY);
		tl.addView(scoreTv, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
		
		
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_numbers_review);
		
		Numbers = getIntent().getExtras().getStringArray("Numbers");
		RecalledNumbers = getIntent().getExtras().getStringArray("RecalledNumbers");
		timeInMilliseconds = getIntent().getExtras().getLong("Time");
		
		CreateTable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.numbers_review, menu);
		return true;
	}

}
