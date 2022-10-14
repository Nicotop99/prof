package com.pubmania.professionista.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pubmania.professionista.R;

import java.util.ArrayList;
import java.util.List;

public class adapter_image extends RecyclerView.Adapter<adapter_image.ViewHolder> {
    private List<String> listdata;


    public adapter_image(Context context, List<String> foto) {
        this.listdata = foto;
        Log.d("lfmdfsdmlf","jfdddddddddddddddddnljnslnfldsflk");

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_view_image, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        Log.d("lfmdfsdmlf","createeee");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d("lfmdfsdmlf","binddd");


        Glide.with(holder.itemView).load(String.valueOf(listdata.get(position))).into(holder.imageView);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return listdata.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {

            super( itemView );
            Log.d("lfmdfsdmlf","viewww");

            this.imageView = (ImageView) itemView.findViewById(R.id.imageView21);
        }
    }
}
