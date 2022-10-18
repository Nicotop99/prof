package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.Result;
import com.pubmania.professionista.Adapter.Adapter_Profile_bottom;
import com.pubmania.professionista.Adapter.Array_prodottooo;
import com.pubmania.professionista.Adapter.adapter_edit_foto;
import com.pubmania.professionista.Adapter.adapter_list_search_prodotti;
import com.pubmania.professionista.Adapter.try_adapter;
import com.pubmania.professionista.StringAdapter.ArrayPost;
import com.pubmania.professionista.StringAdapter.ArrayProdotto;
import com.pubmania.professionista.StringAdapter.StringNotifiche;
import com.pubmania.professionista.StringAdapter.StringRecensioni;
import com.pubmania.professionista.StringAdapter.StringRegistrazione;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static com.pubmania.professionista.Adapter.Array_prodottooo.adapterr;
import static com.pubmania.professionista.Adapter.adapter_edit_foto.arrayFotoDaEliminare;
import static com.pubmania.professionista.Adapter.adapter_edit_foto.maintitle;

import org.json.JSONException;
import org.json.JSONObject;

public class HomePage extends AppCompatActivity {

    ImageButton menuSlider;
    DrawerLayout drawableContainer;
    ImageSlider imageSlider;
    ConstraintLayout layout;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home_page );
        setBottonSearch();
