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

import com.Roman.memorysportssim.R;
import com.Roman.memorysportssim.model.StatEntry;

import java.util.ArrayList;


public class StatActivity extends Activity {

    String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей", "Анна", "Денис", "Андрей" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        ListView lvStat = (ListView) findViewById(R.id.lvStat);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.stat_entry, names);

        lvStat.setAdapter(adapter);

    }

    private class StatEntryAdapter extends ArrayAdapter<StatEntry> {


        public StatEntryAdapter(Activity activity, ArrayList<StatEntry> entries) {

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder view;
            if (rowView == null) {
                // Get a new instance of the row layout view
                LayoutInflater inflater = activity.getLayoutInflater();
                rowView = inflater.inflate(R.layout.rowlayout, null);

                // Hold the view objects in an object, that way the don't need to be "re-  finded"
                view = new ViewHolder();
                view.retaurant_name= (TextView) rowView.findViewById(R.id.restaurantname);
                view.restaurant_address= (TextView) rowView.findViewById(R.id.textView1);

                rowView.setTag(view);
            }
            return super.getView(position, convertView, parent);
        }

        private static class ViewHolder {
            private TextView upperLine;
            private TextView bottomLine;
        }

    }

}