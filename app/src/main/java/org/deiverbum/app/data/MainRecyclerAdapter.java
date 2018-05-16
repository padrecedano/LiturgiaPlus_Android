package org.deiverbum.app.data;

// Created by cedano on 19/10/17.


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.deiverbum.app.R;

import java.util.ArrayList;


public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private ItemListener mListener;
    private ArrayList<MainDataModel> mValues;
    private Context mContext;

    public MainRecyclerAdapter(Context context, ArrayList<MainDataModel> values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener = itemListener;
    }

    @Override
    public MainRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(MainDataModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageView imageView;
        RelativeLayout relativeLayout;
        MainDataModel item;

        ViewHolder(View v) {

            super(v);

            v.setOnClickListener(this);
            textView = v.findViewById(R.id.textView);
            imageView = v.findViewById(R.id.imageView);
            relativeLayout = v.findViewById(R.id.relativeLayout);

        }

        public void setData(MainDataModel item) {
            this.item = item;

            textView.setText(item.text);
            //textView.setGravity(Gravity.CENTER);
            //textView.setTextColor(Color.GRAY);

            imageView.setImageResource(item.drawable);
            relativeLayout.setBackgroundColor(item.color);
/*
            int newHeight = 250; // New height in pixels
            int newWidth = 250; // New width in pixels
            imageView.getLayoutParams().height = newHeight;

            // Apply the new width for ImageView programmatically
            imageView.getLayoutParams().width = newWidth;
*/
        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }
}