/*
        SharedPreferences s2 = getSharedPreferences("fotoPinnate",MODE_PRIVATE);
        String[] pin = s2.getString("pin","null").split("ì");
        SharedPreferences.Editor editor = s2.edit();
        editor.putInt("fotoCaricate",0);
        editor.putString("pin","null");
        editor.commit();

 */



        email = "oliverio.enicola@gmail.com";
        setMenuBasso();
        creaArticolo();
        setImageButtonCategoria();
        SharedPreferences sharedPreferences = getSharedPreferences("notifiche",MODE_PRIVATE);
        Log.d("kdjnakjsndjna",sharedPreferences.getString("s1","true"));
        setAutoCLick();


        firebaseFirestore.collection(email).document("birra alla spine").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.d("jsandfjas", String.valueOf(task));
                Log.d("jsandfjas", String.valueOf(task.getResult()));
                Log.d("jsandfjas", String.valueOf(task.getResult().getData()));
            }
        });

        setNewToken();
        setMenuLaterale();

        startService(new Intent(getApplicationContext(),MyFirebaseMessagingService.class));

        imageSlider = (ImageSlider) findViewById( R.id.image_slider );
        menuSlider = (ImageButton) findViewById( R.id.imageButton10 );
        drawableContainer = (DrawerLayout ) findViewById( R.id.drawMene     );
        menuSlider.setOnClickListener( new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                drawableContainer.openDrawer( Gravity.LEFT );
            }
        } );
        setImageSlider();
    }


    private void setNewToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    return;
                }
                Log.d("fndlsjfl",task.getResult());

            }
        });
        SharedPreferences sharedPreferencess = getSharedPreferences("token",MODE_PRIVATE);
        if(sharedPreferencess.getString("token","notEnable").equals("notEnable")){
            Log.d("fndlsjfl","noToken");
            SharedPreferences.Editor s = sharedPreferencess.edit();
            s.putString("token","enable");
            s.commit();
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            // Get new FCM registration token


                            // Log and toast
                            Log.d("fndlsjfl",task.getResult());
                            firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> taskg) {
                                    if(task.isSuccessful()){
                                        Log.d("lmdsadnmam", String.valueOf("ciaoooo"));

                                        for (QueryDocumentSnapshot documentSnapshot : taskg.getResult()){
                                            if(documentSnapshot.getString("email").equals(email)){
                                                Log.d("jnfskdjnfjsd","dddddd");

                                                ArrayList<String> tokenString = (ArrayList<String>) documentSnapshot.get("token");
                                                tokenString.add(task.getResult());
                                                String[] arr = tokenString.toArray( new String[tokenString.size()] );
                                                List<String> listIng = Arrays.asList( arr );
                                                DocumentReference d = firebaseFirestore.collection("Professionisti").document(documentSnapshot.getId());
                                                d.update("token",listIng).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Log.d("jnfskdjnfjsd","complete");
                                                    }
                                                });
                                            }
                                        }
                                    }else{
                                        Log.d("lmdsadnmam", String.valueOf("2222222"));

                                    }
                                }
                            });


                        }
                    });
        }
        else{
            Log.d("fndlsjfl","toskskskksskkss");

        }
    }


    NavigationView navigationView;
    private void setMenuLaterale() {
        navigationView = (NavigationView) findViewById( R.id.menulaterale );
        View header = navigationView.getHeaderView(0);
        TextView text = (TextView) header.findViewById(R.id.textView21);
        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        if(documentSnapshot.getString( "email" ).equals( email )){
                            text.setText(documentSnapshot.getString( "nome" ) + " " + documentSnapshot.getString( "cognome" ));
                        }
                    }
                }
            }
        } );
        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.menu_lat_profilo) {
                    // DO your stuff
                    startActivity( new Intent(getApplicationContext(), profileMenu.class) );

                }else if(id == R.id.menu_lat_impo){
                    startActivity( new Intent(getApplicationContext(),SettingActivity.class) );
                }
                else if(id == R.id.notificheLato){
                    startActivity(new Intent(getApplicationContext(),Notification_botton.class));
                }
                return true;
            }
        } );
    }

    int intl = 0;
    ArrayList<SlideModel> arrayModel = new ArrayList<>();
    ConstraintLayout constraintLayout;
    ConstraintLayout constraintLayoutNewPost;
    private void setImageSlider() {
        constraintLayout = (ConstraintLayout) findViewById(R.id.conPinned);
        constraintLayoutNewPost = (ConstraintLayout) findViewById(R.id.conNessunPost);
        constraintLayoutNewPost.setVisibility(View.GONE);
        constraintLayout.setVisibility(View.GONE);
        imageSlider.setVisibility(View.VISIBLE);
        SharedPreferences s2 = getSharedPreferences("fotoPinnate",MODE_PRIVATE);
        if(!s2.getString("pin","null").equals("null")){
            String[] pinn = s2.getString("pin","null").split("ì");
            for (int i = 0;i< pinn.length;i++){
                arrayModel.add( new SlideModel( String.valueOf( pinn[i] ), null ) );

            }
            imageSlider.setImageList( arrayModel );

        }
        Log.d("jfnsjdknf", String.valueOf(s2.getInt("fotoCaricate",0000)));
        if(arrayModel.size() ==0 && s2.getInt("fotoCaricate",0) >0){
            constraintLayout.setVisibility(View.VISIBLE);
            Log.d("fjondsjfnjs","1111");

            imageSlider.setVisibility(View.GONE);
        }else if(arrayModel.size() == 0 && s2.getInt("fotoCaricate",0) == 0){
            Log.d("fjondsjfnjs","0000");
            constraintLayoutNewPost.setVisibility(View.VISIBLE);

        }


        setConstract();

        setNewPost();
    }



    ImageView creaPrimoPost;
    private void setNewPost() {
        creaPrimoPost = (ImageButton) findViewById( R.id.imageButton39 );
        creaPrimoPost.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType( "image/*" );
                intent.putExtra( Intent.EXTRA_ALLOW_MULTIPLE, true );
                intent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( intent, "Choose Picture" ), 4 );
            }
        } );
    }
    List<SlideModel> slideModels = new ArrayList<>();
    List<Uri> imageListPath = new ArrayList<>();
    ArrayList<String> uriArray = new ArrayList<>();
    Uri filePath;
    ImageSlider imageSliderr;
    Uri uriFotoProfilo;


    GridView gridView;

    ArrayList<ArrayPost> arrayPost;
    Adapter_Profile_bottom adapter_profile_bottom;
    int idGridView;
    private void setConstract() {
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setReload();
            }
        });
    }

    private void setReload() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomePage.this, R.style.MyDialogThemedee );
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View viewView = inflater.inflate( R.layout.grid_pinned,null);
        dialogBuilder.setView( viewView );
        AlertDialog alertDialogg = dialogBuilder.create();
        alertDialogg.show();


        arrayPost = new ArrayList<>();
        gridView = (GridView)  viewView.findViewById( R.id.gridProfilo );
        gridView.setBackgroundColor( Color.TRANSPARENT );
        firebaseFirestore.collection( email+"Post" )

                .orderBy( "pinnato", Query.Direction.DESCENDING )
                .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size() >0){
                            Log.d("djnaskjdnkajnd","primo");
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : list){
                                Log.d("djnaskjdnkajnd","secondo");

                                ArrayPost arr = documentSnapshot.toObject( ArrayPost.class );
                                if(arr.getCategoria().equals( "Post" )) {
                                    Log.d("djnaskjdnkajnd","terxo");

                                    arrayPost.add( arr );
                                    adapter_profile_bottom = new Adapter_Profile_bottom( HomePage.this, arrayPost );
                                    gridView.setAdapter( adapter_profile_bottom );

                                    gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            idGridView = i;
                                            TextView textView = (TextView) view.findViewById( R.id.idPost );
                                            String idPost = textView.getText().toString();

                                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomePage.this, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title

                                            LayoutInflater inflater = getLayoutInflater();
                                            View viewView = inflater.inflate( R.layout.alert_grid_profile, null );

                                            dialogBuilder.setView( viewView );
                                            AlertDialog alertDialog = dialogBuilder.create();
                                            alertDialog.show();

                                            ArrayList<String> uriArr = new ArrayList<>();
                                            ImageSlider imageSlider = (ImageSlider) viewView.findViewById( R.id.slider );
                                            TextView desc = (TextView) viewView.findViewById( R.id.textView69 );
                                            ImageButton close = (ImageButton) viewView.findViewById( R.id.imageButton35 );
                                            ImageButton pinna = (ImageButton) viewView.findViewById( R.id.imageButton34 );
                                            TextView insiedeButton = (TextView) viewView.findViewById( R.id.textView72 ) ;
                                            ImageButton elimina = (ImageButton) viewView.findViewById( R.id.imageButton36 );
                                            Group g2 = (Group) viewView.findViewById( R.id.groupEditPhotoProfile );
                                            Group g1 = (Group) viewView.findViewById( R.id.groupo1);
                                            firebaseFirestore.collection( email+"Post" ).document(idPost).get().addOnSuccessListener( new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    if(documentSnapshot != null){
                                                        ArrayPost arrayPost = documentSnapshot.toObject( ArrayPost.class );
                                                        ArrayList<SlideModel> arrayList = new ArrayList<>();
                                                        for (int i = 0;i<arrayPost.getFoto().size();i++){
                                                            arrayList.add( new SlideModel( String.valueOf( arrayPost.getFoto().get( i ) ),null ) );
                                                            uriArr.add( arrayPost.getFoto().get( i ) );
                                                        }
                                                        imageSlider.setImageList( arrayList );
                                                        if(arrayPost.getPinnato().equals( "no" )){
                                                            insiedeButton.setText( getString( R.string.mettiinevidenza ) );
                                                        }else{
                                                            insiedeButton.setText( getString( R.string.rimuovidallevidenza ) );
                                                        }
                                                        desc.setText( arrayPost.getDescrizione() );
                                                        if(!arrayPost.getDescrizione().isEmpty()){
                                                            desc.setVisibility( View.VISIBLE );
                                                        }




                                                    }
                                                }
                                            } );

                                            close.setOnClickListener( new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    alertDialog.dismiss();
                                                }
                                            } );

                                            elimina.setOnClickListener( new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            switch (which) {
                                                                case DialogInterface.BUTTON_POSITIVE:
                                                                    //Yes button clicked
                                                                    g1.setVisibility( View.GONE );
                                                                    g2.setVisibility( View.VISIBLE );
                                                                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                                                                    SharedPreferences s2 = getSharedPreferences("fotoPinnate",MODE_PRIVATE);
                                                                    String[] pin = s2.getString("pin","null").split("ì");
                                                                    SharedPreferences.Editor editor = s2.edit();
                                                                    editor.putInt("fotoCaricate",s2.getInt("fotoCaricate",0) - 1);
                                                                    Log.d("jdndkajsndas", String.valueOf(s2.getInt("fotoCaricate",000)));
                                                                    if(pin.length > 1) {
                                                                        for (int i = 0; i < pin.length; i++) {
                                                                            if (pin[i].equals(uriArr.get(0))) {
                                                                                String fin = s2.getString("pin", "null").replace(uriArr.get(0) + "ì", "");
                                                                                editor.putString("pin", fin);
                                                                                editor.commit();
                                                                            }
                                                                        }
                                                                    }else{

                                                                        editor.putString("pin", "null");
                                                                        editor.commit();
                                                                    }
                                                                    for (int i = 0;i<uriArr.size();i++){
                                                                        StorageReference storageReference = firebaseStorage.getReferenceFromUrl( uriArr.get( i ) );
                                                                        storageReference.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                uriArr.remove( 0 );
                                                                                if(uriArr.size() == 0){
                                                                                    DocumentReference documentReference = firebaseFirestore.collection( email+"Post" ).document(idPost);
                                                                                    documentReference.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            alertDialog.dismiss();
                                                                                            alertDialogg.dismiss();
                                                                                            setConstract();
                                                                                            setImageSlider();
                                                                                            arrayPost.remove(idGridView);
                                                                                            adapter_profile_bottom.notifyDataSetChanged();

                                                                                        }
                                                                                    } );
                                                                                }else{
                                                                                    Log.d( "kfmldkmf", String.valueOf( uriArr.size() ) );
                                                                                }


                                                                            }
                                                                        } );
                                                                    }







                                                                    break;

                                                                case DialogInterface.BUTTON_NEGATIVE:
                                                                    //No button clicked

                                                                    dialog.dismiss();

                                                                    break;
                                                            }
                                                        }
                                                    };
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
                                                    builder.setMessage(getString( R.string.seisicurodivolereliminarequestopost )).setPositiveButton(getString( R.string.si ), dialogClickListener)
                                                            .setNegativeButton(getString( R.string.no ), dialogClickListener).show();
                                                }
                                            } );

                                            pinna.setOnClickListener( new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if (insiedeButton.getText().toString().equals( getString( R.string.mettiinevidenza ) )) {

                                                        DocumentReference documentReference = firebaseFirestore.collection( email + "Post" ).document( idPost );
                                                        documentReference.update( "pinnato", "si" ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                alertDialog.dismiss();
                                                                alertDialogg.dismiss();
                                                                SharedPreferences s2 = getSharedPreferences("fotoPinnate",MODE_PRIVATE);
                                                                SharedPreferences.Editor editor = s2.edit();
                                                                editor.putString("pin",uriArr.get(0) + "ì");
                                                                editor.commit();
                                                                setReload();
                                                                setImageSlider();
                                                            }
                                                        } );
                                                    }else{
                                                        DocumentReference documentReference = firebaseFirestore.collection( email + "Post" ).document( idPost );
                                                        documentReference.update( "pinnato", "no" ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                alertDialog.dismiss();
                                                                setReload();
                                                                alertDialogg.dismiss();

                                                                setImageSlider();
                                                                SharedPreferences s2 = getSharedPreferences("fotoPinnate",MODE_PRIVATE);
                                                                String[] pin = s2.getString("pin","null").split("ì");
                                                                if(pin.length > 1) {
                                                                    for (int i = 0; i < pin.length; i++) {
                                                                        if (pin[i].equals(uriArr.get(0))) {
                                                                            String fin = s2.getString("pin", "null").replace(uriArr.get(0) + "ì", "");
                                                                            SharedPreferences.Editor editor = s2.edit();
                                                                            editor.putString("pin", fin);
                                                                            editor.commit();
                                                                        }
                                                                    }
                                                                }else{
                                                                    SharedPreferences.Editor editor = s2.edit();
                                                                    editor.putString("pin", "null");
                                                                    editor.commit();
                                                                }

                                                            }
                                                        } );
                                                    }
                                                }
                                            } );




                                        }
                                    } );
                                    int totalHeight = 0;
                                    for (int i = 0; i < gridView.getCount(); i++) {
                                        View listItem = adapter_profile_bottom.getView(i, null, gridView);
                                        listItem.measure(0, 0);
                                        totalHeight += listItem.getMeasuredHeight();
                                    }

                                    ViewGroup.LayoutParams params = gridView.getLayoutParams();
                                    params.height = totalHeight
                                            + (3 * (adapter_profile_bottom.getCount() - 1));
                                    gridView.setLayoutParams(params);
                                    gridView.requestLayout();
                                }

                            }
                        }else{
                            gridView.setAdapter( null );
                        }
                    }
                } );


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
    Uri imageUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/pub-mania.appspot.com/o/WIN_20221012_19_25_21_Pro.jpg?alt=media&token=710c0bd3-3303-45f7-87e8-2bb6635d5c45");
    private void setMenuBasso() {
        scanQR = (FloatingActionButton) findViewById( R.id.floatBotton );
        scanQR.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size = 0;
                documentSnapshott = null;

                if (ContextCompat.checkSelfPermission(HomePage.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //ask for authorisation
                    ActivityCompat.requestPermissions( HomePage.this, new String[]{Manifest.permission.CAMERA}, 50 );
                }
                else {
                    //start your camera


                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomePage.this );
// ...Irrelevant code for customizing the buttons and title


                    LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    View viewView = inflater.inflate( R.layout.layout_sca_qrcode, null );

                    dialogBuilder.setView( viewView );

                    codeScannerView = (CodeScannerView) viewView.findViewById( R.id.scanner_view );
                    codeScanner = new CodeScanner( HomePage.this, codeScannerView );
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
                                                                                                                                                        AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( HomePage.this, R.style.MyDialogThemeeee );
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
                                                                                                                    AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( HomePage.this, R.style.MyDialogThemeeee );
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
                                                                                                                                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( HomePage.this, R.style.MyDialogThemeeee );
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
                                                                    AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( HomePage.this, R.style.MyDialogThemeeee );
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
                                                            AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( HomePage.this,R.style.MyDialogThemeeee  );
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

        bottomAppBar.setSelectedItemId(R.id.HomeBotton);
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.home_icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);
        bottomAppBar.getMenu().getItem(0).setIcon(wrappedDrawable);

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
                                                editor.putString("nomePub", documentSnapshot.getString("nome"));
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
                                urlFotoProfilo = documentSnapshot.getString("fotoProfilo");
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
    String TOPIC,token;
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

    String urlFotoProfilo;
    ImageButton cockatailImageButton;
    ImageButton docliImageButton;
    ImageButton salatiImageButon;
    ImageButton benvandeImageButton;
    TextView textBottonMenu;
    ConstraintLayout l_categorie;
    ConstraintLayout l_cocktail;
    ListView listProdottiCocktail;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    ArrayList<ArrayProdotto> arrayProd = new ArrayList<>();
    TextView titolo;
    String categoriaSelezionata;
    ImageView plusButton;
    TextView vuoto;
    public static String cat;
    private void setImageButtonCategoria() {
        vuoto = (TextView) findViewById( R.id.textView134 );
        titolo = (TextView) findViewById( R.id.textView37 );
        textBottonMenu = (TextView) findViewById( R.id.textView17 );
        l_categorie = (ConstraintLayout) findViewById( R.id.constraintLayout );
        l_cocktail = (ConstraintLayout) findViewById( R.id.constraintLayout2 );
        cockatailImageButton = (ImageButton) findViewById( R.id.imageButton12 );
        docliImageButton = (ImageButton) findViewById( R.id.imageButton11 );
        salatiImageButon = (ImageButton) findViewById( R.id.imageButton13 );
        benvandeImageButton = (ImageButton) findViewById( R.id.imageButton14 );
        listProdottiCocktail = (ListView) findViewById( R.id.list_cockta );

        listProdottiCocktail.setFocusable( false );
        listProdottiCocktail.setFocusableInTouchMode( false );
        cockatailImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat = "Cocktail";
                titolo.setText( getString( R.string.cocktail ) );
                arrayProd = new ArrayList<>();
                arrayProd.clear();
                categoriaSelezionata = getString( R.string.cocktail );
                l_categorie.setVisibility( View.GONE );
                plusButton.setVisibility( View.GONE );
                listProdottiCocktail.setAdapter( null );
                arrayList.clear();

                textBottonMenu.setText( getText( R.string.menuPrincipale ) );
                l_cocktail.setVisibility( View.VISIBLE );
                firebaseFirestore.collection( email ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size() > 0){
                            vuoto.setVisibility( View.GONE );
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                if(d.get( "categoria" ).equals( getString( R.string.cocktail ) )) {

                                    ArrayProdotto dataModal = d.toObject( ArrayProdotto.class );

                                    // after getting data from Firebase we are
                                    // storing that data in our array list
                                    arrayProd.add( dataModal );

                                    Array_prodottooo adapter = new Array_prodottooo( HomePage.this, arrayProd );

                                    // after passing this array list to our adapter
                                    // class we are setting our adapter to our list view.
                                    listProdottiCocktail.setAdapter( adapter );
                                    adapter.notifyDataSetChanged();
                                    listProdottiCocktail.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                            ImageView imageView = view.findViewById( R.id.imageButton24 );
                                            imageView.performClick();

                                        }
                                    } );
                                }
                            }
                            if(arrayProd.size() == 0){
                                vuoto.setText( getString( R.string.nonhaipubblicatococktail ) );
                                vuoto.setVisibility( View.VISIBLE );
                            }
                        }else{
                            vuoto.setText( getString( R.string.nonhaipubblicatococktail ) );
                            vuoto.setVisibility( View.VISIBLE );

                        }
                    }
                } );

            }
        } );

        docliImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat = "Dolci";
                arrayProd.clear();
                arrayList.clear();

                titolo.setText( getString( R.string.dolci ) );
                categoriaSelezionata = getString( R.string.dolci );
                listProdottiCocktail.setAdapter( null );

                arrayProd = new ArrayList<>();
                l_categorie.setVisibility( View.GONE );
                plusButton.setVisibility( View.GONE );

                textBottonMenu.setText( getText( R.string.menuPrincipale ) );
                l_cocktail.setVisibility( View.VISIBLE );
                firebaseFirestore.collection( email ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size() > 0){
                            vuoto.setVisibility( View.GONE );

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                if(d.get( "categoria" ).equals( getString( R.string.dolci ) )) {

                                    ArrayProdotto dataModal = d.toObject( ArrayProdotto.class );

                                    // after getting data from Firebase we are
                                    // storing that data in our array list
                                    arrayProd.add( dataModal );

                                    Array_prodottooo adapter = new Array_prodottooo( HomePage.this, arrayProd );

                                    // after passing this array list to our adapter
                                    // class we are setting our adapter to our list view.
                                    listProdottiCocktail.setAdapter( adapter );
                                    listProdottiCocktail.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            ImageView imageView = view.findViewById( R.id.imageButton24 );
                                            imageView.performClick();
                                        }
                                    } );
                                }
                            }
                            if(arrayProd.size() == 0){
                                vuoto.setText( getString( R.string.nessundocle ) );
                                vuoto.setVisibility( View.VISIBLE );
                            }
                        }else{
                            vuoto.setText( getString( R.string.nessundocle ) );
                            vuoto.setVisibility( View.VISIBLE );

                        }
                    }
                } );

            }
        } );


        salatiImageButon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat = "Salati";
                listProdottiCocktail.setAdapter( null );

                titolo.setText( getString( R.string.salati ) );
                categoriaSelezionata = getString( R.string.salati );
                arrayProd.clear();
                arrayList.clear();

                arrayProd = new ArrayList<>();
                l_categorie.setVisibility( View.GONE );
                plusButton.setVisibility( View.GONE );

                textBottonMenu.setText( getText( R.string.menuPrincipale ) );
                l_cocktail.setVisibility( View.VISIBLE );
                firebaseFirestore.collection( email ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size() > 0){
                            vuoto.setVisibility( View.GONE );

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                if(d.get( "categoria" ).equals( getString( R.string.salati ) )) {

                                    ArrayProdotto dataModal = d.toObject( ArrayProdotto.class );

                                    // after getting data from Firebase we are
                                    // storing that data in our array list
                                    arrayProd.add( dataModal );

                                    Array_prodottooo adapter = new Array_prodottooo( HomePage.this, arrayProd );

                                    // after passing this array list to our adapter
                                    // class we are setting our adapter to our list view.
                                    listProdottiCocktail.setAdapter( adapter );
                                    listProdottiCocktail.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            ImageView imageView = view.findViewById( R.id.imageButton24 );
                                            imageView.performClick();
                                        }
                                    } );
                                }
                            }
                            if(arrayProd.size() == 0){
                                vuoto.setText( getString( R.string.nessunsalato ) );
                                vuoto.setVisibility( View.VISIBLE );
                            }
                        }else{
                            vuoto.setText( getString( R.string.nessunsalato ) );
                            vuoto.setVisibility( View.VISIBLE );

                        }
                    }
                } );

            }
        } );

        benvandeImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.clear();
                cat = "Bevande";
                arrayProd.clear();
                listProdottiCocktail.setAdapter( null );
                titolo.setText( getString( R.string.bevande ) );
                categoriaSelezionata = getString( R.string.bevande );

                arrayProd = new ArrayList<>();
                l_categorie.setVisibility( View.GONE );
                plusButton.setVisibility( View.GONE );
                textBottonMenu.setText( getText( R.string.menuPrincipale ) );
                l_cocktail.setVisibility( View.VISIBLE );
                firebaseFirestore.collection( email ).get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size() > 0){
                            vuoto.setVisibility( View.GONE );
                            Log.d("jnfdjksnfkjsnd", String.valueOf(queryDocumentSnapshots.getDocuments()));
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                if(d.get( "categoria" ).equals( getString( R.string.bevande ) )) {

                                    ArrayProdotto dataModal = d.toObject( ArrayProdotto.class );

                                    // after getting data from Firebase we are
                                    // storing that data in our array list
                                    arrayProd.add( dataModal );

                                    Array_prodottooo adapter = new Array_prodottooo( HomePage.this, arrayProd );

                                    // after passing this array list to our adapter
                                    // class we are setting our adapter to our list view.
                                    listProdottiCocktail.setAdapter( adapter );
                                    listProdottiCocktail.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            ImageView imageView = view.findViewById( R.id.imageButton24 );
                                            imageView.performClick();




                                        }
                                    } );
                                }
                            }
                            if(arrayProd.size() == 0){
                                vuoto.setText( getString( R.string.nessunabevanda ) );
                                vuoto.setVisibility( View.VISIBLE );
                            }
                        }else{
                            Log.d("hdbhksabdkbasd","emnpty");
                            vuoto.setText( getString( R.string.nessunabevanda ) );
                            vuoto.setVisibility( View.VISIBLE );

                        }
                    }
                } ).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("ojfndskjnf", String.valueOf(task.getResult().size()));
                    }
                });

            }
        } );
    }

    ImageButton creaArt;
    ImageSlider image_prdotto_slide;
    int immaginiCaricate = 1;
    private void setAutoCLick() {


        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        if (intent.hasExtra("autoClick")) {
            if(extras.getString( "autoClick" ).equals( "Cocktail" )){
                cockatailImageButton.performClick();
            }else if(extras.getString( "autoClick" ).equals( "Dolci" )){
                docliImageButton.performClick();
            }else if(extras.getString( "autoClick" ).equals( "Bevande" )){
                benvandeImageButton.performClick();
            } else if (extras.getString( "autoClick" ).equals( "Salati" )) {
                salatiImageButon.performClick();
            }
        } else {
            // Do something else
        }



    }
    @Override
    public void onBackPressed() {
        // Prevents the user from clicking the back button and returning to the parent activity
        if(l_search.getVisibility() == View.VISIBLE){
            backList.setVisibility( View.GONE );

            l_search.setVisibility( View.GONE );
            list_ricerca.setVisibility( View.GONE );
            group.setVisibility( View.VISIBLE );

            sfondoList.setVisibility( View.GONE );
        }else if(l_cocktail.getVisibility() == View.VISIBLE){
            l_cocktail.setVisibility( View.GONE );
            group.setVisibility( View.VISIBLE );
            l_categorie.setVisibility( View.VISIBLE );
            textBottonMenu.setText( getText( R.string.aggiungiprodottoalmenù ) );


        }
        Log.d( "kmfldksmf","fds" );
    }

    private void creaArticolo() {
        plusButton = (ImageView) findViewById( R.id.imageView20 );
        creaArt = (ImageButton) findViewById( R.id.imageButton8 );
        creaArt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonString = textBottonMenu.getText().toString();
                if (buttonString.equals( getString( R.string.aggiungiprodottoalmenù ) )){


                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomePage.this, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title

                LayoutInflater inflater = getLayoutInflater();
                View viewView = inflater.inflate( R.layout.layout_crea_nuovo_prodotto, null );

                dialogBuilder.setView( viewView );
                AlertDialog alertDialog = dialogBuilder.create();


                ImageButton closeDialog = (ImageButton) viewView.findViewById( R.id.imageButton17 );
                closeDialog.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                } );


                TextInputEditText T_ingredienti = (TextInputEditText) viewView.findViewById( R.id.textIngrediente1 );
                TextInputLayout l_ingredienti = (TextInputLayout) viewView.findViewById( R.id.textLayoutingrdiete1 );
                ImageButton creaArticolo = (ImageButton) viewView.findViewById( R.id.imageButton15 );
                ListView lista_ingredienti = (ListView) viewView.findViewById( R.id.listCreaIngrediente );
                ArrayList<String> arrayList = new ArrayList<String>();
                final String[][] ingredientiString = new String[1][1];
                T_ingredienti.addTextChangedListener( new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        l_ingredienti.setError( null );
                        Log.d( "kfkfkkfkf", "lllll" );
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                } );
                l_ingredienti.setEndIconOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ingrediente = T_ingredienti.getText().toString();


                        if (arrayList.contains( ingrediente )) {
                            l_ingredienti.setError( getString( R.string.ingredientegiainserito ) );

                        } else if (ingrediente.isEmpty()) {
                            l_ingredienti.setError( getString( R.string.ingredientegiainserito ) );
                        } else {
                            T_ingredienti.setText( null );
                            arrayList.add( ingrediente );
                            try_adapter adapter = new try_adapter( HomePage.this, arrayList );
                            lista_ingredienti.setAdapter( adapter );
                            adapter.notifyDataSetChanged();
                        }


                    }
                } );


                TextInputLayout l_nomeArt = (TextInputLayout) viewView.findViewById( R.id.textLayoutNome );
                TextInputLayout l_prezzoArt = (TextInputLayout) viewView.findViewById( R.id.textLayoutPrezzo );
                TextInputEditText t_nomeArt = (TextInputEditText) viewView.findViewById( R.id.textNome );
                TextInputEditText t_prezzoArt = (TextInputEditText) viewView.findViewById( R.id.textPrezzo );
                Spinner spinner = (Spinner) viewView.findViewById( R.id.spinner );


                creaArticolo.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ingredientiString[0] = new String[]{String.valueOf( arrayList )};


                        creaArticolo.setClickable( true );
                        String nome = t_nomeArt.getText().toString();
                        String prezzo = t_prezzoArt.getText().toString();
                        String categoria = spinner.getSelectedItem().toString();
                        l_nomeArt.setError( null );
                        l_prezzoArt.setError( null );
                        if (nome.isEmpty()) {
                            l_nomeArt.setError( getString( R.string.nomearticolononinserito ) );
                        } else if (prezzo.isEmpty()) {
                            l_prezzoArt.setError( getString( R.string.prezzononinserito ) );
                        } else if (categoria.equals( getString( R.string.selezionacategoria ) )) {
                            l_nomeArt.setError( getString( R.string.categorianonselezionata ) );
                        } else {
                            creaArticolo.setClickable( false );
                            ArrayProdotto arrayProdotto = new ArrayProdotto();

                            String[] arr = arrayList.toArray( new String[arrayList.size()] );
                            List<String> listIng = Arrays.asList( arr );


                            arrayProdotto.setIngredienti( listIng );
                            arrayProdotto.setNome( nome );
                            arrayProdotto.setId( nome.replace(" ", "") );
                            arrayProdotto.setCategoria( categoria );
                            arrayProdotto.setEmail( email );
                            arrayProdotto.setPrezzo( prezzo );
                            Calendar cal = Calendar.getInstance();
                            arrayProdotto.setGiorno( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
                            arrayProdotto.setMese( String.valueOf( cal.get( Calendar.MONTH ) ) );
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());

                            arrayProdotto.setOra( currentDateandTime );
                            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                            ArrayList<String> uriArray = new ArrayList<>();

                            firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    alertDialog.dismiss();
                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomePage.this, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title
                                    LayoutInflater inflater = getLayoutInflater();
                                    View viewView = inflater.inflate( R.layout.layout_add_image_prodotto, null );
                                    dialogBuilder.setView( viewView );
                                    AlertDialog alertDialogg = dialogBuilder.create();
                                    alertDialogg.show();

                                    image_prdotto_slide = (ImageSlider) viewView.findViewById( R.id.image_slider );
                                    ImageButton caricaProdotto = (ImageButton) viewView.findViewById( R.id.imageButton19 );
                                    ImageButton chooseImage = (ImageButton) viewView.findViewById( R.id.imageButton18 );
                                    ProgressBar progressBar = (ProgressBar) viewView.findViewById( R.id.progressBar );
                                    TextView coutImageText = (TextView) viewView.findViewById( R.id.textView36 );
                                    chooseImage.setOnClickListener( new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent();
                                            intent.setType( "image/*" );
                                            intent.putExtra( Intent.EXTRA_ALLOW_MULTIPLE, true );
                                            intent.setAction( Intent.ACTION_GET_CONTENT );
                                            startActivityForResult( Intent.createChooser( intent, "Choose Picture" ), 1 );
                                        }
                                    } );
                                    caricaProdotto.setOnClickListener( new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (imageListPath.size() != 0) {
                                                caricaProdotto.setClickable( false );
                                                progressBar.setVisibility( View.VISIBLE );
                                                coutImageText.setVisibility( View.VISIBLE );
                                                coutImageText.setText( immaginiCaricate + " / " + imageListPath.size() );
                                                immaginiCaricate = 1;
                                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                                StorageReference storageRef = storage.getReference();

                                                for (int i = 0; i < imageListPath.size(); i++) {
                                                    int finalI = i;
                                                    StorageReference storagee = storageRef.child( email + "/" + UUID.randomUUID().toString() );
                                                    Log.d( "kfkdldl", String.valueOf( imageListPath.get( i ) ) );
                                                    storagee.putFile( imageListPath.get( i ) ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                                            if (snapshot.getBytesTransferred() != 0) {
                                                                double progress = 100 * ((double) snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                                                int currentprogress = (int) progress;
                                                                progressBar.setProgress( currentprogress );
                                                                Log.d( "ldldldldldldlld", String.valueOf( progress ) );
                                                            }
                                                        }
                                                    } )
                                                            .addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                @Override
                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                    storagee.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                                                        @Override
                                                                        public void onSuccess(Uri uri) {
                                                                            uriArray.add( uri.toString() );
                                                                            if (uriArray.size() > immaginiCaricate) {
                                                                                immaginiCaricate += 1;
                                                                                coutImageText.setText( immaginiCaricate + " / " + (imageListPath.size()) );
                                                                            }
                                                                            if (uriArray.size() == imageListPath.size()) {
                                                                                immaginiCaricate = 1;
                                                                                FirebaseFirestore firebaseFirestore1 = FirebaseFirestore.getInstance();
                                                                                DocumentReference documentReference = firebaseFirestore1.collection( email ).document( nome );

                                                                                String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                                                List<String> listIngg = Arrays.asList( arr );

                                                                                documentReference.update( "foto", listIngg ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        alertDialogg.dismiss();
                                                                                        Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                                    @Override
                                                                                                    public void onClick(View view) {
                                                                                                        //portalo al prodotto
                                                                                                    }
                                                                                                } )

                                                                                                .show();
                                                                                    }
                                                                                } );
                                                                            }
                                                                        }
                                                                    } );


                                                                }
                                                            } ).addOnFailureListener( new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            caricaProdotto.setClickable( true );
                                                            Toast.makeText( getApplicationContext(), getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                        }
                                                    } );

                                                }
                                            } else {
                                                String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                List<String> listIngg = Arrays.asList( arr );
                                                DocumentReference documentReference = firebaseFirestore.collection( email ).document(nome);
                                                documentReference.update( "foto", listIngg ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        alertDialogg.dismiss();

                                                    }
                                                } );

                                            }
                                        }
                                    } );
                                }
                            } );
                        }
                    }
                } );
                alertDialog.show();

            }
                else {
                    l_cocktail.setVisibility( View.GONE );
                    l_categorie.setVisibility( View.VISIBLE );
                    plusButton.setVisibility( View.VISIBLE );
                    textBottonMenu.setText( getString( R.string.aggiungiprodottoalmenù ) );
                }
        }
        } );
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );

        if(requestCode == 50){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomePage.this );
