package com.itmayiedu.service.syslog;

import java.util.List;

import com.itmayiedu.service.syslog.config.SystemName;

/**
 * config.xml配置文件接口
 * @author pengdan
 *
 */
public interface ConfigDataService {

	List<SystemName> listSystem() throws Exception;
}
