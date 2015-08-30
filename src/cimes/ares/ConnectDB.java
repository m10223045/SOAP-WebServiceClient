package cimes.ares;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectDB {
	private String DBDRIVER = "com.mysql.jdbc.Driver";
	private String DBURL = "DBURL";
	private String DBUSER = "root";
	private String DBPASS = "DBPASS";

	private Connection conn = null;
	
	public ConnectDB() throws ClassNotFoundException{
		try {
			conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS);		
		}catch(SQLException ex){
		}		
	}
	
    public Connection getConnection(){
    	return conn;
    }
}
