package com.example.pratik.jpa2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pratik.jpa2.MainActivity;
import com.example.pratik.jpa2.MapsActivity;
import com.example.pratik.jpa2.Model.Contacts;
import com.example.pratik.jpa2.Model.Geo;
import com.example.pratik.jpa2.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by Pratik on 16-Nov-16.
 */

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    List<Contacts> contactsList;
    Geo geo;

    public CustomBaseAdapter(Context context, List<Contacts> items) {
        this.context = context;
        this.contactsList = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tv_id;
        TextView tv_name;
        TextView tv_username;
        TextView tv_email;
        TextView tv_street;
        TextView tv_suite;
        TextView tv_city;
        TextView tv_zipcode;
        TextView tv_lat;
        TextView tv_lng;
        TextView tv_phone;
        TextView tv_website;
        TextView tv_address_name;
        TextView tv_catchPhrase;
        TextView tv_bs;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.user_list_raw, null);
            holder = new ViewHolder();
            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            holder.tv_email = (TextView) convertView.findViewById(R.id.tv_email);
            holder.tv_street = (TextView) convertView.findViewById(R.id.tv_street);
            holder.tv_suite = (TextView) convertView.findViewById(R.id.tv_suite);
            holder.tv_city = (TextView) convertView.findViewById(R.id.tv_city);
            holder.tv_zipcode = (TextView) convertView.findViewById(R.id.tv_zipcode);
            holder.tv_lat = (TextView) convertView.findViewById(R.id.tv_lat);
            holder.tv_lng = (TextView) convertView.findViewById(R.id.tv_lng);
            holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            holder.tv_website = (TextView) convertView.findViewById(R.id.tv_website);
            holder.tv_address_name = (TextView) convertView.findViewById(R.id.tv_address_name);
            holder.tv_catchPhrase = (TextView) convertView.findViewById(R.id.tv_catchPhrase);
            holder.tv_bs = (TextView) convertView.findViewById(R.id.tv_bs);

            geo = new Geo();


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Contacts contacts = (Contacts) getItem(position);


        if(!TextUtils.isEmpty(contacts.getId()))

        holder.tv_id.setText(contacts.getId());
        holder.tv_name.setText(contacts.getName());
        holder.tv_username.setText(contacts.getUsername());
        holder.tv_email.setText(contacts.getEmail());
        holder.tv_phone.setText(contacts.getPhone());
        holder.tv_website.setText(contacts.getWebsite());
        for (int i = 0; i < contactsList.get(position).getAddresses().size(); i++) {
            holder.tv_street.setText(contactsList.get(position).getAddresses().get(i).getStreet());
            holder.tv_suite.setText(contactsList.get(position).getAddresses().get(i).getSuite());
            holder.tv_city.setText(contactsList.get(position).getAddresses().get(i).getCity());

            for (int i1 = 0; i1 < contactsList.get(position).getAddresses().get(i).getGeos().size(); i1++) {
                holder.tv_lat.setTag(contactsList.get(position).getAddresses().get(i).getGeos().size());
                holder.tv_lat.setText(contactsList.get(position).getAddresses().get(i).getGeos().get(i1).getLat());
//                holder.tv_lng.setTag(contactsList.get(position).getAddresses().get(i).getGeos().get(i1).getLng());
                holder.tv_lng.setText(contactsList.get(position).getAddresses().get(i).getGeos().get(i1).getLng());

            }
        }

        for (int i = 0; i <contactsList.get(position).getCompanies().size() ; i++) {
            holder.tv_address_name.setText(contactsList.get(position).getCompanies().get(i).getName());
            holder.tv_catchPhrase.setText(contactsList.get(position).getCompanies().get(i).getCatchPhrase());
            holder.tv_bs.setText(contactsList.get(position).getCompanies().get(i).getBs());
        }
        holder.tv_lat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lat = (String) v.getTag();
                String lng = (String) v.getTag();
                String strUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + "Label which you want" + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                context.startActivity(intent);
            }
        });

//        holder.tv_lat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, MapsActivity.class);
//                String lat=
//                i.putExtra("EXTRA_SESSION_ID", holder.tv_lat);
//                context.startActivity(i);
//            }
//        });

        return convertView;

    }

    @Override
    public int getCount() {
        return contactsList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contactsList.indexOf(getItem(position));
    }
}
