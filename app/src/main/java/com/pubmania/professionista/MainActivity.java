package com.pubmania.professionista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pubmania.professionista.FragmentInro.fragment_adapter;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ImageView cursor1,curso2,cursor3;
    TextView textFine;
    ImageButton avanti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}