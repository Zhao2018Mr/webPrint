package com.cmbird.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.cmbird.javafx.controller.PrintController;
import com.cmbird.strategy.PrintStrategy;
import com.cmbird.utils.AjaxResult;
import com.cmbird.utils.HtmlToImageUtils;
import com.cmbird.utils.PrintUtils;
import com.cmbird.utils.TraceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.List;

/**
 * html 方式打印
 * @author zyj
 * @date 2021年05月28日 13:16
 */
@Component("html")
public class HtmlPrintStrategy implements PrintStrategy {

    private Logger logger= LoggerFactory.getLogger(HtmlPrintStrategy.class);

    /**
     * html 方式打印
     * @author zyj
     * @date 2021/5/28 13:17
     * @param
     * @return java.lang.String
     */
    @Override
    public AjaxResult print(String data) {
        List<String> htmls= JSON.parseArray(data,String.class);
        try {
            for (String html : htmls) {
                HtmlToImageUtils.html2Img(html, PrintController.temporaryFileStorageDirectoryStatic);
                AjaxResult ajaxResult = PrintUtils.JPGPrint(new File(PrintController.temporaryFileStorageDirectoryStatic));
                if (200 != (Integer)ajaxResult.get("code")) {
                    return ajaxResult;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(TraceUtils.getTrace(e));
            return AjaxResult.error("HTML转Image错误");
        }
        return AjaxResult.success();
    }
}
