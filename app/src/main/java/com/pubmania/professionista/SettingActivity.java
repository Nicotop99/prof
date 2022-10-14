package com.pubmania.professionista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_setting );
        setNotifiche();
    }

    ConstraintLayout l_notifiche;
    private void setNotifiche() {
        l_notifiche = (ConstraintLayout) findViewById(R.id.constraintLayout6);
        l_notifiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),gestione_notifiche.class));
            }
        });
    }


}