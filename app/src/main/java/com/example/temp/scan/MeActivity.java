package com.example.temp.scan;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MeActivity extends AppCompatActivity {


    private Button bottomHome;
    private Button bottomPhoto;
    private Button bottomMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        initBottomBar();

    }

    private void initBottomBar () {
        bottomHome = (Button) findViewById(R.id.bottom_home);
        bottomHome.setBackgroundResource(R.drawable.home_bg);
        bottomPhoto = (Button) findViewById(R.id.bottom_photo);
        bottomPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeActivity.this,PhotoActivity.class);
                startActivity(intent);
            }
        });
        bottomMe = (Button) findViewById(R.id.bottom_me);
        bottomMe.setBackgroundResource(R.drawable.me_now_bg);
        bottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }
}
