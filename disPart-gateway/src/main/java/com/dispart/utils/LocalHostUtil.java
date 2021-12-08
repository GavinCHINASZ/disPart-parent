package com.dispart.utils;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

/**
 * @author:xts
 * @date:Created in 2021/7/9 13:07
 * @description
 * @modified by:
 * @version: 1.0
 */
public class LocalHostUtil {
    public static String getLocalIp(){
        InetAddress addr=null;
        try {
            addr=InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        byte[] ipAddr=addr.getAddress();
        String ipAddrStr="";
        for(int i=0;i<ipAddr.length;i++){
            if(i>0){
                ipAddrStr+=".";
            }
            ipAddrStr+=ipAddr[i]& 0xFF;
        }
        return ipAddrStr;
    }
    public static String getLocalPort(){
        MBeanServer beanServer= ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectsNames=null;
        try {
            objectsNames=beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                    Query.match(Query.attr("protocol"),Query.value("HTTP/1.1")));
            String port=objectsNames.iterator().next().getKeyProperty("port");
            return port;
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
        return null;
    }
}