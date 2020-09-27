package com.itmayiedu.service.syslog.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;







@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "config")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigList {

	@XmlElement(name = "system")
    private List<SystemName> configList;
}
