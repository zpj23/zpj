package com.goldenweb.fxpg.frame.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.goldenweb.fxpg.frame.dao.GenericDao;
import com.goldenweb.fxpg.frame.tools.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
 

public class GenericDaoHibernate<T, PK extends Serializable> implements
		GenericDao<T, PK> {
	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());
	private Class<T> persistentClass;
	
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public void setPersistentClass(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	//private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Constructor that takes in a class to see which type of entity to persist.
	 * Use this constructor when subclassing.
	 * 
	 * @param persistentClass
	 *            the class type you'd like to persist
	 */
	public GenericDaoHibernate(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;

	}

	/**
	 * Constructor that takes in a class and sessionFactory for easy creation of
	 * DAO.
	 * 
	 * @param persistentClass
	 *            the class type you'd like to persist
	 * @param sessionFactory
	 *            the pre-configured Hibernate SessionFactory
	 */
	public GenericDaoHibernate(final Class<T> persistentClass,
			SessionFactory sessionFactory) {
		this.persistentClass = persistentClass;
		this.sessionFactory = sessionFactory;
		//this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	public GenericDaoHibernate() {
		super();
	}

	/*public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}*/

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	@Autowired
	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		//this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public List<T> getAll() {
		return this.getSession().createQuery(" from "+this.persistentClass.getName()).list();
		//return hibernateTemplate.loadAll(this.persistentClass);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public List<T> getAllDistinct() {
		Collection result = new LinkedHashSet(getAll());
		return new ArrayList(result);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public T get(PK id) {
		/*T entity = (T) hibernateTemplate.get(this.persistentClass, id);

		if (entity == null) {
			log.warn("Uh oh, '" + this.persistentClass + "' object with id '"
					+ id + "' not found...");
			return null;
		}*/
		T entity = (T) this.getSession().get(this.persistentClass.getName(), id);  
        return entity;  
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public boolean exists(PK id) {
		T entity = (T) this.getSession().get(this.persistentClass.getName(), id);
		return entity != null;
		
		/*T entity = (T) hibernateTemplate.get(this.persistentClass, id);
		return entity != null;*/
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public T save(T object) {
		return (T) this.getSession().merge(object);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public T savereturn(T object) {
		return (T) this.getSession().merge(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(PK id) {
		T entity = this.get(id);
		this.getSession().delete(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public List<T> findByNamedQuery(String queryName,
			Map<String, Object> queryParams) {
		/*String[] params = new String[queryParams.size()];
		Object[] values = new Object[queryParams.size()];

		int index = 0;
		for (String s : queryParams.keySet()) {
			params[index] = s;
			values[index++] = queryParams.get(s);
		}

		return hibernateTemplate.findByNamedQueryAndNamedParam(queryName,
				params, values);*/
		return null;
	}

	
	@Override
	public Query createQuery(String queryString) {
		Assert.hasText(queryString, "queryString不能为空");
		Query queryObject = getSession().createQuery(queryString);
		return queryObject;
	};

	@Override
	public Query createQuery(String queryString, Object[] values) {
		Assert.hasText(queryString, "queryString不能为空");
		try {
			Query queryObject = createQuery(queryString);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					queryObject.setParameter(i, values[i]);
				}
			}
			return queryObject;
		} catch (HibernateException e) {
			log.error(e);
			throw e;
		}

	}
	
	/**
	 * 分页查询,可带条件、页码
	 * 
	 * @Desc 基于HQL语句查询
	 * @param sql
	 * @param conditions
	 * @param page
	 * @param pageSize
	 * @return List<Object[]>
	 */
	@Override
	public Page hqlQueryPage(String hql,
			Page queryPage, Map values) {
		if(hql==null || "".equals(hql)){
			hql = " from "+ this.persistentClass.getName()+" where 1=1 ";
			if(values != null){
				Set keys = values.keySet();
				for (Object o : keys) {
					if(values.get(o) != null && !values.get(o).equals(""))
					hql += " and "+(String)o+"=:"+(String)o;
				}
			}
		}
		
		Query query = this.getSession().createQuery(hql).setFirstResult(
				(queryPage.getNowPage() - 1)
						* queryPage.getPageSize()).setMaxResults(
								queryPage.getPageSize());

		if(values != null){
			Set keys = values.keySet();
			for (Object o : keys) {
				if(values.get(o) != null && !values.get(o).equals(""))
				query.setParameter((String)o, values.get(o));
			}
		}
		queryPage.setResults(query.list());
		String countHql = "select count(*) "+hql;		//封装查询总记录条数
		Long allCount =  getAllCount(countHql, values);	//总记录条数
		queryPage.setTotalResult(allCount.intValue());
		queryPage.calcuatePage();//计算总页数
		return queryPage;
	}
		
	/**
	 * 
	 * 这个方法除了督查督办其他别调用，查询条件不好控制
	 * @Title hqlQueryPage2
	 * @param hql
	 * @param queryPage
	 * @param values
	 * @param ordercon
	 * @return
	 * @author yjq
	 * @time 2015-10-12 下午2:39:31
	 */
	@Override
	public Page hqlQueryPage2(String hql,
			Page queryPage, Map values,String ordercon) {
		if(hql==null || "".equals(hql)){
			hql = " from "+ this.persistentClass.getName()+" where 1=1 ";
			if(values != null){
				Set keys = values.keySet();
				for (Object o : keys) {
					if(values.get(o) != null && !values.get(o).equals(""))					
						hql += " and "+(String)o+" like '%"+values.get(o)+"%' ";					
						//hql += " and "+(String)o+"=:"+(String)o;
				}
			}
			hql += " order by "+ordercon+" desc ";
		}
		
		Query query = this.getSession().createQuery(hql).setFirstResult(
				(queryPage.getNowPage() - 1)
						* queryPage.getPageSize()).setMaxResults(
								queryPage.getPageSize());

		/*if(values != null){
			Set keys = values.keySet();
			for (Object o : keys) {
				if(values.get(o) != null && !values.get(o).equals(""))
				query.setParameter((String)o, values.get(o));
			}
		}*/
		queryPage.setResults(query.list());
		String countHql = "select count(*) "+hql;		//封装查询总记录条数
		Long allCount =  getAllCount(countHql, null);	//总记录条数
		queryPage.setTotalResult(allCount.intValue());
		queryPage.calcuatePage();//计算总页数
		return queryPage;
	}
	
	
	/**
	 * hql查询得到总查询数目
	 * 
	 * @param hql
	 * 			查询字符串
	 * @param values
	 * 			查询值
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Long getAllCount(String hql, Map values) {
		Query query = getSession().createQuery(hql);
		if(values != null){
			Set keys = values.keySet();
			for (Object o : keys) {
				if(values.get(o) != null && !values.get(o).equals(""))
				query.setParameter((String)o, values.get(o));
			}
		}
		return (Long) query.uniqueResult();
	}
	
	@Override
	public List<T> find(String hql, Object[] values) {
		return createQuery(hql, values).list();
	} 

	@Override
	public Session getSession() {

		return this.getSessionFactory().getCurrentSession();
	}

	/**
	 * 移除hql中的select语句
	 * 
	 * @param hql
	 * @return
	 */
	protected static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 移除hq中的order语句
	 * 
	 * @param hql
	 * @return
	 */
	protected static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	@Override
	public boolean isExist(Map<String, String> map) {
		String hql = "from " + persistentClass.getName() + " where 1=1 ";
		Set<String> setKey = map.keySet();
		Iterator<String> iterator = setKey.iterator();
		while (iterator.hasNext()) {
			String col = iterator.next();
			hql += "and " + col + "= '" + map.get(col) + "'";
		}

		List result = this.find(hql, null);
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;

	}

	/**
	 * @Title: updateRow
	 * @Description: TODO(更新记录)
	 * @param map 要更新的字段 键值对
	 * @param id 主键
	 * @param idFiled 主键字段名称 实体类中主键字段名称
	 * @param 设定文件
	 * @return T 返回类型
	 */ 
	@Override
	public T updateRow(Map<String, Object> map, String id, String idFiled) {

		Set<String> setKey = map.keySet();
		if (setKey.size() == 0) {
			return null;
		}
		Iterator<String> iterator = setKey.iterator();
		String hql = "update " + persistentClass.getName() + " set ";
		while (iterator.hasNext()) {
			String col = iterator.next();
			hql += col + "='" + map.get(col) + "',";
		}
		hql = hql.substring(0, hql.length() - 1);

		hql += " where " + idFiled + "='" + id + "'";
		this.getSession().createQuery(hql).executeUpdate();
		//this.hibernateTemplate.bulkUpdate(hql);
		this.getSession().flush();
		this.getSession().clear();
		return this.get((PK) id);
	}

	/**
	 * 根据参数查询数据集合
	 * 
	 * @param map
	 *            封装的参数 (时间类型格式 yyyy-MM-dd HH:mm:ss)
	 *            如：map("name|like","王五")根据名为王五的模糊查询；
	 *            map("name|=","王五")根据名为王五的查询； map("age|>","31")查询年龄大于31的
	 *            map("age|<","31")查询年龄小于31的 map("age|<=","31")查询年龄小于等于31的
	 *            map("age|>=","31")查询年龄大于等于31的
	 * @return
	 */ 
	public List<T> geAllByParam(Map<String, String> map) {
		Set<String> setKey = map.keySet();
		if (setKey.size() == 0) {
			return null;
		}
		Iterator<String> iterator = setKey.iterator();
		String hql = "from " + persistentClass.getName() + " where 1=1";
		while (iterator.hasNext()) {
			String col = iterator.next().toLowerCase();
			Class returnType = map.get(col).getClass();
			String name = "";
			if (col.indexOf("|like") != -1) {
				name = col.replace("|like", "");
				hql = hql + " and " + name + " like '%" + map.get(col) + "%'";
			} else if (col.indexOf("|=") != -1) {
				name = col.replace("|=", "");
				// 数字类型
				if ("class java.lang.Integer".equals(returnType)) {
					hql = hql + " and " + name + " = "
							+ Integer.valueOf(map.get(col));
				}
				// 时间类型
				else if ("class java.util.Date".equals(returnType)) {
					hql = hql + " " + name + "   = to_date(" + (map.get(col))
							+ ",'yyyy-mm-dd HH:mi:ss')";
				}
				// 字符类型
				else {
					hql = hql + " and " + name + " = '" + map.get(col) + "'";
				}

			} else if (col.indexOf("|>") != -1) {
				name = col.replace("|>", "");
				// 数字类型
				if ("class java.lang.Integer".equals(returnType)) {
					hql = hql + " and " + name + " > "
							+ Integer.valueOf(map.get(col));
				}
				// 时间类型
				else if ("class java.util.Date".equals(returnType)) {
					hql = hql + " and " + name + "   > to_date("
							+ (map.get(col)) + ",'yyyy-mm-dd HH:mi:ss')";
				}
				// 字符类型
				else {
					hql = hql + " and " + name + " > '" + map.get(col) + "'";
				}
			} else if (col.indexOf("|<") != -1) {
				name = col.replace("|<", "");
				// 数字类型
				if ("class java.lang.Integer".equals(returnType)) {
					hql = hql + " and " + name + " < "
							+ Integer.valueOf(map.get(col));
				}
				// 时间类型
				else if ("class java.util.Date".equals(returnType)) {
					hql = hql + " and " + name + "  < to_date("
							+ (map.get(col)) + ",'yyyy-mm-dd HH:mi:ss')";
				}
				// 字符类型
				else {
					hql = hql + " and " + name + " < '" + map.get(col) + "'";
				}
			} else if (col.indexOf("|>=") != -1) {
				name = col.replace("|>=", "");
				// 数字类型
				if ("class java.lang.Integer".equals(returnType)) {
					hql = hql + " and " + name + " >= "
							+ Integer.valueOf(map.get(col));
				}
				// 时间类型
				else if ("class java.util.Date".equals(returnType)) {
					hql = hql + " and " + name + " >= to_date("
							+ (map.get(col)) + ",'yyyy-mm-dd HH:mi:ss')";
				}
				// 字符类型
				else {
					hql = hql + " and " + name + " >= '" + map.get(col) + "'";
				}
			} else if (col.indexOf("|<=") != -1) {
				name = col.replace("|<=", "");
				// 数字类型
				if ("class java.lang.Integer".equals(returnType)) {
					hql = hql + " and " + name + " <= "
							+ Integer.valueOf(map.get(col));
				}
				// 时间类型
				else if ("class java.util.Date".equals(returnType)) {
					hql = hql + " and " + name + "  <= to_date("
							+ (map.get(col)) + ",'yyyy-mm-dd HH:mi:ss')";
				}
				// 字符类型
				else {
					hql = hql + " and " + name + " <= '" + map.get(col) + "'";
				}
			}

		}
		List<T> list = this.getSession().createQuery(hql).list();
		//List<T> list = (List<T>) this.getHibernateTemplate().find(hql);
		this.getSession().flush();
		this.getSession().clear();
		return list;
	}
	
	@Override
	public String queryGson(Map map, String dataFormat){
		Gson gson = new GsonBuilder()
		.setDateFormat(dataFormat)
		.create();
		return gson.toJson(map);
	}
	
	
	/**
	 * 通过sql来查询，返回List<Object[]>
	 * @param sql
	 * @return
	 */
	public List<Object[]> queryListBySql(String sql){
		return this.getSession().createSQLQuery(sql).list();		
	}
	
	/**
	 * 分页查询,可带条件、页码
	 * 
	 * @Desc 基于HQL语句查询
	 * @param sql
	 * @param conditions
	 * @param page
	 * @param pageSize
	 * @return List<Object[]>
	 */
	public List<Object[]> findListToPageByHql(final String sql,
			final List<String> conditions, final int page, final int pageSize) {
		Session session=null;			
		session= sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		if (conditions != null && conditions.size() > 0) {
			for (int i = 0; i < conditions.size(); i++) {
				if (conditions.get(i) != null
						&& !conditions.get(i).equals("")) {
					query.setString(i, conditions.get(i));
				}
			}
		}
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		List list= query.list();
		return list;
	}
	
	/** SQL中的逗号连接字符 */
	public static final String COMMA = " , ";
	/** SQL中的and关键字 */
	public static final String AND = " and ";
	
	public String addEqNotNullHQL (Map<Object, Object> mapFields , String separator){
		StringBuilder stringBuilder = new StringBuilder();
		for(Map.Entry<Object, Object> entry : mapFields.entrySet()){
			stringBuilder.append(entry.getKey())
			.append("='").append(entry.getValue()).append("'")
			.append(separator);
		}
		return StringUtils.removeEnd(stringBuilder.toString(), separator);
	}
	 
	/**
	 * 更新数据
	 * @Title updateRow
	 * @param map 列数据
	 * @param separatorMap 过滤条件
	 * @return boolean
	 * @author Lee
	 * @time 2014年8月12日 上午9:06:26
	 */
	public boolean updateRow(Map<Object, Object> map, Map<Object, Object> separatorMap) {
        try {
			StringBuilder sb = new StringBuilder("update ");
			sb.append(this.getPersistentClass().getName())
			.append(" set ")
			.append(this.addEqNotNullHQL(map, GenericDaoHibernate. COMMA))
			.append(" where ")
			.append(this.addEqNotNullHQL(separatorMap, GenericDaoHibernate.AND));
			this.getSession().createQuery(sb.toString()).executeUpdate();
			this.getSession().flush();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
        return false;
	}
	
	public Page hqlQueryPage2(String hql,String selectstr,
			Page queryPage) {
		
		String hql_ = selectstr+hql;
		
		
		Query query = this.getSession().createQuery(hql_).setFirstResult(
				(queryPage.getNowPage() - 1)
						* queryPage.getPageSize()).setMaxResults(
								queryPage.getPageSize());

		
		queryPage.setResults(query.list());
		String countHql = "select count(*) "+hql;		//封装查询总记录条数
		Long allCount =  getAllCount(countHql, null);	//总记录条数
		queryPage.setTotalResult(allCount.intValue());
		queryPage.calcuatePage();//计算总页数
		return queryPage;
	}
	
	
    /**
     * jdbcTemplate的执行sql
     * @param sql
     */
	public void executeSql(String sql) {
		try {    		
			// jdbcTemplate.execute(sql);
			this.getSession().createSQLQuery(sql).executeUpdate();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
}