// ...Irrelevant code for customizing the buttons and title


            LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View viewView = inflater.inflate( R.layout.layout_sca_qrcode, null );

            dialogBuilder.setView( viewView );

            codeScannerView = (CodeScannerView) viewView.findViewById( R.id.scanner_view );
            codeScanner = new CodeScanner( HomePage.this, codeScannerView );
            codeScanner.startPreview();


            codeScanner.setDecodeCallback( new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull Result result) {
                    Log.d( "pfdkskf", result.getText() );
                    alertDialog.dismiss();

                }
            } );

            alertDialog = dialogBuilder.create();
            alertDialog.show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == RESULT_CANCELED) {
            // action cancelled
            setResult(Activity.RESULT_OK);
        }
        if(data != null) {
            slideModels.clear();
            filePath = null;
            imageListPath.clear();
            if (requestCode == 1) {
                slideModels.clear();
                imageListPath = new ArrayList<>();

                ClipData selectedimg = data.getClipData();
                if (selectedimg != null) {
                    for (int i = 0; i < selectedimg.getItemCount(); i++) {
                        filePath = data.getData();

                        ClipData.Item item = selectedimg.getItemAt( i );
                        Log.d( "kfkdksllsl", String.valueOf( item.getUri() ) );
                        imageListPath.add( item.getUri() );
                        slideModels.add( new SlideModel( String.valueOf( item.getUri() ), null ) );
                        if (i < selectedimg.getItemCount() + 1) {
                            image_prdotto_slide.setImageList( slideModels );
                        }
                    }
                } else {
                    imageListPath.add( data.getData() );
                    slideModels.add( new SlideModel( String.valueOf( data.getData() ), null ) );
                    image_prdotto_slide.setImageList( slideModels );
                }
            }
            else if (requestCode == 2) {
                Log.d( "oodosdfo", String.valueOf( maintitle.size() ) );
                Log.d( "oodosdfo", String.valueOf( maintitle.get( maintitle.size() - 1 ) ) );
                maintitle.remove( maintitle.size() - 1 );
                ClipData selectedimg = data.getClipData();
                if (selectedimg != null) {
                    for (int i = 0; i < selectedimg.getItemCount(); i++) {
                        ClipData.Item item = selectedimg.getItemAt( i );
                        Log.d( "kfkdksllsl", String.valueOf( item.getUri() ) );
                        if (i < selectedimg.getItemCount() + 1) {
                            maintitle.add( item.getUri() );
                        } else {
                        }
                    }

                    int positionnn = -1;
                    positionnn = maintitle.indexOf( "vuoto" );
                    if (positionnn == -1) {

                    } else {
                        maintitle.remove( positionnn );
                    }
                    maintitle.add( "vuoto" );
                    adapterr.notifyDataSetChanged();
                } else {

                    maintitle.add( data.getData() );
                    int positionnn = -1;
                    positionnn = maintitle.indexOf( "vuoto" );
                    if (positionnn == -1) {

                    } else {
                        maintitle.remove( positionnn );
                    }
                    maintitle.add( "vuoto" );
                    adapterr.notifyDataSetChanged();
                }
            }
            else if(requestCode == 4){
                uriArray.clear();
                slideModels.clear();
                imageListPath.clear();

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomePage.this, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title

                LayoutInflater inflater = getLayoutInflater();
                View viewView = inflater.inflate( R.layout.alert_crea_post, null );
                dialogBuilder.setView( viewView );
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                imageSliderr = (ImageSlider) viewView.findViewById( R.id.image_slider );

                ClipData selectedimg = data.getClipData();
                if (selectedimg != null) {
                    for (int i = 0; i < selectedimg.getItemCount(); i++) {
                        filePath = data.getData();

                        ClipData.Item item = selectedimg.getItemAt( i );
                        Log.d( "kfkdksllsl", String.valueOf( item.getUri() ) );
                        imageListPath.add( item.getUri() );
                        slideModels.add( new SlideModel( String.valueOf( item.getUri() ), null ) );
                        if (i < selectedimg.getItemCount() + 1) {
                            imageSliderr.setImageList( slideModels );
                        }
                    }
                } else {
                    imageListPath.add( data.getData() );
                    slideModels.add( new SlideModel( String.valueOf( data.getData() ), null ) );
                    imageSliderr.setImageList( slideModels );
                }

                ImageButton creaPostButton = (ImageButton) viewView.findViewById( R.id.imageButton7 );
                TextInputLayout l_desc = (TextInputLayout) viewView.findViewById( R.id.textLayoutNome );
                TextInputEditText t_desc = (TextInputEditText) viewView.findViewById( R.id.textNome );
                Log.d( "lkfmsdlkfm", String.valueOf( imageSliderr.getVisibility() ) );

                creaPostButton.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference storageRef = firebaseStorage.getReference();
                        StorageReference storagee = storageRef.child( email + "/" + UUID.randomUUID().toString() );

                        if (selectedimg != null) {
                            //selezionata piu di una foto

                            for (int i = 0; i < selectedimg.getItemCount(); i++) {
                                filePath = data.getData();
                                ClipData.Item item = selectedimg.getItemAt( i );

                                if (i < selectedimg.getItemCount() + 1) {

                                    storagee.putFile( item.getUri() ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            storagee.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    uriArray.add( String.valueOf( uri ) );

                                                    Log.d( "kfmdskf",uriArray.size() + " " + imageListPath.size() );

                                                    if(uriArray.size() == imageListPath.size()){
                                                        SharedPreferences s2 = getSharedPreferences("fotoPinnate",MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = s2.edit();
                                                        editor.putString("pin",uriArray.get(0) + "ì");
                                                        editor.putInt("fotoCaricate",s2.getInt("fotoCaricate",0) + 1);
                                                        editor.commit();
                                                        String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                        List<String> listIngg = Arrays.asList( arr );
                                                        String desc = t_desc.getText().toString();
                                                        ArrayPost arrayPost = new ArrayPost();
                                                        if(desc.isEmpty()){
                                                            arrayPost.setDescrizione( "" );
                                                        }else{
                                                            arrayPost.setDescrizione( desc );
                                                        }
                                                        arrayPost.setLike( "0" );
                                                        arrayPost.setCategoria( "Post" );
                                                        arrayPost.setPinnato( "si" );
                                                        arrayPost.setFoto( listIngg );
                                                        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                        firebaseFirestore.collection( email+ "Post" ).add( arrayPost ).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {

                                                                documentReference.update( "id",documentReference.getId() ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

                                                                        setImageSlider();
                                                                        alertDialog.dismiss();
                                                                        Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(View view) {
                                                                                        //portalo al prodotto
                                                                                        //notify

                                                                                    }
                                                                                } )

                                                                                .show();
                                                                    }
                                                                } );


                                                            }
                                                        } );
                                                    }
                                                }
                                            } );
                                        }
                                    } );
                                }
                            }
                        }
                        else{
                            storagee.putFile( data.getData() ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storagee.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            uriArray.add( String.valueOf( uri ) );
                                            if(uriArray.size() == imageListPath.size()){
                                                String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                List<String> listIngg = Arrays.asList( arr );
                                                String desc = t_desc.getText().toString();
                                                ArrayPost arrayPost = new ArrayPost();
                                                if(desc.isEmpty()){
                                                    arrayPost.setDescrizione( "" );
                                                }else{
                                                    arrayPost.setDescrizione( desc );
                                                }
                                                SharedPreferences s2 = getSharedPreferences("fotoPinnate",MODE_PRIVATE);
                                                SharedPreferences.Editor editor = s2.edit();
                                                editor.putString("pin",uriArray.get(0) + "ì");
                                                editor.putInt("fotoCaricate",s2.getInt("fotoCaricate",0) + 1);
                                                Log.d("jfnjkanf", String.valueOf(s2.getInt("fotoCaricate",00)));
                                                editor.commit();
                                                arrayPost.setLike( "0" );
                                                arrayPost.setCategoria( "Post" );
                                                arrayPost.setPinnato( "si" );
                                                arrayPost.setFoto( listIngg );
                                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                firebaseFirestore.collection( email+ "Post" ).add( arrayPost ).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {

                                                        documentReference.update( "id",documentReference.getId() ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {


                                                                setImageSlider();

                                                                alertDialog.dismiss();
                                                                Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                        .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View view) {
                                                                                //portalo al prodotto

                                                                            }
                                                                        } )

                                                                        .show();
                                                            }
                                                        } );
                                                    }
                                                } );
                                            }
                                        }
                                    } );
                                }
                            } );

                        }







                    }
                } );

            }
        }
    }
    ImageButton search;
    TextInputLayout l_search;
    TextInputEditText t_search;
    ArrayList<StringRegistrazione> arrayList = new ArrayList<>();
    ImageView sfondoList;
    ArrayList listImage = new ArrayList();
    String nomeee;
    ListView lista_ingredienti;
    ListView list_ricerca;
    ArrayList ingredientiList = new ArrayList();
    Group group;
    ArrayList arraylisttt = new ArrayList();
    ImageButton backList;
    String prezzooo,categoriaaa;
    private ConstraintSet mConstraintSet = new ConstraintSet();
    int in;

    private void setBottonSearch() {
        backList = (ImageButton) findViewById( R.id.imageView25 );
        group = (Group) findViewById( R.id.group1    );
        search = (ImageButton) findViewById( R.id.imageButton9 );
        l_search = (TextInputLayout) findViewById( R.id.textInputLayout2 );
        t_search = (TextInputEditText) findViewById( R.id.textPass );
        list_ricerca = (ListView) findViewById( R.id.list_ricerca_articolo   );
        sfondoList = (ImageView) findViewById( R.id.imageView22 );
        search.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("kfkfsdklfkdòs",t_search.getText().toString());

                if(l_search.getVisibility() == View.GONE){
                    l_search.setVisibility( View.VISIBLE );
                    list_ricerca.setVisibility( View.VISIBLE );
                    backList.setVisibility( View.VISIBLE );

                    group.setVisibility( View.GONE );
                }else{
                    if(t_search.getText().toString().isEmpty()){
                        backList.setVisibility( View.GONE );

                        l_search.setVisibility( View.GONE );
                        list_ricerca.setVisibility( View.GONE );
                        group.setVisibility( View.VISIBLE );

                        sfondoList.setVisibility( View.GONE );
                    }else{

                    }

                }
            }
        } );

            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            t_search.addTextChangedListener( new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(l_cocktail.getVisibility() == View.GONE) {
                        arrayList = new ArrayList<>();
                        arrayList.clear();
                        Log.d( "fkldslfq","dfsdfsdf" );

                        firebaseFirestore.collection( email ).get()


                                .addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if (!queryDocumentSnapshots.isEmpty()) {
                                            arrayList.clear();
                                            // if the snapshot is not empty we are hiding
                                            // our progress bar and adding our data in a list.
                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                            for (DocumentSnapshot d : list) {
                                                Log.d( "fkldslfq", d.getId() );

                                                if (d.getId().contains( charSequence.toString() ) == true) {
                                                    StringRegistrazione dataModal = d.toObject( StringRegistrazione.class );

                                                    // after getting data from Firebase we are
                                                    // storing that data in our array list
                                                    arrayList.add( dataModal );
                                                } else {
                                                    Log.d( "jdkdkdkdk", d.getId() );
                                                    Log.d( "jdkdkdkdk", charSequence.toString() );
                                                }


                                            }
                                            // after that we are passing our array list to our adapter class.
                                            adapter_list_search_prodotti adapter = new adapter_list_search_prodotti( HomePage.this, arrayList );

                                            // after passing this array list to our adapter
                                            // class we are setting our adapter to our list view.
                                            list_ricerca.setAdapter( adapter );
                                            list_ricerca.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                    TextView idText = (TextView) view.findViewById( R.id.textView135 );
                                                    String idPost = idText.getText().toString();
                                                    Log.d("jksankldjna",idPost);
                                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomePage.this,R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title
                                                    LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                    View viewView = inflater.inflate( R.layout.click_item_list_prodotti, null );
                                                    dialogBuilder.setView( viewView );
                                                    AlertDialog alertDialogg = dialogBuilder.create();
                                                    alertDialogg.show();
                                                    TextView titolo = (TextView) viewView.findViewById( R.id.textView36 );
                                                    TextView prezzoo = (TextView) viewView.findViewById( R.id.textView41 );
                                                    Spinner spinner = (Spinner) viewView.findViewById( R.id.spinner );
                                                    ImageView elimia = (ImageView) viewView.findViewById( R.id.imageView58 );
                                                    ImageView modifica = (ImageView) viewView.findViewById( R.id.imageView57 );
                                                    spinner.setEnabled( false );
                                                    ListView listngredienti = (ListView) viewView.findViewById( R.id.listingr );
                                                    ImageSlider imageSlider = (ImageSlider) viewView.findViewById( R.id.image_slider );
                                                    TextView ing = (TextView) viewView.findViewById( R.id.textView43);
                                                    firebaseFirestore.collection( email ).document(idPost).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if(task.isSuccessful()){
                                                                listImage.clear();
                                                                Log.d( "fjfndsjlj", String.valueOf(task.getResult().getData()));
                                                                titolo.setText(task.getResult().getString( "nome" ));
                                                                nomeee = task.getResult().getString( "nome" );
                                                                prezzooo = task.getResult().getString( "prezzo" );
                                                                categoriaaa = task.getResult().getString( "categoria" );
                                                                prezzoo.setText( task.getResult().getString( "prezzo" ) + " ,00€" );
                                                                if(task.getResult().getString( "categoria" ).equals( "Cocktail" )){
                                                                }else if(task.getResult().getString( "categoria" ).equals( "Bevande" )){
                                                                    spinner.setSelection( 3 );
                                                                }
                                                                else if(task.getResult().getString( "categoria" ).equals( "Dolci" )){
                                                                    spinner.setSelection( 1 );
                                                                }
                                                                else if(task.getResult().getString( "categoria" ).equals( "Salati" )){
                                                                    spinner.setSelection( 2 );
                                                                }
                                                                ArrayList<String> group = (ArrayList<String>) task.getResult().get("ingredienti");
                                                                ArrayList<String> fotoList = (ArrayList<String>) task.getResult().get("foto");
                                                                ArrayList<SlideModel> arrMod = new ArrayList<>();
                                                                ingredientiList =(ArrayList<String>)  task.getResult().get( "ingredienti" );

                                                                Log.d( "mldkmkdmd", String.valueOf( task.getResult().get("ingredienti") ) );

                                                                if (group != null) {
                                                                    if(group.size() >0){
                                                                        ArrayIngredienti arraySearchprodotti = new ArrayIngredienti( HomePage.this, group );
                                                                        listngredienti.setAdapter( arraySearchprodotti );
                                                                    }else {
                                                                        listngredienti.setVisibility( View.GONE );
                                                                        ing.setVisibility( View.GONE );
                                                                    }

                                                                } else {
                                                                    listngredienti.setVisibility( View.GONE );
                                                                    ing.setVisibility( View.GONE );
                                                                }


                                                                if (fotoList != null) {
                                                                    if(fotoList.size() >0) {
                                                                        for (int i = 0; i < fotoList.size(); i++) {
                                                                            listImage.add( fotoList.get( i ) );
                                                                            arrMod.add( new SlideModel( fotoList.get( i ),null) );
                                                                        }
                                                                        imageSlider.setImageList( arrMod );

                                                                    }
                                                                    else {
                                                                        imageSlider.setVisibility( View.GONE );
                                                                    }
                                                                } else {
                                                                    imageSlider.setVisibility( View.GONE );
                                                                }
                                                            }
                                                        }
                                                    } );

                                                    elimia.setOnClickListener( new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    switch (which){
                                                                        case DialogInterface.BUTTON_POSITIVE:
                                                                            //Yes button clicked

                                                                            if(listImage.size() > 0) {
                                                                                for (int i = 0; i < listImage.size(); i++) {
                                                                                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                                                                                    StorageReference photoRef = firebaseStorage.getReferenceFromUrl( String.valueOf( listImage.get( i ) ) );
                                                                                    photoRef.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            listImage.remove( 0 );
                                                                                            if (listImage.size() == 0) {
                                                                                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                                                                DocumentReference documentReference = firebaseFirestore.collection( email ).document( nomeee );
                                                                                                documentReference.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        dialog.dismiss();
                                                                                                        startActivity( new Intent( getApplicationContext(), HomePage.class ) );
                                                                                                        finish();
                                                                                                    }
                                                                                                } );
                                                                                            }
                                                                                        }
                                                                                    } );
                                                                                }
                                                                            }else{
                                                                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                                                DocumentReference documentReference = firebaseFirestore.collection( email ).document( nomeee );
                                                                                documentReference.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        dialog.dismiss();
                                                                                        startActivity( new Intent( getApplicationContext(), HomePage.class ) );
                                                                                        finish();
                                                                                    }
                                                                                } );
                                                                            }



                                                                        break;

                                                                        case DialogInterface.BUTTON_NEGATIVE:
                                                                            //No button clicked

                                                                            dialog.dismiss();

                                                                            break;
                                                                    }
                                                                }
                                                            };

                                                            AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
                                                            builder.setMessage(getString( R.string.seisicurodivolereliminareilprodotto )).setPositiveButton(getString( R.string.si ), dialogClickListener)
                                                                    .setNegativeButton(getString( R.string.no ), dialogClickListener).show();
                                                        }
                                                    } );

                                                    modifica.setOnClickListener( new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomePage.this, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title

                                                            LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                            View viewView = inflater.inflate( R.layout.layout_crea_nuovo_prodotto, null );
                                                            TextInputEditText T_ingredienti = (TextInputEditText) viewView.findViewById( R.id.textIngrediente1 );

                                                            TextInputLayout l_ingredienti = (TextInputLayout) viewView.findViewById( R.id.textLayoutingrdiete1 );
                                                            ImageButton creaArticolo = (ImageButton) viewView.findViewById( R.id.imageButton15 );
                                                            lista_ingredienti = (ListView) viewView.findViewById( R.id.listCreaIngrediente );
                                                            arraylisttt = new ArrayList<String>();
                                                            final String[][] ingredientiString = new String[1][1];
                                                            TextInputLayout l_nomeArt = (TextInputLayout) viewView.findViewById( R.id.textLayoutNome );
                                                            TextInputLayout l_prezzoArt = (TextInputLayout) viewView.findViewById( R.id.textLayoutPrezzo );
                                                            TextInputEditText t_nomeArt = (TextInputEditText) viewView.findViewById( R.id.textNome );
                                                            TextInputEditText t_prezzoArt = (TextInputEditText) viewView.findViewById( R.id.textPrezzo );
                                                            Spinner spinner = (Spinner) viewView.findViewById( R.id.spinner );
                                                            dialogBuilder.setView( viewView );
                                                            AlertDialog alertDialog = dialogBuilder.create();

                                                            t_nomeArt.setText( nomeee );
                                                            t_prezzoArt.setText( prezzooo);
                                                            if (categoriaaa.equals( getString( R.string.cocktail ) )) {
                                                                spinner.setSelection( 1 );
                                                            } else if (categoriaaa.equals( getString( R.string.dolci ) )) {
                                                                spinner.setSelection( 2 );
                                                            } else if (categoriaaa.equals( getString( R.string.salati ) )) {
                                                                spinner.setSelection( 3 );
                                                            } else if (categoriaaa.equals( getString( R.string.bevande ) )) {
                                                                spinner.setSelection( 4 );
                                                            }
                                                            arraylisttt = ingredientiList;
                                                            try_adapter adapter = new try_adapter( HomePage.this, arraylisttt );
                                                            lista_ingredienti.setAdapter( adapter );
                                                            adapter.notifyDataSetChanged();
                                                            T_ingredienti.addTextChangedListener( new TextWatcher() {
                                                                @Override
                                                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                                    l_ingredienti.setError( null );
                                                                    Log.d( "kfkfkkfkf", "lllll" );
                                                                }

                                                                @Override
                                                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                                }

                                                                @Override
                                                                public void afterTextChanged(Editable editable) {

                                                                }
                                                            } );
                                                            l_ingredienti.setEndIconOnClickListener( new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                    String ingrediente = T_ingredienti.getText().toString();


                                                                    if (arrayList.contains( ingrediente )) {
                                                                        l_ingredienti.setError( getString( R.string.ingredientegiainserito ) );

                                                                    } else if (ingrediente.isEmpty()) {
                                                                        l_ingredienti.setError( getString( R.string.ingredientegiainserito ) );
                                                                    } else {
                                                                        T_ingredienti.setText( null );
                                                                        arraylisttt.add( ingrediente );
                                                                        try_adapter adapter = new try_adapter( HomePage.this, arraylisttt );
                                                                        lista_ingredienti.setAdapter( adapter );
                                                                        adapter.notifyDataSetChanged();
                                                                    }


                                                                }
                                                            } );
                                                            creaArticolo.setOnClickListener( new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {

                                                                    ingredientiString[0] = new String[]{String.valueOf( arrayList )};


                                                                    creaArticolo.setClickable( true );
                                                                    String nome = t_nomeArt.getText().toString();
                                                                    String prezzo = t_prezzoArt.getText().toString();
                                                                    String categoria = spinner.getSelectedItem().toString();
                                                                    l_nomeArt.setError( null );
                                                                    l_prezzoArt.setError( null );
                                                                    if (nome.isEmpty()) {
                                                                        l_nomeArt.setError( getString( R.string.nomearticolononinserito ) );
                                                                    } else if (prezzo.isEmpty()) {
                                                                        l_prezzoArt.setError( getString( R.string.prezzononinserito ) );
                                                                    } else if (categoria.equals( getString( R.string.selezionacategoria ) )) {
                                                                        l_nomeArt.setError( getString( R.string.categorianonselezionata ) );
                                                                    }
                                                                    else {




                                                                        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                                        DocumentReference documentReference = firebaseFirestore.collection( email ).document( nome );


                                                                        ArrayProdotto arrayProdotto = new ArrayProdotto();

                                                                        String[] arr = (String[]) arraylisttt.toArray( new String[arraylisttt.size()] );
                                                                        List<String> listIng = Arrays.asList( arr );
                                                                        arrayProdotto.setIngredienti( listIng );
                                                                        arrayProdotto.setNome( nome );
                                                                        arrayProdotto.setId( nome );
                                                                        arrayProdotto.setCategoria( categoria );
                                                                        arrayProdotto.setEmail( email );
                                                                        arrayProdotto.setPrezzo( prezzo );
                                                                        Calendar cal = Calendar.getInstance();
                                                                        arrayProdotto.setGiorno( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
                                                                        arrayProdotto.setMese( String.valueOf( cal.get( Calendar.MONTH ) ) );
                                                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                                                                        String currentDateandTime = sdf.format(new Date());
                                                                        arrayProdotto.setOra( currentDateandTime );
                                                                        ArrayList<String> uriArray = new ArrayList<>();


                                                                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomePage.this, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title
                                                                        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                                        View viewView = inflater.inflate( R.layout.layout_edit_image, null );
                                                                        dialogBuilder.setView( viewView );
                                                                        AlertDialog alertDialogg = dialogBuilder.create();
                                                                        alertDialogg.show();
                                                                        ImageButton imageButton = viewView.findViewById( R.id.imageButton22 );
                                                                        GridView listPhotoEdit = (GridView) viewView.findViewById( R.id.listEdit );
                                                                        androidx.constraintlayout.widget.Group progressGroup = (androidx.constraintlayout.widget.Group) viewView.findViewById( R.id.groupProgress );
                                                                        androidx.constraintlayout.widget.Group progressImage = (androidx.constraintlayout.widget.Group) viewView.findViewById( R.id.groupImage );
                                                                        List<String> arrayList = new ArrayList<String>();
                                                                        arrayList.clear();
                                                                        Log.d( "pkfdslkf", String.valueOf( listImage ) );
                                                                        if (listImage != null) {
                                                                            arrayList = listImage;
                                                                        }
                                                                        if(!arrayList.contains( "vuoto" )) {
                                                                            arrayList.add( "vuoto" );
                                                                        }

                                                                        adapterr = new adapter_edit_foto( HomePage.this, arrayList );
                                                                        Log.d( "fkldsmf", String.valueOf( arrayList ) );
                                                                        // after passing this array list to our adapter
                                                                        // class we are setting our adapter to our list view.
                                                                        listPhotoEdit.setAdapter( adapterr );


                                                                        List<String> finalArrayList = arrayList;
                                                                        imageButton.setOnClickListener( new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View view) {
                                                                                progressGroup.setVisibility( View.VISIBLE );
                                                                                progressImage.setVisibility( View.GONE );
                                                                                if (adapter_edit_foto.arrayFotoDaEliminare.size() > 0) {
                                                                                    for (int in = 0; in< adapter_edit_foto.arrayFotoDaEliminare.size();in++){


                                                                                        if (Patterns.WEB_URL.matcher( String.valueOf( maintitle.get( i ) ) ).matches()) {
                                                                                            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                                                                                            Log.d( "dfsdfsd", String.valueOf( arrayFotoDaEliminare.get( in )  ) );
                                                                                            StorageReference storageReference = firebaseStorage.getReferenceFromUrl( String.valueOf(  arrayFotoDaEliminare.get( in ) ) );
                                                                                            int finalIn = in;
                                                                                            int finalIn1 = in;
                                                                                            storageReference.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                                                    Log.d( "sdòfds",finalIn + " " + arrayFotoDaEliminare.size() );
                                                                                                    adapter_edit_foto.arrayFotoDaEliminare.remove( 0);
                                                                                                    if(arrayFotoDaEliminare.size() == 0){




                                                                                                        Log.d( "kfmdskmf", String.valueOf( finalArrayList ) );


                                                                                                        FirebaseStorage storage = FirebaseStorage.getInstance();
                                                                                                        StorageReference storageRef = storage.getReference();

                                                                                                        int positionnn = -1;
                                                                                                        positionnn = maintitle.indexOf( "vuoto" );
                                                                                                        if (positionnn == -1) {
                                                                                                            maintitle.remove( positionnn );
                                                                                                        } else {
                                                                                                            maintitle.remove( positionnn );
                                                                                                        }

                                                                                                        for (int i = 0; i < maintitle.size(); i++) {
                                                                                                            Log.d( "kfkfldldl", String.valueOf( maintitle.get( i ) ) );
                                                                                                            Log.d( "kfkfldldl", String.valueOf( maintitle.size() ) );
                                                                                                            if (Patterns.WEB_URL.matcher( String.valueOf( maintitle.get( i ) ) ).matches()) {
                                                                                                                uriArray.add( String.valueOf( maintitle.get( i ) ) );
                                                                                                                immaginiCaricate = uriArray.size();
                                                                                                                Log.d( "lkmdslfmsd",i + " " + maintitle.size() );
                                                                                                                if(maintitle.size() == i +1){
                                                                                                                    String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                                                                                    List<String> listIngg = Arrays.asList( arr );
                                                                                                                    arrayProdotto.setFoto( listIngg );
                                                                                                                    firebaseFirestore.collection( email ).document(nome).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                            DocumentReference documentReference1 = firebaseFirestore.collection( email ).document(nomeee);
                                                                                                                            documentReference1.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                @Override
                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                    firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            Intent intent = new Intent( getApplicationContext(), HomePage.class );
                                                                                                                                            startActivity( intent );
                                                                                                                                            finish();

                                                                                                                                            Log.d( "okfmodkmsfm","lfòsmòòòòòòòòòòòòòò" );
                                                                                                                                            Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                                                    .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                                                                                        @Override
                                                                                                                                                        public void onClick(View view) {
                                                                                                                                                            //portalo al prodotto
                                                                                                                                                        }
                                                                                                                                                    } )

                                                                                                                                                    .show();
                                                                                                                                        }
                                                                                                                                    } );
                                                                                                                                }
                                                                                                                            } );

                                                                                                                        }
                                                                                                                    } );
                                                                                                                }
                                                                                                            }
                                                                                                            else
                                                                                                            {
                                                                                                                Log.d( "okfmodkmsfm", "fkmlkd" );
                                                                                                                StorageReference storagee = storageRef.child( email + "/" + UUID.randomUUID().toString() );
                                                                                                                storagee.putFile( (Uri) maintitle.get( i ) ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                                    @Override
                                                                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                                        Log.d( "okfmodkmsfm", "fkmasdasdasdasdlkd" );
                                                                                                                        storagee.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                                                                                                            @Override
                                                                                                                            public void onSuccess(Uri uri) {
                                                                                                                                uriArray.add( uri.toString() );
                                                                                                                                if (uriArray.size() > immaginiCaricate) {
                                                                                                                                    immaginiCaricate += 1;
                                                                                                                                    Log.d( "okfmodkmsfm", "sizeewwwwwwwwwwww" );

                                                                                                                                }
                                                                                                                                Log.d( "okfmodkmsfm", uriArray.size() + " " + immaginiCaricate + " " + maintitle.size() );

                                                                                                                                if (maintitle.size() == immaginiCaricate) {
                                                                                                                                    Log.d( "okfmodkmsfm", "sizee" );

                                                                                                                                    immaginiCaricate = 1;
                                                                                                                                    FirebaseFirestore firebaseFirestore1 = FirebaseFirestore.getInstance();

                                                                                                                                    String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                                                                                                    List<String> listIngg = Arrays.asList( arr );
                                                                                                                                    arrayProdotto.setFoto( listIngg );

                                                                                                                                    firebaseFirestore.collection( email ).document(nome).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                                @Override
                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                    DocumentReference documentReference1 = firebaseFirestore.collection( email ).document(nomeee);
                                                                                                                                                    documentReference1.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                                        @Override
                                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                            firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                                                @Override
                                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                    Intent intent = new Intent( getApplicationContext(), HomePage.class );
                                                                                                                                                                    startActivity( intent );
                                                                                                                                                                    finish();

                                                                                                                                                                    Log.d( "okfmodkmsfm","lfòsmòòòòòòòòòòòòòò" );
                                                                                                                                                                    Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                                                                            .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                                                                                                                @Override
                                                                                                                                                                                public void onClick(View view) {
                                                                                                                                                                                    //portalo al prodotto
                                                                                                                                                                                }
                                                                                                                                                                            } )

                                                                                                                                                                            .show();
                                                                                                                                                                }
                                                                                                                                                            } );
                                                                                                                                                        }
                                                                                                                                                    } );

                                                                                                                                                }
                                                                                                                                            } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                                                @Override
                                                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                                                    progressGroup.setVisibility( View.GONE );
                                                                                                                                                    progressImage.setVisibility( View.VISIBLE );
                                                                                                                                                    Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                                                                                }
                                                                                                                                            } );
                                                                                                                                        }
                                                                                                                                    } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                                        @Override
                                                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                                                            progressGroup.setVisibility( View.GONE );
                                                                                                                                            progressImage.setVisibility( View.VISIBLE );
                                                                                                                                            Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                                                                        }
                                                                                                                                    } );







                                                                                                                                }
                                                                                                                            }
                                                                                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                            @Override
                                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                                progressGroup.setVisibility( View.GONE );
                                                                                                                                progressImage.setVisibility( View.VISIBLE );
                                                                                                                                Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                                                            }
                                                                                                                        } );
                                                                                                                    }
                                                                                                                } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                                                                                    @Override
                                                                                                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                                                                                                    }
                                                                                                                } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                    @Override
                                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                                        progressGroup.setVisibility( View.GONE );
                                                                                                                        progressImage.setVisibility( View.VISIBLE );
                                                                                                                        Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();

                                                                                                                    }
                                                                                                                } );

                                                                                                            }

                                                                                                        }


                                                                                                    }
                                                                                                    else{
                                                                                                        Log.d( "lksdmflksdmlfmsdlk","Pfdk"   );
                                                                                                    }
                                                                                                }
                                                                                            } )
                                                                                                    .addOnFailureListener( new OnFailureListener() {
                                                                                                        @Override
                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                            progressGroup.setVisibility( View.GONE );
                                                                                                            progressImage.setVisibility( View.VISIBLE );
                                                                                                            Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                                        }
                                                                                                    } );
                                                                                        } else {
                                                                                        }

                                                                                    }


                                                                                }


                                                                                else {


                                                                                    Log.d( "asfdasfasfasfasf", String.valueOf( finalArrayList ) );
                                                                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                                                                    StorageReference storageRef = storage.getReference();

                                                                                    int positionnnt = -1;
                                                                                    positionnnt = maintitle.indexOf( "vuoto" );
                                                                                    if (positionnnt == -1) {
                                                                                        maintitle.remove( positionnnt );
                                                                                    } else {
                                                                                        maintitle.remove( positionnnt );
                                                                                    }
                                                                                    if (maintitle.size() > 0) {
                                                                                        for (int i = 0; i < maintitle.size(); i++) {
                                                                                            Log.d( "asfdasfasfasfasf", String.valueOf( maintitle.get( i ) ) );
                                                                                            Log.d( "asfdasfasfasfasf", String.valueOf( maintitle.size() ) );
                                                                                            if (Patterns.WEB_URL.matcher( String.valueOf( maintitle.get( i ) ) ).matches()) {
                                                                                                uriArray.add( String.valueOf( maintitle.get( i ) ) );
                                                                                                immaginiCaricate = uriArray.size();
                                                                                                Log.d( "lkmdslfmsd", i + " " + maintitle.size() );
                                                                                                if (maintitle.size() == i + 1) {
                                                                                                    String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                                                                    List<String> listIngg = Arrays.asList( arr );
                                                                                                    arrayProdotto.setFoto( listIngg );
                                                                                                    firebaseFirestore.collection( email ).document( nome ).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                            firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                    DocumentReference documentReference1 = firebaseFirestore.collection( email ).document( nomeee );
                                                                                                                    documentReference1.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                            firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                @Override
                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                    Intent intent = new Intent( getApplicationContext(), HomePage.class );
                                                                                                                                    startActivity( intent );
                                                                                                                                    finish();

                                                                                                                                    Log.d( "okfmodkmsfm", "lfòsmòòòòòòòòòòòòòò" );
                                                                                                                                    Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                                            .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                                                                                @Override
                                                                                                                                                public void onClick(View view) {
                                                                                                                                                    //portalo al prodotto
                                                                                                                                                }
                                                                                                                                            } )

                                                                                                                                            .show();
                                                                                                                                }
                                                                                                                            } );
                                                                                                                        }
                                                                                                                    } );

                                                                                                                }
                                                                                                            } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                @Override
                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                    progressGroup.setVisibility( View.GONE );
                                                                                                                    progressImage.setVisibility( View.VISIBLE );
                                                                                                                    Toast.makeText( getApplicationContext(), getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                                                }
                                                                                                            } );
                                                                                                        }
                                                                                                    } ).addOnFailureListener( new OnFailureListener() {
                                                                                                        @Override
                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                            progressGroup.setVisibility( View.GONE );
                                                                                                            progressImage.setVisibility( View.VISIBLE );
                                                                                                            Toast.makeText( getApplicationContext(), getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                                        }
                                                                                                    } );
                                                                                                }

                                                                                            } else {
                                                                                                Log.d( "asfdasfasfasfasf", "fkmlkd" );
                                                                                                StorageReference storagee = storageRef.child( email + "/" + UUID.randomUUID().toString() );
                                                                                                storagee.putFile( (Uri) maintitle.get( i ) ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                        Log.d( "asfdasfasfasfasf", "fkmasdasdasdasdlkd" );
                                                                                                        storagee.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                                                                                            @Override
                                                                                                            public void onSuccess(Uri uri) {
                                                                                                                uriArray.add( uri.toString() );
                                                                                                                if (uriArray.size() > immaginiCaricate) {
                                                                                                                    immaginiCaricate += 1;
                                                                                                                    Log.d( "asfdasfasfasfasf", "sizeewwwwwwwwwwww" );

                                                                                                                }
                                                                                                                Log.d( "asfdasfasfasfasf", uriArray.size() + " " + immaginiCaricate + " " + maintitle.size() );

                                                                                                                if (maintitle.size() == immaginiCaricate) {
                                                                                                                    Log.d( "asfdasfasfasfasf", "sizee" );

                                                                                                                    immaginiCaricate = 1;
                                                                                                                    FirebaseFirestore firebaseFirestore1 = FirebaseFirestore.getInstance();
                                                                                                                    DocumentReference documentReference = firebaseFirestore1.collection( email ).document( nome );

                                                                                                                    String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                                                                                    List<String> listIngg = Arrays.asList( arr );

                                                                                                                    arrayProdotto.setFoto( listIngg );

                                                                                                                    firebaseFirestore.collection( email ).document( nome ).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                            firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                @Override
                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                    DocumentReference documentReference1 = firebaseFirestore.collection( email ).document( nomeee );
                                                                                                                                    documentReference1.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                                @Override
                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                    Intent intent = new Intent( getApplicationContext(), HomePage.class );
                                                                                                                                                    startActivity( intent );
                                                                                                                                                    finish();

                                                                                                                                                    Log.d( "okfmodkmsfm", "lfòsmòòòòòòòòòòòòòò" );
                                                                                                                                                    Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                                                            .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                                                                                                @Override
                                                                                                                                                                public void onClick(View view) {
                                                                                                                                                                    //portalo al prodotto
                                                                                                                                                                }
                                                                                                                                                            } )

                                                                                                                                                            .show();
                                                                                                                                                }
                                                                                                                                            } );
                                                                                                                                        }
                                                                                                                                    } );

                                                                                                                                }
                                                                                                                            } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                                @Override
                                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                                    progressGroup.setVisibility( View.GONE );
                                                                                                                                    progressImage.setVisibility( View.VISIBLE );
                                                                                                                                    Toast.makeText( getApplicationContext(), getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                                                                }
                                                                                                                            } );
                                                                                                                        }
                                                                                                                    } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                        @Override
                                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                                            progressGroup.setVisibility( View.GONE );
                                                                                                                            progressImage.setVisibility( View.VISIBLE );
                                                                                                                            Toast.makeText( getApplicationContext(), getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                                                        }
                                                                                                                    } );


                                                                                                                }
                                                                                                            }
                                                                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                                                                            @Override
                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                progressGroup.setVisibility( View.GONE );
                                                                                                                progressImage.setVisibility( View.VISIBLE );
                                                                                                                Toast.makeText( getApplicationContext(), getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                                            }
                                                                                                        } );
                                                                                                    }
                                                                                                } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                                                                    @Override
                                                                                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                                                                                    }
                                                                                                } ).addOnFailureListener( new OnFailureListener() {
                                                                                                    @Override
                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                        progressGroup.setVisibility( View.GONE );
                                                                                                        progressImage.setVisibility( View.VISIBLE );
                                                                                                        Toast.makeText( getApplicationContext(), getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();

                                                                                                    }
                                                                                                } );

                                                                                            }
                                                                                        }
                                                                                    }else{
                                                                                        String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                                                        List<String> listIngg = Arrays.asList( arr );

                                                                                        arrayProdotto.setFoto( listIngg );

                                                                                        firebaseFirestore.collection( email ).document(nome).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        Intent intent = new Intent( getApplicationContext(), HomePage.class );

                                                                                                        startActivity( intent );
                                                                                                        finish();


                                                                                                        Log.d( "okfmodkmsfm","lfòsmòòòòòòòòòòòòòò" );
                                                                                                        Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                                                    @Override
                                                                                                                    public void onClick(View view) {
                                                                                                                        //portalo al prodotto
                                                                                                                    }
                                                                                                                } )

                                                                                                                .show();
                                                                                                    }
                                                                                                } ).addOnFailureListener( new OnFailureListener() {
                                                                                                    @Override
                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                        progressGroup.setVisibility( View.GONE );
                                                                                                        progressImage.setVisibility( View.VISIBLE );
                                                                                                        Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                                    }
                                                                                                } );
                                                                                            }
                                                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                                                            @Override
                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                progressGroup.setVisibility( View.GONE );
                                                                                                progressImage.setVisibility( View.VISIBLE );
                                                                                                Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                            }
                                                                                        } );
                                                                                    }
                                                                                }



                                                                            }
                                                                        } );


                                                                        alertDialogg.show();
                                                                    }

                                                                }
                                                            } );






                                                            alertDialog.show();
                                                        }
                                                    } );

                                                }
                                            } );
                                        } else {
                                            // if the snapshot is empty we are displaying a toast message.

                                        }
                                    }
                                } );
                    }
                    else{
                        Log.d( "lflflfl","llfl" );
                        arrayList = new ArrayList<>();
                        firebaseFirestore.collection( email ).get()


                                .addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if (!queryDocumentSnapshots.isEmpty()) {
                                            // if the snapshot is not empty we are hiding
                                            // our progress bar and adding our data in a list.
                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                            for (DocumentSnapshot d : list) {
                                                if(d.getString( "categoria" ).equals( categoriaSelezionata )) {


                                                    if (d.getId().contains( charSequence.toString() ) == true) {
                                                        StringRegistrazione dataModal = d.toObject( StringRegistrazione.class );

                                                        // after getting data from Firebase we are
                                                        // storing that data in our array list
                                                        arrayList.add( dataModal );
                                                    } else {

                                                    }
                                                }

                                            }
                                            // after that we are passing our array list to our adapter class.
                                            adapter_list_search_prodotti adapter = new adapter_list_search_prodotti( HomePage.this, arrayList );

                                            // after passing this array list to our adapter
                                            // class we are setting our adapter to our list view.
                                            list_ricerca.setAdapter( adapter );
                                            list_ricerca.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                    TextView idText = (TextView) view.findViewById( R.id.textView135 );
                                                    String idPost = idText.getText().toString();
                                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomePage.this,R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title
                                                    LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                    View viewView = inflater.inflate( R.layout.click_item_list_prodotti, null );
                                                    dialogBuilder.setView( viewView );
                                                    AlertDialog alertDialogg = dialogBuilder.create();
                                                    alertDialogg.show();
                                                    TextView titolo = (TextView) viewView.findViewById( R.id.textView36 );
                                                    TextView prezzo = (TextView) viewView.findViewById( R.id.textView41 );
                                                    Spinner spinner = (Spinner) viewView.findViewById( R.id.spinner );
                                                    ImageView elimia = (ImageView) viewView.findViewById( R.id.imageView58 );
                                                    ImageView modifica = (ImageView) viewView.findViewById( R.id.imageView57 );
                                                    spinner.setEnabled( false );
                                                    ListView listngredienti = (ListView) viewView.findViewById( R.id.listingr );
                                                    ImageSlider imageSlider = (ImageSlider) viewView.findViewById( R.id.image_slider );
                                                    TextView ing = (TextView) viewView.findViewById( R.id.textView43);
                                                    firebaseFirestore.collection( email ).document(idPost).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if(task.isSuccessful()){
                                                                listImage.clear();
                                                                Log.d( "fjfndsjlj", String.valueOf( listImage ) );
                                                                titolo.setText(task.getResult().getString( "nome" ));
                                                                nomeee = task.getResult().getString( "nome" );
                                                                prezzooo = task.getResult().getString( "prezzo" );
                                                                categoriaaa = task.getResult().getString( "categoria" );
                                                                prezzo.setText( task.getResult().getString( "prezzo" ) + " ,00€" );
                                                                if(task.getResult().getString( "categoria" ).equals( "Cocktail" )){
                                                                }else if(task.getResult().getString( "categoria" ).equals( "Bevande" )){
                                                                    spinner.setSelection( 3 );
                                                                }
                                                                else if(task.getResult().getString( "categoria" ).equals( "Dolci" )){
                                                                    spinner.setSelection( 1 );
                                                                }
                                                                else if(task.getResult().getString( "categoria" ).equals( "Salati" )){
                                                                    spinner.setSelection( 2 );
                                                                }
                                                                ArrayList<String> group = (ArrayList<String>) task.getResult().get("ingredienti");
                                                                ArrayList<String> fotoList = (ArrayList<String>) task.getResult().get("foto");
                                                                ArrayList<SlideModel> arrMod = new ArrayList<>();
                                                                ingredientiList =(ArrayList<String>)  task.getResult().get( "ingredienti" );

                                                                Log.d( "mldkmkdmd", String.valueOf( task.getResult().get("ingredienti") ) );

                                                                if (group != null) {
                                                                    if(group.size() >0){
                                                                        ArrayIngredienti arraySearchprodotti = new ArrayIngredienti( HomePage.this, group );
                                                                        listngredienti.setAdapter( arraySearchprodotti );
                                                                    }else {
                                                                        listngredienti.setVisibility( View.GONE );
                                                                        ing.setVisibility( View.GONE );
                                                                    }

                                                                } else {
                                                                    listngredienti.setVisibility( View.GONE );
                                                                    ing.setVisibility( View.GONE );
                                                                }


                                                                if (fotoList != null) {
                                                                    if(fotoList.size() >0) {
                                                                        for (int i = 0; i < fotoList.size(); i++) {
                                                                            listImage.add( fotoList.get( i ) );
                                                                            arrMod.add( new SlideModel( fotoList.get( i ), null ) );
                                                                        }
                                                                        imageSlider.setImageList( arrMod );

                                                                    }
                                                                    else {
                                                                        imageSlider.setVisibility( View.GONE );
                                                                    }
                                                                } else {
                                                                    imageSlider.setVisibility( View.GONE );
                                                                }
                                                            }
                                                        }
                                                    } );

                                                    elimia.setOnClickListener( new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    switch (which){
                                                                        case DialogInterface.BUTTON_POSITIVE:
                                                                            //Yes button clicked

                                                                            if(listImage.size() > 0) {
                                                                                for (int i = 0; i < listImage.size(); i++) {
                                                                                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                                                                                    StorageReference photoRef = firebaseStorage.getReferenceFromUrl( String.valueOf( listImage.get( i ) ) );
                                                                                    photoRef.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            listImage.remove( 0 );
                                                                                            if (listImage.size() == 0) {
                                                                                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                                                                DocumentReference documentReference = firebaseFirestore.collection( email ).document( nomeee );
                                                                                                documentReference.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        dialog.dismiss();
                                                                                                        startActivity( new Intent( getApplicationContext(), HomePage.class ) );
                                                                                                        finish();
                                                                                                    }
                                                                                                } );
                                                                                            }
                                                                                        }
                                                                                    } );
                                                                                }
                                                                            }else{
                                                                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                                                DocumentReference documentReference = firebaseFirestore.collection( email ).document( nomeee );
                                                                                documentReference.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        dialog.dismiss();
                                                                                        startActivity( new Intent( getApplicationContext(), HomePage.class ) );
                                                                                        finish();
                                                                                    }
                                                                                } );
                                                                            }



                                                                            break;

                                                                        case DialogInterface.BUTTON_NEGATIVE:
                                                                            //No button clicked

                                                                            dialog.dismiss();

                                                                            break;
                                                                    }
                                                                }
                                                            };

                                                            AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
                                                            builder.setMessage(getString( R.string.seisicurodivolereliminareilprodotto )).setPositiveButton(getString( R.string.si ), dialogClickListener)
                                                                    .setNegativeButton(getString( R.string.no ), dialogClickListener).show();
                                                        }
                                                    } );

                                                    modifica.setOnClickListener( new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomePage.this, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title

                                                            LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                            View viewView = inflater.inflate( R.layout.layout_crea_nuovo_prodotto, null );
                                                            TextInputEditText T_ingredienti = (TextInputEditText) viewView.findViewById( R.id.textIngrediente1 );

                                                            TextInputLayout l_ingredienti = (TextInputLayout) viewView.findViewById( R.id.textLayoutingrdiete1 );
                                                            ImageButton creaArticolo = (ImageButton) viewView.findViewById( R.id.imageButton15 );
                                                            lista_ingredienti = (ListView) viewView.findViewById( R.id.listCreaIngrediente );
                                                            arraylisttt = new ArrayList<String>();
                                                            final String[][] ingredientiString = new String[1][1];
                                                            TextInputLayout l_nomeArt = (TextInputLayout) viewView.findViewById( R.id.textLayoutNome );
                                                            TextInputLayout l_prezzoArt = (TextInputLayout) viewView.findViewById( R.id.textLayoutPrezzo );
                                                            TextInputEditText t_nomeArt = (TextInputEditText) viewView.findViewById( R.id.textNome );
                                                            TextInputEditText t_prezzoArt = (TextInputEditText) viewView.findViewById( R.id.textPrezzo );
                                                            Spinner spinner = (Spinner) viewView.findViewById( R.id.spinner );
                                                            dialogBuilder.setView( viewView );
                                                            AlertDialog alertDialog = dialogBuilder.create();

                                                            t_nomeArt.setText( nomeee );
                                                            t_prezzoArt.setText( prezzooo);
                                                            if (categoriaaa.equals( getString( R.string.cocktail ) )) {
                                                                spinner.setSelection( 1 );
                                                            } else if (categoriaaa.equals( getString( R.string.dolci ) )) {
                                                                spinner.setSelection( 2 );
                                                            } else if (categoriaaa.equals( getString( R.string.salati ) )) {
                                                                spinner.setSelection( 3 );
                                                            } else if (categoriaaa.equals( getString( R.string.bevande ) )) {
                                                                spinner.setSelection( 4 );
                                                            }
                                                            arraylisttt = ingredientiList;
                                                            try_adapter adapter = new try_adapter( HomePage.this, arraylisttt );
                                                            lista_ingredienti.setAdapter( adapter );
                                                            adapter.notifyDataSetChanged();
                                                            T_ingredienti.addTextChangedListener( new TextWatcher() {
                                                                @Override
                                                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                                    l_ingredienti.setError( null );
                                                                    Log.d( "kfkfkkfkf", "lllll" );
                                                                }

                                                                @Override
                                                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                                }

                                                                @Override
                                                                public void afterTextChanged(Editable editable) {

                                                                }
                                                            } );
                                                            l_ingredienti.setEndIconOnClickListener( new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                    String ingrediente = T_ingredienti.getText().toString();


                                                                    if (arrayList.contains( ingrediente )) {
                                                                        l_ingredienti.setError( getString( R.string.ingredientegiainserito ) );

                                                                    } else if (ingrediente.isEmpty()) {
                                                                        l_ingredienti.setError( getString( R.string.ingredientegiainserito ) );
                                                                    } else {
                                                                        T_ingredienti.setText( null );
                                                                        arraylisttt.add( ingrediente );
                                                                        try_adapter adapter = new try_adapter( HomePage.this, arraylisttt );
                                                                        lista_ingredienti.setAdapter( adapter );
                                                                        adapter.notifyDataSetChanged();
                                                                    }


                                                                }
                                                            } );
                                                            creaArticolo.setOnClickListener( new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {

                                                                    ingredientiString[0] = new String[]{String.valueOf( arrayList )};


                                                                    creaArticolo.setClickable( true );
                                                                    String nome = t_nomeArt.getText().toString();
                                                                    String prezzo = t_prezzoArt.getText().toString();
                                                                    String categoria = spinner.getSelectedItem().toString();
                                                                    l_nomeArt.setError( null );
                                                                    l_prezzoArt.setError( null );
                                                                    if (nome.isEmpty()) {
                                                                        l_nomeArt.setError( getString( R.string.nomearticolononinserito ) );
                                                                    } else if (prezzo.isEmpty()) {
                                                                        l_prezzoArt.setError( getString( R.string.prezzononinserito ) );
                                                                    } else if (categoria.equals( getString( R.string.selezionacategoria ) )) {
                                                                        l_nomeArt.setError( getString( R.string.categorianonselezionata ) );
                                                                    }
                                                                    else {




                                                                        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                                        DocumentReference documentReference = firebaseFirestore.collection( email ).document( nome );


                                                                        ArrayProdotto arrayProdotto = new ArrayProdotto();

                                                                        String[] arr = (String[]) arraylisttt.toArray( new String[arraylisttt.size()] );
                                                                        List<String> listIng = Arrays.asList( arr );
                                                                        arrayProdotto.setIngredienti( listIng );
                                                                        arrayProdotto.setNome( nome );
                                                                        arrayProdotto.setId( nome );
                                                                        arrayProdotto.setCategoria( categoria );
                                                                        arrayProdotto.setEmail( email );
                                                                        arrayProdotto.setPrezzo( prezzo );
                                                                        Calendar cal = Calendar.getInstance();
                                                                        arrayProdotto.setGiorno( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
                                                                        arrayProdotto.setMese( String.valueOf( cal.get( Calendar.MONTH ) ) );
                                                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                                                                        String currentDateandTime = sdf.format(new Date());
                                                                        arrayProdotto.setOra( currentDateandTime );
                                                                        ArrayList<String> uriArray = new ArrayList<>();


                                                                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomePage.this, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title
                                                                        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                                        View viewView = inflater.inflate( R.layout.layout_edit_image, null );
                                                                        dialogBuilder.setView( viewView );
                                                                        AlertDialog alertDialogg = dialogBuilder.create();
                                                                        alertDialogg.show();
                                                                        ImageButton imageButton = viewView.findViewById( R.id.imageButton22 );
                                                                        GridView listPhotoEdit = (GridView) viewView.findViewById( R.id.listEdit );
                                                                        androidx.constraintlayout.widget.Group progressGroup = (androidx.constraintlayout.widget.Group) viewView.findViewById( R.id.groupProgress );
                                                                        androidx.constraintlayout.widget.Group progressImage = (androidx.constraintlayout.widget.Group) viewView.findViewById( R.id.groupImage );
                                                                        List<String> arrayList = new ArrayList<String>();
                                                                        arrayList.clear();
                                                                        Log.d( "pkfdslkf", String.valueOf( listImage ) );
                                                                        if (listImage != null) {
                                                                            arrayList = listImage;
                                                                        }
                                                                        if(!arrayList.contains( "vuoto" )) {
                                                                            arrayList.add( "vuoto" );
                                                                        }

                                                                        adapterr = new adapter_edit_foto( HomePage.this, arrayList );
                                                                        Log.d( "fkldsmf", String.valueOf( arrayList ) );
                                                                        // after passing this array list to our adapter
                                                                        // class we are setting our adapter to our list view.
                                                                        listPhotoEdit.setAdapter( adapterr );
                                                                        List<String> finalArrayList = arrayList;
                                                                        imageButton.setOnClickListener( new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View view) {
                                                                                progressGroup.setVisibility( View.VISIBLE );
                                                                                progressImage.setVisibility( View.GONE );
                                                                                if (adapter_edit_foto.arrayFotoDaEliminare.size() > 0) {
                                                                                    for (int in = 0; in< adapter_edit_foto.arrayFotoDaEliminare.size();in++){


                                                                                        if (Patterns.WEB_URL.matcher( String.valueOf( maintitle.get( i ) ) ).matches()) {
                                                                                            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                                                                                            Log.d( "dfsdfsd", String.valueOf( arrayFotoDaEliminare.get( in )  ) );
                                                                                            StorageReference storageReference = firebaseStorage.getReferenceFromUrl( String.valueOf(  arrayFotoDaEliminare.get( in ) ) );
                                                                                            int finalIn = in;
                                                                                            int finalIn1 = in;
                                                                                            storageReference.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                                                    Log.d( "sdòfds",finalIn + " " + arrayFotoDaEliminare.size() );
                                                                                                    adapter_edit_foto.arrayFotoDaEliminare.remove( 0);
                                                                                                    if(arrayFotoDaEliminare.size() == 0){




                                                                                                        Log.d( "kfmdskmf", String.valueOf( finalArrayList ) );


                                                                                                        FirebaseStorage storage = FirebaseStorage.getInstance();
                                                                                                        StorageReference storageRef = storage.getReference();

                                                                                                        int positionnn = -1;
                                                                                                        positionnn = maintitle.indexOf( "vuoto" );
                                                                                                        if (positionnn == -1) {
                                                                                                            maintitle.remove( positionnn );
                                                                                                        } else {
                                                                                                            maintitle.remove( positionnn );
                                                                                                        }

                                                                                                        for (int i = 0; i < maintitle.size(); i++) {
                                                                                                            Log.d( "kfkfldldl", String.valueOf( maintitle.get( i ) ) );
                                                                                                            Log.d( "kfkfldldl", String.valueOf( maintitle.size() ) );
                                                                                                            if (Patterns.WEB_URL.matcher( String.valueOf( maintitle.get( i ) ) ).matches()) {
                                                                                                                uriArray.add( String.valueOf( maintitle.get( i ) ) );
                                                                                                                immaginiCaricate = uriArray.size();
                                                                                                                Log.d( "lkmdslfmsd",i + " " + maintitle.size() );
                                                                                                                if(maintitle.size() == i +1){
                                                                                                                    String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                                                                                    List<String> listIngg = Arrays.asList( arr );
                                                                                                                    arrayProdotto.setFoto( listIngg );
                                                                                                                    firebaseFirestore.collection( email ).document(nome).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                            DocumentReference documentReference1 = firebaseFirestore.collection( email ).document(nomeee);
                                                                                                                            documentReference1.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                @Override
                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                    firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            Intent intent = new Intent( getApplicationContext(), HomePage.class );
                                                                                                                                            startActivity( intent );
                                                                                                                                            finish();

                                                                                                                                            Log.d( "okfmodkmsfm","lfòsmòòòòòòòòòòòòòò" );
                                                                                                                                            Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                                                    .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                                                                                        @Override
                                                                                                                                                        public void onClick(View view) {
                                                                                                                                                            //portalo al prodotto
                                                                                                                                                        }
                                                                                                                                                    } )

                                                                                                                                                    .show();
                                                                                                                                        }
                                                                                                                                    } );
                                                                                                                                }
                                                                                                                            } );

                                                                                                                        }
                                                                                                                    } );
                                                                                                                }
                                                                                                            }
                                                                                                            else
                                                                                                            {
                                                                                                                Log.d( "okfmodkmsfm", "fkmlkd" );
                                                                                                                StorageReference storagee = storageRef.child( email + "/" + UUID.randomUUID().toString() );
                                                                                                                storagee.putFile( (Uri) maintitle.get( i ) ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                                    @Override
                                                                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                                        Log.d( "okfmodkmsfm", "fkmasdasdasdasdlkd" );
                                                                                                                        storagee.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                                                                                                            @Override
                                                                                                                            public void onSuccess(Uri uri) {
                                                                                                                                uriArray.add( uri.toString() );
                                                                                                                                if (uriArray.size() > immaginiCaricate) {
                                                                                                                                    immaginiCaricate += 1;
                                                                                                                                    Log.d( "okfmodkmsfm", "sizeewwwwwwwwwwww" );

                                                                                                                                }
                                                                                                                                Log.d( "okfmodkmsfm", uriArray.size() + " " + immaginiCaricate + " " + maintitle.size() );

                                                                                                                                if (maintitle.size() == immaginiCaricate) {
                                                                                                                                    Log.d( "okfmodkmsfm", "sizee" );

                                                                                                                                    immaginiCaricate = 1;
                                                                                                                                    FirebaseFirestore firebaseFirestore1 = FirebaseFirestore.getInstance();

                                                                                                                                    String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                                                                                                    List<String> listIngg = Arrays.asList( arr );
                                                                                                                                    arrayProdotto.setFoto( listIngg );

                                                                                                                                    firebaseFirestore.collection( email ).document(nome).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                                @Override
                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                    DocumentReference documentReference1 = firebaseFirestore.collection( email ).document(nomeee);
                                                                                                                                                    documentReference1.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                                        @Override
                                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                            firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                                                @Override
                                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                    Intent intent = new Intent( getApplicationContext(), HomePage.class );
                                                                                                                                                                    startActivity( intent );
                                                                                                                                                                    finish();

                                                                                                                                                                    Log.d( "okfmodkmsfm","lfòsmòòòòòòòòòòòòòò" );
                                                                                                                                                                    Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                                                                            .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                                                                                                                @Override
                                                                                                                                                                                public void onClick(View view) {
                                                                                                                                                                                    //portalo al prodotto
                                                                                                                                                                                }
                                                                                                                                                                            } )

                                                                                                                                                                            .show();
                                                                                                                                                                }
                                                                                                                                                            } );
                                                                                                                                                        }
                                                                                                                                                    } );

                                                                                                                                                }
                                                                                                                                            } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                                                @Override
                                                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                                                    progressGroup.setVisibility( View.GONE );
                                                                                                                                                    progressImage.setVisibility( View.VISIBLE );
                                                                                                                                                    Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                                                                                }
                                                                                                                                            } );
                                                                                                                                        }
                                                                                                                                    } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                                        @Override
                                                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                                                            progressGroup.setVisibility( View.GONE );
                                                                                                                                            progressImage.setVisibility( View.VISIBLE );
                                                                                                                                            Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                                                                        }
                                                                                                                                    } );







                                                                                                                                }
                                                                                                                            }
                                                                                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                            @Override
                                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                                progressGroup.setVisibility( View.GONE );
                                                                                                                                progressImage.setVisibility( View.VISIBLE );
                                                                                                                                Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                                                            }
                                                                                                                        } );
                                                                                                                    }
                                                                                                                } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                                                                                    @Override
                                                                                                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                                                                                                    }
                                                                                                                } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                    @Override
                                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                                        progressGroup.setVisibility( View.GONE );
                                                                                                                        progressImage.setVisibility( View.VISIBLE );
                                                                                                                        Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();

                                                                                                                    }
                                                                                                                } );

                                                                                                            }

                                                                                                        }


                                                                                                    }
                                                                                                    else{
                                                                                                        Log.d( "lksdmflksdmlfmsdlk","Pfdk"   );
                                                                                                    }
                                                                                                }
                                                                                            } )
                                                                                                    .addOnFailureListener( new OnFailureListener() {
                                                                                                        @Override
                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                            progressGroup.setVisibility( View.GONE );
                                                                                                            progressImage.setVisibility( View.VISIBLE );
                                                                                                            Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                                        }
                                                                                                    } );
                                                                                        } else {
                                                                                        }

                                                                                    }


                                                                                }


                                                                                else {


                                                                                    Log.d( "asfdasfasfasfasf", String.valueOf( finalArrayList ) );
                                                                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                                                                    StorageReference storageRef = storage.getReference();

                                                                                    int positionnnt = -1;
                                                                                    positionnnt = maintitle.indexOf( "vuoto" );
                                                                                    if (positionnnt == -1) {
                                                                                        maintitle.remove( positionnnt );
                                                                                    } else {
                                                                                        maintitle.remove( positionnnt );
                                                                                    }
                                                                                    if (maintitle.size() > 0) {
                                                                                        for (int i = 0; i < maintitle.size(); i++) {
                                                                                            Log.d( "asfdasfasfasfasf", String.valueOf( maintitle.get( i ) ) );
                                                                                            Log.d( "asfdasfasfasfasf", String.valueOf( maintitle.size() ) );
                                                                                            if (Patterns.WEB_URL.matcher( String.valueOf( maintitle.get( i ) ) ).matches()) {
                                                                                                uriArray.add( String.valueOf( maintitle.get( i ) ) );
                                                                                                immaginiCaricate = uriArray.size();
                                                                                                Log.d( "lkmdslfmsd", i + " " + maintitle.size() );
                                                                                                if (maintitle.size() == i + 1) {
                                                                                                    String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                                                                    List<String> listIngg = Arrays.asList( arr );
                                                                                                    arrayProdotto.setFoto( listIngg );
                                                                                                    firebaseFirestore.collection( email ).document( nome ).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                            firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                    DocumentReference documentReference1 = firebaseFirestore.collection( email ).document( nomeee );
                                                                                                                    documentReference1.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                            firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                @Override
                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                    Intent intent = new Intent( getApplicationContext(), HomePage.class );
                                                                                                                                    startActivity( intent );
                                                                                                                                    finish();

                                                                                                                                    Log.d( "okfmodkmsfm", "lfòsmòòòòòòòòòòòòòò" );
                                                                                                                                    Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                                            .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                                                                                @Override
                                                                                                                                                public void onClick(View view) {
                                                                                                                                                    //portalo al prodotto
                                                                                                                                                }
                                                                                                                                            } )

                                                                                                                                            .show();
                                                                                                                                }
                                                                                                                            } );
                                                                                                                        }
                                                                                                                    } );

                                                                                                                }
                                                                                                            } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                @Override
                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                    progressGroup.setVisibility( View.GONE );
                                                                                                                    progressImage.setVisibility( View.VISIBLE );
                                                                                                                    Toast.makeText( getApplicationContext(), getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                                                }
                                                                                                            } );
                                                                                                        }
                                                                                                    } ).addOnFailureListener( new OnFailureListener() {
                                                                                                        @Override
                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                            progressGroup.setVisibility( View.GONE );
                                                                                                            progressImage.setVisibility( View.VISIBLE );
                                                                                                            Toast.makeText( getApplicationContext(), getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                                        }
                                                                                                    } );
                                                                                                }

                                                                                            } else {
                                                                                                Log.d( "asfdasfasfasfasf", "fkmlkd" );
                                                                                                StorageReference storagee = storageRef.child( email + "/" + UUID.randomUUID().toString() );
                                                                                                storagee.putFile( (Uri) maintitle.get( i ) ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                        Log.d( "asfdasfasfasfasf", "fkmasdasdasdasdlkd" );
                                                                                                        storagee.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                                                                                            @Override
                                                                                                            public void onSuccess(Uri uri) {
                                                                                                                uriArray.add( uri.toString() );
                                                                                                                if (uriArray.size() > immaginiCaricate) {
                                                                                                                    immaginiCaricate += 1;
                                                                                                                    Log.d( "asfdasfasfasfasf", "sizeewwwwwwwwwwww" );

                                                                                                                }
                                                                                                                Log.d( "asfdasfasfasfasf", uriArray.size() + " " + immaginiCaricate + " " + maintitle.size() );

                                                                                                                if (maintitle.size() == immaginiCaricate) {
                                                                                                                    Log.d( "asfdasfasfasfasf", "sizee" );

                                                                                                                    immaginiCaricate = 1;
                                                                                                                    FirebaseFirestore firebaseFirestore1 = FirebaseFirestore.getInstance();
                                                                                                                    DocumentReference documentReference = firebaseFirestore1.collection( email ).document( nome );

                                                                                                                    String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                                                                                    List<String> listIngg = Arrays.asList( arr );

                                                                                                                    arrayProdotto.setFoto( listIngg );

                                                                                                                    firebaseFirestore.collection( email ).document( nome ).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                            firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                @Override
                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                    DocumentReference documentReference1 = firebaseFirestore.collection( email ).document( nomeee );
                                                                                                                                    documentReference1.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                                                @Override
                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                    Intent intent = new Intent( getApplicationContext(), HomePage.class );
                                                                                                                                                    startActivity( intent );
                                                                                                                                                    finish();

                                                                                                                                                    Log.d( "okfmodkmsfm", "lfòsmòòòòòòòòòòòòòò" );
                                                                                                                                                    Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                                                            .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                                                                                                @Override
                                                                                                                                                                public void onClick(View view) {
                                                                                                                                                                    //portalo al prodotto
                                                                                                                                                                }
                                                                                                                                                            } )

                                                                                                                                                            .show();
                                                                                                                                                }
                                                                                                                                            } );
                                                                                                                                        }
                                                                                                                                    } );

                                                                                                                                }
                                                                                                                            } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                                @Override
                                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                                    progressGroup.setVisibility( View.GONE );
                                                                                                                                    progressImage.setVisibility( View.VISIBLE );
                                                                                                                                    Toast.makeText( getApplicationContext(), getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                                                                }
                                                                                                                            } );
                                                                                                                        }
                                                                                                                    } ).addOnFailureListener( new OnFailureListener() {
                                                                                                                        @Override
                                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                                            progressGroup.setVisibility( View.GONE );
                                                                                                                            progressImage.setVisibility( View.VISIBLE );
                                                                                                                            Toast.makeText( getApplicationContext(), getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                                                        }
                                                                                                                    } );


                                                                                                                }
                                                                                                            }
                                                                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                                                                            @Override
                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                progressGroup.setVisibility( View.GONE );
                                                                                                                progressImage.setVisibility( View.VISIBLE );
                                                                                                                Toast.makeText( getApplicationContext(), getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                                            }
                                                                                                        } );
                                                                                                    }
                                                                                                } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                                                                    @Override
                                                                                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                                                                                    }
                                                                                                } ).addOnFailureListener( new OnFailureListener() {
                                                                                                    @Override
                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                        progressGroup.setVisibility( View.GONE );
                                                                                                        progressImage.setVisibility( View.VISIBLE );
                                                                                                        Toast.makeText( getApplicationContext(), getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();

                                                                                                    }
                                                                                                } );

                                                                                            }
                                                                                        }
                                                                                    }else{
                                                                                        String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                                                        List<String> listIngg = Arrays.asList( arr );

                                                                                        arrayProdotto.setFoto( listIngg );

                                                                                        firebaseFirestore.collection( email ).document(nome).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        Intent intent = new Intent( getApplicationContext(), HomePage.class );

                                                                                                        startActivity( intent );
                                                                                                        finish();


                                                                                                        Log.d( "okfmodkmsfm","lfòsmòòòòòòòòòòòòòò" );
                                                                                                        Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                                                    @Override
                                                                                                                    public void onClick(View view) {
                                                                                                                        //portalo al prodotto
                                                                                                                    }
                                                                                                                } )

                                                                                                                .show();
                                                                                                    }
                                                                                                } ).addOnFailureListener( new OnFailureListener() {
                                                                                                    @Override
                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                        progressGroup.setVisibility( View.GONE );
                                                                                                        progressImage.setVisibility( View.VISIBLE );
                                                                                                        Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                                    }
                                                                                                } );
                                                                                            }
                                                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                                                            @Override
                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                progressGroup.setVisibility( View.GONE );
                                                                                                progressImage.setVisibility( View.VISIBLE );
                                                                                                Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                            }
                                                                                        } );
                                                                                    }
                                                                                }



                                                                            }
                                                                        } );


                                                                        alertDialogg.show();
                                                                    }

                                                                }
                                                            } );






                                                            alertDialog.show();
                                                        }
                                                    } );

                                                }
                                            } );

                                        } else {
                                            // if the snapshot is empty we are displaying a toast message.

                                        }
                                    }
                                } );
                    }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }
                @Override
                public void afterTextChanged(Editable editable) {

                }
            } );

            backList.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    t_search.setText( null );

                    if (l_cocktail.getVisibility() == View.GONE) {
                            l_search.setVisibility( View.GONE );
                            list_ricerca.setVisibility( View.GONE );
                            backList.setVisibility( View.GONE );
                            sfondoList.setVisibility( View.GONE );
                            group.setVisibility( View.VISIBLE );
                            InputMethodManager imm = (InputMethodManager) getSystemService( Context.
                                    INPUT_METHOD_SERVICE );
                            imm.hideSoftInputFromWindow( t_search.getWindowToken(), 0 );
                    }else{
                        backList.setVisibility( View.GONE );
                        l_search.setVisibility( View.GONE );
                        list_ricerca.setVisibility( View.GONE );
                        group.setVisibility( View.VISIBLE );
                        sfondoList.setVisibility( View.GONE );
                            InputMethodManager imm = (InputMethodManager) getSystemService( Context.
                                    INPUT_METHOD_SERVICE );
                            imm.hideSoftInputFromWindow( t_search.getWindowToken(), 0 );
                    }
                }
            } );
    }
}