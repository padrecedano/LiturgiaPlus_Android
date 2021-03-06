package org.deiverbum.app.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.deiverbum.app.R;
import org.deiverbum.app.model.MainItem;

import java.util.List;


public class MainItemsAdapter extends RecyclerView.Adapter<MainItemsAdapter.MyViewHolder> {

    private List<MainItem> mainList;
    private MainItemsAdapter.ItemListener mListener;

    public MainItemsAdapter(List<MainItem> mainList, MainItemsAdapter.ItemListener itemListener) {
        this.mainList = mainList;
        mListener = itemListener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MainItem album = mainList.get(position);
        holder.title.setText(album.getName());
        holder.thumbnail.setImageResource(album.getThumbnail());
        holder.setData(mainList.get(position));
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public interface ItemListener {
        void onItemClick(MainItem item);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView thumbnail;
        MainItem item;
        RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = view.findViewById(R.id.title);
            thumbnail = view.findViewById(R.id.thumbnail);
            relativeLayout = view.findViewById(R.id.mainCardRelativeLayout);

        }

        public void setData(MainItem item) {
            this.item = item;
            relativeLayout.setBackgroundColor(item.getColor());
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }
}