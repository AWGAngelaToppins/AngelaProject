package com.awginc.ulg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LinkGroupDesc {
	
	public static String getSeasonDesc(String seasoncode) {
		if("E".equals(seasoncode))
			return "EASTER ";
		else
		if("X".equals(seasoncode))
			return "CHRISTMAS ";
		else
		if("V".equals(seasoncode))
			return "VALENTINE ";
		else
		if("S".equals(seasoncode))
			return "SUMMER ";
		else
		if("H".equals(seasoncode))
			return "HALLOWEEN ";
		else
			return "";
	}
	public static String getMinMaxSize(PreparedStatement preparedStatement, CategoryVendorObj vendor, List<CategoryVendorObj> itemList) throws SQLException {
		
		ResultSet resultSet = null;
		String returnValue = "none";

		try {
			preparedStatement.setInt(1,vendor.getCatNbr());
			preparedStatement.setString(2,vendor.getPrivateLblCd());
			preparedStatement.setString(3,vendor.getVendorCode());
			preparedStatement.setString(4,vendor.getUpcDept());				
			preparedStatement.setString(5,vendor.getSeasonCode());
		
			//set the host values for bsp units for the where in clause
			int counter = 5;
			for(CategoryVendorObj item:itemList) {
				counter++;
				preparedStatement.setDouble(counter,item.getUnitBsp());
			}
			
			resultSet = preparedStatement.executeQuery();
			List<String> list = new ArrayList<String>();
			while(resultSet.next())	
				list.add(resultSet.getString(1).trim());
			
			
			if(list.size()>1) 
				returnValue = (String)list.get(0)+"-"+(String)list.get(1);
			else 
				returnValue = (String)list.get(0);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
 				resultSet.close();
 			}
		}
		return returnValue;
	}	
	public static String getSubCatDesc(PreparedStatement preparedStatement, CategoryVendorObj vendor, List<CategoryVendorObj> itemList) throws SQLException{
		
		ResultSet resultSet = null;
		String returnValue = "none";
		
		try {
			preparedStatement.setInt(1,vendor.getCatNbr());
			preparedStatement.setString(2,vendor.getPrivateLblCd());
			preparedStatement.setString(3,vendor.getVendorCode());
			preparedStatement.setString(4,vendor.getUpcDept());			
			preparedStatement.setString(5,vendor.getSeasonCode());

//			set the host values for bsp units for the where in clause
			int counter = 5;
			for(CategoryVendorObj item:itemList) {
				counter++;
				preparedStatement.setDouble(counter,item.getUnitBsp());
			}
	
			resultSet = preparedStatement.executeQuery();
			List<String> list = new ArrayList<String>();
			while(resultSet.next())	
				list.add(resultSet.getString(1).trim());
			
			if(list.size()>1) 
				returnValue = (String)list.get(0)+"* "; //business asked to append asterisk to indicate multiple
			else 
				returnValue = (String)list.get(0);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
 				resultSet.close();
 			}
		}
		return returnValue;
	}
	public static String getBrandDesc(PreparedStatement preparedStatement, CategoryVendorObj vendor, List<CategoryVendorObj> itemList) throws SQLException{

		ResultSet resultSet = null;
		String returnValue = "none";
		
    	try {
			preparedStatement.setInt(1,vendor.getCatNbr());
			preparedStatement.setString(2,vendor.getPrivateLblCd());
			preparedStatement.setString(3,vendor.getVendorCode());
			preparedStatement.setString(4,vendor.getUpcDept());
			preparedStatement.setString(5,vendor.getSeasonCode());

			//set the host values for bsp units for the where in clause
			int counter = 5;
			for(CategoryVendorObj item:itemList) {
				counter++;
				preparedStatement.setDouble(counter,item.getUnitBsp());
			}

			resultSet = preparedStatement.executeQuery();
			List<String> list = new ArrayList<String>();
			while(resultSet.next())	
				list.add(resultSet.getString(1).trim());
			
			
			if(list.size()>1) 
				returnValue = (String)list.get(0)+"* "; //business asked to append asterisk to indicate multiple
			else 
				returnValue = (String)list.get(0);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
 				resultSet.close();
 			}
		}
    	return returnValue;
	}
	public static String buildBspUnitParms(List<CategoryVendorObj> itemList) {
		StringBuilder parms = new StringBuilder();
		
		for(int i=0;i<itemList.size();i++) 
			parms.append("?,");
		
		String newValue = parms.substring(0,parms.length()-1);//remove last comma
		return "and use_this_bsp in ("+newValue+") ";
	}
	public static String buildBspUnitParmsMinMaxSize(List<CategoryVendorObj> itemList) {
		StringBuilder parms = new StringBuilder();
		
		for(int i=0;i<itemList.size();i++) 
			parms.append("?,");
		
		String newValue = parms.substring(0,parms.length()-1);//remove last comma
		return "where use_this_bsp in ("+newValue+") ";
	}	
}
