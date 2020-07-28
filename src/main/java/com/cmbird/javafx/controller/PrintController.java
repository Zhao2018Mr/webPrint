package com.cmbird.javafx.controller;


import com.cmbird.utils.DialogUtil;
import com.cmbird.utils.LoginConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.print.PrintService;
import java.awt.print.PrinterJob;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 界面的控制器
 */
@Component
public class PrintController implements Initializable {


    @FXML
    Pane pane_login;

    @Autowired
    private ApplicationContext context;

    public static final String PRINT = "print";

    public static final String LOG_STORAGE_PATH_KEY = "logStoragePath";
    public static final String TEMPORARY_FILE_STORAGE_DIRECTORY_KEY = "temporaryFileStorageDirectory";
    public static final String PRINTER_KEY = "printer";



    // 打印机
    public static String printStatic = "";
    // 日志路径
    public static String logStoragePathStatic="";
    //临时文件存放路径
    public static String temporaryFileStorageDirectoryStatic = "";


    @FXML
    private TextField logStoragePath;
    @FXML
    private TextField temporaryFileStorageDirectory;
    @FXML
    private ChoiceBox printer;
    @FXML
    private Button btn_confirm;


    /**
     * 初始化
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 获取打印机
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        for (PrintService printService : printServices) {
            printer.getItems().addAll(printService.getName());
        }
        // 默认第一个
        printer.setValue(printServices[0].getName());
        loadPerferences();

    }

    /**
     * 加载缓存信息
     */
    private void loadPerferences() {
        if (!LoginConfig.pref.node(PRINT).get(LOG_STORAGE_PATH_KEY, "").equalsIgnoreCase("")) {
            logStoragePath.setText(LoginConfig.pref.node(PRINT).get(LOG_STORAGE_PATH_KEY, ""));
        }
        if (!LoginConfig.pref.node(PRINT).get(TEMPORARY_FILE_STORAGE_DIRECTORY_KEY, "").equalsIgnoreCase("")) {
            temporaryFileStorageDirectory.setText(LoginConfig.pref.node(PRINT).get(TEMPORARY_FILE_STORAGE_DIRECTORY_KEY, ""));
        }
        if (!LoginConfig.pref.node(PRINT).get(PRINTER_KEY, "").equalsIgnoreCase("")) {
            printer.setValue(LoginConfig.pref.node(PRINT).get(PRINTER_KEY, ""));
        }
        PrintController.printStatic=printer.getValue().toString();
        PrintController.logStoragePathStatic=logStoragePath.getText();
        PrintController.temporaryFileStorageDirectoryStatic=temporaryFileStorageDirectory.getText();
    }

    /**
     * 保存缓存信息
     */
    private void savePreferences() {
        LoginConfig.pref.node(PRINT).put("logStoragePath", PrintController.logStoragePathStatic);
        LoginConfig.pref.node(PRINT).put("temporaryFileStorageDirectory",PrintController.temporaryFileStorageDirectoryStatic);
        LoginConfig.pref.node(PRINT).put("printer", PrintController.printStatic);
    }

    /**
     * 保存确认
     */
    public void confirm() {
        PrintController.printStatic=printer.getValue().toString();
        PrintController.logStoragePathStatic=logStoragePath.getText();
        PrintController.temporaryFileStorageDirectoryStatic=temporaryFileStorageDirectory.getText();
        savePreferences();
        DialogUtil.showMessage("保存",null,"保存成功");
    }

}
