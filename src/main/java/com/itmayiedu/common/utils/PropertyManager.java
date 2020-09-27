package com.itmayiedu.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

/**   
 * 属性文件操作工具类
 */
public class PropertyManager {
    private Properties properties = null;
    private Object propertiesLock = new Object();
    private String resourceURI = "/conf/eventserver/system.properties";

    /**
     * 创建新的属性管理器
     */
    public PropertyManager() {
    	
    }    
    /**
     * 创建新的属性管理器
     */
    public PropertyManager(String resourceURI) {
        this.resourceURI = resourceURI;
    }
    /**
     * 取得属性值,无值时返回默认值
     * @param name String 属性名称
     * @param defaultValue String 属性名称
     * @return String 属性值
     */
    public String getProperty(String name,String defaultValue) {
          if (properties == null) {
            synchronized(propertiesLock) {
                if (properties == null) {
                    loadProps();
                }
            }
        }
        String property = properties.getProperty(name);
        if (property == null) {
            return defaultValue;
        }
        else {
            return property.trim();
        }
    }
    /**
     * 取得属性值,无值时返回默认值
     * @param name String 属性名称
     * @param defaultValue long 属性名称
     * @return long 属性值
     */
    public long getProperty(String name,long defaultValue) {
          if (properties == null) {
            synchronized(propertiesLock) {
                if (properties == null) {
                    loadProps();
                }
            }
        }
        String property = properties.getProperty(name);
        if (property == null) {
            return defaultValue;
        }
        else {
             return Long.parseLong(property.trim());
        }
    }
    /**
     * 取得属性值,无值时返回默认值
     * @param name String 属性名称
     * @param defaultValue int 属性名称
     * @return int 属性值
     */
    public int getProperty(String name,int defaultValue) {
          if (properties == null) {
            synchronized(propertiesLock) {
                if (properties == null) {
                    loadProps();
                }
            }
        }
        String property = properties.getProperty(name);
        if (property == null) {
            return defaultValue;
        }
        else {
             return Integer.parseInt(property.trim());
        }
    }
    /**
     * 取得属性值
     * @param name String 属性名称
     * @return String 属性值
     */
    public String getProperty(String name) {
          return getProperty(name,null);
    }
    /**
     * 设置属性值
     * @param name String 属性名称
     * @param value String 属性值
     */
    public void setProperty(String name, String value) {
         synchronized (propertiesLock) {
             if (properties == null) {
                loadProps();
            }
            properties.setProperty(name, value);
            saveProps();
        }
    }
    /**
     * 删除属性值
     * @param name String 属性名称
     */
    public void deleteProperty(String name) {
        synchronized (propertiesLock) {
             if (properties == null) {
                loadProps();
            }
            properties.remove(name);
            saveProps();
        }
    }
    /**
     * 枚举属性
     * @return Enumeration 返回Enumeration型属性
     */
    public Enumeration propNames() {
        if (properties == null) {
            synchronized(propertiesLock) {
                //Need an additional check
                if (properties == null) {
                    loadProps();
                }
            }
        }
        return properties.propertyNames();
    }

    /**
     * 加载属性
     */
    private void loadProps() {
        properties = new Properties();
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream(resourceURI);
            properties.load(in);
        }
        catch (Exception e) {
            System.err.println("Error reading Application properties in PropertyManager.loadProps() " + e);
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
            } catch (Exception e) { }
        }
    }

    /**
     * 保存属性
     */
    private void saveProps() {
        String path = properties.getProperty("path").trim();
        OutputStream out = null;
        try {
            out = new FileOutputStream(path);
            properties.store(out, "properties -- " + (new java.util.Date()));
        }
        catch (Exception ioe) {
             ioe.printStackTrace();
        }
        finally {
            try {
               out.close();
            } catch (Exception e) { }
        }
    }
    /**
     * 属性文件是否可读
     * @return boolean 返回true或false
     */
    public boolean propFileIsReadable() {
        try {
            InputStream in = getClass().getResourceAsStream(resourceURI);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * 属性文件是否存在
     * @return boolean 返回true或false
     */
    public boolean propFileExists() {
        String path = getProperty("path");
        File file = new File(path);
        if (file.isFile()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 属性文件是否可写
     * @return boolean 返回true或false
     */
    public boolean propFileIsWritable() {
        String path = getProperty("path");
        File file = new File(path);
        if (file.isFile()) {
            if (file.canWrite()) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}

