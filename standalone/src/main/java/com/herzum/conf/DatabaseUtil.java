package com.herzum.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DatabaseUtil {
	
	
	public static Properties getProperties() {
		
		Properties prop = new Properties();
		InputStream input = null;

		
		try {

			//input = new FileInputStream("./config.properties");
			input = new FileInputStream("/Users/ilakkmanoharan/eclipse-workspace/Project1Aug25/relativity-confluence-cleanup-basic/standalone/src/main/resources/config.properties");
			
			//input = new FileInputStream("/Home/config.properties");  //client env
			
			//input = new FileInputStream("/home/chinny/config.properties"); //ubuntu
			
			// load a properties file
			prop.load(input);
			

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return prop;
		
	}
	
	public static Connection getConnection() {
		
		String driver = getProperties().getProperty("jdbc.driver");
		if (driver != null) {
		    try {
				Class.forName(driver) ;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String url = getProperties().getProperty("jdbc.url");
		String username = getProperties().getProperty("jdbc.username");
		String password = getProperties().getProperty("jdbc.password");
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);
            
            
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return conn;
		
	}

}
