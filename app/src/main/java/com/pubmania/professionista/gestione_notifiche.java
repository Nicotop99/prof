package com.pubmania.professionista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

public class gestione_notifiche extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestione_notifiche);
        setId();
        setS1();
        sets2();
        setS3();
        sets5();
    }

    private void sets5() {
        s5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    Log.d("jfnkssdnfsj","nfjkldsnf");
                    SharedPreferences sharedPreferences = getSharedPreferences("notifiche",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("s5", "false");
                    myEdit.commit();
                }else{
                    SharedPreferences sharedPreferences = getSharedPreferences("notifiche",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("s5", "true");
                    myEdit.commit();
                }
            }
        });
    }

    private void setS3() {
        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    SharedPreferences sharedPreferences = getSharedPreferences("notifiche",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("s3", "false");
                    myEdit.commit();
                }else{
                    SharedPreferences sharedPreferences = getSharedPreferences("notifiche",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("s3", "true");
                    myEdit.commit();
                }
            }
        });
    }

    private void sets2() {
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    SharedPreferences sharedPreferences = getSharedPreferences("notifiche",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("s2", "false");
                    myEdit.commit();
                }else{
                    SharedPreferences sharedPreferences = getSharedPreferences("notifiche",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("s2", "true");
                    myEdit.commit();
                }
            }
        });
    }

    private void setS1() {
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    SharedPreferences sharedPreferences = getSharedPreferences("notifiche",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("s1", "false");
                    myEdit.commit();
                }else{
                    SharedPreferences sharedPreferences = getSharedPreferences("notifiche",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("s1", "true");
                    myEdit.commit();
                }
            }
        });
    }


    Switch s1,s2,s3,s4,s5;
    private void setId() {
        s1 = (Switch) findViewById(R.id.switch2);
        s2 = (Switch) findViewById(R.id.switch3);
        s3 = (Switch) findViewById(R.id.switch4);
        s4 = (Switch) findViewById(R.id.switch5);
        s5 = (Switch) findViewById(R.id.switch7);
    }
}