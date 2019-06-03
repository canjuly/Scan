package com.example.temp.scan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TextActivity extends AppCompatActivity {

    private EditText editText;
    private ImageView titleLeft;
    private TextView titleMid;
    private TextView titleRight;
    private Button textButton;

    private Bitmap bitmap;

    private boolean hasGotToken = false;
    private StringBuilder stringBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        editText = (EditText) findViewById(R.id.text_edit_text);
        textButton = (Button) findViewById(R.id.text_button);

        initTitle();
        editText.setText("正在识别，请稍候...");
        initAccessTokenLicenseFile();
        bitmap = NowBitmap.getBitmap();
        String path = saveBitmapFile(bitmap);

        RecognizeService.recAccurateBasic(TextActivity.this,path,
            new RecognizeService.ServiceListener() {
            @Override
            public void onResult(String result) {
                Log.d("TextActivity",result);
                parseJSONWithGSON(result);
                editText.setText(stringBuilder.toString());
            }
        });

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        titleMid.setText("文字修改");
        titleRight.setText("");
    }


    public String saveBitmapFile(Bitmap bitmap) {
        File outputImage = new File(getExternalCacheDir(),"result_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputImage));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = outputImage.getAbsolutePath();
        return path;
    }


    public void initAccessTokenLicenseFile() {
        OCR.getInstance(TextActivity.this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                final String token = result.getAccessToken();
                hasGotToken = true;
                Log.d("TextActivity", "TextActivity onResult()" + token);
            }

            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                error.printStackTrace();
            }
        }, "aip.license", getApplicationContext());
    }

    public void parseJSONWithGSON(String jsonData) {
        stringBuilder = new StringBuilder();
        Gson gson = new Gson();
        App app = gson.fromJson(jsonData, App.class);
        Log.d("TextActivity", "log_id is " + app.getLog_id());
        Log.d("TextActivity", "direction is " + app.getDirection());
        Log.d("TextActivity", "words_result_num is " + app.getWords_result_num());
        List<App.WordsResultBean> appList = app.getWords_result();
        for (App.WordsResultBean wordsResultBean : appList) {
            Log.d("TextActivity", "word is " + wordsResultBean.getWords());
            stringBuilder.append(wordsResultBean.getWords());
            stringBuilder.append("\n");
        }
    }
}
