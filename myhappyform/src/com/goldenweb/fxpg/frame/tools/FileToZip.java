package com.goldenweb.fxpg.frame.tools;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

/**
 * @Description: TODO(文件打包、解压缩)
 * @author Lee 
 * @date 2014-2-11 上午10:27:18
 */
public class FileToZip {

	/**
	 * @Description: TODO(压缩)
	 * @Title zipFile
	 * @param srcFolder 需要压缩文件的路径
	 * @param destFile 压缩后文件的路径
	 * @throws Exception
	 * @author Lee
	 * @time 2014-2-14 下午3:40:54
	 */
	public static void zipFile(File srcFolder, File destFile) throws Exception {
	    Project prj = new Project();
	    Zip zip = new Zip();
	    zip.setProject(prj);
	    zip.setDestFile(destFile);
	    FileSet fileSet = new FileSet();
	    fileSet.setProject(prj);
	    fileSet.setDir(srcFolder); 
	    zip.addFileset(fileSet);
	    zip.execute();
	}
	
	/**
	 * @Description: TODO(解压)
	 * @Title unzipFile
	 * @param srcFile
	 * @param destFolder
	 * @throws Exception
	 * @author Lee
	 * @time 2014-2-14 下午3:59:57
	 */
	public static void unzipFile(File srcFile, File destFolder)
	        throws Exception {
	    Project prj = new Project();
	    Expand expand = new Expand();
	    expand.setProject(prj);
	    expand.setSrc(srcFile);
	    expand.setOverwrite(true);
	    expand.setDest(destFolder);
	    expand.execute();
	}
	 
	/**
	 * @Description: TODO(删除文件)
	 * @Title delDir
	 * @param file
	 * @author Lee
	 * @time 2014-2-14 下午4:04:07
	 */
	public static void delDir(File file){
		File[] files=file.listFiles();
		for(int k=0;k<files.length;k++){
			File file2=files[k];
			if(file2.isDirectory()){
				delDir(file2);
			}else{
				file2.delete();
			}
		}
		file.delete();
	}
}
