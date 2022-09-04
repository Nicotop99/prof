package com.pubmania.professionista.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.pubmania.professionista.R;
import com.pubmania.professionista.StringAdapter.StringRec;

import java.util.ArrayList;

public class AdapterRecensioni extends ArrayAdapter<StringRec> {
    Activity context;
    ArrayList<StringRec> arrayList;

    public AdapterRecensioni(@NonNull Context context, ArrayList<StringRec> arrayList) {
        super(context, 0,arrayList);
        this.context =(Activity) context;
        this.arrayList = arrayList;
    }

    int countMedia,countTot;
    int media;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_recensioni_pub,null,false);
        StringRec stringRec = getItem(position);
        ImageView immagineLato = (ImageView) view.findViewById(R.id.imageView40);
        TextView titolo = (TextView) view.findViewById(R.id.textView93);
        TextView desc = (TextView) view.findViewById(R.id.textView95);
        ImageView rec1 = (ImageView) view.findViewById(R.id.imageView41);
        ImageView rec2 = (ImageView) view.findViewById(R.id.imageView42);
        ImageView rec3 = (ImageView) view.findViewById(R.id.imageView43);
        ImageView rec4 = (ImageView) view.findViewById(R.id.imageView44);
        ImageView rec5 = (ImageView) view.findViewById(R.id.imageView45);
        TextView ora = (TextView) view.findViewById(R.id.textView96);
        CardView cardView = (CardView) view.findViewById(R.id.materialCardView);
        if(stringRec.getTitolo().isEmpty()){
            titolo.setVisibility(View.GONE);
        }else{
            titolo.setText(stringRec.getTitolo());
        }

        if(stringRec.getDesc().isEmpty()){
            desc.setVisibility(View.GONE);
        }else{
            desc.setText(stringRec.getDesc());
        }

        if(stringRec.getArrayList() != null){
            Glide.with(context).load(stringRec.getArrayList().get(0)).into(immagineLato);
        }else{
            immagineLato.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
        }

        ora.setText(stringRec.getOra());

        if(!stringRec.getValStruttura().equals("0")){
            countMedia +=1;
            countTot +=Integer.parseInt( stringRec.getValStruttura());
        }
        if(!stringRec.getValProdotti().equals("0")){
            countMedia +=1;
            countTot +=Integer.parseInt( stringRec.getValProdotti());
        }
        if(!stringRec.getValServizio().equals("0")){
            countMedia +=1;
            countTot +=Integer.parseInt( stringRec.getValServizio());
        }
        if(!stringRec.getValBagni().equals("0")){
            countMedia +=1;
            countTot +=Integer.parseInt( stringRec.getValBagni());
        }if(!stringRec.getValQuantitaGente().equals("0")){
            countMedia +=1;
            countTot +=Integer.parseInt( stringRec.getValQuantitaGente());
        }if(!stringRec.getValRagazze().equals("0")){
            countMedia +=1;
            countTot +=Integer.parseInt( stringRec.getValRagazze());
        }if(!stringRec.getValRagazzi().equals("0")){
            countMedia +=1;
            countTot +=Integer.parseInt( stringRec.getValRagazzi());
        }
        if(!stringRec.getValPrezzi().equals("0")){
            countMedia +=1;
            countTot +=Integer.parseInt( stringRec.getValPrezzi());
        }
        if(!stringRec.getValDivertimento().equals("0")){
            countMedia +=1;
            countTot +=Integer.parseInt( stringRec.getValDivertimento());
        }



        media = countTot / countMedia;
        if(media == 1){
            rec1.setImageResource(R.drawable.ballrecensionegiallo);
            rec2.setImageResource(R.drawable.ballrecensionebianco);
            rec3.setImageResource(R.drawable.ballrecensionebianco);
            rec4.setImageResource(R.drawable.ballrecensionebianco);
            rec5.setImageResource(R.drawable.ballrecensionebianco);
        }else if(media == 2){
            rec1.setImageResource(R.drawable.ballrecensionegiallo);
            rec2.setImageResource(R.drawable.ballrecensionegiallo);
            rec3.setImageResource(R.drawable.ballrecensionebianco);
            rec4.setImageResource(R.drawable.ballrecensionebianco);
            rec5.setImageResource(R.drawable.ballrecensionebianco);
        }else if(media == 3){
            rec1.setImageResource(R.drawable.ballrecensionegiallo);
            rec2.setImageResource(R.drawable.ballrecensionegiallo);
            rec3.setImageResource(R.drawable.ballrecensionegiallo);
            rec4.setImageResource(R.drawable.ballrecensionebianco);
            rec5.setImageResource(R.drawable.ballrecensionebianco);
        }else if(media == 4){
            rec1.setImageResource(R.drawable.ballrecensionegiallo);
            rec2.setImageResource(R.drawable.ballrecensionegiallo);
            rec3.setImageResource(R.drawable.ballrecensionegiallo);
            rec4.setImageResource(R.drawable.ballrecensionegiallo);
            rec5.setImageResource(R.drawable.ballrecensionebianco);
        }else if(media == 5){
            rec1.setImageResource(R.drawable.ballrecensionegiallo);
            rec2.setImageResource(R.drawable.ballrecensionegiallo);
            rec3.setImageResource(R.drawable.ballrecensionegiallo);
            rec4.setImageResource(R.drawable.ballrecensionegiallo);
            rec5.setImageResource(R.drawable.ballrecensionegiallo);
        }



        return view;
    }
}
