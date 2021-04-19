package core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtils {
	private static PropertyUtils instance = new PropertyUtils();
	private final Logger logger = LoggerFactory.getLogger(getClass());
//	private static Map<String, String> propertyMap = new HashMap<String, String>();
	
	private PropertyUtils() {}
	
	public static PropertyUtils getInstance() {
		return instance;
	}
	
	public void init() {
		logger.info("=============================  Core Property Setting ============================= ");
		File file = new File("Server.properties");
		try(FileReader reader = new FileReader(file)) {
			Properties p = new Properties();
			p.load(reader);
			System.setProperty("DBConnection", p.getProperty("DBConnection", "repository/DBConnection.properties"));
			System.setProperty("ResourcePath", p.getProperty("ResourcePath", "dist/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
