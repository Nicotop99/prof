package com.pubmania.professionista.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pubmania.professionista.R;
import com.pubmania.professionista.StringAdapter.ArrayPost;
import com.pubmania.professionista.StringAdapter.ArrayProdotto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

public class Adapter_Profile_bottom extends ArrayAdapter<ArrayPost> {
    Activity context;
    ArrayList<ArrayPost> arrayPosts;
    public Adapter_Profile_bottom(@NonNull Context context, ArrayList<ArrayPost> arrayPosts) {
        super( context, 0,arrayPosts );
        this.context = (Activity)context;
        this.arrayPosts = arrayPosts;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate( R.layout.grif_profile, null,true);
        int red = Color.parseColor("#00FFFFFF");
        rowView.setBackgroundColor( red);
        rowView.setBackgroundResource( R.drawable.trans_drawable );
        ArrayPost dataModal = getItem(position);
        TextView idPost = (TextView) rowView.findViewById( R.id.idPost );
        idPost.setText( dataModal.getId() );
        ImageView imageView = (ImageView) rowView.findViewById( R.id.roundedImageView );
        ImageView pin = (ImageView) rowView.findViewById( R.id.imageView15 );

        Glide.with(context)
                .load(dataModal.getFoto().get( 0 ))
                .into(imageView);
        if(dataModal.getPinnato().equals( "si" )){
            pin.setVisibility( View.VISIBLE );
        }




        return rowView;
    }
}
