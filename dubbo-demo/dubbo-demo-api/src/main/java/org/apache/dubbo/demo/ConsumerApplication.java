package org.apache.dubbo.demo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

public class ConsumerApplication {

	public static void main(String[] args) {
		ApplicationConfig app = Application.getAppInstance("genericAppConsumer");
		RegistryConfig registry = Application.getRegistInstance("localhost:2181", "zookeeper");
		ReferenceConfig service = Application.getReference("MyGenericService");
		service.setApplication(app);
		service.setRegistry(registry);
		GenericService genericService = (GenericService) service.get();
		Object result = genericService.$invoke("sayHello", new String[] { "java.lang.String" },
				new Object[] { "world" });
		System.out.println(result);
		try {
			Thread.sleep(1000 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
