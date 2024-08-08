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

    public static Bundle houseOwnerBundle;
    ActivityAdminChooseBinding activityAdminChooseBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdminChooseBinding=ActivityAdminChooseBinding.inflate(getLayoutInflater());
        setContentView(activityAdminChooseBinding.getRoot());
        houseOwnerBundle = getIntent().getExtras();
        setListerners();
    }

    private void setListerners(){
        activityAdminChooseBinding.cvAddNewHouseContainer.setOnClickListener(v->{
            Intent intent=new Intent(this, AddHouseActivity.class);
            intent.putExtra("addHouse",true);
            startActivity(intent);
            overridePendingTransition(R.animator.enter, R.animator.exit);
        });

        activityAdminChooseBinding.cvUpdateHouseContainer.setOnClickListener(v->{
            //use AddHouseActivity populated with existing details of House change in configuration of titles
            Intent intent=new Intent(this, HomeScreenActivity.class);
            intent.putExtra("isForUpdateOrRemove",0);
            startActivity(intent);
            overridePendingTransition(R.animator.enter, R.animator.exit);
        });

        activityAdminChooseBinding.cvRemoveHouseContainer.setOnClickListener(v->{
            //Verify details to remove House use Details activity change in configuration of titles
            Intent intent=new Intent(this, HomeScreenActivity.class);
            intent.putExtra("isForUpdateOrRemove",1);
            startActivity(intent);
            overridePendingTransition(R.animator.enter, R.animator.exit);
        });

        activityAdminChooseBinding.btnLogout.setOnClickListener(v->{
            logout();
        });
    }

    private void logout(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.animator.left_to_right, R.animator.right_to_left);
    }
}