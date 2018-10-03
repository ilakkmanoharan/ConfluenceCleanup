package com.herzum.conf;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Arrays;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;
import org.apache.tika.Tika;


public class FilesCleanup {
	
	   String outputFile = null;
	
	   public FilesCleanup(String outputFile) {
		this.outputFile = outputFile;
	}
	   
    
	public void detectFileType(File f, String searchString) {
		
		try {
		
	Tika tika = new Tika();
	String filetype = tika.detect(f);
	
	
	if(!((filetype.startsWith("image")) || (filetype.startsWith("video")))) {
		
		doFileSearchByTika(f, searchString);
		
	}
	
	
		}catch(Exception ex){
	        System.out.println (ex.toString());
		}
		
		
	}
    	
	public void doFileSearchByTika(File f, String searchString) {
		
	try {
    
    FileSearchByTika tikaFileSearch = new FileSearchByTika();

    System.out.println("TIKA OUTPUT!!!!!!!!   " + f + " contains " + searchString + ": " 
            + tikaFileSearch.contains(f, searchString));
    
    
    //System.out.println(" ################Tika  filetype:" + filetype);
    
    if(tikaFileSearch.contains(f, searchString)) {
    	
    
    String outString = f + " contains " + searchString + ": " 
            + tikaFileSearch.contains(f, searchString);
    
    writeToFile(outString);
    
    String filePath = f.toString();
    
    System.out.println(" This is filepath: " + filePath);
    
    extractReqFields(filePath);
    
    
    }
    
		}catch(Exception ex){
	        System.out.println (ex.toString());
		}
		
	
    
	}
	
	

	public void walk(String path, String  searchString) {
		
		
		System.out.println(" We are in the walk method, going to walk the directory");

        File root = new File( path );
        File[] list = root.listFiles();
        
        
        if(list == null) {
        	System.out.println(" There are no files at the confluence attachments storage directory");
        	
        } else {

       

        for ( File f : list ) {
            if ( f.isDirectory() ) {
            	
            	   System.out.println("We are in the directory: " + f);
            	   
            	  
            	   
            	   
                walk( f.getAbsolutePath(),  searchString);
             }
            else {
            	
            	
          
            	
            	System.out.println(" calling detectFileType: ");
            	
            	detectFileType(f, searchString);
            	
            
                
            }
        }
        
        
        }
       }
	   
	   

	   public void writeErrorToFile() {

		   String outString = "writing error to file....Please check if the necessary properties in the configuration file have values";
		   writeToFile(outString);

	   }
	   
