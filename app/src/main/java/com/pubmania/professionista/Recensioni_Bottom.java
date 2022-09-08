package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pubmania.professionista.Adapter.AdapterRecensioni;
import com.pubmania.professionista.Adapter.array_list_coupon;
import com.pubmania.professionista.StringAdapter.ArrayProdotto;
import com.pubmania.professionista.StringAdapter.StringCoupon;
import com.pubmania.professionista.StringAdapter.StringRec;
import com.pubmania.professionista.StringAdapter.StringRecensioni;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Recensioni_Bottom extends AppCompatActivity {

    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList = new ArrayList();
    String email;
    private BarChart chart;
    private static final int MAX_X_VALUE = 7;
    private static final int MAX_Y_VALUE = 50;
    private static final int MIN_Y_VALUE = 5;
    private static final String SET_LABEL = "App Downloads";
    private static final String[] DAYS = { "1", "2", "3", "4", "5" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recensioni_bottom);
        email = "oliverio.enicola@gmail.com";
        // initializing variable for bar chart.
        chart = findViewById(R.id.idBarChart);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.setScaleEnabled(false);
        chart.getDescription().setEnabled(false);



        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);

        xAxis.setEnabled(true);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        // xAxis.setValueFormatter(new IndexAxisValueFormatter(DAYS));


        YAxis axisRight = chart.getAxisRight();
        axisRight.setGranularity(10f);

        axisRight.setGranularity(1.0f);

        YAxis axisRighttt = chart.getAxisLeft();
        axisRighttt.setGranularity(10f);

        axisRighttt.setGranularity(1.0f);



        setTimer();
        setSPinner();
        setListView();
    }

    ListView listView;
    ArrayList<StringRec> arrayList;
    TextView nessRec;
    private void setListView() {
        arrayList = new ArrayList<>();
        nessRec = (TextView) findViewById(R.id.textView94);
        listView = (ListView) findViewById(R.id.listRec);
        firebaseFirestore.collection(email+"Rec").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : list){
                        StringRec stringRec = documentSnapshot.toObject(StringRec.class);
                        if(!documentSnapshot.getString("titolo").equals("") && !documentSnapshot.getString("desc").equals("")) {
                            arrayList.add(stringRec);
                        }
                        AdapterRecensioni adapterRecensioni = new AdapterRecensioni(Recensioni_Bottom.this,arrayList);
                        listView.setAdapter(adapterRecensioni);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                clickItem(view);
                            }
                        });
                    }
                    if(arrayList.size() == 0){
                        nessRec.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
    ImageView uno8,due8,tre8,quattro8,cinque8;
    ImageView uno7,due7,tre7,quattro7,cinque7;
    ImageView uno6,due6,tre6,quattro6,cinque6;
    ImageView uno10,due10,tre10,quattro10,cinque10;
    ImageView uno5,due5,tre5,quattro5,cinque5;
    ImageView uno4,due4,tre4,quattro4,cinque4;
    ImageView uno3,due3,tre3,quattro3,cinque3;
    ImageView uno2,due2,tre2,quattro2,cinque2;
    ImageView uno1,due1,tre1,quattro1,cinque1;
    ImageSlider imageSlider;
    TextView recensioniDi,titolo,desc;
    ArrayList<SlideModel> arraySlider;
    private void clickItem(View view) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( Recensioni_Bottom.this, R.style.MyDialogThemedee );
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View viewView = inflater.inflate( R.layout.click_rec_pub,null);
        dialogBuilder.setView( viewView );
        AlertDialog alertDialogg = dialogBuilder.create();
        alertDialogg.show();

        setidListView(viewView);
        arraySlider = new ArrayList<>();

        TextView idPost = (TextView) view.findViewById(R.id.textView103);
        firebaseFirestore.collection(email+"Rec").document(idPost.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                StringRec stringRec = documentSnapshot.toObject(StringRec.class);
                if(stringRec.getArrayList() == null){
                    imageSlider.setVisibility(View.GONE);
                }
                else{
                    for (int i = 0;i<stringRec.getArrayList().size();i++) {
                        arraySlider.add( new SlideModel( String.valueOf( stringRec.getArrayList().get(i)), ScaleTypes.CENTER_INSIDE ) );

                        if(i + 1 == stringRec.getArrayList().size()){
                            imageSlider.setImageList(arraySlider);

                        }
                    }
                }
                firebaseFirestore.collection("Pubblico").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot1 : task.getResult()){
                                if(documentSnapshot1.getString("email").equals(stringRec.getEmailPubblico())){
                                    recensioniDi.setText(getString(R.string.recensioneDi) + documentSnapshot1.getString("nome") + " " + " " + documentSnapshot1.getString("cognome"));
                                }
                            }
                        }
                    }
                });
                titolo.setText(stringRec.getTitolo());
                desc.setText(stringRec.getDesc());
                {
                    if (stringRec.getValStruttura().equals("1")) {
                        uno1.setImageResource(R.drawable.ballrecensionegiallo);
                    } else if (stringRec.getValStruttura().equals("2")) {
                        uno1.setImageResource(R.drawable.ballrecensionegiallo);
                        due1.setImageResource(R.drawable.ballrecensionegiallo);
                    } else if (stringRec.getValStruttura().equals("3")) {
                        uno1.setImageResource(R.drawable.ballrecensionegiallo);
                        due1.setImageResource(R.drawable.ballrecensionegiallo);
                        tre1.setImageResource(R.drawable.ballrecensionegiallo);
                    } else if (stringRec.getValStruttura().equals("4")) {
                        uno1.setImageResource(R.drawable.ballrecensionegiallo);
                        due1.setImageResource(R.drawable.ballrecensionegiallo);
                        tre1.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro1.setImageResource(R.drawable.ballrecensionegiallo);
                    } else if (stringRec.getValStruttura().equals("5")) {
                        uno1.setImageResource(R.drawable.ballrecensionegiallo);
                        due1.setImageResource(R.drawable.ballrecensionegiallo);
                        tre1.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro1.setImageResource(R.drawable.ballrecensionegiallo);
                        cinque1.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                }

                {
                    if(stringRec.getValProdotti().equals("1")){
                        uno2.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValProdotti().equals("2")){
                        uno2.setImageResource(R.drawable.ballrecensionegiallo);
                        due2.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValProdotti().equals("3")){
                        uno2.setImageResource(R.drawable.ballrecensionegiallo);
                        due2.setImageResource(R.drawable.ballrecensionegiallo);
                        tre2.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValProdotti().equals("4")){
                        uno2.setImageResource(R.drawable.ballrecensionegiallo);
                        due2.setImageResource(R.drawable.ballrecensionegiallo);
                        tre2.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro2.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValProdotti().equals("5")){
                        uno2.setImageResource(R.drawable.ballrecensionegiallo);
                        due2.setImageResource(R.drawable.ballrecensionegiallo);
                        tre2.setImageResource(R.drawable.ballrecensionegiallo);
                        uno2.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro2.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                }

                {
                    if(stringRec.getValServizio().equals("1")){
                        uno3.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValServizio().equals("2")){
                        uno3.setImageResource(R.drawable.ballrecensionegiallo);
                        due3.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValServizio().equals("3")){
                        uno3.setImageResource(R.drawable.ballrecensionegiallo);
                        due3.setImageResource(R.drawable.ballrecensionegiallo);
                        tre3.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValServizio().equals("4")){
                        uno3.setImageResource(R.drawable.ballrecensionegiallo);
                        due3.setImageResource(R.drawable.ballrecensionegiallo);
                        tre3.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro4.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValServizio().equals("5")){
                        uno3.setImageResource(R.drawable.ballrecensionegiallo);
                        due3.setImageResource(R.drawable.ballrecensionegiallo);
                        tre3.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro4.setImageResource(R.drawable.ballrecensionegiallo);
                        cinque3.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                }

                {
                    if(stringRec.getValBagni().equals("1")){
                        uno4.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValBagni().equals("2")){
                        uno4.setImageResource(R.drawable.ballrecensionegiallo);
                        due4.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValBagni().equals("3")){
                        uno4.setImageResource(R.drawable.ballrecensionegiallo);
                        due4.setImageResource(R.drawable.ballrecensionegiallo);
                        tre4.setImageResource(R.drawable.ballrecensionegiallo);


                    }
                    else if(stringRec.getValBagni().equals("4")){
                        uno4.setImageResource(R.drawable.ballrecensionegiallo);
                        due4.setImageResource(R.drawable.ballrecensionegiallo);
                        tre4.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro4.setImageResource(R.drawable.ballrecensionegiallo);

                    }
                    else if(stringRec.getValBagni().equals("5")){
                        uno4.setImageResource(R.drawable.ballrecensionegiallo);
                        due4.setImageResource(R.drawable.ballrecensionegiallo);
                        tre4.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro4.setImageResource(R.drawable.ballrecensionegiallo);
                        cinque4.setImageResource(R.drawable.ballrecensionegiallo);

                    }
                }

                {
                    if(stringRec.getValQuantitaGente().equals("1")){
                        uno5.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValQuantitaGente().equals("2")){
                        uno5.setImageResource(R.drawable.ballrecensionegiallo);
                        due5.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValQuantitaGente().equals("3")){
                        uno5.setImageResource(R.drawable.ballrecensionegiallo);
                        due5.setImageResource(R.drawable.ballrecensionegiallo);
                        tre5.setImageResource(R.drawable.ballrecensionegiallo);


                    }
                    else if(stringRec.getValQuantitaGente().equals("4")){
                        uno5.setImageResource(R.drawable.ballrecensionegiallo);
                        due5.setImageResource(R.drawable.ballrecensionegiallo);
                        tre5.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro5.setImageResource(R.drawable.ballrecensionegiallo);

                    }
                    else if(stringRec.getValQuantitaGente().equals("5")){
                        uno5.setImageResource(R.drawable.ballrecensionegiallo);
                        due5.setImageResource(R.drawable.ballrecensionegiallo);
                        tre5.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro5.setImageResource(R.drawable.ballrecensionegiallo);
                        cinque5.setImageResource(R.drawable.ballrecensionegiallo);

                    }
                }

                {
                    if(stringRec.getValRagazzi().equals("1")){
                        uno10.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValRagazzi().equals("2")){
                        uno10.setImageResource(R.drawable.ballrecensionegiallo);
                        due10.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValRagazzi().equals("3")){
                        uno10.setImageResource(R.drawable.ballrecensionegiallo);
                        due10.setImageResource(R.drawable.ballrecensionegiallo);
                        tre10.setImageResource(R.drawable.ballrecensionegiallo);


                    }
                    else if(stringRec.getValRagazzi().equals("4")){
                        uno10.setImageResource(R.drawable.ballrecensionegiallo);
                        due10.setImageResource(R.drawable.ballrecensionegiallo);
                        tre10.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro10.setImageResource(R.drawable.ballrecensionegiallo);

                    }
                    else if(stringRec.getValRagazzi().equals("5")){
                        uno10.setImageResource(R.drawable.ballrecensionegiallo);
                        due10.setImageResource(R.drawable.ballrecensionegiallo);
                        tre10.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro10.setImageResource(R.drawable.ballrecensionegiallo);
                        cinque10.setImageResource(R.drawable.ballrecensionegiallo);

                    }
                }

                {
                    if(stringRec.getValRagazze().equals("1")){
                        uno6.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValRagazze().equals("2")){
                        uno6.setImageResource(R.drawable.ballrecensionegiallo);
                        due6.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValRagazze().equals("3")){
                        uno6.setImageResource(R.drawable.ballrecensionegiallo);
                        due6.setImageResource(R.drawable.ballrecensionegiallo);
                        tre6.setImageResource(R.drawable.ballrecensionegiallo);


                    }
                    else if(stringRec.getValRagazze().equals("4")){
                        uno6.setImageResource(R.drawable.ballrecensionegiallo);
                        due6.setImageResource(R.drawable.ballrecensionegiallo);
                        tre6.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro6.setImageResource(R.drawable.ballrecensionegiallo);

                    }
                    else if(stringRec.getValRagazze().equals("5")){
                        uno6.setImageResource(R.drawable.ballrecensionegiallo);
                        due6.setImageResource(R.drawable.ballrecensionegiallo);
                        tre6.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro6.setImageResource(R.drawable.ballrecensionegiallo);
                        cinque6.setImageResource(R.drawable.ballrecensionegiallo);

                    }
                }

                {
                    if(stringRec.getValPrezzi().equals("1")){
                        uno7.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValPrezzi().equals("2")){
                        uno7.setImageResource(R.drawable.ballrecensionegiallo);
                        due7.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValPrezzi().equals("3")){
                        uno7.setImageResource(R.drawable.ballrecensionegiallo);
                        due7.setImageResource(R.drawable.ballrecensionegiallo);
                        tre7.setImageResource(R.drawable.ballrecensionegiallo);


                    }
                    else if(stringRec.getValPrezzi().equals("4")){
                        uno7.setImageResource(R.drawable.ballrecensionegiallo);
                        due7.setImageResource(R.drawable.ballrecensionegiallo);
                        tre7.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro7.setImageResource(R.drawable.ballrecensionegiallo);

                    }
                    else if(stringRec.getValPrezzi().equals("5")){
                        uno7.setImageResource(R.drawable.ballrecensionegiallo);
                        due7.setImageResource(R.drawable.ballrecensionegiallo);
                        tre7.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro7.setImageResource(R.drawable.ballrecensionegiallo);
                        cinque7.setImageResource(R.drawable.ballrecensionegiallo);

                    }
                }

                {
                    if(stringRec.getValDivertimento().equals("1")){
                        uno8.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValDivertimento().equals("2")){
                        uno8.setImageResource(R.drawable.ballrecensionegiallo);
                        due8.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                    else if(stringRec.getValDivertimento().equals("3")){
                        uno8.setImageResource(R.drawable.ballrecensionegiallo);
                        due8.setImageResource(R.drawable.ballrecensionegiallo);
                        tre8.setImageResource(R.drawable.ballrecensionegiallo);


                    }
                    else if(stringRec.getValDivertimento().equals("4")){
                        uno8.setImageResource(R.drawable.ballrecensionegiallo);
                        due8.setImageResource(R.drawable.ballrecensionegiallo);
                        tre8.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro8.setImageResource(R.drawable.ballrecensionegiallo);

                    }
                    else if(stringRec.getValDivertimento().equals("5")){
                        uno8.setImageResource(R.drawable.ballrecensionegiallo);
                        due8.setImageResource(R.drawable.ballrecensionegiallo);
                        tre8.setImageResource(R.drawable.ballrecensionegiallo);
                        quattro8.setImageResource(R.drawable.ballrecensionegiallo);
                        cinque8.setImageResource(R.drawable.ballrecensionegiallo);

                    }
                }

            }


        });




    }

    private void setidListView(View view) {
        imageSlider = (ImageSlider) view.findViewById(R.id.imageSlider);
        recensioniDi = (TextView) view.findViewById(R.id.textView98);
        titolo = (TextView) view.findViewById(R.id.textView100);
        desc = (TextView) view.findViewById(R.id.textView102);
        uno1 = (ImageView)view. findViewById( R.id.imageView48 );
        due1 = (ImageView) view.findViewById( R.id.imageView49 );
        tre1 = (ImageView) view.findViewById( R.id.imageView50 );
        quattro1 = (ImageView) view.findViewById( R.id.imageView51 );
        cinque1 = (ImageView) view.findViewById( R.id.imageView52 );
        uno2 = (ImageView) view.findViewById(R.id.imageView483);
        due2 = (ImageView) view.findViewById(R.id.imageView493);
        tre2 = (ImageView)view. findViewById(R.id.imageView503);
        quattro2 = (ImageView) view.findViewById(R.id.imageView513);
        cinque2 = (ImageView) view.findViewById(R.id.imageView523);
        uno3 = (ImageView) view.findViewById(R.id.imageView4831);
        due3 = (ImageView)view. findViewById(R.id.imageView4931);
        tre3 = (ImageView) view.findViewById(R.id.imageView5031);
        quattro3 = (ImageView)view. findViewById(R.id.imageView5131);
        cinque3 = (ImageView) view.findViewById(R.id.imageView5231);
        uno4 = (ImageView) view.findViewById(R.id.imageView482);
        due4 = (ImageView)view. findViewById(R.id.imageView492);
        tre4 = (ImageView)view. findViewById(R.id.imageView502);
        quattro4 = (ImageView) view.findViewById(R.id.imageView512);
        cinque4 = (ImageView) view.findViewById(R.id.imageView522);
        uno5 = (ImageView) view.findViewById(R.id.imageView484);
        due5 = (ImageView)view. findViewById(R.id.imageView494);
        tre5 = (ImageView)view. findViewById(R.id.imageView504);
        quattro5 = (ImageView)view. findViewById(R.id.imageView514);
        cinque5 = (ImageView)view. findViewById(R.id.imageView524);
        uno10 = (ImageView) view.findViewById(R.id.imageView48459);
        due10 = (ImageView) view.findViewById(R.id.imageView49459);
        tre10 = (ImageView)view. findViewById(R.id.imageView50459);
        quattro10 = (ImageView) view.findViewById(R.id.imageView51459);
        cinque10 = (ImageView)view. findViewById(R.id.imageView52459);
        uno6 = (ImageView) view.findViewById(R.id.imageView4845);
        due6 = (ImageView) view.findViewById(R.id.imageView4945);
        tre6 = (ImageView) view.findViewById(R.id.imageView5045);
        quattro6 = (ImageView)view. findViewById(R.id.imageView5145);
        cinque6 = (ImageView)view. findViewById(R.id.imageView5245);
        uno7 = (ImageView)view. findViewById(R.id.imageView4846);
        due7 = (ImageView)view. findViewById(R.id.imageView4946);
        tre7 = (ImageView) view.findViewById(R.id.imageView5046);
        quattro7 = (ImageView) view.findViewById(R.id.imageView51646);
        cinque7 = (ImageView) view.findViewById(R.id.imageView5246);
        uno8 = (ImageView) view.findViewById(R.id.imageView48467);
        due8 = (ImageView) view.findViewById(R.id.imageView49467);
        tre8 = (ImageView) view.findViewById(R.id.imageView50467);
        quattro8 = (ImageView) view.findViewById(R.id.imageView516467);
        cinque8 = (ImageView)view. findViewById(R.id.imageView52467);
    }

    Spinner spinner;
    boolean c = false;
    private void setSPinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("jnfdskjfn","ofasdasdnd");
                d = 0;
                if(c == true) {
                    valutazioneUno = 0;
                    valutazioneDue = 0;
                    valutazioneTre = 0;
                    valutazioneQuattro = 0;
                    valutazioneCinque = 0;
                    String periodo = spinner.getSelectedItem().toString();
                    barEntriesArrayList.clear();
                    if (periodo.equals(getString(R.string.ultimi7giorno))) {
                        giorni = 7;
                        setTimer();
                    } else if (periodo.equals(getString(R.string.ultimi14giorni))) {
                        giorni = 14;
                        setTimer();
                    } else if (periodo.equals(getString(R.string.trenta30giornifa))) {
                        giorni = 30;
                        setTimer();
                    } else if (periodo.equals(getString(R.string.seimesifa))) {
                        giorni = 180;
                        setTimer();
                    } else if (periodo.equals(getString(R.string.unannofa))) {
                        giorni = 365;
                        setTimer();
                    } else if (periodo.equals(getString(R.string.sempre))) {
                        giorni = 10000000;
                        setTimer();

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("jnfdskjfn","ofnd");
            }
        });
    }


    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    int giorni = 7;
    int d = 1;
    float a = 2f;
    int valutazioneUno,valutazioneDue,valutazioneTre,valutazioneQuattro,valutazioneCinque;
    int countMedia;
    TextView coutRecensioni;
    private void setTimer() {
        coutRecensioni = (TextView) findViewById(R.id.textView97);
        firebaseFirestore.collection(email+"Rec").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){

                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot doc : list) {
                            StringRec stringCoupon1 = doc.toObject( StringRec.class );
                            SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );
                            String currentDateandTime = sdf.format( new Date() );
                            Date currentTime = Calendar.getInstance().getTime();
                            Log.d("ofdosf", String.valueOf(currentTime.getTime()));
                            String dataPost = doc.getString("ora");
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            formatter.setLenient(false);
                            Date oldDate = null;
                            try {
                                oldDate = formatter.parse(dataPost);
                                Log.d("jnfkjdsf", String.valueOf(oldDate.getTime()));
                                long diff = currentTime.getTime() - oldDate.getTime();
                                int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                if(days<giorni){

                                    int media = Integer.parseInt(doc.getString("media"));

                                    if(media == 1){
                                        valutazioneUno +=1;
                                        Log.d("ljksdfn","klflfld");
                                    }else if(media ==2){
                                        valutazioneDue += 1;
                                    }else if(media == 3){
                                        valutazioneTre += 1;
                                    }else if(media == 4){
                                        valutazioneQuattro +=1;
                                    }else if(media == 5){
                                        valutazioneCinque +=1;
                                    }


                                    d +=1;
                                    a += 2f;
                                }


                            } catch (ParseException e) {
                                e.printStackTrace();
                                Log.d("jnfkjdsf",e.getMessage());
                            }














                        }



                        Log.d("kjfndknf", valutazioneUno + " " + valutazioneDue + valutazioneTre + valutazioneQuattro + valutazioneCinque);
                        Log.d("onfdsn","ofnsssssd");

                        barEntriesArrayList.add(new BarEntry(1,valutazioneUno));
                        barEntriesArrayList.add(new BarEntry(2, valutazioneDue));
                        barEntriesArrayList.add(new BarEntry(3,valutazioneTre));
                        barEntriesArrayList.add(new BarEntry(4,valutazioneQuattro));
                        barEntriesArrayList.add(new BarEntry(5,valutazioneCinque));
                        barDataSet = new BarDataSet(barEntriesArrayList, getString(R.string.valutazionie));
                        BarData data = new BarData(barDataSet);
                        coutRecensioni.setText(getString(R.string.cisono)+ " " + d + " " + getString(R.string.recensioni) );
                        data.setDrawValues(false);

                        chart.setData(data);
                        barDataSet.setValueTextSize(16f);
                        chart.invalidate();
                        c = true;
                        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                Log.d("jnfkjdsf",e.getX() + " " + e.getY());
                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Recensioni_Bottom.this, R.style.MyDialogThemeeee );
// ...Irrelevant code for customizing the buttons and title


                                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                View viewView = inflater.inflate( R.layout.layout_click_grafico, null );

                                dialogBuilderr.setView( viewView );

                                AlertDialog alertDialogg = dialogBuilderr.create();
                                alertDialogg.show();
                                TextView titolo = (TextView)viewView.findViewById(R.id.textView91);
                                TextView size = (TextView)viewView.findViewById(R.id.textView92);
                                titolo.setText((int)e.getY() + " "+ getString(R.string.recension));
                                size.setText((int)e.getX()+" " + getString(R.string.stelle));

                            }

                            @Override
                            public void onNothingSelected() {

                            }
                        });


                }
            }
        });


    }




    private void prepareChartData(BarData data) {
        data.setValueTextSize(12f);
        chart.setData(data);
        chart.invalidate();
    }


}