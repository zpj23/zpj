package com.goldenweb.fxpg.weboffice.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.frame.dao.impl.GenericDaoHibernate;
import com.goldenweb.fxpg.weboffice.dao.BookMarkHibernate;
import com.goldenweb.fxpg.weboffice.pojo.BookMarks;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-4-14 上午10:03:33
 */
@Repository
public class BookMarkHibernateDao extends GenericDaoHibernate<BookMarks, Serializable> implements
		BookMarkHibernate {
	
	public BookMarkHibernateDao(){
		super(BookMarks.class);
	}
}
