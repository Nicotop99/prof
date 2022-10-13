package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pubmania.professionista.StringAdapter.StringRegistrazione;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registrazione extends AppCompatActivity {


    TextInputLayout l_email, l_confEmail, l_pass, l_confPass, l_nome, l_cognome, l_cellulare;
    TextInputEditText t_email, t_confEmail, t_pass, t_confPass, t_nome, t_cognome, t_cellulare;
    ImageButton registrati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registrazione );
        setId();
        setRegistrati();
        setEdittext();
        getToken();
    }
    String token;

    void getToken() {
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
                        Log.d("fndlsjfl", token);


                    }
                });
    }
    private void setEdittext() {
    }

    FirebaseAuth auth;
    private void setRegistrati() {
        registrati.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l_email.setError( null );
                l_confEmail.setError( null );
                l_pass.setError( null );
                l_confPass.setError( null );
                l_nome.setError( null );
                l_cognome.setError( null );
                l_cellulare.setError( null );
                String email = t_email.getText().toString();
                String confEmail = t_confEmail.getText().toString();
                String pass = t_pass.getText().toString();
                String confPass = t_confPass.getText().toString();
                String nome = t_nome.getText().toString();
                String cognome = t_cognome.getText().toString();
                String cellulare = t_cellulare.getText().toString();
                if(!email.equals( confEmail )){
                    l_email.setError( getString( R.string.leemailnoncoicidono ) );
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    l_email.setError( getString( R.string.indirizzoemailnonvalido ) );
                }else if(email.isEmpty()){
                    l_email.setError( getString( R.string.emailnoninserita ) );
                }else if(confEmail.isEmpty()){
                    l_confEmail.setError( getString( R.string.emailnoninserita ) );
                }else if(pass.isEmpty()){
                    l_pass.setError( getString( R.string.passwordnoninserita ) );
                }else if(confPass.isEmpty()){
                    l_confPass.setError( getString( R.string.passwordnoninserita ) );
                }else if(pass.length() <= 7){
                    l_pass.setError( getString( R.string.passwordalmeno8caratteri ) );
                }else if(!pass.equals( confPass )){
                    l_confPass.setError(getString(  R.string.lepasswordnoncoicidono) );
                }else if(nome.isEmpty()){
                    l_nome.setError( getString( R.string.nomenoninserito ) );
                } else if (cognome.isEmpty()) {
                    l_cognome.setError( getString( R.string.cognomenoninserito ) );
                }else if(cellulare.isEmpty()){
                    l_cellulare.setError( getString( R.string.cellularenoninserito ) );
                }else if(cellulare.length() < 8 && cellulare.length() > 11){
                    l_cellulare.setError( getString( R.string.cellnonvalido ) );
                }else{
                    registrati.setClickable( false );

                    auth = FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword( email,pass ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            FirebaseUser user = auth.getCurrentUser();

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                StringRegistrazione stringRegistrazione = new StringRegistrazione();
                                                stringRegistrazione.setCognome( cognome );
                                                stringRegistrazione.setToken(token);

                                                stringRegistrazione.setFollower( "0" );
                                                stringRegistrazione.setFotoProfilo( "https://firebasestorage.googleapis.com/v0/b/pubmania-404db.appspot.com/o/download%20(5).jfif?alt=media&token=9fbd065e-5e01-445c-a9fe-60a02bbbfcca" );
                                                stringRegistrazione.setEmail( email );
                                                stringRegistrazione.setNome( nome );
                                                stringRegistrazione.setNumero( cellulare );
                                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                firebaseFirestore.collection( "Professionisti" ).add( stringRegistrazione )
                                                        .addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                documentReference.update( "id", documentReference.getId() ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Toast.makeText( getApplicationContext(),getApplicationContext().getString( R.string.emaildiconfermainviata ),Toast.LENGTH_LONG ).show();
                                                                        startActivity( new Intent(getApplicationContext(),Login.class) );
                                                                    }
                                                                } ).addOnFailureListener( new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        registrati.setClickable( true );

                                                                        Toast.makeText( getApplicationContext(),getApplicationContext().getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                                    }
                                                                } );

                                                            }
                                                        } ).addOnFailureListener( new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText( getApplicationContext(),getApplicationContext().getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                                                        registrati.setClickable( true );

                                                    }
                                                } );







                                            }
                                        }
                                    });
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( getApplicationContext(),getApplicationContext().getString( R.string.erroreriprovapiutardi ), Toast.LENGTH_LONG ).show();
                            registrati.setClickable( true );

                        }
                    } );
                }



            }
        } );
    }

    private void setId() {
        l_email = (TextInputLayout) findViewById( R.id.inputEmail );
        l_confEmail = (TextInputLayout) findViewById( R.id.layoutConfEmail );
        l_pass = (TextInputLayout) findViewById( R.id.layoutPass );
        l_confPass = (TextInputLayout) findViewById( R.id.layutConfPass );
        l_nome = (TextInputLayout) findViewById( R.id.layourNome );
        l_cognome = (TextInputLayout) findViewById( R.id.layourCognome );
        l_cellulare = (TextInputLayout) findViewById( R.id.layourCellulare );



        t_email = (TextInputEditText) findViewById( R.id.textEmail );
        t_confEmail = (TextInputEditText) findViewById( R.id.textCofnEmail );
        t_pass = (TextInputEditText) findViewById( R.id.textpass );
        t_confPass = (TextInputEditText) findViewById( R.id.textConfPass );
        t_nome = (TextInputEditText) findViewById( R.id.textNome );
        t_cognome = (TextInputEditText) findViewById( R.id.textCognome );
        t_cellulare = (TextInputEditText) findViewById( R.id.textCelliulare );


        registrati = (ImageButton) findViewById( R.id.imageButton4 );
    }
}