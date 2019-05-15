package com.imc.dao.common;

import com.alibaba.druid.util.StringUtils;
import com.imc.annotation.PoParam;
import com.imc.dao.query.Field;
import com.imc.po.common.BasePo;
import com.imc.utils.Sql2JavaMapping;
import com.imc.utils.SqlTypeHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * @author luoly
 * @date 2019/4/12 18:07
 * @description
 */
@Repository
public class CommonDao extends SqlSessionDaoSupport{

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

    public <T extends BasePo> int updateObject(T object) {
        return this.updateObject(object, false);
    }

    public <T extends BasePo> int updateObject(T object, boolean updateNull) {
        Map<String, Object> params = new HashMap();
        String tableName = this.getTableName(object.getClass());
        List<Field> fields = this.persistentObject2Fields(object, updateNull);
        params.put("tableName", tableName);
        params.put("columns", fields);
        return this.getSqlSession().update(this.getCommonDaoFullSqlId("updateObject"), params);
    }

    public <T extends BasePo> int deleteObject(Class<T> clazz, String id) {
        Map<String, Object> params = new HashMap();
        String tableName = this.getTableName(clazz);
        params.put("tableName", tableName);
        params.put("id", id);
        return this.getSqlSession().delete(this.getCommonDaoFullSqlId("deleteObject"), params);
    }

    public <T extends BasePo> int batchDeleteObjects(Class<T> clazz, List<String> ids) {
        Map<String, Object> params = new HashMap();
        String tableName = this.getTableName(clazz);
        params.put("tableName", tableName);
        params.put("ids", ids);
        return this.getSqlSession().delete(this.getCommonDaoFullSqlId("batchDeleteObjects"), params);
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
        HashMap<String, Object> rowMap = this.getSqlSession().selectOne(this.getCommonDaoFullSqlId("findObjectById"), params);
        return this.constructPersistentObject(clazz, rowMap);
    }

    private <T extends BasePo> T constructPersistentObject(Class<T> clazz, HashMap<String, Object> rowMap) {
        try {
            if (rowMap != null && !rowMap.isEmpty()) {
                T object = clazz.newInstance();
                HashMap property2Column = new HashMap();
                Iterator rowMapIterator = rowMap.keySet().iterator();

                while(rowMapIterator.hasNext()) {
                    String column = (String)rowMapIterator.next();
                    String property = SqlTypeHelper.column2Property(column);
                    property2Column.put(property, column);
                }

                PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(object);
                int propsLen = props.length;

                for(int i = 0; i < propsLen; ++i) {
                    PropertyDescriptor prop = props[i];
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T extends BasePo> List<String> getQueryColumns(Class<T> clazz, List<String> excludeProperties) {
        ArrayList columns = new ArrayList();
        if (excludeProperties != null && excludeProperties.size() > 0) {
            List excludeColumns = this.propertiesToColumns(excludeProperties);

            try {
                List<Field> fields = this.persistentObject2Fields(clazz.newInstance(), true);
                Iterator fieldIterator = fields.iterator();

                while(fieldIterator.hasNext()) {
                    Field f = (Field)fieldIterator.next();
                    if (!excludeColumns.contains(f.getName())) {
                        columns.add(f.getName());
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return columns;
    }

    private ArrayList propertiesToColumns(List<String> properties) {
        ArrayList columns = new ArrayList();
        Iterator iterator = properties.iterator();

        while(iterator.hasNext()) {
            String next = (String)iterator.next();
            columns.add(SqlTypeHelper.property2Column(next));
        }

        return columns;
    }

    private String getCommonDaoFullSqlId(String myBatisSqlId) {
        return COMMONDAO_NAMESPACE + "." + myBatisSqlId;
    }

    private String getTableName(Class<? extends BasePo> clazz) {
        PoParam poParam = clazz.getAnnotation(PoParam.class);
        if (poParam == null) {
            throw new RuntimeException(clazz.getClass().getName() + " must set annotation " + PoParam.class.getSimpleName());
        } else {
            return poParam.table();
        }
    }

    private List<Field> persistentObject2Fields(BasePo object, boolean updateNull) {
        ArrayList fields = new ArrayList();
        PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(object);
        int propsLen = props.length;

        for(int i = 0; i < propsLen; ++i) {
            PropertyDescriptor prop = props[i];
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
                } catch (Exception e) {
                    e.printStackTrace();
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
