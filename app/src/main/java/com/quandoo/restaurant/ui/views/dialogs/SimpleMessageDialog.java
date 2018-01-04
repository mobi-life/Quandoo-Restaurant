package com.quandoo.restaurant.ui.views.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.quandoo.restaurant.R;

/**
 * Created by Behzad on 1/3/2018.
 */

public class SimpleMessageDialog extends BaseDialog {

    public static SimpleMessageDialog getInstance(String title, String message) {
        SimpleMessageDialog instance = new SimpleMessageDialog();
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
                .setPositiveButton(R.string.dialog_ok, null)
                .create();
    }
}
