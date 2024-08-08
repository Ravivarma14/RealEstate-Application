package com.example.realestateapp.presentation.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.realestateapp.R;
import com.example.realestateapp.databinding.ActivityHomeScreenBinding;
import com.example.realestateapp.presentation.ui.Adapter.HouseListAdapter;
import com.example.realestateapp.presentation.ui.Model.HouseModel;
import com.example.realestateapp.presentation.ui.SQLiteDB.SQLDBHelper;
import com.example.realestateapp.presentation.ui.Utils.BitmapUtils;

import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity {

    ActivityHomeScreenBinding homeScreenBinding;
    Context context;
    ArrayList<HouseModel> houseList;
    int isForUpdateOrRemove=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeScreenBinding= ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(homeScreenBinding.getRoot());

    }

    @Override
    protected void onResume() {
        super.onResume();
        isForUpdateOrRemove=getIntent().getIntExtra("isForUpdateOrRemove",-1);
        context=HomeScreenActivity.this;
        setViews();
        SQLDBHelper sqldbHelper=new SQLDBHelper(this);
        if(isForUpdateOrRemove==0 || isForUpdateOrRemove==1){
            Log.d("TAG", "onResume: update or remove: "+ isForUpdateOrRemove +" mail: "+ AdminChooseActivity.houseOwnerBundle.getString("email").trim());
            houseList=sqldbHelper.getOwnerHouseList(AdminChooseActivity.houseOwnerBundle.getString("email").trim());
        }
        else {
            houseList = sqldbHelper.getHouseList();
        }

        if (!houseList.isEmpty())
            setRecyclerView(houseList);
        else
            homeScreenBinding.tvNoHouses.setVisibility(View.VISIBLE);

        setListeners();
    }

    private void setListeners(){
        homeScreenBinding.btnLogout.setOnClickListener(v->{
            logout();
        });
    }
    private void setViews(){
        if(isForUpdateOrRemove==0){
            //for update
            homeScreenBinding.btnLogout.setVisibility(View.GONE);
            homeScreenBinding.llChooseUpdateTitleInfo.setVisibility(View.VISIBLE);
            homeScreenBinding.llHomeScreenInfo.setVisibility(View.GONE);
        } else if (isForUpdateOrRemove==1) {
            //for remove
            homeScreenBinding.btnLogout.setVisibility(View.GONE);
            homeScreenBinding.llChooseUpdateTitleInfo.setVisibility(View.VISIBLE);
            homeScreenBinding.tvChooseHouseTitle.setText("Choose House to Remove");
            homeScreenBinding.tvDetailsTitle.setVisibility(View.GONE);
            homeScreenBinding.llHomeScreenInfo.setVisibility(View.GONE);
        }
    }

    private void setRecyclerView(ArrayList<HouseModel> houseList){
        HouseListAdapter adapter = new HouseListAdapter(houseList, context, HomeScreenActivity.this, isForUpdateOrRemove);
        homeScreenBinding.rvHomeList.setAdapter(adapter);
        homeScreenBinding.rvHomeList.setLayoutManager(new LinearLayoutManager(this));
    }
    public void navDetailsActivity(HouseModel house,int isUpdateOrRemove) {
        Intent intent = null;
        if(isUpdateOrRemove==0) {
            intent=new Intent(this,AddHouseActivity.class);
            intent.putExtra("addHouse", false);
        }
        else if (isUpdateOrRemove==1) {
            intent=new Intent(this,DetailsActivity.class);
            intent.putExtra("isDelete", true);
        }
        else{
            intent=new Intent(this,DetailsActivity.class);
        }
        intent.putExtra("house",house);
        startActivity(intent);
        overridePendingTransition(R.animator.enter, R.animator.exit);
    }

    private void logout(){
        Intent intent = new Intent(context, LoginActivity.class);
        BitmapUtils.setLogin(this,"","",false);
        startActivity(intent);
        overridePendingTransition(R.animator.left_to_right, R.animator.right_to_left);
        finish();
    }
}