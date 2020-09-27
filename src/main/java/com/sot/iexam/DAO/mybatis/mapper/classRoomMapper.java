package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.classRoom;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author Kimbobo
 */
@Repository
public interface classRoomMapper {

    void update(@Param("classRoom") classRoom classRoom);

    public List<classRoom> getAllClassRoomCanUse(String startTime, String endTime);

    public List<classRoom> getAllClassFreeRoomCanUse(String startTime, String endTime,String search);

    Integer getAllClassFreeRoomCanUseNum(String startTime, String endTime,String search);

    public List<classRoom> getAllManagedInfo(@Param("classRoomIds") List<Integer> classRoomIds);


}
