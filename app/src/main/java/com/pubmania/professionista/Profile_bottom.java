package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.Manifest;
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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.Result;
import com.pubmania.professionista.Adapter.Adapter_Profile_bottom;
import com.pubmania.professionista.StringAdapter.ArrayPost;
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
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_bottom extends AppCompatActivity {

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile_bottom );
        email = "oliverio.enicola@gmail.com";
        setCreaPost();
        setGridView();
        setMenuBasso();
        setTopActivity();
        changeImageProfile();
    }
    ImageButton editPhotoProfile;
    ImageView imageView;
    ImageButton salvaChangeFoto;
    String idEmail;
    ImageButton nuovaFoto;
    TextView salvaChangeFotoText;
    ConstraintLayout ccreafoto;
    private void changeImageProfile() {
        editPhotoProfile = (ImageButton) findViewById( R.id.imageButton31 );
        editPhotoProfile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( Profile_bottom.this, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title

                LayoutInflater inflater = getLayoutInflater();
                View viewView = inflater.inflate( R.layout.change_image, null );
                dialogBuilder.setView( viewView );
                AlertDialog alertDialog = dialogBuilder.create();
                imageView = (ImageView) viewView.findViewById( R.id.imageView14 ) ;
                Group group = (Group) viewView.findViewById( R.id.groupEditPhotoProfile );
                Group group2 = (Group) viewView.findViewById( R.id.group23 );
                nuovaFoto = (ImageButton) viewView.findViewById( R.id.imageButton32 );
                cFoto = (ConstraintLayout) viewView.findViewById(R.id.cfoto);
                ccreafoto = (ConstraintLayout) viewView.findViewById(R.id.cofotoNuova);
                SharedPreferences sharedPreferences = getSharedPreferences("fotoProfilo",MODE_PRIVATE);
                Glide.with(Profile_bottom.this).load(sharedPreferences.getString("fotoProfilo","null")).into(imageView);


                nuovaFoto.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setType( "image/*" );
                        intent.putExtra( Intent.EXTRA_ALLOW_MULTIPLE, true );
                        intent.setAction( Intent.ACTION_GET_CONTENT );
                        startActivityForResult( Intent.createChooser( intent, "Choose Picture" ), 4 );
                    }
                } );
                salvaChangeFoto = (ImageButton) viewView.findViewById( R.id.imageButton33 ) ;
                salvaChangeFotoText = (TextView) viewView.findViewById( R.id.textView67 );
                salvaChangeFoto.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        group.setVisibility( View.VISIBLE );
                        group2.setVisibility( View.GONE );
                        ccreafoto.setVisibility(View.GONE);
                        salvaChangeFoto.setVisibility( View.GONE );
                        salvaChangeFotoText.setVisibility( View.GONE );
                        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task != null){
                                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                        if(documentSnapshot.getString( "email" ).equals( email )) {
                                            idEmail = documentSnapshot.getId();
                                            FirebaseStorage storage = FirebaseStorage.getInstance();
                                            StorageReference storageReference = storage.getReference();
                                            StorageReference storageReference1 = storageReference.child( email + "/" + UUID.randomUUID().toString()  );
                                            storageReference1.putFile( uriFotoProfilo ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    storageReference1.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(idEmail);

                                                            Glide.with(Profile_bottom.this).load(uri).into(photoProfilo);
                                                            documentReference.update( "fotoProfilo", String.valueOf( uri ) ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    alertDialog.dismiss();
                                                                    FirebaseStorage storagee = FirebaseStorage.getInstance();

                                                                    SharedPreferences sharedPreferences = getSharedPreferences("fotoProfilo",MODE_PRIVATE);

                                                                    StorageReference httpsReference = storagee.getReferenceFromUrl(String.valueOf(uri));
                                                                    try {
                                                                        final File localFile = File.createTempFile("Images", ".jpg");
                                                                        httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                            @Override
                                                                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                                                Log.d("janasnd", localFile.getAbsolutePath());
                                                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                                editor.putString("fotoProfilo", localFile.getAbsolutePath());
                                                                                editor.putString("uriFoto", String.valueOf(uri));
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

                                                                    Snackbar.make( findViewById( android.R.id.content ),getString( R.string.fotoProfiloCambiata ),Snackbar.LENGTH_LONG ).show();
                                                                }
                                                            } ).addOnFailureListener( new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    group.setVisibility( View.GONE );
                                                                    group2.setVisibility( View.VISIBLE );
                                                                    salvaChangeFoto.setVisibility( View.VISIBLE );
                                                                    salvaChangeFotoText.setVisibility( View.VISIBLE );
                                                                    Snackbar.make( findViewById( android.R.id.content ),getString( R.string.erroreriprovapiutardi ),Snackbar.LENGTH_LONG ).show();

                                                                }
                                                            } );
                                                        }
                                                    } ).addOnFailureListener( new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.erroreriprovapiutardi ),Snackbar.LENGTH_LONG ).show();
                                                            group.setVisibility( View.GONE );
                                                            group2.setVisibility( View.VISIBLE );
                                                            salvaChangeFoto.setVisibility( View.VISIBLE );
                                                            salvaChangeFotoText.setVisibility( View.VISIBLE );
                                                        }
                                                    } );
                                                }
                                            } ).addOnFailureListener( new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Snackbar.make( findViewById( android.R.id.content ),getString( R.string.erroreriprovapiutardi ),Snackbar.LENGTH_LONG ).show();
                                                    group.setVisibility( View.GONE );
                                                    group2.setVisibility( View.VISIBLE );
                                                    salvaChangeFoto.setVisibility( View.VISIBLE );
                                                    salvaChangeFotoText.setVisibility( View.VISIBLE );
                                                }
                                            } );
                                        }
                                    }
                                }
                            }
                        } ).addOnFailureListener( new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make( findViewById( android.R.id.content ),getString( R.string.erroreriprovapiutardi ),Snackbar.LENGTH_LONG ).show();

                            }
                        } );;

                    }
                } );



                alertDialog.show();
            }
        } );
    }


    TextView nomePubb;
    TextView followe;
    TextView coupon;
    ImageView rec1,rec2,rec3,rec4,rec5;
    CircleImageView photoProfilo;
    int countMedia;
    int intTotRec;
    int inccc;
    TextView countRec;
    private void setTopActivity() {
        nomePubb = (TextView) findViewById( R.id.textView57 );
        countRec = (TextView) findViewById(R.id.textView61);
        followe = (TextView) findViewById( R.id.textView59 );
        coupon = (TextView) findViewById( R.id.textView63 );
        rec1 = (ImageView) findViewById( R.id.imageView32 );
        rec2 = (ImageView) findViewById( R.id.imageView33 );
        rec3 = (ImageView) findViewById( R.id.imageView34 );
        rec4 = (ImageView) findViewById( R.id.imageView35 );
        rec5 = (ImageView) findViewById( R.id.imageView36 );
        photoProfilo = (CircleImageView) findViewById( R.id.circleImageView );
        SharedPreferences sharedPreferences = getSharedPreferences("fotoProfilo",MODE_PRIVATE);
        Glide.with(Profile_bottom.this).load(sharedPreferences.getString("fotoProfilo","null")).into(photoProfilo);
        if(sharedPreferences.getString("nomePub","null").equals("null")) {
            firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task != null) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            if (documentSnapshot.getString("email").equals(email)) {
                                nomePubb.setText(documentSnapshot.getString("nomeLocale"));
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("nomePub",documentSnapshot.getString("nomeLocale"));
                                editor.commit();

                            }
                        }
                    }
                }
            });
        }else{
            nomePubb.setText(sharedPreferences.getString("nomePub",""));
        }
        firebaseFirestore.collection(email+"follower").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                followe.setText(String.valueOf(task.getResult().size()));
            }
        });
        inccc = 0;
        firebaseFirestore.collection( email+"Post" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task!= null){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString("categoria").equals("Coupon")){
                            inccc+=1;
                        }
                    }
                    coupon.setText(String.valueOf(inccc));

                }
            }
        } );
        firebaseFirestore.collection(email+"Rec").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        countMedia += Integer.parseInt(documentSnapshot.getString("media"));

                    }
                    countRec.setText(task.getResult().size() + " " + getString(R.string.recensioni));
                    int tot = countMedia / task.getResult().size();
                    if(tot == 1){
                        rec1.setImageResource(R.drawable.ballrecensionegiallo);
                        rec2.setImageResource(R.drawable.ballrecensionebianco);
                        rec3.setImageResource(R.drawable.ballrecensionebianco);
                        rec4.setImageResource(R.drawable.ballrecensionebianco);
                        rec5.setImageResource(R.drawable.ballrecensionebianco);
                    }else if(tot == 2){
                        rec1.setImageResource(R.drawable.ballrecensionegiallo);
                        rec2.setImageResource(R.drawable.ballrecensionegiallo);
                        rec3.setImageResource(R.drawable.ballrecensionebianco);
                        rec4.setImageResource(R.drawable.ballrecensionebianco);
                        rec5.setImageResource(R.drawable.ballrecensionebianco);
                    }
                    else if(tot == 3){
                        rec1.setImageResource(R.drawable.ballrecensionegiallo);
                        rec2.setImageResource(R.drawable.ballrecensionegiallo);
                        rec3.setImageResource(R.drawable.ballrecensionegiallo);
                        rec4.setImageResource(R.drawable.ballrecensionebianco);
                        rec5.setImageResource(R.drawable.ballrecensionebianco);
                    }
                    else if(tot == 4){
                        rec1.setImageResource(R.drawable.ballrecensionegiallo);
                        rec2.setImageResource(R.drawable.ballrecensionegiallo);
                        rec3.setImageResource(R.drawable.ballrecensionegiallo);
                        rec4.setImageResource(R.drawable.ballrecensionegiallo);
                        rec5.setImageResource(R.drawable.ballrecensionebianco);
                    }
                    else if(tot == 5){
                        rec1.setImageResource(R.drawable.ballrecensionegiallo);
                        rec2.setImageResource(R.drawable.ballrecensionegiallo);
                        rec3.setImageResource(R.drawable.ballrecensionegiallo);
                        rec4.setImageResource(R.drawable.ballrecensionegiallo);
                        rec5.setImageResource(R.drawable.ballrecensionegiallo);
                    }
                }
            }
        });
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

                if (ContextCompat.checkSelfPermission(Profile_bottom.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //ask for authorisation
                    ActivityCompat.requestPermissions( Profile_bottom.this, new String[]{Manifest.permission.CAMERA}, 50 );
                }
                else {
                    //start your camera


                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( Profile_bottom.this );
// ...Irrelevant code for customizing the buttons and title


                    LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    View viewView = inflater.inflate( R.layout.layout_sca_qrcode, null );

                    dialogBuilder.setView( viewView );

                    codeScannerView = (CodeScannerView) viewView.findViewById( R.id.scanner_view );
                    codeScanner = new CodeScanner( Profile_bottom.this, codeScannerView );
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
                                                                                                                                                        AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Profile_bottom.this, R.style.MyDialogThemeeee );
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
                                                                                                                    AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Profile_bottom.this, R.style.MyDialogThemeeee );
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
                                                                                                                                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Profile_bottom.this, R.style.MyDialogThemeeee );
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
                                                                                    AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Profile_bottom.this, R.style.MyDialogThemeeee );
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
                                                                                AlertDialog.Builder dialogBuilderr = new AlertDialog.Builder( Profile_bottom.this,R.style.MyDialogThemeeee  );
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

        bottomAppBar.setSelectedItemId(R.id.profil_page);



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

    GridView gridView;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    ArrayList<ArrayPost> arrayPost;
    Adapter_Profile_bottom adapter_profile_bottom;
    Group group;
    
    private void setGridView() {

        arrayPost = new ArrayList<>();
        gridView = (GridView) findViewById( R.id.gridProfilo );
        group = (Group) findViewById( R.id.group );
        gridView.setBackgroundColor( Color.TRANSPARENT );
        firebaseFirestore.collection( email+"Post" )

                .orderBy( "pinnato", Query.Direction.DESCENDING )
                .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size() >0){
                    group.setVisibility( View.GONE );
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : list){
                        ArrayPost arr = documentSnapshot.toObject( ArrayPost.class );
                        if(arr.getCategoria().equals( "Post" )) {
                            arrayPost.add( arr );
                            adapter_profile_bottom = new Adapter_Profile_bottom( Profile_bottom.this, arrayPost );
                            gridView.setAdapter( adapter_profile_bottom );

                            gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    TextView textView = (TextView) view.findViewById( R.id.idPost );
                                    String idPost = textView.getText().toString();

                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( Profile_bottom.this, R.style.MyDialogTheme );
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
                                                            Log.d("kjnfdkasndkjna", String.valueOf(s2.getInt("fotoCaricate",00000)));
                                                            if(pin.length > 1) {
                                                                for (int i = 0; i < pin.length; i++) {
                                                                    if (pin[i].equals(uriArr.get(0))) {
                                                                        String fin = s2.getString("pin", "null").replace(uriArr.get(0) + "ì", "");
                                                                        editor.putString("pin", fin);
                                                                    }
                                                                }
                                                            }else{

                                                                editor.putString("pin", "null");

                                                            }
                                                            editor.commit();

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
                                                                                    setGridView();
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
                                            AlertDialog.Builder builder = new AlertDialog.Builder(Profile_bottom.this);
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
                                                        setGridView();
                                                        SharedPreferences s2 = getSharedPreferences("fotoPinnate",MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = s2.edit();
                                                        editor.putString("pin",s2.getString("pin","") +  uriArr.get(0) + "ì");

                                                        editor.commit();
                                                    }
                                                } );
                                            }else{
                                                DocumentReference documentReference = firebaseFirestore.collection( email + "Post" ).document( idPost );
                                                documentReference.update( "pinnato", "no" ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        alertDialog.dismiss();
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
                                                        setGridView();
                                                    }
                                                } );
                                            }
                                        }
                                    } );




                                }
                            } );

                        }

                    }
                }else{
                    gridView.setAdapter( null );
                    group.setVisibility( View.VISIBLE );
                }
            }
        } );

    }

    ImageButton creaPrimoPost;
    private void setCreaPost() {
        creaPrimoPost = (ImageButton) findViewById( R.id.imageButton6 );
        creaPrimoPost.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType( "image/*" );
                intent.putExtra( Intent.EXTRA_ALLOW_MULTIPLE, true );
                intent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( intent, "Choose Picture" ), 1 );
            }
        } );


    }

    List<SlideModel> slideModels = new ArrayList<>();
    List<Uri> imageListPath = new ArrayList<>();
    ArrayList<String> uriArray = new ArrayList<>();
    Uri filePath;
    ImageSlider imageSlider;
    Uri uriFotoProfilo;
    ConstraintLayout cFoto;
    Group loadingGroup,creaPostGroup;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if(resultCode == RESULT_OK){
            if (requestCode == 1) {
                                uriArray.clear();
                slideModels.clear();
                imageListPath.clear();

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( Profile_bottom.this, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title

                LayoutInflater inflater = getLayoutInflater();
                View viewView = inflater.inflate( R.layout.alert_crea_post, null );
                dialogBuilder.setView( viewView );
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                imageSlider = (ImageSlider) viewView.findViewById( R.id.image_slider );
                loadingGroup = (Group) viewView.findViewById(R.id.Loading);
                creaPostGroup = (Group) viewView.findViewById(R.id.creaPostGroup);
                ClipData selectedimg = data.getClipData();
                if (selectedimg != null) {
                    for (int i = 0; i < selectedimg.getItemCount(); i++) {
                        filePath = data.getData();

                        ClipData.Item item = selectedimg.getItemAt( i );
                        Log.d( "kfkdksllsl", String.valueOf( item.getUri() ) );
                        imageListPath.add( item.getUri() );
                        slideModels.add( new SlideModel( String.valueOf( item.getUri() ), null ) );
                        if (i < selectedimg.getItemCount() + 1) {
                            imageSlider.setImageList( slideModels );
                        }
                    }
                } else {
                    imageListPath.add( data.getData() );
                    slideModels.add( new SlideModel( String.valueOf( data.getData() ), null ) );
                    imageSlider.setImageList( slideModels );
                }

                ImageButton creaPostButton = (ImageButton) viewView.findViewById( R.id.imageButton7 );
                TextInputLayout l_desc = (TextInputLayout) viewView.findViewById( R.id.textLayoutNome );
                TextInputEditText t_desc = (TextInputEditText) viewView.findViewById( R.id.textNome );
                Log.d( "lkfmsdlkfm", String.valueOf( imageSlider.getVisibility() ) );

                creaPostButton.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        loadingGroup.setVisibility(View.VISIBLE);
                        creaPostGroup.setVisibility(View.GONE);
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

                                                        SharedPreferences sharedPreferences = getSharedPreferences("fotoPinnate",MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putInt("fotoCaricate",sharedPreferences.getInt("fotoCaricate",0) + 1);
                                                        Log.d("jndjandl", String.valueOf(sharedPreferences.getInt("fotoCaricate",00)));
                                                        editor.commit();
                                                        String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                        List<String> listIngg = Arrays.asList( arr );
                                                        String desc = t_desc.getText().toString();

                                                        firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if(task.isSuccessful()){
                                                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                                        if(documentSnapshot.getString("email").equals(email)){
                                                                            ArrayPost arrayPost = new ArrayPost();
                                                                            if(desc.isEmpty()){
                                                                                arrayPost.setDescrizione( "" );
                                                                            }else{
                                                                                arrayPost.setDescrizione( desc );
                                                                            }

                                                                            arrayPost.setEmail(email);
                                                                            arrayPost.setFotoProfilo(documentSnapshot.getString("fotoProfilo"));
                                                                            arrayPost.setNomeLocale(documentSnapshot.getString("nomeLocale"));
                                                                            arrayPost.setLike( "0" );
                                                                            arrayPost.setCategoria( "Post" );
                                                                            arrayPost.setPinnato( "no" );
                                                                            arrayPost.setFoto( listIngg );
                                                                            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                                            firebaseFirestore.collection( email+ "Post" ).add( arrayPost ).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                                                                                @Override
                                                                                public void onSuccess(DocumentReference documentReference) {

                                                                                    documentReference.update( "id",documentReference.getId() ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void aVoid) {
                                                                                            setGridView();

                                                                                            alertDialog.dismiss();
                                                                                            Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                    .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                                        @Override
                                                                                                        public void onClick(View view) {

                                                                                                            String idPost = documentReference.getId();

                                                                                                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( Profile_bottom.this, R.style.MyDialogTheme );
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
                                                                                                                                    Log.d("kjnfdkasndkjna", String.valueOf(s2.getInt("fotoCaricate",00000)));
                                                                                                                                    if(pin.length > 1) {
                                                                                                                                        for (int i = 0; i < pin.length; i++) {
                                                                                                                                            if (pin[i].equals(uriArr.get(0))) {
                                                                                                                                                String fin = s2.getString("pin", "null").replace(uriArr.get(0) + "ì", "");
                                                                                                                                                editor.putString("pin", fin);
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }else{

                                                                                                                                        editor.putString("pin", "null");

                                                                                                                                    }
                                                                                                                                    editor.commit();

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
                                                                                                                                                            setGridView();
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
                                                                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(Profile_bottom.this);
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
                                                                                                                                setGridView();
                                                                                                                                SharedPreferences s2 = getSharedPreferences("fotoPinnate",MODE_PRIVATE);
                                                                                                                                SharedPreferences.Editor editor = s2.edit();
                                                                                                                                editor.putString("pin",s2.getString("pin","") +  uriArr.get(0) + "ì");

                                                                                                                                editor.commit();
                                                                                                                            }
                                                                                                                        } );
                                                                                                                    }else{
                                                                                                                        DocumentReference documentReference = firebaseFirestore.collection( email + "Post" ).document( idPost );
                                                                                                                        documentReference.update( "pinnato", "no" ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                            @Override
                                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                alertDialog.dismiss();
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
                                                                                                                                setGridView();
                                                                                                                            }
                                                                                                                        } );
                                                                                                                    }
                                                                                                                }
                                                                                                            } );




                                                                                                        }
                                                                                                    } )

                                                                                                    .show();
                                                                                        }
                                                                                    } ).addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                            loadingGroup.setVisibility(View.GONE);
                                                                                            creaPostGroup.setVisibility(View.VISIBLE);
                                                                                        }
                                                                                    });


                                                                                }
                                                                            } ).addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    loadingGroup.setVisibility(View.GONE);
                                                                                    creaPostGroup.setVisibility(View.VISIBLE);
                                                                                }
                                                                            });
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        });



                                                    }
                                                }
                                            } ).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    loadingGroup.setVisibility(View.GONE);
                                                    creaPostGroup.setVisibility(View.VISIBLE);
                                                }
                                            });
                                        }
                                    } ).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            loadingGroup.setVisibility(View.GONE);
                                            creaPostGroup.setVisibility(View.VISIBLE);
                                        }
                                    });
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


                                                firebaseFirestore.collection("Professionisti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                                if(documentSnapshot.getString("email").equals(email)){
                                                                    ArrayPost arrayPost = new ArrayPost();
                                                                    if(desc.isEmpty()){
                                                                        arrayPost.setDescrizione( "" );
                                                                    }else{
                                                                        arrayPost.setDescrizione( desc );
                                                                    }

                                                                    arrayPost.setEmail(email);
                                                                    arrayPost.setFotoProfilo(documentSnapshot.getString("fotoProfilo"));
                                                                    arrayPost.setNomeLocale(documentSnapshot.getString("nomeLocale"));
                                                                    arrayPost.setLike( "0" );
                                                                    arrayPost.setCategoria( "Post" );
                                                                    arrayPost.setPinnato( "no" );
                                                                    arrayPost.setFoto( listIngg );
                                                                    SharedPreferences sharedPreferences = getSharedPreferences("fotoPinnate",MODE_PRIVATE);
                                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                    editor.putInt("fotoCaricate",sharedPreferences.getInt("fotoCaricate",0) + 1);
                                                                    Log.d("jndjandl", String.valueOf(sharedPreferences.getInt("fotoCaricate",00) + 1));
                                                                    editor.commit();
                                                                    arrayPost.setFoto( listIngg );
                                                                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                                    firebaseFirestore.collection( email+ "Post" ).add( arrayPost ).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentReference documentReference) {

                                                                            documentReference.update( "id",documentReference.getId() ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    setGridView();


                                                                                    alertDialog.dismiss();
                                                                                    Snackbar.make( findViewById( android.R.id.content ), getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                            .setAction( getString( R.string.visualizza ), new View.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(View view) {

                                                                                                    String idPost = documentReference.getId();

                                                                                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( Profile_bottom.this, R.style.MyDialogTheme );
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
                                                                                                                            Log.d("kjnfdkasndkjna", String.valueOf(s2.getInt("fotoCaricate",00000)));
                                                                                                                            if(pin.length > 1) {
                                                                                                                                for (int i = 0; i < pin.length; i++) {
                                                                                                                                    if (pin[i].equals(uriArr.get(0))) {
                                                                                                                                        String fin = s2.getString("pin", "null").replace(uriArr.get(0) + "ì", "");
                                                                                                                                        editor.putString("pin", fin);
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }else{

                                                                                                                                editor.putString("pin", "null");

                                                                                                                            }
                                                                                                                            editor.commit();

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
                                                                                                                                                    setGridView();
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
                                                                                                            AlertDialog.Builder builder = new AlertDialog.Builder(Profile_bottom.this);
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
                                                                                                                        setGridView();
                                                                                                                        SharedPreferences s2 = getSharedPreferences("fotoPinnate",MODE_PRIVATE);
                                                                                                                        SharedPreferences.Editor editor = s2.edit();
                                                                                                                        editor.putString("pin",s2.getString("pin","") +  uriArr.get(0) + "ì");

                                                                                                                        editor.commit();
                                                                                                                    }
                                                                                                                } );
                                                                                                            }else{
                                                                                                                DocumentReference documentReference = firebaseFirestore.collection( email + "Post" ).document( idPost );
                                                                                                                documentReference.update( "pinnato", "no" ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                                    @Override
                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                        alertDialog.dismiss();
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
                                                                                                                        setGridView();
                                                                                                                    }
                                                                                                                } );
                                                                                                            }
                                                                                                        }
                                                                                                    } );




                                                                                                }
                                                                                            } )

                                                                                            .show();
                                                                                }
                                                                            } ).addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    loadingGroup.setVisibility(View.GONE);
                                                                                    creaPostGroup.setVisibility(View.VISIBLE);
                                                                                }
                                                                            });
                                                                        }
                                                                    } ).addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            loadingGroup.setVisibility(View.GONE);
                                                                            creaPostGroup.setVisibility(View.VISIBLE);
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        }
                                                    }
                                                });





                                            }
                                        }
                                    } ).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            loadingGroup.setVisibility(View.GONE);
                                            creaPostGroup.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }
                            } ).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    loadingGroup.setVisibility(View.GONE);
                                    creaPostGroup.setVisibility(View.VISIBLE);
                                }
                            });

                        }







                    }
                } );

            }
            else if(requestCode == 4){
                uriFotoProfilo = data.getData();
                Glide.with(Profile_bottom.this).load(data.getData()).into(imageView);
                salvaChangeFoto.setVisibility( View.VISIBLE );
                salvaChangeFotoText.setVisibility( View.VISIBLE );
                cFoto.setVisibility(View.VISIBLE);
                salvaChangeFoto.setImageTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.succesGreen));
                nuovaFoto.setImageTintList(AppCompatResources.getColorStateList(getApplicationContext(), R.color.grigio));

            }
        }

    }
}