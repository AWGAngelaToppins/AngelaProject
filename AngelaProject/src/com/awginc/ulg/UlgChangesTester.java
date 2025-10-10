package com.awginc.ulg;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class UlgChangesTester  {

	private static String outgoingDirectory="C:\\Users\\atoppins\\Downloads\\ULGtesting\\";
	
	static List<ReportObj> previous_ULG6_Updates = new ArrayList<ReportObj>();
	static List<ReportObj> current_ULG5_Addtl    = new ArrayList<ReportObj>();
	static List<ReportObj> current_ULG6_Updates  = new ArrayList<ReportObj>();

	static HashMap<String,String> current_ULG5_Addtl_Map     = new HashMap<String,String>();  //itemCode, ReportObj 
	static HashMap<String,String> previousItemLinkGrpNbMap    = new HashMap<String,String>();  //itemCode, linkGrpNb 

	static HashMap<String,String> itemsMovedMap       = new HashMap<String,String>();
	static HashMap<String,String> itemsInfoChangesMap = new HashMap<String,String>();
	static HashMap<String,String> newLinkGroupMap=new HashMap<String,String>();
	static HashMap<String,String> newItems=new HashMap<String,String>();
	
	static HashMap<String,String> prevLinkGroups     = new HashMap<String,String>();     //linkGrpNb, linkGrpNb
	
	static HashMap<String,String> linkGroupsDeletedMap = new HashMap<String,String>();     //linkGrpNb, linkGrpNb
	static HashMap<String,String> discontinuedMap    = new HashMap<String,String>();     //itemCode, itemCode
	

//	static int changeSequenceNbr=0;
	
	static PreparedStatement preparedStatement;
	

	public static String process() throws Exception {
		
		mainProcess();
		
		return "";

	}

	private static void mainProcess() throws Exception {

		int changeSequenceNbr = getCurrentDayMaxSeqNbr(getCurrDate());

		getInputFiles();
		
		List<ReportObj> automationChangeList  = new ArrayList<ReportObj>();
		
		//--------------------------------------------------------------------------------------------------------------
		//various maps for determining what changes were made 
		//--------------------------------------------------------------------------------------------------------------
		previousItemLinkGrpNbMap = UlgChangesMap.previousItemLinkGrpNbMap(current_ULG5_Addtl);
		current_ULG5_Addtl_Map   = UlgChangesMap.currULG5AddtlMap(current_ULG5_Addtl);
		newLinkGroupMap          = UlgChangesMap.newLinkGrpMap(current_ULG5_Addtl_Map, current_ULG6_Updates);
		newItems                 = UlgChangesMap.newItemsMap(current_ULG5_Addtl);
		itemsMovedMap            = UlgChangesMap.itemsMovedMap(current_ULG5_Addtl, current_ULG6_Updates);
		itemsInfoChangesMap      = UlgChangesMap.itemsInfoChangesMap(current_ULG5_Addtl, current_ULG6_Updates);
		discontinuedMap          = UlgChangesMap.discontinuedMap(outgoingDirectory, UlgConstants.ulg8);
		linkGroupsDeletedMap     = UlgChangesMap.linkGroupsDeletedMap(outgoingDirectory, UlgConstants.ulg7);
		
		//--------------------------------------------------------------------------------------------------------------
		//build the auto change list 
		//--------------------------------------------------------------------------------------------------------------
		//any items moved to another link group?
		HashMap<String,String> actualUpdateLinkGrpDesc = new HashMap<String,String>();
		int itemsMovedTotal=0;
		HashMap<String,String> linkGroupDeleted= new HashMap<String,String>();
		int itemInfoChanged=0;
		int NewItems=0;
		
		HashMap<String,String> newLinkGrp=new HashMap<String,String>();
		int testDiscontinuedItems=0;

		List<String> temp = new ArrayList<String>();
		
		ReportObj curr1;
		for(ReportObj curr:current_ULG6_Updates){
			
			//any items moved? 
			if(itemsMovedMap.containsKey(curr.getReportItemCode())){
				itemsMovedTotal++;
				curr.setAutoChangeType("Item Moved");
				curr.setAutoChanges(itemsMovedMap.get(curr.getReportItemCode()));
				automationChangeList.add(curr);
			}
			//any item info updated?
			if(itemsInfoChangesMap.containsKey(curr.getReportItemCode())){
				temp = Utility.split("|", itemsInfoChangesMap.get(curr.getReportItemCode()));
				itemInfoChanged++;
				for(int i=0;i<temp.size();i++) {
					if(i==0) {
						//skip
					}else
					if("".equals(temp.get(i).trim())){
						//skip
					}else
					{
						curr1 = UlgChangeFormat.setNewReportBeanObj(curr);
						curr1.setAutoChangeType("Item Info Updated");
						curr1.setAutoChanges(temp.get(i));
						automationChangeList.add(curr1);
					}
				}
			}
			
			//any new link groups?
			ReportObj curr2;
			if(newLinkGroupMap.containsKey(curr.getReportLinkGroupNbr())) {
				curr2 = UlgChangeFormat.setNewReportBeanObj(curr);
				newLinkGrp.put(curr.getReportLinkGroupNbr(), curr.getReportLinkGroupNbr()); //to get unique number of new linkGrp created
				curr2.setAutoChanges("New Link Group "+curr.getReportLinkGroupNbr());
				curr2.setAutoChangeType("New Link Group");
				automationChangeList.add(curr2);
			}
			
			//any link group desc updated?
			ReportObj curr3;
			//any new items?  
			if(newItems.containsKey(curr.getReportItemCode())) {
				curr3 = UlgChangeFormat.setNewReportBeanObj(curr);
				curr3.setAutoChangeType("Newly Added Item");
				curr3.setAutoChanges("New Item "+curr.getReportItemCode());
				automationChangeList.add(curr3);
				NewItems++;
			}
		}
		
		//-----------------------------------------------------
		//any discontinued and deleted link groups?
		//-----------------------------------------------------
		ReportObj prev1;
		for(ReportObj prev:previous_ULG6_Updates) {
			if(prev.getReportItemCode().contains("018834"))
				System.out.println("stop here");
			if(discontinuedMap.containsKey(prev.getReportItemCode())) {
				testDiscontinuedItems++;
				prev.setAutoChangeType("Delete Discontinued Item");
				prev.setAutoChanges(prev.getReportItemCode());
				prev.setReportTempLinkGroupNbr(" ");
				automationChangeList.add(prev);
//				System.out.println("delete discontinued item");
			}
			//any link group deleted?
			if(linkGroupsDeletedMap.containsKey(prev.getReportLinkGroupNbr())) {
				prev1 = UlgChangeFormat.setNewReportBeanObj(prev);
				linkGroupDeleted.put(prev1.getReportLinkGroupNbr(), prev1.getReportLinkGroupNbr());
				prev1.setAutoChangeType("Delete Empty Link Group");
				prev1.setAutoChanges(prev1.getReportLinkGroupNbr());
				prev1.setReportTempLinkGroupNbr(" ");
				automationChangeList.add(prev1);
//				System.out.println("delete empty link group");
			}
		}

		System.out.println(" ");
		System.out.println("New Link Grp Nbr          - newLinkGrp              = "+newLinkGrp.size());
		System.out.println("New Items                 - NewItems                = "+NewItems);
		System.out.println("Items Moved               - testItemsMovedTotal     = "+itemsMovedTotal);
		System.out.println("Item Info Updated         - itemInfoChanged         = "+itemInfoChanged); 
		System.out.println("Delete Link Grp           - linkGroupDeleted        = "+linkGroupDeleted.size());
		System.out.println("Update Link Grp Desc TODO - actualUpdateLinkGrpDesc = "+actualUpdateLinkGrpDesc.size());
		System.out.println("Delete Discontinued Items - testDiscontinuedItems   = "+testDiscontinuedItems);

		System.out.println(" ");
//		System.out.println("Delete Discontinued Items and delete link grp + testDiscontinuedItemsAndDeleteLinkGrp = "+testDiscontinuedItemsAndDeleteLinkGrp);
		
		List<String> reportList = new ArrayList<String>();
		System.out.println("automationChangeList size = "+automationChangeList.size());
		if(automationChangeList.size()>0) {
   			reportList = UlgReportFormat.buildReportDetails(automationChangeList); 
   			ReportOutputs.createOutputFile(outgoingDirectory+UlgConstants.ulg11+".txt", reportList);
   		}
		
		//Log ULG Automation changes
		boolean skipHeading=false;
		System.out.println("reportList size ="+reportList.size());
		for(String text:reportList) {
			if(skipHeading==true) { 
			   changeSequenceNbr++;
			   insertUlgChanges(text, changeSequenceNbr);
			}
			else
				skipHeading=true;
		}
		preparedStatement = testPreparedStatement("commit");
		preparedStatement.executeUpdate();
	}


	private static void getInputFiles() throws Exception {
   		File[] incomingFiles = Utility.getFiles(outgoingDirectory);
   		int nbrOfFiles = incomingFiles.length;
 
   		File inputFile;
   		for (int i = 0; i < nbrOfFiles; i++) {
   			inputFile = new File(outgoingDirectory+incomingFiles[i].getName());
   			System.out.println("input file "+incomingFiles[i].getName());
	   		if(incomingFiles[i].getName().contains("ULG5_AddtlDetails.txt")) {
   	   			List<String> textList = Utility.retrieveTextFile(inputFile);	
   	   			current_ULG5_Addtl = getInputLoop(textList,"ULG5");
   			}else
	   		if(incomingFiles[i].getName().contains("ULG6_PrevDayUpdates.txt")) {
   	   			List<String> textList = Utility.retrieveTextFile(inputFile);	
   	   			previous_ULG6_Updates = getInputLoop(textList,"ULG6");
   			}else
	   		if(incomingFiles[i].getName().contains("ULG6_Updates.txt")) {
   	   			List<String> textList = Utility.retrieveTextFile(inputFile);	
   	   			current_ULG6_Updates = getInputLoop(textList,"ULG6");
   			}
   		}
		System.out.println("get input file ULG5_AddtlDetails.txt   size = "+current_ULG5_Addtl.size());
		System.out.println("get input file ULG6_PrevDayUpdates.txt size = "+previous_ULG6_Updates.size());
		System.out.println("get input file ULG6_Updates.txt        size = "+current_ULG6_Updates.size());

	}
	private static List<ReportObj> getInputLoop(List<String> textList, String whichFile) throws Exception {
   		List<String> columns = new ArrayList<String>();
   		List<ReportObj> inputList = new ArrayList<ReportObj>();
			for(String text:textList) {
				if("ULG5".equals(whichFile)) {
					if(!text.contains("--NextLinkGroup")) { //exclude headings
						columns = Utility.split("|", text);
						inputList.add(UlgChangeFormat.setAddtlReportBeanObj(columns));
					}
				}else
				if("ULG6".equals(whichFile)) {
					if(!text.contains("Link_Grp_Nbr")) { //exclude headings
						columns = Utility.split("|", text);
						inputList.add(UserReportFormat.setReportBeanObj(columns));
					}
				}
			}
   		return inputList;
		
	}

	private static void insertUlgChanges(String recs, int changeSequenceNbr ) throws Exception {

//		PreparedStatement preparedStatement=null;
		
//		changeSequenceNbr++;
		
		List<String> temp = new ArrayList<String>();
		temp = Utility.split("|", recs);
		
		String query = 
				"insert into DB2PDBA.ULG_CHG " + 
		        "      ( CHG_SEQ_NB "+
				"       ,CHG_TYP   "+
				"		,CHG_DESC  "+      
				"		,ITEM_CD   "+     
				"		,ULG_GRP_NB  "+   
				"		,ITEM_DESC   "+  
				"		,UNIT_COST_DESC  "+
				"		,SIZE_DESC   "+
				"		,PACK_DESC   "+ 
				"		,UNIT_DESC    "   +
				"		,CITY_SRP_DESC "  +
				"		,UPC_DESC "       +
				"		,CMDTY_DESC "     +
				"		,SUB_CMDTY_DESC   "+
				"		,OBX_DESC  "      +
				"		,ULG_DESC)    "+   
				"		VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "; 
		try {
			preparedStatement = testPreparedStatement(query);
			
			preparedStatement.setInt(1,changeSequenceNbr);//sequence nbr
			preparedStatement.setString(2,temp.get(0).trim()); //changeType
			preparedStatement.setString(3,temp.get(1).trim()); //changeDesc
			preparedStatement.setString(4,removeSingeQuote(temp.get(2).trim()));//itemCode
			preparedStatement.setInt(5,Integer.parseInt(temp.get(3).trim()));//linkGrpNbr
			preparedStatement.setString(6,removeSingeQuote(temp.get(4).trim()));//itemDesc
			preparedStatement.setString(7,removeSingeQuote(temp.get(5).trim()));//bspRange
			preparedStatement.setString(8,removeSingeQuote(temp.get(6).trim()));//sizeRange
			preparedStatement.setString(9,removeSingeQuote(temp.get(7).trim()));//pack
			preparedStatement.setString(10,removeSingeQuote(temp.get(8).trim()));//unit
			preparedStatement.setString(11,removeSingeQuote(temp.get(9).trim()));//citySRP
			preparedStatement.setString(12,removeSingeQuote(temp.get(10).trim()));//upc
			preparedStatement.setString(13,removeSingeQuote(temp.get(11).trim()));//cmdty
			preparedStatement.setString(14,removeSingeQuote(temp.get(12).trim()));//subCmdty
			preparedStatement.setString(15,removeSingeQuote(temp.get(13).trim()));//obi
			preparedStatement.setString(16,temp.get(15).trim());//link group desc 
			
			preparedStatement.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("insertUlgChanges()");
			e.printStackTrace();
		}
	}
	private static String removeSingeQuote(String value) {
		return value.trim().replaceAll("'", "");
	}
	private static String getCurrDate() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date currDate = new Date();
		String currDtFmt = dateFormat.format(currDate);

		return currDtFmt;
	}
	private static int getCurrentDayMaxSeqNbr(String currDate) throws SQLException {

		ResultSet resultSet = null;
		int returnValue = 0;

		PreparedStatement preparedStatement=null;
		
		String query = 
				  "select (case when seqnbr is null then 0 else seqnbr end) "
				+ "from ("
				+ "       select max(chg_seq_nb) seqnbr from DB2PDBA.ULG_CHG where chg_dt = ? "
				+ "     ) ";
		
		try {
			preparedStatement = testPreparedStatement(query);
			preparedStatement.setString(1, currDate);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())	
				returnValue = resultSet.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
 				resultSet.close();
 			}
		}
		return returnValue;		
	}
	public static PreparedStatement testPreparedStatement(String query) throws SQLException {
		//FOR TESTING ONLY
		Connection conn = getConn();
		PreparedStatement test = conn.prepareStatement(query);
		
		return test;
		
	}
	public static Connection getConn() throws SQLException {
		String url ="jdbc:db2://TRHDB2:60000/AWGT1";
		String id  = "dvantage";
		String psw = "dvantage";
		
	      Connection connection;
			try
			{
				Class.forName("com.ibm.db2.jcc.DB2Driver");  
				DriverManager.setLogWriter(null);
				connection = DriverManager.getConnection(url,id,psw);
			}
			catch (ClassNotFoundException e)
			{
				throw new SQLException("Unable to load DB2 driver: " + e.getMessage());
			}
			return connection;
				
	}
}
