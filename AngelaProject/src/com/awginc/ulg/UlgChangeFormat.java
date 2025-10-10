package com.awginc.ulg;

import java.util.ArrayList;
import java.util.List;

public class UlgChangeFormat {

	public static ReportObj setNewReportBeanObj(ReportObj inputObj) throws Exception {
		
		ReportObj reportBeanObj = new ReportObj();
		
		reportBeanObj = new ReportObj();
		reportBeanObj.setReportTempLinkGroupNbr(inputObj.getReportTempLinkGroupNbr());
		reportBeanObj.setReportLinkGroupNbr(inputObj.getReportLinkGroupNbr());
		reportBeanObj.setReportItemCode(inputObj.getReportItemCode());
		
		reportBeanObj.setReportItemDesc(inputObj.getReportItemDesc());
		reportBeanObj.setReportBspItem(inputObj.getReportBspItem());
		reportBeanObj.setReportBspRange(inputObj.getReportBspRange());
		reportBeanObj.setReportSizeRange(inputObj.getReportSizeRange());
		reportBeanObj.setReportCatNbr(inputObj.getReportCatNbr());
		reportBeanObj.setReportLblCd(inputObj.getReportLblCd());

		reportBeanObj.setReportVendorCd(inputObj.getReportVendorCd());
		reportBeanObj.setReportSeason(inputObj.getReportSeason());
		reportBeanObj.setReportLinkGroupDesc(inputObj.getReportLinkGroupDesc());//don't remove the comma for this field
		reportBeanObj.setReportBrand(inputObj.getReportBrand());
		reportBeanObj.setReportSubCatDesc(inputObj.getReportSubCatDesc());
		reportBeanObj.setReportPackRange(inputObj.getReportPackRange());
		reportBeanObj.setReportUnitRange(inputObj.getReportUnitRange());
		reportBeanObj.setReportCitySrpRange(inputObj.getReportCitySrpRange());
		reportBeanObj.setReportUpc(inputObj.getReportUpc());
		reportBeanObj.setReportCmdty(inputObj.getReportCmdty());    //formattedCmdty
		reportBeanObj.setReportSubCmdty(inputObj.getReportSubCmdty()); //formattedSubCmdty   
		reportBeanObj.setReportObiRange(inputObj.getReportObiRange());
		reportBeanObj.setReportDept(inputObj.getReportDept());
		
		reportBeanObj.setItemInfoUpdateflag(inputObj.getItemInfoUpdateflag());

		reportBeanObj.setFaciliyToCorporateLevelKeyUlg(inputObj.getFaciliyToCorporateLevelKey()); 
		
		reportBeanObj.setReportLinkGroupDescXref(inputObj.getReportLinkGroupDescXref());
		
		return reportBeanObj;
	}
	
