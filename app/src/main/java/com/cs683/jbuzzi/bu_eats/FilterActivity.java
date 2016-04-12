package com.cs683.jbuzzi.bu_eats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class FilterActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView3);
        // Initialize the text view with '0'
        textView.setText(seekBar.getProgress() + "/" + seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Display the value in text view
                textView.setText(progress + "/" + seekBar.getMax());
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        RadioButton american = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton asian = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton cafe = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton deli = (RadioButton) findViewById(R.id.radioButton4);
        RadioButton mexican = (RadioButton) findViewById(R.id.radioButton5);
        RadioButton italian = (RadioButton) findViewById(R.id.radioButton6);
        RadioButton greek = (RadioButton) findViewById(R.id.radioButton7);

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton1:
                if (checked) {
                    // American
                    asian.setChecked(false);
                    cafe.setChecked(false);
                    deli.setChecked(false);
                    mexican.setChecked(false);
                    italian.setChecked(false);
                    greek.setChecked(false);
                }
                    break;
            case R.id.radioButton2:
                if (checked) {
                    // Asian
                    american.setChecked(false);
                    cafe.setChecked(false);
                    deli.setChecked(false);
                    mexican.setChecked(false);
                    italian.setChecked(false);
                    greek.setChecked(false);
                }
                    break;
            case R.id.radioButton3:
                if (checked) {
                    // Cafe
                    american.setChecked(false);
                    asian.setChecked(false);
                    deli.setChecked(false);
                    mexican.setChecked(false);
                    italian.setChecked(false);
                    greek.setChecked(false);
                }
                    break;
            case R.id.radioButton4:
                if (checked) {
                    // Deli
                    american.setChecked(false);
                    asian.setChecked(false);
                    cafe.setChecked(false);
                    mexican.setChecked(false);
                    italian.setChecked(false);
                    greek.setChecked(false);
                }
                    break;
            case R.id.radioButton5:
                if (checked) {
                    // Mexican
                    american.setChecked(false);
                    asian.setChecked(false);
                    cafe.setChecked(false);
                    deli.setChecked(false);
                    italian.setChecked(false);
                    greek.setChecked(false);
                }
                    break;
            case R.id.radioButton6:
                if (checked) {
                    // Italian
                    american.setChecked(false);
                    asian.setChecked(false);
                    cafe.setChecked(false);
                    deli.setChecked(false);
                    mexican.setChecked(false);
                    greek.setChecked(false);
                }
                    break;
            case R.id.radioButton7:
                if (checked) {
                    // Greek
                    american.setChecked(false);
                    asian.setChecked(false);
                    cafe.setChecked(false);
                    deli.setChecked(false);
                    italian.setChecked(false);
                    mexican.setChecked(false);
                }
                    break;
        }
    }

    public void searchClicked (View view) {
        //Get selected food by user
        String selectedFood;
        RadioButton american = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton asian = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton cafe = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton deli = (RadioButton) findViewById(R.id.radioButton4);
        RadioButton mexican = (RadioButton) findViewById(R.id.radioButton5);
        RadioButton italian = (RadioButton) findViewById(R.id.radioButton6);
        RadioButton greek = (RadioButton) findViewById(R.id.radioButton7);

        if (american.isChecked()) {
            selectedFood = "American";
        } else if (asian.isChecked()) {
            selectedFood = "Asian";
        } else if (cafe.isChecked()) {
            selectedFood = "Cafe";
        } else if (deli.isChecked()) {
            selectedFood = "Deli";
        } else if (mexican.isChecked()) {
            selectedFood = "Mexican";
        } else if (italian.isChecked()) {
            selectedFood = "Italian";
        } else if (greek.isChecked()) {
            selectedFood = "Greek";
        } else {
            selectedFood = "";
        }

        //Get desire restaurant rating
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        int rating = seekBar.getProgress();

        Intent i = new Intent(getApplicationContext(), SearchResultsActivity.class);
        i.putExtra("cuisine", selectedFood);
        i.putExtra("rating", rating);
        startActivity(i);
    }
}
