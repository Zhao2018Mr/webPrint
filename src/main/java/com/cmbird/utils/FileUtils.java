package com.cmbird.utils;

import java.io.File;

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
}
