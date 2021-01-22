package com.wenbronk.le.iris.jmx;


import com.wenbronk.le.iris.jmx.HelloMBean;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * 远程连接jmx
 */
public class Client {

    public static void main(String[] args) throws Exception {
        JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
        JMXConnector connect = JMXConnectorFactory.connect(jmxServiceURL, null);

        MBeanServerConnection mBeanServerConnection = connect.getMBeanServerConnection();

        String[] domains = mBeanServerConnection.getDomains();
        for (int i = 0; i < domains.length; i++) {
            String obj = domains[i];
            System.out.printf("domian[%d] = %s", i, domains[i].toString());
            System.out.println();
        }

        // 注册名和之前server的一致
        ObjectName objectName = new ObjectName("com.wenbronk.le.jmxBean:name=Hello");
        // 获取参数
        getParam(mBeanServerConnection, objectName);
        System.out.println("get params======================");
        // 更改参数
        changeParams(mBeanServerConnection, objectName);
        getParam(mBeanServerConnection, objectName);
        System.out.println("set params======================");

        useMethod(mBeanServerConnection, objectName);
        getParam(mBeanServerConnection, objectName);
        System.out.println("useMethod.========================");
    }

    /**
     * 对method的调用, 采用反射的方式进行
     */
    public static void useMethod(MBeanServerConnection connection, ObjectName objectName) {
        HelloMBean helloMBean = MBeanServerInvocationHandler.newProxyInstance(connection, objectName, HelloMBean.class, true);
        String age = helloMBean.getAge();
        String name = helloMBean.getName();
        helloMBean.setAge("2323223");
        helloMBean.helloWorld("nchar");
    }

    /**
     * 可进行相关参数修改
     * 通过setAttribute、getAttrubute方法来进行操作，则属性的首字母要大写
     */
    public static void changeParams(MBeanServerConnection mBeanServerConnection, ObjectName objectName) throws AttributeNotFoundException, InvalidAttributeValueException, ReflectionException, IOException, InstanceNotFoundException, MBeanException {
        mBeanServerConnection.setAttribute(objectName, new Attribute("Name", "hangzhou"));
        mBeanServerConnection.setAttribute(objectName, new Attribute("Age", "1990"));
    }

    /**
     * 获取参数
     */
    public static void getParam(MBeanServerConnection connection, ObjectName objectName) throws AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException, IOException {
        String age = (String) connection.getAttribute(objectName, "Age");
        String name = (String) connection.getAttribute(objectName, "Name");
        System.out.println("name " + name + " age: " + age);
    }
}