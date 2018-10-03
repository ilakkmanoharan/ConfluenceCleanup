package com.herzum.conf;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PagesCleanup {

	
public void cleanupForSearchString(String searchStr, String replaceString) throws SQLException{
		
		System.out.println("we are insde the cleanupForSearchString method");
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		 String sql3 = "UPDATE BODYCONTENT" +
	  	   	   " SET BODY = CAST(REPLACE(CAST(BODY AS NVARCHAR(MAX)), ? , ?) AS NTEXT)" +
			   " FROM dbo.BODYCONTENT" +
	  	   	   " WHERE BODY LIKE '%[^A-Za-z0-9]' + ? + '[^A-Za-z0-9]%'";
		 
		 try {
		
		  conn = DatabaseUtil.getConnection(); 
		  
		  System.out.println("connection established...");
	    	
	    	  conn.setAutoCommit(false);
	    	  
	    	  System.out.println(" This is searchStrbefore:" + searchStr);
	    	   	      
	    	   	      
	    	  System.out.println(" query: " + sql3 + "   searchstring: " + searchStr + "  replacestring: " + replaceString);
	    	   	   
	    	    		
	    	    	pstmt = conn.prepareStatement(sql3);
	    	    	
	    	    	String sstr = " " + searchStr.trim() + " ";
	    	    	
	    	    	
	    	    	System.out.println("LL#" + sstr + "@");
	    	    	
	    	    	System.out.println(" This is sstr:" + sstr);
	    	    	
	    	    	
	    	    	searchStr = padRight(searchStr, 1);
	    	    	
	    	    	searchStr = padLeft(searchStr, 1);
	    	    	
	    	    	System.out.println("#" + searchStr + "@");
	    	    	
            String searchStr1 = padRight(searchStr, 2);
	    	    	
	    	    	String searchStr2 = padLeft(searchStr1, 2);
	    	    	
	    	    	System.out.println("#" + searchStr2 + "@");
	    	    	
	    	    	System.out.println(" This is searchStr:" + searchStr2);
	    	    	
	    	    pstmt.setNString(1, sstr);
	    	    	pstmt.setNString(2, replaceString);
	    	    	pstmt.setNString(3, sstr);
	    	    		
	    	   
	    	    //pstmt.setNString(1, searchStr);
	    	    	//pstmt.setNString(2, replaceString);
	    	    	//pstmt.setNString(3, searchStr);
	    	    
	        pstmt.executeUpdate();
			
		   conn.commit();
	           
		  } catch (SQLException e) {

		      System.out.println(e.getMessage());
		      conn.rollback();

	      } finally {

		       if (pstmt != null) {
			       pstmt.close();
		        }

		       if (conn != null) {
			        conn.close();
		        }

	  }
	    	    
		
}

public String padRight(String s, int n) {
    return String.format("%1$-" + n + "s", s);  
}

public String padLeft(String s, int n) {
   return String.format("%1$" + n + "s", s);  
}


public void cleanupForSearchStrList(List<String> searchStrList, String replaceString) throws SQLException{
	
	System.out.println("we are insde the cleanupForSearchStrList method");
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	 String sql3 = "UPDATE BODYCONTENT" +
  	   	   " SET BODY = CAST(REPLACE(CAST(BODY AS NVARCHAR(MAX)), ? , ?) AS NTEXT)" +
  	   	   " FROM dbo.BODYCONTENT" +
  	   	   " WHERE BODY LIKE '%[^A-Za-z0-9]' + ? + '[^A-Za-z0-9]%'";
	
	 try {
	
	  conn = DatabaseUtil.getConnection(); 
	  
	  pstmt = conn.prepareStatement(sql3);
	  
	  System.out.println("connection established...");
      
	  conn.setAutoCommit(false);
	  
     
      for(String str: searchStrList) {
    	   	      
    	   	System.out.println(" query: " + sql3 + "   searchstring: " + str + "  replacestring: " + replaceString);
    	    		
    	    	//pstmt = conn.prepareStatement(sql3);
    	   	
    	   	String sstr = " " + str.trim() + " ";
    	   	
    	   	
    	   	pstmt.setNString(1, sstr);
	    	pstmt.setNString(2, replaceString);
	    	pstmt.setNString(3, sstr);
    	   	
    	   
    	    	//pstmt.setNString(1, str.trim());
    	    	//pstmt.setNString(2, replaceString);
    	    	//pstmt.setNString(3, str.trim());
    	    	
    	    	pstmt.addBatch();
    	    	
       }
    	    
        
		pstmt.executeBatch();
		
		
        conn.commit();
           
       
		
       } catch (SQLException e) {

	      System.out.println(e.getMessage());
	      conn.rollback();

      } finally {

	       if (pstmt != null) {
		       pstmt.close();
	        }

	       if (conn != null) {
		        conn.close();
	        }

  }
		
    	    
}
	
	

public void cleanupForSearchReplaceMap(Map<String, String> SearchReplaceMap) throws SQLException{
	
	System.out.println("we are insde the cleanupForSearchReplaceMap method");
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	
	
	 String sql3 = "UPDATE BODYCONTENT" +
  	   	   " SET BODY = CAST(REPLACE(CAST(BODY AS NVARCHAR(MAX)), ? , ?) AS NTEXT)" +
  	   	   " FROM dbo.BODYCONTENT" +
  	   	   " WHERE BODY LIKE '%[^A-Za-z0-9]' + ? + '[^A-Za-z0-9]%'";
	
	 try {
	
	  conn = DatabaseUtil.getConnection(); 
	  
	  pstmt = conn.prepareStatement(sql3);
	  
	  System.out.println("connection established...");
      
	  conn.setAutoCommit(false);
     
     // for(String str: searchStrList) {
	  for (Map.Entry<String, String> entry : SearchReplaceMap.entrySet()) {
	  String key = entry.getKey();
      String value = entry.getValue();

    	   	      
    	   	System.out.println(" query: " + sql3 + "   searchstring: " + key + "  replacestring: " + value);
    	  
    	    		
    	    //	pstmt = conn.prepareStatement(sql3);
    	    		
    	   
    	    	pstmt.setNString(1, key.trim());
    	    	
    	    	System.out.println(" 1. settingstring " + key);
    	    	
    	    	pstmt.setNString(2, value.trim());
    	    	
    	    	System.out.println(" 2. settingstring " + value);
    	    	pstmt.setNString(3, key.trim());
    	    	
    	    	System.out.println(" 2. settingstring " + key);
    	    	
    	    	
    	    	System.out.println(" here is the prepared statement:    " + pstmt);
    	    	
    	    	
    	    	pstmt.addBatch();
    	    	
       }
    	    
        
		pstmt.executeBatch();
		
		System.out.println(" length of preparedstatement.executeBatch():  " + pstmt.executeBatch().length);
		
        conn.commit();
        
		
       } catch (SQLException e) {

	      System.out.println(e.getMessage());
	      //conn.rollback();

      } finally {

	       if (pstmt != null) {
		       pstmt.close();
	        }

	       if (conn != null) {
		        conn.close();
	        }

  }
		
    	    
}
    	    	

	
}

