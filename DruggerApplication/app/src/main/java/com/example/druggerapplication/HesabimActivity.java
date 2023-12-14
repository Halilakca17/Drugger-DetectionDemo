package com.example.druggerapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HesabimActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;
    private ImageView imageViewProfile;
    private static final String PREF_NAME = "MyPrefs";
    private static final String PREF_EMAIL = "email";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hesabim);

        imageViewProfile = findViewById(R.id.imageViewProfile);


        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askUserForImageSelection();
            }
        });


        TextView textViewEmail = findViewById(R.id.textViewEmail);



        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString(PREF_EMAIL, "");


        textViewEmail.setText("Email: " + userEmail);
        Button btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HesabimActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

    }



    // Kullanıcıya galeriden fotoğraf seçmek isteyip istemediğini soran metod
    private void askUserForImageSelection() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }


    // Kullanıcının galeriden seçtiği fotoğrafı işleyen metod
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            // Kullanıcı galeriden bir fotoğraf seçti
            Uri selectedImageUri = data.getData();
            // Seçilen fotoğrafı ImageView'a yükleyin
            imageViewProfile.setImageURI(selectedImageUri);
            // Burada seçilen fotoğrafı başka bir yere kaydetmek gibi işlemleri gerçekleştirebilirsiniz
            Toast.makeText(this, "Fotoğraf başarıyla seçildi", Toast.LENGTH_SHORT).show();
        }
    }
}
