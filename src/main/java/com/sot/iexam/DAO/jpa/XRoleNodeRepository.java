package com.sot.iexam.DAO.jpa;


import com.sot.iexam.DO.XRoleNode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface XRoleNodeRepository extends JpaRepository<XRoleNode, Integer> {

    @Query(value = "select * from x_role_node " +
            "where if(?1 !='',node_id = ?1 , 1=1) " +
            "and if(?2 !='',role_id = ?2 , 1=1)" +
            "and if(?3 !='',id = ?3 , 1=1)", nativeQuery = true)
    List<XRoleNode> find(String node_id, String role_id, String id,
                         Pageable pageable);

    @Query(value = "select count(*) from x_role_node " +
            "where if(?1 !='',node_id = ?1 , 1=1) " +
            "and if(?2 !='',role_id = ?2 , 1=1)" +
            "and if(?3 !='',id = ?3 , 1=1)", nativeQuery = true)
    Long Count(String node_id, String role_id, String id);

    List<XRoleNode> findByNodeId(Integer nodeid);


    void deleteByNodeId(int tempid);

    void deleteByRoleId(int roleId);

    @Query(value = "select node_id from x_role_node where if(?1 !='',role_id = ?1 , 1=1)  ", nativeQuery = true)
    int[] selectByRoleId(int roleid);

    @Modifying
    @Query(value = "truncate table x_role_node", nativeQuery = true)
    void reset();
}
