package com.pubmania.professionista.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pubmania.professionista.R;
import com.pubmania.professionista.StringAdapter.StringRegistrazione;

import java.util.ArrayList;

import static com.pubmania.professionista.R.drawable.bianco;

public class adapter_list_search_prodotti extends ArrayAdapter<StringRegistrazione> {

    // constructor for our list view adapter.
    public adapter_list_search_prodotti(@NonNull Context context, ArrayList<StringRegistrazione> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_search, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        StringRegistrazione dataModal = getItem(position);

        // initializing our UI components of list view item.
        TextView nameTV = listitemView.findViewById(R.id.textView27);
        nameTV.setText( dataModal.getId() );
        // below line is use to add item click listener
        // for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                Log.d("kfkfkfkfk",dataModal.getId());
            }
        });
        return listitemView;
    }
}
