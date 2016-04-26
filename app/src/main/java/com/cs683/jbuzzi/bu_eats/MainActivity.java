package com.cs683.jbuzzi.bu_eats;

import android.net.Uri;
import android.os.Bundle;;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.GridView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;
import android.widget.Toast;

import java.util.Date;
import java.util.Vector;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Restaurant[] filteredRestaurants;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set test restaurant data
        Restaurant[] restaurants = new Restaurant[13];
        restaurants[0] = new Restaurant("The Capital Grille", "American", "900 Boylston St, Boston, MA 02115","(617) 262-8900", "https://www.thecapitalgrille.com", 4, R.drawable.sample0, 3);
        restaurants[1] = new Restaurant("Tasty Burger", "American", "1301 Boylston St, Boston, MA 02215","(617) 425-4444", "http://www.tastyburger.com", 5, R.drawable.sample1, 2);
        restaurants[2] = new Restaurant("Rosa Mexicano", "Mexican", "155 Seaport Blvd, Boston, MA 02210","(617) 476-6122", "https://www.rosamexicano.com", 3, R.drawable.sample2, 3);
        restaurants[3] = new Restaurant("Seven Subs", "Deli", "1364 Beacon St, Brookline, MA 02446","(617) 232-7070", "http://eatsevens.com", 2, R.drawable.sample3, 2);
        restaurants[4] = new Restaurant("Gyro City", "Greek", "88 Peterborough St, Boston, MA 02215","(617) 266-4976", "http://www.gyrocity.com", 5, R.drawable.sample4, 4);
        restaurants[5] = new Restaurant("Basho", "Asian", "1338 Boylston St, Boston, MA 02215","(617) 262-1338", "https://bashosushi.com", 4, R.drawable.sample5, 3);
        restaurants[6] = new Restaurant("Sal's Pizza", "Italian", "51 Brookline Ave, Boston, MA 02115","(617) 536-4444", "http://www.sals-pizza.com", 3, R.drawable.sample6, 4);
        restaurants[7] = new Restaurant("Noodle Street", "Asian", "627 Commonwealth Avenue, Boston, MA 02215","(617) 536-3100", "http://www.noodlestreet.com", 4, R.drawable.sample7, 2);
        restaurants[8] = new Restaurant("Beijin Cafe", "Asian", "728 Commonwealth Avenue, Boston, MA 02215","(617) 859-3925", "http://www.beijingcafeusa.com", 3, R.drawable.sample8, 3);
        restaurants[9] = new Restaurant("Tavern In The Square", "American", "161 Brighton Ave, Boston, MA 02134","(617) 782-8100", "https://taverninthesquare.com", 4, R.drawable.sample9, 4);
        restaurants[10] = new Restaurant("Dave's Dinner", "American", "1046 Beacon St, Brookline, MA 02446","(617) 566-8733", "http://www.davesdiner.com", 3, R.drawable.sample10, 1);
        restaurants[11] = new Restaurant("Starbucks", "Cafe", "700 Commonwealth Avenue, Boston, MA 02215","(617) 358-5450", "https://www.starbucks.com", 4, R.drawable.sample11, 1);
        restaurants[12] = new Restaurant("Panera", "Cafe", "888 Commonwealth Avenue, Brookline, MA 02115","(617) 738-1501", "https://www.panerabread.com", 4, R.drawable.sample12, 1);

        //Displays app greeting based on time of day
        TextView header = (TextView) findViewById(R.id.header);
        try {
            if (isTimeBetween("05:00:00", "10:59:59")){
                header.setText("It's breakfast time!");
                filterRestaurants(restaurants, 1);
                Log.v(TAG, "showing breakfast gretting");
            } else if (isTimeBetween("11:00:00", "16:59:59")){
                header.setText("It's lunch time!");
                filterRestaurants(restaurants, 2);
                Log.v(TAG, "showing lunch greeting");
            } else if (isTimeBetween("17:00:00", "20:59:59")){
                header.setText("It's dinner time!");
                filterRestaurants(restaurants, 3);
                Log.v(TAG, "showing dinner greeting");
            } else if (isTimeBetween("21:00:00", "04:59:59")){
                header.setText("Looking for a late night snack?");
                filterRestaurants(restaurants, 4);
                Log.v(TAG, "showing late night greeting");
            } else {
                header.setText("Hungry?");
                filteredRestaurants = restaurants;
                filterRestaurants(restaurants, 4);
                Log.v(TAG, "showing default greeting");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Set up restaurant grid view
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new CustomAdapter(this, filteredRestaurants));

        //On item click
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                // Send intent to Detail Activity
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);

                // Pass restaurant info
                Restaurant selectedRestaurant = filteredRestaurants[position];
                i.putExtra("restaurantName", selectedRestaurant.getName());
                i.putExtra("restaurantAddress", selectedRestaurant.getAddress());
                i.putExtra("restaurantPhone", selectedRestaurant.getPhone());
                i.putExtra("restaurantWebsite", selectedRestaurant.getWebsiteURL());
                i.putExtra("restaurantCuisine", selectedRestaurant.getCuisine());
                i.putExtra("restaurantRaiting", selectedRestaurant.getRating());
                i.putExtra("restaurantImageId", selectedRestaurant.getImageId());
                i.putExtra("restaurantMealTime", selectedRestaurant.getMealTime());
                startActivity(i);
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Show favorite restaurant screen
        if (id == R.id.action_favorites) {
            showFavorites();
            return true;
        } if (id == R.id.action_feedback) {
            sendFeedback();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.cs683.jbuzzi.bu_eats/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.cs683.jbuzzi.bu_eats/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    void sendFeedback() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "App Feedback");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.setData(Uri.parse("mailto:feedback@BUEats.com"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            toast("There are no email clients installed.");
        }
    }

    void showFavorites() {
        Intent i = new Intent(this, FavoriteActivity.class);
        startActivity(i);
    }

    public void filterOptions (View view) {
        Intent i = new Intent(this, FilterActivity.class);
        startActivity(i);
    }

    //Returns true if current time is between initialTime and finalTime. Use to determined the time
    //of day.
    boolean isTimeBetween(String initialTime, String finalTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        if (initialTime.matches(reg) && finalTime.matches(reg)) {
            boolean valid = false;
            //Start Time
            Date inTime = new SimpleDateFormat("HH:mm:ss").parse(initialTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(inTime);

            //Current Time
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String currentTime = dateFormat.format(date);
            Date checkTime = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(checkTime);

            //End Time
            java.util.Date finTime = new SimpleDateFormat("HH:mm:ss").parse(finalTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(finTime);

            if (finalTime.compareTo(initialTime) < 0) {
                calendar2.add(Calendar.DATE, 1);
                calendar3.add(Calendar.DATE, 1);
            }

            java.util.Date actualTime = calendar3.getTime();
            if ((actualTime.after(calendar1.getTime()) || actualTime.compareTo(calendar1.getTime()) == 0)
                    && actualTime.before(calendar2.getTime())) {
                valid = true;
            }
            return valid;
        } else {
            throw new IllegalArgumentException("Not a valid time, expecting HH:MM:SS format");
        }
    }

    //Filters restaurants based on meal time pass in. (1)breakfast (2)lunch (3)dinner (4)late night snack
    void filterRestaurants(Restaurant[] restaurants, int mealTime) {
        Vector restaurantList = new Vector();
        for(int i = 0; i <restaurants.length; i++){
            if(restaurants[i].getMealTime() == mealTime) {
                restaurantList.addElement(restaurants[i]);
            }
        }

        filteredRestaurants = new Restaurant[restaurantList.size()];
        restaurantList.copyInto(filteredRestaurants);
    }

    private void toast(String aToast){
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_LONG).show();
    }
}
