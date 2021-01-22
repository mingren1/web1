package com.wenbronk.le.iris.jmx;

import com.wenbronk.le.iris.jmx.Hello;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class HelloAgent {

    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        Hello hello = new Hello();
        // com.le.iris:type=ZhixinSource/QPS  域名：name=MBean名称
        ObjectName helloName  = new ObjectName("com.wenbronk.le.jmxBean:name=" + hello.getClass().getName());

        ObjectInstance objectInstance = server.registerMBean(hello, helloName);

        Thread.sleep(60*1000*1000);
    }
}