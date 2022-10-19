package com.pubmania.professionista;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ArrayIngredienti extends ArrayAdapter<String> {

        Activity context;
        ArrayList<String> arrayList;

public ArrayIngredienti(@NonNull Context context, ArrayList<String> arrayList) {
        super( context, 0, arrayList );
        this.context = (Activity) context;
        this.arrayList = arrayList;
        }


@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate( R.layout.array_list_search,null,false );
        TextView textView = (TextView) view.findViewById( R.id.textView21 );
        textView.setText( getItem(position ) );


        return view;
        }
        }