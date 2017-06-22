package com.goldenweb.fxpg.frame.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.goldenweb.fxpg.frame.tools.Page;

/**
 * @Description: TODO(service通用类)
 * @author Lee
 * @param <T>
 * @param <PK> 
 * @date 2014-2-11 上午8:32:03
 */
public interface GenericService<T, PK extends Serializable> {

    /**
     * @Title getAll
     * @Description TODO(查询结果集)
     * @return List<T>
     * @author Lee
     * @time 2014-2-11 上午8:25:22
     */
    public List<T> getAll();

    /**
     * @Title get
     * @Description TODO(查询单个对象)
     * @param id
     * @return T
     * @author Lee
     * @time 2014-2-11 上午8:25:27
     */
    public T get(PK id);

    /**
     * @Title exists
     * @Description TODO(查询对象是否存在)
     * @param id
     * @return boolean
     * @author Lee
     * @time 2014-2-11 上午8:25:30
     */
    public boolean exists(PK id);

    /**
     * @Title save
     * @Description TODO(保存对象) 
     * @param object
     * @return T
     * @author Lee
     * @time 2014-2-11 上午8:25:34
     */
    public T save(T object);

    /**
     * @Title remove
     * @Description TODO(删除对象) 
     * @param id void
     * @author Lee
     * @time 2014-2-11 上午8:25:38
     */
    public void remove(PK id);
    
    /**
     * 
    * @Description: TODO(根据主键删除多个对象)
    * @param @param ids    主键集合
    * @return void    
    * @throws
     */
    public void remove(PK[] ids);

    /**
     * @Title updateRow
     * @Description TODO(更新对象) 
     * @param map
     * @param id
     * @param idFiled
     * @return T
     * @author Lee
     * @time 2014-2-11 上午8:25:42
     */
    public T updateRow(Map<String, Object> map ,String id,String idFiled);
    
    /**
     * @Title isExist
     * @Description TODO(查询对象属性是否有值) 
     * @param map
     * @return boolean
     * @author Lee
     * @time 2014-2-11 上午8:25:44
     */
    public boolean isExist(Map<String, String> map);
    
    public Page getByPage(Page queryPage,Map values);
}
