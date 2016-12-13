package com.example.pratik.jpa2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pratik.jpa2.Model.Worldpopulation;
import com.example.pratik.jpa2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Pratik on 08-Dec-16.
 */

public class CountryAdapter extends BaseAdapter {
    Context context;
    List<Worldpopulation> worldpopulationList;

    public CountryAdapter(Context context, List<Worldpopulation> items) {
        this.context = context;
        this.worldpopulationList = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView img_flag;
        TextView tv_country;
        TextView tv_rank;
        TextView tv_population;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.country_list_raw, null);
            holder = new ViewHolder();
            holder.tv_rank = (TextView) convertView.findViewById(R.id.tv_rank);
            holder.tv_country = (TextView) convertView.findViewById(R.id.tv_country);
            holder.tv_population = (TextView) convertView.findViewById(R.id.tv_population);


            holder.img_flag = (ImageView) convertView.findViewById(R.id.img_flag);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Worldpopulation worldpopulation = (Worldpopulation) getItem(position);


        holder.tv_rank.setText(worldpopulation.getRank());
        holder.tv_country.setText(worldpopulation.getCountry());
        holder.tv_population.setText(worldpopulation.getPopulation());
        Picasso.with(context)
                .load(worldpopulation.getFlag())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img_flag);


        return convertView;
    }

    @Override
    public int getCount() {
        return worldpopulationList.size();
    }

    @Override
    public Object getItem(int position) {
        return worldpopulationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return worldpopulationList.indexOf(getItem(position));
    }
}