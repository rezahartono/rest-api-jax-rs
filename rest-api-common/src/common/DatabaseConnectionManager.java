package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
	private static DatabaseConnectionManager dbm;
	
	public static DatabaseConnectionManager getInstance() {
		if(dbm == null) {
			dbm = new DatabaseConnectionManager();
		}
		
		return dbm;
	}

	public Connection db = getConnection();
	public String schemaName = "restapi_v1";
	private final String url = "jdbc:postgresql://localhost:5432/restapi";

	public Connection getConnection() {
		try {
			Properties props = new Properties();
			props.setProperty("user", "postgres");
			props.setProperty("password", "root");
//			props.setProperty("ssl", "true");
			db = DriverManager.getConnection(url, props);
			
			if (db != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

		return db;
	}
}
