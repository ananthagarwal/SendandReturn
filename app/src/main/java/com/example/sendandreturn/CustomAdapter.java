package com.example.sendandreturn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by ananthagarwal on 5/31/17.
 */

public class CustomAdapter extends ArrayAdapter<PurchaseItem> implements View.OnClickListener{

    private ArrayList<PurchaseItem> dataBase;
    Context mContext;
    private static LayoutInflater inflater = null;
    private PurchaseActivity purchaseActivity;


    public static class ViewHolder {
        TextView name;
        TextView store;
        ImageView info;
        ImageView image;

    }

    public CustomAdapter(ArrayList<PurchaseItem> data, Context context, PurchaseActivity purchaseActivity) {
        super(context, R.layout.row_item, data);
        dataBase = data;
        mContext = context;
        this.purchaseActivity = purchaseActivity;

    }
    @Override
    public void onClick(View view) {

        int position = (int) view.getTag();
        PurchaseItem purchaseItem = (PurchaseItem) getItem(position);

        switch (view.getId()) {
            case R.id.item_infobutton:
                purchaseActivity.editItem(view, purchaseItem);
                break;

        }

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PurchaseItem purchaseItem = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.store = (TextView) convertView.findViewById(R.id.location);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.itemImage);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_infobutton);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        viewHolder.name.setText(purchaseItem.getName());
        viewHolder.image.setImageBitmap(BitmapFactory.decodeFile(purchaseItem.getImage()));
        viewHolder.store.setText(purchaseItem.getStore());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);

        return convertView;

    }
}

