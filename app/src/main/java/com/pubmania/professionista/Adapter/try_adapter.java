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

public class try_adapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList maintitle;


    public try_adapter(Context context, List<String> maintitle) {
        super(context, R.layout.prova_linear_layout, maintitle);
        // TODO Auto-generated constructor stub

        this.context= (Activity) context;
        this.maintitle= (ArrayList) maintitle;


    }
    ImageButton delete_image;


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.prova_linear_layout, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.textView33);
        titleText.setText( String.valueOf(  maintitle.get( position )) );

        delete_image = (ImageButton) rowView.findViewById( R.id.imageButton16 );
        delete_image.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maintitle.remove( position );
                notifyDataSetChanged();
            }
        } );
        return rowView;

    };
}
