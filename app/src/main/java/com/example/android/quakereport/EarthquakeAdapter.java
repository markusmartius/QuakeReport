package com.example.android.quakereport;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    // New constructor
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> nameForArrayOfEarthquakeObjects) {
        // the zero means we're initially not passing a view as it will be passed later???
        super(context, 0, nameForArrayOfEarthquakeObjects);
        // Set colour of list item
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        // Get the earthquake magnitude from the current Earthquake object and
        // set this text on the name TextView
        magnitudeTextView.setText(currentEarthquake.getMagnitude());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView locationTextView = (TextView) listItemView.findViewById(R.id.location);
        // Get the earthquake location from the current Earthquake object and
        // set this text on the name TextView
        locationTextView.setText(currentEarthquake.getLocation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        // Get the earthquake date from the current Earthquake object and
        // set this text on the name TextView
        dateTextView.setText(currentEarthquake.getDate());

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
