package com.example.realestateapp.presentation.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.realestateapp.R;
import com.example.realestateapp.databinding.ActivityAdminChooseBinding;

public class AdminChooseActivity extends AppCompatActivity {

    ActivityAdminChooseBinding activityAdminChooseBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdminChooseBinding=ActivityAdminChooseBinding.inflate(getLayoutInflater());
        setContentView(activityAdminChooseBinding.getRoot());

        setListerners();
    }

    private void setListerners(){
        activityAdminChooseBinding.cvAddNewHouseContainer.setOnClickListener(v->{
            Intent intent=new Intent(this, AddHouseActivity.class);
            startActivity(intent);
            overridePendingTransition(R.animator.enter, R.animator.exit);
        });

        activityAdminChooseBinding.cvUpdateHouseContainer.setOnClickListener(v->{
            //use AddHouseActivity populated with existing details of House change in configuration of titles
            Intent intent=new Intent(this, AddHouseActivity.class);
            startActivity(intent);
            overridePendingTransition(R.animator.enter, R.animator.exit);
        });

        activityAdminChooseBinding.cvRemoveHouseContainer.setOnClickListener(v->{
            //Verify details to remove House use Details activity change in configuration of titles
            Intent intent=new Intent(this, DetailsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.animator.enter, R.animator.exit);
        });
    }
}