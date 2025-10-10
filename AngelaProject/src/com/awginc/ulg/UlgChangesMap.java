package com.awginc.ulg;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UlgChangesMap {
	
	public static HashMap<String,String> currULG5AddtlMap(List<ReportObj> current_ULG5_Addtl) {
		HashMap<String,String> map = new HashMap<String,String>();
		for(ReportObj curr:current_ULG5_Addtl) { 
			map.put(curr.getReportLinkGroupNbr(), curr.getReportLinkGroupNbr());
		}
		return map;
	}
	public static HashMap<String,String> previousItemLinkGrpNbMap(List<ReportObj> current_ULG5_Addtl) {
		HashMap<String,String> map = new HashMap<String,String>();
		for(ReportObj curr:current_ULG5_Addtl) { 
			map.put(curr.getReportItemCode(), curr.getReportLinkGroupNbr());
		}
		return map;
	}
	public static HashMap<String,String> newLinkGrpMap(HashMap<String,String> current_ULG5_Addtl_Map,List<ReportObj> current_ULG6_Updates) {
		HashMap<String,String> map = new HashMap<String,String>();
		for(ReportObj curr:current_ULG6_Updates) {
			if(!current_ULG5_Addtl_Map.containsKey(curr.getReportLinkGroupNbr())) {   //any new link groups after automation updates?
				map.put(curr.getReportLinkGroupNbr(), curr.getReportLinkGroupNbr());
			}
		}
		System.out.println("newLinkGroupTotals size = "+map.size());
		return map;
	}
	public static HashMap<String,String> newItemsMap(List<ReportObj> current_ULG5_Addtl) {
		HashMap<String,String> map = new HashMap<String,String>();
		for(ReportObj curr:current_ULG5_Addtl) {
			if("0".equals(curr.getReportLinkGroupNbr())) //means new items
				map.put(curr.getReportItemCode(), curr.getReportItemCode());
		}
		System.out.println("newItems           size = "+map.size());
		return map;
	}
	public static HashMap<String,String> discontinuedMap(String outgoingDirectory, String inputFileName) throws Exception {
		HashMap<String,String> map = new HashMap<String,String>();
   		File[] incomingFiles = Utility.getFiles(outgoingDirectory);
   		int nbrOfFiles       = incomingFiles.length;
   		
   		List<String> discontItems = new ArrayList<String>();
   		
   		File inputFile;
   		for (int i = 0; i < nbrOfFiles; i++) {
   			inputFile = new File(outgoingDirectory+incomingFiles[i].getName());
   			
   			if(incomingFiles[i].getName().contains(inputFileName)) {
   				List<String> textList = Utility.retrieveTextFile(inputFile);	
   				for(String text:textList) {
   					discontItems = Utility.split("|", text);
   					map.put(discontItems.get(0).trim(), discontItems.get(0).trim());
   				}
   			}
   		}
   		return map;
	}	
	public static HashMap<String,String> itemsMovedMap(List<ReportObj> current_ULG5_Addtl, List<ReportObj> current_ULG6_Updates) {
		HashMap<String,String> map = new HashMap<String,String>();

		ReportObj addtl   = new ReportObj();
		ReportObj updates = new ReportObj();

		for(int i=0;i<current_ULG6_Updates.size();i++){
			addtl   = (ReportObj) current_ULG5_Addtl.get(i);
			updates = (ReportObj) current_ULG6_Updates.get(i);
			
			//any items moved to another link group?
			if(addtl.getReportItemCode().equals(updates.getReportItemCode())) {
				if(!"0".equals(addtl.getReportLinkGroupNbr())) { //exclude new items
					if(!addtl.getReportLinkGroupNbr().equals(updates.getReportLinkGroupNbr())) {
						map.put(addtl.getReportItemCode(), "Moved item from "+addtl.getReportLinkGroupNbr()+" to "+updates.getReportLinkGroupNbr());
					}
				}
			}
		}
		return map;
	}
	public static HashMap<String,String> linkGroupsDeletedMap(String outgoingDirectory, String inputFileName) throws Exception {
		HashMap<String,String> map = new HashMap<String,String>();

   		File[] incomingFiles = Utility.getFiles(outgoingDirectory);
   		int nbrOfFiles = incomingFiles.length;
 
   		List<String> discontItems = new ArrayList<String>();
   		
   		File inputFile;
   		for (int i = 0; i < nbrOfFiles; i++) {
   			inputFile = new File(outgoingDirectory+incomingFiles[i].getName());
   			
   			if(incomingFiles[i].getName().contains(inputFileName)) {
   				List<String> textList = Utility.retrieveTextFile(inputFile);	
   				for(String text:textList) {
   					discontItems = Utility.split("|", text);
   					map.put(discontItems.get(0).trim(), discontItems.get(0).trim());
   				}
   			}
   		}
   		return map;
	}	
	public static HashMap<String,String> itemsInfoChangesMap(List<ReportObj> current_ULG5_Addtl, List<ReportObj> current_ULG6_Updates) throws Exception{
		
		HashMap<String,String> map = new HashMap<String,String>();
		ReportObj addtl   = new ReportObj();
		ReportObj updates = new ReportObj();
		ReportObj prevDay = new ReportObj();
		List<String> temp = new ArrayList<String>();
		String anyChanges;
		
		for(int i=0;i<current_ULG6_Updates.size();i++){
			addtl   = (ReportObj) current_ULG5_Addtl.get(i);
			updates = (ReportObj) current_ULG6_Updates.get(i);
			prevDay = new ReportObj();
			
			//check for item info updates
			if(!"0".equals(addtl.getReportLinkGroupNbr())) { //exclude new items
				if("yes".equals(addtl.getItemInfoUpdateflag())) {
					temp = Utility.split(":", addtl.getFaciliyToCorporateLevelKeyUlg());
					prevDay.setReportItemDesc(temp.get(0));
					prevDay.setReportPackRange(temp.get(1));
					prevDay.setReportSizeRange(temp.get(2));
					prevDay.setReportBspRange(temp.get(3));
					prevDay.setReportUnitRange(temp.get(4));
					prevDay.setReportCitySrpRange(temp.get(5));
					prevDay.setReportUpc(temp.get(6));
					prevDay.setReportObiRange(UserReportFormat.removeCommas(temp.get(7))); 
					prevDay.setReportCmdty(temp.get(8));
					prevDay.setReportSubCmdty(temp.get(9));
					anyChanges = itemInfoChanges(updates, prevDay);
					map.put(addtl.getReportItemCode(), anyChanges.toString());
				}
		    }
		}
		return map;
	}
	private static String itemInfoChanges(ReportObj currentDay, ReportObj prevDay) throws Exception {
		
		Boolean anyChanges = false;
		
		StringBuilder comments = new StringBuilder();
		
		if(!currentDay.getReportItemDesc().equals(prevDay.getReportItemDesc())) {
			comments.append("PrevItemDesc  "+prevDay.getReportItemDesc()+"| ");
			anyChanges = true;
		}
		if(!currentDay.getReportPackRange().equals(prevDay.getReportPackRange())) {
			comments.append("prevPack  "+prevDay.getReportPackRange()+"| ");
			anyChanges = true;
		}
	    if(!currentDay.getReportSizeRange().equals(prevDay.getReportSizeRange())) {
			comments.append("prevSize  "+prevDay.getReportSizeRange()+"| ");
			anyChanges = true;
		}
		if(!currentDay.getReportBspRange().equals(prevDay.getReportBspRange())) {
			comments.append("prevBSPRange  "+prevDay.getReportBspRange()+"| ");
			anyChanges = true;
		}
		if(!currentDay.getReportUnitRange().equals(prevDay.getReportUnitRange())) {
			comments.append("prevUnit  "+prevDay.getReportUnitRange()+"| ");
			anyChanges = true;
		}
		if(!currentDay.getReportCitySrpRange().equals(prevDay.getReportCitySrpRange())) {
			comments.append("prevCitySRP  "+prevDay.getReportCitySrpRange()+"| ");
			anyChanges = true;
		}
		if(!currentDay.getReportUpc().equals(prevDay.getReportUpc())) {
			comments.append("prevUpc  "+prevDay.getReportUpc()+"| ");
			anyChanges = true;
		}
		if(!currentDay.getReportObiRange().equals(prevDay.getReportObiRange())) {
			comments.append("prevObx  "+ removeCommas(prevDay.getReportObiRange()+"| "));
			anyChanges = true;
		}
		if(!currentDay.getReportCmdty().equals(prevDay.getReportCmdty())) {
			comments.append("prevCmdty  "+prevDay.getReportCmdty()+"| ");
			anyChanges = true;
		}
		if(!currentDay.getReportSubCmdty().equals(prevDay.getReportSubCmdty())) {
			comments.append("prevSubcmdty  "+prevDay.getReportSubCmdty()+"| ");
			anyChanges = true;
		}

		if(anyChanges==false)
			return "none";
		else
			return "Item "+currentDay.getReportItemCode()+"| "+comments.toString();
	}	
	private static String removeCommas(String inputValue) throws Exception {

		String newValue = inputValue.replaceAll(",", " ");
		return newValue;
	}	
}
