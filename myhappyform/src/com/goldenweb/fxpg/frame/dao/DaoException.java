package com.goldenweb.fxpg.frame.dao;


/**
 * 编译时检测异常
 * @author Lee 
 * @date 2014-2-11 下午1:16:39
 */
public class DaoException extends RuntimeException {
    private static final long serialVersionUID = 7662715726329519089L;

    public DaoException(String msg){
        super(msg);
    }
}
