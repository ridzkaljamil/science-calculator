package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    // Durasi splash screen dalam milidetik
    private static final int SPLASH_TIMEOUT = 2000; // 2 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Handler untuk menunda beralih ke MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Pindah ke MainActivity setelah timeout
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Tutup splash screen agar tidak bisa di-back
            }
        }, SPLASH_TIMEOUT);
    }
}