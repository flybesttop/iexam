package com.sot.iexam.controller;

import com.sot.iexam.DO.reviewerTitle;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.front.ReviewerTitleService;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kimbobo
 */
@RestController
@RequestMapping("reviewerTitle")
public class ReviewerTitleController {
    @Autowired
    ReviewerTitleService reviewerTitleService;

    @LogAnnotation(needData = false, operateType = "阅卷人添加职称信息")
    @RequestMapping("addReviewerTitle")
    public void addReviewerTitle(HttpServletResponse response, @RequestBody reviewerTitle reviewerTitle){
        Map result = new HashMap();
        boolean r = reviewerTitleService.addRegistrationTitle(reviewerTitle, result);
        ResponseUtil.ajaxReturn(response, result, "", r ? 1 : 0);
    }

    @LogAnnotation(needData = false, operateType = "修改职称信息")
    @RequestMapping("changeReviewerTitleStatus")
    public void changeReviewerTitleStatus(HttpServletResponse response,@RequestBody reviewerTitle reviewerTitle){
        reviewerTitleService.removeReviewer(reviewerTitle);
        ResponseUtil.ajaxReturn(response, "", "", 1);
    }
}
