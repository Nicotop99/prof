package com.pubmania.professionista;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pubmania.professionista.Adapter.array_list_coupon;
import com.pubmania.professionista.StringAdapter.ArrayProdotto;
import com.pubmania.professionista.StringAdapter.StringCoupon;
import com.pubmania.professionista.StringAdapter.StringRec;
import com.pubmania.professionista.StringAdapter.StringRecensioni;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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




        setTimer();
    }


    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
    ArrayList<Integer>prova = new ArrayList<>();
    ArrayList<Integer>provaCheck = new ArrayList<>();
    float d = 1f;
    float a = 2f;
    ArrayList<ArrayProdotto> arrayList = new ArrayList<>();

    private void setTimer() {
        firebaseFirestore.collection(email+"Rec").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot :list){
                        StringRec stringRec = documentSnapshot.toObject(StringRec.class);

                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );
                            String currentDateandTime = sdf.format( new Date() );
                            Date date1 = simpleDateFormat.parse( currentDateandTime );

                            Date date2 = simpleDateFormat.parse( stringRec.getOra() );

                            int different = (int) (date1.getTime() - date2.getTime());
                            prova.add( different );
                            provaCheck.add( different );



                            Collections.sort( prova );


                            //chi lo ha piu basso è piu recente


                        } catch (ParseException e) {
                            e.printStackTrace();

                        }
                    }
                    Collections.sort( provaCheck );
                    for (int i = 0; i < prova.size() ; i++) {
                        int finalI = i;
                        Log.d( "kfmsdklmf", String.valueOf( i ) + " " +prova.size() );
                        firebaseFirestore.collection( email + "Rec" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (queryDocumentSnapshots != null) {
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot doc : list) {
                                        StringRec stringCoupon1 = doc.toObject( StringRec.class );
                                            SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );
                                            String currentDateandTime = sdf.format( new Date() );

                                            Date date1 = null;
                                            try {
                                                date1 = simpleDateFormat.parse( currentDateandTime );
                                                Date date2 = simpleDateFormat.parse( stringCoupon1.getOra() );

                                                int different = (int) (date1.getTime() - date2.getTime());


                                                if (provaCheck.size() > 0) {
                                                    if (different == prova.get( finalI )) {
                                                        provaCheck.remove( 0 );
                                                        Log.d("jknfkjdsnf","ssssssss");


                                                        d +=1;
                                                        a += 2f;
                                                    }

                                                } else {

                                                    if (barEntriesArrayList.size() == queryDocumentSnapshots.size()) {
                                                        Log.d("jknfkjdsnf","aaaaaaa");
                                                        barDataSet = new BarDataSet(barEntriesArrayList, getString(R.string.valutazionie));
                                                        BarData data = new BarData(barDataSet);
                                                        configureChartAppearance();
                                                        prepareChartData(data);
                                                        configureChartAppearance();
                                                        prepareChartData(data);

                                                    }
                                                }



                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }



                                    }
                                }
                            }
                        } );
                    }

                }
            }
        });
        barEntriesArrayList.clear();
        barEntriesArrayList.add(new BarEntry(0f, 9f));
        barEntriesArrayList.add(new BarEntry(1f, 3f));
        barEntriesArrayList.add(new BarEntry(2f, 5f));
        barEntriesArrayList.add(new BarEntry(3f, 2f));
        barEntriesArrayList.add(new BarEntry(4f, 6f));
        Log.d("jonfdksljnf",DAYS.length + " " + barEntriesArrayList.size());
        barDataSet = new BarDataSet(barEntriesArrayList, getString(R.string.valutazionie));
        BarData data = new BarData(barDataSet);
        configureChartAppearance();
        prepareChartData(data);
        configureChartAppearance();
        prepareChartData(data);
    }


    private void configureChartAppearance() {
        chart.getDescription().setEnabled(false);
        chart.setDrawValueAboveBar(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);



//String setter in x-Axis
        chart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(DAYS));

        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setGranularity(10f);
        axisLeft.setAxisMinimum(0);

        YAxis axisRight = chart.getAxisRight();
        axisRight.setGranularity(10f);
        axisRight.setAxisMinimum(0);
    }
    private void prepareChartData(BarData data) {
        data.setValueTextSize(12f);
        chart.setData(data);
        chart.invalidate();
    }


}