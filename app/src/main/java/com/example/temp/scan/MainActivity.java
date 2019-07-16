package com.example.temp.scan;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<UsePDF> PDFList = new ArrayList<>();

    private SearchView searchView;
    private Button mainAdd;
    private Button bottomHome;
    private Button bottomPhoto;
    private Button bottomMe;

    public static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBottomBar();
        initPDFs();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        PDFAdapter adapter = new PDFAdapter(PDFList);
        recyclerView.setAdapter(adapter);

        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                intent.putExtra("search_data",query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mainAdd = (Button) findViewById(R.id.main_add);
        mainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.
                        PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this,"you denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
                default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //判断手机版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4以上系统用这个方法
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri,null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(externalContentUri,null,selection,null,null);
        if (cursor!= null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            NowBitmap.setBitmap(bitmap);
            Intent intent = new Intent(MainActivity.this,PhotoActivity.class);
            intent.putExtra("photo_flag",false);
            startActivity(intent);
        } else {
            Toast.makeText(this,"failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private void initBottomBar () {
        bottomHome = (Button) findViewById(R.id.bottom_home);
        bottomHome.setBackgroundResource(R.drawable.home_now__bg);
        bottomPhoto = (Button) findViewById(R.id.bottom_photo);
        bottomPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PhotoActivity.class);
//                Intent intent = new Intent(MainActivity.this,TempActivity.class);
                startActivity(intent);
            }
        });
        bottomMe = (Button) findViewById(R.id.bottom_me);
        bottomMe.setBackgroundResource(R.drawable.me_bg);
        bottomMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    private void initPDFs() {
            UsePDF apple = new UsePDF("一种复杂版面扭曲文档图像快速矫正方法", R.drawable.catch_bg,"在对复杂版面扭曲文档图像进行OCR识别时,识别率较低。针对这类文档图像提出一种基于形态学文本行定位的扭曲校正方法。首先根据形态学特征在复杂版面中定位文本行,区分处理文字区域和非文字区域,利用文本行信息提取文本线;再以文本线为基准利用窗口扫描法进行文字行校正,最终重构图像。实验结果表明,该方法校正效果明显,对于复杂版面的扭曲文档图像有较好的校正效果,校正后识别率大幅度提高。","2019/04/23","韩梅梅");
            PDFList.add(apple);
            UsePDF banana = new UsePDF("文档图像纠偏算法的研究与分析", R.drawable.catch_bg,"提出了一种效率很高的文档图像纠偏算法,即利用文档图像的水平投影值,得到水平投影值的维格纳-威利分布。根据维格纳-威利分布的极值的最大值得到偏斜角度,实验结果验证了算法的有效性。","2019/05/10","李雷");
            PDFList.add(banana);
            UsePDF grape = new UsePDF("变形文档图像的矫正方法研究", R.drawable.catch_bg,"针对文档表面易发生弯曲变形从而影响文档图像识别率这一问题,通过建模恢复文档表面形状,并运用参数化插值的方法实现了变形文档图像的矫正。本文讨论了基本理论及方法,并对算法的设计及实验中开发的平台进行了介绍,实验数据表明本方法可以有效的实现文档图像的矫正。","2015/05/12","张鹏");
            PDFList.add(grape);
    }
}
