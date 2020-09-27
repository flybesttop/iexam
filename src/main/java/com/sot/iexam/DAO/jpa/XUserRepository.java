package com.sot.iexam.DAO.jpa;


import com.sot.iexam.DO.XUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface XUserRepository extends JpaRepository<XUser, Integer> {

    @Query(value = "select * from x_user " +
            "where if(?1 !='',is_enabled = ?1 , 1=1) " +
            "and if(?2 !='',password = ?2 , 1=1)" +
            "and if(?3 !='',phone = ?3 , 1=1 )" +
            "and if(?4 !='',real_name like %?4% , 1=1)" +
            "and if(?5 !='',username like %?5%, 1=1)" +
            "and if(?6 !='',id= ?6 , 1=1) "
            , nativeQuery = true)
    List<XUser> find(String is_enabled, String password, String phone, String real_name,
                     String username, String id,
                     Pageable pageable);

    @Query(value = "select count(*) from x_user " +
            "where if(?1 !='',is_enabled = ?1 , 1=1) " +
            "and if(?2 !='',password = ?2 , 1=1)" +
            "and if(?3 !='',phone = ?3 , 1=1 )" +
            "and if(?4 !='',real_name like %?4% , 1=1)" +
            "and if(?5 !='',username like %?5% , 1=1)" +
            "and if(?6 !='',id= ?6 , 1=1) "
            , nativeQuery = true)
    Long Count(String is_enabled, String password, String phone, String real_name,
               String username, String id);


    XUser findByUsernameAndPassword(String username, String password);


    @Query(value = "select * from x_user where id=?1", nativeQuery = true)
    XUser findByIdToNews(Integer id);

    @Query(value = "select u.id, u.is_enabled, u.password, u.phone, u.real_name, u.username, u.create_time, u.modified_time " +
            "from x_user u,x_user_role ur where ur.role_id = ?1 and u.id = ur.user_id and if(?2 != '' , u.real_name like %?2%,1=1) limit 0,200 ", nativeQuery = true)
    List<XUser> findByRoleId(Integer roleID, String realName);

    @Query(value = "select u.id, u.is_enabled, u.password, u.phone, u.real_name, u.username, u.create_time, u.modified_time " +
            "from x_user u where u.id not in(select user_id from x_user_role where role_id = ?1) and if(?2 != '' , u.real_name like %?2%,1=1) limit 0,200", nativeQuery = true)
    List<XUser> findWithoutRoleId(Integer roleId, String realName);

    @Query(value = "select * from x_user where real_name like %?3% or phone like %?3% limit ?1,?2", nativeQuery = true)
    List<XUser> findByPageAndSize(Integer page,Integer size,String userSearch);

    @Query(value = "select count(*) from x_user where real_name like %?1% or phone like %?1%", nativeQuery = true)
    Long countByUserSearch(String userSearch);

    @Query(value = "select u.* from x_user u,reviewer_title r where r.title_id=?4 and r.reviewer_id=u.id and r.status=1 and ( u.real_name like %?3% or u.phone like %?3% ) limit ?1,?2", nativeQuery = true)
    List<XUser> findByPageAndSizeByTitleId(Integer page,Integer size,String reviewSearch,Integer titleId);

    @Query(value = "select count(*) from x_user u,reviewer_title r where r.title_id=?2 and r.reviewer_id=u.id and r.status=1 and ( u.real_name like %?1% or u.phone like %?1% )", nativeQuery = true)
    Long countByUserSearchByTitleId(String reviewSearch,Integer titleId);
}
