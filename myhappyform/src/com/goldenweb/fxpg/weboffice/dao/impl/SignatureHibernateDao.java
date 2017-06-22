package com.goldenweb.fxpg.weboffice.dao.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.frame.dao.impl.GenericDaoHibernate;
import com.goldenweb.fxpg.weboffice.dao.SignatureHibernate;
import com.goldenweb.fxpg.weboffice.pojo.Signature;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-4-14 下午6:14:36
 */
@Repository
public class SignatureHibernateDao extends GenericDaoHibernate<Signature, Serializable> implements
		SignatureHibernate {
	
	@Autowired
	private LobHandler lobHandler;
	
	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}
	
	public SignatureHibernateDao(){
		super(Signature.class);
	}


	@Override
	public void saveSignature(final Signature signature,final InputStream inputStream) {
		
		String mSql="Insert Into WEBOFFICE_Signature (SignatureID,UserName,Password,MarkName,MarkSize,MarkType,MarkBody) values (SQ_SIGNATURE.NEXTVAL,?,?,?,?,?,?)";
		
		getJdbcTemplate().execute(mSql,
			      new AbstractLobCreatingPreparedStatementCallback(this.lobHandler) {
					@Override
					protected void setValues(PreparedStatement ps,
							LobCreator lobCreator) throws SQLException,
							DataAccessException {
						ps.setString(1, signature.getUserName());
						ps.setString(2, signature.getPassword());
						ps.setString(3, signature.getMarkName());
						ps.setInt(4, signature.getMarkBody().length);
						ps.setString(5, signature.getMarkType());
						
						lobCreator.setBlobAsBytes(ps, 6, signature.getMarkBody());
					}
		});
	}


	private String bytesToHexString(byte [] bArray){
		String hexString="0123456789ABCDEF";
		StringBuilder sb=new StringBuilder(bArray.length*2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for(int i=0;i<bArray.length;i++){
			sb.append(hexString.charAt((bArray[i]&0xf0)>>4));
			sb.append(hexString.charAt((bArray[i]&0x0f)>>0));
		}
		return sb.toString();
	}

}
