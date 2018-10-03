package com.herzum.conf;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {

	public static void main(String[] args) {
		
			
		String searchString = ((searchString = DatabaseUtil.getProperties().getProperty("searchString")) != null && !searchString.isEmpty()) ? searchString : null;
		
		System.out.println(" 1. searchString =  " + searchString);
		
		String replaceString = ((replaceString = DatabaseUtil.getProperties().getProperty("replaceString")) != null && !replaceString.isEmpty()) ? replaceString : null;
		
		System.out.println(" 2. replaceString = " + replaceString);
		
		String searchStrList = ((searchStrList = DatabaseUtil.getProperties().getProperty("searchStrList")) != null && !searchStrList.isEmpty()) ? searchStrList: null;
		
		System.out.println(" 3. searchStrList= " + searchStrList);
		
		String delimiter = ((delimiter = DatabaseUtil.getProperties().getProperty("delimiter")) != null && !delimiter.isEmpty()) ? delimiter : null;
		
		System.out.println(" 4. delimiter = " + delimiter);
		
		String directoryPath = ((directoryPath = DatabaseUtil.getProperties().getProperty("directory")) != null && !directoryPath.isEmpty()) ? directoryPath : null;
		
		System.out.println("directoryPath:  " + directoryPath);
		
		String outputFile = DatabaseUtil.getProperties().getProperty("outputFileLocation");
		
		
		File file = new File(outputFile); 

		 if (file.exists()) {
		     file.delete(); 
		     }
		 try {
			 
			file.createNewFile();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		FilesCleanup filescleanup = new FilesCleanup(outputFile);
		
		/*
		
		
		if(directoryPath != null && !directoryPath.isEmpty() && searchStrList != null && !searchStrList.isEmpty() && delimiter!= null && !delimiter.isEmpty() && replaceString != null && !replaceString.isEmpty()) {
		
			String[] parts = searchStrList.split(delimiter);
			
            ArrayList<String> stringlist = new ArrayList<String>();
			
			for(String s: parts) {
				stringlist.add(s.trim());
			}
			
			
			if(searchString != null && !searchString.isEmpty()) {
				
				stringlist.add(searchString);
				
			}
			
			for(String search: stringlist) {
					
					filescleanup.walk(directoryPath, search.trim());
			
			}
		
		}else if(directoryPath != null && !directoryPath.isEmpty() && searchString != null && !searchString.isEmpty() && replaceString != null && !replaceString.isEmpty()) {
        System.out.println(" Here is the searchString for files cleanup: " + searchString);
        
			filescleanup.walk(directoryPath, searchString);
		
		} 
		
		*/
		
		if(directoryPath != null && !directoryPath.isEmpty() && searchString != null && !searchString.isEmpty() && replaceString != null && !replaceString.isEmpty()) {
	        System.out.println(" Here is the searchString for files cleanup: " + searchString);
	        
				filescleanup.walk(directoryPath, searchString);
			
			} 
		
		
		
		PagesCleanup pagecleanup = new PagesCleanup();
		
		
		
		
		if(searchStrList != null && !searchStrList.isEmpty() && delimiter!= null && !delimiter.isEmpty() && replaceString != null && !replaceString.isEmpty()) {

			System.out.println("5. Inside if(searchStrList != null && delimiter!= null && replaceString != null)");
			
			String[] parts = searchStrList.split(delimiter);
			//List<String> stringlist = Arrays.asList(parts);
			
			//ArrayList<String> stringlist = new ArrayList<String>(Arrays.asList(parts));
			ArrayList<String> stringlist = new ArrayList<String>();
			
			for(String s: parts) {
				stringlist.add(s.trim());
			}
			
			
			if(searchString != null && !searchString.isEmpty()) {
				
				stringlist.add(searchString);
				
			}
			
			
			
			
			//Map<String, String> SearchReplaceMap = getSearchReplaceStringMap(stringlist, replaceString);
		
			
			
			
			
			
		
			try {
				
				//pagecleanup.cleanupForSearchReplaceMap(SearchReplaceMap);

				pagecleanup.cleanupForSearchStrList(stringlist, replaceString);
				
				//pagecleanup.cleanupForSearchString(search.trim(), replaceString);
				
				//pagecleanup.cleanupForSearchString(key.trim(), value.trim());
				

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			}
		  
		
			
			
		} else if(searchString != null && !searchString.isEmpty() && replaceString != null && !replaceString.isEmpty()) {
			
			//List<String> stringlist = new ArrayList<String>();
			//stringlist.add(searchString);
			//Map<String, String> SearchReplaceMap = getSearchReplaceStringMap(stringlist, replaceString);
			
			try {
				
				pagecleanup.cleanupForSearchString(searchString, replaceString);
				
				//pagecleanup.cleanupForSearchReplaceMap(SearchReplaceMap);
				
			} catch (SQLException e) {

				System.out.println(e.getMessage());

		    }
			
			
			
		}
		
		
		
	}
	
public static Map<String, String> getSearchReplaceStringMap(List<String> searchStringList, String replaceString) {
		
		
		Map<String, String> SearchReplaceStrMap1 = new HashMap<String, String>();
		
		for(String searchString2: searchStringList) {

			System.out.println(" 6. searchString2 = " + searchString2);
			
			SearchReplaceStrMap1.put(searchString2.trim(), replaceString);
			
			String searchStringWithBracs = "(" + searchString2.trim() + ")";
			
			String replaceWithBracs = "(" + replaceString + ")";
			
			SearchReplaceStrMap1.put(searchStringWithBracs, replaceWithBracs);
			
			String searchStringWithFullStop = searchString2.trim() + ".";
			
			String replaceWithFullStop = replaceString + ".";
			
			SearchReplaceStrMap1.put(searchStringWithFullStop, replaceWithFullStop);
			
			String searchStringWithComma = searchString2.trim() + ",";
			
			String replaceWithComma = replaceString + ",";
			
			SearchReplaceStrMap1.put(searchStringWithComma, replaceWithComma);
			
			String searchStringWithExcl = searchString2.trim() + "!";
			
			String replaceWithExcl = replaceString + "!";
			
			String replaceWithSemiCol = replaceString + ";";
			
			String replaceWithSingleQuo = "'" + replaceString + "'";
			
			String replaceWithDoubleQuo = "\"" + replaceString + "\"";
			
			SearchReplaceStrMap1.put(searchStringWithExcl, replaceWithExcl);
			
			String searchStringWithSemiCol = searchString2.trim() + ";";
			
			SearchReplaceStrMap1.put(searchStringWithSemiCol, replaceWithSemiCol);

			String searchStringWithSingleQuo = "'" + searchString2.trim() + "'";
			
			SearchReplaceStrMap1.put(searchStringWithSingleQuo, replaceWithSingleQuo);
			
			String searchStringWithDoubleQuo = "\"" + searchString2.trim() + "\"";
			
			SearchReplaceStrMap1.put(searchStringWithDoubleQuo, replaceWithDoubleQuo);
			
	}
		
		return SearchReplaceStrMap1;
		
	}	
		
	

}
