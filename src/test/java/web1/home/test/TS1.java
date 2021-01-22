package web1.home.test;

import java.rmi.registry.LocateRegistry;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.tomcat.jni.Status;

public class TS1 {

	public static void main(String[] args) {
		try {
            String DOMAIN = "localhost";
            // 创建一个MBeanServer
            MBeanServer server = MBeanServerFactory.createMBeanServer(DOMAIN);
            // 用MBeanServer注册LoginStatsMBean
            // MBeanServer.registerMBean(Object，ObjectName)方法使用的参数有两个：一个是MBean实现的一个实例；另一个是类型ObjectName的一个对象-它用于唯一地标识该MBean
            server.registerMBean(new Status(), new ObjectName(DOMAIN + ":name=statusBean"));
            // 存取该JMX服务的URL：
            JMXServiceURL url = new JMXServiceURL("rmi", "127.0.0.1", 9589, "/jndi/rmi://localhost:" + 1099 + "/app");
            // start()和stop()来启动和停止 JMXConnectorServer
            JMXConnectorServer jmxServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
            System.out.println(url);
            // 在RMI上注册
            LocateRegistry.createRegistry(1099);
            jmxServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
