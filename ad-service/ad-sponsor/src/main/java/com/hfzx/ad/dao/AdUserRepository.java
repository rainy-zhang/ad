package com.hfzx.ad.dao;

import com.hfzx.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: zhangyu
 * @Description: 用户Dao
 * @Date: in 2020/4/21 23:01
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    /**
     * <h2>根据用户名查找用户</h2>
     * @param username
     * @return
     */
    AdUser findByUsername(String username);

}
