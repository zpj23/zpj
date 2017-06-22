package com.goldenweb.sys.util;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import sun.misc.BASE64Decoder;

public class DESCipherCrossoverDelphi {
	Key key;

	/*public Test(String str) throws FileNotFoundException,
			IOException {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword("passsecret");
		Properties p = getProperties(encryptor);
		String pass = p.getProperty("rules");
		setKey(pass + str);

	}*/

	public DESCipherCrossoverDelphi(Object str) throws FileNotFoundException,
			IOException {
		setKey(str.toString());
	}

	public DESCipherCrossoverDelphi() {
		 //setKey("final");
		 setKey("12345678");
	}

	/**
	 * 根据参数生成KEY
	 */
	public void setKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			_generator.init(new SecureRandom(strKey.getBytes()));
			this.key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		}
	}

	/**
	 * 加密String明文输入,String密文输出
	 */
	public String getEncString(String strMing) {
		/*byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("UTF8");
			byteMi = this.getEncCode(byteMing);
			strMi = base64en.encode(byteMi);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;*/
		return strMing;
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public String getDesString(String strMi) {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = base64De.decodeBuffer(strMi);
			byteMing = this.getDesCode(byteMi);
			strMing = new String(byteMing, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	private byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	public static void main(String args[]) throws FileNotFoundException,
			IOException {
		 // 设置密钥
		 // des.setKey("parameter加密");
		
		//String des="mima1234";
		
		 String str1 = "1";
		 // DES加密
		 // String str2 = des.getEncString(des);  
		// String deStr = new Test("oa").getDesString(des);
		 /*String min = "[{\"BB\":770,\"ZDXM\":701,\"ZDZC\":13,\"ZDJC\":56,\"ZDCQ\":327,\"QYGZ\":7,\"SNLY\":2,\"CSGH\":46,\"JY\":18,\"WS\":3,\"HJBH\":9,\"AQSC\":10,\"SPYPAQ\":2,\"LDBZ\":1,\"CXJS\":174,\"SHGL\":11,\"QT\":149},{\"WC\":595},{\"AGREE\":593,\"ZDXM\":545,\"ZDZC\":7,\"ZDJC\":41,\"ZDCQ\":245,\"QYGZ\":4,\"SNLY\":1,\"CSGH\":42,\"JY\":10,\"WS\":1,\"HJBH\":7,\"AQSC\":8,\"SPYPAQ\":1,\"LDBZ\":0,\"CXJS\":141,\"SHGL\":9,\"QT\":119},{\"DELAY\":2,\"ZDXM\":1,\"ZDZC\":1,\"ZDJC\":0,\"ZDCQ\":0,\"QYGZ\":1,\"SNLY\":0,\"CSGH\":0,\"JY\":0,\"WS\":0,\"HJBH\":0,\"AQSC\":0,\"SPYPAQ\":0,\"LDBZ\":0,\"CXJS\":1,\"SHGL\":0,\"QT\":0},{\"REJECT\":0,\"ZDXM\":0,\"ZDZC\":0,\"ZDJC\":0,\"ZDCQ\":0,\"QYGZ\":0,\"SNLY\":0,\"CSGH\":0,\"JY\":0,\"WS\":0,\"HJBH\":0,\"AQSC\":0,\"SPYPAQ\":0,\"LDBZ\":0,\"CXJS\":0,\"SHGL\":0,\"QT\":0}]";
		 min="asd";*/
		 String deStr = new DESCipherCrossoverDelphi().getEncString(str1);
		System.out.println("密文:" + deStr);
		 // DES解密
		 System.out.println("明文:" + new DESCipherCrossoverDelphi().getDesString("H3Zy4txGn88="));
	}

	/**
	 * 给desrules.properties解密
	 * 
	 * @return
	 * @throws IOException
	 */
	public Properties getProperties(StandardPBEStringEncryptor encryptor)
			throws IOException {
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("/desrules.properties");
		Properties p = new EncryptableProperties(encryptor);
		p.load(in);
		return p;
	}

}