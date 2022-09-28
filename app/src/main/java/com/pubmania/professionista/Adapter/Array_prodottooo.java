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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

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
import com.pubmania.professionista.ArrayIngredienti;
import com.pubmania.professionista.HomePage;
import com.pubmania.professionista.R;
import com.pubmania.professionista.StringAdapter.ArrayProdotto;
import com.pubmania.professionista.StringAdapter.StringRegistrazione;
import com.squareup.picasso.Picasso;

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
    List<String> arrayListLista;
    int in;
    ImageSlider image_prdotto_slide;
    public static ListView lista_ingredientiiii;
    public static ListView listView;
    int immaginiCaricate = 1;
    ImageButton search;
    TextInputLayout l_search;
    TextInputEditText t_search;
    ArrayList<StringRegistrazione> arrayList;
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
    ArrayList<SlideModel> arrayListProva = new ArrayList<>();

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate( R.layout.layout_prodotti, parent, false);
        }
        arrayListProva.clear();
        notifyDataSetChanged();
        in = 1;
        email = "oliverio.enicola@gmail.com";
        ArrayProdotto dataModal = getItem(position);
        TextView title = listitemView.findViewById(R.id.textView38);
        TextView prezzo = listitemView.findViewById( R.id.textView39 );
        ImageView show = (ImageView) listitemView.findViewById( R.id.imageButton24 );
        title.setText( dataModal.getNome() );
        ImageView listImageListVieww = (ImageView) listitemView.findViewById( R.id.listviewImage );
        prezzo.setText( dataModal.getPrezzo() + ",00 €" );
        TextView ingredienti = listitemView.findViewById( R.id.textView40 );
        listView = listitemView.findViewById( R.id.list_prodotti );

        listView.setFocusable( false );
        listView.setFocusableInTouchMode( false );
        listView.setClickable( false );
        listView.setEnabled( false );
        listView.setClickable( false );
        if (dataModal.getIngredienti().size() == 0) {
            ingredienti.setVisibility( View.GONE );
        }
        else {
            Log.d( "kmdslkfmsd", "prova" );
            Adape_ingredienti adapterr = new Adape_ingredienti( context, dataModal.getIngredienti() );
            listView.setAdapter( adapterr );

            Log.d( "kmfdsklfmlksdm", String.valueOf( adapterr.getCount() ) );
            int totalHeight = 0;
            for (int i = 0; i < listView.getCount(); i++) {
                View listItem = adapterr.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight
                    + (listView.getDividerHeight() * (adapterr.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();

       /*
            if(dataModal.getIngredienti().size() > 8){
                listView.removeAllViews();
                listView.refreshDrawableState();

            }else{
                listView.removeAllViews();
                listView.refreshDrawableState();


            }

             */
            Log.d( "fkms.dmfsdm", String.valueOf( dataModal.getIngredienti().size() ) );

        }
        if(dataModal.getFoto().size() >0){
            Picasso.get().load( dataModal.getFoto().get( 0   ) ).into( listImageListVieww );
        }else{
            listImageListVieww.setVisibility( View.GONE );
        }


        ImageView elimiina = listitemView.findViewById( R.id.imageButton21 );
        ImageView modifica = listitemView.findViewById( R.id.imageButton20 );
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
                lista_ingredientiiii = (ListView) viewView.findViewById( R.id.listCreaIngrediente );
                arrayListLista = new ArrayList<String>();
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
                arrayListLista = dataModal.getIngredienti();
                try_adapter adapter = new try_adapter( context, arrayListLista );
                lista_ingredientiiii.setAdapter( adapter );
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


                        if (arrayListLista.contains( ingrediente )) {
                            l_ingredienti.setError( context.getString( R.string.ingredientegiainserito ) );

                        } else if (ingrediente.isEmpty()) {
                            l_ingredienti.setError( context.getString( R.string.ingredientegiainserito ) );
                        } else {
                            T_ingredienti.setText( null );
                            arrayListLista.add( ingrediente );
                            try_adapter adapter = new try_adapter( context, arrayListLista );
                            lista_ingredientiiii.setAdapter( adapter );
                            adapter.notifyDataSetChanged();
                        }


                    }
                } );
                creaArticolo.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ingredientiString[0] = new String[]{String.valueOf( arrayListLista )};

                        Log.d( "kfmòfkmfòf" , String.valueOf( arrayListLista ) );
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


                                ArrayProdotto arrayProdotto = new ArrayProdotto();

                                String[] arr = arrayListLista.toArray( new String[arrayListLista.size()] );
                                List<String> listIng = Arrays.asList( arr );
                                Log.d( "kfmmkfmkf", String.valueOf( arr ) );
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
                                if(!arrayList.contains( "vuoto" )) {
                                    arrayList.add( "vuoto" );
                                }
                                adapterr = new adapter_edit_foto( context, arrayList );
                                Log.d( "erggergergerge","prova");
                                // after passing this array list to our adapter
                                // class we are setting our adapter to our list view.
                                listPhotoEdit.setAdapter( adapterr );
                                image_prdotto_slide = (ImageSlider) viewView.findViewById( R.id.image_slider );


                                List<String> finalArrayList = arrayList;
                            List<String> finalArrayList1 = arrayList;
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





                                            Log.d( "asfdasfasfasfasf", String.valueOf( finalArrayList1 ) );
                                            FirebaseStorage storage = FirebaseStorage.getInstance();
                                            StorageReference storageRef = storage.getReference();

                                            int positionnnt = -1;
                                            positionnnt = maintitle.indexOf( "vuoto" );
                                            if (positionnnt == -1) {
                                                maintitle.remove( positionnnt );
                                            } else {
                                                maintitle.remove( positionnnt );
                                            }
                                            if(maintitle.size() > 0) {
                                                for (int i = 0; i < maintitle.size(); i++) {
                                                    Log.d( "asfdasfasfasfasf", String.valueOf( maintitle.get( position ) ) );
                                                    Log.d( "asfdasfasfasfasf", String.valueOf( maintitle.size() ) );
                                                    if (Patterns.WEB_URL.matcher( String.valueOf( maintitle.get( i ) ) ).matches()) {
                                                        uriArray.add( String.valueOf( maintitle.get( i ) ) );
                                                        immaginiCaricate = uriArray.size();
                                                        Log.d( "lkmdslfmsd", i + " " + maintitle.size() );
                                                        if (maintitle.size() == i + 1) {
                                                            String[] arr = uriArray.toArray( new String[uriArray.size()] );
                                                            List<String> listIngg = Arrays.asList( arr );
                                                            arrayProdotto.setFoto( listIngg );
                                                            firebaseFirestore.collection( email ).document( dataModal.getNome() ).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Intent intent = new Intent( context, HomePage.class );

                                                                            context.startActivity( intent );
                                                                            ((Activity) context).finish();

                                                                            notifyDataSetChanged();
                                                                            Log.d( "okfmodkmsfm", "lfòsmòòòòòòòòòòòòòò" );
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
                                                                            Toast.makeText( context, context.getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                        }
                                                                    } );
                                                                }
                                                            } ).addOnFailureListener( new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    progressGroup.setVisibility( View.GONE );
                                                                    progressImage.setVisibility( View.VISIBLE );
                                                                    Toast.makeText( context, context.getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
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

                                                                            firebaseFirestore.collection( email ).document( dataModal.getNome() ).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    firebaseFirestore.collection( email ).document( nome ).set( arrayProdotto ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            Intent intent = new Intent( context, HomePage.class );

                                                                                            context.startActivity( intent );
                                                                                            ((Activity) context).finish();

                                                                                            notifyDataSetChanged();
                                                                                            Log.d( "okfmodkmsfm", "lfòsmòòòòòòòòòòòòòò" );
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
                                                                                            Toast.makeText( context, context.getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                        }
                                                                                    } );
                                                                                }
                                                                            } ).addOnFailureListener( new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    progressGroup.setVisibility( View.GONE );
                                                                                    progressImage.setVisibility( View.VISIBLE );
                                                                                    Toast.makeText( context, context.getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                }
                                                                            } );


                                                                        }
                                                                    }
                                                                } ).addOnFailureListener( new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        progressGroup.setVisibility( View.GONE );
                                                                        progressImage.setVisibility( View.VISIBLE );
                                                                        Toast.makeText( context, context.getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
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
                                                                Toast.makeText( context, context.getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();

                                                            }
                                                        } );

                                                    }
                                                }
                                            }
                                            else{
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



                                    }
                                } );


                                alertDialogg.show();
                            }

                    }
                } );






                alertDialog.show();
            }
            });
        show.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String idPost = dataModal.getId();
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( context,R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
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
                                    ArrayIngredienti arraySearchprodotti = new ArrayIngredienti( context, group );
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
                                                                    context. startActivity( new Intent( context, HomePage.class ) );
                                                                    ((Activity)context).finish();
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
                        if (categoriaaa.equals( context.getString( R.string.cocktail ) )) {
                            spinner.setSelection( 1 );
                        } else if (categoriaaa.equals(context. getString( R.string.dolci ) )) {
                            spinner.setSelection( 2 );
                        } else if (categoriaaa.equals(context. getString( R.string.salati ) )) {
                            spinner.setSelection( 3 );
                        } else if (categoriaaa.equals(context. getString( R.string.bevande ) )) {
                            spinner.setSelection( 4 );
                        }
                        arraylisttt = ingredientiList;
                        try_adapter adapter = new try_adapter( context, arraylisttt );
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


                                if (arraylisttt.contains( ingrediente )) {
                                    l_ingredienti.setError( context.getString( R.string.ingredientegiainserito ) );

                                } else if (ingrediente.isEmpty()) {
                                    l_ingredienti.setError( context.getString( R.string.ingredientegiainserito ) );
                                } else {
                                    T_ingredienti.setText( null );
                                    arraylisttt.add( ingrediente );
                                    try_adapter adapter = new try_adapter( context, arraylisttt );
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
                                    l_nomeArt.setError(context. getString( R.string.nomearticolononinserito ) );
                                } else if (prezzo.isEmpty()) {
                                    l_prezzoArt.setError(context. getString( R.string.prezzononinserito ) );
                                } else if (categoria.equals( context.getString( R.string.selezionacategoria ) )) {
                                    l_nomeArt.setError(context. getString( R.string.categorianonselezionata ) );
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


                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( context, R.style.MyDialogTheme );
// ...Irrelevant code for customizing the buttons and title
                                    LayoutInflater inflater = (LayoutInflater)context. getSystemService( Context.LAYOUT_INFLATER_SERVICE );
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
                                                                                                        Intent intent = new Intent( context, HomePage.class );
                                                                                                        context.startActivity( intent );
                                                                                                        ((Activity)context).finish();

                                                                                                        Log.d( "okfmodkmsfm","lfòsmòòòòòòòòòòòòòò" );
                                                                                                        Snackbar.make(((Activity)context).findViewById( android.R.id.content ), context.getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                .setAction(context. getString( R.string.visualizza ), new View.OnClickListener() {
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
                                                                                                                                Intent intent = new Intent( context, HomePage.class );
                                                                                                                                context.startActivity( intent );
                                                                                                                                ((Activity)context).finish();

                                                                                                                                Log.d( "okfmodkmsfm","lfòsmòòòòòòòòòòòòòò" );
                                                                                                                                Snackbar.make(((Activity)context).findViewById( android.R.id.content ),context. getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                                        .setAction(context. getString( R.string.visualizza ), new View.OnClickListener() {
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
                                                                                                Intent intent = new Intent( context, HomePage.class );
                                                                                                context.startActivity( intent );
                                                                                                ((Activity)context). finish();

                                                                                                Log.d( "okfmodkmsfm", "lfòsmòòòòòòòòòòòòòò" );
                                                                                                Snackbar.make(((Activity)context). findViewById( android.R.id.content ), context.getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                        .setAction(context. getString( R.string.visualizza ), new View.OnClickListener() {
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
                                                                                Toast.makeText( context, context.getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                            }
                                                                        } );
                                                                    }
                                                                } ).addOnFailureListener( new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        progressGroup.setVisibility( View.GONE );
                                                                        progressImage.setVisibility( View.VISIBLE );
                                                                        Toast.makeText( context, context.getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
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
                                                                                                                Intent intent = new Intent( context, HomePage.class );
                                                                                                                context.startActivity( intent );
                                                                                                                ((Activity)context). finish();

                                                                                                                Log.d( "okfmodkmsfm", "lfòsmòòòòòòòòòòòòòò" );
                                                                                                                Snackbar.make(((Activity)context). findViewById( android.R.id.content ),context. getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
                                                                                                                        .setAction(context. getString( R.string.visualizza ), new View.OnClickListener() {
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
                                                                                                Toast.makeText( context, context.getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                            }
                                                                                        } );
                                                                                    }
                                                                                } ).addOnFailureListener( new OnFailureListener() {
                                                                                    @Override
                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                        progressGroup.setVisibility( View.GONE );
                                                                                        progressImage.setVisibility( View.VISIBLE );
                                                                                        Toast.makeText( context,context. getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                                    }
                                                                                } );


                                                                            }
                                                                        }
                                                                    } ).addOnFailureListener( new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            progressGroup.setVisibility( View.GONE );
                                                                            progressImage.setVisibility( View.VISIBLE );
                                                                            Toast.makeText( context,context. getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
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
                                                                    Toast.makeText( context,context. getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();

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
                                                                    Intent intent = new Intent( context, HomePage.class );

                                                                    context.startActivity( intent );
                                                                    ((Activity)context).finish();


                                                                    Log.d( "okfmodkmsfm","lfòsmòòòòòòòòòòòòòò" );
                                                                    Snackbar.make(((Activity)context).findViewById( android.R.id.content ),context. getString( R.string.Prodottocreatoconsuccesso ), Snackbar.LENGTH_SHORT )
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

        return listitemView;
    }
    public static adapter_edit_foto adapterr;

    @Override
    public int getCount() {
        return list.size();
    }
}