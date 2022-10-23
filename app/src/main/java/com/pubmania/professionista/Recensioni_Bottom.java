package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.Result;
import com.pubmania.professionista.Adapter.AdapterRecensioni;
import com.pubmania.professionista.StringAdapter.StringNotifiche;
import com.pubmania.professionista.StringAdapter.StringRec;
import com.pubmania.professionista.StringAdapter.StringRecensioni;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
        setMenuBasso();

        /*

        for (int i = 0;i<1000;i++){
            Map<String, Object> user = new HashMap<>();
            user.put("emailCliente", "Ada");
            user.put("emailPub", email);
            Date date = new Date();
            Random r = new Random();
            int giorno = r.nextInt(30);
            int mese = r.nextInt(11);
            int media = r.nextInt(10);
            date.setMonth( mese );
            date.setDate( giorno );
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            String currentDateandTime = sdf.format(date);
            user.put("ora", currentDateandTime);
            firebaseFirestore.collection( email+"couponUtilizzati" ).add( user );
        }



         */


    }





    BottomNavigationView bottomAppBar;
    FloatingActionButton scanQR;
    AlertDialog alertDialog;
    List<String> arrayToken;
    CodeScanner codeScanner;
    boolean ciao = false;
    int countI,usate;
    int quanteVolte;
    DocumentSnapshot documentSnapshott;
    ArrayList<String>tokenList = new ArrayList<>();
    boolean entrato = false;
    String nomePub;
    boolean exist = false;
    boolean exist2 = false;
    private Bitmap my_image;
    String idPost;
    int size = 0;
    String nomeCliente;
    CodeScannerView codeScannerView;
    String tipo,prezzo,prodotti,tokenn,emailCliente;
    String urlFotoProfilo;
    Uri imageUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/pub-mania.appspot.com/o/WIN_20221012_19_25_21_Pro.jpg?alt=media&token=710c0bd3-3303-45f7-87e8-2bb6635d5c45");
    private void setMenuBasso() {
        scanQR = (FloatingActionButton) findViewById( R.id.floatBotton );
        scanQR.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size = 0;
                documentSnapshott = null;

                if (ContextCompat.checkSelfPermission(Recensioni_Bottom.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //ask for authorisation
                    ActivityCompat.requestPermissions( Recensioni_Bottom.this, new String[]{Manifest.permission.CAMERA}, 50 );
                }
                else {
                    //start your camera


                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( Recensioni_Bottom.this );
// ...Irrelevant code for customizing the buttons and title


                    LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    View viewView = inflater.inflate( R.layout.layout_sca_qrcode, null );

                    dialogBuilder.setView( viewView );

                    codeScannerView = (CodeScannerView) viewView.findViewById( R.id.scanner_view );
                    codeScanner = new CodeScanner( Recensioni_Bottom.this, codeScannerView );
                    codeScanner.startPreview();


                    codeScanner.setDecodeCallback( new DecodeCallback() {
                        @Override
                        public void onDecoded(@NonNull Result result) {
                            Log.d( "òfksdòfk",result.getText() );
                            String[] splitvirgola = result.getText().split(";");
                            String[] splitList = splitvirgola[1].split(",");
                            Log.d("ffjndaskjfn", String.valueOf(splitList.length));
                            for (int i = 0;i< splitList.length;i++){
                                Log.d("njkjnas","fnkjdnfaj");
                                Log.d("njkjnas",splitList[i]);
                            }
                            Log.d("ajkndasd",splitvirgola[1]);
                            Log.d("jfnsadjkfn",splitvirgola[1]);
                            tokenList = new ArrayList<String>(Arrays.asList(splitList));
                            usate = 1;
                            entrato  = false;
                            String[] separated = splitvirgola[0].split(":");
                            idPost = separated[1];
                            prodotti = separated[6];
                            tipo = separated[3];
                            prezzo = separated[4];
                            if(!separated[2].equals( "Sempre" )) {
                                quanteVolte = Integer.parseInt( separated[2] );
                                Log.d( "omfodsfm", String.valueOf( quanteVolte ) );

                            }
                            emailCliente = separated[0];
                            nomeCliente = separated[7];

                            Log.d( "omfodsfm",idPost );
                            countI = 1;
                            firebaseFirestore.collection("Pubblico").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                            if(documentSnapshot.getString("email").equals(emailCliente)){
                                                urlFotoProfilo = documentSnapshot.getString("fotoProfilo");
                                                firebaseFirestore.collection( email+"CouponUtilizzati" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if(task != null){
                                                            Log.d( "kmfslkfmksdmf","zerp" );

                                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                                                size = documentSnapshot.getData().size();
                                                                if(documentSnapshot.getId().equals( idPost )){
                                                                    exist2 = true;
                                                                    countI = documentSnapshot.getData().size() +1;

                                                                    documentSnapshott =documentSnapshot;
                                                                }


                                                            }

                                                            if (exist2 == true) {
                                                                for (int i = 1; i < documentSnapshott.getData().size(); i++) {
                                                                    if (documentSnapshott.getData().get( String.valueOf( i ) ).equals( emailCliente )) {

                                                                        usate += 1;
                                                                    }
                                                                }


                                                                if (quanteVolte == 1 && usate >= 1) {
                                                                    entrato = true;
                                                                    alertDialog.dismiss();
                                                                    Snackbar.make( findViewById( android.R.id.content ), getString( R.string.questoutentrhagiautilizzatoquestocoupon ), Snackbar.LENGTH_LONG ).show();
                                                                    Log.d( "kmfslkfmksdmf", "3" );

                                                                } else if (quanteVolte == 2 && usate >= 2) {

                                                                    if (usate >= 2) {
                                                                        alertDialog.dismiss();
                                                                        Log.d( "kmfslkfmksdmf", "4" );
                                                                        entrato = true;

                                                                        Snackbar.make( findViewById( android.R.id.content ), getString( R.string.questoutentrhagiautilizzatoquestocoupon ), Snackbar.LENGTH_LONG ).show();
                                                                    }
                                                                } else if (quanteVolte == 3 && usate >= 3) {

                                                                    if (usate >= 3) {
                                                                        alertDialog.dismiss();
                                                                        Log.d( "kmfslkfmksdmf", "5" );
                                                                        entrato = true;

                                                                        Snackbar.make( findViewById( android.R.id.content ), getString( R.string.questoutentrhagiautilizzatoquestocoupon ), Snackbar.LENGTH_LONG ).show();
                                                                    }
                                                                } else if (quanteVolte == 4 && usate >= 4) {

                                                                    if (usate >= 4) {
                                                                        alertDialog.dismiss();
                                                                        Log.d( "kmfslkfmksdmf", "6" );
                                                                        entrato = true;

                                                                        Snackbar.make( findViewById( android.R.id.content ), getString( R.string.questoutentrhagiautilizzatoquestocoupon ), Snackbar.LENGTH_LONG ).show();
                                                                    }
                                                                } else if (quanteVolte == 5 && usate >= 5) {

                                                                    if (usate >= 5) {
                                                                        alertDialog.dismiss();
                                                                        Log.d( "kmfslkfmksdmf", "7" );
                                                                        entrato = true;

                                                                        Snackbar.make( findViewById( android.R.id.content ), getString( R.string.questoutentrhagiautilizzatoquestocoupon ), Snackbar.LENGTH_LONG ).show();
                                                                    }
                                                                } else {
                                                                    entrato = true;
                                                                    boolean exit = false;




                                                                    Log.d( "asdsadasdasd", String.valueOf( countI ) );
                                                                    DocumentReference documentReference = firebaseFirestore.collection( email + "CouponUtilizzati" ).document( idPost );
                                                                    documentReference.update( String.valueOf( countI ), emailCliente ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {


                                                                            Log.d( "pkfmdsòkmòks","sec" );
                                                                            Map<String, Object> user = new HashMap<>();
                                                                            user.put("idPost", idPost);
                                                                            user.put( "emailPub",email );

                                                                            firebaseFirestore.collection( emailCliente+"Coupon" ).add( user )
                                                                                    .addOnCompleteListener( new OnCompleteListener<DocumentReference>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<DocumentReference> task) {





                                                                                            firebaseFirestore.collection( emailCliente+"Rec" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                    Log.d("oijflsdjfl","kkkasdasdasdsad");

                                                                                                    if(task.getResult().size() > 0){
                                                                                                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                                                                            Log.d("oijflsdjfl","unoo");

                                                                                                            if(!documentSnapshot.getString( "emailPub" ).equals( email )){
                                                                                                                Log.d("mflkfskfmksd","uno");
                                                                                                                StringRecensioni stringRecensioni = new StringRecensioni();
                                                                                                                stringRecensioni.setEmailCliente( emailCliente );
                                                                                                                stringRecensioni.setEmailPub( email );
                                                                                                                stringRecensioni.setNomeLocale( nomePub );
                                                                                                                stringRecensioni.setToken("token");
                                                                                                                stringRecensioni.setIdPost( idPost );
                                                                                                                stringRecensioni.setUrlFotoProfilo( urlFotoProfilo );
                                                                                                                firebaseFirestore.collection( emailCliente+"Rec" ).add( stringRecensioni )
                                                                                                                        .addOnCompleteListener( new OnCompleteListener<DocumentReference>() {
                                                                                                                            @Override
                                                                                                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                                                                Log.d("mflkfskfmksd","due");

                                                                                                                                DocumentReference documentReference1 = firebaseFirestore.collection( emailCliente+"Rec" ).document(task.getResult().getId());
                                                                                                                                documentReference1.update( "id", task.getResult().getId() ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                                                                                                                                        String currentDateandTime = sdf.format(new Date());
                                                                                                                                        Map<String, Object> user = new HashMap<>();
                                                                                                                                        user.put("emailPub", email);
                                                                                                                                        user.put("emailCliente", emailCliente);
                                                                                                                                        user.put("ora", currentDateandTime);
                                                                                                                                        firebaseFirestore.collection( email+"couponUtilizzati" ).add( user )
                                                                                                                                                .addOnCompleteListener( new OnCompleteListener<DocumentReference>() {
                                                                                                                                                    @Override
                                                                                                                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                                                                                        AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Recensioni_Bottom.this, R.style.MyDialogThemeeee );
                                                                                                                                                        //dddddddddddd

                                                                                                                                                        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                                                                                                                        View viewView = inflater.inflate( R.layout.layout_viualizza_coupon_scansionato, null );

                                                                                                                                                        dialogBuilderr.setView( viewView );

                                                                                                                                                        AlertDialog alertDialogg = dialogBuilderr.create();
                                                                                                                                                        alertDialogg.show();
                                                                                                                                                        TextView title = (TextView) viewView.findViewById( R.id.textView75 );
                                                                                                                                                        TextView prodotto = (TextView) viewView.findViewById( R.id.textView76 );
                                                                                                                                                        ImageButton imageButton = (ImageButton) viewView.findViewById( R.id.imageButton37 );
                                                                                                                                                        imageButton.setOnClickListener( new View.OnClickListener() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onClick(View view) {
                                                                                                                                                                alertDialogg.dismiss();
                                                                                                                                                            }
                                                                                                                                                        } );
                                                                                                                                                        if (tipo.equals( "Prezzo" )) {
                                                                                                                                                            title.setText( getString( R.string.scontoDi ) + " " + prezzo + " ,00€ " + getString( R.string.su ) );
                                                                                                                                                        } else {
                                                                                                                                                            title.setText( prezzo + " % " + getString( R.string.discontosu ) );
                                                                                                                                                        }
                                                                                                                                                        if (prodotti.equals( "Tutti" )) {
                                                                                                                                                            prodotto.setText( getText( R.string.sututtolimporto ) );
                                                                                                                                                        } else {
                                                                                                                                                            prodotto.setText( prodotti );
                                                                                                                                                        }


                                                                                                                                                        alertDialog.dismiss();
                                                                                                                                                    }
                                                                                                                                                } );






                                                                                                                                    }
                                                                                                                                } );
                                                                                                                            }
                                                                                                                        } );
                                                                                                            }
                                                                                                            else{
                                                                                                                //ciaoooo
                                                                                                                if(ciao == false){
                                                                                                                    ciao = true;
                                                                                                                    Log.d("jndkjasnkdj","njkanajs");
                                                                                                                    AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Recensioni_Bottom.this, R.style.MyDialogThemeeee );
// ...Irrelevant code for customizing the buttons and title


                                                                                                                    LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                                                                                    View viewView = inflater.inflate( R.layout.layout_viualizza_coupon_scansionato, null );

                                                                                                                    dialogBuilderr.setView( viewView );

                                                                                                                    AlertDialog alertDialogg = dialogBuilderr.create();
                                                                                                                    alertDialogg.show();
                                                                                                                    TextView title = (TextView) viewView.findViewById( R.id.textView75 );
                                                                                                                    TextView prodotto = (TextView) viewView.findViewById( R.id.textView76 );
                                                                                                                    ImageButton imageButton = (ImageButton) viewView.findViewById( R.id.imageButton37 );
                                                                                                                    imageButton.setOnClickListener( new View.OnClickListener() {
                                                                                                                        @Override
                                                                                                                        public void onClick(View view) {
                                                                                                                            alertDialogg.dismiss();
                                                                                                                            ciao = false;
                                                                                                                        }
                                                                                                                    } );
                                                                                                                    if (tipo.equals( "Prezzo" )) {
                                                                                                                        title.setText( getString( R.string.scontoDi ) + " " + prezzo + " ,00€ " + getString( R.string.su ) );
                                                                                                                    } else {
                                                                                                                        title.setText( prezzo + " % " + getString( R.string.discontosu ) );
                                                                                                                    }
                                                                                                                    if (prodotti.equals( "Tutti" )) {
                                                                                                                        prodotto.setText( getText( R.string.sututtolimporto ) );
                                                                                                                    } else {
                                                                                                                        prodotto.setText( prodotti );
                                                                                                                    }


                                                                                                                    alertDialog.dismiss();
                                                                                                                }

                                                                                                            }

                                                                                                        }

                                                                                                    }
                                                                                                    else{
                                                                                                        Log.d("mflkfskfmksd","uno");
                                                                                                        StringRecensioni stringRecensioni = new StringRecensioni();
                                                                                                        stringRecensioni.setEmailCliente( emailCliente );
                                                                                                        stringRecensioni.setEmailPub( email );
                                                                                                        stringRecensioni.setNomeLocale( nomePub );
                                                                                                        stringRecensioni.setToken("token");
                                                                                                        stringRecensioni.setIdPost( idPost );
                                                                                                        stringRecensioni.setUrlFotoProfilo( urlFotoProfilo );
                                                                                                        firebaseFirestore.collection( emailCliente+"Rec" ).add( stringRecensioni )
                                                                                                                .addOnCompleteListener( new OnCompleteListener<DocumentReference>() {
                                                                                                                    @Override
                                                                                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                                                        Log.d("mflkfskfmksd","due");

                                                                                                                        DocumentReference documentReference1 = firebaseFirestore.collection( emailCliente+"Rec" ).document(task.getResult().getId());
                                                                                                                        documentReference1.update( "id", task.getResult().getId() ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                            @Override
                                                                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                                                                                                                                String currentDateandTime = sdf.format(new Date());
                                                                                                                                Map<String, Object> user = new HashMap<>();
                                                                                                                                user.put("emailPub", email);
                                                                                                                                user.put("emailCliente", emailCliente);
                                                                                                                                user.put("ora", currentDateandTime);
                                                                                                                                firebaseFirestore.collection( email+"couponUtilizzati" ).add( user )
                                                                                                                                        .addOnCompleteListener( new OnCompleteListener<DocumentReference>() {
                                                                                                                                            @Override
                                                                                                                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                                                                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Recensioni_Bottom.this, R.style.MyDialogThemeeee );
                                                                                                                                                //dddddddddddd

                                                                                                                                                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                                                                                                                View viewView = inflater.inflate( R.layout.layout_viualizza_coupon_scansionato, null );

                                                                                                                                                dialogBuilderr.setView( viewView );

                                                                                                                                                AlertDialog alertDialogg = dialogBuilderr.create();
                                                                                                                                                alertDialogg.show();
                                                                                                                                                TextView title = (TextView) viewView.findViewById( R.id.textView75 );
                                                                                                                                                TextView prodotto = (TextView) viewView.findViewById( R.id.textView76 );
                                                                                                                                                ImageButton imageButton = (ImageButton) viewView.findViewById( R.id.imageButton37 );
                                                                                                                                                imageButton.setOnClickListener( new View.OnClickListener() {
                                                                                                                                                    @Override
                                                                                                                                                    public void onClick(View view) {
                                                                                                                                                        alertDialogg.dismiss();
                                                                                                                                                    }
                                                                                                                                                } );
                                                                                                                                                if (tipo.equals( "Prezzo" )) {
                                                                                                                                                    title.setText( getString( R.string.scontoDi ) + " " + prezzo + " ,00€ " + getString( R.string.su ) );
                                                                                                                                                } else {
                                                                                                                                                    title.setText( prezzo + " % " + getString( R.string.discontosu ) );
                                                                                                                                                }
                                                                                                                                                if (prodotti.equals( "Tutti" )) {
                                                                                                                                                    prodotto.setText( getText( R.string.sututtolimporto ) );
                                                                                                                                                } else {
                                                                                                                                                    prodotto.setText( prodotti );
                                                                                                                                                }


                                                                                                                                                alertDialog.dismiss();
                                                                                                                                            }
                                                                                                                                        } );






                                                                                                                            }
                                                                                                                        } );
                                                                                                                    }
                                                                                                                } );
                                                                                                    }
                                                                                                }
                                                                                            } );

                                                                                            //coupon utilizzato





                                                                                        }
                                                                                    } ).addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                            Log.d("lflsdnf",e.getMessage());
                                                                                        }
                                                                                    });














                                                                        }
                                                                    } ).addOnFailureListener( new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Map<String, Object> user = new HashMap<>();
                                                                            user.put( "1", emailCliente );
                                                                            firebaseFirestore.collection( email + "CouponUtilizzati" ).document( idPost ).set( user )
                                                                                    .addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            alertDialog.dismiss();
                                                                                        }
                                                                                    } ).addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                            Log.d("ofjndsljnflj",e.getMessage());
                                                                                        }
                                                                                    });
                                                                        }
                                                                    } );
                                                                }

                                                            }
                                                            else {
                                                                Log.d( "kmfslkfmksdmf", "9999" );
                                                                if (entrato == false) {
                                                                    countI = 1;
                                                                    Log.d( "Qklmfldksmdf", String.valueOf( countI ) + "lllllll" );
                                                                    Map<String, Object> user = new HashMap<>();
                                                                    user.put( "1", emailCliente );
                                                                    firebaseFirestore.collection( email + "CouponUtilizzati" ).document( idPost ).set( user )
                                                                            .addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    Log.d( "Qklmfldksmdf", "fkmdklfm" );
                                                                                    entrato = true;
                                                                                    //coupon utilizzato
                                                                                    AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Recensioni_Bottom.this, R.style.MyDialogThemeeee );
// ...Irrelevant code for customizing the buttons and title


                                                                                    LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                                                    View viewView = inflater.inflate( R.layout.layout_viualizza_coupon_scansionato, null );

                                                                                    dialogBuilderr.setView( viewView );

                                                                                    AlertDialog alertDialogg = dialogBuilderr.create();
                                                                                    alertDialogg.show();
                                                                                    TextView title = (TextView) viewView.findViewById( R.id.textView75 );
                                                                                    TextView prodotto = (TextView) viewView.findViewById( R.id.textView76 );
                                                                                    ImageButton imageButton = (ImageButton) viewView.findViewById( R.id.imageButton37 );
                                                                                    imageButton.setOnClickListener( new View.OnClickListener() {
                                                                                        @Override
                                                                                        public void onClick(View view) {
                                                                                            alertDialogg.dismiss();
                                                                                        }
                                                                                    } );
                                                                                    if (tipo.equals( "Prezzo" )) {
                                                                                        title.setText( getString( R.string.scontoDi ) + " " + prezzo + " ,00€ " + getString( R.string.su ) );
                                                                                    } else {
                                                                                        title.setText( prezzo + " % " + getString( R.string.discontosu ) );
                                                                                    }
                                                                                    if (prodotti.equals( "Tutti" )) {
                                                                                        prodotto.setText( getText( R.string.sututtolimporto ) );
                                                                                    } else {
                                                                                        prodotto.setText( prodotti );
                                                                                    }


                                                                                    alertDialog.dismiss();
                                                                                }
                                                                            } )


                                                                            .addOnFailureListener( new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Log.d( "Qklmfldksmdf", e.getMessage() );

                                                                                    Map<String, Object> user = new HashMap<>();
                                                                                    user.put( "1", emailCliente );
                                                                                    firebaseFirestore.collection( email + "CouponUtilizzati" ).document( idPost ).set( user )
                                                                                            .addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                    alertDialog.dismiss();
                                                                                                }
                                                                                            } );
                                                                                }
                                                                            } );
                                                                }
                                                            }

                                                            Log.d( "omsodfsdm", "asdasdasdsdasd" );
                                                            entrato = true;
                                                            if(task.getResult().size()== 0){
                                                                countI = 1;
                                                                Log.d( "Qklmfldksmdf", String.valueOf( countI ) +"lllllll");
                                                                Map<String, Object> user = new HashMap<>();
                                                                user.put("1", emailCliente);
                                                                firebaseFirestore.collection( email+"CouponUtilizzati" ).document(idPost).set( user )



                                                                        .addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {



                                                                                for (int i = 0;i<tokenList.size();i++){
                                                                                    if(i== 0){
                                                                                        propvaNotifica(tokenList.get(0).replace("[",""),idPost);

                                                                                    }else if(i == tokenList.size() -1){
                                                                                        propvaNotifica(tokenList.get(i).replace("]",""),idPost);

                                                                                    }

                                                                                    else{
                                                                                        propvaNotifica(tokenList.get(i),idPost);

                                                                                    }

                                                                                }
                                                                                Log.d("kjfnaskjfnjans","fuoriiii");


                                                                                //coupon utilizzato
                                                                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Recensioni_Bottom.this,R.style.MyDialogThemeeee  );
// ...Irrelevant code for customizing the buttons and title


                                                                                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                                                View viewView = inflater.inflate( R.layout.layout_viualizza_coupon_scansionato, null );

                                                                                dialogBuilderr.setView( viewView );

                                                                                AlertDialog alertDialogg = dialogBuilderr.create();
                                                                                alertDialogg.show();
                                                                                TextView title = (TextView) viewView.findViewById( R.id.textView75 );
                                                                                TextView prodotto = (TextView) viewView.findViewById( R.id.textView76 );
                                                                                ImageButton imageButton = (ImageButton) viewView.findViewById( R.id.imageButton37 );
                                                                                imageButton.setOnClickListener( new View.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(View view) {
                                                                                        alertDialogg.dismiss();
                                                                                    }
                                                                                } );
                                                                                if(tipo.equals( "Prezzo" )){
                                                                                    title.setText( getString( R.string.scontoDi ) + " " + prezzo + " ,00€ " + getString( R.string.su ));
                                                                                }else{
                                                                                    title.setText( prezzo + " % " + getString( R.string.discontosu ) );
                                                                                }
                                                                                if(prodotti.equals( "Tutti" )){
                                                                                    prodotto.setText( getText( R.string.sututtolimporto ) );
                                                                                }else{
                                                                                    prodotto.setText( prodotti );
                                                                                }



                                                                                alertDialog.dismiss();
                                                                            }
                                                                        } )


                                                                        .addOnFailureListener( new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Map<String, Object> user = new HashMap<>();
                                                                                user.put("1", emailCliente);
                                                                                firebaseFirestore.collection( email+"CouponUtilizzati" ).document(idPost).set( user )
                                                                                        .addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                alertDialog.dismiss();
                                                                                            }
                                                                                        } );
                                                                            }
                                                                        } );
                                                            }
                                                            else{
                                                                for (int i = 0;i<tokenList.size();i++){
                                                                    if(i== 0){
                                                                        propvaNotifica(tokenList.get(0).replace("[",""),idPost);

                                                                    }else if(i == tokenList.size() -1){
                                                                        propvaNotifica(tokenList.get(i).replace("]",""),idPost);

                                                                    }

                                                                    else{
                                                                        propvaNotifica(tokenList.get(i),idPost);

                                                                    }

                                                                }
                                                                Log.d("kjfnaskjfnjans","fuoriiii");

                                                            }
                                                        }
                                                        else{
                                                        }
                                                    }
                                                } );

                                            }
                                        }
                                    }
                                }
                            });
                        }
                    } );

                    alertDialog = dialogBuilder.create();
                    alertDialog.show();
                }
            }
        } );

        bottomAppBar = (BottomNavigationView) findViewById( R.id.bottomNavView );
        bottomAppBar.findViewById( R.id.nullable ).setClickable( false );
        bottomAppBar.setItemIconTintList(null);

        if ( ContextCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.WRITE_EXTERNAL_STORAGE  }, 1232);
        }else{
            Log.d("jfnalfnl","flkmsfm");
        }
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app

        bottomAppBar.setSelectedItemId(R.id.recensioniBotton);
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.dashboard_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);
        bottomAppBar.getMenu().getItem(3).setIcon(wrappedDrawable);

        Menu menu = bottomAppBar.getMenu();
        SharedPreferences sharedPreferences = getSharedPreferences("fotoProfilo",MODE_PRIVATE);
        if(sharedPreferences.getString("fotoProfilo","null").equals("null")) {
            firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task != null) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            if (documentSnapshot.getString("email").equals(email)) {
                                Log.d("fndjsdfjas", documentSnapshot.getString("fotoProfilo"));
                                if (sharedPreferences.getString("fotoProfilo", "null").equals("null")) {
                                    StorageReference storageRef = storage.getReference();
                                    StorageReference httpsReference = storage.getReferenceFromUrl(documentSnapshot.getString("fotoProfilo"));
                                    try {
                                        final File localFile = File.createTempFile("Images", ".jpg");
                                        httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                Log.d("janasnd", localFile.getAbsolutePath());
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("fotoProfilo", localFile.getAbsolutePath());
                                                editor.putString("uriFoto",documentSnapshot.getString("fotoProfilo"));
                                                editor.putString("nomePub", documentSnapshot.getString("nomeLocale"));
                                                editor.putString("nomecognome",documentSnapshot.getString("nome") + " " + documentSnapshot.getString("cognome"));
                                                editor.commit();
                                                File imgFile = new File(localFile.getAbsolutePath());

                                                if (imgFile.exists()) {

                                                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                                    Drawable profileImage = new BitmapDrawable(getResources(), myBitmap);
                                                    bottomAppBar.getMenu().getItem(4).setIcon(profileImage);


                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("ijnksaf", e.getMessage());
                                            }
                                        });
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {

                                }
                                menu.findItem(R.id.profil_page).setTitle(documentSnapshot.getString("nome"));
                                nomePub = documentSnapshot.getString("nomeLocale");
                                arrayToken = (List<String>) documentSnapshot.get("token");
                            }
                        }
                    }
                }
            });
        }
        else{


            MenuItem menuItem = menu.findItem(R.id.profil_page);

            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(sharedPreferences.getString("fotoProfilo","null"))
                    .apply(RequestOptions
                            .circleCropTransform()
                            .placeholder(Drawable.createFromPath("Your PlaceHolder")))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

                            menuItem.setIcon(new BitmapDrawable(getResources(), resource));

                        }




                    });



        }
        bottomAppBar.setOnItemSelectedListener( new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.HomeBotton:
                        startActivity( new Intent( getApplicationContext(), HomePage.class ) );
                        Log.d( "kfmdslfmlsd","fkdmlfk" );
                        finish();
                        break;
                    case R.id.couponBotton:
                        startActivity( new Intent( getApplicationContext(), couponActivity.class ) );
                        finish();
                        break;
                    case R.id.profil_page:
                        startActivity( new Intent( getApplicationContext(), Profile_bottom.class ) );
                        finish();
                        break;
                    case R.id.recensioniBotton:
                        startActivity( new Intent( getApplicationContext(), Recensioni_Bottom.class ) );
                        finish();
                        break;
                }
                return true;
            }
        } );


    }

    String nomeCognome,uriProfilo;
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAbWZozX0:APA91bElDXMdAF898t_M5ai0z5cCTkG9po-deDqqirLm5zL9FI_UgxdQtlUdH0k7fToZIClrylH5LXbEZeVXsXpbr1rpYj6FpD20mFTLOVot-YbiYjhSf85Ca7qbHI9zzCCh0nCktwYF";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";
    String TOPIC;
    private void propvaNotifica(String token, String idPosttt) {

        TOPIC = "/topics/userABC"; //topic must match with what the receiver subscribed to
        Log.d("jndjakndj",token);
        Log.d("kjndjkankjansj","djndjsandfa");
        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title",nomeCliente + " " +  getString(R.string.hausatouncoupon)  );
            notifcationBody.put("message", getString(R.string.cliccalanotifica));
            notifcationBody.put("tipo","Coupon");
            notifcationBody.put("idPost",idPosttt);
            notification.put("to", token);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
        sendNotification(notification,idPosttt,token);




    }
    boolean entratoo = false;

    private void sendNotification(JSONObject notification,String idPostt,String tok) {
        Log.d("kjndjkankjansj","222");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("kjndjkankjansj","333");

                        try {
                            if(response.getString("failure").equals("1")){

                                // token non valido
                                firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if (documentSnapshot.getString("email").equals(email)){
                                                    DocumentReference d  = firebaseFirestore.collection("Professionisti").document(documentSnapshot.getId());
                                                    ArrayList<String > a = (ArrayList<String>) documentSnapshot.get("token");
                                                    Log.d("njanfd", String.valueOf(a.size()));
                                                    for (int i = 0;i<a.size();i++){
                                                        if(a.get(i).equals(tok)){
                                                            a.remove(i);
                                                        }
                                                    }
                                                    d.update("token",a).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Log.d("onjlm","ok");
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.d("onjlm","ok" + e.getMessage());

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(entratoo == false) {
                            StringNotifiche stringNotifiche = new StringNotifiche();
                            stringNotifiche.setCategoria("Coupon");
                            stringNotifiche.setVisualizzato("false");
                            stringNotifiche.setEmailCliente(email);
                            stringNotifiche.setEmailPub(email);
                            stringNotifiche.setFotoProfilo(urlFotoProfilo);
                            stringNotifiche.setIdPost(idPostt);
                            stringNotifiche.setNomecognomeCliente(nomeCliente);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());
                            stringNotifiche.setOra(currentDateandTime);
                            firebaseFirestore.collection(email + "Notifiche").add(stringNotifiche)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                DocumentReference documentReference = task.getResult();
                                                documentReference.update("id", task.getResult().getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {


                                                    }
                                                });
                                            }
                                        }
                                    });
                            entratoo = true;
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("kjndjkankjansj","4444");
                        for (int i = 0;i<tokenList.size();i++){
                            if(i== 0){
                                propvaNotifica(tokenList.get(0).replace("[",""),idPost);

                            }else if(i == tokenList.size() -1){
                                propvaNotifica(tokenList.get(i).replace("]",""),idPost);

                            }

                            else{
                                propvaNotifica(tokenList.get(i),idPost);

                            }

                        }
                        Log.d("onfljdsnfl",error.getMessage() + " ciao");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }



    TextView nuoviFollower,nuoveRecensioni,mediaRecensioni,coponUtilizzati,prodttiCaricati;
    TextView perc1,perc2,perc3,perc4,perc5;
    int countFollower;
    int countFollowerAfter;
    int countRec,counRecAfte;
    float sommaMedia,countRecMedia,sommaMediaAfter,sommaRecMediaAfter;
    Float percentMedia;
    int countCouponUtilzzati,couponAfter;
    int coutProdotti,coutProdottiAfter;
    private void setTextView() {
        coutProdotti = 0;
        countFollower = 0;
        countFollowerAfter = 0;
        countCouponUtilzzati = 0;
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
                    Log.d( "fdsfs", String.valueOf(  tot ) );

                    if(tot > 100000){
                        perc1.setVisibility( View.GONE );
                    }else  if(countFollower > countFollowerAfter){
                        int x = Math.abs(100-tot);
                        perc1.setVisibility( View.VISIBLE );

                        perc1.setText( getString( R.string.hairicevutooo ) + " " + x + "% " + getString( R.string.followerinPiu ) );
                        perc1.setTextColor( Color.GREEN);
                    }else{
                        perc1.setVisibility( View.VISIBLE );

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
                                    Log.d( "osdljfsdj", String.valueOf( sommaMedia ) );
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

                    float me =  (sommaMedia /  countRecMedia);
                    float after =  (sommaMediaAfter /  sommaRecMediaAfter);
                    Log.d( "fldsnlfnjsdnflnsd", sommaMediaAfter + " " + sommaRecMediaAfter + " " + counRecAfte );
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
                    Log.d( "fjnsd.kfnsdk", String.valueOf( percent ) );
                    Log.d( "dddddddddddd", sommaMedia+ " " + counRecAfte );

                   if(percent.isInfinite() || percent.isNaN()){
                        perc2.setVisibility( View.GONE );
                    }
                    else if( sommaMedia > counRecAfte){
                        int x = Math.abs(100-tot);
                        perc2.setText( getString( R.string.hairicevutooo ) + " " + x + "% " + getString( R.string.recensioniInPiu ) );
                        perc2.setTextColor( Color.GREEN);
                       perc2.setVisibility( View.VISIBLE );

                   }
                    else{
                       perc2.setVisibility( View.VISIBLE );

                       perc2.setText( getString( R.string.hairicevutooo ) + " " +  (100-tot) +  "% " + getString( R.string.recensioniinMeno ) );
                            perc2.setTextColor(Color.RED);
                        }



                        percentMedia =me / after;

                    Log.d( "fjnsdljnflj", String.valueOf( percentMedia ) );
                    int totMedia = (int) (percentMedia * 100);
                    Log.d( "jlfndsjlnfsdn",me + " " + after + " " + totMedia );
                    if(percentMedia.isNaN() || percentMedia.isInfinite()){
                        perc3.setVisibility( View.GONE );
                    }
                    else if( me> after){
                        int x = Math.abs(100-totMedia);
                        perc3.setText( getString( R.string.Lamediae ) + " " + x + "% " + getString( R.string.rispoettoa ) );
                        perc3.setTextColor( Color.GREEN);
                        perc3.setVisibility( View.VISIBLE );
                    }

                    else
                        {
                            perc3.setVisibility( View.VISIBLE );

                            perc3.setText( getString( R.string.Lamediae2 ) + " " +  (100-totMedia) +  "% " + getString( R.string.rispoettoa )  );
                            perc3.setTextColor(Color.RED);
                        }
                    }
                }

        } );
        firebaseFirestore.collection( email+"couponUtilizzati" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
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
                               countCouponUtilzzati+=1;
                            }else if(days - giorniDash < giorniDash ){
                                couponAfter +=1;
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    coponUtilizzati.setText( String.valueOf( countCouponUtilzzati ) );
                    Float percent = Float.valueOf( countFollower) / Float.valueOf( couponAfter );
                    int tot = (int) (percent * 100);
                    Log.d( "fjnsd.kfnsdk",countCouponUtilzzati + " " + couponAfter );
                    Log.d( "fdsfs", String.valueOf(  tot ) );

                    if(tot > 100000){
                        perc4.setVisibility( View.GONE );
                    }else  if(countCouponUtilzzati > couponAfter){
                        int x = Math.abs(100-tot);
                        perc4.setVisibility( View.VISIBLE );
                        //usati 22% di coupon in piu
                        perc4.setText( getString( R.string.usati ) + " " + x + "% " + getString( R.string.dicouponinpiu ) );
                        perc4.setTextColor( Color.GREEN);
                    }else{
                        perc4.setVisibility( View.VISIBLE );

                        perc4.setText( getString( R.string.usati ) + " " +  (100-tot) +  "% " + getString( R.string.dicouponinmeno ) );
                        perc4.setTextColor(Color.RED);
                    }
                }
            }
        } );
        firebaseFirestore.collection( email ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
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
                               coutProdotti+=1;
                            }else if(days - giorniDash < giorniDash ){
                                coutProdottiAfter +=1;
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    prodttiCaricati.setText( String.valueOf( coutProdotti ) );
                    Float percent = Float.valueOf( coutProdotti) / Float.valueOf( coutProdottiAfter );
                    int tot = (int) (percent * 100);
                    Log.d( "fjnsd.kfnsdk",coutProdotti + " " + coutProdottiAfter );
                    Log.d( "fdsfs", String.valueOf(  tot ) );

                    if(tot > 100000){
                        perc5.setVisibility( View.GONE );
                    }else  if(coutProdotti > coutProdottiAfter){
                        int x = Math.abs(100-tot);
                        perc5.setVisibility( View.VISIBLE );
                        //usati 22% di coupon in piu
                        perc5.setText( getString( R.string.usati ) + " " + x + "% " + getString( R.string.dicouponinpiu ) );
                        perc5.setTextColor( Color.GREEN);
                    }else{
                        perc5.setVisibility( View.VISIBLE );

                        perc5.setText( getString( R.string.usati ) + " " +  (100-tot) +  "% " + getString( R.string.dicouponinmeno ) );
                        perc5.setTextColor(Color.RED);
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
                        if(percent.isInfinite() || percent.isNaN()){
                            percentualeGrafico.setVisibility( View.GONE );
                        }else{
                            int x = Math.abs(100-per);
                            percentualeGrafico.setText( getString( R.string.hairicevuto ) + " " + x +"% " + getString( R.string.divisiteinpiu )  );
                        }


                    }else{

                        percentualeGrafico.setTextColor( Color.RED );
                        Float percent = Float.valueOf( countVisual) / Float.valueOf( prova );
                        int per = (int) (percent * 100);
                        Log.d( "fsdlflsdkm", String.valueOf( percent ) );
                        if(percent.isInfinite() || percent.isNaN()){
                            percentualeGrafico.setVisibility( View.GONE );
                        }else{

                            percentualeGrafico.setText( getString( R.string.hairicevutooo ) + " " + per +"% " + getString( R.string.divisiteinmeno )  );
                        }


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
        layout_dash = (ConstraintLayout) findViewById( R.id.constraintLayout3 );
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
                }else{
                    nessRec.setVisibility( View.VISIBLE );
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
                        arraySlider.add( new SlideModel( String.valueOf( stringRec.getArrayList().get(i)), null ) );

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
                                    recensioniDi.setText(getString(R.string.recensioneDi) +" " + documentSnapshot1.getString("nome") + " " + " " + documentSnapshot1.getString("cognome"));
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
                    chart.setVisibility( View.VISIBLE );
                    coutRecensioni.setVisibility( View.VISIBLE );
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
                        barDataSet.setColor(Color.parseColor("#EFF0AF"));
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
                else{
                    coutRecensioni.setVisibility( View.GONE );
                    chart.setVisibility( View.GONE );
                }
            }
        });


    }







}