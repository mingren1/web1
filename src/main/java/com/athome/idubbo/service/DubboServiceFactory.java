package com.athome.idubbo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.athome.idubbo.domain.RequestDto;

public class DubboServiceFactory {

	private ApplicationConfig application;
    private RegistryConfig registry;

    private static class SingletonHolder {
        private static DubboServiceFactory INSTANCE = new DubboServiceFactory();
    }

    private DubboServiceFactory(){
        Properties prop = new Properties();
        ClassLoader loader = DubboServiceFactory.class.getClassLoader();

        try {
            prop.load(loader.getResourceAsStream("dubboconf.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(prop.getProperty("application.name")); 
        //����������dubbo��application��Ϣ*(demoֻ������name)*�����demoû�ж����dubbo.xml�����ļ�
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(prop.getProperty("registry.address")); 
        //��������dubbo��ע��������Ϣ�����demoû�ж����dubbo.xml�����ļ�

        this.application = applicationConfig;
        this.registry = registryConfig;

    }

    public static DubboServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Object genericInvoke(String interfaceClass, String methodName, List<Map<String, Object>> parameters){

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(application); 
        reference.setRegistry(registry); 
        reference.setInterface(interfaceClass); // �ӿ��� 
        reference.setGeneric(true); // ����Ϊ�����ӿ� 
        
        //ReferenceConfigʵ�����أ���װ����ע�����ĵ������Լ����ṩ�ߵ����ӣ�
        //��Ҫ���棬�����ظ�����ReferenceConfig��������������Ⲣ�һ����ڴ������й©��
        //API��ʽ���ʱ�����׺��Դ����⡣
        //����ʹ��dubbo���õļ򵥻��湤������л���
        GenericService genericService = reference.get();
        
//        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
//        GenericService genericService = cache.get(reference); 
        
        // ��com.alibaba.dubbo.rpc.service.GenericService����������нӿ����� 

        int len = parameters.size();
        String[] invokeParamTyeps = new String[len];
        Object[] invokeParams = new Object[len];
        for(int i = 0; i < len; i++){
            invokeParamTyeps[i] = parameters.get(i).get("ParamType") + "";
            invokeParams[i] = parameters.get(i).get("Object");
        }
        return genericService.$invoke(methodName, invokeParamTyeps, invokeParams);
    }
    
    
    public static void main(String[] args) {
    	RequestDto dto = new RequestDto();
    	dto.setInterfaceName("com.athome.idubbo.service.UserInfoService");
    	dto.setMethodName("getUser");
    	Map[] methodParams = new Map[1];
    	methodParams[0] = new HashMap<>();
    	methodParams[0].put("id", "001");
		dto.setMethodParam(methodParams);
    	Map map = new HashMap();
        map.put("ParamType", "java.lang.String");  //��˽ӿڲ�������
        map.put("Object", dto.getMethodParams()[0].get("id"));  //���Ե��ú�˽ӿڵ�ʵ��

        List<Map<String, Object>> paramInfos= new ArrayList<Map<String,Object>>();
        paramInfos.add(map);

        DubboServiceFactory dubbo = DubboServiceFactory.getInstance();

        Object genericInvoke = dubbo.genericInvoke(dto.getInterfaceName(), dto.getMethodName(), paramInfos);
        System.out.println(genericInvoke);
	}
}
