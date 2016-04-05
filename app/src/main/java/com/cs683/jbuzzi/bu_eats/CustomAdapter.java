package com.cs683.jbuzzi.bu_eats;

/**
 * Created by jbuzzi on 4/4/16.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{
    Context context;
    Restaurant [] restaurants;
    private static LayoutInflater inflater=null;

    public CustomAdapter(Context context, Restaurant[] restaurants) {
        this.context = context;
        this.restaurants = restaurants;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return restaurants.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView textView;
        ImageView imageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.restaurant_list, null);
        holder.textView=(TextView) rowView.findViewById(R.id.textView1);
        holder.imageView=(ImageView) rowView.findViewById(R.id.imageView1);

        Restaurant restaurant = restaurants[position];
        holder.textView.setText(restaurant.getName());
        holder.imageView.setImageResource(restaurant.getImageId());

        return rowView;
    }
}
