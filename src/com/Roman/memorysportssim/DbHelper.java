package com.Roman.memorysportssim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.Roman.memorysportssim.model.StatEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "memorySportSim", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE stat(id integer primary key autoincrement, " +
                "date integer, " +
                "digits integer, " +
                "success integer, " +
                "mem_millis integer, " +
                "recall_millis integer, " +
                "event integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void addStatEntry(Context ctx, int digits, int success, int memMillis, int recallMillis, int event) {
        DbHelper helper = new DbHelper(ctx);
        ContentValues cv = new ContentValues();
        cv.put("date", System.currentTimeMillis());
        cv.put("digits", digits);
        cv.put("success", success);
        cv.put("mem_millis", memMillis);
        cv.put("recall_millis", recallMillis);
        cv.put("event", event);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert("stat", null, cv);
        helper.close();
    }

    public static List<StatEntry> listEntries(Context ctx, int limit, int offset) {
        DbHelper helper = new DbHelper(ctx);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("stat", null, null, null, null, null, "date desc", offset + "," + limit);
        List<StatEntry> res = new ArrayList<StatEntry>();
        if (c.moveToFirst()) {
            int dateIdx = c.getColumnIndex("date");
            int digitsIdx = c.getColumnIndex("digits");
            int successIdx = c.getColumnIndex("success");
            int memMillisIdx = c.getColumnIndex("mem_millis");
            int recallMillisIdx = c.getColumnIndex("recall_millis");
            int event = c.getColumnIndex("event");
            do {
                StatEntry e = new StatEntry();
                e.setDate(new Date(c.getLong(dateIdx)));
                e.setDigits(c.getInt(digitsIdx));
                e.setSuccess(c.getInt(successIdx));
                e.setMemMillis(c.getInt(memMillisIdx));
                e.setRecallMillis(c.getInt(recallMillisIdx));
                e.setEvent(c.getInt(event));
                res.add(e);
            } while (c.moveToNext());
        }
        c.close();
        helper.close();
        return res;
    }

}
