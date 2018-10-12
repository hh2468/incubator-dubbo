package org.apache.dubbo.demo.generic.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GenericSerivceImpl {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "META-INF/spring/generic/dubbo-demo-provider.xml" });
		context.start();
		System.in.read(); // press any key to exit
	}

}
