package com.awginc.ulg;

import java.io.File;
import java.io.FilenameFilter;

public class UlgInputFilter implements FilenameFilter {

	public boolean accept(File dir, String name)
	{
		boolean accepted = false;

    	if (name.contains(UlgConstants.ulg1) ||
    		name.contains(UlgConstants.ulg2) ||
    		name.contains(UlgConstants.ulg3) ||
    		name.contains(UlgConstants.ulg4) ||
    		name.contains(UlgConstants.ulg5) ||
    		name.contains(UlgConstants.ulg6) ||
    		name.contains(UlgConstants.ulg7) ||
    		name.contains(UlgConstants.ulg10) ||
    		name.contains(UlgConstants.ulg8)){
			accepted = true;
		}

		return accepted;
	}
}
