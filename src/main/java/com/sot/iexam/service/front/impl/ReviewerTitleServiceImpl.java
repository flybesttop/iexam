package com.sot.iexam.service.front.impl;

import com.sot.iexam.DAO.mybatis.mapper.reviewerTitleMapper;
import com.sot.iexam.DO.reviewerTitle;
import com.sot.iexam.service.front.ReviewerTitleService;
import com.sot.iexam.util.Timmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.Map;

@Service
public class ReviewerTitleServiceImpl implements ReviewerTitleService {
    @Autowired
    reviewerTitleMapper reviewerTitleMapper;

    @Override
    public boolean addRegistrationTitle(reviewerTitle reviewerTitle, Map result) {

        reviewerTitle check=reviewerTitleMapper.checkExistence(reviewerTitle);
        if (check != null) {

            if (check.getStatus()==1){
                result.put("result", "已经存在该阅卷");
                return false;
            }else{
                check.setStatus(1);
                reviewerTitleMapper.updateStatus(check);
                result.put("result", "启用成功");
                return true;
            }

        }
        result.put("result", "启用成功");
        reviewerTitle.setAddTime(Timmer.now());
        reviewerTitle.setModifyTime(Timmer.now());
        reviewerTitle.setReviewTimes(0);
        reviewerTitleMapper.insert(reviewerTitle);
        return true;
    }

    @Override
    public void removeReviewer(reviewerTitle reviewerTitle) {
        reviewerTitle.setStatus(0);
        reviewerTitleMapper.updateStatus(reviewerTitle);
    }
}
