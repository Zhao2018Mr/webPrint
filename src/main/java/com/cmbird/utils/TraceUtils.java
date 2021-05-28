package com.cmbird.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 打印异常的堆栈信息
 */
public class TraceUtils {

    /**
     * 输出异常信息
     * @param t
     * @return
     */
    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
