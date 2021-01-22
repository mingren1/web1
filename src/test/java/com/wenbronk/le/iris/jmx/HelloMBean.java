package com.wenbronk.le.iris.jmx;


/**
 * 实现接口, 可在jconsoler中调用属性
 */
public interface HelloMBean {

    public String getName();

    public void setName(String name);

    public String getAge();

    public void setAge(String age);

    public void helloWorld();

    public void helloWorld(String str);

    public void getTelephone();

}