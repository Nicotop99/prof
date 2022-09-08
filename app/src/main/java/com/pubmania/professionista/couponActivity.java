package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pubmania.professionista.Adapter.AdapterSearchCoupon;
import com.pubmania.professionista.Adapter.array_list_coupon;
import com.pubmania.professionista.StringAdapter.ArrayProdotto;
import com.pubmania.professionista.StringAdapter.StringCoupon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class couponActivity extends AppCompatActivity {

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.layout_coupon );
        email = "oliverio.enicola@gmail.com";
        setcreaNuovoCoupon();
        getToken();
        generaListView();
        setMenuBasso();
        openMenuDestra();
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
                listView.setAdapter( array_list_coupon );
                piuusato.setImageResource( R.drawable.back_filtro_select );
                menorecente.setImageResource( R.drawable.back_filtro_non_select );
                menousato.setImageResource( R.drawable.back_filtro_non_select );
                piurecente.setImageResource( R.drawable.back_filtro_non_select );

                firebaseFirestore.collection( email+"Post" )
                        .orderBy( "volteUtilizzate", Query.Direction.DESCENDING ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
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

                                                            if (arrayList.size() > 0) {


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

        menorecente.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prova.clear();
                provaCheck.clear();
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

                                                            if (arrayList.size() > 0) {


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
                listView.setAdapter( array_list_coupon );
                piuusato.setImageResource( R.drawable.back_filtro_non_select );
                menorecente.setImageResource( R.drawable.back_filtro_non_select );
                menousato.setImageResource( R.drawable.back_filtro_select );
                piurecente.setImageResource( R.drawable.back_filtro_non_select );
                firebaseFirestore.collection( email+"Post" )
                        .orderBy( "volteUtilizzate", Query.Direction.ASCENDING ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
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

    ImageButton menuSlider;
    DrawerLayout drawerLayout;
    private void openMenuDestra() {
        menuSlider = (ImageButton) findViewById( R.id.imageButton24 );
        drawerLayout = (DrawerLayout ) findViewById( R.id.drawMene     );

        menuSlider.setOnClickListener( new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer( Gravity.LEFT );
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
        firebaseFirestore.collection( email+"Post" ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
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
    private void setMenuBasso() {
        bottomAppBar = (BottomNavigationView) findViewById( R.id.bottomNavView );
        bottomAppBar.setSelectedItemId(R.id.couponBotton);

        bottomAppBar.setOnItemSelectedListener( new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.HomeBotton:
                        startActivity( new Intent( getApplicationContext(), HomePage.class ) );
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
                }
                return true;
            }
        } );


    }
    AlertDialog alertDialog;
    ImageButton creaCoupon;
    public static Group group;
    ArrayList<ArrayProdotto> arrayList;

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
                alertDialog = dialogBuilder.create();
                dialogBuilder.setOnKeyListener( new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {



                        Log.d( "kfmdlskmf",",f,f" );
                        return false;
                    }
                } );
                alertDialog.show();
                alertDialog.setOnKeyListener( new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        if (i == KeyEvent.KEYCODE_BACK
                                && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            // TODO do the "back pressed" work here

                            if(group.getVisibility() == View.VISIBLE) {
                                group.setVisibility( View.GONE );
                            }else{
                                alertDialog.dismiss();
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
                                            alertDialog.dismiss();
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