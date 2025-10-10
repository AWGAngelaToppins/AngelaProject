package com.awginc.ulg;

public interface SqlConstants {
	
	//retrieve list of item codes that have multiple vendors, will choose only one to use per item code 
	public static final String getItemsWithMultiVendors = 
			"select ic, seasoncode, count(*) from (	" +		//count represents countOfUnqiueVendorPerItem
					"	select ic, seasoncode, VndrCd, vendornm  from (  " +		
					"		SELECT " +
					"			DBO_ARD_ITEM.ITEM_SCM_CD IC,      " +
					"			DBO_ARD_ITEM.SOR_CTGRY_NB AS Cat, " +
					"			DBO_ARD_ITEM.CTGRY_DS AS Category, " +
					"			DBO_ARD_ITEM.PRVT_LBL_CD AS FirstOfPRVT_LBL_CD,   " +
					"			DBO_ARD_VNDR_VIEW.AP_VNDR_CD AS VndrCd,  " +
					"			DBO_ARD_VNDR_VIEW.AP_VNDR_NM vendornm,   " +
					"			(CASE      " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='E '  THEN 'E'      " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='X '  THEN 'X'      " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='H '  THEN 'H'      " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='S '  THEN 'S'      " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='V '  THEN 'V' ELSE  'Z'     " +
					"			END) SEASONCODE  " +
					"			, DBO_ARD_ITEM.dc_cd  " +
					"		FROM	DBO.NON_ULG_ARD_ITEM DBO_ARD_ITEM " +
					"		INNER JOIN	DBO.ARD_VNDR_VIEW DBO_ARD_VNDR_VIEW " +
					"			ON DBO_ARD_ITEM.PRCHS_VNDR_KY = DBO_ARD_VNDR_VIEW.PRCHS_VNDR_KY " +
					"		WHERE	1=1  " +
					"       and  NOT (DBO_ARD_ITEM.rndm_wght_cd = 'C' and DBO_ARD_ITEM.rtl_dprtmnt_cd in ('040','041','042','043') ) "+ //exclude case items				
					"       and not (DBO_ARD_ITEM.rtl_dprtmnt_cd in ('011','051'))"+ //exlude these depts per business				
					"       and not (DBO_ARD_ITEM.ordr_book_indx_cd = '99999')"+     //exclude OBI				
					"       and not (DBO_ARD_ITEM.upc_prdct_cd='000000000000000') "+ 				
					"       and not (DBO_ARD_ITEM.sor_ctgry_nb in (640,770))"+       //VMC Commodity				
//					"       and DBO_ARD_ITEM.ITEM_STTS_RSN_CD <> 'D' "+              //exclude discontinued items - IMPORTANT I need to not exclude discontinued items when trying to see if an item has multiple vendors.  Otherwise, it will cause items to be incorrectly labeled as "discontinued" and deleted from ULG_ITEM table.				
					"       and DBO_ARD_ITEM.ITEM_UPC_NB<>0 "+                       //no upc				
					"       and DBO_ARD_ITEM.item_upc_nb > 1000000 "+                //exclude PLUs	
					"       AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '***DUMMY VENDOR***' " +  //exclude dummy vendor
					"       AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> 'DUMMY VENDOR' " +        //exclude dummy vendor
					"       AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '**DUMMY VENDOR**' " +    //exclude dummy vendor
					"       and DBO_ARD_ITEM.fclty_cd not in ('02','04','07') "+          //exclude inactive facilities
					"	) GROUP BY  ic, seasoncode, VndrCd, vendornm  " +
					"	  order by ic, vendornm	 " +
					")group by ic, seasoncode having count(*)>1  " ;
	
	//get the most common vendor at the item code level 
	public static final String singleVendor = 
			"Select cat, category, lblcd, vndrcd, vndrname, seasoncode, vndrcd||cat||lblcd||seasoncode as dataKey, count(*) totalitems from (  " +  //count represents a nbrOfDCsUsingThisVendor(AcrossDCs) per item   		
					"	SELECT  " +	
					"		DBO_ARD_ITEM.SOR_CTGRY_NB AS Cat, " +
					"		DBO_ARD_ITEM.CTGRY_DS AS Category, " +
					"		DBO_ARD_ITEM.PRVT_LBL_CD AS LBLCD, " + 
					"		DBO_ARD_VNDR_VIEW.AP_VNDR_CD AS VndrCd, " +
					"		DBO_ARD_VNDR_VIEW.AP_VNDR_NM AS VndrName, " + 
					"		(CASE  " +
					"		WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='E '    THEN 'E'  " +
					"		WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='X '    THEN 'X'  " +
					"		WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='H '    THEN 'H'  " +
					"		WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='S '    THEN 'S'  " +
					"		WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='V '    THEN 'V' ELSE  'Z' " +
					"		END) SEASONCODE " +
					"		, DBO_ARD_ITEM.dc_cd, DBO_ARD_ITEM.ITEM_SCM_CD itemcode  " +
					"	FROM	DBO.NON_ULG_ARD_ITEM DBO_ARD_ITEM " +
					"	INNER JOIN	DBO.ARD_VNDR_VIEW DBO_ARD_VNDR_VIEW  " +
					"		ON DBO_ARD_ITEM.PRCHS_VNDR_KY = DBO_ARD_VNDR_VIEW.PRCHS_VNDR_KY " +
					"	WHERE	1=1 " +
					"       and DBO_ARD_ITEM.ITEM_SCM_CD = ? "+
					"       and  NOT (DBO_ARD_ITEM.rndm_wght_cd = 'C' and DBO_ARD_ITEM.rtl_dprtmnt_cd in ('040','041','042','043') ) "+ //exclude case items				
					"       and not (DBO_ARD_ITEM.rtl_dprtmnt_cd in ('011','051'))"+ //exlude these depts per business				
					"       and not (DBO_ARD_ITEM.ordr_book_indx_cd = '99999')"+     //exclude OBI				
					"       and not (DBO_ARD_ITEM.upc_prdct_cd='000000000000000') "+ 				
					"       and not (DBO_ARD_ITEM.sor_ctgry_nb in (640,770))"+       //VMC Commodity				
//					"       and DBO_ARD_ITEM.ITEM_STTS_RSN_CD <> 'D' "+              //exclude discontinued items - IMPORTANT I need to not exclude discontinued items when trying to see if an item has multiple vendors.  Otherwise, it will cause items to be incorrectly labeled as "discontinued" and deleted from ULG_ITEM table.						
					"       and DBO_ARD_ITEM.ITEM_UPC_NB<>0 "+                       //no upc				
					"       and DBO_ARD_ITEM.item_upc_nb > 1000000 "+                //exlude PLUs	
					"       AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '***DUMMY VENDOR***' " +  //exclude dummy vendor
					"       AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> 'DUMMY VENDOR' " +        //exclude dummy vendor
					"       AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '**DUMMY VENDOR**' " +    //exclude dummy vendor
					"       and DBO_ARD_ITEM.fclty_cd not in ('02','04','07') "+          //exclude inactive facilities
					") "+		
					" where seasoncode = ?  " +
					"Group By 	cat, category, lblcd, vndrcd, vndrname, seasoncode, vndrcd||cat||lblcd||seasoncode  " +	
					"Order By	count(*) desc, vndrcd  " ;	
								
			

	
	//get all vendor categories
	public static final String allVendors = 
			"SELECT VNDRCD, CAT, CATEGORY, LBLCD, vndrnm, SEASONCODE, vndrcd||cat||lblcd||seasoncode as dataKey FROM (  " + 		
					"	SELECT 	DBO_ARD_ITEM.SOR_CTGRY_NB AS Cat, " +
					"		DBO_ARD_ITEM.CTGRY_DS AS Category, " +
					"		DBO_ARD_ITEM.PRVT_LBL_CD AS LBLCD,  " +
					"		DBO_ARD_VNDR_VIEW.AP_VNDR_CD AS VndrCd, " +
					"		DBO_ARD_VNDR_VIEW.AP_VNDR_NM as vndrnm,  " +
					"		(CASE  " +
					"		WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='E '  THEN 'E'  " +
					"		WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='X '  THEN 'X'  " +
					"		WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='H '  THEN 'H'  " +
					"		WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='S '  THEN 'S' " + 
					"		WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='V '  THEN 'V' ELSE  'Z' " +
					"		END) SEASONCODE " +
					"	FROM	DBO.NON_ULG_ARD_ITEM DBO_ARD_ITEM " +
					"	INNER JOIN	DBO.ARD_VNDR_VIEW DBO_ARD_VNDR_VIEW  " +
					"		ON DBO_ARD_ITEM.PRCHS_VNDR_KY = DBO_ARD_VNDR_VIEW.PRCHS_VNDR_KY " +
					"	WHERE	1=1 " + 
					"       and  NOT (DBO_ARD_ITEM.rndm_wght_cd = 'C' and DBO_ARD_ITEM.rtl_dprtmnt_cd in ('040','041','042','043') ) "+ //exclude case items				
					"       and not (DBO_ARD_ITEM.rtl_dprtmnt_cd in ('011','051'))"+ //exlude these depts per business				
					"       and not (DBO_ARD_ITEM.ordr_book_indx_cd = '99999')"+     //exclude OBI				
					"       and not (DBO_ARD_ITEM.upc_prdct_cd='000000000000000') "+ 				
					"       and not (DBO_ARD_ITEM.sor_ctgry_nb in (640,770))"+      //VMC Commodity				
					"       and DBO_ARD_ITEM.ITEM_STTS_RSN_CD <> 'D' "+             //exclude discontinued items				
					"       and DBO_ARD_ITEM.ITEM_UPC_NB<>0 "+                      //no upc				
					"       and DBO_ARD_ITEM.item_upc_nb > 1000000 "+               //exclude PLUs	
					"       AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '***DUMMY VENDOR***' " +  //exclude dummy vendor
					"       AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> 'DUMMY VENDOR' " +        //exclude dummy vendor
					"       AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '**DUMMY VENDOR**' " +    //exclude dummy vendor
					"       and DBO_ARD_ITEM.fclty_cd not in ('02','04','07') "+          //exclude inactive facilities
					")	 " +	
					"GROUP BY	VNDRCD, CAT, CATEGORY, LBLCD,  vndrnm, SEASONCODE, vndrcd||cat||lblcd||seasoncode   " +	
					"order by   VNDRCD, CAT, CATEGORY, LBLCD,  vndrnm, SEASONCODE " ;


