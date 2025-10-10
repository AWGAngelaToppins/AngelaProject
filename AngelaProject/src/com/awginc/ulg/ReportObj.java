package com.awginc.ulg;

import java.util.List;

public class ReportObj {

	private String autoChanges;
	private String autoChangeType;
	private String itemInfoUpdateflag;
	private String origLinkGrpNb;
	
	private String reportTempLinkGroupNbr;
	private String reportLinkGroupNbr;
	private String reportUserUpdateId;
	private String reportUserUpdateTs;
	private String reportCatNbr;
	private String reportLblCd;
	private String reportVendorCd;
	private String reportSeason;
	
	private String reportLinkGroupDesc;
	private String reportLinkGroupDescXref; //ULG_XREF table
	
	private String reportSizeRange;
	private String reportBspRange;
	private String reportBspItem; 
	private String reportBrand;
	private String reportSubCatDesc;
	private String reportItemCode;
	private String reportPackRange;
	private String reportUnitRange;
	private String reportCitySrpRange;
	private String reportUpc;
	private String reportCmdty;
	private String reportSubCmdty;
	private String reportObiRange;
	private String reportItemDesc;
	
	private List<String> linkGroupListResult;
	private List<String> linkGroupItemListResult;

	private String faciliyToCorporateLevelKey;      //built during retrieving addtl details
	private String faciliyToCorporateLevelKeyUlg;   //data on the ULG_ITEM table
	private String updateFacilityToCorporateflag;   //if yes, update item info on ULG_ITEM table
	
	private String reportDept;
	
	private String reportDiscontinuedFlag;
	
	
	
