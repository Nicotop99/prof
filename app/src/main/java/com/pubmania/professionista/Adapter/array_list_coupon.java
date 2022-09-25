package com.pubmania.professionista.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pubmania.professionista.R;
import com.pubmania.professionista.StringAdapter.StringCoupon;
import com.pubmania.professionista.couponActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static com.pubmania.professionista.couponActivity.arrayListCoupon;

public class array_list_coupon extends ArrayAdapter<StringCoupon> {
    Activity context;
    ArrayList<StringCoupon>arrayList;
    String email;

    public array_list_coupon(@NonNull Context context, @NonNull ArrayList<StringCoupon> objects) {
        super( context,0, objects );
        this.context = (Activity)context;
        this.arrayList =(ArrayList<StringCoupon>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View view = inflater.inflate( R.layout.list_view_coupon,null,true );
        StringCoupon stringCoupon = getItem( position );
        email = "oliverio.enicola@gmail.com";
        TextView percentuale = (TextView) view.findViewById( R.id.textView49 );
        TextView titolo = (TextView) view.findViewById( R.id.textView54 );
        TextView quantePersone = (TextView) view.findViewById( R.id.textView55 );
        ImageButton elimina = (ImageButton) view.findViewById( R.id.imageButton30 );
        TextView textView = (TextView) context.findViewById( R.id.textView47 );
        TextView textView1 = (TextView) context.findViewById( R.id.textView48 );
        Log.d( "fjdslf", String.valueOf( arrayList.size() ) );
        if(arrayList.size() == 0){
            textView.setVisibility( View.VISIBLE );
            textView1.setVisibility( View.VISIBLE );
        }else{
            textView.setVisibility( View.GONE );
            textView1.setVisibility( View.GONE );
        }

        quantePersone.setText( stringCoupon.getQuanteVolte() +" " + context.getString( R.string.volteutilizzato ) );

        Log.d( "kfmdskfmksd",arrayList.get( 0 ).getPrezzo() );
        Log.d( "kmfdlksmfs", String.valueOf( position ) );
        elimina.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                DocumentReference documentReference1 = firebaseFirestore.collection( email+"Post" ).document(stringCoupon.getId());
                                documentReference1.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        arrayList.remove( position );
                                        if(arrayList.size() == 0){
                                            textView.setVisibility( View.VISIBLE );
                                            textView1.setVisibility( View.VISIBLE );
                                        }
                                        notifyDataSetChanged();
                                    }
                                } );







                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked

                                dialog.dismiss();

                                break;
                        }
                    }
                };
                notifyDataSetChanged();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getString( R.string.seisicurodivolereliminarequesrcopuon )).setPositiveButton(context.getString( R.string.si ), dialogClickListener)
                        .setNegativeButton(context.getString( R.string.no ), dialogClickListener).show();
            }
        } );

        if(stringCoupon.getCategoria().equals( "Coupon" )) {
            if (stringCoupon.tipo.equals( "Percentuale" )) {
                percentuale.setText( stringCoupon.getPrezzo() + " %" );

                if (stringCoupon.getQualeProdotto().equals( context.getString( R.string.tutti ) )) {
                    titolo.setText( stringCoupon.getPrezzo() + "% " + context.getString( R.string.sututtiiprodotti ) );
                } else {
                    titolo.setText( stringCoupon.getPrezzo() + "% " + stringCoupon.getQualeProdotto() );
                }
            } else if (stringCoupon.tipo.equals( "Prezzo" )) {
                percentuale.setText( stringCoupon.getPrezzo() + " ,00€" );

                if (stringCoupon.getQualeProdotto().equals( context.getString( R.string.tutti ) )) {
                    titolo.setText( context.getString( R.string.scontoDi ) + " " + stringCoupon.getPrezzo() + " ,00€ " + context.getString( R.string.sututtolimporto ) );
                } else {
                    titolo.setText( context.getString( R.string.scontoDi ) + " " + stringCoupon.getPrezzo() + " ,00€ " + context.getString( R.string.su ) + stringCoupon.getQualeProdotto() );
                }
            }

        }





        return view;


    }
}
