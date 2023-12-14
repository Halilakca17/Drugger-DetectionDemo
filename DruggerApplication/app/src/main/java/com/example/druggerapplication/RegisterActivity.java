package com.example.druggerapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class RegisterActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "user_preferences";
    private EditText edtRegisterEmail, edtRegisterPassword;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        edtRegisterPassword = findViewById(R.id.editTextRegisterPassword);

        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnGoogleRegister = findViewById(R.id.btnGoogleRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String registerEmail = edtRegisterEmail.getText().toString();
                String registerPassword = edtRegisterPassword.getText().toString();

                if (!registerEmail.isEmpty() && !registerPassword.isEmpty() ) {
                    // SharedPreferences'e kaydet
                    SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email", registerEmail);
                    editor.putString("password", registerPassword);
                    editor.apply();

                    Toast.makeText(RegisterActivity.this, "Kayıt işlemi başarılı", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Lütfen tüm bilgileri doldurun", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGoogleRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Google ile kayıt olma
                // GoogleSignInOptions nesnesini oluştur
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

                // GoogleSignInClient nesnesini oluştur
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(RegisterActivity.this, gso);

                // Google hesabı seçim ekranını başlat
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String googleIdToken = account.getIdToken();
            String userEmail = account.getEmail();
            String userName = account.getDisplayName();

            // SharedPreferences'e kaydet
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email", userEmail);
            editor.putString("displayName", userName);
            editor.apply();

            Toast.makeText(this, "Google ile kayıt başarılı", Toast.LENGTH_SHORT).show();
            // Google ile kayıt başarılı, account nesnesini kullanarak işlemlerinizi gerçekleştirin.
            // Örneğin: updateUI(account);

            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        } catch (ApiException e) {
            Log.w(TAG, "Google ile kayıt başarısız, signInResult:failed code=" + e.getStatusCode());
            // Google ile kayıt başarısız, gerekirse hata işlemlerini gerçekleştirin.
        }
    }
}
