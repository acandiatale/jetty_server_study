package handlerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.EventListener;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.rewrite.handler.CompactPathRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewriteRegexRule;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import servletManager.TestServlet;

public class HandlerFactory {
	private File resourceFile;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public HandlerFactory() {
		this(System.getProperty("ResourcePath", "dist/"));
	}
	
	public HandlerFactory(String resourcePath) {
		resourceFile = new File("dist/");
	}
	
	public void setHandlers(Server server) {
		URI uri = URI.create(resourceFile.toURI().toASCIIString().replace("/index.html$", "/"));
		
		if(uri == null) {
			System.out.println("URIResource loading fail!! dist directory not founded");
			logger.info("URIResource loading fail!! dist directory not founded");
			System.exit(1);
		}
		
		logger.info("resource URI : " + uri.toString());
		System.out.println("resource URI : " + uri.toString());
		
		HandlerList handlerList = new HandlerList();
		
		RewriteHandler rewrite = new RewriteHandler();
		rewrite.setRewriteRequestURI(true);		
		rewrite.setRewritePathInfo(false);
		rewrite.setOriginalPathAttribute("requestedPath");
		
//		rewrite.addRule(new CompactPathRule());
		rewrite.addRule(new RewriteRegexRule("(\\/vlog)?(\\/introduce)?", "/index.html"));
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		ResourceHandler handler = new ResourceHandler();
		
		try {
			context.setContextPath("/");
			context.setWelcomeFiles(new String[] {"index.html"});
			context.setBaseResource(Resource.newResource(uri));
//			System.out.println(handler.getBaseResource().toString());
//			handler.setDirectoriesListed(false);
//			handler.setAcceptRanges(true);
			rewrite.setHandler(context);
//			handlerList.addHandler(rewrite);
			
			ServletHolder defHolder = new ServletHolder("default", DefaultServlet.class);
			defHolder.setInitParameter("dirAllowed", "false");
			context.addServlet(defHolder, "/");
			
			DefaultHandler def = new DefaultHandler();
//			handlerList.addHandler(def);
			handlerList.setHandlers(new Handler[] {rewrite, def});
//			contextCollection.addHandler(handlerList);
			server.setHandler(handlerList);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
}
