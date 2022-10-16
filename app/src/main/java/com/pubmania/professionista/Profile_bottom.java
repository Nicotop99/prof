package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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

import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pubmania.professionista.Adapter.Adapter_Profile_bottom;
import com.pubmania.professionista.StringAdapter.ArrayPost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    TextView salvaChangeFotoText;
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
                ImageButton nuovaFoto = (ImageButton) viewView.findViewById( R.id.imageButton32 );
                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task != null){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                if(documentSnapshot.getString( "email" ).equals( email )) {
                                    Glide.with(Profile_bottom.this).load(documentSnapshot.getString("fotoProfilo")).into(imageView);
                                    idEmail = documentSnapshot.getId();
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
                        salvaChangeFoto.setVisibility( View.GONE );
                        salvaChangeFotoText.setVisibility( View.GONE );

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
                } );



                alertDialog.show();
            }
        } );
    }


    TextView nomePub;
    TextView followe;
    TextView coupon;
    ImageView rec1,rec2,rec3,rec4,rec5;
    CircleImageView photoProfilo;
    int countMedia;
    int intTotRec;
    TextView countRec;
    private void setTopActivity() {
        nomePub = (TextView) findViewById( R.id.textView57 );
        countRec = (TextView) findViewById(R.id.textView61);
        followe = (TextView) findViewById( R.id.textView59 );
        coupon = (TextView) findViewById( R.id.textView63 );
        rec1 = (ImageView) findViewById( R.id.imageView32 );
        rec2 = (ImageView) findViewById( R.id.imageView33 );
        rec3 = (ImageView) findViewById( R.id.imageView34 );
        rec4 = (ImageView) findViewById( R.id.imageView35 );
        rec5 = (ImageView) findViewById( R.id.imageView36 );
        photoProfilo = (CircleImageView) findViewById( R.id.circleImageView );
        firebaseFirestore.collection( "Professionisti").get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task != null){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString( "email" ).equals( email )){
                            nomePub.setText( documentSnapshot.getString( "nomeLocale" ) );
                            followe.setText( documentSnapshot.getString( "follower" ) );
                            Glide.with(Profile_bottom.this).load(documentSnapshot.getString("fotoProfilo")).into(photoProfilo);

                        }
                    }
                }
            }
        } );
        firebaseFirestore.collection( email+"Coupon" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task!= null){
                    coupon.setText( String.valueOf(  task.getResult().size()));

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
    FloatingActionButton floatingActionButton;
    private void setMenuBasso() {
        bottomAppBar = (BottomNavigationView) findViewById( R.id.bottomNavView );
        bottomAppBar.findViewById( R.id.nullable ).setClickable( false );

        floatingActionButton = (FloatingActionButton) findViewById( R.id.floatBotton );
        floatingActionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creaPrimoPost.performClick();
            }
        } );
        bottomAppBar.setSelectedItemId(R.id.profil_page);
        Menu menu = bottomAppBar.getMenu();
        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task != null){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString( "email" ).equals( email )){
                            menu.findItem(R.id.profil_page).setTitle( documentSnapshot.getString( "nome" ) );

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
                }
                return true;
            }
        } );


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
                                                    }
                                                } );
                                            }else{
                                                DocumentReference documentReference = firebaseFirestore.collection( email + "Post" ).document( idPost );
                                                documentReference.update( "pinnato", "no" ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        alertDialog.dismiss();
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
            else if(requestCode == 4){
                uriFotoProfilo = data.getData();

                Glide.with(Profile_bottom.this).load(data.getData()).into(imageView);
                salvaChangeFoto.setVisibility( View.VISIBLE );
                salvaChangeFotoText.setVisibility( View.VISIBLE );
            }
        }

    }
}