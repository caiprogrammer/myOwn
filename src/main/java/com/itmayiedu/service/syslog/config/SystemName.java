package com.itmayiedu.service.syslog.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "system")
@XmlAccessorType(XmlAccessType.FIELD)
public class SystemName {
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "path")
    private String path;
    @XmlAttribute(name = "classname")
    private String classname;
    @XmlAttribute(name = "protocol")
    private String protocol;
    @XmlAttribute(name = "generalization")
    private String generalization;
    @XmlAttribute(name = "port")
    private String port;

}
