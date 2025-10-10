package com.awg.common;

import java.io.File;
import java.io.FilenameFilter;

public interface InputFilter extends FilenameFilter {

	@Override
	default boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		boolean accepted = false;

		if (name.startsWith("BRdataReport_")) {
			accepted = true;
		}

		return accepted;
	}
}
