package com.example.temp.scan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ImageView titleLeft;
    private TextView titleMid;
    private TextView titleRight;

    private List<UsePDF> PDFList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initTitle();
        initPDFs();

        Intent intent = getIntent();
        String data = intent.getStringExtra("search_data");
        Toast.makeText(SearchActivity.this, data, Toast.LENGTH_SHORT).show();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        PDFAdapter adapter = new PDFAdapter(PDFList);
        recyclerView.setAdapter(adapter);

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
        titleMid.setText("搜索结果");
        titleRight.setText("");
    }

    private void initPDFs() {
        UsePDF apple = new UsePDF("Apple", R.drawable.pdf_bg);
        PDFList.add(apple);
        UsePDF banana = new UsePDF("banana", R.drawable.pdf_bg);
        PDFList.add(banana);
    }
}
