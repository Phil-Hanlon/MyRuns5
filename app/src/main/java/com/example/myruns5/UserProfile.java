package com.example.myruns5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;


public class UserProfile extends AppCompatActivity {
    private Button b;

    ImageView imageView;
    File imageFile;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        // Get the imageView containing the profile picture
        imageView = (ImageView) findViewById(R.id.profileImage);

        // Check permission for camera, and request if necessary
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);


        if( savedInstanceState != null && savedInstanceState.containsKey("image_uri") ) {

            imageUri = Uri.parse( savedInstanceState.getString("image_uri") );
            imageView.setImageURI(imageUri);
        } else {

            // Orientation-change reveals that this file is not being actually saved for some reason
            // the image's uri is gotten on the first time through,
            // but on orientation change, no such file exists.
            imageFile = new File(getExternalFilesDir(null), "propic.jpg");
            imageUri = Uri.parse(imageFile.toString());
        }




    }


    public void onSaveInstanceState(Bundle bundle) {

        super.onSaveInstanceState(bundle);
        bundle.putString("image_uri", imageUri.toString());
    }


    // IMAGE_CAPTURE intent
    public void photoButtonClicked( View view ) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);


        startActivityForResult(intent, 0);
    }


    // CROP intent
    public void Crop() {

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, 1);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if( resultCode == Activity.RESULT_OK ) {


            // If IMAGE_CAPTURE intent
            if( requestCode == 0 ) {

                Log.d("imageUri", imageUri.toString());



                imageView.setImageURI(imageUri);

                // Crop();
            }


        } else {

            Log.d("onActivityResult", "activity failed.");
        }


    }


    public void saveClicked(View view) {

        finish();
    }


    public void cancelClicked(View view) {

        finish();
    }
}
