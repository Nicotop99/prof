package com.pubmania.professionista.Adapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pubmania.professionista.HomePage;
import com.pubmania.professionista.R;
import com.pubmania.professionista.StringAdapter.ArrayProdotto;

import java.security.acl.Group;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.pubmania.professionista.Adapter.adapter_edit_foto.arrayFotoDaEliminare;
import static com.pubmania.professionista.Adapter.adapter_edit_foto.maintitle;

public class Array_prodottooo extends ArrayAdapter<ArrayProdotto> {

    // constructor for our list view adapter.
    ArrayList<ArrayProdotto> list;
    Context context;
    public Array_prodottooo(@NonNull Context context, ArrayList<ArrayProdotto> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
        this.context = context;
        this.list = dataModalArrayList;
    }
    String email;
    List<String> arrayList;
    int in;
    ImageSlider image_prdotto_slide;
    public static ListView lista_ingredienti;
    public static ListView listView;
    int immaginiCaricate = 1;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate( R.layout.layout_prodotti, parent, false);
        }
        in = 1;
        email = "oliverio.enicola@gmail.com";
        ArrayProdotto dataModal = getItem(position);
        TextView title = listitemView.findViewById(R.id.textView38);
        TextView prezzo = listitemView.findViewById( R.id.textView39 );
        title.setText( dataModal.getNome() );
        prezzo.setText( dataModal.getPrezzo() + ",00 €" );
        TextView ingredienti = listitemView.findViewById( R.id.textView40 );
        listView = listitemView.findViewById( R.id.list_prodotti );
        listView.setFocusable( false );
        listView.setClickable( false );
        if (dataModal.getIngredienti().size() == 0) {
            ingredienti.setVisibility( View.GONE );
        } else {
            Log.d( "kmdslkfmsd", "prova" );
            Adape_ingredienti adapterr = new Adape_ingredienti( context, dataModal.getIngredienti() );
            listView.setAdapter( adapterr );

            listView.getLayoutParams().height = (dataModal.getIngredienti().size() * 150);
            listView.requestLayout();
        }

        ImageButton elimiina = listitemView.findViewById( R.id.imageButton21 );
        ImageButton modifica = listitemView.findViewById( R.id.imageButton20 );
        elimiina.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                if(dataModal.getFoto().size() >0){
                                for (int i = 0; i<dataModal.getFoto().size(); i++){
                                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                                    StorageReference photoRef = firebaseStorage.getReferenceFromUrl(dataModal.getFoto().get( i ));
                                    photoRef.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            in += 1;
                                            if(in == dataModal.getFoto().size()){
                                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                DocumentReference documentReference = firebaseFirestore.collection( email ).document(dataModal.getNome());
                                                documentReference.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        list.remove( position );
                                                        notifyDataSetChanged();
                                                        dialog.dismiss();
                                                    }
                                                } );
                                            }
                                        }
                                    } );
                                }
                                }else{
                                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                    DocumentReference documentReference = firebaseFirestore.collection( email ).document( dataModal.getNome() );
                                    documentReference.delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            dialog.dismiss();
                                           context. startActivity( new Intent( context, HomePage.class ) );

                                            ((Activity)context).finish();
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

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getString( R.string.seisicurodivolereliminareilprodotto )).setPositiveButton(context.getString( R.string.si ), dialogClickListener)
                        .setNegativeButton(context.getString( R.string.no ), dialogClickListener).show();
            }
        } );
        modifica.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( context, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title

                LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View viewView = inflater.inflate( R.layout.layout_crea_nuovo_prodotto, null );
                TextInputEditText T_ingredienti = (TextInputEditText) viewView.findViewById( R.id.textIngrediente1 );

                TextInputLayout l_ingredienti = (TextInputLayout) viewView.findViewById( R.id.textLayoutingrdiete1 );
                ImageButton creaArticolo = (ImageButton) viewView.findViewById( R.id.imageButton15 );
                lista_ingredienti = (ListView) viewView.findViewById( R.id.listCreaIngrediente );
                arrayList = new ArrayList<String>();
                final String[][] ingredientiString = new String[1][1];
                TextInputLayout l_nomeArt = (TextInputLayout) viewView.findViewById( R.id.textLayoutNome );
                TextInputLayout l_prezzoArt = (TextInputLayout) viewView.findViewById( R.id.textLayoutPrezzo );
                TextInputEditText t_nomeArt = (TextInputEditText) viewView.findViewById( R.id.textNome );
                TextInputEditText t_prezzoArt = (TextInputEditText) viewView.findViewById( R.id.textPrezzo );
                Spinner spinner = (Spinner) viewView.findViewById( R.id.spinner );
                dialogBuilder.setView( viewView );
                AlertDialog alertDialog = dialogBuilder.create();

                t_nomeArt.setText( dataModal.getNome() );
                t_prezzoArt.setText( dataModal.getPrezzo() );
                if (dataModal.getCategoria().equals( context.getString( R.string.cocktail ) )) {
                    spinner.setSelection( 1 );
                } else if (dataModal.getCategoria().equals( context.getString( R.string.dolci ) )) {
                    spinner.setSelection( 2 );
                } else if (dataModal.getCategoria().equals( context.getString( R.string.salati ) )) {
                    spinner.setSelection( 3 );
                } else if (dataModal.getCategoria().equals( context.getString( R.string.bevande ) )) {
                    spinner.setSelection( 4 );
                }
                arrayList = dataModal.getIngredienti();
                try_adapter adapter = new try_adapter( context, arrayList );
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
                            l_ingredienti.setError( context.getString( R.string.ingredientegiainserito ) );

                        } else if (ingrediente.isEmpty()) {
                            l_ingredienti.setError( context.getString( R.string.ingredientegiainserito ) );
                        } else {
                            T_ingredienti.setText( null );
                            arrayList.add( ingrediente );
                            try_adapter adapter = new try_adapter( context, arrayList );
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
                            l_nomeArt.setError( context.getString( R.string.nomearticolononinserito ) );
                        } else if (prezzo.isEmpty()) {
                            l_prezzoArt.setError( context.getString( R.string.prezzononinserito ) );
                        } else if (categoria.equals( context.getString( R.string.selezionacategoria ) )) {
                            l_nomeArt.setError( context.getString( R.string.categorianonselezionata ) );
                        }
                        else {




                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                DocumentReference documentReference = firebaseFirestore.collection( email ).document( dataModal.getNome() );

                                creaArticolo.setClickable( false );
                                ArrayProdotto arrayProdotto = new ArrayProdotto();

                                String[] arr = arrayList.toArray( new String[arrayList.size()] );
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

                                alertDialog.dismiss();
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( context, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title
                                LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
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
                                Log.d( "pkfdslkf", String.valueOf( dataModal.getFoto() ) );
                                if (dataModal.getFoto() != null) {
                                    arrayList = dataModal.getFoto();
                                }
                                arrayList.add( "vuoto" );
                                adapterr = new adapter_edit_foto( context, arrayList );
                                Log.d( "fkldsmf", String.valueOf( arrayList ) );
                                // after passing this array list to our adapter
                                // class we are setting our adapter to our list view.
                                listPhotoEdit.setAdapter( adapterr );
                                image_prdotto_slide = (ImageSlider) viewView.findViewById( R.id.image_slider );


                                List<String> finalArrayList = arrayList;
                                imageButton.setOnClickListener( new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        progressGroup.setVisibility( View.VISIBLE );
                                        progressImage.setVisibility( View.GONE );
                                        if (adapter_edit_foto.arrayFotoDaEliminare.size() > 0) {
                                            for (int in = 0; in< adapter_edit_foto.arrayFotoDaEliminare.size();in++){


                                                if (Patterns.WEB_URL.matcher( String.valueOf( maintitle.get( position ) ) ).matches()) {
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
                                                                    Log.d( "kfkfldldl", String.valueOf( maintitle.get( position ) ) );
                                                                    Log.d( "kfkfldldl", String.valueOf( maintitle.size() ) );
                                                                    if (Patterns.WEB_URL.matcher( String.valueOf( maintitle.get( i ) ) ).matches()) {
                                                                        uriArray.add( String.valueOf( maintitle.get( i ) ) );
                                                                        immaginiCaricate = uriArray.size();
                                                                        Log.d( "lkmdslfmsd",i + " " + maintitle.size() );
                                                                        if(maintitle.size() == i +1){
                                                                            String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                                            List<String> listIngg = Arrays.asList( arr );
                                                                            arrayProdotto.setFoto( listIngg );
                                                                            firebaseFirestore.collection( email ).document(dataModal.getNome()).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            Intent intent = new Intent( context, HomePage.class );
                                                                                            intent.putExtra( "autoClick", "Cocktail" );
                                                                                            context.startActivity( intent );
                                                                                            ((Activity) context).finish();
                                                                                            notifyDataSetChanged();
                                                                                            Log.d( "okfmodkmsfm","lfòsmòòòòòòòòòòòòòò" );
                                                                                            Snackbar.make( ((Activity) context).findViewById( android.R.id.content ), context.getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                    .setAction( context.getString( R.string.visualizza ), new View.OnClickListener() {
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

                                                                                            firebaseFirestore.collection( email ).document(dataModal.getNome()).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                    firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                            Intent intent = new Intent( context, HomePage.class );
                                                                                                            intent.putExtra( "autoClick", "Cocktail" );
                                                                                                            context.startActivity( intent );
                                                                                                            ((Activity) context).finish();

                                                                                                            notifyDataSetChanged();
                                                                                                            Log.d( "okfmodkmsfm","lfòsmòòòòòòòòòòòòòò" );
                                                                                                            Snackbar.make( ((Activity) context).findViewById( android.R.id.content ), context.getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                    .setAction( context.getString( R.string.visualizza ), new View.OnClickListener() {
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
                                                                                                            Toast.makeText( context,context.getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                                        }
                                                                                                    } );
                                                                                                }
                                                                                            } ).addOnFailureListener( new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                    progressGroup.setVisibility( View.GONE );
                                                                                                    progressImage.setVisibility( View.VISIBLE );
                                                                                                    Toast.makeText( context,context.getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                                }
                                                                                            } );







                                                                                        }
                                                                                    }
                                                                                } ).addOnFailureListener( new OnFailureListener() {
                                                                                    @Override
                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                        progressGroup.setVisibility( View.GONE );
                                                                                        progressImage.setVisibility( View.VISIBLE );
                                                                                        Toast.makeText( context,context.getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
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
                                                                                Toast.makeText( context,context.getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();

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
                                                            Toast.makeText( context,context.getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                } else {
                                                }

                                            }


                                        }


                                        else{





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
                                            for (int i = 0; i < maintitle.size(); i++) {
                                                Log.d( "asfdasfasfasfasf", String.valueOf( maintitle.get( position ) ) );
                                                Log.d( "asfdasfasfasfasf", String.valueOf( maintitle.size() ) );
                                                if (Patterns.WEB_URL.matcher( String.valueOf( maintitle.get( i ) ) ).matches()) {
                                                    uriArray.add( String.valueOf( maintitle.get( i ) ) );
                                                    immaginiCaricate = uriArray.size();
                                                    Log.d( "lkmdslfmsd",i + " " + maintitle.size() );
                                                    if(maintitle.size() == i +1){
                                                        String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                        List<String> listIngg = Arrays.asList( arr );
                                                        arrayProdotto.setFoto( listIngg );
                                                        firebaseFirestore.collection( email ).document(dataModal.getNome()).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Intent intent = new Intent( context, HomePage.class );
                                                                        intent.putExtra( "autoClick", "Cocktail" );
                                                                        context.startActivity( intent );
                                                                        ((Activity) context).finish();

                                                                        notifyDataSetChanged();
                                                                        Log.d( "okfmodkmsfm","lfòsmòòòòòòòòòòòòòò" );
                                                                        Snackbar.make( ((Activity) context).findViewById( android.R.id.content ), context.getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                .setAction( context.getString( R.string.visualizza ), new View.OnClickListener() {
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
                                                                        Toast.makeText( context,context.getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                    }
                                                                } );
                                                            }
                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                progressGroup.setVisibility( View.GONE );
                                                                progressImage.setVisibility( View.VISIBLE );
                                                                Toast.makeText( context,context.getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                            }
                                                        } );
                                                    }

                                                }
                                                else {
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

                                                                        firebaseFirestore.collection( email ).document(dataModal.getNome()).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        Intent intent = new Intent( context, HomePage.class );
                                                                                        intent.putExtra( "autoClick", "Cocktail" );
                                                                                        context.startActivity( intent );
                                                                                        ((Activity) context).finish();

                                                                                        notifyDataSetChanged();
                                                                                        Log.d( "okfmodkmsfm","lfòsmòòòòòòòòòòòòòò" );
                                                                                        Snackbar.make( ((Activity) context).findViewById( android.R.id.content ), context.getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                .setAction( context.getString( R.string.visualizza ), new View.OnClickListener() {
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
                                                                                        Toast.makeText( context,context.getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                                    }
                                                                                } );
                                                                            }
                                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                progressGroup.setVisibility( View.GONE );
                                                                                progressImage.setVisibility( View.VISIBLE );
                                                                                Toast.makeText( context,context.getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                            }
                                                                        } );


                                                                    }
                                                                }
                                                            } ).addOnFailureListener( new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    progressGroup.setVisibility( View.GONE );
                                                                    progressImage.setVisibility( View.VISIBLE );
                                                                    Toast.makeText( context,context.getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
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
                                                            Toast.makeText( context,context.getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();

                                                        }
                                                    } );

                                                }
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
            });





        return listitemView;
    }
    public static adapter_edit_foto adapterr;
}