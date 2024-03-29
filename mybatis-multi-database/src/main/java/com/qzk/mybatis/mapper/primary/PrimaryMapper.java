package com.qzk.mybatis.mapper.primary;

import com.qzk.mybatis.entity.BootUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description TODO
 * @Date 2022-03-31-21-53
 * @Author Courage
 */
public interface PrimaryMapper {
    /**
     * 查询所有数据
     *
     * @return List<BootUser>
     */
    List<BootUser> selectAll();
    /**
     * 新增
     *
     * @param bootUser 入参
     */
    void insert(@Param("bootUser") BootUser bootUser);

}
