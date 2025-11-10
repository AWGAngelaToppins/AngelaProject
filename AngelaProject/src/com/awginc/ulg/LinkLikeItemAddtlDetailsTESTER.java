package com.awginc.ulg;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.awg.common.AwgConstants;

public class LinkLikeItemAddtlDetailsTESTER    {
	

	private static String outgoingDirectory="C:\\Users\\atoppins\\Downloads\\ULGtesting\\";
	
	public static String process() throws Exception {
		
		List<String> reportBeanList = new ArrayList<String>();

   		File[] incomingFiles = Utility.getFiles(outgoingDirectory);
   		int nbrOfFiles = incomingFiles.length;

   		File inputFile;
   		for (int i = 0; i < nbrOfFiles; i++) {
   			inputFile = new File(outgoingDirectory+incomingFiles[i].getName());
   			if(incomingFiles[i].getName().contains(UlgConstants.ulg4)) {
   				reportBeanList = processLinkGroups(inputFile);	
   				ReportOutputs.createOutputFileAllItemDetails(reportBeanList, outgoingDirectory);
   			}
   		}
		
		return "";

	}
	private static List<String> processLinkGroups(File  inputFile) throws Exception {

		List<String> reportBeanList = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();
		ReportObj reportBeanObj = new ReportObj();
		ReportObj addtlInfo = new ReportObj();
		List<String> textList = Utility.retrieveTextFile(inputFile);
		
		
		String xrefLinkGroupDesc="";
		
		
		System.out.println(UlgConstants.ulg4+".txt: total input records "+textList.size());
		
		for(String text:textList) {
			if(text.contains("--NextLinkGroup--")){
				reportBeanList.add(text);
			}else
			{
				
				temp = Utility.split(AwgConstants.delimiter,text);

				reportBeanObj = new ReportObj();
				reportBeanObj.setReportTempLinkGroupNbr(temp.get(0).trim());
				reportBeanObj.setReportItemCode(temp.get(2).trim());
				reportBeanObj.setReportCatNbr(temp.get(3).trim());
				reportBeanObj.setReportLblCd(temp.get(4).trim());
				reportBeanObj.setReportVendorCd(temp.get(5).trim());
				reportBeanObj.setReportSeason(temp.get(6).trim());
				reportBeanObj.setReportLinkGroupDesc(temp.get(7).trim());
				reportBeanObj.setReportSizeRange(temp.get(8).trim());
				reportBeanObj.setReportBspRange(temp.get(9).trim());
				reportBeanObj.setReportBspItem(temp.get(10).trim());
				reportBeanObj.setReportBrand(temp.get(11).trim());
				reportBeanObj.setReportSubCatDesc(temp.get(12).trim());
				reportBeanObj.setReportPackRange(temp.get(13).trim());
				
				addtlInfo = ItemDetails.getAddtlItemInfo(testPreparedStatement(SqlConstants.addtlItemInfo), temp.get(2).trim());
				reportBeanObj.setReportUnitRange(addtlInfo.getReportUnitRange());
				reportBeanObj.setReportCitySrpRange(addtlInfo.getReportCitySrpRange());
								
				reportBeanObj.setReportUpc(temp.get(16).trim());
				reportBeanObj.setReportCmdty(temp.get(17).trim()); //formattedCmdty
				reportBeanObj.setReportSubCmdty(temp.get(18).trim()); //formattedSubCmdty   
				
				reportBeanObj.setReportObiRange(temp.get(19).trim());
				reportBeanObj.setReportItemDesc(temp.get(20).trim());
				
				reportBeanObj.setFaciliyToCorporateLevelKey(getCompareKey(reportBeanObj));
				reportBeanObj.setFaciliyToCorporateLevelKeyUlg(addtlInfo.getFaciliyToCorporateLevelKeyUlg());

				if(reportBeanObj.getFaciliyToCorporateLevelKey().equals(reportBeanObj.getFaciliyToCorporateLevelKeyUlg()))
					reportBeanObj.setUpdateFacilityToCorporateflag("no");
				else
					reportBeanObj.setUpdateFacilityToCorporateflag("yes");
				
				reportBeanObj.setReportLinkGroupNbr(addtlInfo.getReportLinkGroupNbr());//actual link group nbr if value is not 0
				
				reportBeanObj.setReportUserUpdateId(addtlInfo.getReportUserUpdateId());
				
				//ULG_XREF:ulg_desc
				xrefLinkGroupDesc = ItemDetails.getXrefLinkGroupDesc(testPreparedStatement(SqlConstants.getXrefLinkGroupDesc), Integer.parseInt(addtlInfo.getReportLinkGroupNbr()));
				reportBeanObj.setReportLinkGroupDescXref(xrefLinkGroupDesc);
				
				//don't override user custom link group description 
				if(addtlInfo.getReportUserUpdateId()==null){
					//dont do anything, these are new items - not in the ULG_ITEM table yet
//					System.out.println("skip "+temp.get(2).trim());
				}else
				if(!addtlInfo.getReportUserUpdateId().contains("AutoBatch")){
					reportBeanObj.setReportLinkGroupDesc(xrefLinkGroupDesc);
				}
				
				reportBeanObj.setReportDiscontinuedFlag(temp.get(26).trim());
				
				
				reportBeanList.add(formatOutputRecord(reportBeanObj));
			}
		}
		return reportBeanList;
	}
	private static String getCompareKey(ReportObj reportBeanObj) {
		StringBuilder faciliyToCorporateLevelKey = new StringBuilder();

		faciliyToCorporateLevelKey.append(reportBeanObj.getReportItemDesc()).append(":");
		faciliyToCorporateLevelKey.append(reportBeanObj.getReportPackRange()).append(":");
		faciliyToCorporateLevelKey.append(reportBeanObj.getReportSizeRange()).append(":");
		faciliyToCorporateLevelKey.append(reportBeanObj.getReportBspRange()).append(":");
		faciliyToCorporateLevelKey.append(reportBeanObj.getReportUnitRange()).append(":");
		faciliyToCorporateLevelKey.append(reportBeanObj.getReportCitySrpRange()).append(":");
		faciliyToCorporateLevelKey.append(reportBeanObj.getReportUpc()).append(":");
		faciliyToCorporateLevelKey.append(reportBeanObj.getReportObiRange()).append(":");
		faciliyToCorporateLevelKey.append(reportBeanObj.getReportCmdty()).append(":");
		faciliyToCorporateLevelKey.append(reportBeanObj.getReportSubCmdty());

		return faciliyToCorporateLevelKey.toString();
	}
	private static String formatOutputRecord(ReportObj obj) {
		
		StringBuilder reportLine = new StringBuilder();
		
		reportLine.append(Utility.newValue2("  "+obj.getReportTempLinkGroupNbr(),20)+"|"); 
		reportLine.append(Utility.newValue(" "+obj.getReportLinkGroupNbr(),20)+"|");   //actual link group nbr if value is not 0
		reportLine.append(Utility.newValue(obj.getReportItemCode(),12)+"|"); 
		reportLine.append(Utility.newValue(obj.getReportCatNbr(),8)+"|"); 
		reportLine.append(Utility.newValue(obj.getReportLblCd(),6)+"|"); 
		reportLine.append(Utility.newValue(obj.getReportVendorCd(),12)+"|"); 
		reportLine.append(Utility.newSeasonValue(obj.getReportSeason(),7)+"|"); 
		reportLine.append(Utility.newValue(obj.getReportLinkGroupDesc(),90)+"|"); 
		reportLine.append(Utility.newValue(obj.getReportSizeRange(),30) +"|"); 
		reportLine.append(Utility.newValue(obj.getReportBspRange(),15)+"|"); //bsp range


		reportLine.append(Utility.newValue(obj.getReportBspItem(),8)+"|"); //bsp for itemcode
		reportLine.append(Utility.newValue(obj.getReportBrand(),18)+"|"); 
		reportLine.append(Utility.newValue(obj.getReportSubCatDesc(),50)+"|"); 
		reportLine.append(Utility.newValue(obj.getReportPackRange(), 30) +"|");
		reportLine.append(Utility.newValue(obj.getReportUnitRange(),30) +"|");
		reportLine.append(Utility.newValue(obj.getReportCitySrpRange(),30) +"|");
		reportLine.append(Utility.newValue(obj.getReportUpc(),30) +"|");
		
		reportLine.append(Utility.newValue(obj.getReportCmdty(),7) +"|");
		reportLine.append(Utility.newValue(obj.getReportSubCmdty(),10)+"|" );
		reportLine.append(Utility.newValue(obj.getReportObiRange(),50) +"|");
		reportLine.append(Utility.newValue(obj.getReportItemDesc(), 30) +"|");
		reportLine.append(Utility.newValue(obj.getUpdateFacilityToCorporateflag(), 10) +"|");
		reportLine.append(Utility.newValue(obj.getFaciliyToCorporateLevelKey(), 120) +"|");
		reportLine.append(Utility.newValue(obj.getFaciliyToCorporateLevelKeyUlg(), 120)  +"|");
		reportLine.append(Utility.newValue(obj.getReportUserUpdateId(),20) +"|");
		reportLine.append(Utility.newValue(obj.getReportLinkGroupDescXref(),100)+"|");
		reportLine.append(Utility.newValue(obj.getReportDiscontinuedFlag(),40));
		
		return reportLine.toString();
		
	}
	public static PreparedStatement testPreparedStatement(String query) throws SQLException {
		//FOR TESTING ONLY
		Connection conn = getConn();
		PreparedStatement test = conn.prepareStatement(query);
		
		return test;
		
	}
	public static Connection getConn() throws SQLException {
//		String url ="jdbc:db2://DWPROD01:60000/DWPROD";
//		String id  = "databob";
//		String psw = "databob";

		String url ="jdbc:db2://PRHDB2:60000/AWG";
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
	
