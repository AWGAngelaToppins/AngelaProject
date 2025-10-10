package com.awginc.ulg;

public class AuditLogDeleteLinkGroup {
	
	//keep track of items move to another link group
	
	private ReportObj reportObj;
	private String linkGrpNbr;
	
	public ReportObj getReportObj() {
		return reportObj;
	}
	public void setReportObj(ReportObj reportObj) {
		this.reportObj = reportObj;
	}
	public String getLinkGrpNbr() {
		return linkGrpNbr;
	}
	public void setLinkGrpNbr(String linkGrpNbr) {
		this.linkGrpNbr = linkGrpNbr;
	}
	
	
}
