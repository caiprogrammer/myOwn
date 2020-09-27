package com.itmayiedu.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;










import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.itmayiedu.service.syslog.ConfigDataService;
import com.itmayiedu.service.syslog.config.ConfigList;
import com.itmayiedu.service.syslog.config.SystemName;
import com.itmayiedu.service.syslog.config.XmlBuilder;

@Service
public class ConfigDataServiceImpl implements ConfigDataService {

	@Override
	public List<SystemName> listSystem() throws Exception {
		        //读取Resource目录下的XML文件
		        Resource resource = new ClassPathResource("config.xml");
		        //利用输入流获取XML文件内容
		        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
		        StringBuffer buffer = new StringBuffer();
		        String line = "";
		        while ((line = br.readLine()) != null) {
		            buffer.append(line);
		        }
		        br.close();
		        //XML转为JAVA对象
		        ConfigList configList = (ConfigList) XmlBuilder.xmlStrToObject(ConfigList.class, buffer.toString());
		        return configList.getConfigList();
		    }
		
	

}
