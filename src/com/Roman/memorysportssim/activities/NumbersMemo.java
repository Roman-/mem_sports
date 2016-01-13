package com.Roman.memorysportssim.activities;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.Roman.memorysportssim.R;

public class NumbersMemo extends Activity {
    String event;
    //    char[] myAlphabetChars = "БВГДЖЗКАИОУЛМНПРСТФХЧШЮЯ".toCharArray();
    char[] myAlphabetChars = "АБВГДЕЖЗИКЛМНОПРСТУФМЧШ".toCharArray();
    long startTime = 0L;
    private Timer cdt, t;
    TextView timeTv, countDownTv;
    String[] Numbers; // lines (easy for review check)
    int ConcentrationTime = 3000;
    TableRow countDownTr;
    TableLayout tl;
    Button goButton;
    TextView[][] TVs;
    int amountOfDigits, groupBy, digitsPerRow;

    int BGColor(int i, int j) {
        if ((i + j) % 2 == 0)
            return getResources().getColor(R.color.tvBack1);
        return getResources().getColor(R.color.tvBack2);
    }

    public String ShowCountDownSecs() {//when upToMillis == true, time is shown in mm:ss.tt format. Otherwise mm:ss
        long timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
        return Integer.toString((ConcentrationTime - (int) timeInMilliseconds) / 1000 + 1); // +1 for displaying "321" instead of "210"
    }

