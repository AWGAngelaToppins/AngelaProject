package com.awginc.ulg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateUlg {

	public static int updateItemInfo(PreparedStatement preparedStatement, ReportObj item) throws SQLException {
		int returnValue = 0;
		try {
			preparedStatement.setString(1,item.getReportItemDesc());
			preparedStatement.setString(2,item.getReportPackRange());
			preparedStatement.setString(3,item.getReportSizeRange());
			preparedStatement.setString(4,item.getReportBspRange());
			preparedStatement.setString(5,item.getReportUnitRange());
			preparedStatement.setString(6,item.getReportCitySrpRange());
			preparedStatement.setString(7,item.getReportUpc());
			preparedStatement.setString(8,item.getReportObiRange());
			preparedStatement.setString(9,item.getReportCmdty());
			preparedStatement.setString(10,item.getReportSubCmdty());
			preparedStatement.setString(11,formatItemCode(item.getReportItemCode()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;		
	}
	public static int insertUlgXref(PreparedStatement preparedStatement, int linkGrpNbr, String linkGrpDesc) throws SQLException {
		int returnValue = 0;
		try {
			preparedStatement.setInt(1,linkGrpNbr);
			preparedStatement.setString(2,linkGrpDesc);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;		
	}
	public static int insertUlgItemRecord(PreparedStatement preparedStatement, int linkGrpNbr, String itemCode, ReportObj obj) throws SQLException {
		int returnValue = 0;

		try {
			preparedStatement.setString(1,formatItemCode(itemCode));
			preparedStatement.setInt(2,linkGrpNbr);
			preparedStatement.setString(3,obj.getReportItemDesc());
			preparedStatement.setString(4,obj.getReportPackRange());
			preparedStatement.setString(5,obj.getReportSizeRange());
			preparedStatement.setString(6,obj.getReportBspRange());
			preparedStatement.setString(7,obj.getReportUnitRange());
			preparedStatement.setString(8,obj.getReportCitySrpRange());
			preparedStatement.setString(9,obj.getReportUpc());
			preparedStatement.setString(10,obj.getReportObiRange());
			preparedStatement.setString(11,obj.getReportCmdty());
			preparedStatement.setString(12,obj.getReportSubCmdty());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error UpdateUlg.insertUlgItemRecord() itemCode="+itemCode+"  linkGrpNbr="+linkGrpNbr);
			e.printStackTrace();
		}
		return returnValue;		
	}	
	public static int insertUlgAuditRecord(PreparedStatement preparedStatement, int auditSeq, ReportObj obj, String auditChangeDesc) throws SQLException {
		int returnValue = 0;

		try {
			preparedStatement.setInt(1,auditSeq);
			preparedStatement.setString(2,auditChangeDesc);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;		
	}
	public static int insertUlgAuditRecord2(PreparedStatement preparedStatement, int auditSeq, ReportObj obj, String auditChangeDesc) throws SQLException {
		int returnValue = 0;

		try {
			preparedStatement.setInt(1,auditSeq);
			preparedStatement.setString(2,auditChangeDesc);
			preparedStatement.setString(3,obj.getReportLinkGroupNbr());
			preparedStatement.setString(4,obj.getReportLinkGroupDesc());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;		
	}	
	public static int insertUlgAuditItemDetailsRecord(PreparedStatement preparedStatement, int auditSeq, ReportObj obj, String auditChangeDesc, ReportObj origUlgObj ) throws SQLException {
		int returnValue = 0;

		try {
			preparedStatement.setInt(1,auditSeq);
			preparedStatement.setString(2,auditChangeDesc);

			preparedStatement.setString(3,UpdateUlg.formatItemCode(obj.getReportItemCode()));
			preparedStatement.setString(4,obj.getReportLinkGroupNbr());
			preparedStatement.setString(5,obj.getReportLinkGroupDesc());
			preparedStatement.setString(6,obj.getReportItemDesc());
			preparedStatement.setString(7,obj.getReportPackRange());
			preparedStatement.setString(8,obj.getReportSizeRange());
			preparedStatement.setString(9,obj.getReportBspRange());
			preparedStatement.setString(10,obj.getReportUnitRange());
			preparedStatement.setString(11,obj.getReportCitySrpRange());
			preparedStatement.setString(12,obj.getReportUpc());
			preparedStatement.setString(13,obj.getReportObiRange());
			preparedStatement.setString(14,obj.getReportCmdty());
			preparedStatement.setString(15,obj.getReportSubCmdty());
			
			preparedStatement.setString(16,origUlgObj.getReportItemDesc());
			preparedStatement.setString(17,origUlgObj.getReportPackRange());
			preparedStatement.setString(18,origUlgObj.getReportSizeRange());
			preparedStatement.setString(19,origUlgObj.getReportBspRange());
			preparedStatement.setString(20,origUlgObj.getReportUnitRange());
			preparedStatement.setString(21,origUlgObj.getReportCitySrpRange());
			preparedStatement.setString(22,origUlgObj.getReportUpc());
			preparedStatement.setString(23,origUlgObj.getReportObiRange());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;		
	}
	public static int insertUlgAuditMoveItem(PreparedStatement preparedStatement, int auditSeq, ReportObj reportObj, String auditChangeDesc, ReportObj origUlgObj ) throws SQLException {
		int returnValue = 0;

		try {
			preparedStatement.setInt(1,auditSeq);
			preparedStatement.setString(2,auditChangeDesc);

			preparedStatement.setString(3,UpdateUlg.formatItemCode(reportObj.getReportItemCode()));
			preparedStatement.setString(4,reportObj.getReportLinkGroupNbr());
			preparedStatement.setString(5,reportObj.getReportLinkGroupDesc());
			preparedStatement.setString(6,reportObj.getReportItemDesc());
			preparedStatement.setString(7,reportObj.getReportPackRange());
			preparedStatement.setString(8,reportObj.getReportSizeRange());
			preparedStatement.setString(9,reportObj.getReportBspRange());
			preparedStatement.setString(10,reportObj.getReportUnitRange());
			preparedStatement.setString(11,reportObj.getReportCitySrpRange());
			preparedStatement.setString(12,reportObj.getReportUpc());
			preparedStatement.setString(13,reportObj.getReportObiRange());
			preparedStatement.setString(14,reportObj.getReportCmdty());
			preparedStatement.setString(15,reportObj.getReportSubCmdty());

			preparedStatement.setString(16,origUlgObj.getReportLinkGroupNbr());
			preparedStatement.setString(17,origUlgObj.getReportLinkGroupDesc());

			preparedStatement.setString(18,origUlgObj.getReportItemDesc());
			preparedStatement.setString(19,origUlgObj.getReportPackRange());
			preparedStatement.setString(20,origUlgObj.getReportSizeRange());
			preparedStatement.setString(21,origUlgObj.getReportBspRange());
			preparedStatement.setString(22,origUlgObj.getReportUnitRange());
			preparedStatement.setString(23,origUlgObj.getReportCitySrpRange());
			preparedStatement.setString(24,origUlgObj.getReportUpc());
			preparedStatement.setString(25,origUlgObj.getReportObiRange());
			preparedStatement.setString(26,origUlgObj.getReportCmdty());
			preparedStatement.setString(27,origUlgObj.getReportSubCmdty());
			
			
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("UpdateUlg.insertUlgAuditMoveItem itemCode="+reportObj.getReportItemCode()+"  auditSeq="+auditSeq);
			e.printStackTrace();
		}
		return returnValue;		
	}
	public static int insertUlgAuditDelete(PreparedStatement preparedStatement, int auditSeq, ReportObj reportObj, String auditChangeDesc) throws SQLException {
		int returnValue = 0;

		try {
			preparedStatement.setInt(1,auditSeq);
			preparedStatement.setString(2,auditChangeDesc);
			preparedStatement.setString(3,UpdateUlg.formatItemCode(reportObj.getReportItemCode()));
			preparedStatement.setString(4,reportObj.getReportLinkGroupNbr());
			preparedStatement.setString(5,reportObj.getReportLinkGroupDesc());
			preparedStatement.setString(6,reportObj.getReportItemDesc());
			preparedStatement.setString(7,reportObj.getReportPackRange());
			preparedStatement.setString(8,reportObj.getReportSizeRange());
			preparedStatement.setString(9,reportObj.getReportBspRange());
			preparedStatement.setString(10,reportObj.getReportUnitRange());
			preparedStatement.setString(11,reportObj.getReportCitySrpRange());
			preparedStatement.setString(12,reportObj.getReportUpc());
			preparedStatement.setString(13,reportObj.getReportObiRange());
			preparedStatement.setString(14,reportObj.getReportCmdty());
			preparedStatement.setString(15,reportObj.getReportSubCmdty());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;		
	}	
	public static int updateUlgItemRecord(PreparedStatement preparedStatement, int linkGrpNbr, String itemCode)  {
		
		
		int returnValue = 0;

		try {
			
			preparedStatement.setInt(1,linkGrpNbr);
			preparedStatement.setString(2,formatItemCode(itemCode));
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;		
	}		

	public static List<ReportObj> emptyLinkGroups(PreparedStatement preparedStatement)  {
		List<ReportObj> list = new ArrayList<ReportObj>();
		ReportObj obj;
		ResultSet resultSet = null;
		try {
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())	{
				obj = new ReportObj();
				obj.setReportLinkGroupNbr(resultSet.getString(1));
				obj.setReportLinkGroupDesc(resultSet.getString(2));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;		
	}
	public static int deleteEmptyLinkGroups(PreparedStatement preparedStatement, String linkGrpNbr)  {
		int returnValue = 0;
		try {
			preparedStatement.setString(1,linkGrpNbr);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;		
	}	
	public static int updateUlgXrefRecord(PreparedStatement preparedStatement, int linkGrpNbr, String linkGrpDesc)  {
		int returnValue = 0;
		try {
				preparedStatement.setString(1,linkGrpDesc);
				preparedStatement.setInt(2,linkGrpNbr);
				preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;		
	}		
	public static int updateUlgXrefRecordByItem(PreparedStatement preparedStatement, String itemCode, String linkGrpDesc)  {
		int returnValue = 0;
		try {
				preparedStatement.setString(1,linkGrpDesc);
				preparedStatement.setString(2,formatItemCode(itemCode));
				preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;		
	}		
	public static int deleteUlgItemRecord(PreparedStatement preparedStatement, String itemCode) throws SQLException {
		int returnValue = 0;
		try {
			preparedStatement.setString(1,formatItemCode(itemCode));
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;		
	}	
	public static String formatItemCode(String oldValue) {
		StringBuilder newVal = new StringBuilder();
		int diff = 7 - oldValue.length();
		for(int i=0;i<diff;i++) {
			newVal.append("0");
		}
		return newVal.toString()+oldValue;
	}
}
