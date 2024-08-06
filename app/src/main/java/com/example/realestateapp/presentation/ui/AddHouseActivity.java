package com.example.realestateapp.presentation.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.realestateapp.R;
import com.example.realestateapp.databinding.ActivityAddHouseBinding;
import com.example.realestateapp.databinding.ActivityAdminChooseBinding;
import com.example.realestateapp.presentation.ui.Model.HouseModel;
import com.example.realestateapp.presentation.ui.SQLiteDB.SQLDBHelper;
import com.example.realestateapp.presentation.ui.Utils.BitmapUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.Executors;

public class AddHouseActivity extends AppCompatActivity {

    ActivityAddHouseBinding addHouseBinding;
    Bitmap selectedHouseImage;
    boolean addHouse;
    int isHouse=-1;
    String address="";
    SQLDBHelper sqldbHelper;
    Handler handler;
    boolean success=false;
    boolean isInProgress=false;
    static double latitude;
    static double longitude;
    HouseModel houseModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addHouseBinding=ActivityAddHouseBinding.inflate(getLayoutInflater());
        setContentView(addHouseBinding.getRoot());
        addHouse=getIntent().getBooleanExtra("addHouse",true);
        houseModel=getIntent().getSerializableExtra("house",HouseModel.class);
        if(houseModel!=null){
            latitude=houseModel.getLatitude();
            longitude=houseModel.getLongitude();
        }

