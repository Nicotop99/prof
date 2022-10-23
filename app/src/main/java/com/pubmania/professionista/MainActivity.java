package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pubmania.professionista.FragmentInro.fragment_adapter;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ImageView cursor1,curso2,cursor3;
    TextView textFine;
    ImageButton avanti;
    androidx.constraintlayout.widget.Group g1,g2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        g1 = (androidx.constraintlayout.widget.Group) findViewById(R.id.g1);

        g2 = (androidx.constraintlayout.widget.Group) findViewById(R.id.g2);


        getAutoLogin();
        viewPager = (ViewPager) findViewById( R.id.viewPagerIntro );
        viewPager.setAdapter( new fragment_adapter( getSupportFragmentManager() ) );
        cursor1 = (ImageView) findViewById( R.id.imageView3 );
        curso2 = (ImageView) findViewById( R.id.imageView5 );
        cursor3 = (ImageView) findViewById( R.id.imageView4 );
        textFine = (TextView) findViewById( R.id.textView3 );
        avanti = (ImageButton) findViewById( R.id.imageButton );
        avanti.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int page = viewPager.getCurrentItem();
                if(page == 0){
                    viewPager.setCurrentItem( 1 );
                }else if(page == 1){
                    viewPager.setCurrentItem( 2 );
                }else if(page == 2){
                    // intent
                    startActivity( new Intent(getApplicationContext(), Login.class) );
                }
            }
        } );




        viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 19, getResources().getDisplayMetrics());
                float px47 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 47, getResources().getDisplayMetrics());

                if(position == 0){
                    curso2.setImageResource( R.drawable.tondo_giallo );
                    cursor3.setImageResource( R.drawable.tondo_giallo );
                    cursor1.setImageResource( R.drawable.in_page );
                    cursor1.getLayoutParams().height = (int) px;
                    textFine.setText( getString( R.string.avanti ) );

                    cursor1.getLayoutParams().width = (int) px47;
                }else if(position == 1){
                    cursor1.setImageResource( R.drawable.tondo_giallo );
                    cursor3.setImageResource( R.drawable.tondo_giallo );
                    curso2.setImageResource( R.drawable.in_page );
                    curso2.getLayoutParams().height = (int) px;
                    curso2.getLayoutParams().width = (int) px47;
                    textFine.setText( getString( R.string.Fine ) );
                    textFine.setText( getString( R.string.avanti ) );

                }else if(position == 2){
                    textFine.setText( getString( R.string.Fine ) );
                    curso2.setImageResource( R.drawable.tondo_giallo );
                    cursor1.setImageResource( R.drawable.tondo_giallo );
                    cursor3.setImageResource( R.drawable.in_page );
                    cursor3.getLayoutParams().height = (int) px;
                    cursor3.getLayoutParams().width = (int) px47;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        } );
    }
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private void getAutoLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("emailPassAutoLogin",MODE_PRIVATE);
        if(!sharedPreferences.getString( "email","null" ).equals( "null" )){
            g1.setVisibility(View.GONE);
            g2.setVisibility(View.VISIBLE);
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

}