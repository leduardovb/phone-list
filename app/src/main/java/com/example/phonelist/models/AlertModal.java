package com.example.phonelist.models;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertModal {

    private final Context context;
    private AlertDialog.Builder builder;

    public AlertModal(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }

    public void buildModal(String title, String message) {
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
    }

    public void buildModal(String title, String message, boolean cancelable) {
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(cancelable);
    }

    public void setPositiveButton(String label, DialogInterface.OnClickListener onClick) {
        builder.setPositiveButton(label, onClick);
    }

    public void setNegativeButton(String label, DialogInterface.OnClickListener onClick) {
        builder.setNegativeButton(label, onClick);
    }

    public void show() {
        builder.create().show();
    }

    public void reset() {
        builder = new AlertDialog.Builder(context);
    }

    public void reset(String title, String message) {
        builder = new AlertDialog.Builder(context);
        buildModal(title, message);
    }

    public void reset(String title, String message, boolean cancelable) {
        builder = new AlertDialog.Builder(context);
        buildModal(title, message, cancelable);
    }

}
