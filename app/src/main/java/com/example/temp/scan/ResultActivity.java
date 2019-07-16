package com.example.temp.scan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private ImageView resultPicture;
    private Button resultIdentify;
    private Button resultPDF;
    private Bitmap bitmap;
    private ImageView titleLeft;
    private TextView titleMid;
    private TextView titleRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultPicture = (ImageView) findViewById(R.id.result_picture);
        resultIdentify = (Button) findViewById(R.id.result_identify);
        resultPDF = (Button) findViewById(R.id.result_pdf);

        bitmap = NowBitmap.getBitmap();
        resultPicture.setImageBitmap(bitmap);
        initTitle();

        resultIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NowBitmap.setBitmap(bitmap);
                Intent intent = new Intent(ResultActivity.this,TextActivity.class);
                startActivity(intent);
                finish();
            }
        });

        resultPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToPdf();
                finish();
            }
        });


    }

    public void initTitle() {
        titleLeft = (ImageView) findViewById(R.id.title_left);
        titleMid = (TextView) findViewById(R.id.title_mid);
        titleRight = (TextView) findViewById(R.id.title_right);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleMid.setText("图像结果");
        titleRight.setText("");
    }

    public void ToPdf(){

        Toast.makeText(ResultActivity.this,"已保存为PDF", Toast.LENGTH_SHORT).show();
    }

}
