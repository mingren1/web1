package web1.home.test;


import java.lang.management.ManagementFactory;
 
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
 
public class JMXdemo {
	public static void main(String[] args) throws MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, InterruptedException {
		MBeanServer server=ManagementFactory.getPlatformMBeanServer();
		ObjectName objectName=new ObjectName("jmx:type=User");
		User bean=new User();
		server.registerMBean(bean, objectName);
		String oldName=null;
		String oldPwd=null;
		System.out.println("jmx started!!!");
		while(true){
			if(oldName!=bean.getName()|| oldPwd !=bean.getPasswd()){
				System.out.println(bean.getName()+":"+bean.getPasswd());
				oldName=bean.getName();
				oldPwd=bean.getPasswd();
			}
			Thread.sleep(1000);
		}
	}
 
}