    public String ShowTimeByMills(boolean upToMillis) {//when upToMillis == true, time is shown in mm:ss.tt format. Otherwise mm:ss
        long timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
        int secs = (int) (timeInMilliseconds / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        return String.format("%02d", mins) + ":" + String.format("%02d", secs);
    }


    public void StartTimer() {
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        timeTv.setText(ShowTimeByMills(false));
                    }
                });

            }
        }, 0, 250);
    }

    public void StartCountDownTimer() {
        cdt = new Timer();
        startTime = SystemClock.uptimeMillis();
        cdt.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        countDownTv.setText(ShowCountDownSecs());
                        if (SystemClock.uptimeMillis() - startTime > ConcentrationTime) {
                            cdt.cancel(); // stop this timer
                            tl.removeView(countDownTr);
                            startTime = SystemClock.uptimeMillis();
                            ShowTable();
                            StartTimer();
                        }
                    }
                });

            }
        }, 0, 250);
    }

    TableRow NewTableRow() {
        TableRow tr = new TableRow(this);// Create a new row to be added
        //tr.setBackgroundColor(getResources().getColor(R.color.MemoBackground));
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        return tr;
    }

    void CreateMaskedTable() { // TODO border http://stackoverflow.com/questions/2108456/how-can-i-create-a-table-with-borders-in-android
        int numberOfColumns = (int) Math.ceil((float) digitsPerRow / groupBy);
        int numberOfRows = (int) Math.ceil((float) amountOfDigits / digitsPerRow);
        tl = (TableLayout) findViewById(R.id.TableLayout1);/* Find Tablelayout defined in main.xml */
        //tl.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        tl.setBackgroundColor(getResources().getColor(R.color.MemoBackground));
        TVs = new TextView[numberOfRows][numberOfColumns];
        //add timer
        TableRow timeTR = NewTableRow();
        timeTR.setGravity(Gravity.CENTER);
        timeTv = new TextView(this);
        timeTv.setTextColor(getResources().getColor(R.color.memoTextColor));
        timeTR.addView(timeTv);
        tl.addView(timeTR, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        //add countDown timer
        countDownTr = NewTableRow();
        countDownTr.setGravity(Gravity.CENTER_HORIZONTAL);
        countDownTv = new TextView(this);
        countDownTv.setTextColor(Color.RED);
        countDownTv.setTextSize(110); // need to be big
        countDownTr.addView(countDownTv);
        tl.addView(countDownTr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < numberOfRows; i++) {
            TableRow tr = NewTableRow();
            TextView rowNumberTv = new TextView(this);
            rowNumberTv.setText("#" + Integer.toString(i + 1)); // till the end of the string
            rowNumberTv.setBackgroundColor(Color.rgb(12, 62, 12));
            rowNumberTv.setTextColor(getResources().getColor(R.color.memoTextColor));
            //TVs[i][j].setTextSize(R.dimen.tableTextSize);
            rowNumberTv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(rowNumberTv);
            if (i == numberOfRows - 1 && (amountOfDigits % digitsPerRow != 0)) // last row have less columns
                numberOfColumns = (int) Math.ceil((float) (amountOfDigits % digitsPerRow) / groupBy);
            for (int j = 0; (j < numberOfColumns); j++) {
                TVs[i][j] = new TextView(this);
                //setting text
                if (j < numberOfColumns - 1) // regular case
                    TVs[i][j].setText(Numbers[i].substring(j * groupBy, (j + 1) * groupBy));
                else // irregular
                    TVs[i][j].setText(Numbers[i].substring(j * groupBy, Numbers[i].length())); // till the end of the string
                TVs[i][j].setBackgroundColor(BGColor(i, j));
                TVs[i][j].setPadding(3, 3, 3, 3);
                TVs[i][j].setGravity(Gravity.CENTER);
                TVs[i][j].setTextColor(getResources().getColor(R.color.memoTextColor));
                //TVs[i][j].setTextSize(R.dimen.tableTextSize);
                TVs[i][j].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tr.addView(TVs[i][j]);
            }
            tr.setId(1234 + i); // 1234, 1235, 1236, ...
            tr.setVisibility(View.INVISIBLE); // invisible
            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
        //TableRow buttonTr = NewTableRow();
        goButton = new Button(this);    //recall button
        goButton.setText("Recall");
        goButton.setVisibility(View.INVISIBLE); // invis
        goButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NumbersMemo.this, NumbersRecall.class);
                intent.putExtra("Numbers", Numbers);
                intent.putExtra("event", event);
                intent.putExtra("Time", SystemClock.uptimeMillis() - startTime);
                startActivity(intent);
                finish();
            }
        });
        tl.addView(goButton, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

    }

    void ShowTable() {
        goButton.setVisibility(View.VISIBLE);
        for (int i = 0; i < Numbers.length; i++)
            findViewById(1234 + i).setVisibility(View.VISIBLE);

    }

    char generateElementByEvent(String event, Random rn) {
        if (event.equals("letters"))
            return myAlphabetChars[rn.nextInt(myAlphabetChars.length)];
        if (event.equals("numbers"))
            return Character.forDigit(rn.nextInt(10), 10);
        if (event.equals("binaries"))
            return Character.forDigit(rn.nextInt(2), 10);
        return '?';
    }

    void GenerateNumbers() { // in global array String[] Numbers;
        int numberOfLines = (int) Math.ceil((float) amountOfDigits / digitsPerRow);
        Numbers = new String[numberOfLines];
        //generate numbers
        int digitsInRow = digitsPerRow;
        Random rn = new Random();
        for (int i = 0; i < numberOfLines; i++) {
            if ((i == numberOfLines - 1) && (amountOfDigits % digitsPerRow != 0)) // last row
                digitsInRow = amountOfDigits % digitsPerRow; // last N digits
            char[] charArray = new char[digitsInRow]; // current string as a char array
            for (int j = 0; j < digitsInRow; j++) {
                charArray[j] = generateElementByEvent(event, rn);
            }
            Numbers[i] = new String(charArray);// current string as a char array
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_memo);

        amountOfDigits = getIntent().getExtras().getInt("amountOfDigits");
        groupBy = getIntent().getExtras().getInt("groupBy");
        digitsPerRow = getIntent().getExtras().getInt("digitsPerRow");
        event = getIntent().getStringExtra("event");
        GenerateNumbers();
        CreateMaskedTable(); // requires global array String[] Numbers;
        StartCountDownTimer();//321 timer
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.numbers_memo, menu);
        return true;
    }

}

