package com.example.realestateapp.presentation.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realestateapp.R;
import com.example.realestateapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding loginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        setListeners();
    }

    private void setListeners(){
        loginBinding.btnLogin.setOnClickListener(v->{

        });

        loginBinding.btnNavSignup.setOnClickListener(v->{
            Intent intent=new Intent(this, SignUpActivity.class);
            startActivity(intent);
            overridePendingTransition(R.animator.enter, R.animator.exit);

            finish();
        });
    }
}