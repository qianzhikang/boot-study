package com.qzk.orm.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * @Description TODO
 * @Date 2022-03-18-14-38
 * @Author Courage
 */
@Component
@Slf4j
public class DataBaseAuditListener {


    @PrePersist
    public void prePersist(Object object) {
        Class<?> aClass = object.getClass();

        try {
            addOperateTime(object,aClass,"createTime");

        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("反射属性异常",e);
        }
    }


    @PreUpdate
    public void preUpdate(Object object) {
        Class<?> aClass = object.getClass();

        try {
            addOperateTime(object,aClass,"updateTime");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("反射属性异常",e);
        }

    }


    protected void addOperateTime(Object object, Class<?> aClass, String propertyName) throws NoSuchFieldException, IllegalAccessException {
        Field time = aClass.getDeclaredField(propertyName);
        time.setAccessible(true);
        Object createdTimeValue = time.get(object);
        if (createdTimeValue == null) {
            time.set(object, new Date());
        }
    }





}
