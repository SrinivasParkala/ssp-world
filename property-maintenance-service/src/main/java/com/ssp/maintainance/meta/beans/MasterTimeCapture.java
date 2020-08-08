package com.ssp.maintainance.meta.beans;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MasterTimeCapture {
	public static final String SEPERATOR = ":";
	private Map<String,TimeCapture> timeCapture = new HashMap<String,TimeCapture>();
	
	public void setStartTime(String objectKey, long startTime){
		TimeCapture tc = new TimeCapture();
		tc.setStartTime(startTime);
		timeCapture.put(objectKey, tc);
	}
	
	public void setEndTime(String objectKey, long endTime){
		TimeCapture tc = timeCapture.get(objectKey);
		if( tc != null ){
			tc.setEndTime(endTime);
		}
	}

	public String buildTimeStreamForService(){
		StringBuilder stringBuilder = new StringBuilder();
		
		Iterator<String> keyIter = timeCapture.keySet().iterator();
		
		String objectKey = null;
		TimeCapture tc = null;
		while( keyIter.hasNext()){
			objectKey = keyIter.next();
			tc = timeCapture.get(objectKey);
			stringBuilder.append(objectKey).append(MasterTimeCapture.SEPERATOR).append(new Timestamp(tc.getStartTime())).append(MasterTimeCapture.SEPERATOR).append(tc.getEndTime()-tc.getStartTime());
			if( keyIter.hasNext() ){
				stringBuilder.append(MasterControl.SEPERATOR);
			}
		}
		
		return stringBuilder.toString();
	}
	
	class TimeCapture{
		long startTime;
		long endTime;
		
		public long getStartTime() {
			return startTime;
		}
		public void setStartTime(long startTime) {
			this.startTime = startTime;
		}
		public long getEndTime() {
			return endTime;
		}
		public void setEndTime(long endTime) {
			this.endTime = endTime;
		}
		
	}
}
