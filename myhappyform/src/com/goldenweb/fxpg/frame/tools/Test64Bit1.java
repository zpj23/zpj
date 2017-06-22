package com.goldenweb.fxpg.frame.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
public class Test64Bit1 {

	
	public static void main(String[] args) throws UnsupportedEncodingException
    {
        String strImg = GetImageStr(null,null);
        
        GenerateImage(strImg);
    }
	
	
	
    public static String GetImageStr(String mainPath,String url)
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
    	String baseurl = ServletActionContext.getServletContext().getRealPath("/").replaceAll("\\\\", "/");
    	baseurl =baseurl.replace(mainPath+"/", "");
        String imgPath = baseurl+mainPath+"/"+url;//待处理的图片
        InputStream in = null;
        byte[] data = null;
        String imgData = "";
        //读取图片字节数组
        try 
        {	
        	File imgFile = new File(imgPath);
        	if(imgFile.exists()){
	            in = new FileInputStream(imgFile);        
	            data = new byte[in.available()];
	            in.read(data);
	            in.close();
	            //对字节数组Base64编码
	            BASE64Encoder encoder = new BASE64Encoder();
	            imgData = encoder.encode(data);
        	}
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return imgData;//返回Base64编码过的字节数组字符串
    }
    public static boolean GenerateImage(String imgStr)
    {//对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try 
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "d:\\222.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);    
            out.write(b);
            out.flush();
            out.close();
            return true;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }
	
	
}  
