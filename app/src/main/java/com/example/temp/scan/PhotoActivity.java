package com.example.temp.scan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralBasicParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.WordSimple;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import me.pqpo.smartcropperlib.view.CropImageView;

public class PhotoActivity extends AppCompatActivity {


    public static final int TAKE_PHOTO = 1;



    private CropImageView picture;
    private Button takePhoto;
    private Button completePhoto;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        takePhoto = (Button) findViewById(R.id.photo_button);
        completePhoto = (Button) findViewById(R.id.photo_complete);
        picture = (CropImageView) findViewById(R.id.photo_image);

        Intent intent = getIntent();
        Boolean flag = intent.getBooleanExtra("photo_flag",true);

        if(flag == true) {
            File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT >= 24) {
                imageUri = FileProvider.getUriForFile(PhotoActivity.this,
                        "com.example.temp.scan",outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }
            getPermission();
        } else {
            picture.setImageToCrop(NowBitmap.getBitmap());

        }


        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              openCameraForResult();
            }
        });
        completePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Bitmap crop = picture.crop();
            Log.d("PhotoActivity","123");
            NowBitmap.setBitmap(crop);
            Intent intent = new Intent(PhotoActivity.this, ResultActivity.class);
            startActivity(intent);
            finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                        picture.setImageBitmap(bitmap);
                        picture.setImageToCrop(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(PhotoActivity.this,
                android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            //先判断有没有权限 ，没有就在这里进行权限的申请
            ActivityCompat.requestPermissions(PhotoActivity.this,
                    new String[] {Manifest.permission.CAMERA},1);
        }else {
            //说明已经获取到摄像头权限了
            Log.d("PhotoActivity","已经获取了权限");
            openCameraForResult();
        }
    }

    private void openCameraForResult() {
        Intent startCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(startCameraIntent,TAKE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCameraForResult();
                } else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }




}