	public static final String discontinuedItem = 
			"SELECT IC FROM ( " +
					"       SELECT IC, LISTAGG(flag,', ') as test from (" +
					"              SELECT IC, FLAG FROM ( " +
					"                     SELECT ITEM_SCM_CD IC,  (CASE WHEN DBO_ARD_ITEM.ITEM_STTS_RSN_CD = 'D'     THEN 'discontinued' ELSE  'DONT_ITEM_CODE' END) AS flag   " + 
					"                     FROM DBO.NON_ULG_ARD_ITEM DBO_ARD_ITEM        " +
					"                     INNER JOIN DBO.ARD_VNDR_VIEW DBO_ARD_VNDR_VIEW ON DBO_ARD_ITEM.PRCHS_VNDR_KY = DBO_ARD_VNDR_VIEW.PRCHS_VNDR_KY  " +    
					"                     WHERE 1=1 	" +     
					"                     and  NOT (DBO_ARD_ITEM.rndm_wght_cd = 'C' and DBO_ARD_ITEM.rtl_dprtmnt_cd in ('040','041','042','043') ) "+ //exclude case items				
					"                     and not (DBO_ARD_ITEM.rtl_dprtmnt_cd in ('011','051'))"+ //exlude these depts per business				
					"                     and not (DBO_ARD_ITEM.ordr_book_indx_cd = '99999')"+   //exclude OBI				
					"                     and not (DBO_ARD_ITEM.upc_prdct_cd='000000000000000') "+ 			
					"                     and not (DBO_ARD_ITEM.sor_ctgry_nb in (640,770))"+     //VMC Commodity				
					"                     and DBO_ARD_ITEM.ITEM_UPC_NB<>0  " +                   //no upc				
					"                     and DBO_ARD_ITEM.item_upc_nb > 1000000   " +           //exclude PLUs	
					"                     and DBO_ARD_ITEM.fclty_cd not in ('02','04','07') "+          //exclude inactive facilities
					"              ) group by IC, FLAG  " +
					"       ) group by ic " +
					")WHERE test = 'discontinued'  " ;
	
			
	//get list of bsp and item code at cat, private label and vendor level
	public static final String bspAndItem = 
			"SELECT ic, max(unitbsp), count(*) total_items_with_diff_bsp, item_shrt_ds, sor_ctgry_nb,sor_sub_ctgry_nb, substr(upc_prdct_cd,1,10), deptcode   " +
		    "FROM (  " +				
					"	SELECT 	ic, cat, category, lblcd, vndrcd, vndrnm, use_this_bsp as UNITBSP, count(*) TOTAL_dc_cd, item_shrt_ds,str_pack_qt as packqty,item_size_ds,upc_prdct_cd, sor_ctgry_nb,sor_sub_ctgry_nb, deptcode	 "+
					"   FROM ( " +		
					"		SELECT ic, cat, category, lblcd, vndrcd,  vndrnm, unitbsp2, " +

					//             new code to handle future BSP
					"		       (case	" +
					"		           when bsp_diff <0 and future_bsp_dt <= decrease_bsp_dt and futurebsp>0  then futureBSP 	" +
					"		           when bsp_diff >0 and future_bsp_dt <= increase_bsp_dt and futurebsp>0  then futureBSP	" +
					"		        else  unitbsp2 	" +
					"		        END) as use_this_bsp,	" +
					
					"              seasoncode, item_shrt_ds,str_pack_qt,item_size_ds,upc_prdct_cd,ordr_book_indx_cd,sor_ctgry_nb,sor_sub_ctgry_nb, deptcode "+
					"       FROM (  " +		
					"			SELECT 	DBO_ARD_ITEM.ITEM_SCM_CD IC, " +
					"				DBO_ARD_ITEM.SOR_CTGRY_NB AS Cat, " +
					"				DBO_ARD_ITEM.CTGRY_DS AS Category, " +
					"				DBO_ARD_ITEM.PRVT_LBL_CD AS lblcd, " + 
					"				DBO_ARD_VNDR_VIEW.AP_VNDR_CD AS VndrCd, " +
					"				DBO_ARD_VNDR_VIEW.AP_VNDR_NM vndrnm,  " +
					"				CAST(ROUND(DBO_ARD_ITEM.CRNT_BSP_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) as unitbsp2 ,  " +
					
