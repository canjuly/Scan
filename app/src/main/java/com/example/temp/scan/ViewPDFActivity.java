package com.example.temp.scan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPDFActivity extends AppCompatActivity {

    private ImageView titleLeft;
    private TextView titleMid;
    private TextView titleRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        initTitle();
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
        titleMid.setText("文档浏览");
        titleRight.setText("");
    }
}
