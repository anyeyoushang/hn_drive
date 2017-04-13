/*
 * @(#) test.java 2015年7月27日
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package config.anno;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * @Description 
 * 
 * @author mn.qiao
 * @version 1.0
 * @since 2015年7月27日
 */
public class ClassSearcher {
	private static List<File> classFiles=new ArrayList<File>();
	/**
	 * 
	 * 
	 * @author mn.qiao
	 * @since 2015年7月27日
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes" })
	public static List<Class> findModelClass() throws ClassNotFoundException{
		classFiles=null;
		classFiles=new ArrayList<File>();
		List<Class> classList=new ArrayList<Class>();
			
			String path=ClassSearcher.class.getResource("/").getFile();//文件根目录
			List<File> fileList=findFiles(path,".class");
			for(File f:fileList){
				String className=className(f,"/classes");
				Class<?> classFile=Class.forName(className);
				if(classFile.getSuperclass()==Model.class){
					classList.add(classFile);
				}
			}
		
		return classList;
	}
	
	
	/**
	 * 
	 * 
	 * @author mn.qiao
	 * @since 2015年7月27日
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes" })
	public static List<Class> findControllerClass() throws ClassNotFoundException{
		classFiles=null;
		classFiles=new ArrayList<File>();
		List<Class> classList=new ArrayList<Class>();
		String path=ClassSearcher.class.getResource("/").getFile();//文件根目录
		List<File> fileList=findFiles(path,".class");
		for(File f:fileList){
			String className=className(f,"/classes");
			Class<?> classFile=Class.forName(className);
			if(classFile.getSuperclass()==Controller.class 
					|| (classFile.getSuperclass()!=null&&classFile.getSuperclass().getSuperclass()==Controller.class)
					){
				classList.add(classFile);
			}
		}
		
		return classList;
	}
	
	/**
	 * 
	 * 
	 * @author mn.qiao
	 * @since 2015年7月27日
	 * @param path
	 * @param fileName
	 * @return
	 */
	private static List<File> findFiles(String path,String fileName){
		File baseDir=new File(path);
		if(!baseDir.exists()||!baseDir.isDirectory()){//文件是否存在
			return null;
		}else{
			File[] fileList=baseDir.listFiles();//下面的所有文件
			for(File f:fileList){
				if(f.isDirectory()==true){//是否有效文件
					findFiles(f.getPath(),fileName);
				}else{
					if(f.isFile()&&f.getName().indexOf(fileName)>0){
						classFiles.add(f);
					}
				}
			}
		}
		return classFiles;
	}
	
	
	/**
	 * 
	 * 
	 * @author mn.qiao
	 * @since 2015年7月27日
	 * @param classFile
	 * @param pre
	 * @return
	 */
	private static String className(File classFile,String pre){
		String path=classFile.toString().replace("\\", "/");
		String className=path.substring(path.indexOf(pre)+pre.length(),
				path.indexOf(".class"));
		if(className.startsWith("/")){
			className=className.substring(className.indexOf("/")+1);
		}
		className=className.replace("/", ".");
		return className;
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		classFiles=null;
		classFiles=new ArrayList<File>();
		List<Class> classList=new ArrayList<Class>();
		String path=ClassSearcher.class.getResource("/").getFile();//文件根目录
		List<File> fileList=findFiles(path,".class");
		for(File f:fileList){
			String className=className(f,"/classes");
			Class<?> classFile=Class.forName(className);
			if(classFile.getSuperclass()==Controller.class 
					|| (classFile.getSuperclass()!=null&&classFile.getSuperclass().getSuperclass()==Controller.class)
					){
				classList.add(classFile);
			}
		}
	}
	
	
}
