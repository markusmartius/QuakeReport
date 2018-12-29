/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;
import android.content.Context;
import android.content.Loader;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {

    // set context for checking internet connection
    Context context = this;

    // set global variable to store internet connectivity
    boolean isConnected;

    /** TextView that is displayed when the internet state is false */
    private TextView mInternetState;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    /** TextView that is displayed when the loading is in progress */
    private ProgressBar mInProgress;

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    /** Adapter for the list of earthquakes */
    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // set the id for the empty state view
        mEmptyStateTextView = (TextView) findViewById(R.id.empty);

        // add the empty state as an option in the earthquakeListView object.
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Check if there is an internet connection
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected == true) {
            // set the id for the progress view
            mInProgress = (ProgressBar) findViewById(R.id.indeterminateBar);

            mInProgress.setVisibility(View.VISIBLE);

            // Create a new adapter that takes an empty list of earthquakes as input
            mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(mAdapter);

            // Set anonomous class (new AdapterV... )as it is only a single method. If there were
            // a number of methods we'd create a seperate class and call it from here.
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // get the {@link Earthquake} object at the given position the user clicked on
                    Earthquake currentEarthquake = mAdapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri earthquakeUri = Uri.parse(currentEarthquake.getURL());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                    // Could do this instead of previous three
                /*
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                */

                    startActivity(websiteIntent);
                }
            });
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
            Log.i(LOG_TAG, "In initLoader()");
        } else {
            // set the id for the internet state view
            mInternetState = (TextView) findViewById(R.id.internetState);

            // add the empty state as an option in the earthquakeListView object.
            earthquakeListView.setEmptyView(mInternetState);
            // Set empty state text to display "No earthquakes found."
            // will set everytime, but is not used yet and is a low cost.
            mEmptyStateTextView.setText(R.string.internet_state);
        }
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        Log.i(LOG_TAG, "In onCreateLoader()");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {

        // Set empty state text to display "No earthquakes found."
        // will set everytime, but is not used yet and is a low cost.
        mEmptyStateTextView.setText(R.string.empty);

        // Set progress bar to invisible
        mInProgress.setVisibility(View.GONE);

        // Clear the adapter of previous earthquake data
        Log.i(LOG_TAG, "In onLoadFinished()");
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // Loader reset, so we can clear out our existing data.
        Log.i(LOG_TAG, "In onLoaderReset()");
        mAdapter.clear();
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}





