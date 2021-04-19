package httpAdapter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import handlerFactory.HandlerFactory;

public class HttpServerFactory {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public void init() {
		logger.info("============================= INITIALIZING SERVER =============================");
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setName("server");

		// Create a Server instance.
		Server server = new Server(threadPool);

		// Create a ServerConnector to accept connections from clients.
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(13579);
		

		// Add the Connector to the Server
		server.addConnector(connector);
		
		logger.info("Init HTTP_SERVER HandlerFactory");
		HandlerFactory handlerFactory = new HandlerFactory();
		handlerFactory.setHandlers(server);
		try {
			server.start();
			logger.info("============================= SERVER START SUCCESSFULLY =============================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
