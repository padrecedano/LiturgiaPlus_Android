package org.deiverbum.app.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.deiverbum.app.R;
import org.deiverbum.app.activities.BibliaLibrosActivity;
import org.deiverbum.app.activities.OracionesGenericActivity;
import org.deiverbum.app.model.BibliaLibros;

import java.util.List;

public class BibliaAdapter extends RecyclerView.Adapter<BibliaAdapter.BibliaViewHolder> {
    List<BibliaLibros> booksList;
    Context context;

    public BibliaAdapter(List<BibliaLibros> booksList) {
        this.booksList = booksList;
    }

    @Override
    public BibliaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.rv_rosario, parent, false);
        BibliaViewHolder viewHolder = new BibliaViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BibliaViewHolder holder, final int position) {
        BibliaLibros oraciones = booksList.get(position);
        holder.bookName.setText(oraciones.getName());
        holder.bookDescription.setText(oraciones.getDescription());


//        holder.ratingBar.setRating(movies.getRating());
        /*
        holder.popmemu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Oraciones movies = oracionesList.get(position);
                PopupMenu popupMenu = new PopupMenu(context, holder.popmemu);
                popupMenu.inflate(R.menu.oraciones_items);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.watch_later:
                                break;
                            case R.id.favourite:
                                break;
                            case R.id.download:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class BibliaViewHolder extends RecyclerView.ViewHolder {
        public TextView bookName, bookDescription;
        public View view;

        public BibliaViewHolder(final View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.tv_title);
            bookDescription = itemView.findViewById(R.id.tv_description);
            view = itemView;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String misterio = (String) bookName.getText();
                    int dayCode = 500;// myMap.get(misterio);
                    if (dayCode < -1) {
                        Intent i = new Intent(context, OracionesGenericActivity.class);
                        i.putExtra("EXTRA_PAGE", dayCode);
                        i.putExtra("TITLE", misterio);
                        context.startActivity(i);
                    } else {
                        Intent i = new Intent(context, BibliaLibrosActivity.class);
                        i.putExtra("EXTRA_PAGE", dayCode);
                        i.putExtra("TITLE", misterio);
                        context.startActivity(i);


                    }
                }
            });

        }


    }
}
