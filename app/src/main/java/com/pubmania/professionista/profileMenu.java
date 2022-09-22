package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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