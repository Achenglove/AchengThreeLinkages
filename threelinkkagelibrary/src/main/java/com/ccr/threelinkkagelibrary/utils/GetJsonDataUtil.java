package com.ccr.threelinkkagelibrary.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 在此写用途
 * TODO<读取Json文件的工具类>
 * @Author: Acheng
 * @Email: 345887272@qq.com
 * @Date: 2017-05-25 17:12
 * @Version: V1.0 <描述当前版本功能>
 */

public class GetJsonDataUtil {


    public String getJson(Context context,String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}

