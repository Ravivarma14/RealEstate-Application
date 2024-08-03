package com.example.realestateapp.presentation.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.realestateapp.R;
import com.example.realestateapp.databinding.ActivityAddHouseBinding;
import com.example.realestateapp.databinding.ActivityAdminChooseBinding;

public class AddHouseActivity extends AppCompatActivity {

    ActivityAddHouseBinding addHouseBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addHouseBinding=ActivityAddHouseBinding.inflate(getLayoutInflater());
        setContentView(addHouseBinding.getRoot());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.left_to_right, R.animator.right_to_left);
    }
}