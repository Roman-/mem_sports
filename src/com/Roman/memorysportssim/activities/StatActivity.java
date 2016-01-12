package com.Roman.memorysportssim.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.Roman.memorysportssim.DbHelper;
import com.Roman.memorysportssim.R;
import com.Roman.memorysportssim.TimeUtils;
import com.Roman.memorysportssim.model.StatEntry;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class StatActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        ListView lvStat = (ListView) findViewById(R.id.lvStat);

        List<StatEntry> entries = DbHelper.listEntries(this, 100, 0);

        lvStat.setAdapter(new StatEntryAdapter(this, entries));

    }

    private static class StatEntryAdapter extends ArrayAdapter<StatEntry> {

        private static SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US);

        private Activity activity;
        private List<StatEntry> entries;

        public StatEntryAdapter(Activity activity, List<StatEntry> entries) {
            super(activity, R.layout.stat_layout_entry, entries);
            this.activity = activity;
            this.entries = entries;
        }

        private String getEventName(int eventId) {
            switch (eventId) {
                case 0:
                    return "Letters";
                case 1:
                    return "Numbers";
                case 2:
                    return "Binaries";
            }
            return "Unknown";
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder view;
            if (rowView == null) {
                // Get a new instance of the row layout view
                LayoutInflater inflater = activity.getLayoutInflater();
                rowView = inflater.inflate(R.layout.stat_layout_entry, null, true);

                // Hold the view objects in an object, that way the don't need to be "re-  finded"
                view = new ViewHolder();
                view.txtDate = (TextView) rowView.findViewById(R.id.txtDate);
                view.upperLine= (TextView) rowView.findViewById(R.id.tvTop);
                view.bottomLine= (TextView) rowView.findViewById(R.id.tvBottom);

                rowView.setTag(view);
            } else {
                view = (ViewHolder) rowView.getTag();
            }

            StatEntry e = entries.get(position);
            view.txtDate.setText(SDF.format(e.getDate()));
            view.upperLine.setText(getEventName(e.getEvent()) + ": " + e.getSuccess() + "/" + e.getDigits());

            String bottomText = "Memo: " + TimeUtils.formatMillis(e.getMemMillis()) + "(" +
            TimeUtils.formatMillis(e.getMemMillis() / e.getDigits()) + "), recall: " +
                    TimeUtils.formatMillis(e.getRecallMillis());

            view.bottomLine.setText(bottomText);
            return rowView;
        }

        private static class ViewHolder {
            private TextView txtDate;
            private TextView upperLine;
            private TextView bottomLine;
        }

    }

}