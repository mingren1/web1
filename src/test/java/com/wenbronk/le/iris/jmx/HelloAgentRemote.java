package com.wenbronk.le.iris.jmx;

import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

/**
 * 开启后, 可通过jconsoler进行连接
 */
public class HelloAgentRemote {

    public static void main(String[] args) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        Hello hello = new Hello("vini", "23");// 试了下, name 和 type没啥区别
        ObjectName helloName  = new ObjectName("com.wenbronk.le.jmxBean:name=" + hello.getClass().getSimpleName());
        //ObjectName helloName1  = new ObjectName("com.wenbronk.le.jmxBean:type=" + hello.getClass().getSimpleName());

//        Hello testtt = hello;
//        ObjectName testtName = new ObjectName("com.wenbronk.le.jmxBean:type=" + testtt.getClass().getSimpleName());
        //ObjectName testtName1 = new ObjectName("com.wenbronk.le.jmxBean:name=" + testtt.getClass().getSimpleName());

        System.out.println(hello.getClass().getSimpleName());
        ObjectInstance instance = mbs.registerMBean(hello, helloName);
        //ObjectInstance instance3 = mbs.registerMBean(hello, helloName1);
//        ObjectInstance instance2 = mbs.registerMBean(testtt, testtName);
        //ObjectInstance instance4 = mbs.registerMBean(testtt, testtName1);

        try {
            // 注册一个端口并绑定
            Registry registry = LocateRegistry.createRegistry(9999);
            JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
                                                          //service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi
            JMXConnectorServer jmxConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(jmxServiceURL, null, mbs);

            jmxConnectorServer.start();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}