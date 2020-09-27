package com.sot.iexam.DAO.jpa;

import com.sot.iexam.DO.XUserRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface XUserRoleRepository extends JpaRepository<XUserRole, Integer> {

    @Query(value = "select * from x_user_role " +
            "where if(?1 !='',role_id = ?1 , 1=1) " +
            "and if(?2 !='',user_id = ?2 , 1=1)" +
            "and if(?3 !='',id = ?3 , 1=1)", nativeQuery = true)
    List<XUserRole> find(String role_id, String user_id, String id,
                         Pageable pageable);

    @Query(value = "select count(*) from x_user_role " +
            "where if(?1 !='',role_id = ?1 , 1=1) " +
            "and if(?2 !='',user_id = ?2 , 1=1)" +
            "and if(?3 !='',id = ?3 , 1=1)", nativeQuery = true)
    Long Count(String role_id, String user_id, String id);


    List<XUserRole> findByUserId(int userId);

    XUserRole findByUserIdAndRoleId(int userId, int roleId);

    @Transactional
    void deleteByUserId(Integer userId);

    @Transactional
    void deleteByUserIdAndRoleId(Integer userId, Integer roleId);
}
