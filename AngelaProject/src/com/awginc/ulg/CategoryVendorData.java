package com.awginc.ulg;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.awg.common.AwgConstants;

public class CategoryVendorData {

	public static List<CategoryVendorObj> getVendorList(File  inputFile) throws Exception {

		List<CategoryVendorObj> allVendors = new ArrayList<CategoryVendorObj>();
		List<String> temp = new ArrayList<String>();
		
		CategoryVendorObj obj;
		List<String> textList = Utility.retrieveTextFile(inputFile);
		for(String text:textList) {
			temp = Utility.split(AwgConstants.delimiter,text);
			obj = new CategoryVendorObj();
			obj.setCatNbr(Integer.parseInt(temp.get(0).trim()));
			obj.setCatDesc(temp.get(1).trim());
			obj.setPrivateLblCd(temp.get(2).trim());
			obj.setVendorCode(temp.get(3).trim());
			obj.setVendorDesc(temp.get(4).trim());
			obj.setSeasonCode(temp.get(5).trim());
			obj.setDataKey(temp.get(6).trim());
			allVendors.add(obj);
		}
		return allVendors;
	}
	public static HashMap<String,String> buildItemsWithCommonVendorMap(File  inputFile) throws Exception {
		
		List<String> temp = new ArrayList<String>();
		
		//This will be the key to prevent adding the same item code to multiple link groups.  Item will be added to get the most common vendor.
		HashMap<String,String> itemsWithMultipleVendors = new HashMap<String,String>();
		
		List<String> textList = Utility.retrieveTextFile(inputFile);
		for(String text:textList) {
			temp = Utility.split(AwgConstants.delimiter,text);
			itemsWithMultipleVendors.put(temp.get(0).trim(),temp.get(7));
		}
		return itemsWithMultipleVendors;
	}	
	public static CategoryVendorObj getVendor(PreparedStatement preparedStatement, String itemCode, String seasonCode) throws SQLException{

		ResultSet resultSet = null;
		CategoryVendorObj obj = new CategoryVendorObj();

		try {
		preparedStatement.setString(1,itemCode);
		preparedStatement.setString(2,seasonCode);
		preparedStatement.setMaxRows(1); //only need the first row
		resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next())	{
			obj = new CategoryVendorObj();
			obj.setItemCode(itemCode);
			obj.setCatNbr(resultSet.getInt(1));
			obj.setCatDesc(resultSet.getString(2).trim());
			obj.setPrivateLblCd(resultSet.getString(3).trim());
			obj.setVendorCode(resultSet.getString(4).trim());
			obj.setVendorDesc(resultSet.getString(5).trim());
			obj.setSeasonCode(resultSet.getString(6));
			obj.setDataKey(resultSet.getString(7));
			obj.setVendorCount(resultSet.getInt(8));
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
 				resultSet.close();
 			}
		}
		if(obj.getItemCode()==null)	{
			System.out.println("CategoryVendorData.java getVendor for itemCode = "+itemCode);
		}
		return obj;
	}	
	public static List<CategoryVendorObj> getItemsWithMultipleVendors(PreparedStatement stmt) throws SQLException{
		ResultSet resultSet = null;
		List<CategoryVendorObj> list = new ArrayList<CategoryVendorObj>();
		
		try {
			resultSet = stmt.executeQuery();
		
			CategoryVendorObj obj = new CategoryVendorObj();
			while(resultSet.next())	{
				obj = new CategoryVendorObj();
				obj.setItemCode(resultSet.getString(1).trim());
				obj.setSeasonCode(resultSet.getString(2).trim());
				obj.setVendorCount(resultSet.getInt(3));
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
	public static List<CategoryVendorObj> getAllVendors(PreparedStatement preparedStatement) throws SQLException{
		
		ResultSet resultSet = null;
		List<CategoryVendorObj> list = new ArrayList<CategoryVendorObj>();
		
		try {
		resultSet = preparedStatement.executeQuery();
		
		CategoryVendorObj obj = new CategoryVendorObj();
		while(resultSet.next())	{
			obj = new CategoryVendorObj();
			obj.setCatNbr(resultSet.getInt(2));
			obj.setCatDesc(resultSet.getString(3).trim());
			obj.setPrivateLblCd(resultSet.getString(4).trim());
			obj.setVendorCode(resultSet.getString(1).trim());
			obj.setVendorDesc(resultSet.getString(5).trim());
			obj.setSeasonCode(resultSet.getString(6).trim());
			obj.setDataKey(resultSet.getString(7).trim());
			
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
}
