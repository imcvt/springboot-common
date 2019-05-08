package com.imc.dao.common;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

/**
 * @author luoly
 * @date 2019/4/15 14:42
 * @description
 */
@Repository
public class CommonDao extends BaseDao{
    public CommonDao() {
    }

    protected String getNamespace() {
        return null;
    }
}
