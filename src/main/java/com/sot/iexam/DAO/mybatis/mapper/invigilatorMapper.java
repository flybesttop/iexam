package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.XUser;
import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.invigilator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface invigilatorMapper {

    List<Integer> getAllInvigilators();


    void disableInvigilatorMapper(Integer userId);

    Integer getInvigilatorAssignment(Integer userId);

    void enableInvigilatorMapper(Integer userId);

    void insert(@Param("invigilator") invigilator invigilator);

    void update(@Param("invigilator") invigilator invigilator);

    XUser getInvigilatorById(Integer invigilatorId);


    List<XUser> getInfo(List list);

    List<XUser> getInfoSearch(List list);

    Integer getInfoCount(List list);

    void resetInvigilator(Integer examId);
}

