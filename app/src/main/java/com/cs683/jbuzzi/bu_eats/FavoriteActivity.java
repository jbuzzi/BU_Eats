package com.cs683.jbuzzi.bu_eats;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

public class FavoriteActivity extends AppCompatActivity {

    Restaurant[] favoriteRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RestaurantDBHelper restaurantDBHelper = new RestaurantDBHelper(getApplicationContext());

        // dBase is access for reading
        SQLiteDatabase dBase = restaurantDBHelper.getReadableDatabase();

        // projection specifies columns from the database
        String[] projection = {RestaurantDBHelper.FIELD1_NAME,
                RestaurantDBHelper.FIELD2_NAME,
                RestaurantDBHelper.FIELD3_NAME,
                RestaurantDBHelper.FIELD4_NAME,
                RestaurantDBHelper.FIELD8_NAME,
                RestaurantDBHelper.FIELD5_NAME,
                RestaurantDBHelper.FIELD6_NAME,
                RestaurantDBHelper.FIELD7_NAME};


        // Query c performed with projection where name macthes selected restaurant
        Cursor c = dBase.query(RestaurantDBHelper.RESTAURANT_TABLE_NAME,     // table to query
                projection,                         // columns to return
                null,                               // columns for WHERE clause
                null,                         // values for WHERE clause
                null,                               // don't group rows
                null,                               // don't filter by row groups
                null                                // sort order
        );

        Button favButton = (Button) findViewById(R.id.favorite);
        int resultCount = c.getCount();
        int count = 0;

       favoriteRestaurants = new Restaurant[resultCount];
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String restaurantName = c.getString(c.getColumnIndexOrThrow(RestaurantDBHelper.FIELD1_NAME));
            String restaurantCuisine = c.getString(c.getColumnIndexOrThrow(RestaurantDBHelper.FIELD2_NAME));
            String restaurantAddress = c.getString(c.getColumnIndexOrThrow(RestaurantDBHelper.FIELD3_NAME));
            String restaurantPhone = c.getString(c.getColumnIndexOrThrow(RestaurantDBHelper.FIELD4_NAME));
            String restaurantWebsite = c.getString(c.getColumnIndexOrThrow(RestaurantDBHelper.FIELD8_NAME));
            int restaurantRating = c.getInt(c.getColumnIndexOrThrow(RestaurantDBHelper.FIELD5_NAME));
            int restaurantImageId = c.getInt(c.getColumnIndexOrThrow(RestaurantDBHelper.FIELD6_NAME));
            int restaurantMealTime = c.getInt(c.getColumnIndexOrThrow(RestaurantDBHelper.FIELD7_NAME));
            favoriteRestaurants[count] = new Restaurant(restaurantName, restaurantCuisine, restaurantAddress, restaurantPhone, restaurantWebsite, restaurantRating, restaurantImageId, restaurantMealTime);
            count++;
            c.moveToNext();
        }

        //Set up restaurant grid view
        GridView gridview = (GridView) findViewById(R.id.favGridView);
        gridview.setAdapter(new CustomAdapter(this, favoriteRestaurants));

        //On item click
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Send intent to Detail Activity
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);

                // Pass restaurant info
                Restaurant selectedRestaurant = favoriteRestaurants[position];
                i.putExtra("restaurantName", selectedRestaurant.getName());
                i.putExtra("restaurantAddress", selectedRestaurant.getAddress());
                i.putExtra("restaurantPhone", selectedRestaurant.getPhone());
                i.putExtra("restaurantCuisine", selectedRestaurant.getCuisine());
                i.putExtra("restaurantRaiting", selectedRestaurant.getRating());
                i.putExtra("restaurantImageId", selectedRestaurant.getImageId());
                i.putExtra("restaurantMealTime", selectedRestaurant.getMealTime());
                startActivity(i);
            }
        });

    }

}
