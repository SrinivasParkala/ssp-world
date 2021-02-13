package com.in.ka.vatika;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class BalanceReportGenerator {
	
	private final String SKIP_SEND = "skipAlreadySendSite";
	private final String CORPUS_TERMS = "no_of_corpus_terms";
	private final String CURRENT_FY_YEAR = "FY";
	private final String MAILID = "mail_id";
	private final String PASSWORD = "password";
	private final String SENDMAIL = "sendEmail";
	private final String TESTMAIL = "testMail";
	private final String TESTSITES = "testSites";
	private final String MAIL_TEMPLATE = "mailTemplate";
	private final String SEND_NOTIFICATION = "sendNoticationMail";
			
	private String[] defaultHeader = {"Plot","Site_No","Name","Owner","Some","Email","Contact"};
	private String[] missingHeader = {"Site_No","Dimension","Sqr-ft"};
	private String[] summaryHeader = {"Site_No","Name","Dimension","Resident","Email","Mobile","Yearly Maintainence"};
	private String[] billingHeader = {"Site_No","Name","Dimension","Resident","Email","Mobile","Yearly Maintainence",
											"MF_Paid_2017-18","MF_Paid_2018-19","MF_Paid_2019-20","MF_Paid_2020-21","Corpus_Paid","MF_Pending_TillDate","CF_Pending_TillDate",
											 "Total_Due"};
	
	private final int TANKER_COST=300;
	private final int NO_OF_TANKERS=4;
	private final int GARBAGE_COST=250;
	private final float CLEANING_COST = 0.67f;
	private final int BASIC_COST = 4976;
	private final int SW_COST = 300;
	private final int MEM_COST = 500;
	
	private int MOBILE = 0;
	private int MAIL = 1;
	private int MOBILE_MAIL = 2;
	private int MOBILE_OR_MAIL = 3;
	
	private String DATE = "Date";
	
	private int CURRENT_YEAR;
	private int MF_NUMBER_OF_TERMS;
	private int CF_NUMBER_OF_TERMS;
	private int CORPUS_FUND = 50000;
	private int P_CORPUS_FUND = 5000;
	
	private String MISSING_CELL = "-";
	private String MISSING_CELL_1 = "-0";
	
	private String path;
	private Map<String, String[]> masterData;
	private Map<String, String[]> paymentDetails;
	private Map<String, String[]> siteDetails;
	private Properties properties;
	
	public BalanceReportGenerator(String propertyFile) throws Exception{
		
		properties = new Properties();
		
		properties.load(new FileInputStream(propertyFile+"/conf/pvrowa.properties"));
		
		CF_NUMBER_OF_TERMS = Integer.valueOf(properties.getProperty(CORPUS_TERMS));
		CURRENT_YEAR = Integer.valueOf(properties.getProperty(CURRENT_FY_YEAR));
		MF_NUMBER_OF_TERMS = CURRENT_YEAR - 2016;
		
		boolean skipSend = false;
		
		try{
			skipSend = Boolean.valueOf(properties.getProperty(SKIP_SEND));
		}
		catch(Exception e){
			//Do nothing
		}
		
		
		this.path = propertyFile;
		
		masterData = readFileToMemory("Vatika_Owners_MasterData.csv", 1);
		paymentDetails = readFileToMemory("Unit_Wise_Balance.csv", 1);
		siteDetails = readFileToMemory("Vatika_Site_Dimension.csv", 2);
		
		printMasterWithMissingData(MOBILE,"Records_with_missing_mobile.csv");
		printMasterWithMissingData(MAIL,"Records_with_missing_email.csv");
		printMasterWithMissingData(MOBILE_MAIL,"Records_with_missing_mobile_and_mail.csv");
		printMasterWithMissingData(MOBILE_OR_MAIL,"Records_with_missing_mobile_or_mail.csv");
		
		printUnrechableContacts("Records_unreachable.csv");
		printMissingMasterData("Records_missing_in_master.csv");
		printMissingBillingData("Records_missing_in_billing.csv");
		
		printMergedRecords("Complete_Summary.csv");
		
		File file = new File(path+"/logs/mailSendReport.log");
		
		Map<String,String> mailLog = new TreeMap<String,String>();
		if( skipSend ){
			mailLog= readFileToMemory(file);
		}
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(file), "utf-8"));
		
		printBillingAndSendSummary("Complete_Billing_Summary.csv", mailLog, writer);
		
	}
	
	private void printBillingAndSendSummary(String fileName, Map<String,String> mailLog, BufferedWriter lwriter) throws Exception{
		File file = new File(path+"/reports/"+fileName);
		PrintWriter writer = new PrintWriter(file);
		PrintWriter writer1 = new PrintWriter(new File(path+"/reports/totalDefaulter.csv"));
		
		Iterator<String> keys = masterData.keySet().iterator();
		String key = null;
		String[] masterDetail = null;
		String[] billDetail = null;
		double maintainence = 0;
		
		double pcf, pmf, tmf, mf_17, mf_18, mf_19, mf_20, cf = 0;
		boolean resident = false;
		saveSummaryHeader(billingHeader, writer);
		saveSummaryHeader(defaultHeader, writer1);
				
		MailDataPojo mailDataPojo = null;
		
		String mailId = properties.getProperty(MAILID);
		String password = properties.getProperty(PASSWORD);
		String sendMail = properties.getProperty(SENDMAIL);
		String testMail = properties.getProperty(TESTMAIL);
		String testSites = properties.getProperty(TESTSITES);
		String mailTemplate = properties.getProperty(MAIL_TEMPLATE);
		
		String sendNotification = properties.getProperty(SEND_NOTIFICATION);
		boolean sendNotificationOnly = false;
		
		try {
			sendNotificationOnly = Boolean.valueOf(sendNotification);
		}
		catch(Exception e) {
			//Do nothing
		}
		
		MailSender mailSender = new MailSender(mailId, password, path, path+"/conf/"+mailTemplate);
				
		try{
			while( keys.hasNext() ){
				key = keys.next();
				
				if( mailLog.containsKey(key) ){
					System.out.println("Skipping site:"+key);
					lwriter.write(key+",Processed\n");
					continue;
				}
				
				masterDetail = masterData.get(key);
				billDetail = paymentDetails.get(key);
						
				if( billDetail == null ){	
					System.out.println("Missing Billing details for:"+key);
					continue;
				}
				
				try{
					mf_17 = Double.valueOf(billDetail[4]);
				}
				catch(NumberFormatException nfe){
					mf_17 = 0;
				}
				
				try{
					mf_18 = Double.valueOf(billDetail[5]);
				}
				catch(NumberFormatException nfe){
					mf_18 = 0;
				}
				
				try{
					mf_19 = Double.valueOf(billDetail[6]);
				}
				catch(NumberFormatException nfe){
					mf_19 = 0;
				}
				
				try{
					mf_20 = Double.valueOf(billDetail[7]);
				}
				catch(NumberFormatException nfe){
					mf_20 = 0;
				}
				
				try{
					cf = Double.valueOf(billDetail[8]);
				}
				catch(NumberFormatException nfe){
					cf = 0;
				}
				
				tmf = mf_17+mf_18+mf_19+mf_20;
							
				try{
					Double.valueOf(getSiteDimension(key));
				}
				catch(NullPointerException npe){
					System.out.println("Site Details Missing for:"+key);
					continue;
				}
				maintainence = computeMaintainenceDueForSite( masterDetail[4], key);

				pmf = maintainence - tmf;
				pcf = 0;
				
			
				if( billDetail[9] != null && billDetail[9].toLowerCase().contains("yes")){
					pcf = 0;
				}
				else{
					pcf =  CORPUS_FUND - cf;
				}
	
				if( mf_17 ==0 && mf_18==0 && mf_19==0 && mf_20 ==0 && cf==0 ){
					System.out.println("People who has not paid single installalment:"+key);
					
					for(String d : masterDetail ){
						writer1.print(d+",");
					}
					writer1.println();
				}
				
				mailDataPojo = saveRecord(key, mf_17, mf_18, mf_19, mf_20, cf, pmf, pcf, writer);
				
				if( sendNotificationOnly && sendMail.equals("true")) {
					mailSender.sendNotification(mailDataPojo, testMail, testSites);
				}
				else if( !masterDetail[7].toLowerCase().equals("yes") && sendMail.equals("true") ){
					mailSender.sendMail(mailDataPojo, testMail, testSites);
				}
				else{
					mailSender.onlyLogMessages(sendNotificationOnly, mailDataPojo);
				}
				
				lwriter.write(key+",Processed\n");
			}
			
		}
		catch(Exception e){
			System.out.println("Failed while working in site no:"+key);
			throw e;
		}
		finally{
			writer.close();
			writer1.close();
			lwriter.close();
		}
	}
	
	private void printMergedRecords(String fileName) throws FileNotFoundException{
		File file = new File(path+"/reports/"+fileName);
		PrintWriter writer = new PrintWriter(file);
		
		Iterator<String> keys = masterData.keySet().iterator();
		String key;
		saveSummaryHeader(summaryHeader, writer);
		while( keys.hasNext() ){
			key = keys.next();
			saveRecord(key, writer);
		}
		writer.close();
	}
	
	private void printUnrechableContacts(String fileName) throws FileNotFoundException{
		File file = new File(path+"/reports/"+fileName);
		PrintWriter writer = new PrintWriter(file);
		
		Iterator<String> keys = siteDetails.keySet().iterator();
		String[] data = null;
		String key;
		
		saveSummaryHeader(missingHeader, writer);
		while( keys.hasNext() ){
			key = keys.next();
			if( masterData.containsKey(key)){
				continue;
			}
			else{
				data = siteDetails.get(key);
				writer.print(data[2]);
				writer.print(",");
				writer.print(data[3]);
				writer.print(",");
				writer.print(data[4]);
				writer.println();
			}
		}//while
		writer.close();
	
	}
	
	private void printMissingMasterData(String fileName) throws FileNotFoundException{
		File file = new File(path+"/reports/"+fileName);
		PrintWriter writer = new PrintWriter(file);
		
		Iterator<String> keys = paymentDetails.keySet().iterator();
		String key;
		
		writer.println(summaryHeader[0]);
		while( keys.hasNext() ){
			key = keys.next();
			if( masterData.containsKey(key)){
				continue;
			}
			else{
				writer.println(key);
			}
		}//while
		writer.close();
	
	}
	
	private void printMissingBillingData(String fileName) throws FileNotFoundException{
		File file = new File(path+"/reports/"+fileName);
		PrintWriter writer = new PrintWriter(file);
		
		Iterator<String> keys = masterData.keySet().iterator();
		String key;
		saveSummaryHeader(summaryHeader, writer);
		
		while( keys.hasNext() ){
			key = keys.next();
			if( paymentDetails.containsKey(key)){
				continue;
			}
			else{
				saveRecord(key, writer);
			}
		}//while
		writer.close();
	}
	
	private void printMasterWithMissingData(int missing, String fileName) throws FileNotFoundException{
		Iterator<String> keys = masterData.keySet().iterator();
		String key;
		String[] data;
		int count = 0;
		
		File file = new File(path+"/reports/"+fileName);
		PrintWriter writer = new PrintWriter(file);
		saveSummaryHeader(summaryHeader, writer);
		
		while( keys.hasNext() ){
			key = keys.next();
			data = masterData.get(key);
			
			if( MOBILE == missing ){
				if( data[6].trim().equals(MISSING_CELL) ||  data[6].trim().equals(MISSING_CELL_1) ){
					count++;
					saveRecord(key, writer);
				}
			}
			else if( MAIL == missing ){
				if( data[5].trim().equals(MISSING_CELL) || data[5].trim().equals(MISSING_CELL_1) ){
					count++;
					saveRecord(key, writer);
				}
			}
			else if( MOBILE_MAIL == missing ){
				if( (data[5].trim().equals(MISSING_CELL) && data[6].trim().equals(MISSING_CELL)) || (data[5].trim().equals(MISSING_CELL_1) && data[6].trim().equals(MISSING_CELL_1))){
					count++;
					saveRecord(key, writer);
				}
			}
			else if( MOBILE_OR_MAIL == missing ){
				if( data[5].trim().equals(MISSING_CELL) || data[6].trim().equals(MISSING_CELL) || data[5].trim().equals(MISSING_CELL_1) || data[6].trim().equals(MISSING_CELL_1)){
					count++;
					saveRecord(key, writer);
				}
			}
			
		}//while
		writer.close();
	}
	
	private void saveSummaryHeader(String[] header, PrintWriter writer){
		int len = header.length;
		int count = 0;
		for(String data : header){
			count++;
			if( count != len )
				writer.print(data+",");
			else
				writer.print(data);
		}
		writer.println();
	}
	
	private MailDataPojo saveRecord(String key, double mf_17, double mf_18, double mf_19, double mf_20, double cf, double pmf, double pcf, PrintWriter writer){
		String[] data = new String[billingHeader.length];
		String[] masterDetail = masterData.get(key);
		
		if( masterDetail == null ){
			System.out.println("Missing Data for:"+key);
		}
		
		try{
			getSiteDimension(key);
		}
		catch(Exception e){
			//System.out.println("Missing Data for site:"+key);
			return null;
		}
		
		data[0] = masterDetail[1];//Site_No
		data[1] = masterDetail[2];//Site_Name
		data[2] = String.valueOf(getSiteDimension(key));//Site_Dimension
		data[3] = masterDetail[4];//Site_Resident
		data[4] = masterDetail[5];//Email
		data[5] = masterDetail[6];//Mobile
		
		boolean resident = true;
		if( data[3].toLowerCase().contains("no")){
			resident = false;
		}
		
		float mf = computeMaintainenceForSite(resident, key);
		
		data[6] = String.valueOf(mf);//Yearly Maintainence
		
		data[7] = String.valueOf(mf_17);//MF_Paid_2017-18
		data[8] = String.valueOf(mf_18);//MF_Paid_2018-19
		data[9] = String.valueOf(mf_19);//MF_Paid_2019-20
		data[10] = String.valueOf(mf_20);//MF_Paid_2020-21
		data[11] = String.valueOf(cf);//Corpus_Paid
		data[12] = String.valueOf(pmf);//MF_Pending_TillDate
		data[13] = String.valueOf(pcf);//CF_Pending_TillDate
		data[14] = String.valueOf(pmf+pcf);//Total_Due
	
		MailDataPojo mailDataPojo = createMailingObj(data);		
		saveRecord(data,writer);
		
		return mailDataPojo;
	}
	
	private MailDataPojo createMailingObj(String[] data){
		MailDataPojo mailDataPojo = new MailDataPojo();
		
		String date = properties.getProperty(DATE);
		
		if( date == null || (date != null && date.trim().length() ==0 ) ) {
			date = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toString();
			date = date.substring(0, 10);
		}
		
		mailDataPojo.setName(data[1]);
		mailDataPojo.setSite(data[0]);
		mailDataPojo.setEmail(data[4]);
		mailDataPojo.setDated(date);
		
		if( Float.valueOf(data[12]) > 100 ){
			mailDataPojo.setMf(Float.valueOf(data[12]));
		}
		else{
			mailDataPojo.setMf(0);
		}
		
		if( Float.valueOf(data[10]) == 0 ){
			mailDataPojo.setCm(Float.valueOf(data[6]));
		}
		
		double cf = Double.valueOf(data[13]);
		mailDataPojo.setCf((float)cf);
		/*if( cf != 0 ){//cf <= CORPUS_ONE_TIME_FUND || cf <= CORPUS_FUND ){		
			mailDataPojo.setCf(Float.valueOf(((P_CORPUS_FUND*(CF_NUMBER_OF_TERMS)) - Float.valueOf(data[11]))));
		}
		else{
			mailDataPojo.setCf(0);
		}*/
		
		Float adjDue = Float.valueOf(data[14]);
			
		mailDataPojo.setDue(Float.valueOf(adjDue));
			
		return mailDataPojo;
	}
	
	private void saveRecord(String key, PrintWriter writer){
		String[] data = new String[summaryHeader.length];
		String[] masterDetail = masterData.get(key);
		
		if( masterDetail == null ){
			System.out.println("Missing Data for:"+key);
		}
		
		try{
			getSiteDimension(key);
		}
		catch(Exception e){
			System.out.println("Missing Data for site:"+key);
			return;
		}
		
		data[0] = masterDetail[1];
		data[1] = masterDetail[2];
		data[2] = String.valueOf(getSiteDimension(key));
		data[3] = masterDetail[4];
		data[4] = masterDetail[5];
		data[5] = masterDetail[6];
		
		boolean resident = true;
		if( data[3].toLowerCase().contains("no")){
			resident = false;
		}
		data[6] = String.valueOf(computeMaintainenceForSite(resident, key));
		saveRecord(data,writer);
	}
	
	private void saveRecord(String[] record, PrintWriter writer){
		int len = record.length;
		int count = 0;
		for(String data : record){
			count++;
			if( count != len )
				writer.print(data+",");
			else
				writer.print(data);
		}
		writer.println();
	}
	
	private void printRecord(String[] record){
		for(String data : record){
			System.out.print(data+":");
		}
		System.out.println(record.length);
	}
	
	private Map<String, String> readFileToMemory(File fileName) throws IOException{
		
		
		if( !fileName.exists() ){
			return new TreeMap<String,String>();
		}
		Map<String, String> cache = new TreeMap<String,String>();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		
		String line = null;
		String[] sarray = null;
		boolean skipheader = false;
		
		while( (line = reader.readLine()) != null ){
			
			if( skipheader ){
				skipheader = false;
				continue;
			}//if
			
			sarray = line.split(",");
			
			String key = removeBlanks(sarray[0].trim());
			cache.put(key, sarray[1]);
		}
		reader.close();
		
		return cache;
	}
		
	private Map<String, String[]> readFileToMemory(String fileName, int keyIndex) throws IOException{
		Map<String, String[]> cache = new TreeMap<String,String[]>();
		File file = new File(path+"/masterdata/"+fileName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String line = null;
		String[] sarray = null;
		boolean skipheader = true;
		
		while( (line = reader.readLine()) != null ){
			
			if( skipheader ){
				skipheader = false;
				continue;
			}//if
			
			sarray = line.split(",");
			
			if( !"Plots".equals(sarray[0].trim()) || sarray.length < keyIndex || "".equals(sarray[keyIndex].trim()) ){
				continue;
			}
			String key = removeBlanks(sarray[keyIndex].trim());
			cache.put(key, sarray);
		}
		reader.close();
		
		return cache;
	}
	
	public static String removeBlanks(String value){
		while( value.indexOf(" ") != -1 ){
			value = value.replace(" ", "");
		}
		return value.toLowerCase();
	}
	
	public String getSiteDimension(String sNo){
		String siteNo = BalanceReportGenerator.removeBlanks(sNo);
		String[] maintenancePojo = siteDetails.get(siteNo);
		return maintenancePojo[4];
	}
	
	public float computeMaintainenceForSite(boolean resident, String sNo){
		float total = 0;
		String siteNo = BalanceReportGenerator.removeBlanks(sNo);
		String[] maintenancePojo = siteDetails.get(siteNo);
		
		if( !resident ){
			double dimension = Double.valueOf(maintenancePojo[4]);
			float cleanCost = (float)(CLEANING_COST*dimension);
			total = BASIC_COST+cleanCost+SW_COST+MEM_COST;
		}
		else{
			float garbageCost = (float)(GARBAGE_COST*12);
			float waterCost = (float)(TANKER_COST*NO_OF_TANKERS*12);
			total = total+BASIC_COST+waterCost+garbageCost+SW_COST+MEM_COST;
		}
		
		return total;
	}
	
	public float computeMaintainenceDueForSite(String residentSince, String sNo){
		float total = 0;
		String siteNo = BalanceReportGenerator.removeBlanks(sNo);
		String[] maintenancePojo = siteDetails.get(siteNo);
		
		if( residentSince.toLowerCase().equals("no") ){
			double dimension = Double.valueOf(maintenancePojo[4]);
			float cleanCost = (float)(CLEANING_COST*dimension);
			total = BASIC_COST+cleanCost+SW_COST+MEM_COST;
			
			total = total*MF_NUMBER_OF_TERMS;
		}
		else{
			
			int residingFrom = Integer.valueOf(residentSince);
			int terms = CURRENT_YEAR - residingFrom + 1;
			
			double dimension = Double.valueOf(maintenancePojo[4]);
			float cleanCost = (float)(CLEANING_COST*dimension);
			total = BASIC_COST+cleanCost+SW_COST+MEM_COST;
			total = total*(MF_NUMBER_OF_TERMS-terms);

			float garbageCost = (float)(GARBAGE_COST*12);
			float waterCost = (float)(TANKER_COST*NO_OF_TANKERS*12);
			total = total+(BASIC_COST+waterCost+garbageCost+SW_COST+MEM_COST)*(terms);
			
		}
		
		return total;
	}
	
	public static void main(String[] args){
		try {
			BalanceReportGenerator balanceReportGenerator = new BalanceReportGenerator(args[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
