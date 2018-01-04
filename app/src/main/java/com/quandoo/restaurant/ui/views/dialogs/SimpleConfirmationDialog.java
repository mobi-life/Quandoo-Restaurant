package com.quandoo.restaurant.ui.views.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.quandoo.restaurant.R;

/**
 * Created by Behzad on 1/3/2018.
 */

public class SimpleConfirmationDialog extends BaseDialog {

    private DialogInterface.OnClickListener mOnPositiveClicked;

    public static SimpleConfirmationDialog getInstance(String title, String message) {
        SimpleConfirmationDialog instance = new SimpleConfirmationDialog();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        instance.setArguments(args);
        return instance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = args.getString(ARG_TITLE);
        String message = args.getString(ARG_MESSAGE);
        return new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton(R.string.dialog_no, null)
                .setPositiveButton(R.string.dialog_yes, mOnPositiveClicked)
                .create();
    }


    public SimpleConfirmationDialog setOnPositiveClicked(DialogInterface.OnClickListener onPositiveClicked) {
        this.mOnPositiveClicked = onPositiveClicked;
        return this;
    }
}
