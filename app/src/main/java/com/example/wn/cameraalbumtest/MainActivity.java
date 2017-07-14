package com.example.wn.cameraalbumtest;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final int TAKE_PHOTO=1;

    private ImageView photo;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button takePhoto = (Button) findViewById(R.id.take_photo_btn);
        photo= (ImageView) findViewById(R.id.photo_iv);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File imageFile=new File(getExternalCacheDir(),"my_image");
                if(imageFile.exists()){
                    imageFile.delete();
                }
                try {
                    imageFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT>=24){
                    imageUri=FileProvider.getUriForFile(MainActivity.this,"com.example.wn.cameraalbumtest",imageFile);
                }
                else {
                    imageUri=Uri.fromFile(imageFile);
                }
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(RESULT_OK==resultCode){
                    //Bitmap bitmap=null;
                    //try {
                    //    bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    //} catch (FileNotFoundException e) {
                    //    e.printStackTrace();
                    //}
                    //photo.setImageBitmap(bitmap);
                    Glide.with(MainActivity.this).load(imageUri).into(photo);
                }
                break;
            default:break;
        }
    }
}
