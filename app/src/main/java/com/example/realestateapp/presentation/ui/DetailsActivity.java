package com.example.realestateapp.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.realestateapp.R;
import com.example.realestateapp.databinding.ActivityDetailsBinding;
import com.example.realestateapp.presentation.ui.Model.HouseModel;
import com.example.realestateapp.presentation.ui.SQLiteDB.SQLDBHelper;
import com.example.realestateapp.presentation.ui.Utils.BitmapUtils;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding detailsBinding;
    boolean isDelete;
    SQLDBHelper sqldbHelper;
    HouseModel houseModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding=ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(detailsBinding.getRoot());
        isDelete=getIntent().getBooleanExtra("isDelete",false);
        houseModel=getIntent().getSerializableExtra("house",HouseModel.class);
        sqldbHelper=new SQLDBHelper(this);

        setViews();
        setListeners();
    }
    private void setViews(){
        int visibility=(isDelete)? View.VISIBLE : View.GONE;
        detailsBinding.btnRemove.setVisibility(visibility);
        detailsBinding.tvTitle.setText((isDelete)? "Verify House Details":"Details of House");

        detailsBinding.ivHomePicture.setImageBitmap(BitmapUtils.getImage(houseModel.getHouseImage()));
        detailsBinding.tvHomeApartment.setText((houseModel.getIsHouse()==1)?"Home":"Apartment");
        detailsBinding.tvHouseName.setText(houseModel.getHouseName());
        detailsBinding.tvAddress.setText(houseModel.getAddress());
        detailsBinding.tvSqft.setText(""+houseModel.getSqft());
        detailsBinding.tvNoOfBedrooms.setText(""+houseModel.getNoOfBedrooms());
        detailsBinding.tvNoOfBathrooms.setText(""+houseModel.getNoOfBathrooms());
        detailsBinding.tvHouseAddedByName.setText(houseModel.getHouseAddedBy());
        detailsBinding.tvDetailsOfFacilities.setText(houseModel.getFacilities());
        //TODO address has name address and long-lat (had to think)
        detailsBinding.tvDescriptionAddress.setText(houseModel.getAddress());
        detailsBinding.tvPrice.setText(""+houseModel.getPricePerMonth());
    }

    private void setListeners(){
        detailsBinding.ivBtnBack.setOnClickListener(v->{
            onBackPressed();
        });
        detailsBinding.btnViewOnMap.setOnClickListener(v->{
            //navigate to map
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("chooseOnMap",false);
            intent.putExtra("latitude",houseModel.getLatitude());
            intent.putExtra("logintude",houseModel.getLongitude());
            startActivity(intent);
            overridePendingTransition(R.animator.enter, R.animator.exit);
        });
        detailsBinding.btnRemove.setOnClickListener(v->{
            if(sqldbHelper.removeHouse(houseModel.getId())){
                Toast.makeText(this,"House removed successfully",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.left_to_right, R.animator.right_to_left);
    }
}