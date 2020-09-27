package com.sot.iexam.service.front;


import com.sot.iexam.DO.controversialGrade;

import java.util.Map;

public interface ControversialGradeService {

    void addControversialGrade(controversialGrade controversialGrade);

    Map getControversialGradeList(Integer page, Integer size, controversialGrade conditionsInObj);

    void updateControversialGrade(controversialGrade controversialGrade);
}
    