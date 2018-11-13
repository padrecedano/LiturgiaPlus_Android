package org.deiverbum.app.data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.deiverbum.app.R;
import org.deiverbum.app.activities.RosarioActivity;
import org.deiverbum.app.model.Oraciones;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracionesAdapter extends RecyclerView.Adapter<OracionesAdapter.OracionViewHolder> {
    List<Oraciones> oracionesList;
    Context context;

    public OracionesAdapter(List<Oraciones> oracionesList) {
        this.oracionesList = oracionesList;
    }

    @Override
    public OracionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.rv_rosario, parent, false);
        OracionViewHolder viewHolder = new OracionViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OracionViewHolder holder, final int position) {
        Oraciones oraciones = oracionesList.get(position);
        holder.oracionName.setText(oraciones.getName());
        holder.description.setText(oraciones.getDescription());
        holder.poster.setImageResource(oraciones.getImageId());
//        holder.ratingBar.setRating(movies.getRating());
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
        });
    }

    @Override
    public int getItemCount() {
        return oracionesList.size();
    }

    public class OracionViewHolder extends RecyclerView.ViewHolder {
        public TextView oracionName, description;
        public ImageView poster, popmemu;
        public View view;


        public OracionViewHolder(final View itemView) {
            super(itemView);
            oracionName = itemView.findViewById(R.id.tv_oracion);
            description = itemView.findViewById(R.id.tv_description);
            //ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            poster = itemView.findViewById(R.id.iv_poster);
            popmemu = itemView.findViewById(R.id.iv_menu);
            view = itemView;
            final Map<String, Integer> myMap = new HashMap<>();
            myMap.put("Misterios Gloriosos", 1);
            myMap.put("Misterios Gozosos", 2);
            myMap.put("Misterios Dolorosos", 3);
            myMap.put("Misterios Luminosos", 4);
            myMap.put("Letanías", 5);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String misterio = oracionName.getText().toString();
                    int dayCode = myMap.get(misterio);
                    Intent i = new Intent(context, RosarioActivity.class);
                    i.putExtra("EXTRA_PAGE", dayCode);
                    context.startActivity(i);
                }
            });

        }


    }
}
