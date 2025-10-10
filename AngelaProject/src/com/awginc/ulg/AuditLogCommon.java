package com.awginc.ulg;

import java.util.List;

public class AuditLogCommon {

	public static ReportObj getItemInfo(String input) throws Exception {
		
		ReportObj reportObj = new ReportObj(); 
		
		if(input.contains("NONE")) {//on initial run, there won't be any ULG records
			reportObj.setReportLinkGroupNbr("NONE");
			reportObj.setReportLinkGroupDesc("NONE");
			//test
		} else
		{	
			List<String> temp;
			
			temp = Utility.split(":", input);
			reportObj = new ReportObj();
			reportObj.setReportItemDesc(temp.get(0));
			reportObj.setReportPackRange(temp.get(1));
			reportObj.setReportSizeRange(temp.get(2));
			reportObj.setReportBspRange(temp.get(3));
			reportObj.setReportUnitRange(temp.get(4));
			reportObj.setReportCitySrpRange(temp.get(5));
			reportObj.setReportUpc(temp.get(6));
			reportObj.setReportObiRange(temp.get(7));
			reportObj.setReportCmdty(temp.get(8));
			reportObj.setReportSubCmdty(temp.get(9));
		}
		return reportObj;
	}	
}
