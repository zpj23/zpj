package com.goldenweb.fxpg.frame.tools;

import java.io.File;
import java.io.IOException;

import freemarker.template.Configuration;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-21 上午11:14:56
 */
public class FreeMakerUtil {

	private static Configuration config = null;
	   
    private FreeMakerUtil() {  
       // Exists only to defeat instantiation.  
    }  
   
    public static Configuration getConfigurationInstance() throws IOException{
    	 if (config == null) {  
      	   	config = new Configuration();
     		config.setDirectoryForTemplateLoading(new File("/template/porlet"));
         }  
         return config; 
    }
    
}
