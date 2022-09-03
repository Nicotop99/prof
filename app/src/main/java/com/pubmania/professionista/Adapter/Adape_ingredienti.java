package com.pubmania.professionista.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pubmania.professionista.R;

import java.util.ArrayList;
import java.util.List;

public class Adape_ingredienti extends ArrayAdapter<String> {



    private final Activity context;
    private final ArrayList maintitle;


    public Adape_ingredienti(Context context, List<String> maintitle) {
        super(context, R.layout.layout_ingredienti, maintitle);
        // TODO Auto-generated constructor stub

        this.context= (Activity) context;
        this.maintitle= (ArrayList) maintitle;


    }
    ImageButton delete_image;


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.layout_ingredienti, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.textView41);
        titleText.setText( "- " + String.valueOf(  maintitle.get( position )) );


        return rowView;

    };
}
