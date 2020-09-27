package com.sot.iexam.util;


import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kimbobo
 */
public class ResponseUtil {
    public static String ajaxReturn(HttpServletResponse response, Object data) {
        render(response, JSON.toJSONString(data), "json");
        return null;
    }

    public static String ajaxReturn(HttpServletResponse response, Object data, String info, int status) {
        Map<String, Object> jsonData = new HashMap<String, Object>();
        jsonData.put("data", data);
        jsonData.put("info", info);
        jsonData.put("status", status);
        render(response, JSON.toJSONString(jsonData), "json");
        return null;
    }

    public static void render(HttpServletResponse response, String text, String... contentType) {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(text);
        } catch (IOException var8) {
            var8.printStackTrace();
        } finally {
            pw.close();
        }
    }
}
    