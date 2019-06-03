package com.example.temp.scan;

import android.content.Context;
import android.util.Log;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.GeneralBasicParams;
import com.baidu.ocr.sdk.model.GeneralParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.WordSimple;

import java.io.File;

/**
 * Created by temp on 2019/5/16.
 */

public class RecognizeService {
    public interface ServiceListener {
        public void onResult(String result);
    }

    //高精度版
    public static void recAccurateBasic(Context ctx, String filePath, final ServiceListener listener) {
        GeneralParams param = new GeneralParams();
        param.setDetectDirection(true);
        param.setVertexesLocation(true);
        param.setLanguageType(GeneralBasicParams.ENGLISH);
        param.setRecognizeGranularity(GeneralParams.GRANULARITY_SMALL);
        param.setImageFile(new File(filePath));
        Log.d("ResultActivity","123123");

        //这里的recognizeAccurateBasic方法为百度OCR识别的核心方法
        OCR.getInstance(ctx).recognizeAccurateBasic(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                StringBuilder sb = new StringBuilder();
                for (WordSimple wordSimple : result.getWordList()) {
                    WordSimple word = wordSimple;
                    sb.append(word.getWords());
                    sb.append("\n");
                }
                listener.onResult(result.getJsonRes());
            }

            @Override
            public void onError(OCRError error) {
                listener.onResult(error.getMessage());
            }
        });
    }
}
