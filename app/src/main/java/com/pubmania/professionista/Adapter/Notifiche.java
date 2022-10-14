package com.pubmania.professionista.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pubmania.professionista.R;
import com.pubmania.professionista.StringAdapter.StringNotifiche;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Notifiche extends ArrayAdapter<StringNotifiche> {
    ArrayList<StringNotifiche> arrayList;
    Activity context;
    String email;
    public Notifiche(@NonNull Context context, ArrayList<StringNotifiche> arrayList,String email) {
        super(context, 0,arrayList);
        this.email = email;
        this.arrayList = arrayList;
        this.context=(Activity) context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_list_notifiche,null,false);
        StringNotifiche stringNotifiche = getItem(position);

        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.circleImageView2);
        TextView titolo = (TextView) view.findViewById(R.id.textView141);
        ImageView backGround = (ImageView) view.findViewById(R.id.imageView59);
        if(stringNotifiche.getVisualizzato().equals("false")){
            backGround.setImageResource(R.drawable.back_notification_not_show);
        }
        Glide.with(context).load(stringNotifiche.getFotoProfilo()).into(circleImageView);
        if(stringNotifiche.getCategoria().equals("Follower")){
            titolo.setText(stringNotifiche.getNomecognomeCliente() + " " + context.getString(R.string.hainiziatoaseguirti));
        }else if(stringNotifiche.getCategoria().equals("Recensione")){
            titolo.setText(stringNotifiche.getNomecognomeCliente() + " " + context.getString(R.string.hafattounarecensione));
        }else if(stringNotifiche.getCategoria().equals("Coupon")){
            titolo.setText(stringNotifiche.getNomecognomeCliente() + " " + context.getString(R.string.hausatouncoupon));
        }
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection(email+"Notifiche").document(stringNotifiche.getId());
        documentReference.update("visualizzato","true");



        return view;
    }
}
