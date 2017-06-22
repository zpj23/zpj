package com.goldenweb.fxpg.frame.tools;

import java.io.IOException;

/**
 * @Description: TODO(执行的第三方bat、dll文件类)
 * @author Lee 
 * @date 2014-2-11 上午10:36:29
 */
public class SpControlThread extends Thread {

	@Override
	public void run() {
		String cmd=" cmd.exe /c"+"start /min d:/file/control.bat";
		Runtime rt=Runtime.getRuntime();
		try {
			Process proc=rt.exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
