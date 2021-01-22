package com.athome.idubbo.service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;

public class ProviderServer {
	public static void main(String[] args) {


		ApplicationConfig application = new ApplicationConfig();
		application.setName("api-generic-provider");

		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("zookeeper://127.0.0.1:2181");

		application.setRegistry(registry);

		GenericService genericService = new GenericServiceImpl();

		ServiceConfig<GenericService> service = new ServiceConfig<GenericService>();
		service.setApplication(application);
		service.setInterface("com.alibaba.dubbo.samples.generic.api.HelloService");
		service.setRef(genericService);
		service.export();

		ServiceConfig<GenericService> service2 = new ServiceConfig<GenericService>();
		service2.setApplication(application);
		service2.setInterface("com.alibaba.dubbo.samples.generic.api.HiService");
		service2.setRef(genericService);
		service2.export();
	
	}
}
