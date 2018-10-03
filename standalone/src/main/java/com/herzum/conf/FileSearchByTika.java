package com.herzum.conf;

import org.apache.tika.Tika;

import java.net.MalformedURLException;
import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;


import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;

import org.xml.sax.SAXException;
import org.apache.tika.mime.MimeTypeException;
import org.xml.sax.ContentHandler;
import org.apache.tika.mime.MimeTypes;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.mime.MediaType;
import org.apache.tika.language.LanguageProfile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;


public class FileSearchByTika {
	
	
	static boolean debugging = true;

    public static boolean contains(File file, String s) throws MalformedURLException, IOException, MimeTypeException, SAXException, TikaException{

      ContentHandler handler = new BodyContentHandler();

          MimeTypes mimeRegistry = TikaConfig.getDefaultConfig()
                  .getMimeRepository();

          if(debugging) System.out.println("Examining: [" + file + "]");

          if(debugging) System.out.println("The MIME type (based on filename) is: ["
                  + mimeRegistry.getMimeType(file.toString()) + "]");

          if(debugging) System.out.println("The MIME type (based on MAGIC) is: ["
                  + mimeRegistry.getMimeType(file + "]"));

          Detector mimeDetector = (Detector) mimeRegistry;
          if(debugging) System.out
                  .println("The MIME type (based on the Detector interface) is: ["
                          + mimeDetector.detect(file.toURI().toURL()
                                  .openStream(), new Metadata()) + "]");

          LanguageIdentifier lang = new LanguageIdentifier(new LanguageProfile(
                  FileUtils.readFileToString(file)));

          if(debugging) System.out.println("The language of this content is: ["
                  + lang.getLanguage() + "]");
          
          
          
          
          Parser parser = TikaConfig.getDefaultConfig().getParser(
                  MediaType.parse(mimeRegistry.getMimeType(file).getName()));
          
          Metadata parsedMet = new Metadata();
          
          /*
          
          AutoDetectParser parser1 = new AutoDetectParser();
          
          Detector detector = parser1.getDetector();
          
          Metadata parsedMet = new Metadata();
          
          java.awt.PageAttributes.MediaType mediaType = detector.detect(file.toURI().toURL().openStream(), parsedMet);
          
          System.out.println("  This is the mediaType:   " + mediaType.toString() +  "  &&& " + mediaType.getType());
          
          if(!(mediaType.getType().equals("image")) || (mediaType.getType().equals("video"))){
        	  
        	  //Parser parser = TikaConfig.getDefaultConfig().getParser(
                      //MediaType.parse(mimeRegistry.getMimeType(file).getName()));

          //Metadata parsedMet = new Metadata();
              parser.parse(file.toURI().toURL().openStream(), handler,
                      parsedMet, new ParseContext());
        	   
           	} else {
           		
           		System.out.println(" ------This file is not scanned because the filetype is: " + mimeRegistry.getMimeType(file + "]"));
           	}

         */
          
          
          parser.parse(file.toURI().toURL().openStream(), handler,
                  parsedMet, new ParseContext());

          
      return handler.toString().toLowerCase().contains(s.toLowerCase());
    }
    
    /*

    public static void main(String[] args) throws Exception 
    {
      File file = new File(filename);

      System.out.println(file + " contains " + searchString + ": " 
              + contains(file, searchString));
      }

      static String searchString = "void";
      static String filename = "copy.TXT";
  }
  
  */

}
