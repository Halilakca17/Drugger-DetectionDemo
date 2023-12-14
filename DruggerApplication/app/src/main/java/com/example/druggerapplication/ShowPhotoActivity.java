package com.example.druggerapplication;



import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.Random;

public class ShowPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

        ImageView imageView = findViewById(R.id.showImageView);
        TextView detectionText = findViewById(R.id.detectionText);

        // Fotoğraf çekildikten sonra
        if (getIntent().hasExtra("imageBitmap")) {
            Bitmap imageBitmap = getIntent().getParcelableExtra("imageBitmap");
            imageView.setImageBitmap(imageBitmap);


            int randomNumber = new Random().nextInt(100);
            detectionText.setText("Drugger Detection: " + randomNumber);
        }

        // Galeriden seçildikten sonra
        else if (getIntent().hasExtra("selectedImageUri")) {
            Uri selectedImageUri = getIntent().getParcelableExtra("selectedImageUri");

            try {
                // URI'yi kullanarak Bitmap oluştur
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                imageView.setImageBitmap(imageBitmap);


                int randomNumber = new Random().nextInt(100);
                detectionText.setText("Drugger Detection: " + randomNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
