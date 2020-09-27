package com.sot.iexam.service.front;

import com.sot.iexam.DO.grade;

import java.util.Map;

public interface GradeService {

    void addGrade(grade grade);

    void updateGrade(grade grade);

    Map getGradeList(Integer page, Integer size, grade conditionsInObj);

    Map getMyGradeList(Integer page, Integer size, grade conditionsInObj);

    Map getGradeListToSearch(Integer page, Integer size, grade conditionsInObj);
}
    