package com.sot.iexam.aspect;


import com.sot.iexam.DO.XUser;
import com.sot.iexam.DO.examinees;
import com.sot.iexam.DO.log;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.front.LogService;
import com.sot.iexam.util.Timmer;
import org.apache.catalina.connector.ResponseFacade;
import org.apache.catalina.session.StandardSessionFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * @author Kimbobo
 */
@Component
@Aspect
public class LogAopAspect {

    @Autowired
    LogService logService;

    //?PAGE_FROM_TYPE=BACK
    private final String REQUEST_FROM_BACK = "BACK";
    private final Integer REQUEST_FROM_BACK_VALUE = 1;
    //?PAGE_FROM_TYPE=DOOR
    private final String REQUEST_FROM_DOOR = "DOOR";
    private final Integer REQUEST_FROM_DOOR_VALUE = 0;

    @Around("@annotation(com.sot.iexam.annotation.LogAnnotation)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        LogAnnotation LogInfo = method.getAnnotation(LogAnnotation.class);
        String operateType = LogInfo.operateType();
        //System.out.println(operateType);


        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String fromType = request.getParameter("PAGE_FROM_TYPE");
        Object[] args = pjp.getArgs();
        Parameter[] parameters = method.getParameters();

        String methodUse = method.getName();
        StringBuilder DataFromClient = new StringBuilder();
        int i = 0;
        //非单纯页面跳转
        if (LogInfo.needData()) {
            for (Object arg : args) {
                if (arg.getClass() == StandardSessionFacade.class || arg.getClass() == ResponseFacade.class
                        || arg.getClass() == BindingAwareModelMap.class
                        || arg.getClass() == HttpServletRequest.class) {

                } else if (arg.getClass().getName().equals("org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile")) {

                } else {
                    DataFromClient.append(parameters[i].getName() + ":" + arg.toString());
                    DataFromClient.append(",");
                }
                i++;
            }
        }
        if (DataFromClient.length() > 1)
            DataFromClient.deleteCharAt(DataFromClient.length() - 1);
//        System.out.println(methodUse);
//        System.out.println("传入的值：" + DataFromClient);

        log log = new log();
        if (REQUEST_FROM_BACK.equals(fromType)) {
            XUser xUser = (XUser) session.getAttribute("BACKSYS_USER");
            log.setOperateUser(xUser.getId());
            log.setOperateType(REQUEST_FROM_BACK_VALUE);
        } else if (REQUEST_FROM_DOOR.equals(fromType)) {
            examinees frontUser = (examinees) session.getAttribute("FRONT_USER");
            log.setOperateUser(frontUser.getId());
            log.setOperateType(REQUEST_FROM_DOOR_VALUE);
        }

        System.out.println(DataFromClient);

        String time = Timmer.now();
        log.setTime(time);
        log.setData(DataFromClient.toString());
        log.setFunction(methodUse);
        log.setComment(LogInfo.operateType());
        log.setPackageName(method.getDeclaringClass().getName());
        logService.insert(log);
//        System.out.println(log);
        return pjp.proceed();
    }
}
    