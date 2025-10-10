package com.awginc.ulg;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TesterUlg {

	public static void main(String[] args) throws Exception {
//		testProcess("");
//		testULG();
		
		System.out.println("start");
		UlgChangesTester.process();
		System.out.println("end");
	}
	public static void testULG() throws Exception	{
		System.out.println("start");
		LinkLikeItemDetailsTESTER.process();
		System.out.println("end");
	}	
	public static void testProcess(String outgoingDirectory) throws SQLException {
	
		String query = "select DBO_ARD_ITEM.ITEM_SCM_CD  FROM DBO.NON_ULG_ARD_ITEM DBO_ARD_ITEM WHERE DBO_ARD_ITEM.ITEM_SCM_CD='489435' and DBO_ARD_ITEM.fclty_wrhs_cd='0102' ";
		
		PreparedStatement prep = LinkLikeItemDetailsTESTER.testPreparedStatement(query);

		ResultSet resultSet = null;
		resultSet = prep.executeQuery();
		while(resultSet.next())	{
			System.out.println("testing "+resultSet.getString(1));
		}
	}

}
