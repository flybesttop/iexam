package com.sot.iexam.config;

import com.sot.iexam.interceptor.BackInterceptor;
import com.sot.iexam.interceptor.FrontInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    BackInterceptor loginInterceptor;
    @Autowired
    FrontInterceptor frontInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploadFile/**").addResourceLocations("file:e:\\tomcatUpload\\");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String Exclude[] = new String[]{"/css/**", "/js/**", "/font/**", "/fontAwesome/**", "/img/**"};


        registry.addInterceptor(loginInterceptor).addPathPatterns("/backPage/**").excludePathPatterns(Exclude).excludePathPatterns("/backPage/login");
        registry.addInterceptor(frontInterceptor).addPathPatterns("/door/**").excludePathPatterns(Exclude).excludePathPatterns("/door/login");

        super.addInterceptors(registry);
    }

}
    