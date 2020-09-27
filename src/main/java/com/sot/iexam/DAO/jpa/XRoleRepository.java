package com.sot.iexam.DAO.jpa;


import com.sot.iexam.DO.XRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface XRoleRepository extends JpaRepository<XRole, Integer> {

    @Query(value = "select * from x_role " +
            "where if(?1 !='',name like %?1% , 1=1) " +
            "and if(?2 !='',remark = ?2 , 1=1)" +
            "and if(?3 !='',status = ?3 , 1=1 )" +
            "and if(?4 !='',id = ?4 , 1=1)", nativeQuery = true)
    List<XRole> find(String name, String remark, String status, String id,
                     Pageable pageable);

    @Query(value = "select count(*) from x_role " +
            "where if(?1 !='',name like %?1% , 1=1) " +
            "and if(?2 !='',remark = ?2 , 1=1)" +
            "and if(?3 !='',status = ?3 , 1=1 )" +
            "and if(?4 !='',id = ?4 , 1=1)", nativeQuery = true)
    Long Count(String name, String remark, String status, String id);



    @Query(value = "select * from x_role " +
            "where level = -1", nativeQuery = true)
    XRole getAdmin();

    @Query("select distinct r from XUser u,XUserRole ur,XRole r " +
            "where u.id=?1 and u.id=ur.userId and ur.roleId = r.id")
    List<XRole> getRolesByUserId(int userId);
}
