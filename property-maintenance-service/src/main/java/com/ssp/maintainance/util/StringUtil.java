package com.ssp.maintainance.util;

public class StringUtil {
	public static boolean isStringNull(String data){
		if( data == null || ( data!=  null && data.isEmpty())){
			return true;
		}
		else{
			return false;
		}
	}
}
