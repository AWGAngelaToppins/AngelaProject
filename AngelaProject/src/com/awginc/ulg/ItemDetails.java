package com.awginc.ulg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ItemDetails {

	public static List<CategoryVendorObj> selectItemCodes(PreparedStatement preparedStatement, CategoryVendorObj vendor, HashMap<String,String> itemsWithMultipleVendors) throws SQLException {

		ResultSet resultSet = null;
		List<CategoryVendorObj> list = new ArrayList<CategoryVendorObj>();
			
    	try {
			preparedStatement.setInt(1,vendor.getCatNbr());
			preparedStatement.setString(2,vendor.getPrivateLblCd());
			preparedStatement.setString(3,vendor.getVendorCode());
			preparedStatement.setString(4,vendor.getSeasonCode());
			
			resultSet = preparedStatement.executeQuery();
			
			CategoryVendorObj obj = new CategoryVendorObj();
			while(resultSet.next())	{
				obj = new CategoryVendorObj();
				obj.setItemCode(resultSet.getString(1));
				obj.setDataKey(vendor.getDataKey());
				obj.setSubCmdtyDesc(resultSet.getString(6));
				obj.setUpcDept(resultSet.getString(7));
				obj.setDept(resultSet.getString(8));

				//only add the item code if it resides in the same vendor group that you are retrieving item codes for
				//make sure you add the item to the correct selected most common vendor group 
				//scenario: some items may have more than one vendor tied to them 
				if(itemsWithMultipleVendors.containsKey(obj.getItemCode()) ) {
					if(itemsWithMultipleVendors.get(obj.getItemCode()).contains(obj.getDataKey())){
						obj.setSeasonCode(vendor.getSeasonCode());
						obj.setUnitBsp(resultSet.getDouble(2));
						obj.setCatNbr(vendor.getCatNbr());
						obj.setCatDesc(vendor.getCatDesc());
						obj.setPrivateLblCd(vendor.getPrivateLblCd());
						obj.setVendorCode(vendor.getVendorCode());
						obj.setVendorDesc(vendor.getVendorDesc());
						obj.setDataKey(vendor.getDataKey());
						
						list.add(obj);
					}else
					{
					//don't add to list to prevent item codes from being added again due to item existing in multiple vendors
					}
				}else
				{
					obj.setSeasonCode(vendor.getSeasonCode());
					obj.setUnitBsp(resultSet.getDouble(2));
					obj.setCatNbr(vendor.getCatNbr());
					obj.setCatDesc(vendor.getCatDesc());
					obj.setPrivateLblCd(vendor.getPrivateLblCd());
					obj.setVendorCode(vendor.getVendorCode());
					obj.setVendorDesc(vendor.getVendorDesc());
					
					list.add(obj);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
 			if (resultSet != null) {
 				resultSet.close();
 			}
 		}
		return list;
	}
	public static List<CategoryVendorObj> selectDiscontinuedItems(PreparedStatement preparedStatement) throws SQLException {

		ResultSet resultSet = null;
		List<CategoryVendorObj> list = new ArrayList<CategoryVendorObj>();
			
    	try {
			resultSet = preparedStatement.executeQuery();
			
			CategoryVendorObj obj = new CategoryVendorObj();
			while(resultSet.next())	{
				obj = new CategoryVendorObj();
				obj.setItemCode(resultSet.getString(1));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
 			if (resultSet != null) {
 				resultSet.close();
 			}
 		}
		return list;
	}	
	public static String getItemDesc(PreparedStatement preparedStatement, CategoryVendorObj vendor) throws SQLException{

		ResultSet resultSet = null;
		String returnValue = "none";
		
    	try {
			preparedStatement.setInt(1,vendor.getCatNbr());
			preparedStatement.setString(2,vendor.getPrivateLblCd());
			preparedStatement.setString(3,vendor.getVendorCode());
			preparedStatement.setString(4,vendor.getSeasonCode());
			preparedStatement.setString(5,vendor.getItemCode());

			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {	
				returnValue = resultSet.getString(1).trim(); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
 				resultSet.close();
 			}
		}
    	return returnValue;
	}
	public static String getObiDesc(PreparedStatement preparedStatement, CategoryVendorObj vendor) throws SQLException{

		ResultSet resultSet = null;
		String returnValue = "none";
		
    	try {
			preparedStatement.setInt(1,vendor.getCatNbr());
			preparedStatement.setString(2,vendor.getPrivateLblCd());
			preparedStatement.setString(3,vendor.getVendorCode());
			preparedStatement.setString(4,vendor.getSeasonCode());
			preparedStatement.setString(5,vendor.getItemCode());

			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {	
				returnValue = resultSet.getString(1); 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
 				resultSet.close();
 			}
		}
    	return returnValue;
	}
	public static String getPackDesc(PreparedStatement preparedStatement, CategoryVendorObj vendor) throws SQLException{

		ResultSet resultSet = null;
		String returnValue = "none";
		
		String minPack="";
		String maxPack="";
		
    	try {
			preparedStatement.setInt(1,vendor.getCatNbr());
			preparedStatement.setString(2,vendor.getPrivateLblCd());
			preparedStatement.setString(3,vendor.getVendorCode());
			preparedStatement.setString(4,vendor.getItemCode());

			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {	
				minPack = resultSet.getString(2).trim(); 
				maxPack = resultSet.getString(3).trim(); 
			}
			
			if(minPack.contains(maxPack))
				returnValue=minPack;
			else
				returnValue=minPack+"-"+maxPack;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
 				resultSet.close();
 			}
		}
    	return returnValue;
	}
	public static String getUpcDesc(PreparedStatement preparedStatement, CategoryVendorObj vendor) throws SQLException{

		ResultSet resultSet = null;
		String returnValue = "none";
		
    	try {
			preparedStatement.setInt(1,vendor.getCatNbr());
			preparedStatement.setString(2,vendor.getPrivateLblCd());
			preparedStatement.setString(3,vendor.getVendorCode());
			preparedStatement.setString(4,vendor.getItemCode());

			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {	
				returnValue = resultSet.getString(1).trim(); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
 				resultSet.close();
 			}
		}
    	return returnValue;
	}	
	public static ReportObj getAddtlItemInfo(PreparedStatement preparedStatement, String itemCode) throws SQLException{

		ResultSet resultSet = null;
		
		Double minCitySRP=0.0;
		Double maxCitySRP=0.0;
		String citySRP="";
		
		Double minUnit=0.0;
		Double maxUnit=0.0;
		String unit="";
		
		ReportObj obj = new ReportObj();
    	try {
			preparedStatement.setString(1,UpdateUlg.formatItemCode(itemCode));
			preparedStatement.setString(2,UpdateUlg.formatItemCode(itemCode));
			preparedStatement.setString(3,UpdateUlg.formatItemCode(itemCode));
			preparedStatement.setString(4,UpdateUlg.formatItemCode(itemCode));

			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {	
				minCitySRP = resultSet.getDouble(1); 
				maxCitySRP = resultSet.getDouble(2); 

				minUnit = resultSet.getDouble(3); 
				maxUnit = resultSet.getDouble(4); 

				obj.setFaciliyToCorporateLevelKeyUlg(resultSet.getString(5));
				obj.setReportLinkGroupNbr(String.valueOf(resultSet.getInt(6)));
				
				obj.setReportUserUpdateId(resultSet.getString(7));
			}
			
			//citySRP
			if(Double.compare(minCitySRP, maxCitySRP)==0)
				citySRP="$"+String.valueOf(minCitySRP);
			else
				citySRP="$"+String.valueOf(minCitySRP)+" - "+String.valueOf(maxCitySRP);
			obj.setReportCitySrpRange(citySRP);

			//unit
			if(Double.compare(minUnit, maxUnit)==0)
				unit=String.valueOf(minUnit);
			else
				unit=String.valueOf(minUnit)+"-"+String.valueOf(+maxUnit);
			obj.setReportUnitRange(unit);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
 				resultSet.close();
 			}
		}
    	return obj;
	}	
	public static ReportObj getFacilityToCorporateLevelData(PreparedStatement preparedStatement, String itemCode) throws SQLException{

		ResultSet resultSet = null;
		
		ReportObj obj = new ReportObj();
    	try {
			preparedStatement.setString(1,UpdateUlg.formatItemCode(itemCode));

			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {	
				resultSet.getString(1); 
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
 				resultSet.close();
 			}
		}
    	return obj;
	}
	public static String getXrefLinkGroupDesc(PreparedStatement preparedStatement, int ulgGrpNb) throws SQLException{

		ResultSet resultSet = null;
		
		String returnValue="";
    	try {
			preparedStatement.setInt(1,ulgGrpNb);

			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {	
				returnValue = resultSet.getString(1); 
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
 				resultSet.close();
 			}
		}
    	return returnValue;
	}	
	public static int deleteDiscontinuedItem(PreparedStatement preparedStatement, String itemCode)  {
		int returnValue = 9;
		try {
			preparedStatement.setString(1,UpdateUlg.formatItemCode(itemCode));
			returnValue = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;		
	}
	public static ReportObj getItemLinkGrpNbr(PreparedStatement preparedStatement, String itemCd) throws SQLException{

		ReportObj obj = new ReportObj();
		ResultSet resultSet = null;
		
    	try {
			preparedStatement.setString(1,UpdateUlg.formatItemCode(itemCd));

			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {	
				obj.setReportLinkGroupNbr(resultSet.getString(1)); 
				obj.setReportLinkGroupDesc(resultSet.getString(2));
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
 				resultSet.close();
 			}
		}
    	return obj;
	}
}
