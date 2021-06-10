package com.cmbird.utils;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zyj
 * @date 2021年05月28日 15:26
 */
public class FileUtils {

    /**
     * 创建文件节哀
     * @author zyj
     * @date 2021/5/28 15:27
     */
    public static void mkdirDirectory(String path){
        File file = new File(path);
        //用来测试此路径名表示的文件或目录是否存在
        if (!file.exists()) {
            file.mkdirs();//创建目录
        }
    }


    /*读取网络文件*/
    public static InputStream getFileInputStream(String path) {
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            return conn.getInputStream();
        } catch (Exception e) {
//            logger.error("读取网络文件异常:"+path);
        }
        return null;
    }
}
