package dbFactory;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataAccess {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private HikariDataSource dataSource;
	
	public void init() {
		logger.info("============================= INITIALIZING DATA_ACCESS =============================");
		DataSourceManager manager = new DataSourceManager();
		manager.initializingDataSource();
		dataSource = manager.getDataSource();
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	private class DataSourceManager{
		private HikariConfig config;
		private HikariDataSource ds;
		
		public void initializingDataSource() {
			config = new HikariConfig(System.getProperty("DBConnection"));
			ds = new HikariDataSource(config);
		}
		
		public HikariDataSource getDataSource() {
			return ds;
		}
	}
	
	
}
