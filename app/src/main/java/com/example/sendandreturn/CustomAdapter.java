package com.example.sendandreturn;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ananthagarwal on 5/31/17.
 */

public class CustomAdapter extends BaseAdapter {

    private ArrayList<PurchaseItem> dataBase;
    Context mContext;

    public static class ViewHolder {
        TextView name;
        TextView store;
        ImageView info;
        ImageView image;
    }

    public CustomAdapter(ArrayList<PurchaseItem> data, Context context) {
        super();
        dataBase = data;
        mContext = context;


    }
}
