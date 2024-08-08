package com.example.realestateapp.presentation.ui.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.realestateapp.presentation.ui.Model.HouseModel;

import java.util.ArrayList;

public class SQLDBHelper extends SQLiteOpenHelper {
    public static final String DBNAME="RealEstate.db";

    public SQLDBHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users(email TEXT primary key, name TEXT, password TEXT, isHouseOwner INTEGER)");
        db.execSQL("create Table houses(id INTEGER primary key AUTOINCREMENT, isHouse INTEGER, houseName TEXT, sqft INTEGER, noOfBedrooms INTEGER, noOfBathrooms INTEGER, houseAddedBy TEXT, facilities TEXT, address TEXT, pricePerMonth INTEGER, houseImage BLOB, latitude REAL, longitude REAL, addedByEmail TEXT)");
    }

    public ArrayList<HouseModel> getHouseList()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorHouses = db.rawQuery("select * from houses",null);
        ArrayList<HouseModel> houseModelArrayList = getHouseListFromQuery(cursorHouses);
        return houseModelArrayList;
    }

    public ArrayList<HouseModel> getOwnerHouseList(String email){
        Log.d("TAG", "getOwnerHouseList: home owner: "+ email);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorHouses = db.rawQuery("select * from houses where addedByEmail=?",new String[]{email});
        ArrayList<HouseModel> houseModelArrayList = getHouseListFromQuery(cursorHouses);
        Log.d("TAG", "getOwnerHouseList: update house list: "+ cursorHouses.getCount());
        return houseModelArrayList;
    }

    public ArrayList<HouseModel> getHouseListFromQuery(Cursor cursorHouses){
        ArrayList<HouseModel> houseModelArrayList = new ArrayList<>();

        if (cursorHouses.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                Log.d("TAG", "getHouseListFromQuery: house added by: "+ cursorHouses.getString(13) +" housenmae: "+cursorHouses.getString(2));
                houseModelArrayList.add(new HouseModel(cursorHouses.getInt(0),
                        cursorHouses.getInt(1),
                        cursorHouses.getString(2),
                        cursorHouses.getInt(3),
                        cursorHouses.getInt(4),
                        cursorHouses.getInt(5),
                        cursorHouses.getString(6),
                        cursorHouses.getString(7),
                        cursorHouses.getString(8),
                        cursorHouses.getInt(9),
                        cursorHouses.getBlob(10),
                        cursorHouses.getDouble(11),
                        cursorHouses.getDouble(12),
                        cursorHouses.getString(13)
                ));
            } while (cursorHouses.moveToNext());
        }
        cursorHouses.close();
        return houseModelArrayList;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
        db.execSQL("drop Table if exists houses");
    }

    private ContentValues getContentValueObject(HouseModel house){
        ContentValues contentValues=new ContentValues();
        contentValues.put("isHouse",house.isHouse());
        contentValues.put("houseName",house.getHouseName());
        contentValues.put("sqft",house.getSqft());
        contentValues.put("noOfBedrooms",house.getNoOfBedrooms());
        contentValues.put("noOfBathrooms",house.getNoOfBathrooms());
        contentValues.put("houseAddedBy",house.getHouseAddedBy());
        contentValues.put("facilities",house.getFacilities());
        contentValues.put("address",house.getAddress());
        contentValues.put("pricePerMonth",house.getPricePerMonth());
        contentValues.put("houseImage",house.getHouseImage());
        contentValues.put("latitude",house.getLatitude());
        contentValues.put("longitude",house.getLongitude());
        contentValues.put("addedByEmail",house.getAddedByEmail());

        return contentValues;
    }
    public Boolean addHouse(HouseModel house){
        SQLiteDatabase myDB=this.getWritableDatabase();
        ContentValues contentValues=getContentValueObject(house);
        long result=myDB.insert("houses", null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Boolean updateHouseDetails(HouseModel house){
        SQLiteDatabase myDB=this.getWritableDatabase();
        ContentValues contentValues=getContentValueObject(house);
        long result=myDB.update("houses", contentValues, "id=?",new String[]{Integer.toString(house.getId())});
        if(result==-1)
            return false;
        else
            return true;
    }

    public Boolean removeHouse(int id){
        SQLiteDatabase myDB=this.getWritableDatabase();
        long result=myDB.delete("houses", "id=?",new String[]{Integer.toString(id)});
        if(result==-1)
            return false;
        else
            return true;
    }

    public Boolean checkUserExists(String email){
        SQLiteDatabase myDB=this.getWritableDatabase();
        Cursor cursor=myDB.rawQuery("Select * from users where email=?",new String[]{email});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean registerUser(String email, String name, String password, int isHouseOwner){
        SQLiteDatabase myDB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("email",email);
        contentValues.put("name",name);
        contentValues.put("password",password);
        contentValues.put("isHouseOwner",isHouseOwner);
        long result=myDB.insert("users", null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public Bundle login(String email, String password){
        SQLiteDatabase myDB=this.getWritableDatabase();
        Cursor cursor=myDB.rawQuery("Select * from users where email=? and password=?",new String[]{email,password});

        if(cursor.getCount()>0){
            cursor.moveToFirst();

            Bundle bundle=new Bundle();
            bundle.putBoolean("canLogin",true);
            bundle.putString("userName",cursor.getString(1));
            bundle.putInt("isHouseOwner", cursor.getInt(3));
            bundle.putString("email",cursor.getString(0));
            Log.d("LOGIN","username: "+cursor.getString(1));
            return bundle;
        }

        Bundle bundle=new Bundle();
        bundle.putBoolean("canLogin",false);
        bundle.putInt("isHouseOwner", 0);
        return bundle;
    }
}
