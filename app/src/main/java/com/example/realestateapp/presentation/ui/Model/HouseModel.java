package com.example.realestateapp.presentation.ui.Model;

import java.io.Serializable;

public class HouseModel implements Serializable {
    //is house(1) or apartment(0)
    int id;
    int isHouse;
    String houseName;
    int sqft;
    int noOfBedrooms;
    int noOfBathrooms;
    String houseAddedBy;
    String facilities;
    //longitude, lattitude maybe
    String address;
    double latitude;
    double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    int pricePerMonth;
    byte[] houseImage;

    public HouseModel(int id, int isHouse, String houseName, int sqft, int noOfBedrooms, int noOfBathrooms, String houseAddedBy, String facilities, String address, int pricePerMonth, byte[] houseImage, double latitude, double longitude) {
        this.id=id;
        this.isHouse = isHouse;
        this.houseName = houseName;
        this.sqft = sqft;
        this.noOfBedrooms = noOfBedrooms;
        this.noOfBathrooms = noOfBathrooms;
        this.houseAddedBy = houseAddedBy;
        this.facilities = facilities;
        this.address = address;
        this.pricePerMonth = pricePerMonth;
        this.houseImage=houseImage;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public int isHouse() {
        return isHouse;
    }

    public void setHouse(int house) {
        isHouse = house;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public int getSqft() {
        return sqft;
    }

    public void setSqft(int sqft) {
        this.sqft = sqft;
    }

    public int getNoOfBedrooms() {
        return noOfBedrooms;
    }

    public void setNoOfBedrooms(int noOfBedrooms) {
        this.noOfBedrooms = noOfBedrooms;
    }

    public int getNoOfBathrooms() {
        return noOfBathrooms;
    }

    public void setNoOfBathrooms(int noOfBathrooms) {
        this.noOfBathrooms = noOfBathrooms;
    }

    public String getHouseAddedBy() {
        return houseAddedBy;
    }

    public void setHouseAddedBy(String houseAddedBy) {
        this.houseAddedBy = houseAddedBy;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(int pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public byte[] getHouseImage() {
        return houseImage;
    }

    public void setHouseImage(byte[] image) {
        this.houseImage = image;
    }

    public int getId() {
        return id;
    }

    public int getIsHouse() {
        return isHouse;
    }

    public void setIsHouse(int isHouse) {
        this.isHouse = isHouse;
    }
}
