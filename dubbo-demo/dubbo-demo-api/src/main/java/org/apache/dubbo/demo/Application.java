package org.apache.dubbo.demo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
	static Logger log = LoggerFactory.getLogger(Application.class);

	public static ApplicationConfig getAppInstance(String name) {
		ApplicationConfig application = new ApplicationConfig();
		application.setName(name);
		return application;
	}

	public static RegistryConfig getRegistInstance(String address, String protocol) {
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress(address);
		registry.setProtocol(protocol);
		return registry;
	}

	public static ProtocolConfig getProtocolInstance(String protocolName) {
		ProtocolConfig protocol = new ProtocolConfig();
		protocol.setName(protocolName);
		protocol.setPort(12345);
		protocol.setThreads(10);
		return protocol;

	}

	public static GenericService getService() {
		GenericService myService = new MyGenericService();
		return myService;
	}

	public static ServiceConfig exportGeneric(String interfaceName, GenericService myService) {
		// 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
		ServiceConfig<GenericService> service = new ServiceConfig<GenericService>();
		// 弱类型接口名
		service.setInterface(interfaceName);
		service.setVersion("1.0.0");
		// 指向一个通用服务实现
		service.setRef(myService);
		// 暴露及注册服务
		return service;
	}

	public static ReferenceConfig getReference(String interfaceName) {
		ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
		// 弱类型接口名
		reference.setInterface(interfaceName);
		reference.setVersion("1.0.0");
		// 声明为泛化接口
		reference.setGeneric(true);
		return reference;
	}

	public static void main(String[] args) {
		ApplicationConfig app = getAppInstance("genericApp");
		RegistryConfig registry = getRegistInstance("localhost:2181", "zookeeper");
		ProtocolConfig protocol = getProtocolInstance("dubbo");
		ServiceConfig server = exportGeneric("MyGenericService", getService());
		server.setApplication(app);
		server.setRegistry(registry);
		server.setProtocol(protocol);
		server.export();
		log.error("started! ");
		try {
			Thread.sleep(1000 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
