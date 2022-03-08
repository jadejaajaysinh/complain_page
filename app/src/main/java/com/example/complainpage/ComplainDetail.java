package com.example.complainpage;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.preference.PreferenceManager;
import android.provider.Browser;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;

public class ComplainDetail extends AppCompatActivity {
    TextInputLayout Textinputlayout;
    AutoCompleteTextView autocompletetextview;
    ImageView complainig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_detail);

        Textinputlayout=findViewById(R.id.til);
        autocompletetextview=findViewById(R.id.act);
        complainig=findViewById(R.id.complainimage);

        String items[]={"Electrician","Plumbing","Common Area","Car Parking","Lift Service","Drainage","Water Leakage","Security","Other"};
        ArrayAdapter<String> itemAdapter=new ArrayAdapter<>(ComplainDetail.this, R.layout.dropdown_item, items);
        autocompletetextview.setAdapter(itemAdapter);

       complainig.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Dexter.withContext(getApplicationContext())
                       .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                       .withListener(new PermissionListener() {
                           @Override
                           public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                               Intent i= new Intent(Intent.ACTION_PICK);
                               i.setType("image/*");
                               startActivityForResult(Intent.createChooser(i,"Browse for image"),1);

                           }

                           @Override
                           public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                           }

                           @Override
                           public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                               permissionToken.continuePermissionRequest();

                           }
                       }).check();
           }
       });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode== 1  && resultCode== RESULT_OK)
        {
            Uri filepath = data.getData();
            try
            {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                complainig.setImageBitmap(bitmap);
            }
            catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
