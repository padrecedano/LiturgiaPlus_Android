package org.deiverbum.app.data;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.deiverbum.app.R;
import org.deiverbum.app.model.Album;

import java.util.List;


public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;
    private AlbumsAdapter.ItemListener mListener;

    public AlbumsAdapter(Context mContext, List<Album> albumList, AlbumsAdapter.ItemListener itemListener) {
        this.mContext = mContext;
        this.albumList = albumList;
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
        Album album = albumList.get(position);
        holder.title.setText(album.getName());
        //holder.count.setText(album.getItemId() + " songs");
        holder.thumbnail.setImageResource(album.getThumbnail());
        holder.setData(albumList.get(position));

        //imageView.setImageResource(item.drawable);

        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);
/*
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
*/


    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public interface ItemListener {
        void onItemClick(Album item);
    }

    public interface AlbumsAdapterListener {
        void onAddToFavoriteSelected(int position);

        void onPlayNextSelected(int position);

        void onCardSelected(int position, ImageView thumbnail);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public CardView cardView;
        Album item;
        RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            title = view.findViewById(R.id.title);
            //count = (TextView) view.findViewById(R.id.count);
            thumbnail = view.findViewById(R.id.thumbnail);
            //overflow = (ImageView) view.findViewById(R.id.overflow);
            relativeLayout = view.findViewById(R.id.mainCardRelativeLayout);

            //cardView = view.findViewById(R.id.card_view);

        }

        public void setData(Album item) {
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

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

}