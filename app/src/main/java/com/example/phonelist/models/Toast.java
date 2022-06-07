package com.example.phonelist.models;

import android.content.Context;

public class Toast {
    private final Context context;

    public Toast(Context context) {
        this.context = context;
    }

    public void showShortMessage(String message) {
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
    }

    public void showLongMessage(String message) {
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_LONG).show();
    }
}
