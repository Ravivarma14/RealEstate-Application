package com.example.realestateapp.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realestateapp.R;
import com.example.realestateapp.databinding.ActivitySignUpBinding;
import com.example.realestateapp.presentation.ui.SQLiteDB.SQLDBHelper;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding signUpBinding;
    SQLDBHelper sqldbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signUpBinding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());
        sqldbHelper=new SQLDBHelper(this);
        setListeners();
    }
    private void setListeners(){
        signUpBinding.btnNavLogin.setOnClickListener(v->{
            navLoginActivity();
        });

        signUpBinding.btnSignup.setOnClickListener(v->{
            String email=signUpBinding.etEmail.getText().toString().trim();
            String fullname=signUpBinding.etFullname.getText().toString().trim();
            String password=signUpBinding.etPassword.getText().toString().trim();
            if(email.isEmpty()||fullname.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Enter all fields",Toast.LENGTH_SHORT).show();
            }
            else{
                if(email.endsWith(".com") || email.endsWith(".COM")) {
                    if (password.toCharArray().length < 6) {
                        Toast.makeText(this, "Password should be least 6 characters", Toast.LENGTH_SHORT).show();
                    } else {
                        if (sqldbHelper.checkUserExists(email)) {
                            Toast.makeText(this,"User already exist with given Email",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            boolean insert=sqldbHelper.registerUser(email,fullname,password);
                            if(insert){
                                Toast.makeText(this,"Registration Successful! Please Login",Toast.LENGTH_SHORT).show();
                                navLoginActivity();
                            }
                            else {
                                Toast.makeText(this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(this,"Please enter valid email",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void navLoginActivity(){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.animator.left_to_right, R.animator.right_to_left);

        finish();
    }
}