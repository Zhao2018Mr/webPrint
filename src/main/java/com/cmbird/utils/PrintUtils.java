package com.cmbird.utils;

import com.cmbird.javafx.controller.PrintController;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Sides;
import java.awt.print.PrinterJob;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class PrintUtils {

    private static Logger logger= LoggerFactory.getLogger(PrintUtils.class);


    // 传入文件和打印机名称
    public static AjaxResult JPGPrint(File file,String printer) {
        if (file == null) {
            return AjaxResult.error("未找到打印文件，请检查。");
        }
        InputStream fis = null;
        try {
            // 设置打印格式，如果未确定类型，可选择autosense
            DocFlavor flavor = DocFlavor.INPUT_STREAM.JPEG;
            // 设置打印参数
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            aset.add(new Copies(1)); //份数
            //aset.add(MediaSize.ISO.A4); //纸张
            // aset.add(Finishings.STAPLE);//装订
            aset.add(Sides.DUPLEX);//单双面
            // 定位打印服务
            PrintService printService = null;
            if (printer != null) {
                //获得本台电脑连接的所有打印机
                PrintService[] printServices = PrinterJob.lookupPrintServices();

                if(printServices == null || printServices.length == 0) {
                    return AjaxResult.error("未找到可用打印机，请检查。");
                }
                //匹配指定打印机
                for (int i = 0;i < printServices.length; i++) {
                    if (printServices[i].getName().contains(printer)) {
                        printService = printServices[i];
                        break;
                    }
                }
                if(printService==null){
                    return AjaxResult.error("未找到名称为" +  printer + "的打印机，请检查。");
                }
            }
            // 构造待打印的文件流
            fis = new FileInputStream(file);
            Doc doc = new SimpleDoc(fis, flavor, null);
            // 创建打印作业
            DocPrintJob job = printService.createPrintJob();
            try {
                job.print(doc, aset);
            } catch (PrintException e) {
                e.printStackTrace();
                logger.error(TraceUtils.getTrace(e));
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            logger.error(TraceUtils.getTrace(e1));
            return AjaxResult.error("文件未找到，请检查。");
        } finally {
            // 关闭打印的文件流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(TraceUtils.getTrace(e));
                }
            }
            //删除图片
            file.delete();
        }
        return AjaxResult.success();
    }


    /**
     * 打印机
     * @author zyj
     * @date 2021/6/8 17:41
     * @param  printer 打印机
     * @param  reportName 模板名称
     * @param  list 打印的数据
     * @return com.cmbird.utils.AjaxResult
     */
    public static void printReport(String printer,String url, List<Object> list) throws JRException, IOException {
        JasperPrint jasperPrint = JasperFillManager.fillReport(JRLoader.getInputStream(new URL(url)), new HashMap(), new JRBeanCollectionDataSource(list));
        JRAbstractExporter je = new JRPrintServiceExporter();
        jasperPrint.setOrientation(OrientationEnum.PORTRAIT);
        je.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        // 设置指定打印机
        PrintService printerValue = null;
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        // 如果没有传入打印机,则取默认打印机
        if(StringUtils.isEmpty(printer)){
            printer=PrintServiceLookup.lookupDefaultPrintService().getName();
        }
        for (PrintService service : services) {
            if (printer.equals(service.getName())) {
                printerValue = service;
                break;
            }
        }

        je.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printerValue);
        je.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, false);
        je.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, false);
        je.exportReport();
    }



}
