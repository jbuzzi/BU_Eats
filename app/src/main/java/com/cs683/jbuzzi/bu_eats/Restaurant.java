package com.cs683.jbuzzi.bu_eats;

/**
 * Created by jbuzzi on 4/4/16.
 */
public class Restaurant {
    String name;
    String cuisine;
    String address;
    String phone;
    int rating;
    int imageId;
    int mealTime;

    public Restaurant(String name, String cuisine, String address, String phone, int rating, int imageId, int mealTime) {
        this.name = name;
        this.cuisine = cuisine;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
        this.imageId = imageId;
        this.mealTime = mealTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setMealTime(int mealTime) {
        this.mealTime = mealTime;
    }

    public int getMealTime() {
        return mealTime;
    }
}
