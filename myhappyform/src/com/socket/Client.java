package com.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Client {
//	public static void main(String args[]) throws Exception {  
//	      //为了简单起见，所有的异常都直接往外抛  
//	      String host = "127.0.0.1";  //要连接的服务端IP地址  
//	      int port = 8899;   //要连接的服务端对应的监听端口  
//	      //与服务端建立连接  
//	      Socket client = new Socket(host, port);  
//	      //建立连接后就可以往服务端写数据了  
//	      Writer writer = new OutputStreamWriter(client.getOutputStream());  
//	      writer.write("Hello Server.");  
//	      writer.flush();//写完后要记得flush  
//	      writer.close();  
//	      client.close();  
//	   }  
//	public static void main(String args[]) throws Exception {  
//	      //为了简单起见，所有的异常都直接往外抛  
//	     String host = "127.0.0.1";  //要连接的服务端IP地址  
//	     int port = 8899;   //要连接的服务端对应的监听端口  
//	     //与服务端建立连接  
//	     Socket client = new Socket(host, port);  
//	      //建立连接后就可以往服务端写数据了  
//	     Writer writer = new OutputStreamWriter(client.getOutputStream());  
//	      writer.write("Hello Server.");  
//	      writer.write("eof");  
//	      writer.flush();  
//	      //写完以后进行读操作  
//	     Reader reader = new InputStreamReader(client.getInputStream());  
//	      char chars[] = new char[64];  
//	      int len;  
//	      StringBuffer sb = new StringBuffer();  
//	      String temp;  
//	      int index;  
//	      while ((len=reader.read(chars)) != -1) {  
//	         temp = new String(chars, 0, len);  
//	         if ((index = temp.indexOf("eof")) != -1) {  
//	            sb.append(temp.substring(0, index));  
//	            break;  
//	         }  
//	         sb.append(new String(chars, 0, len));  
//	      }  
//	      System.out.println("from server: " + sb);  
//	      writer.close();  
//	      reader.close();  
//	      client.close();  
//	   }  
	
	  public static void main(String args[]) throws Exception {  
	      //为了简单起见，所有的异常都直接往外抛  
	     String host = "127.0.0.1";  //要连接的服务端IP地址  
	     int port = 8899;   //要连接的服务端对应的监听端口  
	     //与服务端建立连接  
	     Socket client = new Socket(host, port);  
	      //建立连接后就可以往服务端写数据了  
	     Writer writer = new OutputStreamWriter(client.getOutputStream(), "GBK");  
	      writer.write("你好，服务端。");  
	      writer.write("eof\n");  
	      writer.flush();  
	      //写完以后进行读操作  
	     BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));  
	      //设置超时间为10秒  
	     client.setSoTimeout(10*1000);  
	      StringBuffer sb = new StringBuffer();  
	      String temp;  
	      int index;  
	      try {  
	         while ((temp=br.readLine()) != null) {  
	            if ((index = temp.indexOf("eof")) != -1) {  
	                sb.append(temp.substring(0, index));  
	                break;  
	            }  
	            sb.append(temp);  
	         }  
	      } catch (SocketTimeoutException e) {  
	         System.out.println("数据读取超时。");  
	      }  
	      System.out.println("服务端: " + sb);  
	      writer.close();  
	      br.close();  
	      client.close();  
	   }  
}
