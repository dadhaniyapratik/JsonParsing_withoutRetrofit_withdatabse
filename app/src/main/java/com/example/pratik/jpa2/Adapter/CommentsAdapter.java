package com.example.pratik.jpa2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pratik.jpa2.Model.Contacts;
import com.example.pratik.jpa2.R;

import java.util.List;

/**
 * Created by Pratik on 17-Nov-16.
 */

public class CommentsAdapter extends BaseAdapter {
    Context context;
    List<Contacts> contactses;

    public CommentsAdapter(Context context, List<Contacts> items) {
        this.context = context;
        this.contactses = items;
    }

    /*private view holder class*/
    private class ViewHolder {

        TextView id;
        TextView postId;
        TextView name;
        TextView email;
        TextView body;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.comments_list_raw, null);
            holder = new ViewHolder();
            holder.postId = (TextView) convertView.findViewById(R.id.tv_postId);
            holder.id = (TextView) convertView.findViewById(R.id.tv_id);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.email = (TextView) convertView.findViewById(R.id.tv_email);
            holder.body = (TextView) convertView.findViewById(R.id.tv_body);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contacts contacts = (Contacts) getItem(position);

        holder.postId.setText("PostId : " +contacts.getPostId());
        holder.id.setText("Id : " +contacts.getId());
        holder.name.setText("Name : " +contacts.getName());
        holder.email.setText("Email : " +contacts.getEmail());
        holder.body.setText("Body : " +contacts.getBody());


        return convertView;
    }

    @Override
    public int getCount() {
        return contactses.size();
    }

    @Override
    public Object getItem(int position) {
        return contactses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contactses.indexOf(getItem(position));
    }
}
