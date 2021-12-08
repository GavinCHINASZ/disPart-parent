package com.dispart.utils;

import com.dispart.vo.RequestMsg;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;

public class ConvertUtil {

    public static String beanToXml(RequestMsg msg) {

        XStream xs = new XStream(new Xpp3Driver(new NoNameCoder()));
        xs.autodetectAnnotations(true);
        xs.aliasSystemAttribute(null, "class");

        return xs.toXML(msg);
    }

    public static <T> T xmlToBean(String xml, Class<T> clazz) {

        XStream xs = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xs);
        xs.allowTypeHierarchy(clazz);
        xs.processAnnotations(clazz);

        return (T)xs.fromXML(xml);
    }

}
