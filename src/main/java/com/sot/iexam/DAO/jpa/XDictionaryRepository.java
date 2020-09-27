package com.sot.iexam.DAO.jpa;

import com.sot.iexam.DO.XDictionary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface XDictionaryRepository extends JpaRepository<XDictionary, Integer> {
    @Query(value = "select * from x_dictionary " +
            "where if(?1 !='',create_time >= ?1 , 1=1) " +
            "and if(?2 !='',create_time <= ?2 , 1=1)" +
            "and if(?3 !='',modified_time >= ?3 , 1=1 )" +
            "and if(?4 !='',modified_time <= ?4 , 1=1)" +
            "and if(?5 !='',code like %?5% , 1=1) " +
            "and if(?6 !='',value like %?6% , 1=1)" +
            "and if(?7 !='',type = ?6 , 1=1)" +
            "and if(?8 !='',detail = ?8 , 1=1)" +
            "and if(?9 !='',id = ?9 , 1=1)", nativeQuery = true)
    List<XDictionary> find(String create_time_start, String create_time_end, String modified_time_start, String modified_time_end,
                           String code, String value, String type, String detail, String id,
                           Pageable pageable);



    @Query(value = "select * from x_dictionary " +
            "where if(?1 !='',code like ?1% , 1=1)" +
            "and if(?2 != '',type = ?2 , 1=1)" +
            "order by sort asc ", nativeQuery = true)
    List<XDictionary> findDataBasePre(String code, String type);

    @Query(value = "select count(*) from x_dictionary " +
            "where if(?1 !='',create_time >= ?1 , 1=1) " +
            "and if(?2 !='',create_time <= ?2 , 1=1)" +
            "and if(?3 !='',modified_time >= ?3 , 1=1 )" +
            "and if(?4 !='',modified_time <= ?4 , 1=1)" +
            "and if(?5 !='',code like %?5% , 1=1) " +
            "and if(?6 !='',value like %?6% , 1=1)" +
            "and if(?7 !='',type = ?6 , 1=1)" +
            "and if(?8 !='',detail = ?8 , 1=1)" +
            "and if(?9 !='',id = ?9 , 1=1)", nativeQuery = true)
    Long Count(String create_time_start, String create_time_end, String modified_time_start, String modified_time_end,
               String code, String value, String type, String detail, String id);
}
