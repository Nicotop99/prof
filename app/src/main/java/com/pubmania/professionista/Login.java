package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        buttonRegistrati();
        loginEmail();
        getAutoLogin();

    }

    private void getAutoLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("emailPassAutoLogin",MODE_PRIVATE);
        if(!sharedPreferences.getString( "email","null" ).equals( "null" )){
            Log.d( "ldldldldl","elaldlfkfkdkkdkdddddddddddddddd"  );
            auth.signInWithEmailAndPassword( sharedPreferences.getString( "email",null ),sharedPreferences.getString( "pass",null ) ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(auth.getCurrentUser().isEmailVerified() == true){
                        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task !=null){

                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                        if (documentSnapshot.getString( "email" ).equals( sharedPreferences.getString( "email",null ) )){
                                             if (documentSnapshot.getString( "partitaIva" ).equals( "" )){
                                                //non ha ancora la partita iva


                                                startActivity( new Intent(getApplicationContext(), CheckPartitaIva.class) );
                                                finish();
                                            }else{


                                                 startActivity( new Intent(getApplicationContext(),HomePage.class) );
                                                 finish();

                                                // effettua login naturalmente
                                            }
                                        }
                                    }

                                }
                            }
                        } );





                    }else{
                        Toast.makeText( getApplicationContext(),getApplicationContext().getString( R.string.sipregadiverificarelinidizzoemail ),Toast.LENGTH_LONG ).show();
                    }



                }
            } );

        }



    }

    ImageButton loginButton;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    TextInputLayout l_email,l_pass;
    TextInputEditText t_email,t_pass;
    Switch restaConnesso;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private void loginEmail() {
        loginButton = (ImageButton) findViewById( R.id.imageButton2 );
        l_email = (TextInputLayout) findViewById( R.id.layoutPass );
        l_pass = (TextInputLayout) findViewById( R.id.textInputLayout2 );
        t_email = (TextInputEditText) findViewById( R.id.textEmail );
        t_pass = (TextInputEditText) findViewById( R.id.textPass );
        restaConnesso = (Switch) findViewById( R.id.switch1 );


        loginButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l_email.setError( null );
                l_pass.setError( null );
                String email = t_email.getText().toString();
                String password = t_pass.getText().toString();
                if(email.isEmpty()){
                    l_email.setError( getString( R.string.emailnoninserita ) );
                }else if(password.isEmpty()){
                    l_pass.setError( getString( R.string.passwordnoninserita ) );
                }else{


                    auth.signInWithEmailAndPassword( email,password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.getResult().getUser().isEmailVerified() == true){
                                if(restaConnesso.isChecked() == true){
                                    SharedPreferences sharedPreferences = getSharedPreferences("emailPassAutoLogin",MODE_PRIVATE);
                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                    myEdit.putString("email", email);
                                    myEdit.putString("pass",password);
                                    myEdit.commit();
                                }




                                firebaseFirestore.collection( "professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task !=null){

                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if (documentSnapshot.getString( "email" ).equals( email )){
                                                    if (documentSnapshot.getString( "partitaIva" ).equals( null ) || documentSnapshot.getString( "partitaIva" ).equals( "" )){
                                                        //non ha ancora la partita iva


                                                        startActivity( new Intent(getApplicationContext(), CheckPartitaIva.class) );
                                                        finish();

                                                    }else{
                                                        startActivity( new Intent(getApplicationContext(),HomePage.class) );
                                                        finish();

                                                        // effettua login naturalmente
                                                    }
                                                }
                                            }

                                        }
                                    }
                                } );





                            }else{
                                Log.d( "ldldldldl","Emainnonverificfdssdfsdfsdfsdfa" );
                                Toast.makeText( getApplicationContext(), getApplicationContext().getString( R.string.sipregadiverificarelinidizzoemail ),Toast.LENGTH_LONG ).show();
                            }



                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(e.getMessage().equals( "The password is invalid or the user does not have a password." )){
                                l_pass.setError( getString( R.string.passworderrata ) );
                            }else if(e.getMessage().equals( "There is no user record corresponding to this identifier. The user may have been deleted." )){
                                l_email.setError( getString( R.string.utentenontrovato ) );
                            }else {
                                Toast.makeText( getApplicationContext(),getApplicationContext().getString( R.string.erroreriprovapiutardi ),Toast.LENGTH_LONG).show();
                            }
                        }
                    } );
                }


            }
        } );


    }



    ImageView registrati;
    private void buttonRegistrati() {
        registrati = (ImageView) findViewById( R.id.imageButton3 );
        registrati.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(getApplicationContext(), Registrazione.class) );
            }
        } );
    }
}