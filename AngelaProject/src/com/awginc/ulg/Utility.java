package com.awginc.ulg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Utility {
	
	public static File[] getFiles(String outgoingDirectory) {
		UlgInputFilter vendorFilter = new UlgInputFilter();
		File incomingFolder = new File(outgoingDirectory);
		File[] incomingFiles = incomingFolder.listFiles(vendorFilter);
		return incomingFiles;
	}
	public static ArrayList<String> split(String sep, String original) throws Exception
	{
	   if (sep == null || sep.equals("") || original == null)
	      throw new IllegalArgumentException("null or empty String");
	   ArrayList<String>  result = new ArrayList<String> ();
	   int oldpos = 0;
	   int pos;
	   int sepLength = sep.length();
	   String substr="";
	   
	   try{
		   while ((pos = original.toUpperCase().indexOf(sep, oldpos)) >= 0)
		   {
		   	  substr = original.substring(oldpos, pos);
		      if (substr.startsWith("\n"))
		         result.add(original.substring(oldpos + 1, pos));
		      else
		         result.add(substr);
		      oldpos = pos + sepLength;
		   }
	
		   if (original.substring(oldpos).toUpperCase().startsWith("\n"))
		      result.add(original.substring(oldpos + 1));
		   else
		      result.add(original.substring(oldpos));
	   }catch(Exception e){
		   System.out.println("TESTING ERROR "+original+"  "+substr);
	   }
	   return result;
	}
	public static String newValue(String oldValue, int padSize) {
		StringBuilder newVal = new StringBuilder();

		if(oldValue == null) {
			return " ";
		}else {
			int diff = padSize - oldValue.trim().length();
			
			for(int i=0;i<diff;i++) {
				newVal.append(" ");
			}
			return oldValue.trim()+" "+newVal.toString();
		}
	}
	public static String newValue2(String oldValue, int padSize) {
		StringBuilder newVal = new StringBuilder();
		
		if(oldValue == null) {
			return " ";
		}else {
	
			int diff = padSize - oldValue.length();
			//diff = diff-1;
			
			for(int i=0;i<diff;i++) {
				newVal.append(" ");
			}
			return oldValue+" "+newVal.toString(); //don't trim old value
		}
	}
	
	public static String newValue(String oldValue, int padSize, String addValue) {
		StringBuilder newVal = new StringBuilder();
		
		int diff = padSize - oldValue.trim().length();
		for(int i=0;i<diff;i++) {
			newVal.append(addValue);
		}
		return newVal.toString()+oldValue.trim();
	}
	public static String newSeasonValue(String oldValue, int padSize) {
		StringBuilder newVal = new StringBuilder();
		
		int diff = padSize - oldValue.length();
		
		for(int i=0;i<diff;i++) {
			newVal.append(" ");
		}
		return oldValue+" "+newVal.toString();
	}
	public static List<String> retrieveTextFile(File textFile){
		
		List<String> list                = new ArrayList<String>();
		String line     = null; 
		try 
		{
			BufferedReader input = new BufferedReader(new FileReader(textFile));
			while ((line = input.readLine()) != null)
			{
				if("".equals(line)){
				}else
				{
					list.add(line);
				}
			}
			input.close();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}finally{
		}
		
		return list;
	}	
}
