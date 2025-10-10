package com.awg.common;

public interface AwgConstants {

	//report type that BRdata needs to use in the header record
	public static final String percentageProofReport   = "Percentage Proof";
	public static final String priceChangeReport       = "Price Change";
	public static final String shelfTalkerReport		   = "Shelf Talker Report";
	public static final String tprErrorReport          = "TPR ERROR REPORT";

	//misc variables used in the core framework
	public static final String invalidReportType       = "invalidReportType";
	public static final String delimiter   = "|";
	public static final String suffix = ".txt";
	public static final String yes = "yes";
	public static final String no  = "no";

	//reportId defined in onDemand reporting process that converts text file to pdf
//	public static final String percentageProofReportId = "01618";
//	public static final String percentageProofVmcReportId = "01619";
//	public static final String priceChangeReportId     = "05444";
//	public static final String priceChangeVmcReportId =  "05445";
//	public static final String shelfTalkerReportId     = "05547";
//	public static final String tprErrorReportId        = "02305";
//	public static final String eligItemsReportId       = "05539";

	//prefix for the output formatted report file name
//	public static final String outputPercentageProofReportName = "BATAPP.PRICING.PERCENTAGEPROOF.";
//	public static final String outputPriceChangeReportName     = "BATAPP.PRICING.PRICECHANGE.";
//	public static final String outputShelfTalkerReportName     = "BATAPP.PRICING.SHELFTALKER.";
//	public static final String outputEligItemsReportName       = "BATAPP.PRICING.ELIGITEMS.";
//	public static final String outputTprErrReportName          = "BATAPP.PRICING.TPRERROR.";
}
