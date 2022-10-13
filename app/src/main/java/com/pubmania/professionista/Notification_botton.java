package com.pubmania.professionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pubmania.professionista.Adapter.Notifiche;
import com.pubmania.professionista.StringAdapter.StringNotifiche;

import java.util.ArrayList;
import java.util.List;

public class Notification_botton extends AppCompatActivity {
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_botton);
        email = "oliverio.enicola@gmail.com";

        setListNotification();


    }

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Notifiche notifiche;
    ArrayList<StringNotifiche> arrayList = new ArrayList<>();
    ListView listNotifiche;
    private void setListNotification() {
        Log.d("kfjndkjnf",email+"Notifiche");
        Log.d("kfjndkjnf","oliverio.enicola@gmail.comNotifiche");
        listNotifiche = (ListView) findViewById(R.id.list_notification);
        firebaseFirestore.collection(email+"Notifiche")
                .orderBy("ora", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("jkndkjasnjd", String.valueOf(queryDocumentSnapshots.size()));
                        if(queryDocumentSnapshots.size() > 0){
                            Log.d("jkndkjasnjd","ccccccc");

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : list){
                                StringNotifiche stringNotifiche = documentSnapshot.toObject(StringNotifiche.class);
                                arrayList.add(stringNotifiche);
                                notifiche = new Notifiche(Notification_botton.this,arrayList,email);
                                listNotifiche.setAdapter(notifiche);
                            }
                        }
                    }
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("jnffkjsanf", String.valueOf(task.getResult().size()));
                    }
                });
    }
}