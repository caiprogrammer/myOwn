package com.itmayiedu.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileUtil {
	
	
	private FileUtil() {}

	/**
	 * 读取文本文件内容,返回文本文件的内容字符串，以"\n"分割 
	 * @param filePathAndName String
	 *            带有完整绝对路径的文件名
	 * @return String 返回文本文件的内容
	 */
	public static String readAll(String filePathAndName){
		return readAll(filePathAndName, null);
	}
	/**
	 * 读取文本文件内容,返回文本文件的内容字符串，以"\n"分割
	 * @param filePathAndName String
	 *            带有完整绝对路径的文件名
	 * @param encoding String
	 *            文本文件打开的编码方式
	 * @return String 返回文本文件的内容
	 */
	public static String readAll(String filePathAndName, String encoding){
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(URLDecoder.decode(filePathAndName));
			InputStreamReader isr;
			if (encoding == null || "".equals(encoding)) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data + "\n");
				}
			} catch (Exception e) {
				return "";
			}
			st = str.toString();
		} catch (IOException es) {
			es.printStackTrace();
			st = "";
		}
		return st;
	}
	/**
	 * 读取第一行文本文件内容
	 * @param filePathAndName String
	 *            带有完整绝对路径的文件名
	 * @param encoding String
	 *            文本文件打开的编码方式
	 * @return String 返回第一行文本文件内容
	 */
	public static String readLine(String filePathAndName,long rowIndex, String encoding){
		String st = "";
		StringBuffer str = new StringBuffer("");
		long i = 0;
		try {
			FileInputStream fs = new FileInputStream(URLDecoder.decode(filePathAndName));
			InputStreamReader isr;
			if (encoding == null || "".equals(encoding)) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					if(rowIndex==i){
						str.append(data);
						break;
					}
					else{
						i++;
					}
				}
			} catch (Exception e) {
				return "";
			}
			st = str.toString();
		} catch (IOException es) {
			es.printStackTrace();
			return "";
		}
		return st;
	}
	/**
	 * 读取第一行文本文件内容
	 * @param filePathAndName String
	 *            带有完整绝对路径的文件名
	 * @return String 返回第一行文本文件内容
	 */
	public static String readLine(String filePathAndName){
		return readLine(filePathAndName,0,null);
	}
	/**
	 * 读取第n行文本文件内容
	 * @param filePathAndName String
	 *            带有完整绝对路径的文件名
	 * @return String 返回第n行文本文件内容
	 */
	public static String readLine(String filePathAndName,long rowIndex){
		return readLine(filePathAndName,rowIndex,null);
	}
	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String 文件内容
	 * @return boolean 是否创建成功
	 */
	public static boolean newFile(String filePathAndName, String fileContent) {
		return newFile(filePathAndName, fileContent, false);
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String 文件内容
	 * @param flag
	 *            boolean 是否追加内容，true为追加，false为新建
	 * @return boolean 是否创建成功
	 */
	public static boolean newFile(String filePathAndName, String fileContent, boolean flag) {

		String filePath = filePathAndName;
		try {
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath,flag);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();
			return true;
		} catch (Exception e) {
			filePath = "";
			System.out.println("新建文件操作出错");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 有编码方式的文件创建
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @param encoding
	 *            编码方式 例如 GBK 或者 UTF-8
	 * @return boolean 是否创建成功
	 */
	public static boolean newFile(String filePathAndName, String fileContent, String encoding) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			PrintWriter myFile = new PrintWriter(myFilePath, encoding);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
			return true;
		} catch (Exception e) {
			System.out.println("创建文件操作出错");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();
		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();

		}

	}


	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            String 如 c:/fqf
	 */
	public static String newFolder(String folderPath) {
		String filePath = folderPath;
		try {
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			filePath = "";
			e.printStackTrace();
		}
		return filePath;
	}
	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如c:/fqf
	 * @param fileContent
	 *            String
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 */
	public static boolean copyFile(String oldPath, String newPath) {
		boolean result = false;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				result = true;
			}else{
				result = false;
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			result = false;
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 */
	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public static void moveFile(String oldPath, String newPath) {
		boolean bn = copyFile(oldPath, newPath);
		if(bn) delFile(oldPath);
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * 多级目录创建
	 * 
	 * @param folderPath
	 *            准备要在本级目录下创建新目录的目录路径 例如 c:myf
	 * @param paths
	 *            无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 * @return String 返回创建文件后的路径 例如 c:myfac
	 */
	public static String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		try {
			String txt;
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			for (int i = 0; st.hasMoreTokens(); i++) {
				txt = st.nextToken();
				if (txts.lastIndexOf("/") != -1) {
					txts = newFolder(txts + txt);
				} else {
					txts = newFolder(txts + txt + "/");
				}
			}
		} catch (Exception e) {
			System.out.println("创建多级目录操作出错");
			txts = "";
			e.printStackTrace();
		}
		return txts;
	}
	/**
	 * 多级目录创建
	 * 
	 * @param folderPath 建立多级目录，一次创建
	 * @return String 返回创建文件后的路径 例如 c:\aa\bbb\cc
	 */
	public static String createFolders(String folderPath) {
		String txts = folderPath;
		try {
			File file = new File(txts);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			System.out.println("创建多级目录操作出错");
			txts = "";
			e.printStackTrace();
		}
		return txts;
	}
	  /**
	   * 判断指定的文件是否存在。
	   * @param fileName 要判断的文件的文件名
	   * @return 存在时返回true，否则返回false。
	   */
	  public static boolean isFileExist(String fileName) {
	    return new File(fileName).isFile();
	  }
		/**
		 * 得到文件夹里面的所有文件
		 * 
		 * @param path  String 文件夹路径 如 c:/fqf
		 */
		public static List GetAllFile(String path) {
			List arrList = new ArrayList();
			File file = new File(path);
			if (!file.exists()) {
				return arrList;
			}
			if (!file.isDirectory()) {
				return arrList;
			}
			String[] tempList = file.list();
			File temp = null;
			for (int i = 0; i < tempList.length; i++) {
				if (path.endsWith(File.separator)) {
					temp = new File(path + tempList[i]);
				} else {
					temp = new File(path + File.separator + tempList[i]);
				}
				if (temp.isFile()) {
					arrList.add(temp);
				}
			}
			return arrList;
		}
		/**
	    * 返回带扩展名的文件名
	    * @param　fileName 文件全路径名
	    * @return　shortFileName 文件名称
	    */
	    public static String getFileName(String fileName)
	    {
	        String shortFileName = fileName;
	        shortFileName = shortFileName.replace("\\", "/");
	        if(shortFileName.indexOf("/") > -1)
	              shortFileName = shortFileName.substring(shortFileName.lastIndexOf("/")+1,shortFileName.length());
	        return shortFileName;
	    }
	    /**
	    * 返回不带扩展名的文件名
	    * @param　fileName 文件名
	    * @return　shortFileName 文件名称
	    */
	    public static String getFileNameWithoutExt(String fileName)
	    {
	        String shortFileName = fileName;
	        if(fileName.indexOf('.') > -1)
	              shortFileName = fileName.substring(0, fileName.lastIndexOf('.'));
	        return shortFileName;
	    }	    
	    /**
	    * 返回文件扩展名,带"."
	    * @param　fileName 文件名
	    * @return　fileExt 文件名称
	    */
	    public static String getFileNameExt(String fileName)
	    {
	        String fileExt = "";
	        if(fileName.indexOf('.') > -1)
	              fileExt = fileName.substring(fileName.lastIndexOf('.'));
	        return fileExt;
	    }

	  public static void main(String [] arg){
		try {
			FileUtil.newFolder("/c:\\dd cc\\cc/dwwdc");
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
}
