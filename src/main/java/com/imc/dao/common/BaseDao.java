package com.imc.dao.common;

import com.alibaba.druid.util.StringUtils;
import com.imc.annotation.PoParam;
import com.imc.dao.query.Field;
import com.imc.po.common.BasePo;
import com.imc.utils.SqlTypeHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * @author luoly
 * @date 2019/4/12 18:07
 * @description
 */
public abstract class BaseDao extends SqlSessionDaoSupport{

    private static final String COMMONDAO_NAMESPACE = "com.imc.dao.common.CommonDaoMapper";

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public <T extends BasePo> int insertObject(T object) {
        if (StringUtils.isEmpty(object.getId())) {
            object.generateAndAssignID();
        }
        Map<String, Object> params = new HashMap();
        String tableName = this.getTableName(object.getClass());
        List<Field> fields = this.persistentObject2Fields(object, false);
        params.put("tableName", tableName);
        params.put("columns", fields);
        return this.getSqlSession().insert(this.getCommonDaoFullSqlId("insertObject"), params);
    }

    public <T extends BasePo> T findObjectById(Class<T> clazz, String id) {
        return this.findObjectById(clazz, id, null);
    }

    private <T extends BasePo> T findObjectById(Class<T> clazz, String id, List<String> excludeProperties) {
        List<String> columns = this.getQueryColumns(clazz, excludeProperties);
        Map<String, Object> params = new HashMap();
        String tableName = this.getTableName(clazz);
        params.put("tableName", tableName);
        params.put("columns", columns);
        params.put("id", id);
        params.put("bannerId", id);
        HashMap<String, Object> rowMap = this.getSqlSession().selectOne(this.getCommonDaoFullSqlId("findObjectById"), params);
        return this.constructPersistentObject(clazz, rowMap);
    }

    private <T extends BasePo> T constructPersistentObject(Class<T> clazz, HashMap<String, Object> rowMap) {
        try {
            if (rowMap != null && !rowMap.isEmpty()) {
                T object = clazz.newInstance();
                HashMap property2Column = new HashMap();
                Iterator var5 = rowMap.keySet().iterator();

                while(var5.hasNext()) {
                    String column = (String)var5.next();
                    String property = SqlTypeHelper.column2Property(column);
                    property2Column.put(property, column);
                }

                PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(object);
                int var17 = props.length;

                for(int var8 = 0; var8 < var17; ++var8) {
                    PropertyDescriptor prop = props[var8];
                    if (prop.getWriteMethod() != null) {
                        String name = prop.getName();
                        String column = (String)property2Column.get(name);
                        if (column != null) {
                            Object value = rowMap.get(column);
                            Object newValue = Sql2JavaMapping.convert2JavaObject(column, prop.getPropertyType(), value);
                            PropertyUtils.setProperty(object, name, newValue);
                        }
                    }
                }

                return object;
            } else {
                return null;
            }
        } catch (Exception var14) {
            throw new RuntimeException(var14);
        }
    }

    private <T extends BasePo> List<String> getQueryColumns(Class<T> clazz, List<String> excludeProperties) {
        ArrayList columns = new ArrayList();
        if (excludeProperties != null && excludeProperties.size() > 0) {
            List excludeColumns = this.propertiesToColumns(excludeProperties);

            try {
                List<Field> fields = this.persistentObject2Fields(clazz.newInstance(), true);
                Iterator var6 = fields.iterator();

                while(var6.hasNext()) {
                    Field f = (Field)var6.next();
                    if (!excludeColumns.contains(f.getName())) {
                        columns.add(f.getName());
                    }
                }
            } catch (Exception var8) {
                throw new RuntimeException(var8);
            }
        }

        return columns;
    }

    private ArrayList propertiesToColumns(List<String> properties) {
        ArrayList columns = new ArrayList();
        Iterator var3 = properties.iterator();

        while(var3.hasNext()) {
            String ep = (String)var3.next();
            columns.add(SqlTypeHelper.property2Column(ep));
        }

        return columns;
    }

    private String getCommonDaoFullSqlId(String myBatisSqlId) {
        return COMMONDAO_NAMESPACE + "." + myBatisSqlId;
    }

    private String getTableName(Class<? extends BasePo> clazz) {
        PoParam poParam = (PoParam)clazz.getAnnotation(PoParam.class);
        if (poParam == null) {
            throw new RuntimeException(clazz.getClass().getName() + " must set annotation " + PoParam.class.getSimpleName());
        } else {
            return poParam.table();
        }
    }

    private List<Field> persistentObject2Fields(BasePo object, boolean updateNull) {
        ArrayList fields = new ArrayList();
        PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(object);
        PropertyDescriptor[] var5 = props;
        int var6 = props.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            PropertyDescriptor prop = var5[var7];
            if (prop.getReadMethod() != null && prop.getWriteMethod() != null) {
                String name = prop.getName();

                try {
                    Object value = PropertyUtils.getProperty(object, name);
                    String column;
                    Field field;
                    if (updateNull) {
                        column = SqlTypeHelper.property2Column(name);
                        field = new Field(column, value, (String)null);
                        fields.add(field);
                    } else if (value != null) {
                        column = SqlTypeHelper.property2Column(name);
                        field = new Field(column, value, (String)null);
                        fields.add(field);
                    }
                } catch (Exception var13) {
                    var13.printStackTrace();
                }
            }
        }

        if (fields.isEmpty()) {
            throw new RuntimeException(object.getClass().getName() + " has no persistent property.");
        } else {
            return fields;
        }
    }

}
