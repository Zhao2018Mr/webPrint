package com.cmbird.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.cmbird.javafx.controller.PrintController;
import com.cmbird.print.websocket.domain.RequestVo;
import com.cmbird.strategy.PrintStrategy;
import com.cmbird.utils.AjaxResult;
import com.cmbird.utils.PrintUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.print.DocFlavor;
import javax.print.PrintException;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * url 方式打印
 *
 * @author zyj
 * @date 2021年05月28日 13:16
 */
@Component("url-image")
public class UrlPrintStrategy implements PrintStrategy {


    private Logger logger= LoggerFactory.getLogger(UrlPrintStrategy.class);

    /**
     * 后缀名
     * @author zyj
     * @date 2021/5/28 13:58
     */
    private static final String SUFFIX = ".jpg";

    /**
     * URL 方式打印
     *
     * @param data list
     * @return java.lang.String
     * @author zyj
     * @date 2021/5/28 13:17
     */
    @Override
    public AjaxResult print(String data) throws Exception {
        List<String> urls = JSON.parseArray(data, String.class);
        for (int i = 0; i < urls.size(); i++) {
           AjaxResult ajaxResult = PrintUtils.JPGPrint(new File(getImg(urls.get(i),i)));
            if (200 != (Integer)ajaxResult.get("code")) {
                return ajaxResult;
            }
        }
        return AjaxResult.success();
    }

    private String getImg(String u,int i) {
        URL url;
        String imagePath= PrintController.temporaryFileStorageDirectoryStatic + System.currentTimeMillis()+i + SUFFIX;
        try {
            url = new URL(u);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream in = conn.getInputStream();
            byte[] data = readInputStream(in);
            File f = new File(imagePath);
            FileOutputStream out = new FileOutputStream(f);
            out.write(data);
            out.close();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return imagePath;
    }

    private byte[] readInputStream(InputStream ins) throws IOException {
        // TODO 自动生成的方法存根
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = ins.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        ins.close();

        return out.toByteArray();
    }

}