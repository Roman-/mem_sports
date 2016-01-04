package com.Roman.memorysportssim.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.Roman.memorysportssim.DbHelper;
import com.Roman.memorysportssim.R;

public class NumbersRecall extends Activity {
    String[] Numbers;
    String[] RecalledNumbers;
    EditText[] RecallEtvs;
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
            r += "." + String.format("%02d", milliseconds);
        return r;
    }

    TableRow NewTableRow() {
        TableRow tr = new TableRow(this);// Create a new row to be added
        tr.setBackgroundColor(getResources().getColor(R.color.MemoBackground));
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        return tr;
    }

    void AddRowNumber(TableRow tr, int i) {
        TextView rowNumberTv = new TextView(this);
        rowNumberTv.setText("#" + Integer.toString(i + 1)); // till the end of the string
        rowNumberTv.setBackgroundColor(Color.rgb(62, 12, 12));
        rowNumberTv.setTextColor(getResources().getColor(R.color.memoTextColor));
        rowNumberTv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tr.addView(rowNumberTv);
    }

    void AddRecallTv(TableRow tr, int i) {
        RecallEtvs[i] = new EditText(this);
        RecallEtvs[i].setFilters(new InputFilter[]{new InputFilter.LengthFilter(Numbers[0].length())});
        RecallEtvs[i].setTextColor(getResources().getColor(R.color.memoTextColor));
        RecallEtvs[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        RecallEtvs[i].setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        tr.addView(RecallEtvs[i]);
    }

    void CreateRecallTable() {
        RecallEtvs = new EditText[Numbers.length];
        TableLayout tl = (TableLayout) findViewById(R.id.TableLayout2);
        TextView TimeTv = new TextView(this);
        TimeTv.setText("Time: " + ShowTimeByMills(true));
        TimeTv.setTextColor(Color.GRAY);
        tl.addView(TimeTv, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < Numbers.length; i++) {
            TableRow tr = NewTableRow();
            AddRowNumber(tr, i);
            AddRecallTv(tr, i);
            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
        Button endRecallButton = new Button(this);    //recall button
        endRecallButton.setText("Finish >>");
        endRecallButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        endRecallButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper.addStatEntry(NumbersRecall.this, 100, 40, 33, 44);
                RecalledNumbers = new String[Numbers.length];
                for (int i = 0; i < Numbers.length; i++)
                    RecalledNumbers[i] = RecallEtvs[i].getText().toString();
                Intent intent = new Intent(NumbersRecall.this, NumbersReview.class);
                intent.putExtra("Numbers", Numbers);
                intent.putExtra("RecalledNumbers", RecalledNumbers);
                intent.putExtra("Time", timeInMilliseconds);
                startActivity(intent);
                finish();
            }
        });
        //Button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tl.addView(endRecallButton, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_recall);

        Numbers = getIntent().getExtras().getStringArray("Numbers");
        timeInMilliseconds = getIntent().getExtras().getLong("Time");
        CreateRecallTable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.numbers_recall, menu);
        return true;
    }

}
