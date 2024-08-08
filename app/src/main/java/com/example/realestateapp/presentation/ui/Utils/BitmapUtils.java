package com.example.realestateapp.presentation.ui.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;

public class BitmapUtils {

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return imagemTratada(stream.toByteArray());
    }

    private static byte[] imagemTratada(byte[] imagem_img){

        while (imagem_img.length > 500000){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagem_img, 0, imagem_img.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagem_img = stream.toByteArray();
        }
        return imagem_img;

    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static void setLogin(Context context, String mail, String password, boolean isHouseOwner){
        if(!mail.equals("done")) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("LoginShredPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("mail", mail);
            editor.putString("password", password);
            editor.putBoolean("isHouseOwner",isHouseOwner);
            editor.commit();
        }
    }

    public static int isLogin(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("LoginShredPref",Context.MODE_PRIVATE);
        String mail=sharedPreferences.getString("mail","");
        String pass=sharedPreferences.getString("password","");
        boolean isHouseOwner= sharedPreferences.getBoolean("isHouseOwner",false);
        if(mail.toString().trim().isEmpty() || pass.toString().trim().isEmpty()){
            return -1;
        }
        else{
            return  isHouseOwner? 1:0;
        }
    }
}