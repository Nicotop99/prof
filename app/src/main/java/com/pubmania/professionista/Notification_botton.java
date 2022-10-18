package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;
import com.pubmania.professionista.Adapter.Notifiche;
import com.pubmania.professionista.StringAdapter.StringNotifiche;
import com.pubmania.professionista.StringAdapter.StringRec;
import com.pubmania.professionista.StringAdapter.StringRecensioni;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Notification_botton extends AppCompatActivity {
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_botton);
        email = "oliverio.enicola@gmail.com";

        setListNotification();
        setMenuBasso();

    }

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Notifiche notifiche;
    ArrayList<StringNotifiche> arrayList = new ArrayList<>();
    ListView listNotifiche;
    private void setListNotification() {
        Log.d("kfjndkjnf",email+"Notifiche");
        Log.d("kfjndkjnf","oliverio.enicola@gmail.comNotifiche");
        listNotifiche = (ListView) findViewById(R.id.list_notification);
        firebaseFirestore.collection(email+"Notifiche")
                .orderBy("ora", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("jkndkjasnjd", String.valueOf(queryDocumentSnapshots.size()));
                        if(queryDocumentSnapshots.size() > 0){
                            Log.d("jkndkjasnjd","ccccccc");

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : list){
                                StringNotifiche stringNotifiche = documentSnapshot.toObject(StringNotifiche.class);
                                arrayList.add(stringNotifiche);
                                notifiche = new Notifiche(Notification_botton.this,arrayList,email);
                                listNotifiche.setAdapter(notifiche);
                                listNotifiche.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        StringNotifiche stringNotifiche1 = arrayList.get(i);
                                        if(stringNotifiche1.getCategoria().equals("Recensione"))
                                        {
                                            firebaseFirestore.collection(email+"Rec").document(stringNotifiche1.getIdPost()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    if(!value.getString("titolo").equals("") && !value.getString("desc").equals("")) {
                                                        clickItem(Notification_botton.this,stringNotifiche1.getIdPost());
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    }
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("jnffkjsanf", String.valueOf(task.getResult().size()));
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
    private void clickItem(Notification_botton view, String idPost) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( Notification_botton.this, R.style.MyDialogThemedee );
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View viewView = inflater.inflate( R.layout.click_rec_pub,null);
        dialogBuilder.setView( viewView );
        AlertDialog alertDialogg = dialogBuilder.create();
        alertDialogg.show();
        setidListView(viewView);
        arraySlider = new ArrayList<>();
        firebaseFirestore.collection(email+"Rec").document(idPost).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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
                                    recensioniDi.setText(getString(R.string.recensioneDi) + " " +  documentSnapshot1.getString("nome") + " " + " " + documentSnapshot1.getString("cognome"));
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
    String tipo,prezzo,prodotti,tokenn,urlFotoProfilo;
    private void setMenuBasso() {
        scanQR = (FloatingActionButton) findViewById( R.id.floatBotton );
        scanQR.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size = 0;
                documentSnapshott = null;

                if (ContextCompat.checkSelfPermission(Notification_botton.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //ask for authorisation
                    ActivityCompat.requestPermissions( Notification_botton.this, new String[]{Manifest.permission.CAMERA}, 50 );
                }
                else {
                    //start your camera


                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( Notification_botton.this );
// ...Irrelevant code for customizing the buttons and title


                    LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    View viewView = inflater.inflate( R.layout.layout_sca_qrcode, null );

                    dialogBuilder.setView( viewView );

                    codeScannerView = (CodeScannerView) viewView.findViewById( R.id.scanner_view );
                    codeScanner = new CodeScanner( Notification_botton.this, codeScannerView );
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
                                                                                                                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Notification_botton.this, R.style.MyDialogThemeeee );
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
                                                                                        AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Notification_botton.this, R.style.MyDialogThemeeee );
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
                                                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Notification_botton.this, R.style.MyDialogThemeeee );
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
                                                            AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Notification_botton.this,R.style.MyDialogThemeeee  );
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

        bottomAppBar.setSelectedItemId(R.id.HomeBotton);
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.home_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);
        bottomAppBar.getMenu().getItem(0).setIcon(wrappedDrawable);
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
}