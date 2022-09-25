package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.Result;
import com.pubmania.professionista.Adapter.AdapterSearchCoupon;
import com.pubmania.professionista.Adapter.array_list_coupon;
import com.pubmania.professionista.StringAdapter.ArrayProdotto;
import com.pubmania.professionista.StringAdapter.StringCoupon;
import com.pubmania.professionista.StringAdapter.StringRecensioni;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class couponActivity extends AppCompatActivity {

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_coupon );
        email = "oliverio.enicola@gmail.com";
        setcreaNuovoCoupon();
        getToken();
        generaListView();
        setMenuBasso();

        filtri();
    }
    ImageButton piuusato,menousato,piurecente,menorecente;
    ArrayList<Integer>prova = new ArrayList<>();
    ArrayList<Integer>provaCheck = new ArrayList<>();
    String token;
    void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

                        // Log and toast
                        Log.d("fndlsjfl",token);


                    }
                });
    }
    private void filtri() {






        piuusato = (ImageButton) findViewById( R.id.imageButton25 );
        piurecente = (ImageButton) findViewById( R.id.imageButton26 );
        menousato = (ImageButton) findViewById( R.id.imageButton27 );
        menorecente = (ImageButton) findViewById( R.id.imageButton28 );
        piuusato.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.clear();
                prova.clear();
                provaCheck.clear();
                array_list_coupon = null;
                arrayListCoupon.clear();
                listView.setAdapter( array_list_coupon );
                piuusato.setImageResource( R.drawable.back_filtro_select );
                menorecente.setImageResource( R.drawable.back_filtro_non_select );
                menousato.setImageResource( R.drawable.back_filtro_non_select );
                piurecente.setImageResource( R.drawable.back_filtro_non_select );

                firebaseFirestore.collection( email+"Post" )
                        .orderBy( "quanteVolte", Query.Direction.DESCENDING ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots != null) {
                            List<DocumentSnapshot> l = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : l) {
                                StringCoupon stringCoupon = documentSnapshot.toObject( StringCoupon.class );
                                if (stringCoupon.getCategoria().equals( "Coupon" )) {

                                    Log.d( "kfmslfsd", stringCoupon.getPrezzo() );
                                    arrayListCoupon.add( stringCoupon );
                                    array_list_coupon = new array_list_coupon( couponActivity.this, arrayListCoupon );


                                    listView.setAdapter( array_list_coupon );
                                }
                            }
                        }
                    }
                } );


            }
        } );

        piurecente.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prova.clear();
                provaCheck.clear();
                array_list_coupon = null;
                arrayListCoupon.clear();

                listView.setAdapter( array_list_coupon );
                Log.d( "fmdskfmosdk","pd,sòk,òds" );
                arrayList.clear();
                piuusato.setImageResource( R.drawable.back_filtro_non_select );
                menorecente.setImageResource( R.drawable.back_filtro_non_select );
                menousato.setImageResource( R.drawable.back_filtro_non_select );
                piurecente.setImageResource( R.drawable.back_filtro_select );
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");


                firebaseFirestore.collection( email+"Post" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null) {
                            List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                StringCoupon stringCoupon = documentSnapshot.toObject( StringCoupon.class );
                                if (stringCoupon.getCategoria().equals( "Coupon" )) {


                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );
                                        String currentDateandTime = sdf.format( new Date() );
                                        Date date1 = simpleDateFormat.parse( currentDateandTime );

                                        Date date2 = simpleDateFormat.parse( stringCoupon.getOra() );

                                        int different = (int) (date1.getTime() - date2.getTime());
                                        prova.add( different );
                                        provaCheck.add( different );
                                        Log.d( "mdlfsld", String.valueOf( prova.size() ) );


                                        Collections.sort( prova );


                                        //chi lo ha piu basso è piu recente


                                    } catch (ParseException e) {
                                        e.printStackTrace();

                                    }


                                }
                            }
                            Collections.sort( provaCheck );

                            for (int i = 0; i < prova.size() ; i++) {
                                int finalI = i;
                                Log.d( "kfmsdklmf", String.valueOf( i ) + " " +prova.size() );
                                int finalI1 = i;

                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                            for (DocumentSnapshot doc : list) {
                                                StringCoupon stringCoupon1 = doc.toObject( StringCoupon.class );
                                                if (stringCoupon1.getCategoria().equals( "Coupon" )) {
                                                    SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );
                                                    String currentDateandTime = sdf.format( new Date() );

                                                    Date date1 = null;
                                                    try {
                                                        date1 = simpleDateFormat.parse( currentDateandTime );
                                                        Date date2 = simpleDateFormat.parse( stringCoupon1.getOra() );

                                                        int different = (int) (date1.getTime() - date2.getTime());
                                                        Log.d( "fkmdskfm","prmo" );

                                                        if (provaCheck.size() > 0) {
                                                            Log.d( "fkmdskfm","2" );
                                                            Log.d( "kfmdskmfks",different + " " + prova.get( finalI ) );
                                                            if (different == prova.get( finalI )) {
                                                                Log.d( "fkmdskfm","3" );

                                                                provaCheck.remove( 0 );
                                                                arrayListCoupon.add( stringCoupon1 );
                                                            }

                                                        } else {
                                                            Log.d( "fkmdskfm","4" );


                                                            if (arrayListCoupon.size() > 0) {

                                                                Log.d( "fkmdskfm","5" );

                                                                array_list_coupon = new array_list_coupon( couponActivity.this, arrayListCoupon );


                                                                listView.setAdapter( array_list_coupon );
                                                            }
                                                        }



                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }


                                                }
                                            }

                            }

                        }
                        else{

                        }
                    }

                } );



            }
        } );

        menorecente.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prova.clear();
                provaCheck.clear();
                arrayListCoupon.clear();

                array_list_coupon = null;
                listView.setAdapter( array_list_coupon );
                Log.d( "fmdskfmosdk","pd,sòk,òds" );
                arrayList.clear();
                piuusato.setImageResource( R.drawable.back_filtro_non_select );
                menorecente.setImageResource( R.drawable.back_filtro_select );
                menousato.setImageResource( R.drawable.back_filtro_non_select );
                piurecente.setImageResource( R.drawable.back_filtro_non_select );
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");


                firebaseFirestore.collection( email+"Post" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null) {
                            List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                StringCoupon stringCoupon = documentSnapshot.toObject( StringCoupon.class );
                                if (stringCoupon.getCategoria().equals( "Coupon" )) {


                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault() );
                                        String currentDateandTime = sdf.format( new Date() );
                                        Date date1 = simpleDateFormat.parse( currentDateandTime );

                                        Date date2 = simpleDateFormat.parse( stringCoupon.getOra() );

                                        int different = (int) (date1.getTime() - date2.getTime());
                                        prova.add( different );
                                        provaCheck.add( different );
                                        Log.d( "mdlfsld", String.valueOf( prova.size() ) );




                                        //chi lo ha piu basso è piu recente


                                    } catch (ParseException e) {
                                        e.printStackTrace();

                                    }


                                }
                            }
                            Collections.sort(provaCheck, Collections.reverseOrder());
                            Collections.sort(prova, Collections.reverseOrder());
                            for (int i = 0; i < prova.size() ; i++) {
                                int finalI = i;
                                Log.d( "kfmsdklmf", String.valueOf( i ) + " " +prova.size() );
                                int finalI1 = i;
                                firebaseFirestore.collection( email + "Post" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if (queryDocumentSnapshots != null) {
                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                            for (DocumentSnapshot doc : list) {
                                                StringCoupon stringCoupon1 = doc.toObject( StringCoupon.class );
                                                if (stringCoupon1.getCategoria().equals( "Coupon" )) {
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
                                                                arrayListCoupon.add( stringCoupon1 );
                                                            }

                                                        } else {

                                                            if (arrayListCoupon.size() > 0) {


                                                                array_list_coupon = new array_list_coupon( couponActivity.this, arrayListCoupon );


                                                                listView.setAdapter( array_list_coupon );
                                                            }
                                                        }



                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }


                                                }
                                            }
                                        }
                                    }
                                } );
                            }

                        }
                        else{

                        }
                    }

                } );



            }
        } );






        menousato.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.clear();
                prova.clear();
                array_list_coupon = null;
                arrayListCoupon.clear();

                listView.setAdapter( array_list_coupon );
                piuusato.setImageResource( R.drawable.back_filtro_non_select );
                menorecente.setImageResource( R.drawable.back_filtro_non_select );
                menousato.setImageResource( R.drawable.back_filtro_select );
                piurecente.setImageResource( R.drawable.back_filtro_non_select );
                firebaseFirestore.collection( email+"Post" )
                        .orderBy( "quanteVolte", Query.Direction.ASCENDING ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null){
                            List<DocumentSnapshot> l = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : l){

                                StringCoupon stringCoupon = documentSnapshot.toObject( StringCoupon.class );
                                if(stringCoupon.getCategoria().equals( "Coupon" )) {
                                    arrayListCoupon.add( stringCoupon );
                                    array_list_coupon = new array_list_coupon( couponActivity.this, arrayListCoupon );

                                    listView.setAdapter( array_list_coupon );
                                }
                            }
                        }
                    }
                } );
            }
        } );



    }


    ListView listView;
    TextView text1,text2;
    public static ArrayList<StringCoupon>arrayListCoupon;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static array_list_coupon array_list_coupon;
    private void generaListView() {
        arrayListCoupon = new ArrayList<>();
        listView = (ListView) findViewById( R.id.listHomeCoupon );
        text1 = (TextView) findViewById( R.id.textView47 );
        text2 = (TextView) findViewById( R.id.textView48 );
        firebaseFirestore.collection( email+"Post" )
                .orderBy( "quanteVolte", Query.Direction.DESCENDING ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    text1.setVisibility(View.GONE);
                    text2.setVisibility( View.GONE );
                    List<DocumentSnapshot> listDocument = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot doc : listDocument) {


                        StringCoupon stringCoupon = doc.toObject( StringCoupon.class );
                        if(stringCoupon.getCategoria().equals( "Coupon" )) {
                            arrayListCoupon.add( stringCoupon );
                            array_list_coupon = new array_list_coupon( couponActivity.this, arrayListCoupon );
                            listView.setAdapter( array_list_coupon );
                        }

                    }

                }
            }
        } );


    }

    BottomNavigationView bottomAppBar;
    FloatingActionButton scanQR;
    AlertDialog alertDialog;
    CodeScanner codeScanner;
    int countI,usate;
    int quanteVolte;
    DocumentSnapshot documentSnapshott;
    boolean entrato = false;
    String nomePub;
    boolean exist = false;
    boolean exist2 = false;
    int size = 0;
    CodeScannerView codeScannerView;
    String tipo,prezzo,prodotti,tokenn;
    String urlFotoProfilo;
    private void setMenuBasso() {
        scanQR = (FloatingActionButton) findViewById( R.id.floatBotton );
        scanQR.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size = 0;
                documentSnapshott = null;

                if (ContextCompat.checkSelfPermission(couponActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //ask for authorisation
                    ActivityCompat.requestPermissions( couponActivity.this, new String[]{Manifest.permission.CAMERA}, 50 );
                }
                else {
                    //start your camera


                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( couponActivity.this );
// ...Irrelevant code for customizing the buttons and title


                    LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    View viewView = inflater.inflate( R.layout.layout_sca_qrcode, null );

                    dialogBuilder.setView( viewView );

                    codeScannerView = (CodeScannerView) viewView.findViewById( R.id.scanner_view );
                    codeScanner = new CodeScanner( couponActivity.this, codeScannerView );
                    codeScanner.startPreview();


                    codeScanner.setDecodeCallback( new DecodeCallback() {
                        @Override
                        public void onDecoded(@NonNull Result result) {
                            Log.d( "òfksdòfk",result.getText() );
                            usate = 1;
                            entrato  = false;
                            String[] separated = result.getText().split(":");
                            Log.d( "omsodfsdm",separated[0] );//emailcliente
                            Log.d( "omsodfsdm",separated[1] );//idPost
                            String idPost = separated[1];
                            prodotti = separated[6];
                            tipo = separated[3];
                            prezzo = separated[4];
                            Log.d( "kmflsdmf",separated[2] );
                            if(!separated[2].equals( "Sempre" )) {
                                quanteVolte = Integer.parseInt( separated[2] );
                                Log.d( "omfodsfm", String.valueOf( quanteVolte ) );

                            }
                            String emailCliente = separated[0];
                            tokenn = separated[7] + ":" + separated[8];
                            Log.d( "omfodsfm",idPost );
                            countI = 1;
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
                                                        firebaseFirestore.collection( emailCliente+"Coupon" ).get().
                                                                addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

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

                                                                                                if(task != null){
                                                                                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){

                                                                                                        if(documentSnapshot.getString( "emailPub" ).equals( email )){
                                                                                                            exist = true;
                                                                                                            Log.d("oijflsdjfl","kkk");
                                                                                                        }

                                                                                                    }
                                                                                                    if(exist == false){
                                                                                                        Log.d("mflkfskfmksd","uno");
                                                                                                        StringRecensioni stringRecensioni = new StringRecensioni();
                                                                                                        stringRecensioni.setEmailCliente( emailCliente );
                                                                                                        stringRecensioni.setEmailPub( email );
                                                                                                        stringRecensioni.setNomeLocale( nomePub );
                                                                                                        stringRecensioni.setToken(tokenn);
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
                                                                                                                                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( couponActivity.this, R.style.MyDialogThemeeee );
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
                                                                                                                                        } );






                                                                                                                            }
                                                                                                                        } );
                                                                                                                    }
                                                                                                                } );
                                                                                                    }
                                                                                                    else{
                                                                                                        AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( couponActivity.this, R.style.MyDialogThemeeee );
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
                                                                                                }
                                                                                                else{
                                                                                                    Log.d("mfldsfksd","okfdslmf");
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
                                                                } );













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
                                                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( couponActivity.this, R.style.MyDialogThemeeee );
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

                                                            //coupon utilizzato
                                                            AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( couponActivity.this,R.style.MyDialogThemeeee  );
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
                                    }
                                    else{
                                    }
                                }
                            } );
                        }
                    } );

                    alertDialog = dialogBuilder.create();
                    alertDialog.show();
                }
            }
        } );
        bottomAppBar = (BottomNavigationView) findViewById( R.id.bottomNavView );
        bottomAppBar.findViewById( R.id.nullable ).setClickable( false );

        bottomAppBar.setSelectedItemId(R.id.couponBotton);
        Menu menu = bottomAppBar.getMenu();
        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task != null){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString( "email" ).equals( email )){
                            menu.findItem(R.id.profil_page).setTitle( documentSnapshot.getString( "nome" ) );
                            nomePub = documentSnapshot.getString( "nomeLocale" );
                            urlFotoProfilo = documentSnapshot.getString( "fotoProfilo" );
                        }
                    }
                }
            }
        } );
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
    AlertDialog alertDialogg;
    ImageButton creaCoupon;
    public static Group group;
    ArrayList<ArrayProdotto> arrayList = new ArrayList<>();

    public static TextInputEditText t_search;
    public static Spinner spinnerProdotto;

    private void setcreaNuovoCoupon() {
        creaCoupon = (ImageButton) findViewById( R.id.imageButton23 );
        creaCoupon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( couponActivity.this, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title

                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View viewView = inflater.inflate( R.layout.crea_coupon_alert_dialog, null );

                dialogBuilder.setView( viewView );
                alertDialogg = dialogBuilder.create();
                dialogBuilder.setOnKeyListener( new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {



                        Log.d( "kfmdlskmf",",f,f" );
                        return false;
                    }
                } );
                alertDialogg.show();
                alertDialogg.setOnKeyListener( new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        if (i == KeyEvent.KEYCODE_BACK
                                && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            // TODO do the "back pressed" work here

                            if(group.getVisibility() == View.VISIBLE) {
                                group.setVisibility( View.GONE );
                            }else{
                                alertDialogg.dismiss();
                            }
                            return true;
                        }
                        return false;
                    }
                } );

                //inizio  prog dialog
                TextInputEditText t_titolo = (TextInputEditText) viewView.findViewById( R.id.t_titolocoupon );
                TextInputLayout l_titolo = (TextInputLayout) viewView.findViewById( R.id.l_titolocoupo );
                Spinner spinnerTipoSconto = (Spinner) viewView.findViewById( R.id.spinner2 );
                TextInputLayout l_prezzo = (TextInputLayout) viewView.findViewById( R.id.l_prezzopercentuale );
                TextInputEditText t_prezzo = (TextInputEditText) viewView.findViewById( R.id.t_prezzopercentuale );

                Spinner spinnerQuanteVolte = (Spinner) viewView.findViewById( R.id.spinner3);
                Spinner spinnerChi = (Spinner) viewView.findViewById( R.id.spinner4 );
                spinnerProdotto = (Spinner) viewView.findViewById( R.id.spinner5 );
                TextView textPrezzoorPer = (TextView) viewView.findViewById( R.id.textView52 );
                ImageButton crea = (ImageButton) viewView.findViewById( R.id.imageButton29 );
                group = (Group) viewView.findViewById( R.id.groupCreaCopuon );
                ImageView backList = (ImageView) viewView.findViewById( R.id.imageView29 );
                t_search = (TextInputEditText) viewView.findViewById( R.id.t_prodotto );
                TextInputLayout l_search = (TextInputLayout) viewView.findViewById( R.id.l_Prodoto );
                ListView listRicercaProdotti = (ListView) viewView.findViewById( R.id.listProdottiCopuoin );
                listRicercaProdotti.setFocusable( false );
                listRicercaProdotti.setFocusableInTouchMode( false );
                crea.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String titolo = t_titolo.getText().toString();
                        String tipoSconto = spinnerTipoSconto.getSelectedItem().toString();
                        String prezzo = t_prezzo.getText().toString();
                        String quanteVolte = spinnerQuanteVolte.getSelectedItem().toString();
                        String chi = spinnerChi.getSelectedItem().toString();
                        String qualeProdotto = spinnerProdotto.getSelectedItem().toString();
                        if(titolo.isEmpty()){
                            l_titolo.setError( getString( R.string.titolononinserito ) );
                        }else if(prezzo.isEmpty()){
                            l_prezzo.setError( getString( R.string.questocampononpuoesserevuoto ) );
                        }else if(tipoSconto.equals( getString( R.string.scontoApplicato ) )){

                            Toast.makeText( getApplicationContext(),getString( R.string.tipodiscontononinserito ), Toast.LENGTH_LONG ).show();

                        }else if(chi.equals(getString( R.string.chipuousarlo ) )){
                            Toast.makeText( getApplicationContext(),getString( R.string.chipuoutilizzarlo ), Toast.LENGTH_LONG ).show();
                        }

                        else if(quanteVolte.equals( R.string.quanteVoltepuoessereusato )){

                            Toast.makeText( getApplicationContext(),getString( R.string.quantevoltepuoessereinseritononinserit ), Toast.LENGTH_LONG ).show();

                        }else{
                            //tutto apposto
                            Calendar cal = Calendar.getInstance();
                            StringCoupon stringCoupon = new StringCoupon();
                            stringCoupon.setChi( chi );
                            stringCoupon.setEmail( email );
                            stringCoupon.setToken(token);
                            stringCoupon.setGiorno(  String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ));
                            stringCoupon.setMese( String.valueOf( cal.get(Calendar.MONTH) ) );
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());
                            stringCoupon.setOra( currentDateandTime);
                            stringCoupon.setPrezzo( prezzo );
                            stringCoupon.setQuanteVolte( quanteVolte );
                            stringCoupon.setVolteUtilizzate( "0" );
                            stringCoupon.setTipo( tipoSconto );
                            stringCoupon.setTitolo( titolo );
                            stringCoupon.setQualeProdotto( qualeProdotto );
                            stringCoupon.setCategoria( "Coupon" );
                            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                            firebaseFirestore.collection( email + "Post" ).add( stringCoupon ).addOnCompleteListener( new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    DocumentReference documentReference = firebaseFirestore.collection( email + "Post"  ).document(task.getResult().getId());
                                    documentReference.update( "id", task.getResult().getId() ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            arrayListCoupon.add( stringCoupon );
                                            array_list_coupon = new array_list_coupon( couponActivity.this,arrayListCoupon );
                                            listView.setAdapter( array_list_coupon );
                                            alertDialogg.dismiss();
                                        }
                                    } );
                                }
                            } );
                        }
                    }
                } );


                t_titolo.addTextChangedListener( new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if(!charSequence.toString().isEmpty()){
                            l_titolo.setError( null );
                        }
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                } );
                t_prezzo.addTextChangedListener( new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if(!charSequence.toString().isEmpty()){
                            l_prezzo.setError( null );
                        }
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                } );

                spinnerTipoSconto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        // your code here
                        if(spinnerTipoSconto.getItemAtPosition( position ).equals( getString( R.string.prezzo ) )){
                            textPrezzoorPer.setText( getText( R.string.prezzo ) );
                            t_prezzo.setHint( getText( R.string.prezzo ) );
                        }else if (spinnerTipoSconto.getItemAtPosition( position ).equals( getString( R.string.percentiale ) )){
                            textPrezzoorPer.setText( getText( R.string.percentiale ) );
                            t_prezzo.setHint( getText( R.string.percentiale ) );
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });
                spinnerProdotto.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if(spinnerProdotto.getItemAtPosition( i ).equals( getString( R.string.selezionasingolo ) )){
                            group.setVisibility( View.VISIBLE );
                        }else{
                            group.setVisibility( View.GONE );
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                backList.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        t_search.setText( null );
                        Log.d( "okmdls","kmdkfs" );
                        group.setVisibility( View.GONE );
                        InputMethodManager imm = (InputMethodManager) getSystemService( Context.
                                INPUT_METHOD_SERVICE );
                        imm.hideSoftInputFromWindow( t_search.getWindowToken(), 0 );
                    }
                } );

                t_search.addTextChangedListener( new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            arrayList = new ArrayList<>();
                            Log.d( "fkldslfq","dfsdfsdf" );

                            firebaseFirestore.collection( email ).get()


                                    .addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            if (!queryDocumentSnapshots.isEmpty()) {
                                                // if the snapshot is not empty we are hiding
                                                // our progress bar and adding our data in a list.
                                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                for (DocumentSnapshot d : list) {
                                                    Log.d( "fkldslfq", d.getId() );


                                                    if (d.getId().contains( charSequence.toString() ) == true) {
                                                        ArrayProdotto dataModal = d.toObject( ArrayProdotto.class );
                                                        // after getting data from Firebase we are
                                                        // storing that data in our array list
                                                        arrayList.add( dataModal );
                                                    } else {

                                                    }


                                                }
                                                // after that we are passing our array list to our adapter class.
                                                AdapterSearchCoupon adapter = new AdapterSearchCoupon( couponActivity.this, arrayList );

                                                // after passing this array list to our adapter
                                                // class we are setting our adapter to our list view.
                                                listRicercaProdotti.setAdapter( adapter );
                                            } else {
                                                // if the snapshot is empty we are displaying a toast message.

                                            }
                                        }
                                    } );


                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                } );


                        //fine prof dialog


            }
        } );
    }

    @Override
    public void onBackPressed() {
        Log.d( "mflkdsmfl","kdksmdfks" );
        super.onBackPressed();
    }
}