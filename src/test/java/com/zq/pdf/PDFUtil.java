package com.zq.pdf;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @Description:
 * @Author: zhangqi
 * @CreateTime: 2020/7/3015:05
 * @Company: MGL
 *
 * 原文地址:   https://blog.csdn.net/weixin_41986096/article/details/86586085?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.edu_weight&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.edu_weight
 */
public class PDFUtil {
        /**
         * document对象
         */
        private static Document document =  null;

        /**
         *  创建一个书写器，布局文本位置
         * @param leftSize 居左
         * @param rightSize 居右
         * @param onSize 居上
         * @param underSize 居下
         * @param path 存储位置
         * @throws Exception 初始化PDF错误
         */
        public PDFUtil(Integer leftSize , Integer rightSize , Integer onSize , Integer underSize, String path) throws Exception {
            try{
                // 新建document对象 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
                document = new Document(PageSize.A4, leftSize, rightSize, onSize, underSize);
                // 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
                // 打开文件
                document.open();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("PDF初始化错误");
            }
        }

        /**
         *  书写每一个段落选择的字体
         *
         * @param fontType
         *             0 //楷体字
         *             1 //仿宋体
         *             2 //黑体
         *             字体需要可在追加
         * @return
         * @throws IOException
         * @throws DocumentException
         */
        public BaseFont addFontType(Integer fontType)  {
            BaseFont baseFont = null;
            try{

                switch (fontType){
                    case 0:
                        //楷体字
                        baseFont = BaseFont.createFont("c://windows//fonts//simkai.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                        break;
                    case 1:
                        //仿宋体
                        baseFont = BaseFont.createFont("c://windows//fonts//SIMFANG.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                        break;
                    case 2:
                        //黑体
                        baseFont = BaseFont.createFont("c://windows//fonts//SIMHEI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                        break;
                }
                return baseFont;
            }catch (Exception e){
                System.out.println("选择字体异常");
                e.printStackTrace();

            }
            return baseFont;
        }

        /**
         *  添加段落 -  段落位置（ 0 居左  1 居中 2 居右）
         * @param fontType 选择字体
         *             0 //楷体字
         *             1 //仿宋体
         *             2 //黑体
         * @param fontSize 字体大小
         * @param color 字体颜色
         * @param alignment   0 居左  1 居中 2 居右
         * @param text 文本内容
         */
        public void addParagraph(Integer fontType , Integer fontSize,Color color ,Integer alignment ,String text){
            try{
                BaseFont chinese =addFontType(fontType);
                Font font = new Font(chinese, fontSize, Font.COURIER,color);
                Paragraph paragraph =new Paragraph(text,font);
                //居中显示
                paragraph.setAlignment(alignment);
                document.add(paragraph);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("添加段落异常");
            }
        }

        /**
         *  添加段落 -  首行缩进
         * @param fontType 选择字体
         *             0 //楷体字
         *             1 //仿宋体
         *             2 //黑体
         * @param fontSize 字体大小
         * @param color 字体颜色
         * @param index  首行缩进
         * @param text 文本内容
         */
        public void addTextIndent(Integer fontType , Integer fontSize,Color color ,Integer index ,String text){
            try{
                BaseFont chinese =addFontType(fontType);
                Font font = new Font(chinese, fontSize, Font.COURIER,color);
                Paragraph paragraph =new Paragraph(text,font);
                //设置首行缩进
                paragraph.setFirstLineIndent(index);
                document.add(paragraph);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("添加段落异常");
            }
        }

        /**
         *  添加新的一页
         */
        public void addPage(){
            try{
                document.newPage();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("添加段落异常");
            }
        }

        /**
         *  换行
         *  传入1是一行，以此递增
         * @param lineNum 换的行数
         */
        public void newLine(Integer lineNum) {
            try{
                for(int i =0 ; i<lineNum ; i++){
                    document.add(new Paragraph("\n"));
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("换行错误");
            }
        }

        /**
         *  关闭文档
         */
        public void close (){

            // 关闭文档
            document.close();
        }
}
