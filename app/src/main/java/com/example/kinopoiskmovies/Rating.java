package com.example.kinopoiskmovies;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("kp")
    private final double kp;

    public Rating(double kp) {
        this.kp = kp;
    }

    public double getKp() {
        return kp;
    }

    @NonNull
    @Override
    public String toString() {
        return "Rating{" +
                "kp='" + kp + '\'' +
                '}';
    }
}
