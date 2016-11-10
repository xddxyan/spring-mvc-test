package com.metasoft.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.support.XmlWebApplicationContext;

//@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent e) {
		XmlWebApplicationContext xml = (XmlWebApplicationContext) e.getSource();
		if( null==xml.getNamespace() || xml.getNamespace().indexOf("app-servlet") < 0 ){
			return;
		}
		
		/*startup initialize*/
	}

}
