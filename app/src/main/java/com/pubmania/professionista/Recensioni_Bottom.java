package com.pubmania.professionista;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
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
                    }
                    if(arrayList.size() == 0){
                        nessRec.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    Spinner spinner;
    boolean c = false;
    private void setSPinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("jnfdskjfn","ofasdasdnd");

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
    float d = 1f;
    float a = 2f;
    int valutazioneUno,valutazioneDue,valutazioneTre,valutazioneQuattro,valutazioneCinque;
    int countMedia;
    private void setTimer() {
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
                                    int valStruttura = Integer.parseInt(doc.getString("valStruttura"));
                                    int valProdotti = Integer.parseInt(doc.getString("valProdotti"));
                                    int valServizi = Integer.parseInt(doc.getString("valServizio"));
                                    int valBagni = Integer.parseInt(doc.getString("valBagni"));
                                    Log.d("kjnfkdjsn", String.valueOf(valBagni));
                                    int valQuantitaGente = Integer.parseInt(doc.getString("valQuantitaGente"));
                                    int valRagazzi = Integer.parseInt(doc.getString("valRagazzi"));
                                    int valRagazze = Integer.parseInt(doc.getString("valRagazze"));
                                    int valPrezzi = Integer.parseInt(doc.getString("valPrezzi"));
                                    int valDivertimento = Integer.parseInt(doc.getString("valDivertimento"));

                                    countMedia = 0;
                                    if(valStruttura > 0){
                                        countMedia +=1;
                                    }
                                    if(valProdotti>0){
                                        countMedia +=1;
                                    }
                                    if(valServizi>0){
                                        countMedia +=1;
                                    }
                                    if(valBagni>0){
                                        countMedia +=1;
                                    }
                                    if(valQuantitaGente>0){
                                        countMedia +=1;
                                    }
                                    if(valRagazzi>0){
                                        countMedia +=1;
                                    }
                                    if(valRagazze>0){
                                        countMedia +=1;
                                    }
                                    if(valPrezzi>0){
                                        countMedia +=1;
                                    }
                                    if(valDivertimento>0){
                                        countMedia +=1;
                                    }
                                    int somma = valStruttura + valProdotti + valServizi+valBagni+valQuantitaGente+valRagazzi+valRagazze
                                            +valPrezzi+valDivertimento;
                                    int media = somma / countMedia;
                                    Log.d("ijnfkdsjnf",media + " " + somma
                                            + " " +media);
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