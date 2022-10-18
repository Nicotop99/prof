package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;
import com.pubmania.professionista.StringAdapter.StringRecensioni;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class profileMenu extends AppCompatActivity {
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        email = "oliverio.enicola@gmail.com";
        setContentView( R.layout.activity_profile_menu );
        setId();
        setText();
        setClickItem();
        setMenuBasso();
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
    String urlFotoProfilo;
    CodeScannerView codeScannerView;
    String tipo,prezzo,prodotti,tokenn;
    private void setMenuBasso() {
        scanQR = (FloatingActionButton) findViewById( R.id.floatBotton );
        scanQR.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size = 0;
                documentSnapshott = null;

                if (ContextCompat.checkSelfPermission(profileMenu.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //ask for authorisation
                    ActivityCompat.requestPermissions( profileMenu.this, new String[]{Manifest.permission.CAMERA}, 50 );
                }
                else {
                    //start your camera


                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( profileMenu.this );
// ...Irrelevant code for customizing the buttons and title


                    LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    View viewView = inflater.inflate( R.layout.layout_sca_qrcode, null );

                    dialogBuilder.setView( viewView );

                    codeScannerView = (CodeScannerView) viewView.findViewById( R.id.scanner_view );
                    codeScanner = new CodeScanner( profileMenu.this, codeScannerView );
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
                                                                                                                                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( profileMenu.this, R.style.MyDialogThemeeee );
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
                                                                                                        AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( profileMenu.this, R.style.MyDialogThemeeee );
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
                                                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( profileMenu.this, R.style.MyDialogThemeeee );
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
                                                            AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( profileMenu.this,R.style.MyDialogThemeeee  );
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

        bottomAppBar.setSelectedItemId(R.id.profil_page);
        bottomAppBar.findViewById( R.id.nullable ).setClickable( false );
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


    private void setClickItem() {
        l_nome.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( profileMenu.this, R.style.MyDialogThemeeee );
// ...Irrelevant code for customizing the buttons and title


                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View viewView = inflater.inflate( R.layout.change_profile, null );
                dialogBuilderr.setView( viewView );
                AlertDialog alertDialogg = dialogBuilderr.create();
                alertDialogg.show();
                TextView titolo = (TextView) viewView.findViewById( R.id.textView131 );
                titolo.setText( getString( R.string.modificanome ) );
                TextInputEditText textInputEditText = (TextInputEditText) viewView.findViewById( R.id.textEmail );
                TextInputLayout textInputLayout = (TextInputLayout) viewView.findViewById( R.id.textInputEmail );
                textInputLayout.setHint( s_nome );
                ImageView salva = (ImageView) viewView.findViewById( R.id.imageView55 );
                salva.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nome = textInputEditText.getText().toString();
                        if(nome.isEmpty()){
                            Toast.makeText( getApplicationContext(),getString( R.string.inserisciunnome ),Toast.LENGTH_LONG );
                        }else{
                            DocumentReference documentReference1 = firebaseFirestore.collection( "Professionisti" ).document(idProf);
                            documentReference1.update( "nome",nome ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    alertDialogg.dismiss();
                                    t_nome.setText( nome );
                                    Snackbar.make( findViewById( android.R.id.content ),getString( R.string.nomemodificato ),Snackbar.LENGTH_LONG ).show();
                                }
                            } );
                        }
                    }
                } );

            }
        } );
        l_cell.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( profileMenu.this, R.style.MyDialogThemeeee );