					//              new code to handle future BSP
					"				CAST(ROUND(PNDNG_BSP_1_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) as futureBSP,  " +
					"				CAST(ROUND(PNDNG_BSP_1_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) - CAST(ROUND(DBO_ARD_ITEM.CRNT_BSP_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) AS BSP_DIFF,  " +
					"				current date+26 DAYS AS increase_bsp_dt,   " +
					"				current date+4  DAYS AS decrease_bsp_dt,   " +
					"				(case when DBO_ARD_ITEM.PNDNG_BSP_EFCTV_1_DT > current date then DBO_ARD_ITEM.PNDNG_BSP_EFCTV_1_DT else '9999-12-31' end ) as future_bsp_dt,  " +
					"				DBO_ARD_ITEM.PNDNG_BSP_1_AM as future_bsp_total,     " +

					"				(CASE  " +
					"				WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='E '     THEN 'E'  " +
					"				WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='X '     THEN 'X' " + 
					"				WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='H '     THEN 'H'  " +
					"				WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='S '     THEN 'S'  " +
					"				WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='V '     THEN 'V' ELSE  'Z' " +
					"				END) SEASONCODE, " +
					"               DBO_ARD_ITEM.item_shrt_ds, " +
					"               DBO_ARD_ITEM.str_pack_qt, " +
					"               DBO_ARD_ITEM.item_size_ds, " +
					"               DBO_ARD_ITEM.upc_prdct_cd, " +
					"               DBO_ARD_ITEM.ordr_book_indx_cd, " +
					"               DBO_ARD_ITEM.sor_ctgry_nb, " +
					"               DBO_ARD_ITEM.sor_sub_ctgry_nb, " +
					"               DBO_ARD_ITEM.rtl_dprtmnt_cd as deptcode " +
					"			FROM	DBO.NON_ULG_ARD_ITEM DBO_ARD_ITEM " +
					"			INNER JOIN	DBO.ARD_VNDR_VIEW DBO_ARD_VNDR_VIEW  " +
					"				ON DBO_ARD_ITEM.PRCHS_VNDR_KY = DBO_ARD_VNDR_VIEW.PRCHS_VNDR_KY " +
					"			WHERE 1=1  " +	
					"			and  NOT (DBO_ARD_ITEM.rndm_wght_cd = 'C' and DBO_ARD_ITEM.rtl_dprtmnt_cd in ('040','041','042','043') ) "+ //exclude case items				
					"			and not (DBO_ARD_ITEM.rtl_dprtmnt_cd in ('011','051'))"+ //exlude these depts per business				
					"			and not (DBO_ARD_ITEM.ordr_book_indx_cd = '99999')"+     //exclude OBI				
					"			and not (DBO_ARD_ITEM.upc_prdct_cd='000000000000000') "+ 			
					"			and not (DBO_ARD_ITEM.sor_ctgry_nb in (640,770))"+       //VMC Commodity				
					"			and  DBO_ARD_ITEM.ITEM_STTS_RSN_CD <> 'D' "+             //exclude discontinued items				
					"			and  DBO_ARD_ITEM.ITEM_UPC_NB<>0 "+                      //no upc				
					"			and  DBO_ARD_ITEM.item_upc_nb > 1000000 "+               //exclude PLUs	
					"           AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '***DUMMY VENDOR***' " +  //exclude dummy vendor
					"           AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> 'DUMMY VENDOR' " +        //exclude dummy vendor
					"           AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '**DUMMY VENDOR**' " +    //exclude dummy vendor
					"           and DBO_ARD_ITEM.fclty_cd not in ('02','04','07') "+          //exclude inactive facilities
					"			and  DBO_ARD_ITEM.SOR_CTGRY_NB = ? " +
					"			and  DBO_ARD_ITEM.PRVT_LBL_CD  = ?      " +
					"			and  DBO_ARD_VNDR_VIEW.AP_VNDR_CD = ? " +
					"		) " +		
					"	) "
					+ " WHERE seasoncode = ?  " +			
					"	GROUP BY ic, cat, category, lblcd, vndrcd, vndrnm, use_this_bsp, item_shrt_ds,str_pack_qt,item_size_ds,upc_prdct_cd, sor_ctgry_nb,sor_sub_ctgry_nb, deptcode " +
					"	ORDER BY  ic, use_this_bsp desc   " + 			
					") Group by ic, item_shrt_ds, sor_ctgry_nb,sor_sub_ctgry_nb, substr(upc_prdct_cd,1,10), deptcode          " + 				
					"  Order by 7, 2	 " ;	
	
	//get the most common brand at the item code level (build link group desc)
	public static final String singleBrand = 
		"select brand, count(*) TOTAL_ITEMS  "  +
		"from (  	 " +		
				"Select ic, cat, category, lblcd, vndrcd, vndrnm, brand, seasoncode, use_this_bsp "  +
				"from ( " +	
					
					// new code to handle future BSP	
					" Select ic, cat, category, lblcd, vndrcd, vndrnm, brand, seasoncode,  "  +	
						"    (case	 "  +
						"     when bsp_diff <0 and future_bsp_dt <= decrease_bsp_dt and futurebsp>0  then futureBSP  "  +	
						"     when bsp_diff >0 and future_bsp_dt <= increase_bsp_dt and futurebsp>0  then futureBSP	 "  +
						"     else  unitbsp 	 "  +
						"     END) as use_this_bsp  "  +	
					" from ( "  +						
					
					"		SELECT  " +	
					"			DBO_ARD_ITEM.ITEM_SCM_CD IC, " +
					"			DBO_ARD_ITEM.SOR_CTGRY_NB AS Cat, " +
					"			DBO_ARD_ITEM.CTGRY_DS AS Category, " +
					"			DBO_ARD_ITEM.PRVT_LBL_CD AS lblcd,  " +
					"			DBO_ARD_VNDR_VIEW.AP_VNDR_CD AS VndrCd, " +
					"			DBO_ARD_VNDR_VIEW.AP_VNDR_NM as vndrnm, " +  
					"			DBO_ARD_ITEM.BRND_NAME_DS AS Brand , " +
					"			(CASE  " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='E '     THEN 'E'  " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='X '     THEN 'X'  " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='H '    THEN 'H'  " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='S '     THEN 'S'  " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='V '     THEN 'V' ELSE  'Z' " +
					"			END) SEASONCODE, DBO_ARD_ITEM.DC_CD,  "  +
					"           CAST(ROUND(DBO_ARD_ITEM.CRNT_BSP_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2))  as unitbsp,  " +
					
					           // new code to handle future BSP
					"          CAST(ROUND(PNDNG_BSP_1_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) as futureBSP,   " +
					"          CAST(ROUND(PNDNG_BSP_1_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) - CAST(ROUND(DBO_ARD_ITEM.CRNT_BSP_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) AS BSP_DIFF,   " +
					"          current date+26 DAYS AS increase_bsp_dt,    " +
					"          current date+4  DAYS AS decrease_bsp_dt,    " +
					"          (case when DBO_ARD_ITEM.PNDNG_BSP_EFCTV_1_DT > current date then DBO_ARD_ITEM.PNDNG_BSP_EFCTV_1_DT else '9999-12-31' end ) as future_bsp_dt,   " +
					"          DBO_ARD_ITEM.PNDNG_BSP_1_AM as future_bsp_total     " +   
					
