package com.example.wallpaper.Model;


import android.content.SharedPreferences;

public class Rate {

    private static final String RATED_KEY = "rated";
    private static SharedPreferences sharedPreferences;

    // Initialize the SharedPreferences instance
    public static void initialize(SharedPreferences sharedPreferences) {
        Rate.sharedPreferences = sharedPreferences;
    }

    // Check if the item has been rated
    public static boolean isRated() {
        return sharedPreferences.getBoolean(RATED_KEY, false);
    }

    // Set the rating status
    public static void setRated(boolean isRated) {
        sharedPreferences.edit().putBoolean(RATED_KEY, isRated).apply();
    }
}