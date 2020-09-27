package com.sot.iexam.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * @author Kimbobo
 */
@Controller
@RequestMapping(value = "/yanzhenma")
public class YanZhengMaController {

    @RequestMapping(value = "/checkyanzhenma")
    public void checkyanzhenma(HttpServletResponse response,HttpSession session,int status,String yanzheng){
//        HttpSession session =request.getSession();
        String check="true";
        String Loginyanzhen =(String)session.getAttribute("Loginyanzhenma");
        String Registeryanzhen=(String)session.getAttribute("Registeryanzhenma");

        if(yanzheng.equals("")){
            check="false";
        }else{
            if(status==1){
//                System.out.println(Loginyanzhen+" "+yanzheng);
                if(Integer.parseInt(Loginyanzhen)!=Integer.parseInt(yanzheng)){
                    check="false";
                }
            }else if(status==0){
//                System.out.println(Registeryanzhen+" "+yanzheng);
                if(Integer.parseInt(Registeryanzhen)!=Integer.parseInt(yanzheng)){
                    check="false";
                }
            }
        }
//        System.out.println(check);
        ajaxReturn(response,check);
    }

    @RequestMapping(value = "/getLoginyanzhenma")
    public void getyanzhenma(HttpServletResponse response, HttpSession session) throws IOException {
        int width = 80;
        int heiht = 25;
        BufferedImage img = new BufferedImage(width, heiht, BufferedImage.TYPE_INT_RGB);
        //创建一个画笔
        Graphics g = img.getGraphics();
        //背景颜色
        g.setColor(Color.white);
        g.fillRect(1, 1, width - 2, heiht - 2);
        //边框
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, width, heiht);
        //设置文本样式
        g.setFont(new Font("宋体", Font.BOLD, 15));
        g.setColor(Color.black);
        //添加文本
        Random random = new Random();
        String str = "";
        int position = 10;
        for (int i = 0; i < 4; i++) {
            int ran = random.nextInt(10);
            str += ran + "";
            g.drawString(ran + "", position, 18);
            position += 15;
        }
        session.setAttribute("Loginyanzhenma", str);
        for (int i = 0; i < 9; i++) {
            g.setColor(Color.gray);
            g.drawLine(random.nextInt(width), random.nextInt(heiht), random.nextInt(width), random.nextInt(heiht));
        }
        ImageIO.write(img, "jpg", response.getOutputStream());
    }

    @RequestMapping(value = "/getRegisteryanzhenma")
    public void getRegisteryanzhenma(HttpServletResponse response, HttpSession session) throws IOException {
        int width = 80;
        int heiht = 25;
        BufferedImage img = new BufferedImage(width, heiht, BufferedImage.TYPE_INT_RGB);
        //创建一个画笔
        Graphics g = img.getGraphics();
        //背景颜色
        g.setColor(Color.white);
        g.fillRect(1, 1, width - 2, heiht - 2);
        //边框
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, width, heiht);
        //设置文本样式
        g.setFont(new Font("宋体", Font.BOLD, 15));
        g.setColor(Color.black);
        //添加文本
        Random random = new Random();
        String str = "";
        int position = 10;
        for (int i = 0; i < 4; i++) {
            int ran = random.nextInt(10);
            str += ran + "";
            g.drawString(ran + "", position, 18);
            position += 15;
        }
        session.setAttribute("Registeryanzhenma", str);
        for (int i = 0; i < 9; i++) {
            g.setColor(Color.gray);
            g.drawLine(random.nextInt(width), random.nextInt(heiht), random.nextInt(width), random.nextInt(heiht));
        }
        ImageIO.write(img, "jpg", response.getOutputStream());
    }

    private void ajaxReturn(HttpServletResponse response, Object obj) {
        String jsonStr = JSON.toJSONString(obj);
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Contral", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.print(jsonStr);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
