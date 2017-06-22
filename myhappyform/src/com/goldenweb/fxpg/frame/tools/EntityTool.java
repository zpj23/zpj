package com.goldenweb.fxpg.frame.tools;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Description: TODO(将实体工具类，Spring查询对象封装实体工具)
 * @author Lee 
 * @date 2014-2-11 上午10:47:01
 */
public class EntityTool {

    static Log LOGGER = LogFactory.getLog(EntityTool.class);
 
    /**
     * @Description: TODO(将map转成实体，map中的key必须与实体中的属性名相同才可以转换成功)
     * @Title converToEntity
     * @param entityMap 实体map，属性键值对，其中key为实体的属性名，value为属性的值
     * @param entityType 实体类
     * @return T
     * @throws Exception
     * @author Lee
     * @time 2014-2-14 上午8:26:27
     */
    public static <T> T converToEntity(Map<String, Object> entityMap, Class<T> entityType) throws Exception {
        T result = null;
        try {
            result = entityType.newInstance();
            Map<String, Field> fieldsMap = getFieldMap(entityType);
            Field field = null;
            for (Map.Entry<String, Object> entry : entityMap.entrySet()) {
                field = fieldsMap.get(entry.getKey().toLowerCase());
                if (field == null) {
                    LOGGER.error("键值" + entry.getKey().toLowerCase() + "在" + entityType.getName() + "中找不到对应的属性！");
                    throw new Exception();

                }
                field.setAccessible(true);
                Object value = entry.getValue();

                if (value == null) {
                    continue;
                    
                }

                if (isBasicType(field.getType())) {
                    

                    if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
                        if (value.equals(new Boolean(true) || value.equals(true)) || value.equals(new Boolean(true) || value.equals(true))) {
                            field.set(result, value);
                        } else {
                            if (value.toString().equals("1")) {
                                field.set(result, true);
                            } else {
                                field.set(result, false);
                            }
                        }
                    } else {
                        field.set(result, value);
                    }


                } else {
                    /*
                    设置复合类型属性，只设id如果存在
                     */
                    Class fildType = field.getType();
                    Field idField = null;
                    try {
                        idField = getDeclaredField("hits", fildType, true);
                    } catch (NoSuchFieldException e) {
                        continue;
                    }
                    if (idField != null) {
                        idField.setAccessible(true);
                        Object compositeFieldValue = fildType.newInstance();
                        idField.set(compositeFieldValue, value);
                        field.set(result, compositeFieldValue);
                    } else {
                        LOGGER.warn("复合属性" + entry.getKey().toLowerCase() + "没有id属性，没有赋值！");
                    }
                }

            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }
 
    /**
     * @Description: TODO(得到某类的属性)
     * @Title getDeclaredField
     * @param fieldName 属性名
     * @param clazz 类类
     * @param withSuperClass 是否从父类中取属性
     * @return Field
     * @throws NoSuchFieldException
     * @author Lee
     * @time 2014-2-14 上午8:32:17
     */
    private static Field getDeclaredField(String fieldName, Class clazz, boolean withSuperClass) throws NoSuchFieldException {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (field == null && withSuperClass) {
                Class superClazz = clazz.getSuperclass();
                if (superClazz != null) {
                    field = getDeclaredField(fieldName, superClazz, withSuperClass);
                }
            } else {
                throw e;
            }
        }


        return field;
    }
 
    /**
     * @Description: TODO(将多个map转换成实体集合)
     * @Title convertToEntities
     * @param datas
     * @param entityType
     * @return List<E>
     * @throws Exception
     * @author Lee
     * @time 2014-2-14 上午8:32:58
     */
    public static <E> List<E> convertToEntities(List<Map<String, Object>> datas, Class<E> entityType) throws Exception {
        List<E> result = new ArrayList<E>();
        E data = null;
        for (Map<String, Object> map : datas) {
            data = converToEntity(map, entityType);
            result.add(data);
            data = null;
        }
        return result;

    }
 
    /**
     * @Description: TODO(判断是不是基本类型)
     * @Title isBasicType
     * @param type
     * @return boolean
     * @author Lee
     * @time 2014-2-14 上午10:32:00
     */
    public static boolean isBasicType(Class type) {

        if (type.isPrimitive()) {
            return true;
        }
       

        if (type.getPackage().getName().equals("java.lang") || type.getPackage().getName().equals(("java.util")) || type.getPackage().getName().equals("java.math")) {
            return true;
        } else if (type.equals(java.sql.Date.class)) {
            return true;
        } else {
            return false;
        }
    }
 
    /**
     * @Description: TODO(得到某个类的属性map key为属性名，value为属性)
     * @Title getFieldMap
     * @param entityClass
     * @return Map<String, Field>
     * @author Lee
     * @time 2014-2-14 上午10:32:14
     */
    public static Map<String, Field> getFieldMap(Class entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        Map<String, Field> fieldsMap = new HashMap<String, Field>();

        for (Field field : fields) {
            fieldsMap.put(field.getName().toLowerCase(), field);

        }
        Class superclass = entityClass.getSuperclass();

        if (superclass != null) {
            fieldsMap.putAll(getFieldMap(superclass));

        }
        return fieldsMap;
    }
}