        handler=new Handler(Looper.getMainLooper());
        sqldbHelper=new SQLDBHelper(this);
        setViews();
        setListeners();

    }

    private void setViews(){
        if(!addHouse) {
            addHouseBinding.btnAddOrUpdate.setText("Update House Details");
            addHouseBinding.tvTitle.setText("Update House Details");
            addHouseBinding.tvBtnUploadImg.setText("Change image");

            loadDataFromHouse(houseModel);
        }
    }

    private void loadDataFromHouse(HouseModel houseModel){
        if(houseModel!=null){
            addHouseBinding.ivHomePicture.setImageBitmap(BitmapUtils.getImage(houseModel.getHouseImage()));
            if ((houseModel.getIsHouse() == 1)) {
                addHouseBinding.tvHome.setBackgroundColor(getColor(R.color.selected_toggle_bg_color));
                addHouseBinding.tvHome.setTextColor(getColor(R.color.black));
                addHouseBinding.tvApartment.setBackgroundColor(getColor(R.color.unselected_toggle_bg_color));
                isHouse = 1;
            } else {
                addHouseBinding.tvApartment.setBackgroundColor(getColor(R.color.selected_toggle_bg_color));
                addHouseBinding.tvApartment.setTextColor(getColor(R.color.black));
                addHouseBinding.tvHome.setBackgroundColor(getColor(R.color.unselected_toggle_bg_color));
                isHouse = 0;
            }
            addHouseBinding.etHouseName.setText(houseModel.getHouseName());
            addHouseBinding.etSqft.setText(""+houseModel.getSqft());
            addHouseBinding.etNoOfBedrooms.setText(""+houseModel.getNoOfBedrooms());
            addHouseBinding.etNoOfBathrooms.setText(""+houseModel.getNoOfBathrooms());
            addHouseBinding.etHouseOwnerName.setText(houseModel.getHouseAddedBy());
            addHouseBinding.etFacilities.setText(houseModel.getFacilities());
            addHouseBinding.tvDescriptionAddress.setText(houseModel.getAddress());
            addHouseBinding.etPrice.setText(""+houseModel.getPricePerMonth());
        }
    }

    private void setListeners(){
        addHouseBinding.tvBtnUploadImg.setOnClickListener(v->{
            if(!isInProgress) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        addHouseBinding.btnAddOrUpdate.setOnClickListener(v->{
            if(!isInProgress) {
                isInProgress = true;
                addHouseBinding.loading.setVisibility(View.VISIBLE);

                if (addHouse) {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        insertHouse();
                        runOnUiThread(() -> {
                            isInProgress = false;
                            addHouseBinding.loading.setVisibility(View.GONE);
                            if (success)
                                onBackPressed();
                        });
                    });
                } else {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        updateHouse();
                        runOnUiThread(() -> {
                            isInProgress = false;
                            addHouseBinding.loading.setVisibility(View.GONE);
                            if (success)
                                onBackPressed();
                        });
                    });
                }
            }
        });
        addHouseBinding.tvHome.setOnClickListener(v->{
            if(!isInProgress) {
                addHouseBinding.tvHome.setBackgroundColor(getColor(R.color.selected_toggle_bg_color));
                addHouseBinding.tvHome.setTextColor(getColor(R.color.black));
                addHouseBinding.tvApartment.setBackgroundColor(getColor(R.color.unselected_toggle_bg_color));
                isHouse = 1;
            }
        });
        addHouseBinding.tvApartment.setOnClickListener(v->{
            if(!isInProgress) {
                addHouseBinding.tvApartment.setBackgroundColor(getColor(R.color.selected_toggle_bg_color));
                addHouseBinding.tvApartment.setTextColor(getColor(R.color.black));
                addHouseBinding.tvHome.setBackgroundColor(getColor(R.color.unselected_toggle_bg_color));
                isHouse = 0;
            }
        });
        addHouseBinding.btnChooseOnMap.setOnClickListener(v->{
            if(!isInProgress){
                Intent intent=new Intent(this, MapActivity.class);
                intent.putExtra("chooseOnMap",true);
                intent.putExtra("latitude",latitude);
                intent.putExtra("logintude",longitude);
                startActivityForResult(intent, 400);
            }
        });
        addHouseBinding.ivBtnBack.setOnClickListener(v->{
            onBackPressed();
        });
    }

    private void insertHouse(){
        HouseModel house=getHouseModel();
        if(house!=null){
            if(sqldbHelper.addHouse(house)){
                success=true;
                handler.post(()->{Toast.makeText(this, "House added successfully",Toast.LENGTH_SHORT).show();
                });
            }
            else{
                handler.post(()->Toast.makeText(this, "Error while adding House",Toast.LENGTH_SHORT).show());
            }
        }
        else{
            Log.d("TAG", "insertHouse: house is null");
        }
    }
    private void updateHouse(){
        HouseModel house=getHouseModel();
        if(house!=null){
            if(sqldbHelper.updateHouseDetails(house)){
                success=true;
                handler.post(()->{
                    Toast.makeText(this, "House updated successfully",Toast.LENGTH_SHORT).show();
                });
            }
            else{
                handler.post(()->Toast.makeText(this, "Error while updating House",Toast.LENGTH_SHORT).show());
            }
        }
    }

    private HouseModel getHouseModel(){
        HouseModel house = null;
            if (isHouse == -1) {
                handler.post(()->{
                    Toast.makeText(this, "Please choose House or Apartment", Toast.LENGTH_SHORT).show();
                });
                return null;
            } else if (addHouseBinding.etHouseName.getText().toString().isEmpty() || addHouseBinding.etFacilities.getText().toString().isEmpty() || addHouseBinding.etNoOfBathrooms.getText().toString().isEmpty() || addHouseBinding.etNoOfBedrooms.getText().toString().isEmpty() || addHouseBinding.etSqft.getText().toString().isEmpty() || addHouseBinding.etHouseOwnerName.getText().toString().isEmpty() || addHouseBinding.etPrice.getText().toString().isEmpty()|| addHouseBinding.tvDescriptionAddress.getText().toString().isEmpty()) {
                handler.post(()->Toast.makeText(this, "Please Enter all fields", Toast.LENGTH_SHORT).show());
                return null;
            }
            int id;
            byte[] imageToStore;
            if(addHouse) {
                id = -1;
                imageToStore=BitmapUtils.getBytes(selectedHouseImage);
            }
            else{ id=houseModel.getId();
                imageToStore=houseModel.getHouseImage();
            }

             house = new HouseModel(id, isHouse, addHouseBinding.etHouseName.getText().toString(),
                    Integer.parseInt(addHouseBinding.etSqft.getText().toString().trim()),
                    Integer.parseInt(addHouseBinding.etNoOfBedrooms.getText().toString().trim()),
                    Integer.parseInt(addHouseBinding.etNoOfBathrooms.getText().toString().trim()),
                    addHouseBinding.etHouseOwnerName.getText().toString(),
                    addHouseBinding.etFacilities.getText().toString(),
                    address, Integer.parseInt(addHouseBinding.etPrice.getText().toString()),
                    imageToStore,latitude, longitude);

        return house;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode==RESULT_OK){
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                //set house image and assign to house instance
                selectedHouseImage=yourSelectedImage;
                addHouseBinding.ivHomePicture.setImageBitmap(selectedHouseImage);
            }
        }
        if(requestCode==400){
            if(resultCode==RESULT_OK){
                    latitude = data.getDoubleExtra("latitude", -1);
                    longitude = data.getDoubleExtra("longitude", -1);
                    address = data.getStringExtra("address");
                    if (address != null && !address.isEmpty())
                        addHouseBinding.tvDescriptionAddress.setText(address);
                    //Toast.makeText(this, "add: " + address + " lat: " + latitude + " long: " + longitude, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(!isInProgress){
        super.onBackPressed();
        overridePendingTransition(R.animator.left_to_right, R.animator.right_to_left);
        }
    }
}