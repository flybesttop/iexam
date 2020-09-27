package com.sot.iexam.DAO.jpa;

import com.sot.iexam.DO.XNode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface XNodeRepository extends JpaRepository<XNode, Integer> {

    @Query(value = "select * from x_node " +
            "where if(?1 !='',email = ?1 , 1=1) " +
            "and if(?2 !='',icon_class = ?2 , 1=1)" +
            "and if(?3 !='',is_menu = ?3 , 1=1 )" +
            "and if(?4 !='',is_show = ?4 , 1=1)" +
            "and if(?5 !='',level= ?5 , 1=1) " +
            "and if(?6 !='',name like %?6% , 1=1)" +
            "and if(?7 !='',pid= ?7 , 1=1)" +
            "and if(?8 !='',sort_id = ?8 , 1=1)" +
            "and if(?9 !='',url = ?9 , 1=1)" +
            "and if(?10 !='',id = ?10 , 1=1)", nativeQuery = true)
    List<XNode> find(String email, String icon_class, String is_menu, String is_show,
                     String level, String name, String pid, String sort_id,
                     String url, String id,
                     Pageable pageable);


    @Query(value = "select count(*) from x_node " +
            "where if(?1 !='',email = ?1 , 1=1) " +
            "and if(?2 !='',icon_class = ?2 , 1=1)" +
            "and if(?3 !='',is_menu = ?3 , 1=1 )" +
            "and if(?4 !='',is_show = ?4 , 1=1)" +
            "and if(?5 !='',level= ?5 , 1=1) " +
            "and if(?6 !='',name like %?6% , 1=1)" +
            "and if(?7 !='',pid= ?7 , 1=1)" +
            "and if(?8 !='',sort_id = ?8 , 1=1)" +
            "and if(?9 !='',url = ?9 , 1=1)" +
            "and if(?10 !='',id = ?10 , 1=1)", nativeQuery = true)
    Long Count(String email, String icon_class, String is_menu, String is_show,
               String level, String name, String pid, String sort_id,
               String url, String id);

    @Query(value = "select * from x_node order by sort_id asc, is_menu desc", nativeQuery = true)
    List<XNode> findNodeToBack();


    List<XNode> findByPid(int pid);

    @Modifying
    @Query(value = "truncate table x_node", nativeQuery = true)
    void reset();

    @Query("select distinct node from " +
            "XUser u,XUserRole ur,XRole r,XRoleNode rn,XNode node " +
            "where u.id=?1 and u.id=ur.userId and ur.roleId = r.id and ur" +
            ".roleId = rn.roleId and rn.nodeId = node.id ")
    List<XNode> getNodesByUserId(int userId);

    @Query(value = "select distinct node " +
            "from XRole r,XRoleNode rn,XNode node " +
            "where r.id= ?1 and r.id = rn.roleId and rn.nodeId = node.id")
    List<XNode> getNodesByRoleId(int roleId);

    @Query(value = "select * from x_node",nativeQuery = true)
    List<XNode> selectAll();
}
