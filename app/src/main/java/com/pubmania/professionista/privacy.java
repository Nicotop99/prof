package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
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
import com.pubmania.professionista.StringAdapter.StringNotifiche;
import com.pubmania.professionista.StringAdapter.StringRecensioni;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class privacy extends AppCompatActivity {
    String email = "oliverio.enicola@gmail.com";
    String idPostt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        setId();
        setPinned();
        setEditPin();
        firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString("email").equals(email)){
                            idPostt = documentSnapshot.getId();
                        }
                    }
                }
            }
        });
        setMenuBasso();
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
    ArrayList<String> tokenList = new ArrayList<>();
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

                if (ContextCompat.checkSelfPermission(privacy.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //ask for authorisation
                    ActivityCompat.requestPermissions( privacy.this, new String[]{Manifest.permission.CAMERA}, 50 );
                }
                else {
                    //start your camera


                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( privacy.this );
// ...Irrelevant code for customizing the buttons and title


                    LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    View viewView = inflater.inflate( R.layout.layout_sca_qrcode, null );

                    dialogBuilder.setView( viewView );

                    codeScannerView = (CodeScannerView) viewView.findViewById( R.id.scanner_view );
                    codeScanner = new CodeScanner( privacy.this, codeScannerView );
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
                                                                                                                                                        AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( privacy.this, R.style.MyDialogThemeeee );
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
                                                                                                                    AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( privacy.this, R.style.MyDialogThemeeee );
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
                                                                                                                                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( privacy.this, R.style.MyDialogThemeeee );
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
                                                                                    AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( privacy.this, R.style.MyDialogThemeeee );
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
                                                                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( privacy.this,R.style.MyDialogThemeeee  );
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
        bottomAppBar.setSelectedItemId(R.id.nullable);


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

    private void setEditPin() {
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences sharedPreferences = getSharedPreferences("privacy",MODE_PRIVATE);
                SharedPreferences.Editor s = sharedPreferences.edit();
                Log.d("ajsdnasdam", String.valueOf(b));
                s.putString("profiloPubblico", String.valueOf(b));
                s.commit();
                DocumentReference documentReference = firebaseFirestore.collection("Professionisti").document(idPostt);
                documentReference.update("profiloPubblico",String.valueOf(b));
            }
        });
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences sharedPreferences = getSharedPreferences("privacy",MODE_PRIVATE);
                SharedPreferences.Editor s = sharedPreferences.edit();
                Log.d("ajsdnasdam", String.valueOf(b));
                s.putString("nascondiPost", String.valueOf(b));
                s.commit();
                DocumentReference documentReference = firebaseFirestore.collection("Professionisti").document(idPostt);
                documentReference.update("nascondiPost",String.valueOf(b));
            }
        });
        s4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences sharedPreferences = getSharedPreferences("privacy",MODE_PRIVATE);
                SharedPreferences.Editor s = sharedPreferences.edit();
                Log.d("ajsdnasdam", String.valueOf(b));
                s.putString("nascondiFollower", String.valueOf(b));
                s.commit();
                DocumentReference documentReference = firebaseFirestore.collection("Professionisti").document(idPostt);
                documentReference.update("nascondiFollower",String.valueOf(b));
            }
        });

        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences sharedPreferences = getSharedPreferences("privacy",MODE_PRIVATE);
                SharedPreferences.Editor s = sharedPreferences.edit();
                Log.d("ajsdnasdam", String.valueOf(b));
                s.putString("nascondiCoupon", String.valueOf(b));
                s.commit();
                DocumentReference documentReference = firebaseFirestore.collection("Professionisti").document(idPostt);
                documentReference.update("nascondiCoupon",String.valueOf(b));
            }
        });


    }

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private void setPinned() {
        SharedPreferences sharedPreferences = getSharedPreferences("privacy",MODE_PRIVATE);
        if(sharedPreferences.getString("profiloPubblico","null").equals("null")){
            firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            if(documentSnapshot.getString("email").equals(email)){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("profiloPubblico",documentSnapshot.getString("profiloPubblico"));
                                editor.putString("nascondiPost",documentSnapshot.getString("nascondiPost"));
                                editor.putString("nascondiFollower",documentSnapshot.getString("nascondiFollower"));
                                editor.putString("nascondiCoupon",documentSnapshot.getString("nascondiCoupon"));
                                if(!documentSnapshot.getString("profiloPubblico").equals("true")){
                                    s1.setChecked(true);
                                }else{
                                    s1.setChecked(false);
                                }
                                if(!documentSnapshot.getString("nascondiPost").equals("true")){
                                    s2.setChecked(false);
                                }else{
                                    s2.setChecked(true);
                                }
                                if(!documentSnapshot.getString("nascondiFollower").equals("true")){
                                    s3.setChecked(false);
                                }else{
                                    s3.setChecked(true);
                                }
                                if(!documentSnapshot.getString("nascondiCoupon").equals("true")){
                                    s4.setChecked(false);
                                }else{
                                    s4.setChecked(true);
                                }


                            }
                        }
                    }
                }
            });
        }else{


            if(sharedPreferences.getString("profiloPubblico","null").equals("true")){
                s1.setChecked(true);
            }
            else{
                s1.setChecked(false);
            }
            if(!sharedPreferences.getString("nascondiPost","null").equals("false")){
                s2.setChecked(true);
            }else{
                s2.setChecked(false);
            }
            if(!sharedPreferences.getString("nascondiFollower","null").equals("false")){
                s3.setChecked(true);
            }else{
                s3.setChecked(false);
            }
            if(!sharedPreferences.getString("nascondiCoupon","null").equals("false")){
                s4.setChecked(true);
            }else{
                s4.setChecked(false);
            }
        }
    }

    Switch s1,s2,s3,s4;
    private void setId() {
        s1 = (Switch) findViewById(R.id.switch6);
        s2 = (Switch) findViewById(R.id.switch8);
        s3 = (Switch) findViewById(R.id.switch9);
        s4 = (Switch) findViewById(R.id.switch10);
    }
}