package com.goldenweb.fxpg.weboffice.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.fxpg.frame.service.impl.GenericManagerImpl;
import com.goldenweb.fxpg.weboffice.dao.BookMarkHibernate;
import com.goldenweb.fxpg.weboffice.pojo.BookMarks;
import com.goldenweb.fxpg.weboffice.service.BookMarkService;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-4-14 上午10:00:22
 */
@Service
@Component("bookMarkService")
public class BookMarkServiceImpl extends GenericManagerImpl<BookMarks, Serializable> implements
		BookMarkService {
	private BookMarkHibernate bookMarkDao;
	  
    public BookMarkServiceImpl(){}
	
	@Autowired    
    public BookMarkServiceImpl(BookMarkHibernate bookMarkDao){ 
		super(bookMarkDao);
        this.bookMarkDao = bookMarkDao;         
    }     
	
}
