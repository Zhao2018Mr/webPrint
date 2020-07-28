package com.cmbird.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
 
import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
 
/**
 * 重写HtmlImageGenerator
 * @author zyj
 * @date 2020-7-28 14:26:24
 */
 
public class HtmlImgGenerator {
    private JEditorPane editorPane;
    private static final Dimension DEFAULT_SIZE = new Dimension(800, 800);
 
    public static Map<String, String> types = new HashMap<String, String>();
    static
    {
      types.put("gif", "gif");
      types.put("jpg", "jpg");
      types.put("jpeg", "jpg");
      types.put("png", "png");
    }
    public static String formatForExtension(String extension)
    {
      String type = (String)types.get(extension);
      if (type == null)
        return "png";
 
      return type;
    }
 
    public static String formatForFilename(String fileName) {
      int dotIndex = fileName.lastIndexOf(46);
      if (dotIndex < 0)
        return "png";
 
      String ext = fileName.substring(dotIndex + 1);
      return formatForExtension(ext);
    }
 
    
    public HtmlImgGenerator() {
        editorPane = createJEditorPane();
    }
 
 
 
    public void setSize(Dimension dimension) {
        editorPane.setPreferredSize(dimension);
    }
 
//    public void loadUrl(URL url) {
//        try {
//            editorPane.setPage(url);
//        } catch (IOException e) {
//            throw new RuntimeException(String.format("Exception while loading %s", url), e);
//        }
//    }
 
    public void loadUrl(String url) {
        try {
            editorPane.setPage(url);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while loading %s", url), e);
        }
    }
 
    public void loadHtml(String html) {
        editorPane.setEditable(false);
        editorPane.setText(html);
        editorPane.setContentType("text/html");
        onDocumentLoad();
    }
 
 
    public void saveAsImage(String file) {
        saveAsImage(new File(file));
    }
 
    public void saveAsImage(File file) {
        BufferedImage image = getBufferedImage();
 
        BufferedImage bufferedImageToWrite = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        bufferedImageToWrite.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
 
        final String formatName =  formatForFilename(file.getName());
        try {
            if (!ImageIO.write(bufferedImageToWrite, formatName, file))
                throw new IOException("No formatter for specified file type [" + formatName + "] available");
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while saving '%s' image", file), e);
        }
    }
 
    protected void onDocumentLoad() {
    }
 
    public Dimension getDefaultSize() {
        return DEFAULT_SIZE;
    }
 