	   public void writeToFile(String outString) {
		   
		   try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true))) {
				
				bw.write(outString.trim());
				bw.newLine();
				
			} catch (IOException e) {

				e.printStackTrace();

			}   
		   
	   }
	   
	   
	   
        
        
    public void extractReqFields(String filePath) {
    	
    	
    	
    	filePath = filePath.replaceAll(".+(ver003.+)", "$1");
    	
    	String pattern = Pattern.quote(System.getProperty("file.separator"));
   // 	String[] splittedFileName = filePath.split("\\\\");
    	
    	String[] splittedFileName = filePath.split(pattern);
    	
    	System.out.println("File.separator = "+ File.separator);
    System.out.println("File.separatorChar = "+ File.separatorChar);
    	
    	System.out.println(" extractReqFields:  ");
    	
    	System.out.println(Arrays.toString(splittedFileName));
    	
    System.out.println(" splittedFileName[3]:  " + splittedFileName[3]);
    	
    //	int IdSpace = Integer.parseInt(splittedFileName[4]);
    	
    System.out.println(" splittedFileName[6]:  " + splittedFileName[6]);
    	
    //	int IdContent = Integer.parseInt(splittedFileName[7]);
    	
    	try {
    	
      	//showAttachmentsUrl(splittedFileName[9], splittedFileName[12]);
    		showAttachmentsUrl(splittedFileName[3], splittedFileName[6]);
    	
    	} catch (SQLException e) {
    		 
    		System.out.println(e.getMessage());
    		
    	}
    	
    }
        
    public void showAttachmentsUrl(String spaceId, String contentId) throws SQLException{
    	
        String baseUrl = ((baseUrl = DatabaseUtil.getProperties().getProperty("baseurl")) != null && !baseUrl.isEmpty()) ? baseUrl : null;
		
		System.out.println(" 1. baseUrl =  " + baseUrl);
		
		String confPageUrl = null;
		
		
		if(baseUrl != null && !baseUrl.isEmpty()){
		
		confPageUrl = baseUrl + "/pages/viewpageattachments.action?pageId=" ;
		
		}
    	
    	   
    	
        System.out.println("we are insde the showAttachmentsUrl method");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		/*
		
		String sql3 = "SELECT c.TITLE, c2.TITLE, s.SPACENAME, c.PAGEID, s.SPACEID," +
		               " CONCAT('https://einstein.kcura.com/pages/viewpageattachments.action?pageId=', c.PAGEID) AS LOCATION" +
				       " FROM CONTENT c JOIN CONTENT c2 ON c.PAGEID = c2.CONTENTID" +
		               " JOIN CONTENTPROPERTIES cp ON c.CONTENTID = cp.CONTENTID" +
				       " JOIN SPACES s ON c2.SPACEID = s.SPACEID WHERE c.CONTENTTYPE = 'ATTACHMENT' AND c2.SPACEID = ? AND c2.CONTENTID = ?";
		
		 */
		
		/*
		
		String sql3 = "SELECT c.TITLE, c2.TITLE, s.SPACENAME, c.PAGEID, s.SPACEID," +
	               " CONCAT(?, c.PAGEID) AS LOCATION" +
			       " FROM CONTENT c JOIN CONTENT c2 ON c.PAGEID = c2.CONTENTID" +
	               " JOIN CONTENTPROPERTIES cp ON c.CONTENTID = cp.CONTENTID" +
			       " JOIN SPACES s ON c2.SPACEID = s.SPACEID WHERE c.CONTENTTYPE = 'ATTACHMENT' AND c2.SPACEID = ? AND c2.         CONTENTID = ?";
			       
			       */
		
		
		String sql3 = "SELECT distinct" +
	               " CONCAT(?, c.PAGEID) AS LOCATION" +
			       " FROM CONTENT c JOIN CONTENT c2 ON c.PAGEID = c2.CONTENTID" +
	               " JOIN CONTENTPROPERTIES cp ON c.CONTENTID = cp.CONTENTID" +
			       " JOIN SPACES s ON c2.SPACEID = s.SPACEID WHERE c.CONTENTTYPE = 'ATTACHMENT' AND c2.SPACEID = ? AND c2.CONTENTID = ?";
		
		
		 
		 try {
		
		  conn = DatabaseUtil.getConnection(); 
		  
		  System.out.println("connection established...");
	    	
	    	  
	    	   	   
	    	  pstmt = conn.prepareStatement(sql3);
	    	   
	    	    pstmt.setNString(1, confPageUrl);
         	pstmt.setNString(2, spaceId);
	    	    	pstmt.setNString(3, contentId);
	    	    	
	    	    	ResultSet rs = pstmt.executeQuery();
	    	    	
	    	    	if(rs == null) {
	    	    		System.out.println(" The resultset is null");
	    	    	}
	    	    	
	    	    	int size= 0;
	    	    	if (rs != null)   
	    	    	{  
	    	    		System.out.println(" The resultset is NOT null");
	    	    	  //rs.beforeFirst();  
	    	    	  //rs.last();  
	    	    	  //size = rs.getRow(); 
	    	    	  //System.out.println(" size = " + size);
	    	    	}  

	    			while (rs.next()) {
	    				
	    				//String attachmentName = rs.getString("TITLE");
	    				String attachmentLink = rs.getString("LOCATION");
	    				

	    				//System.out.println("attachmentName : " + attachmentName);
	    				System.out.println("attachmentLink : " + attachmentLink);
	    				
	    				//writeToFile("attachmentName : " + attachmentName);
	    				writeToFile("attachmentLink : " + attachmentLink);
	    				
	    				
	    			
	    			}
	    	    
	      
	           
		  } catch (SQLException e) {

		      System.out.println(e.getMessage());
		     
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
