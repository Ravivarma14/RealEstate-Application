package com.example.realestateapp.presentation.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestateapp.R;
import com.example.realestateapp.databinding.HomeListItemBinding;
import com.example.realestateapp.presentation.ui.AddHouseActivity;
import com.example.realestateapp.presentation.ui.DetailsActivity;
import com.example.realestateapp.presentation.ui.HomeScreenActivity;
import com.example.realestateapp.presentation.ui.Model.HouseModel;
import com.example.realestateapp.presentation.ui.Utils.BitmapUtils;

import java.util.ArrayList;

public class HouseListAdapter extends RecyclerView.Adapter<HouseListAdapter.HouseListItem> {

    ArrayList<HouseModel> houseList;
    Context context;
    HomeScreenActivity activity;
    int isUpdateOrRemove=-1;

    public HouseListAdapter(ArrayList<HouseModel> houseList, Context context, HomeScreenActivity activity,int isUpdateOrRemove) {
        this.houseList = houseList;
        this.context = context;
        this.activity=activity;
        this.isUpdateOrRemove=isUpdateOrRemove;
    }

    @NonNull
    @Override
    public HouseListItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeListItemBinding listItemBinding=HomeListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HouseListItem(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseListItem holder, int position) {
        HouseModel house= houseList.get(holder.getAdapterPosition());
        holder.ivHomePic.setImageBitmap(BitmapUtils.getImage(house.getHouseImage()));
        holder.tvHomeOrApartment.setText((house.getIsHouse()==1)? "Home" : "Apartment");
        holder.tvHomeName.setText(house.getHouseName());
        holder.tvAddress.setText(house.getAddress());
        holder.tvBHK.setText(""+house.getNoOfBedrooms());
        holder.tvPricePerMonth.setText(house.getPricePerMonth()+"/month");

        holder.itemView.setOnClickListener(v->{
           activity.navDetailsActivity(house,isUpdateOrRemove);
        });
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    public static class HouseListItem extends RecyclerView.ViewHolder{
        ImageView ivHomePic;
        TextView tvHomeOrApartment;
        TextView tvHomeName;
        TextView tvBHK;
        TextView tvAddress;
        TextView tvPricePerMonth;
        public HouseListItem(HomeListItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            ivHomePic=listItemBinding.ivHomePic;
            tvHomeOrApartment=listItemBinding.tvHomeApartment;
            tvHomeName=listItemBinding.tvHouseName;
            tvBHK=listItemBinding.tvBhk;
            tvAddress=listItemBinding.tvAddress;
            tvPricePerMonth=listItemBinding.tvPricePerMonth;
        }
    }
}