					"		FROM  DBO.NON_ULG_ARD_ITEM DBO_ARD_ITEM " +
					"		INNER JOIN	DBO.ARD_VNDR_VIEW DBO_ARD_VNDR_VIEW  " + 
					"			  ON DBO_ARD_ITEM.PRCHS_VNDR_KY = DBO_ARD_VNDR_VIEW.PRCHS_VNDR_KY " +
					"		WHERE 1=1 " +
					"       and  NOT (DBO_ARD_ITEM.rndm_wght_cd = 'C' and DBO_ARD_ITEM.rtl_dprtmnt_cd in ('040','041','042','043') ) "+ //exclude case items				
					"       and  not (DBO_ARD_ITEM.rtl_dprtmnt_cd in ('011','051'))"+ //exlude these depts per business				
					"       and  not (DBO_ARD_ITEM.ordr_book_indx_cd = '99999')"+     //exclude OBI				
					"       and  not (DBO_ARD_ITEM.upc_prdct_cd='000000000000000') "+ 				
					"       and  not (DBO_ARD_ITEM.sor_ctgry_nb in (640,770))"+       //VMC Commodity				
					"       and  DBO_ARD_ITEM.ITEM_STTS_RSN_CD <> 'D' "+              //exclude discontinued items				
					"       and  DBO_ARD_ITEM.ITEM_UPC_NB<>0 "+                       //no upc				
					"       and  DBO_ARD_ITEM.item_upc_nb > 1000000 "+                //exclude PLUs	
					"       AND  DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '***DUMMY VENDOR***' " +  //exclude dummy vendor
					"       AND  DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> 'DUMMY VENDOR' " +        //exclude dummy vendor
					"       AND  DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '**DUMMY VENDOR**' " +    //exclude dummy vendor
					"       and  DBO_ARD_ITEM.fclty_cd not in ('02','04','07') "+          //exclude inactive facilities
					"		and  DBO_ARD_ITEM.SOR_CTGRY_NB = ? " +
					"		and  DBO_ARD_ITEM.PRVT_LBL_CD = ? " +
					"		and  DBO_ARD_VNDR_VIEW.AP_VNDR_CD = ? " +
					"       and  substr(DBO_ARD_ITEM.upc_prdct_cd,1,10) = ? " +
					") "+
			") Group by ic, cat, category, lblcd, vndrcd, vndrnm, brand, seasoncode, use_this_bsp   	 " +	
		") where seasoncode = ?  ";

	public static final String singleBrandAddtl =
			"group by brand " +			
			"   order by count(*) desc, brand  ";
	
	//get the most common sub-category at the item code level (build link group desc)
	public static final String singleSubcat = 
	"select subcategory, count(*) TOTAL_ITEMS  " +
	"from (    " +			
	   "Select ic, cat, category, lblcd, vndrcd, vndrnm, subcat, subcategory, seasoncode, use_this_bsp  " +
	   "from (	  " +
	   
				//new code to handle future BSP 
				"select ic, cat, category, lblcd, vndrcd, vndrnm, subcat, subcategory, seasoncode,  "+
				"    (case	 "  +
				"     when bsp_diff <0 and future_bsp_dt <= decrease_bsp_dt and futurebsp>0  then futureBSP  "  +	
				"     when bsp_diff >0 and future_bsp_dt <= increase_bsp_dt and futurebsp>0  then futureBSP	 "  +
				"     else  unitbsp 	 "  +
				"     END) as use_this_bsp  "  +	
				" from ( "  +						
					
					"		SELECT   " +	
					"			DBO_ARD_ITEM.ITEM_SCM_CD IC,  " +
					"			DBO_ARD_ITEM.SOR_CTGRY_NB AS Cat,  " +
					"			DBO_ARD_ITEM.CTGRY_DS AS Category,  " +
					"			DBO_ARD_ITEM.PRVT_LBL_CD AS lblcd,   " +
					"			DBO_ARD_VNDR_VIEW.AP_VNDR_CD AS VndrCd,  " +
					"			DBO_ARD_VNDR_VIEW.AP_VNDR_NM as vndrnm,  " +  
					"			DBO_ARD_ITEM.SOR_SUB_CTGRY_NB AS SubCat,  " +
					"			DBO_ARD_ITEM.SUB_CTGRY_DS AS SubCategory,  " +  
					"			(CASE   " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='E '     THEN 'E'   " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='X '     THEN 'X'   " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='H '     THEN 'H'   " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='S '     THEN 'S'   " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='V '     THEN 'V' ELSE  'Z'  " +
					"			END) SEASONCODE, DBO_ARD_ITEM.DC_CD,   " +
					"           CAST(ROUND(DBO_ARD_ITEM.CRNT_BSP_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) as unitbsp,   " + 
			
					           // new code to handle future BSP
					"          CAST(ROUND(PNDNG_BSP_1_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) as futureBSP,   " +
					"          CAST(ROUND(PNDNG_BSP_1_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) - CAST(ROUND(DBO_ARD_ITEM.CRNT_BSP_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) AS BSP_DIFF,   " +
					"          current date+26 DAYS AS increase_bsp_dt,    " +
					"          current date+4  DAYS AS decrease_bsp_dt,    " +
					"          (case when DBO_ARD_ITEM.PNDNG_BSP_EFCTV_1_DT > current date then DBO_ARD_ITEM.PNDNG_BSP_EFCTV_1_DT else '9999-12-31' end ) as future_bsp_dt,   " +
					"          DBO_ARD_ITEM.PNDNG_BSP_1_AM as future_bsp_total     " +   
			
					"		FROM	DBO.NON_ULG_ARD_ITEM DBO_ARD_ITEM  " +
					"		INNER JOIN	DBO.ARD_VNDR_VIEW DBO_ARD_VNDR_VIEW   " +
					"			ON DBO_ARD_ITEM.PRCHS_VNDR_KY = DBO_ARD_VNDR_VIEW.PRCHS_VNDR_KY  " +
					"		WHERE	1=1 " +
					"		and  NOT (DBO_ARD_ITEM.rndm_wght_cd = 'C' and DBO_ARD_ITEM.rtl_dprtmnt_cd in ('040','041','042','043') ) "+ //exclude case items				
					"		and not (DBO_ARD_ITEM.rtl_dprtmnt_cd in ('011','051'))"+ //exlude these depts per business				
					"		and not (DBO_ARD_ITEM.ordr_book_indx_cd = '99999')"+      //exclude OBI				
					"		and not (DBO_ARD_ITEM.upc_prdct_cd='000000000000000') "+ 			
					"		and not (DBO_ARD_ITEM.sor_ctgry_nb in (640,770))"+        //VMC Commodity				
					"		and DBO_ARD_ITEM.ITEM_STTS_RSN_CD <> 'D' "+               //exclude discontinued items				
					"		and DBO_ARD_ITEM.ITEM_UPC_NB<>0 "+                        //no upc				
					"		and DBO_ARD_ITEM.item_upc_nb > 1000000 "+                 //exclude PLUs	
					" 		AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '***DUMMY VENDOR***' " +  //exclude dummy vendor
					" 		AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> 'DUMMY VENDOR' " +        //exclude dummy vendor
					" 		AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '**DUMMY VENDOR**' " +    //exclude dummy vendor
					"       and DBO_ARD_ITEM.fclty_cd not in ('02','04','07') "+          //exclude inactive facilities
					"		and DBO_ARD_ITEM.SOR_CTGRY_NB = ?  " +
					"		and DBO_ARD_ITEM.PRVT_LBL_CD  = ?  " +
					"		and DBO_ARD_VNDR_VIEW.AP_VNDR_CD = ?  " +
					"       and substr(DBO_ARD_ITEM.upc_prdct_cd,1,10) = ? " + 		
					"      ) "+
			") Group by ic, cat, category, lblcd, vndrcd, vndrnm, subcat, subcategory, seasoncode, use_this_bsp    " +		
		") where seasoncode = ?   ";
	