// ...Irrelevant code for customizing the buttons and title


                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View viewView = inflater.inflate( R.layout.change_profile, null );
                dialogBuilderr.setView( viewView );
                AlertDialog alertDialogg = dialogBuilderr.create();
                alertDialogg.show();
                TextView titolo = (TextView) viewView.findViewById( R.id.textView131 );
                titolo.setText( getString( R.string.modificacell ) );
                TextInputEditText textInputEditText = (TextInputEditText) viewView.findViewById( R.id.textEmail );
                TextInputLayout textInputLayout = (TextInputLayout) viewView.findViewById( R.id.textInputEmail );
                textInputEditText.setInputType( InputType.TYPE_CLASS_PHONE );

                textInputLayout.setHint( s_cell );
                ImageView salva = (ImageView) viewView.findViewById( R.id.imageView55 );
                salva.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nome = textInputEditText.getText().toString();
                        if(nome.isEmpty()){
                            Toast.makeText( getApplicationContext(),getString( R.string.inseriscicell ),Toast.LENGTH_LONG );
                        }else{
                            DocumentReference documentReference1 = firebaseFirestore.collection( "Professionisti" ).document(idProf);
                            documentReference1.update( "cellulare",nome ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    alertDialogg.dismiss();
                                    t_cell.setText( nome );

                                    Snackbar.make( findViewById( android.R.id.content ),getString( R.string.nomemodificato ),Snackbar.LENGTH_LONG ).show();
                                }
                            } );
                        }
                    }
                } );

            }
        } );
        l_cognome.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( profileMenu.this, R.style.MyDialogThemeeee );
// ...Irrelevant code for customizing the buttons and title


                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View viewView = inflater.inflate( R.layout.change_profile, null );
                dialogBuilderr.setView( viewView );
                AlertDialog alertDialogg = dialogBuilderr.create();
                alertDialogg.show();
                TextView titolo = (TextView) viewView.findViewById( R.id.textView131 );
                titolo.setText( getString( R.string.modificacogn ) );
                TextInputEditText textInputEditText = (TextInputEditText) viewView.findViewById( R.id.textEmail );
                TextInputLayout textInputLayout = (TextInputLayout) viewView.findViewById( R.id.textInputEmail );
                textInputLayout.setHint( s_cognome );
                ImageView salva = (ImageView) viewView.findViewById( R.id.imageView55 );
                salva.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nome = textInputEditText.getText().toString();
                        if(nome.isEmpty()){
                            Toast.makeText( getApplicationContext(),getString( R.string.inseriscicognom ),Toast.LENGTH_LONG );
                        }else{
                            DocumentReference documentReference1 = firebaseFirestore.collection( "Professionisti" ).document(idProf);
                            documentReference1.update( "cognome",nome ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    alertDialogg.dismiss();
                                    t_cognome.setText( nome );
                                    Snackbar.make( findViewById( android.R.id.content ),getString( R.string.nomemodificato ),Snackbar.LENGTH_LONG ).show();
                                }
                            } );
                        }
                    }
                } );

            }
        } );

    }

    TextInputLayout l_nome,l_cognome,l_email,l_password,l_cell;
    TextInputEditText t_nome,t_cognome,t_email,t_pass,t_cell;

    private void setId() {
        l_nome = (TextInputLayout) findViewById( R.id.textInputLayout2 );
        l_cognome = (TextInputLayout) findViewById( R.id.textInputCognomee);
        l_email = (TextInputLayout) findViewById( R.id.textInputEmail);
        l_password = (TextInputLayout) findViewById( R.id.textInputPassword);
        l_cell = (TextInputLayout) findViewById( R.id.textInputCell);
        t_nome = (TextInputEditText) findViewById( R.id.textPass );
        t_cognome = (TextInputEditText) findViewById( R.id.textCognomee);
        t_email = (TextInputEditText) findViewById( R.id.textEmail);
        t_pass = (TextInputEditText) findViewById( R.id.textPassword);
        t_cell = (TextInputEditText) findViewById( R.id.textCelliulare);
    }
    String s_nome,s_cognome,s_cell,idProf;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private void setText() {
        t_pass.setText( "***********" );
        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString( "email" ).equals( email )){
                            idProf = documentSnapshot.getId();
                                t_nome.setText( documentSnapshot.getString( "nome" ) );
                                s_nome = documentSnapshot.getString( "nome" );
                                s_cognome = documentSnapshot.getString( "cognome" );
                                s_cell = documentSnapshot.getString( "cellulare" );
                                t_cognome.setText( documentSnapshot.getString( "cognome" ) );

                                t_email.setText( email);


                                t_cell.setText( documentSnapshot.getString( "cellulare" ) );

                        }
                    }
                }
            }
        } );
    }
}