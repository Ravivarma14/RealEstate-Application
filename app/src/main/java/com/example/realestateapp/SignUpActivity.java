package com.example.realestateapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.realestateapp.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding signUpBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signUpBinding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());

        setListeners();
    }
    private void setListeners(){
        signUpBinding.btnNavLogin.setOnClickListener(v->{
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.animator.left_to_right, R.animator.right_to_left);

            finish();
        });

        signUpBinding.btnSignup.setOnClickListener(v->{

        });
    }
}