	public static final String singleSubcatAddtl =
		"group by subcategory    " +			
		"order by count(*) desc, subcategory   " ; 
		
	
	//get the min and max size (build link group desc)
	public static final String minMaxSize = 
	"select (case when  min_size = max_size then CAST(ROUND(min_size,2) AS DECIMAL(18,2))||' '||uow else CAST(ROUND(min_size,2) AS DECIMAL(18,2)) ||'-'|| CAST(ROUND(max_size,2) AS DECIMAL(18,2))||' '||uow end) "  +
	"from (  "+
		"select uow, min(RTL_UNIT_WGHT_QT) as min_size, max(RTL_UNIT_WGHT_QT) as max_size "  +
		"from (  " +			
			
				"select uow, use_this_bsp, RTL_UNIT_WGHT_QT, seasoncode "  + 
				"from (  " +		
					
					// new code to handle future BSP	
					" Select uow,   "  +	
						"    (case	 "  +
						"     when bsp_diff <0 and future_bsp_dt <= decrease_bsp_dt and futurebsp>0  then futureBSP  "  +	
						"     when bsp_diff >0 and future_bsp_dt <= increase_bsp_dt and futurebsp>0  then futureBSP	 "  +
						"     else  unitbsp 	 "  +
						"     END) as use_this_bsp,  "  +
						"     RTL_UNIT_WGHT_QT, seasoncode " +
					" from ( "  +						
					
					"		SELECT 	 " +
					"			DBO_ARD_ITEM.ITEM_SCM_CD IC, " +
					"			DBO_ARD_ITEM.SOR_CTGRY_NB AS Cat, " +
					"			DBO_ARD_ITEM.CTGRY_DS AS Category, " +
					"			DBO_ARD_ITEM.PRVT_LBL_CD AS FirstOfPRVT_LBL_CD, " + 
					"			DBO_ARD_VNDR_VIEW.AP_VNDR_CD AS VndrCd, " +
					"			DBO_ARD_VNDR_VIEW.AP_VNDR_NM,  " +
					"			DBO_ARD_ITEM.ITEM_STTS_RSN_CD, DBO_ARD_ITEM.ITEM_SIZE_DS,  DBO_ARD_ITEM.UOW, DBO_ARD_ITEM.RTL_UNIT_WGHT_QT, " +
					"			CAST(ROUND(DBO_ARD_ITEM.CRNT_BSP_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) unitbsp,  " +
					"			(CASE  " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='E '     THEN 'E'  " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='X '     THEN 'X'  " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='H '     THEN 'H'  " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='S '     THEN 'S'  " +
					"			WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='V '     THEN 'V' ELSE  'Z' " +
					"			END) SEASONCODE, DBO_ARD_ITEM.DC_CD,  " +
					
					           // new code to handle future BSP
					"          CAST(ROUND(PNDNG_BSP_1_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) as futureBSP,   " +
					"          CAST(ROUND(PNDNG_BSP_1_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) - CAST(ROUND(DBO_ARD_ITEM.CRNT_BSP_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) AS BSP_DIFF,   " +
					"          current date+26 DAYS AS increase_bsp_dt,    " +
					"          current date+4  DAYS AS decrease_bsp_dt,    " +
					"          (case when DBO_ARD_ITEM.PNDNG_BSP_EFCTV_1_DT > current date then DBO_ARD_ITEM.PNDNG_BSP_EFCTV_1_DT else '9999-12-31' end ) as future_bsp_dt,   " +
					"          DBO_ARD_ITEM.PNDNG_BSP_1_AM as future_bsp_total     " +   
					
					"		FROM	DBO.NON_ULG_ARD_ITEM DBO_ARD_ITEM " +
					"		INNER JOIN	DBO.ARD_VNDR_VIEW DBO_ARD_VNDR_VIEW  " +
					"			ON DBO_ARD_ITEM.PRCHS_VNDR_KY = DBO_ARD_VNDR_VIEW.PRCHS_VNDR_KY " +
					"		WHERE	1=1 " +
					"		and  NOT (DBO_ARD_ITEM.rndm_wght_cd = 'C' and DBO_ARD_ITEM.rtl_dprtmnt_cd in ('040','041','042','043') ) "+ //exclude case items				
					"		and not (DBO_ARD_ITEM.rtl_dprtmnt_cd in ('011','051'))"+ //exlude these depts per business				
					"		and not (DBO_ARD_ITEM.ordr_book_indx_cd = '99999')"+     //exclude OBI				
					"		and not (DBO_ARD_ITEM.upc_prdct_cd='000000000000000') "+ 				
					"		and not (DBO_ARD_ITEM.sor_ctgry_nb in (640,770))"+       //VMC Commodity				
					"		and DBO_ARD_ITEM.ITEM_STTS_RSN_CD <> 'D' "+              //exclude discontinued items				
					"		and DBO_ARD_ITEM.ITEM_UPC_NB<>0 "+                       //no upc				
					"		and DBO_ARD_ITEM.item_upc_nb > 1000000 "+                //exclude PLUs	
					" 		AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '***DUMMY VENDOR***' " +  //exclude dummy vendor
					" 		AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> 'DUMMY VENDOR' " +        //exclude dummy vendor
					" 		AND DBO_ARD_VNDR_VIEW.AP_VNDR_NM <> '**DUMMY VENDOR**' " +    //exclude dummy vendor
					"       and DBO_ARD_ITEM.fclty_cd not in ('02','04','07') "+          //exclude inactive facilities
					"		and DBO_ARD_ITEM.SOR_CTGRY_NB = ? " +
					"		and DBO_ARD_ITEM.PRVT_LBL_CD  = ? " +
					"		and DBO_ARD_VNDR_VIEW.AP_VNDR_CD = ? " +
					"       and substr(DBO_ARD_ITEM.upc_prdct_cd,1,10) = ? " + 					
			        " ) " +
			        " where  seasoncode = ?  " +
                 ")   " ;

	public static final String minMaxSizeAddtl = "  " +
	                "	     Group by uow, use_this_bsp, RTL_UNIT_WGHT_QT, seasoncode   " +		
					"	     Order by RTL_UNIT_WGHT_QT  " +		
				")  group by uow  " + 
			") ";	
	
	public static final String insertUlgXrefSql = "INSERT INTO DB2PDBA.ULG_XREF(ULG_GRP_NB, ULG_DESC, INSERT_TS, INSRT_USERID, UPDT_TS, UPDT_USERID) values (?, ?, CURRENT TIMESTAMP, 'AutoBatch', CURRENT TIMESTAMP, 'AutoBatch')";

	public static final String updateLinkGrpDescSql = "update DB2PDBA.ULG_XREF set ULG_DESC=?, updt_ts=current timestamp where ulg_grp_nb=? and UPDT_USERID = 'AutoBatch' "; //make sure it's not a custom link group so you don't override user updates

	public static final String updateLinkGrpDescByItemSql = "update DB2PDBA.ulg_xref  set ULG_DESC=?, updt_ts=current timestamp  where UPDT_USERID = 'AutoBatch' and ulg_grp_nb = (select b.ulg_grp_nb from DB2PDBA.ulg_item b where b.item_cd = ? ) ";
	
	public static final String emptyLinkGroupSql = "select ulg_grp_nb, ulg_desc from DB2PDBA.ULG_xref WHERE ulg_grp_nb not in (select ulg_grp_nb from DB2PDBA.ULG_item)   and ulg_grp_nb >1  ";
	
	public static final String deleteEmptyLinkGroupSql = "delete from db2pdba.ulg_xref where ulg_grp_nb = ?  ";  

	public static final String deleteDiscontinuedItemsSql = "delete from DB2PDBA.ulg_item where item_cd = ?  ";  
	
