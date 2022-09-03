package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

public class ProfiloPub extends AppCompatActivity {

    TextInputLayout lnomeLocale,laLunedi,lamartedi,lamercoledi,lagiovedi,lavenerdi,lasabato,ladomenica,lclunedi,lcmartedi,lcmercoledi,lcgiovedi,lcvenerdi,lcsabato,lcdomenica;
    TextInputEditText tnomeLocale,taLunedi,tamartedi,tamercoledi,tagiovedi,tavenerdi,tasabato,tadomenica,tclunedi,tcmartedi,tcmercoledi,tcgiovedi,tcvenerdi,tcsabato,tcdomenica;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profilo_pub );
        email = "oliverio.enicola@gmail.com";
        setid();
        setEditText();
        setClick();
    }

    private void setClick() {
        laLunedi.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "aLunedi", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            taLunedi.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.lunediapriraialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );
        lamartedi.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "aMartedi", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            tamartedi.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.martediapriraialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );
        lamercoledi.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "aMercoledi", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            tamercoledi.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.mmercolediapriraialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );
        lagiovedi.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "aGiovedi", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            tagiovedi.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.giovediapriraialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );
        lavenerdi.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "aVenerdi", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            tavenerdi.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.venerdidiapriraialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );
        lasabato.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "aSabato", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            tasabato.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.sabatodiapriraialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );
        ladomenica.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "aDomenica", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            tadomenica.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.domenicadiapriraialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );




        lclunedi.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "cLunedi", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            tclunedi.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.lunedichidialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );
        lcmartedi.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "cMartedi", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            tcmartedi.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.martedichidialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );
        lcmercoledi.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "cMercoledi", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            tcmercoledi.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.mercoledichidialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );
        lcgiovedi.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "cGiovedi", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            tcgiovedi.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.giovedichidialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );
        lcvenerdi.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "cVenerdi", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            tcvenerdi.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.venerdchidialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );
        lcsabato.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "cSabato", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            tcsabato.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.sanatochidialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );
        lcdomenica.setEndIconOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();


                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ProfiloPub.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                if(documentSnapshot.getString( "email" ).equals( email )){
                                                    DocumentReference documentReference = firebaseFirestore.collection( "Professionisti" ).document(documentSnapshot.getId());
                                                    documentReference.update( "cDomenica", hourOfDay + " : " + minute ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            tcdomenica.setText( hourOfDay + " : " + minute );
                                                            Snackbar.make( findViewById( android.R.id.content ),getString( R.string.domenicachidialleore ) + hourOfDay + " : " + minute ,Snackbar.LENGTH_LONG).show();
                                                        }
                                                    } );
                                                }
                                            }
                                        }
                                    }
                                } );



                            }
                        }, 0, 0, true);
                timePickerDialog.show();
            }
        } );


    }

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private void setEditText() {
        firebaseFirestore.collection( "Professionisti" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getString( "email" ).equals( email )){
                            tnomeLocale.setText( documentSnapshot.getString( "nomeLocale" ) );

                            String aLunedi = documentSnapshot.getString( "aLunedi" );
                            String aMartedi = documentSnapshot.getString( "aMartedi" );
                            String aMercoledi = documentSnapshot.getString( "aMercoledi" );
                            String aGiovedi = documentSnapshot.getString( "aGiovedi" );
                            String aVenerdi = documentSnapshot.getString( "aVenerdi" );
                            String aSabato = documentSnapshot.getString( "aSabato" );
                            String aDomenica = documentSnapshot.getString( "aDomenica" );
                            String cLunedi = documentSnapshot.getString( "cLunedi" );
                            String cMartedi = documentSnapshot.getString( "cMartedi" );
                            String cMercoledi = documentSnapshot.getString( "cMercoledi" );
                            String cGiovedi = documentSnapshot.getString( "cGiovedi" );
                            String cVenerdi = documentSnapshot.getString( "cVenerdi" );
                            String cSabato = documentSnapshot.getString( "cSabato" );
                            String cDomenica = documentSnapshot.getString( "cDomenica" );
                            {
                                if (aLunedi != null) {
                                    taLunedi.setText( aLunedi );
                                }
                                if (aMartedi != null) {
                                    tamartedi.setText( aMartedi );
                                }
                                if (aMercoledi != null) {
                                    tamercoledi.setText( aMercoledi );
                                }
                                if (aGiovedi != null) {
                                    tagiovedi.setText( aGiovedi );
                                }
                                if (aVenerdi != null) {
                                    tavenerdi.setText( aVenerdi );
                                }
                                if (aSabato != null) {
                                    tasabato.setText( aSabato );
                                }
                                if (aDomenica != null) {
                                    tadomenica.setText( aDomenica );
                                }
                                if (cLunedi != null) {
                                    tclunedi.setText( cLunedi );
                                }
                                if (cMartedi != null) {
                                    tcmartedi.setText( cMartedi );
                                }
                                if (cMercoledi != null) {
                                    tcmercoledi.setText( cMercoledi );
                                }
                                if (cGiovedi != null) {
                                    tcgiovedi.setText( cGiovedi );
                                }
                                if (cVenerdi != null) {
                                    tcvenerdi.setText( cVenerdi );
                                }
                                if (cSabato != null) {
                                    tcsabato.setText( cSabato );
                                }
                                if (cDomenica != null) {
                                    tcdomenica.setText( cDomenica );
                                }
                            }































                        }
                    }
                }
            }
        } );
    }

    private void setid() {
        lnomeLocale = (TextInputLayout) findViewById( R.id.inputEmail );
        laLunedi = (TextInputLayout) findViewById( R.id.inputALunedi);
        lamartedi = (TextInputLayout) findViewById( R.id.inputamartedi);
        lamercoledi = (TextInputLayout) findViewById( R.id.inputamercoledi);
        lagiovedi = (TextInputLayout) findViewById( R.id.inputagiovedi);
        lavenerdi = (TextInputLayout) findViewById( R.id.inputavenerdi);
        lasabato = (TextInputLayout) findViewById( R.id.inputasabato);
        ladomenica = (TextInputLayout) findViewById( R.id.inputadomenica);
        lclunedi = (TextInputLayout) findViewById( R.id.inputcLunedi);
        lcmartedi = (TextInputLayout) findViewById( R.id.inputcMartedi);
        lcmercoledi = (TextInputLayout) findViewById( R.id.inputcMercoledi);
        lcgiovedi = (TextInputLayout) findViewById( R.id.inputcGiovedi);
        lcvenerdi = (TextInputLayout) findViewById( R.id.inputcVenerdi);
        lcsabato = (TextInputLayout) findViewById( R.id.inputcSabati);
        lcdomenica = (TextInputLayout) findViewById( R.id.inputcDomenica);
        tnomeLocale = (TextInputEditText) findViewById( R.id.textEmail );
        taLunedi = (TextInputEditText) findViewById( R.id.textalunedi);
        tamartedi = (TextInputEditText) findViewById( R.id.textamartedi);
        tamercoledi = (TextInputEditText) findViewById( R.id.textamercoledi);
        tagiovedi = (TextInputEditText) findViewById( R.id.textagiovedi);
        tavenerdi = (TextInputEditText) findViewById( R.id.textavenerdi);
        tasabato = (TextInputEditText) findViewById( R.id.textasabato);
        tadomenica = (TextInputEditText) findViewById( R.id.textadomenica);
        tclunedi = (TextInputEditText) findViewById( R.id.textcLunedi);
        tcmartedi = (TextInputEditText) findViewById( R.id.textcMartedi);
        tcmercoledi = (TextInputEditText) findViewById( R.id.textcMercoledi);
        tcgiovedi = (TextInputEditText) findViewById( R.id.textcGiovedi);
        tcvenerdi = (TextInputEditText) findViewById( R.id.textcVenerdi);
        tcsabato = (TextInputEditText) findViewById( R.id.textcSabato);
        tcdomenica = (TextInputEditText) findViewById( R.id.textcDomenica);
    }
}