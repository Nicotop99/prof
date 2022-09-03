package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pubmania.professionista.StringAdapter.StringRegistrazione;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CheckPartitaIva extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_check_partita_iva );
        buttonregistrati();



    }

    String url;
    ImageButton completaReg;
    Spinner spinner;
    TextInputEditText t_partitaiva;
    TextInputLayout l_partitaIva;
    private void buttonregistrati() {
        completaReg = (ImageButton) findViewById(   R.id.imageButton5 );
        t_partitaiva = (TextInputEditText) findViewById( R.id.textEmail ) ;
        l_partitaIva = (TextInputLayout) findViewById( R.id.layoutPass );
        spinner = (Spinner) findViewById( R.id.spinner );
        completaReg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l_partitaIva.setError( null );
                String partitaIva = t_partitaiva.getText().toString();
                String paese = spinner.getSelectedItem().toString();
                if(partitaIva.isEmpty()){
                    l_partitaIva.setError( getString( R.string.partitaivanoninserita ) );
                }else if(partitaIva.length() != 11){
                    l_partitaIva.setError( getString( R.string.partitaivanonvalida ) );
                }else if(paese.equals( "Seleziona il paese della tua partita IVA" )){
                    l_partitaIva.setError( getString( R.string.paesenoninserito ) );
                }else{
                    if(paese.equals( "Italia(IT)" )){
                        url = "http://apilayer.net/api/validate?access_key=35321e2c6c42a1f5e7555e70b7032405&vat_number=" + "IT" + partitaIva;

                    }
                    StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String string) {
                            try {
                                JSONObject object = new JSONObject(string);
                                Boolean valid = object.getBoolean( "valid" );
                                if(valid == true){
                                    String company_name = object.getString( "company_name" );
                                    String via = object.getString( "company_address" );
                                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                                    firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task != null){
                                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                    if(documentSnapshot.getString( "email" ).equals( user.getEmail() )){
                                                        DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                        documentReference.update( "partitaIva", partitaIva ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                documentReference.update( "nomeLocale", company_name ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        documentReference.update( "viaLocale",via ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                getCord(via);






                                                                            }
                                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                            }
                                                                        } );
                                                                    }
                                                                } ).addOnFailureListener( new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                                    }
                                                                } );
                                                            }
                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText( getApplicationContext(),getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                                                            }
                                                        } );
                                                    }
                                                }

                                            }
                                        }
                                    } );



                                    //okay


                                }else {
                                    l_partitaIva.setError( getString( R.string.partitaivanonvalida ) );
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                        }
                    });
                    RequestQueue rQueue = Volley.newRequestQueue(CheckPartitaIva.this);
                    rQueue.add(request);
                }
            }
        } );
    }

    private void getCord(String via) {
        Geocoder geocoder = new Geocoder( getApplicationContext(), Locale.getDefault() );
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName( via, 1 );
            if (addresses.size() > 0) {


                double latitude = addresses.get( 0 ).getLatitude();
                double longitude = addresses.get( 0 ).getLongitude();
                Location temp = new Location( LocationManager.GPS_PROVIDER );
                temp.setLongitude( longitude );
                temp.setLatitude( latitude );
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if (user.getEmail().equals(documentSnapshot.getString( "email" ))){
                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                    documentReference.update( "lati", latitude ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            documentReference.update( "longi", longitude ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    startActivity( new Intent(getApplicationContext(), HomePage.class) );
                                                    finish();
                                                }
                                            } );
                                        }
                                    } );
                                }
                            }
                        }
                    }
                } );

            }
        } catch (IOException e) {
            e.printStackTrace();
            getCord( via );
        }

    }
}