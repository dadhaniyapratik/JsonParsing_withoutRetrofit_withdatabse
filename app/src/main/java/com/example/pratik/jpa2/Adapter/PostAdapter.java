package com.example.pratik.jpa2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pratik.jpa2.Model.Company;
import com.example.pratik.jpa2.Model.Contacts;
import com.example.pratik.jpa2.R;

import java.util.List;

/**
 * Created by Pratik on 17-Nov-16.
 */

public class PostAdapter extends BaseAdapter {

    Context context;
    List<Contacts> contactses;

    public PostAdapter(Context context, List<Contacts> items) {
        this.context = context;
        this.contactses = items;
    }

    /*private view holder class*/
    private class ViewHolder {

        TextView tv_userid;
        TextView tv_id;
        TextView tv_title1;
        TextView tv_body;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.post_list_raw, null);
            holder = new ViewHolder();
            holder.tv_userid = (TextView) convertView.findViewById(R.id.tv_userid);
            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            holder.tv_title1 = (TextView) convertView.findViewById(R.id.tv_title1);
            holder.tv_body = (TextView) convertView.findViewById(R.id.tv_body);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contacts contacts = (Contacts) getItem(position);

        holder.tv_userid.setText("UserId : " +contacts.getUserId());
        holder.tv_id.setText("Id : " +contacts.getId());
        holder.tv_title1.setText("Title : " +contacts.getTitle1());
        holder.tv_body.setText("Body : " +contacts.getBody());


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
