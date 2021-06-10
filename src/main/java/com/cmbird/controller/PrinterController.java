package com.cmbird.controller;

import com.alibaba.fastjson.JSON;
import com.cmbird.factory.FactoryForPrintStrategy;
import com.cmbird.print.websocket.domain.RequestVo;
import com.cmbird.utils.AjaxResult;
import com.cmbird.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zyj
 * @date 2021年06月08日 16:50
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("print")
public class PrinterController {

    @Autowired
    private FactoryForPrintStrategy factoryForPrintStrategy;

    /**
     * 获取打印机列表
     * @author zyj
     * @date 2021/6/8 16:50
     * @return java.util.List<java.lang.String>
     */
    @GetMapping("/getPrinterList")
    public List<String> getPrinterList(){
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        List<String> printerList = new ArrayList<>();
        for (PrintService printService : printServices) {
            printerList.add(printService.getName());
        }
        return printerList;
    }

    /**
     * 获取默认打印机
     * @author zyj
     * @date 2021/6/8 16:53
     */
    @GetMapping("/getDefaultPrinter")
    public String getDefaultPrinter(){
        PrintService defaultPrintService= PrintServiceLookup.lookupDefaultPrintService();
        return defaultPrintService.getName();
    }

    /**
     * 获取默认打印机
     * @author zyj
     * @date 2021/6/8 16:53
     */
    @PostMapping("/printReport")
    public AjaxResult printReport(@RequestBody RequestVo requestVo){
        AjaxResult ajaxResult = null;
        try {
            ajaxResult = factoryForPrintStrategy.getStrategy(requestVo.getType()).print(requestVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxResult;
    }
}
