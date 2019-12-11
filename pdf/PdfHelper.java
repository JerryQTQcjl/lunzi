package com.borosoft.qyxy.serve;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

/**
 * 本类所有方法都可以根据需求重写
 * @author jerry
 */
public class PdfHelper extends PdfPageEventHelper {

    private BaseFont bf;        //字体
    private PdfWriter writer;
    private PdfContentByte cb;

    /* 业务属性*/
    private String fileNo;      //文件编号
    private String endtime;     //有效日期

    private String QRCode;      //二维码
    private String headerImg;   //页眉图片
    private String waterMark;   //水印

    //这里偷了个懒，并且未查阅到页眉中添加到线条的具体方法
    private String dashLine = "﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍" +
            "﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍﹍" ;

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        try {
            bf = BaseFont.createFont(getChineseFont() + ",1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            headerImg = PdfHelper.class.getResource("/template/image").getPath() + "/logo.png";
            waterMark = PdfHelper.class.getResource("/template/image").getPath() + "/watermark.png";
            QRCode = PdfHelper.class.getResource("/template/image").getPath() + "/bottom_erweima.png";
            cb = writer.getDirectContent();
            this.writer = writer;
            fileNo = "文件编号：XYDG201912110001";
            endtime = "有效期限：2020-03-11";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            if (document.getPageNumber() > 1) {

                    this.addHeader(writer, document);
                    this.addFoot(writer, document);
                    this.addWaterMark(writer,document);
            }else{
                this.addImg(headerImg,document.left(),document.top(40),75);
                this.addImg(QRCode,document.right(100),document.top(40),30);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addImg(String imgPath,float x,float y,int percent) throws Exception {
        Image img = Image.getInstance(imgPath);
        img.setAbsolutePosition(x, y);
        img.scalePercent(percent);
        cb.addImage(img);
    }

    public void addBackGroundImg(String imgPath,float x,float y,int percent) throws Exception {
        Image img = Image.getInstance(imgPath);
        img.setAbsolutePosition(x, y);
        img.scalePercent(percent);
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.8f);
        writer.getDirectContentUnder().setGState(gs1);
        writer.getDirectContentUnder().addImage(img);
    }

    /**
     * 添加页眉
     */
    public void addHeader(PdfWriter writer, Document document) throws Exception {
        this.addImg(headerImg,document.left(),document.top(20),50);
        cb.beginText();
        addHeaderRight(writer, document);
        //下划线
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, dashLine, (document.right() + document.left()) / 2
                , document.top(25), 0);
    }

    public void addHeaderRight(PdfWriter writer, Document document) {
        float y = document.top(0);
        cb.setFontAndSize(bf, 9);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, fileNo, document.left()+500, y, 0);
        y = document.top(15);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, endtime, document.left()+475, y, 0);
    }

    /**
     * 添加页脚
     */
    public void addFoot(PdfWriter writer, Document document) throws Exception {
        cb.setFontAndSize(bf, 10);
        float y = document.bottom(-20);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "第 " + (writer.getPageNumber() -1) + " 页",
                (document.right() + document.left()) / 2, y, 0);
        cb.endText();
    }

    public void addWaterMark(PdfWriter writer, Document document) throws Exception{
        this.addBackGroundImg(waterMark,document.left(250),document.top(250),100);
        this.addBackGroundImg(waterMark,100,document.bottom(250),100);
        this.addBackGroundImg(waterMark,document.left(-50),document.bottom(-50),100);
    }

    public String getChineseFont() {
        return PdfHelper.class.getResource("/template").getPath() + "/simsun.ttc";
    }

}
