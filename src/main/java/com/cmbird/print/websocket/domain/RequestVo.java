package com.cmbird.print.websocket.domain;

import java.util.Map;

/**
 * @author zyj
 * @date 2021年05月28日 13:10
 */
public class RequestVo {

    /**
     * 打印机
     */
    private String printer;

    /**
     * 类型
     */
    private String type;
    /**
     * 数据
     */
    private String data;

    /**
     * 模板文件的网址 不包含文件名
     * 如 http://www.baidu.com/xxx.jasper
     * 此处为 http://www.baidu.com/
     */
    private String baseUrl;

    /**
     * 模板文件的名称 不包含网址
     * 如 http://www.baidu.com/
     * 此处为 xxx.jasper
     */
    private String reportName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
