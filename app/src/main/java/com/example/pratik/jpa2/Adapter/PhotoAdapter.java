package com.example.pratik.jpa2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pratik.jpa2.Model.Contacts;
import com.example.pratik.jpa2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Pratik on 17-Nov-16.
 */

public class PhotoAdapter extends BaseAdapter {

    Context context;
    List<Contacts> contactsList;

    public PhotoAdapter(Context context, List<Contacts> items) {
        this.context = context;
        this.contactsList = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView url  ;
        ImageView thumbnailUrl;
        TextView albumId;
        TextView id;
        TextView title1;
        TextView url1;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.photo_list_raw, null);
            holder = new ViewHolder();
            holder.albumId = (TextView) convertView.findViewById(R.id.tv_albumId);
            holder.id = (TextView) convertView.findViewById(R.id.tv_id);
            holder.title1 = (TextView) convertView.findViewById(R.id.tv_title1);
            holder.url1 = (TextView) convertView.findViewById(R.id.tv_url1);
            holder.url = (ImageView) convertView.findViewById(R.id.img_url);
            holder.thumbnailUrl = (ImageView) convertView.findViewById(R.id.img_thumbnailUrl);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contacts contacts = (Contacts) getItem(position);


        holder.albumId.setText("AlbumId : " +contacts.getAlbumId());
        holder.id.setText("Id : " +contacts.getId());
        holder.title1.setText("Title : " +contacts.getTitle1());
        holder.url1.setText("Url : " +contacts.getUrl());
        Picasso.with(context)
                .load("https://cms-assets.tutsplus.com/uploads/users/21/posts/19431/featured_image/CodeFeature.jpg")
                .resize(100, 100)
                .into(holder.url);

        Picasso.with(context)
                .load("https://cms-assets.tutsplus.com/uploads/users/21/posts/19431/featured_image/CodeFeature.jpg")
                .resize(100, 100)
                .into(holder.thumbnailUrl);


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
