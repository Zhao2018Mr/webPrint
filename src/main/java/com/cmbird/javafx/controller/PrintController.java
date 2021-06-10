package com.cmbird.javafx.controller;


import com.cmbird.utils.DialogUtil;
import com.cmbird.utils.FileUtils;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
