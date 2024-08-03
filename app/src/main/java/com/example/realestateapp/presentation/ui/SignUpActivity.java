package com.example.realestateapp.presentation.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realestateapp.R;
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