	public List<String> getLinkGroupListResult() {
		return linkGroupListResult;
	}
	public void setLinkGroupListResult(List<String> linkGroupListResult) {
		this.linkGroupListResult = linkGroupListResult;
	}
	public List<String> getLinkGroupItemListResult() {
		return linkGroupItemListResult;
	}
	public void setLinkGroupItemListResult(List<String> linkGroupItemListResult) {
		this.linkGroupItemListResult = linkGroupItemListResult;
	}
	public String getReportUserUpdateId() {
		return reportUserUpdateId;
	}
	public void setReportUserUpdateId(String reportUserUpdateId) {
		this.reportUserUpdateId = reportUserUpdateId;
	}
	public String getReportUserUpdateTs() {
		return reportUserUpdateTs;
	}
	public void setReportUserUpdateTs(String reportUserUpdateTs) {
		this.reportUserUpdateTs = reportUserUpdateTs;
	}
	public String getReportCatNbr() {
		return reportCatNbr;
	}
	public void setReportCatNbr(String reportCatNbr) {
		this.reportCatNbr = reportCatNbr;
	}
	public String getReportLblCd() {
		return reportLblCd;
	}
	public void setReportLblCd(String reportLblCd) {
		this.reportLblCd = reportLblCd;
	}
	public String getReportVendorCd() {
		return reportVendorCd;
	}
	public void setReportVendorCd(String reportVendorCd) {
		this.reportVendorCd = reportVendorCd;
	}
	public String getReportSeason() {
		return reportSeason;
	}
	public void setReportSeason(String reportSeason) {
		this.reportSeason = reportSeason;
	}
	public String getReportSizeRange() {
		return reportSizeRange;
	}
	public void setReportSizeRange(String reportSizeRange) {
		this.reportSizeRange = reportSizeRange;
	}
	public String getReportBspRange() {
		return reportBspRange;
	}
	public void setReportBspRange(String reportBspRange) {
		this.reportBspRange = reportBspRange;
	}
	public String getReportBspItem() {
		return reportBspItem;
	}
	public void setReportBspItem(String reportBspItem) {
		this.reportBspItem = reportBspItem;
	}
	public String getReportBrand() {
		return reportBrand;
	}
	public void setReportBrand(String reportBrand) {
		this.reportBrand = reportBrand;
	}
	public String getReportItemCode() {
		return reportItemCode;
	}
	public void setReportItemCode(String reportItemCode) {
		this.reportItemCode = reportItemCode;
	}
	public String getReportPackRange() {
		return reportPackRange;
	}
	public void setReportPackRange(String reportPackRange) {
		this.reportPackRange = reportPackRange;
	}
	public String getReportUnitRange() {
		return reportUnitRange;
	}
	public void setReportUnitRange(String reportUnitRange) {
		this.reportUnitRange = reportUnitRange;
	}
	public String getReportCitySrpRange() {
		return reportCitySrpRange;
	}
	public void setReportCitySrpRange(String reportCitySrpRange) {
		this.reportCitySrpRange = reportCitySrpRange;
	}
	public String getReportUpc() {
		return reportUpc;
	}
	public void setReportUpc(String reportUpc) {
		this.reportUpc = reportUpc;
	}
	public String getReportCmdty() {
		return reportCmdty;
	}
	public void setReportCmdty(String reportCmdty) {
		this.reportCmdty = reportCmdty;
	}
	public String getReportSubCmdty() {
		return reportSubCmdty;
	}
	public void setReportSubCmdty(String reportSubCmdty) {
		this.reportSubCmdty = reportSubCmdty;
	}
	public String getReportObiRange() {
		return reportObiRange;
	}
	public void setReportObiRange(String reportObiRange) {
		this.reportObiRange = reportObiRange;
	}
	public String getReportItemDesc() {
		return reportItemDesc;
	}
	public void setReportItemDesc(String reportItemDesc) {
		this.reportItemDesc = reportItemDesc;
	}
	public String getReportLinkGroupNbr() {
		return reportLinkGroupNbr;
	}
	public void setReportLinkGroupNbr(String reportLinkGroupNbr) {
		this.reportLinkGroupNbr = reportLinkGroupNbr;
	}
	public String getReportSubCatDesc() {
		return reportSubCatDesc;
	}
	public void setReportSubCatDesc(String reportSubCatDesc) {
		this.reportSubCatDesc = reportSubCatDesc;
	}
	public String getReportLinkGroupDesc() {
		return reportLinkGroupDesc;
	}
	public void setReportLinkGroupDesc(String reportLinkGroupDesc) {
		this.reportLinkGroupDesc = reportLinkGroupDesc;
	}
	public String getFaciliyToCorporateLevelKey() {
		return faciliyToCorporateLevelKey;
	}
	public void setFaciliyToCorporateLevelKey(String faciliyToCorporateLevelKey) {
		this.faciliyToCorporateLevelKey = faciliyToCorporateLevelKey;
	}
	public String getUpdateFacilityToCorporateflag() {
		return updateFacilityToCorporateflag;
	}
	public void setUpdateFacilityToCorporateflag(String updateFacilityToCorporateflag) {
		this.updateFacilityToCorporateflag = updateFacilityToCorporateflag;
	}
	public String getFaciliyToCorporateLevelKeyUlg() {
		return faciliyToCorporateLevelKeyUlg;
	}
	public void setFaciliyToCorporateLevelKeyUlg(String faciliyToCorporateLevelKeyUlg) {
		this.faciliyToCorporateLevelKeyUlg = faciliyToCorporateLevelKeyUlg;
	}
	public String getReportTempLinkGroupNbr() {
		return reportTempLinkGroupNbr;
	}
	public void setReportTempLinkGroupNbr(String reportTempLinkGroupNbr) {
		this.reportTempLinkGroupNbr = reportTempLinkGroupNbr;
	}
	public String getReportLinkGroupDescXref() {
		return reportLinkGroupDescXref;
	}
	public void setReportLinkGroupDescXref(String reportLinkGroupDescXref) {
		this.reportLinkGroupDescXref = reportLinkGroupDescXref;
	}
	public String getAutoChanges() {
		return autoChanges;
	}
	public void setAutoChanges(String autoChanges) {
		this.autoChanges = autoChanges;
	}
	public String getAutoChangeType() {
		return autoChangeType;
	}
	public void setAutoChangeType(String autoChangeType) {
		this.autoChangeType = autoChangeType;
	}
	public String getItemInfoUpdateflag() {
		return itemInfoUpdateflag;
	}
	public void setItemInfoUpdateflag(String itemInfoUpdateflag) {
		this.itemInfoUpdateflag = itemInfoUpdateflag;
	}
	public String getOrigLinkGrpNb() {
		return origLinkGrpNb;
	}
	public void setOrigLinkGrpNb(String origLinkGrpNb) {
		this.origLinkGrpNb = origLinkGrpNb;
	}
	public String getReportDiscontinuedFlag() {
		return reportDiscontinuedFlag;
	}
	public void setReportDiscontinuedFlag(String reportDiscontinuedFlag) {
		this.reportDiscontinuedFlag = reportDiscontinuedFlag;
	}
	public String getReportDept() {
		return reportDept;
	}
	public void setReportDept(String reportDept) {
		this.reportDept = reportDept;
	}
	
	
	

}
