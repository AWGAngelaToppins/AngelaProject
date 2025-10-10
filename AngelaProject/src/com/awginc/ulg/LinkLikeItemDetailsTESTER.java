package com.awginc.ulg;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LinkLikeItemDetailsTESTER    {
	
	
	private static String outgoingDirectory="C:\\Users\\atoppins\\Downloads\\ULGtesting\\";

	static int tempLinkGrpNbr = 0;
	static List<String> linkLikeItemDetailsOutput = new ArrayList<String>(); //ULG3_Details.txt
	
	static String nextLinkGroupHeadings = "--NextLinkGroup------|ExistingLinkGroupNbr |itemcode-----|catnbr---|lble---|vendorcode---|season--|linkgroupdesc(brand-season-subcat-size)--------------------------------------------------------------|size---------------------------|bspRange--------|bsp -----|brand--------------|subCatDesc-----------------------------------------|packdesc-----------------------|unitRange----------------------|citySRP------------------------|upc----------------------------|catNbr--|subCatNbr--|obi------------------------------------------------|itemDesc-----------------------|update?----|Datawarehouse(itemDesc-packageRange-sizeRange-bspRange-unitRange-citySRP-Upc-obiRange)-----------------------------------|ULG(itemDesc-packageRange-sizeRange-bspRange-unitRange-citySRP-Upc-obiRange)---------------------------------------------|ULG (updateUserid)   |ULG_XREF desc                                                                                        |  ";

	public static String process() throws Exception {
		
		File[] incomingFiles = Utility.getFiles(outgoingDirectory);
   		int nbrOfFiles = incomingFiles.length;
   		
   		List<CategoryVendorObj> allVendorList = new ArrayList<CategoryVendorObj>();
		HashMap<String,String> itemsWithMultipleVendors = new HashMap<String,String>();

		File inputFile;
   		for (int i = 0; i < nbrOfFiles; i++) {
   			inputFile = new File(outgoingDirectory+incomingFiles[i].getName());
   			if(incomingFiles[i].getName().contains(UlgConstants.ulg2)) {
   				allVendorList = CategoryVendorData.getVendorList(inputFile);	
   			}else
   			if(incomingFiles[i].getName().contains(UlgConstants.ulg1)) {
   				itemsWithMultipleVendors = CategoryVendorData.buildItemsWithCommonVendorMap(inputFile);	
   			}
   		}
		
		mainProcess(allVendorList, itemsWithMultipleVendors);

		return "";
	}

	private static void mainProcess(List<CategoryVendorObj> allVendorList, HashMap<String,String> itemsWithMultipleVendors) throws SQLException {
		
		List<CategoryVendorObj> vendorItemList = new ArrayList<CategoryVendorObj>();
		
		HashMap<Double,Double> bspUnitBreak = new HashMap<Double,Double>();
		Double previousUnitBsp = 0.00;
		Double pennyDiff = 0.00;

		HashMap<String,String> upcBreak = new HashMap<String,String>();

		List<CategoryVendorObj> currentLinkGroupItems = new ArrayList<CategoryVendorObj>();
		
		for(CategoryVendorObj vendor:allVendorList) {
			
			upcBreak = new HashMap<String,String>(); //clear map
				
			//get item details and bsp amounts at the vendor/cat/season/label level 
			currentLinkGroupItems = new ArrayList<CategoryVendorObj>();

			vendorItemList = ItemDetails.selectItemCodes(testPreparedStatement(SqlConstants.bspAndItem),vendor, itemsWithMultipleVendors);
			
			for(CategoryVendorObj vendorItem:vendorItemList) {
				
				if(upcBreak.containsKey(vendorItem.getUpcDept())) {

							//see if the previous item's bsp amount is 1 or 2 cent different with the current item being processed
							pennyDiff = (double) Math.round((vendorItem.getUnitBsp() - previousUnitBsp)*100); 
							pennyDiff = pennyDiff/100;
									
							//business request to not allow category 185 to get the 2 cent rule, overriding the 2 cent rule 
							if(vendorItem.getCatNbr()==185)
								pennyDiff=0.99; //default to out of range number so that this specific category doesn't get linked together if it's the actual difference is 2 cents
								
							if(bspUnitBreak.containsKey(vendorItem.getUnitBsp())) {
								currentLinkGroupItems.add(vendorItem);
							}else
							if(pennyDiff==0.01 || pennyDiff==0.02){
								currentLinkGroupItems.add(vendorItem);
								bspUnitBreak.put(vendorItem.getUnitBsp(), vendorItem.getUnitBsp());
							}
							else 
							{
								//new unit BSP amount which means a new link group
								if(currentLinkGroupItems.size()>0) {
									buildLinkGroup(currentLinkGroupItems);  
								}
									
								currentLinkGroupItems = new ArrayList<CategoryVendorObj>(); //reset list
									
								//Build new link group info at the unit BSP amount level 
								bspUnitBreak.put(vendorItem.getUnitBsp(), vendorItem.getUnitBsp());
								previousUnitBsp = vendorItem.getUnitBsp();
									
								currentLinkGroupItems.add(vendorItem);
							}
				}else
				{
					bspUnitBreak = new HashMap<Double,Double>(); //clear map
					upcBreak = new HashMap<String,String>(); //clear map
					upcBreak.put(vendorItem.getUpcDept(), vendorItem.getUpcDept());
					
					//new upc which means a new link group 
					if(currentLinkGroupItems.size()>0) {
						buildLinkGroup(currentLinkGroupItems);  
					}
						
					currentLinkGroupItems = new ArrayList<CategoryVendorObj>(); //reset list
						
					//Build new link group info at the unit BSP amount level 
					bspUnitBreak.put(vendorItem.getUnitBsp(), vendorItem.getUnitBsp());
					previousUnitBsp = vendorItem.getUnitBsp();
						
					currentLinkGroupItems.add(vendorItem);
				}
			}
			//no more items for the vendor, get the last link group
			if(currentLinkGroupItems.size()>0) {
				buildLinkGroup(currentLinkGroupItems); 
			}
		}
		ReportOutputs.createOutputFileLinkGroupBreak(linkLikeItemDetailsOutput, outgoingDirectory);  //ULG4_Details.txt
	}

	private static String formatBspRange(List<CategoryVendorObj> itemList) {
		
		int lastIndex = itemList.size()-1;
		CategoryVendorObj firstItem = (CategoryVendorObj) itemList.get(0); //1st item
		CategoryVendorObj lastItem = (CategoryVendorObj) itemList.get(lastIndex); //last item

        if(lastItem.getUnitBsp()-firstItem.getUnitBsp()==0.00)
    	   return "$"+firstItem.getUnitBsp();
        else
    	   return "$"+firstItem.getUnitBsp()+" - "+lastItem.getUnitBsp();
	}

	private static void buildLinkGroup(List<CategoryVendorObj> itemList) throws SQLException {

		tempLinkGrpNbr++;
			
		CategoryVendorObj obj = (CategoryVendorObj) itemList.get(0); //1st item

		linkLikeItemDetailsOutput.add(nextLinkGroupHeadings);
		String subCat;
		String brand;
		String minMaxSize;
		
		//keep - will use for future enhancement
		String deptString = " - depts";
		String itemCountString;

		String query = SqlConstants.minMaxSize+LinkGroupDesc.buildBspUnitParmsMinMaxSize(itemList)+SqlConstants.minMaxSizeAddtl;
		minMaxSize = LinkGroupDesc.getMinMaxSize(testPreparedStatement(query), obj,  itemList );

		query = SqlConstants.singleSubcat+LinkGroupDesc.buildBspUnitParms(itemList)+SqlConstants.singleSubcatAddtl;
		subCat     = LinkGroupDesc.getSubCatDesc(testPreparedStatement(query), obj, itemList);

		query = SqlConstants.singleBrand+LinkGroupDesc.buildBspUnitParms(itemList)+SqlConstants.singleBrandAddtl;
		brand      = LinkGroupDesc.getBrandDesc(testPreparedStatement(query), obj, itemList);
		
		String linkGroupDescString = brand+" - "+LinkGroupDesc.getSeasonDesc(obj.getSeasonCode())+subCat+" - "+minMaxSize.toString(); //business asked for the unitBSP to not be part of link group desc 

		//keep - will use for future enhancement
		//how many dept cross over in the link group
		HashMap depts = new HashMap();
		int itemCounter = 0;
      	for(CategoryVendorObj item:itemList) {
      		itemCounter++;
      		depts.put(item.getDept(),item.getDept() ); 
		}

		//keep - will use for future enhancement
      	if(itemCounter>1)
      		itemCountString = "("+itemCounter+" items";
      	else
      		itemCountString = "("+itemCounter+" item";

		//keep - will use for future enhancement
      	if(depts.size()>1) 
      		deptString = "-depts*) ";//counter is number of items within link group and asterick means multiple depts within same link group
      	else
      		deptString = "-dept) "; //counter is number of items within link group 
      	
      	
        String itemDesc;
        String obiDesc;
        String packDesc;
        String upcDesc;
        
        String placeholder = "placeholder|placeholder                                                                                                              |placeholder                                                                                                              |placeholder          |placeholder    |";
        StringBuilder itemRecord = new StringBuilder();
      	for(CategoryVendorObj item:itemList) {

      		//build link group description
			itemDesc = ItemDetails.getItemDesc(testPreparedStatement(SqlConstants.selectItemDesc), item);
			obiDesc  = ItemDetails.getObiDesc(testPreparedStatement(SqlConstants.selectOBI), item);
			packDesc = ItemDetails.getPackDesc(testPreparedStatement(SqlConstants.selectItemPack), item);
			upcDesc = ItemDetails.getUpcDesc(testPreparedStatement(SqlConstants.selectItemUPCs), item);
			
      		itemRecord = new StringBuilder();

      		itemRecord.append(Utility.newValue2("  Group "+String.valueOf(tempLinkGrpNbr),20)+"|"); 
			itemRecord.append(Utility.newValue(" ",20)+"|"); 
			itemRecord.append(Utility.newValue(item.getItemCode(),12)+"|"); 
			itemRecord.append(Utility.newValue(String.valueOf(obj.getCatNbr()),8)+"|");  
			itemRecord.append(Utility.newValue(obj.getPrivateLblCd(),6)+"|");  
			itemRecord.append(Utility.newValue(obj.getVendorCode(),12)+"|"); 
			itemRecord.append(Utility.newSeasonValue(LinkGroupDesc.getSeasonDesc(obj.getSeasonCode()),7)+"|"); 
			
			itemRecord.append(Utility.newValue(linkGroupDescString,90)+"|");  //atoppins 12-22-2024 use this for now, exclude item count and dept for future enchancement 
//			itemRecord.append(Utility.newValue(linkGroupDescString+itemCountString+deptString,100)+"|"); //atoppins 12-22-2024 for now, comment out the item count and dept - future enhancement 
			
			
			itemRecord.append(Utility.newValue(minMaxSize,30) +"|"); 
			itemRecord.append(Utility.newValue(formatBspRange(itemList),15)+"|");  //bsp range
			itemRecord.append(Utility.newValue(String.valueOf(item.getUnitBsp()),8)+"|");  //bsp for itemcode
			itemRecord.append(Utility.newValue(brand,18)+"|"); 
			itemRecord.append(Utility.newValue(subCat,50)+"|");  
			itemRecord.append(Utility.newValue(packDesc, 30) +"|"); 
			itemRecord.append(Utility.newValue("",30) +"|"); 
			itemRecord.append(Utility.newValue(" ",30) +"|"); 
			itemRecord.append(Utility.newValue(upcDesc,30) +"|"); 
			itemRecord.append(Utility.newValue(Utility.newValue(String.valueOf(item.getCatNbr()), 4, "0"),7) +"|"); 
			itemRecord.append(Utility.newValue(Utility.newValue(String.valueOf(item.getSubCmdtyDesc()), 4, "0"),10)+"|" ); 
			itemRecord.append(Utility.newValue(obiDesc,50) +"|"); 
			itemRecord.append(Utility.newValue(itemDesc, 30) +"|"); 
			itemRecord.append(Utility.newValue(placeholder+item.getDept(), 30) ); 

			linkLikeItemDetailsOutput.add(itemRecord.toString());
		}
	}
	
	public static PreparedStatement testPreparedStatement(String query) throws SQLException {
		//FOR TESTING ONLY
		Connection conn = getConn();
		PreparedStatement test = conn.prepareStatement(query);
		
		return test;
		
	}
	public static Connection getConn() throws SQLException {
		String url ="jdbc:db2://DWPROD01:60000/DWPROD";
		String id  = "databob";
		String psw = "databob";
		
	      Connection connection;
			try
			{
				Class.forName("com.ibm.db2.jcc.DB2Driver");  
				DriverManager.setLogWriter(null);
				connection = DriverManager.getConnection(url,id,psw);
			}
			catch (ClassNotFoundException e)
			{
				throw new SQLException("Unable to load DB2 driver: " + e.getMessage());
			}
			return connection;
				
	}
}
	
