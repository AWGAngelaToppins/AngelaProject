package com.awginc.ulg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportFormat {

	public static String buildReportColumnHeadings() {

		String valueString = 
			Utility.newValue("Link Group",12)+"|"+ 
			Utility.newValue("Link_Grp_Nbr",12)+"|"+ 
			Utility.newValue("Item_Code",10)+"|"+ 
			Utility.newValue("Item_Desc",30)+"|"+  

			Utility.newValue("BSP",8)+"|"+ 
			Utility.newValue("BSP_Unit_Range",15) +"|"+ 
			Utility.newValue("Size Range",20) +"|"+ 

			Utility.newValue("Cat_Nbr",7)+"|"+ 
			Utility.newValue("lblCd",5)+"|"+ 				
			Utility.newValue("vendor_code",11)+"|"+ 				
			Utility.newValue("season",6)+"|"+ 				
//			Utility.newValue("Link_Group_Description (brand/season(if applies)/subcat/size/bsp)",90)+"|"+ 
			Utility.newValue("Link_Group_Description (brand/season(if applies)/subcat/size)",90)+"|"+ 

			Utility.newValue("Brand",18)+"|"+ 
			Utility.newValue("Sub_Category",50)+"|"+ 
			Utility.newValue("Pack_Desc",10) +"|"+ 
			Utility.newValue("Unit_Range",10) +"|"+ 
			Utility.newValue("City_Srp_Range",14) +"|"+ 
			Utility.newValue("Upc_Desc",16)+"|"+ 
			Utility.newValue("Cmdty",7)+"|"+ 
			Utility.newValue("Sub_Cmdty",10)+"|"+
			Utility.newValue("Obi_Range",100)+"|"+ 
			Utility.newValue("Orig_Lnk_Grp_Nb",20); 
		    Utility.newValue("DeptCode",20); 
		
  		return valueString;
	}
	public static List<String> buildReportDetails(List<ReportObj> itemList, int linkGrpNbr) {

		List<String> reportList = new ArrayList<String>();
		
//		reportList.add("__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
		reportList.add(buildReportColumnHeadings()); 

		
		String valueString;
	
		String actualLinkGrpNbr;
		
		
	HashMap<String,String> customLinkGrp = new HashMap<String,String>();
  	for(ReportObj item:itemList) {
  		if(item.getReportUserUpdateId().contains("AutoBatch") || item.getReportLinkGroupNbr().equals("0") ) { 
  			//do nothing
  		}else
  			//save custom linkGrpNbr and desc
  			customLinkGrp.put(item.getReportLinkGroupNbr(), item.getReportLinkGroupDesc());
  	}	
	
	String linkGroupDesc;	
  	for(ReportObj item:itemList) {
  		if(item.getReportUserUpdateId().contains("AutoBatch") || item.getReportLinkGroupNbr().equals("0") ) 
  			actualLinkGrpNbr = String.valueOf(linkGrpNbr);
  		else
  			actualLinkGrpNbr = item.getReportLinkGroupNbr();  //if custom link, item will remain with the custom link grp
  		
  		
  		if(customLinkGrp.containsKey(item.getReportLinkGroupNbr()))
  			linkGroupDesc = customLinkGrp.get(item.getReportLinkGroupNbr());//use the actual custom link group desc rather than the build link group desc, ensures the ULG6_Updates.txt report file represent what the database has 
  		else
  	  		linkGroupDesc = item.getReportLinkGroupDesc();//build link group desc
  			
  		
  		valueString = Utility.newValue(item.getReportTempLinkGroupNbr(),12)+"|"+ 
// 		            Utility.newValue(String.valueOf(linkGrpNbr),12)+"|"+ 
  		            Utility.newValue(actualLinkGrpNbr,12)+"|"+ 

  		            Utility.newValue(item.getReportItemCode(),10)+"|"+ 
					Utility.newValue(item.getReportItemDesc(), 30)+"|"+ 

					Utility.newValue(item.getReportBspItem(),8)+"|"+ 
					Utility.newValue(item.getReportBspRange(),15)+"|"+
					Utility.newValue(item.getReportSizeRange(),20) +"|"+ 

					Utility.newValue(String.valueOf(item.getReportCatNbr()),7)+"|"+ 
					Utility.newValue(item.getReportLblCd(),5)+"|"+ 
					Utility.newValue(item.getReportVendorCd(),11)+"|"+ 
					Utility.newValue(item.getReportSeason(),6)+"|"+ 

//					Utility.newValue(item.getReportLinkGroupDesc(),90)+"|"+ 
					Utility.newValue(linkGroupDesc,90)+"|"+ 


					Utility.newValue(item.getReportBrand(),18)+"|"+ 
					Utility.newValue(item.getReportSubCatDesc(),50)+"|"+ 
					Utility.newValue(item.getReportPackRange(), 10) +"|"+
					Utility.newValue(item.getReportUnitRange(),10) +"|"+
					Utility.newValue(item.getReportCitySrpRange(),14) +"|"+
					Utility.newValue(item.getReportUpc(),16) +"|"+
					Utility.newValue(item.getReportCmdty(),7) +"|"+
					Utility.newValue(item.getReportSubCmdty(),10)+"|" +
					Utility.newValue(item.getReportObiRange(),100) +"|" +
  					Utility.newValue(item.getReportLinkGroupNbr(),20) +"|" +
 					Utility.newValue(item.getReportDept(),20) ;
  		
  		reportList.add(valueString);
	}
	
	return reportList;
	}
}
