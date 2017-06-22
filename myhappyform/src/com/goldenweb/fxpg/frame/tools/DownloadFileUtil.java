package com.goldenweb.fxpg.frame.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description: TODO(文件下载的工具类)
 * @author Lee 
 * @date 2014-2-11 上午10:23:12
 */
public class DownloadFileUtil {
 
	/**
	 * @Description: TODO(转成UT-8编码)
	 * @Title toUtf8String
	 * @param s
	 * @return String
	 * @author Lee
	 * @time 2014-2-13 下午4:33:37
	 */
	public static String toUtf8String(String s){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= '\377') {
				sb.append(c);
			} else {
				byte b[];
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) {
						k += 256;
					}
					sb.append((new StringBuilder("%")).append(
							Integer.toHexString(k).toUpperCase()).toString());
				}

			}
		}

		return sb.toString();
	}
	
	/**
	 * @Description: TODO(设置头信息，完成文件下载,支持windows和linux传入路径区别  windows用"\" linux 用"/")
	 * @Title downloadFileWithouTranscoding
	 * @param response
	 * @param fileNameWithPath 带路径的文件名
	 * @param fileName 下载下来的文件的文件名
	 * @throws Exception
	 * @author Lee
	 * @time 2014-2-13 下午4:21:54
	 */
	public static void downloadFileWithouTranscoding(HttpServletResponse response,
			String fileNameWithPath, String fileName) {
		try {
			// 下载文件时显示的文件名，一定要经过这样的转换，否则乱码
			fileName = URLEncoder.encode(fileName, "GB2312");
			fileName = URLDecoder.decode(fileName, "ISO8859-1");
			File file = new File(fileNameWithPath);
			FileInputStream fileinputstream = new FileInputStream(file);
			long l = file.length();
			int k = 0;
			byte abyte0[] = new byte[65000];
			response.setContentType("application/x-msdownload");
			response.setContentLength((int) l);
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName);
			while (k < l) {
				int j;
				j = fileinputstream.read(abyte0, 0, 65000);
				k += j;
				response.getOutputStream().write(abyte0, 0, j);
			}
			fileinputstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
