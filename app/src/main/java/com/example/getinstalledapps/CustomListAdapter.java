package com.example.getinstalledapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<listItem> {
    private int mResource;
    private List<listItem> mItems;
    private LayoutInflater mInflater;

    public CustomListAdapter(@NonNull Context context, int resource, List<listItem> items) {
        super(context, resource, items);
        mResource = resource;
        mItems = items;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        } else {
            view = mInflater.inflate(mResource, null);
        }

        listItem item = mItems.get(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setImageBitmap(item.getThumbnail());

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(item.getTitle());

        return view;
    }
}
