package com.sot.iexam.service.front;

import com.sot.iexam.DO.registration;

import java.util.Map;
/**
 * @author Kimbobo
 */
public interface RegistrationService {

    boolean addRegistration(registration registration, Map map);

    Map getRegistrationByExamineesId(Integer examineesId, Integer page, Integer size);

    registration getRegistrationById(Integer registrationId);

    Map getRegistrationList(Integer page, Integer size, registration registration);

    Map getRegistrationByExamId(Integer examId, Integer page, Integer size);

    boolean updateRegistration(registration registration, Map result);

    boolean deleteByRegistrationId(registration registrationId, Map result);
}
