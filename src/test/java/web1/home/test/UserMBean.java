package web1.home.test;


public interface UserMBean {
	String getName();
 
	void SetName(String name);
 
	String getPasswd();
 
	void SetPasswd(String pwd);
 
	int add(int x, int y);
}