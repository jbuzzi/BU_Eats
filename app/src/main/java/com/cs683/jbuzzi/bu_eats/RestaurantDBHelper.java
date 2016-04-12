package com.cs683.jbuzzi.bu_eats;

/**
 * Created by jbuzzi on 4/4/16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RestaurantDBHelper extends SQLiteOpenHelper {

    public static final String RESTAURANT_TABLE_NAME = "restaurants";
    public static final String PRIMARY_KEY_NAME = "id";
    public static final String FIELD1_NAME = "name";
    public static final String FIELD2_NAME = "cuisine";
    public static final String FIELD3_NAME = "address";
    public static final String FIELD4_NAME = "phone";
    public static final String FIELD8_NAME = "websiteURL";
    public static final String FIELD5_NAME = "rating";
    public static final String FIELD6_NAME = "imageId";
    public static final String FIELD7_NAME = "mealTime";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BUEatsRestaurants.db";
    public static final String TABLE_SPECIFICATIONS =
            // form: "CREATE TABLE restaurants(id INTEGER PRIMARY KEY, name TEXT, cuisine TEXT, address TEXT, phone TEXT, rating INTEGER, imageName TEXT, mealTime INTEGER)"
            "CREATE TABLE " + RESTAURANT_TABLE_NAME + "(" +
                    PRIMARY_KEY_NAME + " INTEGER PRIMARY KEY, " + FIELD1_NAME + " TEXT, " + FIELD2_NAME + " TEXT, " + FIELD3_NAME + " TEXT, " + FIELD4_NAME + " TEXT, " + FIELD8_NAME + " TEXT, " + FIELD5_NAME + " INTEGER, " + FIELD6_NAME + " INTEGER, " + FIELD7_NAME + " INTEGER)";

    public RestaurantDBHelper(Context context) {
        // A database exists, named DATABASE_NAME, with TABLE_SPECIFICATIONS
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_SPECIFICATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}