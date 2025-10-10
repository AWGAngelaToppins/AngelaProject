package com.awg.common;

import java.util.List;

public class ReportParms {

	
	//Y - add header info on pages 2 and on
	//N - add header info only on page 1
	private String headerInfoEveryPage;  

	//Y - add column info on pages 2 and on
	//N - add column info only on page 1
	private String columnNamesEveryPage;

	//0 - HEADER
	//1 - DETAIL
	private String tprEligItemsRecordType;
	
	//header info
	private String storeNbr;
	private String storeName;
	private String reportBaseVMC;
	private String reportType;
	private String exciseTax;
	private String freight;
	private String markup;
	private String address;
	private String city;
	private String state;
	private String dept;
	private String tprParentStore;
	public String getSrp() {
		return srp;
	}

	public void setSrp(String srp) {
		this.srp = srp;
	}

	public String getChildOf() {
		return childOf;
	}

	public void setChildOf(String childOf) {
		this.childOf = childOf;
	}

	private String tprDate;
	
	//new attribute aof Nov 2023
	private String srp;  //not sure what this means but it's on the trp elig report
	private String childOf;
	
	//detail info
	private int rowCountPerPage;

	
	private String outputReportName;
	private String reportId;
	private String divisionCode;
	
	private String nextReportCategoryBreakText;
	private String reportCategoryBreakText;
	
	
	private String newReportHeadingText;
	private String nextNewReportHeadingText;
	
	
	private int pageNumber;
	private String currentDate;
	private String currentTime;
	
	private List<String> columnList;
	
	private String displayCategoryAtHeaderLevel;
	private String displayCategoryBeforeColumnHeadings;
	private String newPageAtNewHeader;
	
	private String displayCategoryTotals;
	
	
	public String getDisplayCategoryTotals() {
		return displayCategoryTotals;
	}

	public void setDisplayCategoryTotals(String displayCategoryTotals) {
		this.displayCategoryTotals = displayCategoryTotals;
	}

	public String getDisplayCategoryBeforeColumnHeadings() {
		return displayCategoryBeforeColumnHeadings;
	}

	public void setDisplayCategoryBeforeColumnHeadings(String displayCategoryBeforeColumnHeadings) {
		this.displayCategoryBeforeColumnHeadings = displayCategoryBeforeColumnHeadings;
	}

	public String getHeaderInfoEveryPage() {
		return headerInfoEveryPage;
	}

	public void setHeaderInfoEveryPage(String headerInfoEveryPage) {
		this.headerInfoEveryPage = headerInfoEveryPage;
	}

	public String getColumnNamesEveryPage() {
		return columnNamesEveryPage;
	}

	public void setColumnNamesEveryPage(String columnNamesEveryPage) {
		this.columnNamesEveryPage = columnNamesEveryPage;
	}

	public String getStoreNbr() {
		return storeNbr;
	}

	public void setStoreNbr(String storeNbr) {
		this.storeNbr = storeNbr;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	public String getReportBaseVMC() {
		return reportBaseVMC;
	}
	
	public void setReportBaseVMC(String reportBaseVMC) {
		this.reportBaseVMC = reportBaseVMC;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	public String getTprParentStore() {
		return tprParentStore;
	}
	
	public void setTprParentStore(String parentStore) {
		this.tprParentStore = parentStore;
	}

	public String getExciseTax() {
		return exciseTax;
	}

	public void setExciseTax(String exciseTax) {
		this.exciseTax = exciseTax;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String strAddress) {
		this.address = strAddress;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getMarkup() {
		return markup;
	}

	public void setMarkup(String markup) {
		this.markup = markup;
	}

	public String getOutputReportName() {
		return outputReportName;
	}

	public void setOutputReportName(String outputReportName) {
		this.outputReportName = outputReportName;
	}

	public int getRowCountPerPage() {
		return rowCountPerPage;
	}

	public void setRowCountPerPage(int rowCountPerPage) {
		this.rowCountPerPage = rowCountPerPage;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	public String getNextReportCategoryBreakText() {
		return nextReportCategoryBreakText;
	}

	public void setNextReportCategoryBreakText(String nextReportCategoryBreakText) {
		this.nextReportCategoryBreakText = nextReportCategoryBreakText;
	}

	public String getReportCategoryBreakText() {
		return reportCategoryBreakText;
	}

	public void setReportCategoryBreakText(String reportCategoryBreakText) {
		this.reportCategoryBreakText = reportCategoryBreakText;
	}

	public String getNewReportHeadingText() {
		return newReportHeadingText;
	}

	public void setNewReportHeadingText(String newReportHeadingText) {
		this.newReportHeadingText = newReportHeadingText;
	}

	public String getNextNewReportHeadingText() {
		return nextNewReportHeadingText;
	}

	public void setNextNewReportHeadingText(String nextNewReportHeadingText) {
		this.nextNewReportHeadingText = nextNewReportHeadingText;
	}

	public String getDisplayCategoryAtHeaderLevel() {
		return displayCategoryAtHeaderLevel;
	}

	public void setDisplayCategoryAtHeaderLevel(String displayCategoryAtHeaderLevel) {
		this.displayCategoryAtHeaderLevel = displayCategoryAtHeaderLevel;
	}
	
	public void setNewPageAtNewHeader(String newPageAtNewHeader) {
		this.newPageAtNewHeader = newPageAtNewHeader;
	}
	public void setTprDate(String tprDate) {
		this.tprDate = tprDate;
	}
	public String getTprDate() {
		return tprDate;
	}

	public String getTprEligItemsRecordType() {
		return tprEligItemsRecordType;
	}

	public void setTprEligItemsRecordType(String tprEligItemsRecordType) {
		this.tprEligItemsRecordType = tprEligItemsRecordType;
	}
}
