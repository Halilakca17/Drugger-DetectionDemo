package com.example.druggerapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;



public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private ImageView imageView;
    private TextView detectionText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Button btnTakePhoto = findViewById(R.id.btnTakePhoto);
        Button btnChooseFromGallery = findViewById(R.id.btnChooseFromGallery);
        Button btnInfo = findViewById(R.id.bilgilendirme);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HesabimActivity.class);
            startActivity(intent);
        });

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Fotoğraf çekme işlemini başlat
                dispatchTakePictureIntent();
            }
        });

        btnChooseFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchChooseFromGalleryIntent();
            }
        });


        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoDialog();
            }
        });
    }






    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Çıkış Yap")
                .setMessage("Çıkış yapmak istediğinize emin misiniz?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // Uygulamadan çık
                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void dispatchChooseFromGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Fotoğraf Seç"), REQUEST_IMAGE_PICK);
    }

    private void showInfoDialog() {
        // Uzun bilgilendirme metni
        String infoText = "Uygulama'nın temel amacı şüphelendiğiniz insanların yüzlerinin fotoğrafı sayesinde yapay zeka modeli kullanarak bir tahminde bulunmaktır." +
                "Sonuçlar her ne kadar doğruluk payı içersede bu modele bakarak birinin uyuşturucu bağımlısı olup olmadığını söylemek mümkün değildir." +
                "Uygulamamız polisler ve ebeveynlerin kullanımı için geliştirilmiştir. ";


        ScrollView scrollView = new ScrollView(this);
        TextView textView = new TextView(this);
        textView.setText(infoText);
        scrollView.addView(textView);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bilgilendirme");
        builder.setView(scrollView);
        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Kullanıcı "Tamam" dediğinde hiçbir şey yapma
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Fotoğraf çekme sonuçları işleme
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    showPhotoActivity(imageBitmap);
                }
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null && data.getData() != null) {
                // Galeriden seçilen fotoğrafın URI'sini al
                Uri selectedImageUri = data.getData();

                showPhotoActivity2(selectedImageUri);
            }
        }
    }
    private void showPhotoActivity(Bitmap imageBitmap) {
        Intent intent = new Intent(HomeActivity.this, ShowPhotoActivity.class);
        intent.putExtra("imageBitmap", imageBitmap); // Fotoğrafı geçir
        startActivity(intent);
    }

    private void showPhotoActivity2(Uri imageUri) {
        Intent intent = new Intent(HomeActivity.this, ShowPhotoActivity.class);
        intent.putExtra("selectedImageUri", imageUri); // URI'yi geçir
        startActivity(intent);
    }
}
