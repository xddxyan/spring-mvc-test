package com.test.codechiev;
import org.copycat.framework.JettyServer;

public class TestWebapp {
	public static void main(String[] args) throws Exception {
		JettyServer server = new JettyServer(8080);
		server.setDomain("/");
		server.startup();
	}
}
