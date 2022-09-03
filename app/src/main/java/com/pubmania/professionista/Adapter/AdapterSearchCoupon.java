package com.pubmania.professionista.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pubmania.professionista.R;
import com.pubmania.professionista.StringAdapter.ArrayProdotto;
import com.pubmania.professionista.StringAdapter.StringRegistrazione;
import com.pubmania.professionista.couponActivity;

import java.util.ArrayList;
import java.util.List;

import static com.pubmania.professionista.couponActivity.spinnerProdotto;
import static com.pubmania.professionista.couponActivity.t_search;

public class AdapterSearchCoupon extends ArrayAdapter<ArrayProdotto> {

    Context context;
    // constructor for our list view adapter.
    public AdapterSearchCoupon(@NonNull Context context, ArrayList<ArrayProdotto> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate( R.layout.layout_list_search, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        ArrayProdotto dataModal = getItem(position);

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

                couponActivity.group.setVisibility( View.GONE );
                InputMethodManager imm = (InputMethodManager)context.getSystemService( Context.
                        INPUT_METHOD_SERVICE );
                imm.hideSoftInputFromWindow( t_search.getWindowToken(), 0 );
                t_search.setText( dataModal.getId() );
                List<String> spinnerArray =  new ArrayList<String>();
                spinnerArray.add(dataModal.getId());
                spinnerArray.add( context.getString( R.string.suqualeprodottovuoifarelosconto ) );
                spinnerArray.add( context.getString( R.string.tutti ) );
                spinnerArray.add( context.getString( R.string.selezionasingolo ) );

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        context, android.R.layout.simple_spinner_item, spinnerArray);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProdotto.setAdapter(adapter);
            }
        });
        return listitemView;
    }
}
