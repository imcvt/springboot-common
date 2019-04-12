package com.imc.dao.common;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luoly
 * @date 2019/4/12 18:07
 * @description
 */
public abstract class BaseDao extends SqlSessionDaoSupport{

//    public <T extends BasePo> int insertObject(T object) {
//        if (StringUtils.isEmpty(object.getId())) {
//            object.generateAndAssignID();
//        }
//
//        Map<String, Object> params = new HashMap();
//        String tableName = this.getTableName(object.getClass());
//        List<Field> fields = this.persistentObject2Fields(object, false);
//        params.put("tableName", tableName);
//        params.put("columns", fields);
//        return this.getSqlSession().insert(this.getCommonDaoFullSqlId("insertObject"), params);
//    }
}
