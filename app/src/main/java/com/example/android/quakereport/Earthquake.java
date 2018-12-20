package com.example.android.quakereport;

public class Earthquake {
    /**
     * Magnitude of the earthquake
     */ // the proceeding m just indicates a private variable
    private Double mMagnitude;
    /**
     * Location the earthquake occurred
     */
    private String mLocation;
    /**
     * The date when the earthquake occurred
     */
    private long mtimeInMilliseconds;

    /**
     * Create a new Earthquake object.
     *
     * @param magnitude             is the magnitude of the earthquake
     * @param location              is the location where the earthquake occurred
     * @param timeInMilliseconds    is the date the earthquake occurred
     *
     */
    public Earthquake(Double magnitude, String location, long timeInMilliseconds) {
        mMagnitude = magnitude;
        mLocation = location;
        mtimeInMilliseconds = timeInMilliseconds;
    }

    /**
     * Get the earthquake magnitude.
     */
    public Double getMagnitude() {
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

    public long getTimeInMilliseconds() {
        return mtimeInMilliseconds;
    }
}

