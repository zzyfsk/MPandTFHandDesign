package com.google.mediapipe.examples.handlandmarker.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.google.mediapipe.examples.handlandmarker.MainActivity;

public class MainActivityController {

    @SuppressLint("StaticFieldLeak")
    private static final MainActivityController mainActivityController = new MainActivityController();

    private MainActivityController(){}

    private Context context;
    private MainActivity activity;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = (MainActivity) context;
    }

    public Activity getActivity() {
        return (MainActivity) activity;
    }

    public void setActivity(Activity activity) {
        this.activity = (MainActivity) activity;
    }

    public static MainActivityController getMainActivityController() {
        return mainActivityController;
    }

    public void setText(String str){
        activity.setText(str);
    }
}