	public static final String insertUlgItemSql = "INSERT INTO DB2PDBA.ULG_ITEM(ITEM_CD, ULG_GRP_NB, ITEM_DESC,PACK_DESC,SIZE_DESC,UNIT_COST_DESC,UNIT_DESC, CITY_SRP_DESC,UPC_DESC,OBX_DESC,CMDTY_DESC,SUB_CMDTY_DESC, INSERT_TS, INSRT_USERID, UPDT_TS, UPDT_USERID) values (?, ?, ?,?,?,?,?,?,?,?,?,?, CURRENT TIMESTAMP, 'AutoBatch', CURRENT TIMESTAMP, 'AutoBatch')";

	//indicates newly added item
	public static final String updateUlgItemTimestampSql = "update db2pdba.ulg_item set insrt_userid='AutoBatch', updt_userid='AutoBatch', insert_ts=current timestamp, updt_ts=current timestamp, ulg_grp_nb=? where item_cd = ? ";

	//don't update the update date columns so that the web app can use the update date columns to determine if an item is a newly added item to the link group
	public static final String updateUlgItemInfoSql = 
			"UPDATE	db2pdba.ulg_item "
			+ "SET	ITEM_DESC = ?, "
			+ "	PACK_DESC = ?, "
			+ "	SIZE_DESC = ?, "
			+ "	UNIT_COST_DESC=?, "
			+ "	UNIT_DESC = ?, "
			+ "	CITY_SRP_DESC = ?, "
			+ "	UPC_DESC = ?, "
			+ "	OBX_DESC = ?, "
			+ "	CMDTY_DESC = ?, "
			+ "	SUB_CMDTY_DESC = ? "
			+ "WHERE	item_cd = ? ";

	public static final String selectItemUPCs =
			" SELECT  LISTAGG(upccode,', ') from ("
			+ "	SELECT	DBO_ARD_ITEM.ITEM_SCM_CD IC,  "
			+ "			DBO_ARD_ITEM.upc_prdct_cd as  upccode    "
			+ "	FROM	DBO.NON_ULG_ARD_ITEM DBO_ARD_ITEM  "
			+ "	        INNER JOIN	DBO.ARD_VNDR_VIEW DBO_ARD_VNDR_VIEW   "
			+ "			ON DBO_ARD_ITEM.PRCHS_VNDR_KY = DBO_ARD_VNDR_VIEW.PRCHS_VNDR_KY  "
			+ "	WHERE		DBO_ARD_ITEM.ITEM_STTS_RSN_CD <>'D' "  
			+ "			and DBO_ARD_ITEM.SOR_CTGRY_NB = ? "  
			+ "			and DBO_ARD_ITEM.PRVT_LBL_CD = ?  "
			+ "			and DBO_ARD_VNDR_VIEW.AP_VNDR_CD = ?  "
			+ "			AND DBO_ARD_ITEM.ITEM_UPC_NB <>0  "  
			+ "			AND DBO_ARD_ITEM.ITEM_SCM_CD = ? "     
			+ "         and DBO_ARD_ITEM.fclty_cd not in ('02','04','07') "  //exclude inactive facilities 
			+ "	GROUP BY	DBO_ARD_ITEM.ITEM_SCM_CD, DBO_ARD_ITEM.upc_prdct_cd " 
			+ ")";	
	
	public static final String selectItemPack = 
			       "SELECT 	 "
			+ "			    DBO_ARD_ITEM.ITEM_SCM_CD IC, "
			+ "			    min(DBO_ARD_ITEM.str_pack_qt) as min_pack, max(DBO_ARD_ITEM.str_pack_qt) as max_pack   "
			+ "		FROM	DBO.NON_ULG_ARD_ITEM DBO_ARD_ITEM "
			+ "		        INNER JOIN	DBO.ARD_VNDR_VIEW DBO_ARD_VNDR_VIEW  "
			+ "			    ON DBO_ARD_ITEM.PRCHS_VNDR_KY = DBO_ARD_VNDR_VIEW.PRCHS_VNDR_KY "
			+ "		WHERE	DBO_ARD_ITEM.ITEM_STTS_RSN_CD <>'D'  "
			+ "			and DBO_ARD_ITEM.SOR_CTGRY_NB = ? "
			+ "			and DBO_ARD_ITEM.PRVT_LBL_CD = ? "
			+ "			and DBO_ARD_VNDR_VIEW.AP_VNDR_CD = ? "
			+ "			AND DBO_ARD_ITEM.ITEM_UPC_NB <>0   "
			+ "			AND DBO_ARD_ITEM.ITEM_SCM_CD = ?  "
			+ "         and DBO_ARD_ITEM.fclty_cd not in ('02','04','07') " //exclude inactive facilities
			+ "		GROUP BY DBO_ARD_ITEM.ITEM_SCM_CD ";
	

	public static final String selectItemDesc =
			"SELECT LISTAGG(itemdesc,', ') "
		+   "FROM ( "  +
			"     SELECT distinct(itemdesc)  "
			+ "   FROM (  " +			
			"		SELECT ic, itemdesc, dccd, SEASONCODE, unitbsp "
			+ "     FROM (	" +	
			"			SELECT 	" +
			"				        DBO_ARD_ITEM.ITEM_SCM_CD IC," +
			"				        DBO_ARD_ITEM.ITEM_SHRT_DS as itemdesc," +
			"				        DBO_ARD_ITEM.SOR_CTGRY_NB AS Cat," +
			"				        DBO_ARD_ITEM.CTGRY_DS AS Category," +
			"				        DBO_ARD_ITEM.PRVT_LBL_CD AS lblcd, " +
			"				        DBO_ARD_VNDR_VIEW.AP_VNDR_CD AS VndrCd," +
			"				        DBO_ARD_VNDR_VIEW.AP_VNDR_NM as vndrnm,  " +
			"				        (CASE " +
			"				        WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='E '     THEN 'E' " +
			"				        WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='X '     THEN 'X' " +
			"				        WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='H '     THEN 'H' " +
			"				        WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='S '     THEN 'S' " +
			"				        WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='V '     THEN 'V' ELSE  'Z'" +
			"				        END) SEASONCODE, DBO_ARD_ITEM.DC_CD as dccd, " +
			"				        CAST(ROUND(DBO_ARD_ITEM.CRNT_BSP_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) as unitbsp  " + 
			"			FROM	    DBO.NON_ULG_ARD_ITEM DBO_ARD_ITEM   " +
			"			INNER JOIN	DBO.ARD_VNDR_VIEW DBO_ARD_VNDR_VIEW " +
			"				        ON DBO_ARD_ITEM.PRCHS_VNDR_KY = DBO_ARD_VNDR_VIEW.PRCHS_VNDR_KY " +
			"			WHERE 1=1 	" +
			"			and  NOT (DBO_ARD_ITEM.rndm_wght_cd = 'C' and DBO_ARD_ITEM.rtl_dprtmnt_cd in ('040','041','042','043') ) "+ //exclude case items				
			"			and not (DBO_ARD_ITEM.rtl_dprtmnt_cd in ('011','051'))"+ //exlude these depts per business				
			"			and not (DBO_ARD_ITEM.ordr_book_indx_cd = '99999')"+     //exclude OBI				
			"			and not (DBO_ARD_ITEM.upc_prdct_cd='000000000000000') "+ 				
			"			and not (DBO_ARD_ITEM.sor_ctgry_nb in (640,770))"+       //VMC Commodity				
			"            and DBO_ARD_ITEM.ITEM_STTS_RSN_CD <> 'D' "+  //exclude discontinued items				
			"            and DBO_ARD_ITEM.ITEM_UPC_NB<>0 "+           //no upc				
			"            and DBO_ARD_ITEM.item_upc_nb > 1000000 "+    //exclude PLUs
			"            and DBO_ARD_ITEM.fclty_cd not in ('02','04','07') " + //exclude inactive facilities
			"			 and DBO_ARD_ITEM.SOR_CTGRY_NB = ?   " +
			"			 and DBO_ARD_ITEM.PRVT_LBL_CD = ? " +
			"			 and DBO_ARD_VNDR_VIEW.AP_VNDR_CD = ? " + 
			"		) "
			+ "     GROUP BY ic, itemdesc, dccd, seasoncode, unitbsp  " +   		
			") "
		+ "  WHERE seasoncode = ?  and ic = ? " + 
			"GROUP BY itemdesc    " +
    ") ";

