package bootstrap;

import core.PropertyUtils;
import dbFactory.DataAccess;
import httpAdapter.HttpServerFactory;

public class Bootstrap {
	public static void main(String[] args) {
		PropertyUtils.getInstance().init();
//		DataAccess db = new DataAccess();
//		db.init();
		HttpServerFactory server = new HttpServerFactory();
		server.init();
	}
}
