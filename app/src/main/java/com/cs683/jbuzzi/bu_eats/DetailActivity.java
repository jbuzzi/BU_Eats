package com.cs683.jbuzzi.bu_eats;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class DetailActivity extends Activity {
    RestaurantDBHelper restaurantDBHelper;
    String name;
    String cuisine;
    String address;
    String phone;
    static String websiteURL;
    int rating;
    int imageId;
    int mealTime;
    Boolean isFavorite;
    ConnectivityManager connManager;
    NetworkInfo netInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        connManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = connManager.getActiveNetworkInfo();

        restaurantDBHelper = new RestaurantDBHelper(getApplicationContext());

        // Get intent data
        Intent i = getIntent();

        name = i.getExtras().getString("restaurantName");
        cuisine = i.getExtras().getString("restaurantCuisine");
        address = i.getExtras().getString("restaurantAddress");
        phone = i.getExtras().getString("restaurantPhone");
        websiteURL = i.getExtras().getString("restaurantWebsite");
        rating = i.getExtras().getInt("restaurantRaiting");
        imageId = i.getExtras().getInt("restaurantImageId");
        mealTime = i.getExtras().getInt("restaurantMealTime");
        int[] restaurantImages = i.getExtras().getIntArray("restaurantImages");

        TextView nameTextView = (TextView) findViewById(R.id.restaurantName);
        nameTextView.setText(name);
        TextView addressTextView = (TextView) findViewById(R.id.restaurantAddress);
        addressTextView.setText(address);
        TextView phoneTextView = (TextView) findViewById(R.id.restaurantPhone);
        phoneTextView.setText(phone);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.resturantRatingBar);
        ratingBar.setRating(rating);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(imageId);
        refreshFavoriteButton();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        showAlert();
                    }
                },
                7000
        );
    }

    public void refreshFavoriteButton () {
        // dBase is access for reading
        SQLiteDatabase dBase = restaurantDBHelper.getReadableDatabase();

        // projection specifies columns from the database
        String[] projection = {"name"};
        String where = RestaurantDBHelper.FIELD1_NAME + " = ?";
        String[] whereValue = {name};

        // Query c performed with projection where name macthes selected restaurant
        Cursor c = dBase.query(RestaurantDBHelper.RESTAURANT_TABLE_NAME,     // table to query
                projection,                         // columns to return
                where,                               // columns for WHERE clause
                whereValue,                         // values for WHERE clause
                null,                               // don't group rows
                null,                               // don't filter by row groups
                null                                // sort order
        );

        Button favButton = (Button) findViewById(R.id.favorite);
        int count = c.getCount();

        if (count > 0) {
            c.moveToFirst();

            //Check restaurant name matches results and set correct title for favorite button.
            String restaurantName = c.getString(c.getColumnIndexOrThrow(RestaurantDBHelper.FIELD1_NAME));
            if (restaurantName.equals(name)) {
                isFavorite = true;
                favButton.setText("Favorited");
            } else {
                isFavorite = false;
                favButton.setText("Set as Favorite");
            }
        } else {
            isFavorite = false;
            favButton.setText("Set as Favorite");
        }
    }

    public void setAsFavorite (View view) {
        Context context = getApplicationContext();
        if (isFavorite) {
            // Data repository db is in write mode
            SQLiteDatabase db = restaurantDBHelper.getWritableDatabase();

            //Query selected restaurant and remove from db
            String where = RestaurantDBHelper.FIELD1_NAME + " = ?";
            String[] whereValue = {name};
            db.delete(RestaurantDBHelper.RESTAURANT_TABLE_NAME, where, whereValue);

            String message = name + " has been removed from your favorites";
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();

            refreshFavoriteButton();
        } else {
            // Data repository db is in write mode
            SQLiteDatabase db = restaurantDBHelper.getWritableDatabase();

            // Map of values created, where column names are the keys and insert
            ContentValues values = new ContentValues();
            values.put(RestaurantDBHelper.FIELD1_NAME, name);
            values.put(RestaurantDBHelper.FIELD2_NAME, cuisine);
            values.put(RestaurantDBHelper.FIELD3_NAME, address);
            values.put(RestaurantDBHelper.FIELD4_NAME, phone);
            values.put(RestaurantDBHelper.FIELD5_NAME, rating);
            values.put(RestaurantDBHelper.FIELD6_NAME, imageId);
            values.put(RestaurantDBHelper.FIELD7_NAME, mealTime);

            db.insert(RestaurantDBHelper.RESTAURANT_TABLE_NAME, null, values);

            String message = name + " has been added to your favorites";
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();

            refreshFavoriteButton();
        }
    }

    public void visitWebsite (View view) {

        boolean isConnected = netInfo != null &&
                netInfo.isConnectedOrConnecting();

        if (isConnected) {
            Intent i = new Intent(getApplicationContext(), RestaurantWeb.class);

            // Pass restaurant info
            i.putExtra("restaurantName", name);
            i.putExtra("restaurantAddress", address);
            i.putExtra("restaurantPhone", phone);
            i.putExtra("restaurantWebsite", websiteURL);
            i.putExtra("restaurantCuisine", cuisine);
            i.putExtra("restaurantRaiting", rating);
            i.putExtra("restaurantImageId", imageId);
            i.putExtra("restaurantMealTime", mealTime);
            startActivity(i);
        } else {
            toast(String.format("No Internet connection available. Connect online and try again later."));
        }
    }

    public void getDirections (View view) {
        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        i.putExtra("restaurantName", name);
        i.putExtra("restaurantAddress", address);
        startActivity(i);
    }

    private void toast(String aToast){
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_LONG).show();
    }

    private void showAlert () {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (!isFinishing()) {
                    new AlertDialog.Builder(DetailActivity.this)
                            .setTitle("Like what you see?")
                            .setMessage("Share this restaurant with friends and plan your next meal.")
                            .setCancelable(true)
                            .setNegativeButton("Cancel", new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Dismiss
                                }
                            })
                            .setPositiveButton("Share", new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    shareText("Check out this place", "Just saw this restaurant on BU Eats we should check it out sometime " + websiteURL);
                                }
                            }).create().show();
                }
            }
        });
    }

    private void shareText(String subject, String body) {
        Intent txtIntent = new Intent(android.content.Intent.ACTION_SEND);
        txtIntent .setType("text/plain");
        txtIntent .putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        txtIntent .putExtra(android.content.Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(txtIntent, "Share"));
    }
}