	public static final String selectOBI =
			"SELECT LISTAGG(obi2,', ') "
		+   "FROM ( " +
			"   SELECT distinct(obi) as obi2 "
			+ " FROM (  " +			
			"		SELECT ic, obi, dccd, SEASONCODE, unitbsp "
			+ "     FROM (	" +	
			"			SELECT 	" +
			"				DBO_ARD_ITEM.ITEM_SCM_CD IC," +
			"				DBO_ARD_ITEM.ordr_book_indx_cd as obi, " +
			"				DBO_ARD_ITEM.SOR_CTGRY_NB AS Cat," +
			"				DBO_ARD_ITEM.CTGRY_DS AS Category," +
			"				DBO_ARD_ITEM.PRVT_LBL_CD AS lblcd, " +
			"				DBO_ARD_VNDR_VIEW.AP_VNDR_CD AS VndrCd," +
			"				DBO_ARD_VNDR_VIEW.AP_VNDR_NM as vndrnm,  " +
			"				(CASE " +
			"				WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='E '     THEN 'E' " +
			"				WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='X '     THEN 'X' " +
			"				WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='H '     THEN 'H' " +
			"				WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='S '     THEN 'S' " +
			"				WHEN DBO_ARD_ITEM.SOR_CTGRY_NB=4 and DBO_ARD_ITEM.SOR_SUB_CTGRY_NB=7 and  SUBSTR(DBO_ARD_ITEM.ITEM_SHRT_DS,1,2)='V '     THEN 'V' ELSE  'Z' " +
			"				END) SEASONCODE, DBO_ARD_ITEM.DC_CD as dccd, " +
			"				CAST(ROUND(DBO_ARD_ITEM.CRNT_BSP_AM/DBO_ARD_ITEM.STR_PACK_QT,2) AS DECIMAL(18,2)) as unitbsp  " + 
			"			FROM	    DBO.NON_ULG_ARD_ITEM DBO_ARD_ITEM   " +
			"			INNER JOIN	DBO.ARD_VNDR_VIEW DBO_ARD_VNDR_VIEW " +
			"				        ON DBO_ARD_ITEM.PRCHS_VNDR_KY = DBO_ARD_VNDR_VIEW.PRCHS_VNDR_KY " +
			"			WHERE 1=1 	" +
			"			and  NOT (DBO_ARD_ITEM.rndm_wght_cd = 'C' and DBO_ARD_ITEM.rtl_dprtmnt_cd in ('040','041','042','043') ) "+ //exclude case items				
			"			and not (DBO_ARD_ITEM.rtl_dprtmnt_cd in ('011','051'))"+   //exlude these depts per business				
			"			and not (DBO_ARD_ITEM.ordr_book_indx_cd = '99999')"+       //exclude OBI				
			"			and not (DBO_ARD_ITEM.upc_prdct_cd='000000000000000') "+ 			
			"			and not (DBO_ARD_ITEM.sor_ctgry_nb in (640,770))"+         //VMC Commodity				
			"			and DBO_ARD_ITEM.ITEM_STTS_RSN_CD <> 'D' "+                //exclude discontinued items				
			"			and DBO_ARD_ITEM.ITEM_UPC_NB<>0 "+                         //no upc				
			"			and DBO_ARD_ITEM.item_upc_nb > 1000000   "+                //exclude PLUs
			"           and DBO_ARD_ITEM.fclty_cd not in ('02','04','07') " +      //exclude inactive facilities
			"			and DBO_ARD_ITEM.SOR_CTGRY_NB = ?   " +
			"			and DBO_ARD_ITEM.PRVT_LBL_CD = ? " +
			"			and DBO_ARD_VNDR_VIEW.AP_VNDR_CD = ? " + 
			"		) "
			+ "     GROUP BY ic, obi, dccd, seasoncode, unitbsp  " +   		
			") "
		+ "  WHERE seasoncode = ?  and ic = ? " + 
			"GROUP BY obi   " +
	") ";

