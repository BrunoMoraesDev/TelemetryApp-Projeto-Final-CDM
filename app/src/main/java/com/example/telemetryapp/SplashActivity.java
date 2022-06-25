package com.example.telemetryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(getMainLooper()).postDelayed(this::verificaLogin, 3000);
    }

    private void verificaLogin() {
        finish();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String emailUsuario = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            OperadorDAO operadorDAO = new OperadorDAO();
            boolean ehAdmin = operadorDAO.ehAdmin(emailUsuario);

            if (ehAdmin){
                startActivity(new Intent(this, AdminActivity.class));
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}