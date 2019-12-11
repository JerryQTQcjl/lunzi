package com.borosoft.qyxy.serve;

import com.borosoft.framework.AppConfig;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;

public class Wkhtmltopdf {

    //wkhtmltopdf 在系统中的路径
    private static String toPdfTool = "";

    static{
    	toPdfTool = AppConfig.getConfig().getString("wkhtmltopdf");
    }


    /**
     * html转pdf
     *
     * @param srcPath  html路径，可以是硬盘上的路径，也可以是网络路径
     * @param destPath pdf保存路径
     * @return 转换成功返回true
     */
    public static boolean convert(String srcPath, String destPath) {
        File file = new File(destPath);
        File parent = file.getParentFile();
        //如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        

        StringBuilder cmd = new StringBuilder();
        cmd.append(toPdfTool);
        cmd.append(" ");

        //html路径 即目标网页路径
        cmd.append(" ");
        cmd.append(srcPath);
        cmd.append(" ");
        //pdf保存路径
        cmd.append(destPath);

        boolean result = true;
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将html转换成pdf
     * @param srcPath
     * @param destPath
     * @return
     */
    public static boolean convertByItext(String srcPath, String destPath) {
        Document document = null;
        PdfWriter writer = null;
        File file = new File(destPath);
        File parent = file.getParentFile();
        //如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        boolean result = true;
        try(InputStream inputStream = new FileInputStream(srcPath);
            OutputStream outputStream = new FileOutputStream(destPath);){
            document = new Document();
            writer = PdfWriter.getInstance(document, outputStream);
            // 添加水印及初始化等信息
            PdfHelper helper = new PdfHelper();
            writer.setPageEvent(helper);
            document.setPageSize(new Rectangle(PageSize.A4));
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputStream);
        }catch(Exception e){
            e.printStackTrace();
            result = false;
        }finally {
            document.close();
            writer.close();
        }
        return result;
    }


    public static void main(String[] args) throws DocumentException, IOException {
        InputStream inputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\html2pdf\\2019-12-11\\aaa.html");
        OutputStream outputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\1.pdf");
        //Step 1—Create a Document.
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        // 添加水印及初始化等信息
        PdfHelper helper = new PdfHelper();
        writer.setPageEvent(helper);

        document.setPageSize(new Rectangle(PageSize.A4));

        // step 3 开启文档
        document.open();

        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputStream);

        //step 5
        document.close();
        writer.close();
        System.out.println("PDF Created!" );
//        addWaterMark();
    }

}