	public static final String addtlItemInfo =
			"select MIN_SIZE, max_size, min_unit, max_unit, (CASE WHEN UPDATEKEY IS NULL THEN 'NONE' ELSE UPDATEKEY END) as keydesc, (CASE WHEN ULGGRPNB IS NULL THEN 0 ELSE ULGGRPNB END) as ulggrpnbr, upduser   " +
			"from ( " +
					"	   select " +
					"		   min(citysrp) as min_size, max(citysrp) as max_size, " +
					"		   min(displayUnit) as min_unit, max(displayUnit) as max_unit," +
				    "         (select trim(b.item_desc)||':'||trim(b.pack_desc)||':'||trim(b.size_desc)||':'||trim(b.unit_cost_desc)||':'||trim(b.unit_desc)||':'||trim(b.city_srp_desc)||':'||trim(b.upc_desc)||':'||trim(b.obx_desc)||':'||trim(b.cmdty_desc)||':'||trim(b.sub_cmdty_desc)  from db2pdba.ulg_item b where b.item_cd = ? )  as updatekey, " +
					"         (select ulg_grp_nb from db2pdba.ulg_item where item_cd = ? ) as ulggrpnb,  " +
				    "         (select updt_userid from db2pdba.ulg_item where item_cd = ?) as upduser "+
					"	   from ( " +
					"		      select " +
					"			      fclty_cd||str_cd as facilitystore, " +
					"			      item_cd, fclty_cd, str_cd, prc_type_cd, cmpny_cd, wrhs_cd, " +
					"			      rtl_prc_mltpl_qt as displayUnit, " +
					"			      rtl_prc_am citysrp, rtl_end_dt, rtl_efctv_dt " +
					"		      from DB2PDBA.spm_crp a " +
					"		      WHERE a.item_cd= ? " +
					"		      and rtl_efctv_dt <=current_date " +
					"		      and (rtl_end_dt = '0001-01-01' or rtl_end_dt >=current_date) " +
					"             and fclty_cd not in ('02','04','07') and prc_type_cd = '001'"+  //exclude inactive facilities
					"	   ) " +
					"	   where facilitystore in " +
					"	   ( " +
					"	      select     a.fclty_cd||a.cntrl_str_cd " +
					"	      from       DB2PDBA.spm_prc_grp a " +
					"	      inner join DB2PDBA.ods_str b       on a.cntrl_str_cd = b.sor_str_cd " +
					"	      inner join DB2PDBA.spm_str_prmtr c on a.cntrl_str_cd = c.str_cd " +
					"	      where      a.city_rrl_dflt_cd = 'C' " +
					"	   ) " +
			") " ;
	public static final String getXrefLinkGroupDesc = "select ulg_desc from DB2PDBA.ulg_xref where ulg_grp_nb = ? ";
	
//	public static final String insertUlgAudit  = "INSERT INTO DB2PDBA.ULG_AUDIT (AUDIT_SEQ_NB, AUDIT_CHANGE_DESC) VALUES (?,?)"; 
//	
//	public static final String insertUlgAudit2 = "INSERT INTO DB2PDBA.ULG_AUDIT (AUDIT_SEQ_NB, AUDIT_CHANGE_DESC, curr_ulg_grp_nb, curr_ulg_grp_desc) VALUES (?,?,?,?)"; 
	
	
//	public static final String insertUlgAuditItemDetails = 
//			"INSERT INTO DB2PDBA.ULG_AUDIT "
//			+ "(AUDIT_SEQ_NB, "
//			+ " AUDIT_CHANGE_DESC,"
//			+ " item_cd, "
//			+ " CURR_ULG_GRP_NB,"             	
//			+ " CURR_ULG_GRP_DESC,"             	
//			+ " CURR_ITEM_DESC,"		
//			+ " CURR_PACK_DESC,"	 	
//			+ " CURR_SIZE_DESC,"	
//			+ " CURR_UNIT_COST_DESC,	"	
//			+ " CURR_UNIT_DESC,"	
//			+ " CURR_CITY_SRP_DESC,"	
//			+ " CURR_UPC_DESC,"	
//			+ " CURR_OBX_DESC,"	
//			+ " CURR_CMDTY_DESC,	"	
//			+ " CURR_SUB_CMDTY_DESC, "
//            + "ORIG_ITEM_DESC,"		
//            + "ORIG_PACK_DESC,"	 	
//			+ "ORIG_SIZE_DESC,"		
//			+ "ORIG_UNIT_COST_DESC,"	
//			+ "ORIG_UNIT_DESC,	"	
//			+ "ORIG_CITY_SRP_DESC,	"
//			+ "ORIG_UPC_DESC,"		
//			+ "ORIG_OBX_DESC"
//			+ ") "
//			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

//	public static final String commonLoggingSql = 
//			"INSERT INTO DB2PDBA.ULG_AUDIT "
//			+ "(AUDIT_SEQ_NB, "
//			+ " AUDIT_CHANGE_DESC,"
//			+ " item_cd, "
//			+ " CURR_ULG_GRP_NB,"             	
//			+ " CURR_ULG_GRP_DESC,"             	
//			+ " CURR_ITEM_DESC,"		
//			+ " CURR_PACK_DESC,"	 	
//			+ " CURR_SIZE_DESC,"	
//			+ " CURR_UNIT_COST_DESC,	"	
//			+ " CURR_UNIT_DESC,"	
//			+ " CURR_CITY_SRP_DESC,"	
//			+ " CURR_UPC_DESC,"	
//			+ " CURR_OBX_DESC,"	
//			+ " CURR_CMDTY_DESC,	"	
//			+ " CURR_SUB_CMDTY_DESC, "
//			+ " ORIG_ulg_grp_nb, "
//			+ " ORIG_ulg_grp_desc, "
//            + " ORIG_ITEM_DESC,"		
//            + " ORIG_PACK_DESC,"	 	
//			+ " ORIG_SIZE_DESC,"		
//			+ " ORIG_UNIT_COST_DESC,"	
//			+ " ORIG_UNIT_DESC,	"	
//			+ " ORIG_CITY_SRP_DESC,	"
//			+ " ORIG_UPC_DESC,"		
//			+ " ORIG_OBX_DESC,"
//			+ " ORIG_CMDTY_DESC,	"	
//			+ " ORIG_SUB_CMDTY_DESC "
//			+ ") "
//			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
//	public static final String logDeleteLinkGrp = 
//			"INSERT INTO DB2PDBA.ULG_AUDIT "
//			+ "(AUDIT_SEQ_NB, "
//			+ " AUDIT_CHANGE_DESC,"
//			+ " item_cd, "
//			+ " ORIG_ULG_GRP_NB,"             	
//			+ " ORIG_ULG_GRP_DESC,"             	
//			+ " ORIG_ITEM_DESC,"		
//			+ " ORIG_PACK_DESC,"	 	
//			+ " ORIG_SIZE_DESC,"	
//			+ " ORIG_UNIT_COST_DESC,	"	
//			+ " ORIG_UNIT_DESC,"	
//			+ " ORIG_CITY_SRP_DESC,"	
//			+ " ORIG_UPC_DESC,"	
//			+ " ORIG_OBX_DESC,"	
//			+ " ORIG_CMDTY_DESC,	"	
//			+ " ORIG_SUB_CMDTY_DESC "
//			+ ") "
//			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";	
	
	public static final String getItemLinkGrpNbr = "select a.ulg_grp_nb, b.ulg_desc from DB2PDBA.ulg_item a, DB2PDBA.ulg_xref b where a.ulg_grp_nb=b.ulg_grp_Nb and a.item_cd = ?  ";  
	
//	public static final String getMaxAuditSeqNbr = "SELECT (CASE WHEN SEQNB IS NULL THEN 0 ELSE SEQNB END) SEQNBVALUE FROM (   "
//			+ "SELECT MAX(AUDIT_SEQ_NB)SEQNB FROM DB2PDBA.ULG_AUDIT where AUDIT_DT = CURRENT DATE )";
	
	public static final String itemExist = "select 'exist' from DB2PDBA.ULG_item where item_cd = ? ";

//	public static final String insertAuditDiscontinuedItem = 
//			"INSERT INTO DB2PDBA.ULG_AUDIT "
//			+ "(AUDIT_SEQ_NB, "
//			+ " AUDIT_CHANGE_DESC,"
//			+ " item_cd, "
//			+ " ORIG_ULG_GRP_NB,"             	
//			+ " ORIG_ULG_GRP_DESC,"             	
//			+ " ORIG_ITEM_DESC,"		
//			+ " ORIG_PACK_DESC,"	 	
//			+ " ORIG_SIZE_DESC,"	
//			+ " ORIG_UNIT_COST_DESC,	"	
//			+ " ORIG_UNIT_DESC,"	
//			+ " ORIG_CITY_SRP_DESC,"	
//			+ " ORIG_UPC_DESC,"	
//			+ " ORIG_OBX_DESC,"	
//			+ " ORIG_CMDTY_DESC,	"	
//			+ " ORIG_SUB_CMDTY_DESC "
//			+ ") "
//			+ "  (SELECT ?,?,"
//			+ "    a.ITEM_CD        ,"
//			+ "    a.ULG_GRP_NB     ,"
//			+ "    b.ulg_desc       ,"
//			+ "    a.ITEM_DESC      ,"
//			+ "    a.PACK_DESC      ,"
//			+ "    a.SIZE_DESC      ,"
//			+ "    a.UNIT_COST_DESC ,"
//			+ "    a.UNIT_DESC      ,"
//			+ "    a.CITY_SRP_DESC  ,"
//			+ "    a.UPC_DESC       ,"
//			+ "    a.OBX_DESC       ,"
//			+ "    a.CMDTY_DESC     ,"
//			+ "    a.SUB_CMDTY_DESC "
//			+ "  FROM DB2PDBA.ULG_ITEM a, DB2PDBA.ULG_xref b WHERE a.ulg_grp_nb=b.ulg_grp_nb and a.item_cd = ? ) ";
	
//	public static final String getAuditChangeDesc = "select trim(audit_change_desc), audit_seq_nb from DB2PDBA.ULG_audit where item_cd = ? ";
	
//	public static final String updateAuditChangeDesc = "update DB2PDBA.ULG_audit set audit_change_desc = ? where item_cd = ? ";
	
}
