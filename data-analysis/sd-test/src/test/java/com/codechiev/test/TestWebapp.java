package com.codechiev.test;
import com.codechiev.test.JettyServer;

public class TestWebapp {
	public static void main(String[] args) throws Exception {
		JettyServer server = new JettyServer(8080);
		server.setDomain("/");
		server.setWebapp("../webapp");
		server.startup();
	}
}
