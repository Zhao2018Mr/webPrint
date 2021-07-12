package com.cmbird.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmbird.print.websocket.domain.RequestVo;
import com.cmbird.strategy.PrintStrategy;
import com.cmbird.utils.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * 使用report 报表内容打印
 * @author zyj
 * @date 2021年05月28日 13:16
 */
@Component("report")
public class ReportPrintStrategy implements PrintStrategy {


    private Logger logger= LoggerFactory.getLogger(ReportPrintStrategy.class);



    /**
     * 使用report 报表内容打印
     * @param  requestVo
     * @return java.lang.String
     * @author zyj
     * @date 2021/5/28 13:17
     */
    @Override
    public synchronized AjaxResult print(RequestVo requestVo) throws Exception {
        JSONArray jsonArray=JSON.parseArray(requestVo.getData());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Integer printCount = jsonObject.getInteger("printCount");
            if(printCount==null){
                printCount = 1 ;
            }
            for (Integer integer = 0; integer < printCount; integer++) {
                List<Object> jsonObjects=new ArrayList<>();
                jsonObjects.add(jsonObject);
                PrintUtils.printReport(requestVo.getPrinter(),requestVo.getBaseUrl()+ URLEncoder.encode(requestVo.getReportName(),"utf-8") ,jsonObjects);
            }
        }
        return AjaxResult.success();
    }

}
