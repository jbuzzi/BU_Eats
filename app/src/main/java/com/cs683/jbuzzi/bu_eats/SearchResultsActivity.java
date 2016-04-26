package com.cs683.jbuzzi.bu_eats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.view.Gravity;

import java.util.Vector;

public class SearchResultsActivity extends AppCompatActivity {

    Restaurant[] restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // Get intent data
        Intent i = getIntent();

        String cuisine = i.getExtras().getString("cuisine");
        int rating = i.getExtras().getInt("rating");

        //Set test restaurant data
        restaurants = new Restaurant[13];
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


        Vector results = new Vector();
        for(int index = 0; index < restaurants.length; index++){
            if(restaurants[index].rating > rating)
                results.addElement(restaurants[index]);
        }

        Restaurant[] resultsInProgress = new Restaurant[results.size()];
        results.copyInto(resultsInProgress);

        Vector results2 = new Vector();
        for(int index = 0; index < resultsInProgress.length; index++){
            if(resultsInProgress[index].cuisine.equals(cuisine))
                results2.addElement(resultsInProgress[index]);
        }

        final Restaurant[] finalResults = new Restaurant[results2.size()];
        results2.copyInto(finalResults);

        if (finalResults.length == 0) {
            RelativeLayout relativeLayout = new RelativeLayout(this);

            RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);

            TextView text = new TextView(this);
            text.setText("NO RESULTS FOUND\nPlease make sure to select some filters and try again.");
            text.setPadding(30, 730, 25, 30);
            text.setTextSize(17);
            text.setGravity(Gravity.CENTER);
            relativeLayout.addView(text);

            CustomDrawableView customDrawableView = new CustomDrawableView(this);
            relativeLayout.addView(customDrawableView);

            setContentView(relativeLayout, lParams);
        } else {
            //Set up restaurant grid view
            GridView gridview = (GridView) findViewById(R.id.resultsGridView);
            gridview.setAdapter(new CustomAdapter(this, finalResults));

            //On item click
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    // Send intent to Detail Activity
                    Intent i = new Intent(getApplicationContext(), DetailActivity.class);

                    // Pass restaurant info
                    Restaurant selectedRestaurant = finalResults[position];
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
}