    public BufferedImage getBufferedImage() {
        JFrame frame = new JFrame();
        frame.setPreferredSize(editorPane.getPreferredSize());
        frame.setUndecorated(true);
        frame.add(editorPane);
        frame.pack();
 
        Dimension prefSize = frame.getPreferredSize();
        BufferedImage img = new BufferedImage(prefSize.width, prefSize.height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = img.getGraphics();
 
        frame.setVisible(true);
        frame.paint(graphics);
        frame.setVisible(false);
        frame.dispose();
 
        return img;
    }
 
    protected JEditorPane createJEditorPane() {
        final JEditorPane editorPane = new JEditorPane();
        editorPane.setSize(getDefaultSize());
        editorPane.setEditable(false);
          EditorKit  kit = new HTMLEditorKit();
        editorPane.setEditorKitForContentType("text/html", kit);
        editorPane.setContentType("text/html");
     
        return editorPane;
    }
 
    public static void main(String[] args) {
    	/*
        HtmlImgGenerator imageGenerator = new HtmlImgGenerator();
        imageGenerator.setSize(new Dimension(1000, 1000));
        String url = new File("D:/test.html").toURI().toString();
        imageGenerator.loadUrl(url);
        //imageGenerator.loadHtml(String html);//加载本地html
        imageGenerator.getBufferedImage();
        imageGenerator.saveAsImage("D:/aaa.png");
        */
    	String content="<div style=\"width:90%;\">   <h2 style=\"text-align: center;font-weight: bold;\">中标结果通知书</h2>   <input type=\"hidden\" id=\"templateId\" name=\"templateId\" value=\"10001\"/>   <span>致测试单位2（测试）：</span><br>   <span style=\"margin-left:30px\">我方已接受紫罗兰有限公司,海棠有限公司,北京月季有限公司,测试单位2（测试）,社会统一信用代码所提交的中国移动业务流程测试07的标下的包1的投标文件，确定北京月季有限公司为本项目中标人。</span><br>   <span style=\"margin-left:32px\">特此通知。</span><br>   <span style=\"float:right; clear:both;\">招标人/招标代理机构：北京月季有限公司紫罗兰有限公司上海分公司</span><br>   <span style=\"float:right; clear:both;\">2020年01月01日</span><br></div>";
 
    	html2Img("标题标题标题标题",content,"D:/aaa.png");
    }
    
    /**
     * 
     * @Description HTML转Image
     * @param htmText HTML文本字符串
     * @return 希望生成的Image Location
     */
    public static String html2Img(String title,String content, String saveImageLocation){
 

//        StringBuffer buffer = new StringBuffer();


//        	buffer.append(" <html> ");
//        	buffer.append(" <head> ");
//        	buffer.append(" <title></title> ");
//        	buffer.append(" <style> ");
//        	buffer.append(" </style> ");
//        	buffer.append(" </head> ");
//        	buffer.append(" <body style=\" width: 800px;\"  > ");
//        	buffer.append(" 	<div class=\"main2\" style=\"margin-left: 50px;\"> ");
//        	buffer.append(" 			<table class=\"tab2\" width=\"100%\">	 ");
//        	buffer.append(" 			<tr> ");
//        	buffer.append(" 				<td class=\"td_bg2\"  style=\" width:10%;\"  align=\"right\">标题：</td> ");
//        	buffer.append(" 				<td> #bt# </td> ");
//        	buffer.append(" 			</tr> ");
//        	buffer.append(" 			<tr> ");
//        	buffer.append(" 				<td class=\"td_bg2\" align=\"right\"></td> ");
//        	buffer.append(" 				<td> ");
//        	buffer.append(" 					<div id=\"content\" name=\"content\" style=\"width:90%;height:450px; \"> ");
//        	buffer.append(" 						 #nr# ");
//        	buffer.append(" 					</div> ");
//        	buffer.append(" 				</td> ");
//        	buffer.append(" 			</tr> ");
//        	buffer.append(" 		</table> ");
//        	buffer.append(" 	</div> ");
//        	buffer.append(" </body> ");
//        	buffer.append(" </html> ");

        	HtmlImgGenerator imageGenerator = new HtmlImgGenerator();
        try {
//        	 String str=buffer.toString();
//
//        	 str = str.replaceAll("#bt#",title);
//             str = str.replaceAll("#nr#",content);
//
//             //str = str.toString().replaceAll("我方已接受","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我方已接受");
//             //str = str.toString().replaceAll("特此通知","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;特此通知");
//             //str = str.toString().replaceAll("style=\"float:right; clear:both;\">","style=\"float:right; clear:both;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
//
//             str = str.toString().replaceAll("<span style=\"margin-left:30px\">","<span style=\"margin-left:30px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
//             str = str.toString().replaceAll("<span style=\"margin-left:32px\">","<span style=\"margin-left:32px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
//             str = str.toString().replaceAll("<style=\"float:right; clear:both;\">","<style=\"float:right; clear:both;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
//             str = str.toString().replaceAll("style=\"float:right; clear:both;\">","style=\"float:right; clear:both;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
             
             //str = str.toString().replaceAll("<br> ","<br> <br> "); 
             
//             System.out.println(str);
            imageGenerator.loadHtml(content);
            imageGenerator.getBufferedImage();
            imageGenerator.saveAsImage(saveImageLocation);
           
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("将HTML文件转换成图片异常");
        }
        return saveImageLocation;
    }
 
    
}