	public static ReportObj setAddtlReportBeanObj(List<String> temp) throws Exception {
		
		ReportObj reportBeanObj = new ReportObj();
		
		reportBeanObj = new ReportObj();
		reportBeanObj.setReportTempLinkGroupNbr(removeCommas(temp.get(0).trim()));
		reportBeanObj.setReportLinkGroupNbr(removeCommas(temp.get(1).trim()));
		reportBeanObj.setReportItemCode(removeCommas(temp.get(2).trim()));
		
		reportBeanObj.setReportItemDesc(removeCommas(temp.get(20).trim()));
		reportBeanObj.setReportBspItem(removeCommas(temp.get(10).trim()));
		reportBeanObj.setReportBspRange(removeCommas(temp.get(9).trim()));
		reportBeanObj.setReportSizeRange(removeCommas(temp.get(8).trim()));
		reportBeanObj.setReportCatNbr(removeCommas(temp.get(3).trim()));
		reportBeanObj.setReportLblCd(removeCommas(temp.get(4).trim()));

		reportBeanObj.setReportVendorCd(removeCommas(temp.get(5).trim()));
		reportBeanObj.setReportSeason(temp.get(6).trim());
		reportBeanObj.setReportLinkGroupDesc(temp.get(7).trim());//don't remove the comma for this field
		reportBeanObj.setReportBrand(removeCommas(temp.get(11).trim()));
		reportBeanObj.setReportSubCatDesc(removeCommas(temp.get(12).trim()));
		reportBeanObj.setReportPackRange(removeCommas(temp.get(13).trim()));
		reportBeanObj.setReportUnitRange(removeCommas(temp.get(14).trim()));
		reportBeanObj.setReportCitySrpRange(removeCommas(temp.get(15).trim()));
		reportBeanObj.setReportUpc(removeCommas(temp.get(16).trim()));
		reportBeanObj.setReportCmdty(removeCommas(temp.get(17).trim()));    //formattedCmdty
		reportBeanObj.setReportSubCmdty((temp.get(18).trim())); //formattedSubCmdty   
		reportBeanObj.setReportObiRange(removeCommas(temp.get(19).trim()));
		reportBeanObj.setReportDept(removeCommas(temp.get(26).trim()));
		
		reportBeanObj.setItemInfoUpdateflag(removeCommas(temp.get(21).trim()));

		reportBeanObj.setFaciliyToCorporateLevelKeyUlg(temp.get(23).trim()); 
		
		reportBeanObj.setReportLinkGroupDescXref(temp.get(25).trim());
		
		return reportBeanObj;
	}
	public static String removeCommas(String inputValue) throws Exception {

		String newValue = inputValue.replaceAll(",", " ");
		return newValue;
	}
	private static String buildReportColumnHeadings() {

		String valueString = 
			Utility.newValue("Link Grp",12)+","+ 
			Utility.newValue("Item Code",10)+","+ 
			Utility.newValue("Item Desc",30)+","+  

			Utility.newValue("BSP",8)+","+ 
			Utility.newValue("BSP Range",15) +","+ 
			Utility.newValue("Size Range",20) +","+ 

			Utility.newValue("Cat Nbr",7)+","+ 
			Utility.newValue("Label code",10)+","+ 				
			Utility.newValue("vendor code",11)+","+ 				
			Utility.newValue("Season",6)+","+ 				
			Utility.newValue("Link Group Description",90)+","+ 

			Utility.newValue("Brand",18)+","+ 
			Utility.newValue("Sub Category",50)+","+ 
			Utility.newValue("Pack",10) +","+ 
			Utility.newValue("Unit",10) +","+ 
			Utility.newValue("City Srp",14) +","+ 
			Utility.newValue("UPC",16)+","+ 
			Utility.newValue("Cmdty",7)+","+ 
			Utility.newValue("Sub Cmdty",10)+","+
			Utility.newValue("OBI",100)+","+ 
		    Utility.newValue("Dept Code",20); 
		
  		return valueString;
	}
	public static List<String> buildReportDetails(List<ReportObj> itemList) {

		List<String> reportList = new ArrayList<String>();
		
		reportList.add(buildReportColumnHeadings()); 
		
		String valueString;
	
  	    for(ReportObj item:itemList) {
  		valueString = 
  		                item.getReportLinkGroupNbr()+","+ 

  		            "'"+item.getReportItemCode()+"',"+ 
					    item.getReportItemDesc()+","+ 

					"'"+item.getReportBspItem()+"',"+ 
					"'"+item.getReportBspRange()+"',"+
					"'"+item.getReportSizeRange() +"',"+ 

					    String.valueOf(item.getReportCatNbr())+","+ 
					    item.getReportLblCd()+","+ 
					"'"+item.getReportVendorCd()+"',"+ 
					    item.getReportSeason()+","+ 
					    item.getReportLinkGroupDesc()+","+ 
					    item.getReportBrand()+","+ 
					    item.getReportSubCatDesc()+","+ 
					"'"+item.getReportPackRange() +"',"+
					"'"+item.getReportUnitRange() +"',"+
					"'"+item.getReportCitySrpRange() +"',"+
					"'"+item.getReportUpc()+"',"+
					"'"+item.getReportCmdty() +"',"+
					"'"+item.getReportSubCmdty()+"'," +
					"'"+item.getReportObiRange() +"'," +
  					"'"+item.getReportDept()+"', " ;
  		
  		reportList.add(valueString);
	}
	
	return reportList;
	}
	public static List<String> buildReportDetailsOrig(List<ReportObj> itemList) {

		List<String> reportList = new ArrayList<String>();
		
		reportList.add(buildReportColumnHeadings()); 
		
		String valueString;
	
  	    for(ReportObj item:itemList) {
  		valueString = 
  		            Utility.newValue("'"+item.getReportLinkGroupNbr(),12)+"',"+ 

  		            Utility.newValue("'"+item.getReportItemCode(),10)+"',"+ 
					Utility.newValue("'"+item.getReportItemDesc(), 30)+"',"+ 

					Utility.newValue("'"+item.getReportBspItem(),8)+"',"+ 
					Utility.newValue("'"+item.getReportBspRange(),15)+"',"+
					Utility.newValue("'"+item.getReportSizeRange(),20) +"',"+ 

					Utility.newValue(String.valueOf(item.getReportCatNbr()),7)+"',"+ 
					Utility.newValue("'"+item.getReportLblCd(),10)+"',"+ 
					Utility.newValue("'"+item.getReportVendorCd(),11)+"',"+ 
					Utility.newValue("'"+item.getReportSeason(),6)+"',"+ 
					Utility.newValue("'"+item.getReportLinkGroupDesc(),90)+"',"+ 
					Utility.newValue("'"+item.getReportBrand(),18)+"',"+ 
					Utility.newValue("'"+item.getReportSubCatDesc(),50)+"',"+ 
					Utility.newValue("'"+item.getReportPackRange(), 10) +"',"+
					Utility.newValue("'"+item.getReportUnitRange(),10) +"',"+
					Utility.newValue("'"+item.getReportCitySrpRange(),14) +"',"+
					Utility.newValue("'"+item.getReportUpc(),16) +"',"+
					Utility.newValue("'"+item.getReportCmdty(),7) +"',"+
					Utility.newValue("'"+item.getReportSubCmdty(),10)+"'," +
					Utility.newValue("'"+item.getReportObiRange(),100) +"'," +
  					Utility.newValue("'"+item.getReportDept(),20)+"' " ;
  		
  		reportList.add(valueString);
	}
	
	return reportList;
	}	
}
