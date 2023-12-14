package com.example.druggerapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class InfoDialog {

    @SuppressLint("SetTextI18n")
    public static void showInfoDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_info, null);

        TextView textView = view.findViewById(R.id.dialog_info_text);
        textView.setText("Bu demo olduğu için bilgilendirme kısmı boş bırakılmıştır.");

        builder.setView(view);
        builder.setPositiveButton("Tamam", (dialog, which) -> {
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
