package com.pubmania.professionista.Adapter;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pubmania.professionista.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class adapter_edit_foto extends ArrayAdapter<String> {

    private final Activity context;
    public static ArrayList maintitle;
    public static int fotoEliIntiger;



    public adapter_edit_foto(Context context, List<String> maintitle) {
        super(context, R.layout.prova_linear_layout, maintitle);
        // TODO Auto-generated constructor stub

        this.context= (Activity) context;
        this.maintitle= (ArrayList) maintitle;


    }
    ImageView image;
    CircleImageView delete;
    int vuotoInt;
    public static ArrayList<String> arrayFotoDaEliminare = new ArrayList<>();

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.layout_list_edit, null,true);
        image = (ImageView) rowView.findViewById( R.id.roundedImageView );
        delete = (CircleImageView) rowView.findViewById( R.id.circleDelete );
        if(!maintitle.get( position ).equals( "vuoto" )) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver() , Uri.parse(  maintitle.get( position ).toString()));
                Picasso.get().load( String.valueOf( bitmap ) ).into( image );
                Log.d( "kfmdlksmf", String.valueOf( bitmap ) );
            } catch (IOException e) {
                e.printStackTrace();
                Log.d( "kfmdlksmf", e.getMessage() );
                Log.d( "kfmdlksmf", String.valueOf( maintitle.size() ) );

            }
            Log.d( "kfmdlksmf", "fdsfsdf" );

            Picasso.get().load( String.valueOf( maintitle.get( position ) ) ).into( image );
        }else{
            delete.setVisibility( View.GONE );
            vuotoInt = position;
        }
        Log.d( "fkldsmf", String.valueOf( maintitle ) );
        image.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vuotoInt == position){
                    //carica nuove immagini
                    Intent intent = new Intent();
                    intent.setType( "image/*" );
                    intent.putExtra( Intent.EXTRA_ALLOW_MULTIPLE, true );
                    intent.setAction( Intent.ACTION_GET_CONTENT );
                    ((Activity) context).startActivityForResult(Intent.createChooser( intent, "Choose Picture" ), 2 );
                }
            }
        } );
        delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                Log.d( "fsdfo", String.valueOf( maintitle.get( position ) ) );
                                if(Patterns.WEB_URL.matcher(String.valueOf( maintitle.get( position ))).matches()){
                                    arrayFotoDaEliminare.add( maintitle.get( position ).toString() );


                                }


                                maintitle.remove( position );
                                notifyDataSetChanged();
                                fotoEliIntiger +=1;





                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked

                                dialog.dismiss();

                                break;
                        }
                    }
                };
                notifyDataSetChanged();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getString( R.string.seisicurodivolereliminareilprodotto )).setPositiveButton(context.getString( R.string.si ), dialogClickListener)
                        .setNegativeButton(context.getString( R.string.no ), dialogClickListener).show();
            }
        } );
        return rowView;
    };
}
