package com.goldenweb.fxpg.frame.tools;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @Description: TODO(BASE64加解密)
 * @author Lee 
 * @date 2014-2-11 上午9:37:38
 */
public class BASE64 {
	            
    /**
     * @Description: TODO(BASE64解密)
     * @Title decryptBASE64
     * @param key
     * @return byte[]
     * @throws Exception
     * @author Lee
     * @time 2014-2-13 下午1:58:57
     */
    public static byte[] decryptBASE64(String key) throws Exception {             
        return (new BASE64Decoder()).decodeBuffer(key);             
    }             
   
    /**
     * @Description: TODO(BASE64加密)
     * @Title encryptBASE64
     * @param key
     * @return String
     * @throws Exception 
     * @author Lee
     * @time 2014-2-13 下午1:59:35
     */
    public static String encryptBASE64(byte[] key) throws Exception {             
        return (new BASE64Encoder()).encodeBuffer(key);             
    }     
}
