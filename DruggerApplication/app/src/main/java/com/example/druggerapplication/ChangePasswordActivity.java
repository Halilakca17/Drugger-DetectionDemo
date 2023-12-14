package com.example.druggerapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "user_preferences";

    private EditText editTextNewPassword;
    private Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedPassword = preferences.getString("password", "");

        String newPassword = editTextNewPassword.getText().toString();

        if (newPassword.equals(savedPassword)) {

            Toast.makeText(this, "Yeni şifre eski şifre ile aynı olamaz", Toast.LENGTH_SHORT).show();
        } else {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("password", newPassword);
            editor.apply();

            Toast.makeText(this, "Şifre başarıyla değiştirildi", Toast.LENGTH_SHORT).show();

            finish();
        }
    }
}
