package com.example.android.quakereport;

public class Earthquake {
    /**
     * Magnitude of the earthquake
     */ // the proceeding m just indicates a private variable
    private String mMagnitude;
    /**
     * Location the earthquake occurred
     */
    private String mLocation;
    /**
     * The date when the earthquake occurred
     */
    private String mDate;

    /**
     * Create a new Earthquake object.
     *
     * @param magnitude     is the magnitude of the earthquake
     * @param location      is the location where the earthquake occurred
     * @param date          is the date the earthquake occurred
     *
     */
    public Earthquake(String magnitude, String location, String date) {
        mMagnitude = magnitude;
        mLocation = location;
        mDate = date;
    }

    /**
     * Get the earthquake magnitude.
     */
    public String getMagnitude() {
        return mMagnitude;
    }

    /**
     * Get the earthquake location.
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * Get the date the earthquake occurred.
     */

    public String getDate() {
        return mDate;
    }
}

