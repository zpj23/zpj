package com.goldenweb.biz.comm;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-4-18 下午5:54:45
 */
public class SeriNoGenerater {
	private static final String SERIAL_NUMBER = "XXXX"; // 流水号格式
	private static SeriNoGenerater seriNoGenerater = null;

	private SeriNoGenerater() {
	}

	/**
	 * 取得PrimaryGenerater的单例实现
	 * 
	 * @return
	 */
	public static SeriNoGenerater getInstance() {
		if (seriNoGenerater == null) {
			synchronized (SeriNoGenerater.class) {
				if (seriNoGenerater == null) {
					seriNoGenerater = new SeriNoGenerater();
				}
			}
		}
		
		return seriNoGenerater;
	}

	/**
	 * 生成下一个编号
	 */
	public synchronized String generaterNextNumber(String sno) {
		String id = null;
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		if (sno == null) {
			id = formatter.format(date) + "0001";
		} else {
			int count = SERIAL_NUMBER.length();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < count; i++) {
				sb.append("0");
			}
			DecimalFormat df = new DecimalFormat("0000");
			id = formatter.format(date)
					+ df.format(1 + Integer.parseInt(sno.substring(8, 12)));
		}
		return id;
	}
}
