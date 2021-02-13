package com.in.ka.vatika;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {

	private Properties properties;
	private Session session;
	private String user;
	
	public static final String OWNER = "<Owner>";
	public static final String SITE = "<Site_No>";
	public static final String DATED = "<Date>";
	public static final String MF = "<MF>";
	public static final String CM = "<CM>";
	
	public static final String CF = "<CF>";
	
	public static final String DUE = "<Total>";
	
	private String mailTemplate; 
	
	private PrintWriter writer;
	
	public MailSender(final String user, final String password, String path, String mailTemplatePath) throws IOException {
		this.user = user;
		properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		/*properties.put("mail.smtp.user", "pvrowa@gmail.com");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.debug", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");*/

		
		
		File file = new File(mailTemplatePath);
		FileReader reader = new FileReader(file);
		char[] b = new char[(int)file.length()];
		reader.read(b);
		mailTemplate = new String(b);
		reader.close();
		
		File f = new File(path+"/logs/mailsent.log");
		writer = new PrintWriter(f);
		
System.out.println(user+"-"+password);

		//session=Session.getInstance(properties, new SMTPAuthenticator());
		//session.setDebug(true);

		session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator() {
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(user.trim(),
								password.trim());
					}
				});
	}
	
	protected void finalize(){
		writer.close();
	}
	
	private void logWriter(String logMessage){
		System.out.println(logMessage);
		writer.println(logMessage);
	}
	
	public void sendNotification(MailDataPojo mailDataPojo, String testMail, String testSites) throws Exception{
		if( testMail != null ){
		
			List<String> sites = Arrays.asList(testSites.split(","));
			if( testSites != null && sites.contains(mailDataPojo.getSite())){
				System.out.println("Site No:"+mailDataPojo.getSite());
				mailDataPojo.setEmail(testMail);
			}
			else{
				return;
			}
		}
		
		logWriter("Sending mail ....to :"+mailDataPojo.getEmail());
		System.out.println("Sending mail ....to :"+mailDataPojo.getEmail());
		
	    MimeMessage message = new MimeMessage(session);  
	    message.setFrom(new InternetAddress(user.trim()));  
	    message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailDataPojo.getEmail()));  
	    message.setSubject("PVROWA Communication for Site-No:"+mailDataPojo.getSite());  
	    
	    String messageStr = new String(mailTemplate);
	    messageStr = messageStr.replaceAll(MailSender.OWNER, mailDataPojo.getName() );
	    messageStr = messageStr.replaceAll(MailSender.SITE, mailDataPojo.getSite() );
	    messageStr = messageStr.replaceAll(MailSender.DATED, mailDataPojo.getDated() );
	    
	    logWriter(messageStr);
	    
	    //create MimeBodyPart object and set your message text     
	    BodyPart messageBodyPart1 = new MimeBodyPart();  
	    messageBodyPart1.setText(messageStr);  
	   
	    //create Multipart object and add MimeBodyPart objects to this object      
	    Multipart multipart = new MimeMultipart();  
	    multipart.addBodyPart(messageBodyPart1);  
	    
	  
	    //set the multiplart object to the message object  
	    message.setContent(multipart );  
	     
	    //send message  
	    Transport.send(message); 
	}
	
	public void onlyLogMessages(boolean notification, MailDataPojo mailDataPojo){
		String messageStr=null;
		
		if( notification ){
			messageStr = new String(mailTemplate);
		    messageStr = messageStr.replaceAll(MailSender.OWNER, mailDataPojo.getName() );
		    messageStr = messageStr.replaceAll(MailSender.SITE, mailDataPojo.getSite() );
		    messageStr = messageStr.replaceAll(MailSender.DATED, mailDataPojo.getDated() );
		}
		else{
			messageStr = new String(mailTemplate);
		    messageStr = messageStr.replaceAll(MailSender.OWNER, mailDataPojo.getName() );
		    messageStr = messageStr.replaceAll(MailSender.SITE, mailDataPojo.getSite() );
		    messageStr = messageStr.replaceAll(MailSender.DATED, mailDataPojo.getDated() );
		    messageStr = messageStr.replaceAll(MailSender.MF, String.format("%.1f",mailDataPojo.getMf()) );
		  
		    messageStr = messageStr.replaceAll(MailSender.CM, String.format("%.1f", mailDataPojo.getCm()) );
		    messageStr = messageStr.replaceAll(MailSender.CF, String.format("%.1f", mailDataPojo.getCf()) );
		    messageStr = messageStr.replaceAll(MailSender.DUE, String.format("%.1f",mailDataPojo.getDue()) );
		}
	    logWriter(messageStr);
	}
	
	public void sendMail(MailDataPojo mailDataPojo, String testMail, String testSites) throws Exception{
		try{  
			//rootFolder = rootFolder.substring(0,rootFolder.lastIndexOf("/"));
			if( mailDataPojo.getEmail() == null || "-".equals( mailDataPojo.getEmail() ) || "0".equals( mailDataPojo.getEmail()) ){
				System.out.println("E-mail is not provided for site_no:"+mailDataPojo.getSite());
				return;
			}//if
			
			if( testMail != null ){
				System.out.println("Site No:"+mailDataPojo.getSite());	
				List<String> sites = Arrays.asList(testSites.split(","));
				if( testSites != null && sites.contains(mailDataPojo.getSite())){
					mailDataPojo.setEmail(testMail);
				}
				else{
					return;
				}
				
			}
			
			if( mailDataPojo.getCf() <= 0 && mailDataPojo.getMf() <= 0){
				System.out.println("Fully paid Site No:"+mailDataPojo.getSite());
				return;
			}
			
			logWriter("Sending mail ....to :"+mailDataPojo.getEmail());
			System.out.println("Sending mail ....to :"+mailDataPojo.getEmail());
			
			if( mailDataPojo.getEmail().equals("-0") || mailDataPojo.getEmail().trim().length() == 0){
				System.out.println("Invalid email:"+mailDataPojo.getEmail());
				return;
			}
			
		    MimeMessage message = new MimeMessage(session);  
		    message.setFrom(new InternetAddress(user.trim()));  
		    message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailDataPojo.getEmail()));  
		    message.setSubject("PVROWA maintenance notice for Site-No:"+mailDataPojo.getSite());  
		    
		    String messageStr = new String(mailTemplate);
		    messageStr = messageStr.replaceAll(MailSender.OWNER, mailDataPojo.getName() );
		    messageStr = messageStr.replaceAll(MailSender.SITE, mailDataPojo.getSite() );
		    messageStr = messageStr.replaceAll(MailSender.DATED, mailDataPojo.getDated() );
		    messageStr = messageStr.replaceAll(MailSender.MF, String.format("%.1f",mailDataPojo.getMf()) );
		  
		    messageStr = messageStr.replaceAll(MailSender.CM, String.format("%.1f", mailDataPojo.getCm()) );
		    messageStr = messageStr.replaceAll(MailSender.CF, String.format("%.1f", mailDataPojo.getCf()) );
		    messageStr = messageStr.replaceAll(MailSender.DUE, String.format("%.1f",mailDataPojo.getDue()) );
			
		    logWriter(messageStr);
		    
		    //3) create MimeBodyPart object and set your message text     
		    BodyPart messageBodyPart1 = new MimeBodyPart();  
		    messageBodyPart1.setText(messageStr);  
		    
		 
		    /*
		    //4) create new MimeBodyPart object and set DataHandler object to this object      
		    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
		  
		    String filename2 = "corpus_siteno_"+maintenancePojo.getSiteNo()+".pdf";//change accordingly  
		    DataSource source2 = new FileDataSource(rootFolder+"/output/corpus_siteno_"+maintenancePojo.getSiteNo()+".pdf");  
		    messageBodyPart2.setDataHandler(new DataHandler(source2));  
		    messageBodyPart2.setFileName(filename2);  
		    
		   //5) create new MimeBodyPart object and set DataHandler object to this object      
		    MimeBodyPart messageBodyPart3 = new MimeBodyPart(); 		  
		    String filename3 = "maintenance_siteno_"+maintenancePojo.getSiteNo()+".pdf";//change accordingly  
		    DataSource source3 = new FileDataSource(rootFolder+"/output/maintenance_siteno_"+maintenancePojo.getSiteNo()+".pdf");  
		    messageBodyPart3.setDataHandler(new DataHandler(source3));  
		    messageBodyPart3.setFileName(filename3); 
		     
		    //5) create new MimeBodyPart object and set DataHandler object to this object      
		    MimeBodyPart messageBodyPart4 = new MimeBodyPart(); 		  
		    String filename4 = "membership_form.docx";//change accordingly  
		    DataSource source4 = new FileDataSource(rootFolder+"/resource/pvrowamaintenancenotice/membership_form.docx");  
		    messageBodyPart4.setDataHandler(new DataHandler(source4));  
		    messageBodyPart4.setFileName(filename4); 
		    
		    //6) create new MimeBodyPart object and set DataHandler object to this object      
		    MimeBodyPart messageBodyPart5 = new MimeBodyPart(); 		  
		    String filename5 = "Why_PVROWA.pdf";//change accordingly  
		    DataSource source5 = new FileDataSource(rootFolder+"/resource/pvrowamaintenancenotice/Why_PVROWA.pdf");  
		    messageBodyPart5.setDataHandler(new DataHandler(source5));  
		    messageBodyPart5.setFileName(filename5); 
		    */
		     
		    //5) create Multipart object and add MimeBodyPart objects to this object      
		    Multipart multipart = new MimeMultipart();  
		    multipart.addBodyPart(messageBodyPart1);  
		    //multipart.addBodyPart(messageBodyPart2);  
		    //multipart.addBodyPart(messageBodyPart3); 
		    //multipart.addBodyPart(messageBodyPart4); 
		    //multipart.addBodyPart(messageBodyPart5); 
		  
		    //6) set the multiplart object to the message object  
		    message.setContent(multipart );  
		     
		    //Transport transport = session.getTransport("smtps");
		    //transport.connect("smtp.gmail.com", 465, "pvrowa@gmail.com", "vatica@123");
		    //transport.sendMessage(message, message.getAllRecipients());
		    //transport.close();
		    
		    //7) send message  
		    Transport.send(message);  	   
		   
		}
		catch(Exception e){
			throw e;
		}
		
		logWriter("Mail sent ....");
		logWriter("-------------------------------------------------------------------------------------------------------------------------------");
	}	
	
	private class SMTPAuthenticator extends Authenticator
	{
	    public PasswordAuthentication getPasswordAuthentication()
	    {
	        return new PasswordAuthentication("pvrowa@gmail.com", "vatica@123");
	    }
	}

}
