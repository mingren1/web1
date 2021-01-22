package com.wenbronk.le.iris.jmx;

/**
 * 必须实现 Mbean, 才可以进行注册
 */
public class Hello implements HelloMBean {

    private String name;
    private String age;

    
    public Hello() {
		super();
	}
	public Hello(String name, String age) {
		super();
		this.name = name;
		this.age = age;
	}
	@Override
    public String getName() {
        System.out.println("get name::" + name);
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
        System.out.println("set name  " + name);
    }
    @Override
    public String getAge() {
        System.out.println("get age::" + age);
        return age;
    }
    @Override
    public void setAge(String age) {
        this.age = age;
        System.out.println("set age  " + age);
    }
    @Override
    public void helloWorld() {
        System.out.println("hello world");
    }
    @Override
    public void helloWorld(String str) {
        System.out.println("hello world " + str);
    }
    @Override
    public void getTelephone() {
        System.out.println("get telephone");
    }
    
    
}