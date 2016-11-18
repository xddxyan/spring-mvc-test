package org.copycat.framework;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {

	public static void main(String[] args) throws Exception {
		JettyServer server = new JettyServer(8080);
		server.startup();
	}

	/**
	 * to control which parts of the container’s classpath should be processed
	 * for things like annotations, META-INF/resources,
	 * META-INF/web-fragment.xml and tlds inside META-INF.
	 *
	 * Normally, nothing from the container classpath will be included for
	 * processing. However, sometimes you will need to include some. For
	 * example, you may have some libraries that are shared amongst your webapps
	 * and thus you have put them into a $JETTY_HOME/lib directory. The
	 * libraries contain annotations and therefore must be scanned.
	 * 
	 * The value of this attribute is a regexp that defines which jars and class
	 * directories from the container’s classpath should be examined.
	 */
	private static String attribute = "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern";
	/**
	 * Jetty buffers static content for webapps such as HTML files, CSS files,
	 * images, etc. If you are using NIO connectors, =====Jetty uses memory-mapped files=====
	 *  to do this. The problem is that on Windows, memory mapping a file
	 * causes the file to lock, so that you cannot update or replace the file.
	 * Effectively this means that you have to stop Jetty to update a file.
	 */
	private static String parameter = "org.eclipse.jetty.servlet.Default.useFileMappedBuffer";
	private static String regex = ".*/jstl-[^/]*\\.jar$|.*/spring-webmvc-[^/]*\\.jar$|.*/sitemesh-[^/]*\\.jar$";
	private Server server = new Server();
	private int port;
	private String host;
	private String domain;

	{
		System.setProperty("spring.profiles.active", "development");
	}

	public JettyServer(int port) {
		this.port = port;
	}

	public JettyServer(String host, int port) {
		this.port = port;
		this.host = host;
	}

	public void startup() {
		try {
			open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void open() throws Exception {
		start();
		while (true) {
			char c = (char) System.in.read();
			if (c == '\n') {
				reload();
			} else if (c == 'q') {
				stop();
				break;
			}
		}
	}

	private void start() throws Exception {
		ServerConnector http = new ServerConnector(server);
		http.setHost(host);
		http.setPort(port);
		http.setIdleTimeout(30000);

		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setDescriptor("src/main/webapp/WEB-INF/web.xml");//servlet xml配置
		webAppContext.setContextPath(domain);
		webAppContext.setWar("src/main/webapp");//根目录
		webAppContext.getInitParams().put(parameter, "false");
		webAppContext.setAttribute(attribute, regex);

		server.addConnector(http);
		server.setHandler(webAppContext);
		server.setStopAtShutdown(true);

		server.start();
	}

	/**
	 * 重新加载构建的类
	 * 
	 * @throws Exception
	 */
	private void reload() throws Exception {
		WebAppContext context = (WebAppContext) server.getHandler();
		context.stop();
		WebAppClassLoader classLoader = new WebAppClassLoader(context);
		classLoader.addClassPath("target/classes");
		classLoader.addClassPath("target/test-classes");
		context.setClassLoader(classLoader);
		context.start();
	}

	private void stop() throws Exception {
		server.stop();
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
