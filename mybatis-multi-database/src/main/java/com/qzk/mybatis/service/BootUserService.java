package com.qzk.mybatis.service;

import com.qzk.mybatis.entity.BootUser;
import com.qzk.mybatis.mapper.primary.PrimaryMapper;
import com.qzk.mybatis.mapper.secondary.SecondaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Date 2022-03-31-22-00
 * @Author Courage
 */
@Service
public class BootUserService {
    @Resource
    private PrimaryMapper primaryMapper;
    @Resource
    private SecondaryMapper secondaryMapper;

    public List<BootUser> getUsers() {
        List<BootUser> primaryUsers = primaryMapper.selectAll();
        List<BootUser> secondaryUsers = secondaryMapper.selectAll();
        List<BootUser> result = new ArrayList<>();
        result.addAll(primaryUsers);
        result.addAll(secondaryUsers);
        return result;
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public void saveUser(BootUser bootUser) {
        primaryMapper.insert(bootUser);
        int a = 2 / 0;
        secondaryMapper.insert(bootUser);
    }
}
