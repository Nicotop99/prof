package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pubmania.professionista.Adapter.AdapterRecensioni;
import com.pubmania.professionista.StringAdapter.StringRec;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Recensioni_Bottom extends AppCompatActivity {

    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;
    BarDataSet barDataSetDash;

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
       // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // user.getMetadata().getCreationTimestamp(); prendere data di registrazione
        setGraphicsDash();
        setSpinnerDash();
        setTipo();
        setTextView();
/*
        for (int i = 0;i<900;i++){
            Map<String, Object> user = new HashMap<>();
            user.put("emailCliente", "Ada");
            user.put("emailPub", email);
            Date date = new Date();
            Random r = new Random();
            int giorno = r.nextInt(30);
            int mese = r.nextInt(6);
            int media = r.nextInt(20);
            date.setMonth( 7 );
            date.setDate( giorno );
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            String currentDateandTime = sdf.format(date);
            user.put("ora", currentDateandTime);
            user.put( "media", String.valueOf( media ));
            firebaseFirestore.collection( email+"Rec" ).add( user );
        }


 */
    }
    TextView nuoviFollower,nuoveRecensioni,mediaRecensioni,coponUtilizzati,prodttiCaricati;
    TextView perc1,perc2,perc3,perc4,perc5;
    int countFollower;
    int countFollowerAfter;
    int countRec,counRecAfte,sommaMediaAfter,sommaRecMediaAfter;
    int sommaMedia,countRecMedia;
    private void setTextView() {
        countFollower = 0;
        countFollowerAfter = 0;
        countRec = 0;
        counRecAfte = 0;
        sommaMediaAfter = 0;
        sommaRecMediaAfter = 0;
        sommaMedia = 0;
        countRecMedia = 0;
        nuoviFollower = (TextView) findViewById( R.id.textView107 );
        nuoveRecensioni = (TextView) findViewById( R.id.textView110 );
        mediaRecensioni = (TextView) findViewById( R.id.textView113 );
        coponUtilizzati = (TextView) findViewById( R.id.textView116 );
        prodttiCaricati = (TextView) findViewById( R.id.textView119 );
        perc1 = (TextView) findViewById( R.id.textView108 );
        perc2 = (TextView) findViewById( R.id.textView111 );
        perc3 = (TextView) findViewById( R.id.textView114 );
        perc4 = (TextView) findViewById( R.id.textView117 );
        perc5 = (TextView) findViewById( R.id.textView120 );


        firebaseFirestore.collection( email + "follower" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );
                        String currentDateandTime = sdf.format( new Date() );
                        Date currentTime = Calendar.getInstance().getTime();

                        String dataPost = documentSnapshot.getString("ora");
                        Log.d( "fkdslksm",dataPost );
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        formatter.setLenient(false);
                        Date oldDate = null;
                        try {
                            oldDate = formatter.parse(dataPost);

                            long diff = currentTime.getTime() - oldDate.getTime();
                            int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                            if(days<giorniDash){
                                countFollower +=1;

                            }else if(days - giorniDash < giorniDash ){
                                countFollowerAfter +=1;
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    nuoviFollower.setText( String.valueOf( countFollower ) );
                    Float percent = Float.valueOf( countFollower) / Float.valueOf( countFollowerAfter );
                    int tot = (int) (percent * 100);
                    Log.d( "fjnsd.kfnsdk",countFollower + " " + countFollowerAfter );
                    Log.d( "fdsfs", String.valueOf( 100 - tot ) );
                    if(countFollower > countFollowerAfter){
                        int x = Math.abs(100-tot);
                        perc1.setText( getString( R.string.hairicevutooo ) + " " + x + "% " + getString( R.string.followerinPiu ) );
                        perc1.setTextColor( Color.GREEN);
                    }else{
                        perc1.setText( getString( R.string.hairicevutooo ) + " " +  (100-tot) +  "% " + getString( R.string.followerInMeno ) );
                        perc1.setTextColor(Color.RED);
                    }

                }
            }
        } );
        firebaseFirestore.collection(email + "Rec"  ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );
                        String currentDateandTime = sdf.format( new Date() );
                        Date currentTime = Calendar.getInstance().getTime();

                        String dataPost = documentSnapshot.getString("ora");
                        Log.d( "fkdslksm",dataPost );
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        formatter.setLenient(false);
                        Date oldDate = null;
                        try {
                            oldDate = formatter.parse(dataPost);

                            long diff = currentTime.getTime() - oldDate.getTime();
                            int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                            if(days<giorniDash){
                                countRec +=1;
                                    sommaMedia += Integer.parseInt( documentSnapshot.getString( "media" ) );
                                    countRecMedia +=1;
                            }else if(days - giorniDash < giorniDash ){
                                counRecAfte +=1;
                                sommaMediaAfter += Integer.parseInt( documentSnapshot.getString( "media" ) );
                                sommaRecMediaAfter +=1;
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d( "fldsnlfnjsdnflnsd", sommaMedia + " " + countRecMedia );
                    double me = sommaMedia / (double) countRecMedia;
                    double after = sommaMediaAfter / (double) sommaRecMediaAfter;
                    if(me > 0) {
                        mediaRecensioni.setText( new DecimalFormat( "##.##" ).format( me ) );
                    }else{
                        mediaRecensioni.setText( "0" );
                    }
                    if(countRec > 0) {
                        nuoveRecensioni.setText( String.valueOf( countRec ) );
                    }else{
                        nuoveRecensioni.setText( "0" );
                    }
                    Float percent = Float.valueOf( countRec) / Float.valueOf( counRecAfte );
                    int tot = (int) (percent * 100);
                    Log.d( "fjnsd.kfnsdk",countRec + " " + counRecAfte );
                    Log.d( "fdsfs", String.valueOf( 100 - tot ) );
                    if( sommaMedia> counRecAfte){
                        int x = Math.abs(100-tot);
                        perc2.setText( getString( R.string.hairicevutooo ) + " " + x + "% " + getString( R.string.recensioniInPiu ) );
                        perc2.setTextColor( Color.GREEN);
                    }else if(tot == 0) {
                        perc2.setTextColor( Color.GRAY );
                        perc2.setText( getString( R.string.hairicevutooo ) + " " + "0" + "% " + getString( R.string.recensioniInPiu ) );

                    }else
                        {
                            perc2.setText( getString( R.string.hairicevutooo ) + " " +  (100-tot) +  "% " + getString( R.string.recensioniinMeno ) );
                            perc2.setTextColor(Color.RED);
                        }


                    Float percentMedia = Float.valueOf( (float) me ) / Float.valueOf( (float) after );
                    int totMedia = (int) (percentMedia * 100);
                    Log.d( "jlfndsjlnfsdn",me + " " + after + " " + totMedia );
                    if( me> after){
                        int x = Math.abs(100-totMedia);
                        perc3.setText( getString( R.string.Lamediae ) + " " + x + "% " + getString( R.string.rispoettoa ) );
                        perc3.setTextColor( Color.GREEN);
                    }else if(totMedia == 0) {
                        perc3.setTextColor( Color.GRAY );

                        perc3.setText( getString( R.string.Lamediae2 ) + " " +  (0) +  "% " + getString( R.string.rispoettoa )  );

                    }else
                        {
                            perc3.setText( getString( R.string.Lamediae2 ) + " " +  (100-totMedia) +  "% " + getString( R.string.rispoettoa )  );
                            perc3.setTextColor(Color.RED);
                        }
                    }
                }

        } );




    }

    Spinner spinnerDash;
    private void setSpinnerDash() {
        spinnerDash = (Spinner) findViewById( R.id.spinner6 );
        spinnerDash.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                d = 0;
                BarEntryDash.clear();

                if(c2 == true) {
                    uno = 0;
                    due = 0;
                    tre = 0;
                    quattro = 0;
                    cinque = 0;
                    sei = 0;
                    sette = 0;
                    String periodo = spinnerDash.getSelectedItem().toString();
                    BarEntryDash.clear();
                    intArrays.clear();
                    barDataSetDash.clear();
                    pro.clear();
                    if (periodo.equals(getString(R.string.ultimi7giorno))) {
                        giorniDash = 7;
                        setGraphicsDash();
                        setTextView();
                    } else if (periodo.equals(getString(R.string.ultimi14giorni))) {
                        giorniDash = 14;
                        setTextView();

                        setGraphicsDash();
                    } else if (periodo.equals(getString(R.string.trenta30giornifa))) {
                        giorniDash = 30;
                        setTextView();

                        setGraphicsDash();
                    } else if (periodo.equals(getString(R.string.seimesifa))) {
                        giorniDash = 180;
                        setGraphicsDash();
                        setTextView();

                    } else if (periodo.equals(getString(R.string.unannofa))) {
                        giorniDash = 365;
                        setTextView();

                        setGraphicsDash();
                    } else if (periodo.equals(getString(R.string.sempre))) {
                        giorniDash = 10000000;
                        setGraphicsDash();
                        setTextView();


                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    int giorniDash = 7;

    ArrayList BarEntryDash = new ArrayList();
    ArrayList<String> pro = new ArrayList<String>();
    ArrayList<Integer> intArrays = new ArrayList<Integer>();
    BarChart barChartDash;
    int countVisual = 0;
    int uno,due,tre,quattro,cinque,sei,sette;
    boolean c2 = false;
    int t = 0;
    TextView dataText;
    BarData data;
    int prova;
    TextView countVisitiText;
    TextView percentualeGrafico;
    private void setGraphicsDash() {
        countVisual = 0;
        prova = 0;
        percentualeGrafico = (TextView) findViewById( R.id.textView124 );
        countVisitiText = (TextView) findViewById( R.id.textView123 );
        dataText = (TextView) findViewById( R.id.textView122 );
        barChartDash = findViewById( R.id.idBarChartDash );
        barChartDash.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChartDash.setScaleEnabled(false);
        barChartDash.getDescription().setEnabled(false);
        barChartDash.clear();
        BarEntryDash.clear();

        XAxis xAxis = barChartDash.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setTextSize( 0f );
        xAxis.setDrawLabels(false);
        xAxis.setEnabled(true);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        // xAxis.setValueFormatter(new IndexAxisValueFormatter(DAYS));


        YAxis axisRight = barChartDash.getAxisRight();
        axisRight.setGranularity(10f);

        axisRight.setGranularity(1.0f);

        YAxis axisRighttt = barChartDash.getAxisLeft();
        axisRighttt.setGranularity(10f);

        axisRighttt.setGranularity(1.0f);
        if(giorniDash < 400) {
            for (int i = 0; i < giorniDash; i++) {
                SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );


                String currentDateandTime = sdf.format( new Date() );
                SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
                Calendar cal2 = Calendar.getInstance();
                try {
                    cal2.setTime( dateFormat.parse( currentDateandTime ) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cal2.add( Calendar.DATE, -(i) );
                DateFormat dateFormatt = new SimpleDateFormat("MM");
                DateFormat dateFormat2t = new SimpleDateFormat("dd");
                Date date = new Date();


                int mon = Integer.parseInt( new SimpleDateFormat("MM").format(cal2.getTime()) );
                intArrays.add( i + 1 );

                pro.add( String.valueOf( dateFormat2t.format( cal2.getTime() ) + "/" +dateFormatt.format(cal2.getTime()) ) );
            }
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(pro));

        firebaseFirestore.collection( email+"Dash" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Log.d( "lfmdlsfn", String.valueOf( task.getResult().size() ) );
                    for (int i = 0;i<giorniDash;i++){
                        BarEntryDash.add( new BarEntry( i,0 ) );
                        Log.d( "jsdnjlfsd", String.valueOf( BarEntryDash.size() ) );

                    }
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){

                        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );
                        String currentDateandTime = sdf.format( new Date() );
                        Date currentTime = Calendar.getInstance().getTime();

                        String dataPost = documentSnapshot.getString("ora");
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        formatter.setLenient(false);
                        Date oldDate = null;
                        try {
                            oldDate = formatter.parse(dataPost);

                            long diff = currentTime.getTime() - oldDate.getTime();
                            int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                            if(days<giorniDash){

                                String currentString = documentSnapshot.getString( "ora" );

                                String[] separated = currentString.split("/");
                                String giorno = separated[0] +"/"+ separated[1];




                                for (int i = 0;i<intArrays.size();i++){

                                    if(giorno.equals( pro.get( i ) )){


                                        if(!BarEntryDash.isEmpty()) {
                                            Log.d( "jkfnsdjdfnjsdn", String.valueOf( i ) );


                                             Entry entry = (Entry) BarEntryDash.get( i );
                                             t  = (int) (entry.getY() + 1);
                                             BarEntry barEntry = (BarEntry) BarEntryDash.get( i );
                                             barEntry.setY( t );
                                            BarEntryDash.set( i , barEntry );
                                            Log.d( "klmdlddm", String.valueOf( BarEntryDash.get( i ) ) );
                                             final String x = barChartDash.getXAxis().getValueFormatter().getFormattedValue(entry.getX(), barChartDash.getXAxis());





                                        }else{
                                            t = 1;

                                        }

                                        
                                    }
                                    t = 0;
                                }
                            }
                            else if(days - giorniDash < giorniDash ){
                                prova +=1;
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    for (int i = 0;i<BarEntryDash.size();i++){
                        BarEntry entry = (BarEntry) BarEntryDash.get( i );
                        countVisual += entry.getY();
                    }
                    Log.d( "ofsdfslfmsdk",countVisual+ " " + prova );
                    if(countVisual > prova){
                        percentualeGrafico.setTextColor( Color.GREEN );
                        Float percent = Float.valueOf( countVisual) / Float.valueOf( prova );
                        int per = (int) (percent * 100);
                        Log.d( "fsdlflsdkm", String.valueOf( countVisual ) + " " + prova );
                        percentualeGrafico.setText( String.valueOf(100- per ) );
                        int x = Math.abs(100-per);
                        percentualeGrafico.setText( getString( R.string.hairicevuto ) + " " + x +"% " + getString( R.string.divisiteinpiu )  );
                    }else{
                        percentualeGrafico.setTextColor( Color.RED );
                        Float percent = Float.valueOf( countVisual) / Float.valueOf( prova );
                        int per = (int) (percent * 100);
                        Log.d( "fsdlflsdkm", String.valueOf( countVisual ) + " " + prova );
                        percentualeGrafico.setText( String.valueOf( 100 - per ) );
                        percentualeGrafico.setText( getString( R.string.hairicevutooo ) + " " + per +"% " + getString( R.string.divisiteinmeno )  );

                    }
                    countVisitiText.setText( getString( R.string.hairicevuto ) + " " + countVisual + " " + getString( R.string.visitsulprofillo ) );
                    barDataSetDash = new BarDataSet(BarEntryDash, "getString(R.string.valutazionie");
                    data = new BarData(  barDataSetDash);
                    data.setDrawValues(false);
                    c2 = true;
                    barDataSetDash.setColor(Color.parseColor("#EFF0AF") );
                    barChartDash.setData(data);
                    Log.d( "pkkdmdkdm", String.valueOf( BarEntryDash.size() ) );

                    barDataSetDash.setValueTextSize(16f);
                    barChartDash.setOnChartValueSelectedListener( new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, Highlight h) {
                            final String x = barChartDash.getXAxis().getValueFormatter().getFormattedValue(e.getX(), barChartDash.getXAxis());
                            String[] parts = x.split("/"); // String array, each element is text between dots



                            String beforeFirstDot = parts[1];
                            Calendar cal=Calendar.getInstance();
                            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                            cal.set(Calendar.MONTH,Integer.parseInt( beforeFirstDot  ) - 1);
                            String month_name = month_date.format(cal.getTime());

                            dataText.setText(getString( R.string.il ) + " " +  parts[0] + " " + month_name + getString( R.string.hairicevuto ) + " " + (int)e.getY() + " " + getString( R.string.visitsulprofillo ));
                            dataText.setVisibility( View.VISIBLE );

                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    } );
                    barChartDash.invalidate();

                }
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );
    }

    ImageView recen,dash;
    boolean recensi = false;
    ConstraintLayout layout_rec,layout_dash;
    ScrollView scrollView;
    private void setTipo() {
        recen = (ImageView) findViewById( R.id.imageView46 ) ;
        dash = (ImageView) findViewById( R.id.imageView47 );
        layout_rec = (ConstraintLayout) findViewById( R.id.l_rec );
        scrollView = (ScrollView) findViewById( R.id.scrollView );
        layout_dash = (ConstraintLayout) findViewById( R.id.l_dash );
        recen.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recensi == false){
                    recensi = true;
                    layout_rec.setVisibility( View.VISIBLE );
                    recen.setImageResource( R.drawable.button_dash_in );
                    dash.setImageResource( R.drawable.button_dash_notinn );
                    layout_dash.setVisibility( View.GONE );
                    scrollView.setVisibility( View.GONE );
                    setTimer();
                    setSPinner();
                    setListView();
                    setGrafico();
                }
            }
        } );
        dash.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recensi == true){
                    recensi = false;
                    layout_dash.setVisibility( View.VISIBLE );
                    layout_rec.setVisibility( View.GONE );
                    scrollView.setVisibility( View.VISIBLE );
                    recen.setImageResource( R.drawable.button_dash_notinn );
                    dash.setImageResource( R.drawable.button_dash_in );

                }
            }
        } );

    }

    private void setGrafico() {
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

                            String dataPost = doc.getString("ora");
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            formatter.setLenient(false);
                            Date oldDate = null;
                            try {
                                oldDate = formatter.parse(dataPost);

                                long diff = currentTime.getTime() - oldDate.getTime();
                                int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                if(days<giorni){

                                    int media = Integer.parseInt(doc.getString("media"));

                                    if(media == 1){
                                        valutazioneUno +=1;

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

                            }














                        }






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







}