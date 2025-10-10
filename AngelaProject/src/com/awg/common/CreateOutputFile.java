package com.awg.common;

import java.io.IOException;
import java.util.List;
import java.io.BufferedWriter; 
import java.io.FileWriter; 
import java.io.FileNotFoundException; 

public class CreateOutputFile 
{
	
	public static String createOutputFile(String outputFileName, List<String> list)
	{
		BufferedWriter bufferedWriter = null;

		try 
		{
			bufferedWriter = new BufferedWriter(new FileWriter(outputFileName));

			//writeout to text file
			for(int i=0;i<list.size();i++)
			{
				bufferedWriter.write(list.get(i));
				bufferedWriter.newLine();
			}
       } 
		catch (FileNotFoundException ex) 
       {
	            ex.printStackTrace();
       } catch (IOException ex) 
       {
	            ex.printStackTrace();
       } finally 
       {
	            //Close the BufferedWriter
            try {
	                if (bufferedWriter != null) 
	                {
	                    bufferedWriter.flush();
	                    bufferedWriter.close();
	                }
	            } catch (IOException ex) 
	            {
	                ex.printStackTrace();
	            }
	        }
		return "";
		
	}
}

