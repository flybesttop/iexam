package com.sot.iexam.controller;

import com.sot.iexam.DO.examinees;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.front.ExamineesService;
import com.sot.iexam.service.front.FileService;
import com.sot.iexam.util.BeanUtils;
import com.sot.iexam.util.ResponseUtil;
import com.sun.deploy.net.HttpResponse;
import com.zlzkj.core.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kimbobo
 */

@Controller
@RequestMapping("examinees")
public class ExamineesController {
    @Autowired
    ExamineesService examineesService;
    @Autowired
    FileService fileService;

    //@LogAnnotation(needData = false, operateType = "门户网站登录")
    @RequestMapping("login")
    public void login(HttpServletResponse response, String account, String password, HttpSession session) {
        String pwd=DigestUtils.md5DigestAsHex(password.getBytes());
        examinees user = examineesService.login(account, pwd);
        if (user != null) {
            session.setAttribute("FRONT_USER", user);
            ResponseUtil.ajaxReturn(response, "", "登录成功", 1);
        } else {
            ResponseUtil.ajaxReturn(response, "", "登录失败", 0);
        }
    }

    @LogAnnotation(needData = false, operateType = "门户网站登出")
    @RequestMapping("logout")
    public void logout(HttpServletResponse response, HttpSession session) {
        session.removeAttribute("FRONT_USER");
        ResponseUtil.ajaxReturn(response, "", "已注销", 1);
    }

    @LogAnnotation(needData = false, operateType = "修改密码")
    @RequestMapping("updatePassword")
    public void updatePassword(HttpServletResponse response, Integer examineesId, String newPwd, String oldPwd) {
        String newPwdMd5=DigestUtils.md5DigestAsHex(newPwd.getBytes());
        String oldPwdMd5=DigestUtils.md5DigestAsHex(oldPwd.getBytes());

        boolean check = examineesService.updatePassword(examineesId, newPwdMd5, oldPwdMd5);
        if (check) {
            ResponseUtil.ajaxReturn(response, "", "修改成功", 1);
        } else {
            ResponseUtil.ajaxReturn(response, "", "原密码输入错误", 0);
        }

    }


    /**
     * 添加考生信息（主要是考生注册） 格式：{@link examinees}
     *
     * @param examinees 传入的考生JSON串
     */
//    @LogAnnotation(needData = false, operateType = "注册")
    @RequestMapping("addExaminees")
    public void addExaminees(HttpServletResponse response, @RequestBody examinees examinees) {
        examinees.setGender(2);
        examinees.setPwd(DigestUtils.md5DigestAsHex(examinees.getPwd().getBytes()));

        boolean result = examineesService.insert(examinees);
        if (result) {
            ResponseUtil.ajaxReturn(response, examinees, "", 1);
        } else {
            ResponseUtil.ajaxReturn(response, null, "", 0);
        }

    }

    @LogAnnotation(needData = false, operateType = "修改头像")
    @RequestMapping("uploadImg")
    public void uploadImg(HttpServletResponse response, @RequestParam("img") MultipartFile multipartFile, @RequestParam("userId") Integer userId) {
        examinees examinees = examineesService.getExamineesInfoById(userId);
        Map<String, Object> data = new HashMap<>(8);
        boolean flag = fileService.uploadMultipartFile(data, multipartFile);
        if (flag) {//上传成功
            ResponseUtil.ajaxReturn(response, data, "上传完毕", 1);
            examinees.setPhotoPath(data.get("imgUrl").toString());
            examineesService.updateExaminees(examinees);
        } else {
            ResponseUtil.ajaxReturn(response, null, data.get("errorMessage").toString(), 0);
        }
    }

    /**
     * 考生信息修改
     *
     * @param examinees 考生信息实体 格式：{@link examinees}
     */
    @LogAnnotation(needData = false, operateType = "信息修改")
    @RequestMapping("updateExaminees")
    public void updateExaminees(HttpServletResponse response, @RequestBody examinees examinees) {
        boolean result = examineesService.updateExaminees(examinees);
        ResponseUtil.ajaxReturn(response, null, "", 1);
    }

    /**
     * 根据考生id查找 考生信息
     * 返回考生的信息
     *
     * @param examineesId 考生id
     */
    @LogAnnotation(needData = false, operateType = "根据id查找考生信息")
    @RequestMapping("getExamInfoByExamineesId")
    public void getExamInfoByExamineesId(HttpServletResponse response, Integer examineesId) {
        examinees examinees = examineesService.getExamineesInfoById(examineesId);
        ResponseUtil.ajaxReturn(response, examinees);
    }

    /**
     * 获取考生信息（根据考生的信息，可多条件查询）
     * 页数从1开始，不传值默认第一页 大小10
     * // {
     * //  "page":页号,
     * //  "size":页大小,
     * //  "examinees":{
     * //       {@link examinees}
     * //        可以根据考试id查询
     * //      }
     * //  }
     *
     * @param map 传入的map包括了
     */
    @LogAnnotation(needData = false, operateType = "获取考生信息列表")
    @RequestMapping("getExamineesList")
    @SuppressWarnings("unchecked")
    public void getExamineesList(HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
        Integer page = Integer.valueOf(map.get("page").toString());
        Integer size = Integer.valueOf(map.get("size").toString());
        examinees e = BeanUtils.map2Bean((LinkedHashMap) map.get("examinees"), examinees.class);
        System.out.println(e.toString());
        Map m = examineesService.getExamineesList(page, size, e);
        ResponseUtil.ajaxReturn(response, m);
    }
}
    