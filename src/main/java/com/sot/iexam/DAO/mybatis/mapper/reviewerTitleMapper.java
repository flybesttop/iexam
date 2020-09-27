package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.reviewerTitle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface reviewerTitleMapper {

    void insert(@Param("reviewerTitle") reviewerTitle reviewerTitle);

    reviewerTitle checkExistence(@Param("reviewerTitle") reviewerTitle reviewerTitle);

    void updateStatus(@Param("reviewerTitle") reviewerTitle reviewerTitle);

}
