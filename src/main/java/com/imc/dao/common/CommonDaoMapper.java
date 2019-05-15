package com.imc.dao.common;

import java.util.Map;

/**
 * @author luoly
 * @date 2019/5/15 11:16
 * @description
 */
public interface CommonDaoMapper {
    int insertObject();

    int updateObject();

    int updateClobColumnById();

    int deleteObject();

    int batchDeleteObjects();

    Map findObjectById(Map map);
}
