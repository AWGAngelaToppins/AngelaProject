package com.awginc.ulg;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportOutputs {

	public static void createOutputFileDiscontinueItems(List<String> list, String outgoingDirectory ) {
		createOutputFile(outgoingDirectory+UlgConstants.ulg3+".txt", list);
	}

	public static void createOutputFileLinkGroupBreak(List<String> list, String outgoingDirectory ) {
		createOutputFile(outgoingDirectory+UlgConstants.ulg4+".txt", list);
	}
	public static void createOutputFileAllItemDetails(List<String> list, String outgoingDirectory ) {
		createOutputFile(outgoingDirectory+UlgConstants.ulg5+".txt", list);
	}
	public static void createOutputULGUpdateReport(List<String> list, String outgoingDirectory ) {
		createOutputFile(outgoingDirectory+UlgConstants.ulg6+".txt", list);
	}
	public static void createOutputDeleteLinkGroups(List<String> list, String outgoingDirectory ) {
		createOutputFile(outgoingDirectory+UlgConstants.ulg7+".txt", list);//empty link groups deleted  
	}
	public static void createOutputDeleteItems(List<String> list, String outgoingDirectory ) {
		createOutputFile(outgoingDirectory+UlgConstants.ulg8+".txt", list);//empty link groups deleted  
	}
	public static void createOutputUserReport(List<String> list, String outgoingDirectory ) {
		createOutputFile(outgoingDirectory+UlgConstants.ulg9+".csv", list);//for user to view all link groups (manually pull off batapp server to give to users, temporary report 
	}
	public static void createOutputFileItemsWithMultiVendors(List<CategoryVendorObj> list, String outgoingDirectory) {
		//List of items that tie to multiple vendors 	
		List<String> debugReportList = new ArrayList<String>();

		for(CategoryVendorObj obj:list) {
			debugReportList.add(appendSpaces(obj.getItemCode(),10)+"|"+
		             appendSpaces(Integer.toString(obj.getCatNbr()),10)+"|"+
		             appendSpaces(obj.getCatDesc(),55)+"|"+
		             appendSpaces(obj.getPrivateLblCd(),10)+"|"+
		             appendSpaces(obj.getVendorCode(),12)+"|"+
		             appendSpaces(obj.getVendorDesc(),55)+"|"+
		             appendSpaces(obj.getSeasonCode(),12)+"|"+
		             appendSpaces(obj.getDataKey(),16)+"|"+
		             obj.getVendorCount());
		}
		createOutputFile(outgoingDirectory+UlgConstants.ulg1+".txt", debugReportList);
	}	

	public static void createOutputFileForAllVendors(List<CategoryVendorObj> list, String outgoingDirectory) {
		
		List<String> debugReportList = new ArrayList<String>();

		for(CategoryVendorObj obj:list) {
				debugReportList.add(appendSpaces(" ",10)+
			             appendSpaces(Integer.toString(obj.getCatNbr()),10)+"|"+
			             appendSpaces(obj.getCatDesc(),55)+"|"+
			             appendSpaces(obj.getPrivateLblCd(),10)+"|"+
			             appendSpaces(obj.getVendorCode(),12)+"|"+
			             appendSpaces(obj.getVendorDesc(),55)+"|"+
			             appendSpaces(obj.getSeasonCode(),12)+"|"+
			             appendSpaces(obj.getDataKey(),16));
				
		}
	    createOutputFile(outgoingDirectory+UlgConstants.ulg2+".txt", debugReportList);
	}
	public static String appendSpaces(String inputValue, int howMany) {
		
		int inputSize = inputValue.trim().length();
		int diff = howMany - inputSize;

		StringBuilder spaces = new StringBuilder();
		for(int i=0;i<diff;i++) {
			spaces.append(" ");
		}
		return inputValue.trim()+spaces.toString();
	}
//	public static String appendSpaces2(String inputValue, int howMany) {
//		
//		if(inputValue==null) {
//			inputValue = "NA";
//		}
//		int inputSize = inputValue.trim().length();
//		int diff = howMany - inputSize;
//
//		StringBuilder spaces = new StringBuilder();
//		for(int i=0;i<diff;i++) {
//			spaces.append(" ");
//		}
//		return inputValue.trim()+spaces.toString();
//	}	
	public static String createOutputFile(String outputFileName, List<String> list)
	{
		BufferedWriter bufferedWriter = null;

		try 
		{
			bufferedWriter = new BufferedWriter(new FileWriter(outputFileName));

			//writeout to text file
			for(int i=0;i<list.size();i++)
			{
				bufferedWriter.write(list.get(i));
				bufferedWriter.newLine();
			}
       } 
		catch (FileNotFoundException ex) 
       {
	            ex.printStackTrace();
       } catch (IOException ex) 
       {
	            ex.printStackTrace();
       } finally 
       {
	            //Close the BufferedWriter
            try {
	                if (bufferedWriter != null) 
	                {
	                    bufferedWriter.flush();
	                    bufferedWriter.close();
	                }
	            } catch (IOException ex) 
	            {
	                ex.printStackTrace();
	            }
	        }
		return "";
		
	}
}
