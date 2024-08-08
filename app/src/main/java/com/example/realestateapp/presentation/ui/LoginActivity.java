package com.example.realestateapp.presentation.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realestateapp.R;
import com.example.realestateapp.databinding.ActivityLoginBinding;
import com.example.realestateapp.presentation.ui.SQLiteDB.SQLDBHelper;
import com.example.realestateapp.presentation.ui.Utils.BitmapUtils;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding loginBinding;
    SQLDBHelper sqldbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        int isLoggedin=BitmapUtils.isLogin(this);
        if(isLoggedin!=-1 && isLoggedin!=1) {
            navHomeScreen(this, "done", "done",false);
        }
        setContentView(loginBinding.getRoot());

        sqldbHelper=new SQLDBHelper(this);
        setListeners();
    }

    private void setListeners(){
        loginBinding.btnLogin.setOnClickListener(v->{
            String email=loginBinding.etEmail.getText().toString().trim();
            String password=loginBinding.etPassword.getText().toString().trim();

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Enter all fields",Toast.LENGTH_SHORT).show();
            }
            else{
                if(email.endsWith(".com") || email.endsWith(".COM")){
                    /*if(email.equals("admin@gmail.com") && password.equals("987654")){
                        adminLogin();
                        return;
                    }*/
                    Bundle loginBundle=sqldbHelper.login(email,password);
                    boolean canLogin=loginBundle.getBoolean("canLogin");
                    int isHouseOwner=loginBundle.getInt("isHouseOwner");
                    Log.d("TAG", "setListeners: houseowner2 ::"+isHouseOwner);
                    if(canLogin){
                        if(isHouseOwner==1)
                            adminLogin(loginBundle);
                        else
                            navHomeScreen(this, email, password, isHouseOwner==1);
                    }
                    else{
                        Toast.makeText(this,"No account found, please Signup first",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this,"Please enter valid email",Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginBinding.btnNavSignup.setOnClickListener(v->{
            Intent intent=new Intent(this, SignUpActivity.class);
            startActivity(intent);
            overridePendingTransition(R.animator.enter, R.animator.exit);

            finish();
        });
    }

    private void adminLogin(Bundle houseOwnerbundle){
        Intent intent=new Intent(this, AdminChooseActivity.class);
        intent.putExtras(houseOwnerbundle);
        startActivity(intent);
        overridePendingTransition(R.animator.enter, R.animator.exit);

        finish();
    }

    private void navHomeScreen(Context context, String email, String password, boolean isHouseOwner){
        Intent intent=new Intent(context, HomeScreenActivity.class);
        BitmapUtils.setLogin(context,email,password, isHouseOwner);
        startActivity(intent);
        overridePendingTransition(R.animator.enter, R.animator.exit);
        Toast.makeText(context,"Login successful",Toast.LENGTH_SHORT).show();
        finish();
    }
}