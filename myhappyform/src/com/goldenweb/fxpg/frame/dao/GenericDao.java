package com.goldenweb.fxpg.frame.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.goldenweb.fxpg.frame.tools.Page;


public interface GenericDao<T, PK extends Serializable> {

	/**
	 * Generic method used to get all objects of a particular type. This is the
	 * same as lookup up all rows in a table.
	 * 
	 * @return List of populated objects
	 */
	List<T> getAll();

	/**
	 * Gets all records without duplicates.
	 * <p>
	 * Note that if you use this method, it is imperative that your model
	 * classes correctly implement the hashcode/equals methods
	 * </p>
	 * 
	 * @return List of populated objects
	 */
	List<T> getAllDistinct();

	/**
	 * Generic method to get an object based on class and identifier. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param id
	 *            the identifier (primary key) of the object to get
	 * @return a populated object
	 * @see org.springframework.orm.ObjectRetrievalFailureException
	 */
	 T get(PK id);

	/**
	 * Checks for existence of an object of type T using the id arg.
	 * 
	 * @param id
	 *            the id of the entity
	 * @return - true if it exists, false if it doesn't
	 */
	boolean exists(PK id);

	/**
	 * Generic method to save an object - handles both update and insert.
	 * 
	 * @param object
	 *            the object to save
	 * @return the persisted object
	 */
	T save(T object);

	T savereturn(T object);

	/**
	 * Generic method to delete an object based on class and id
	 * 
	 * @param id
	 *            the identifier (primary key) of the object to remove
	 */
	void remove(PK id);

	/**
	 * Find a list of records by using a named query
	 * 
	 * @param queryName
	 *            query name of the named query
	 * @param queryParams
	 *            a map of the query names and the values
	 * @return a list of the records found
	 */
	List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

	Query createQuery(String queryString);

	Query createQuery(String queryString, Object[] values);

	List<T> find(String hql, Object[] values);

	boolean isExist(Map<String, String> map);
  
	/**
	 * @Title updateRow
	 * @Description TODO(更新记录)
	 * @param map 要更新的字段 键值对
	 * @param id 主键
	 * @param idFiled 主键字段名称 实体类中主键字段名称
	 * @return T
	 * @author Lee
	 * @time 2014-2-11 上午8:55:59
	 */
	T updateRow(Map<String, Object> map, String id, String idFiled);
	
	/**
	 * 输出GSON
	 * @Title queryGson
	 * @param map 数据
	 * @param dataFormat 日期格式
	 * @return String
	 * @author Lee
	 * @time 2014-3-6 上午09:32:33
	 */
	public String queryGson(Map map, String dataFormat);
	
	public Page hqlQueryPage(String hql,Page queryPage,Map values);
	    
	public Long getAllCount(String hql, Map values);
	
	public Session getSession();

	Page hqlQueryPage2(String string, Page queryPage, Map values,
			String ordercon);
}