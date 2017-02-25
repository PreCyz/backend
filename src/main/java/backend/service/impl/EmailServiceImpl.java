package backend.service.impl;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import backend.exception.DAOException;
import backend.service.EmailService;

public class EmailServiceImpl implements EmailService {
	
	private static final Log logger = LogFactory.getLog(EmailServiceImpl.class);

	private String smtpHost;
	private String defaultFrom;
	
	public EmailServiceImpl(String smtpHost, String defaultFrom) {
		this.smtpHost = smtpHost;
		this.defaultFrom = defaultFrom;
	}

	public EmailServiceImpl() {
		MailcapCommandMap mc = (MailcapCommandMap)CommandMap.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mc);
	}
	
	public void sendEmail(String subject, String content, String[] to, String[] cc, String[] bcc, File[] files, String[] fileNames) {
		try {
			
			Properties mailProps = new Properties();
			mailProps.setProperty("mail.smtp.host", smtpHost);
			
			MimeMessage msg = new MimeMessage(Session.getInstance(mailProps));
			
			//Set the message details
			msg.setFrom(new InternetAddress(defaultFrom));
			msg.setSubject(subject,"utf-8");
			msg.setSentDate(new Date());
			
			// Set the recipients
			if (to != null)
				msg.setRecipients(Message.RecipientType.TO, getAddressArray(to));
			if (cc != null)
				msg.setRecipients(Message.RecipientType.CC, getAddressArray(cc));
			if (bcc != null)
				msg.setRecipients(Message.RecipientType.BCC, getAddressArray(bcc));
			
			Multipart multipart = new MimeMultipart();
			
			// create the message part 
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(content, "text/html; charset=utf-8");
			multipart.addBodyPart(messageBodyPart);

			if (files != null) {
				for (int i = 0; i < files.length; ++i) {
					File file = files[i];
					if (!file.exists()) {
						throw new DAOException("File for email attachement not exists under path: " + file.getAbsolutePath());
					}
					// Part two is attachment
					messageBodyPart = new MimeBodyPart();
					DataSource source =  new FileDataSource(file);
					messageBodyPart.setDataHandler( new DataHandler(source));
					messageBodyPart.setFileName(fileNames[i]);
					multipart.addBodyPart(messageBodyPart);
				}
			}

			msg.setContent(multipart);
			
			Transport.send(msg);
			
			
		} catch (Exception e) {
			logger.error("Error sending message to " + to[0], e);
			throw new DAOException("Error sending message to " + to[0], e);
		}
	}

	private static Address[] getAddressArray(String[] to) {
		Address[] addresses = new Address[to.length]; 
		
		for(int i = 0; i < to.length; ++i) {
			try {
				addresses[i] = new InternetAddress(to[i]);
			} catch (AddressException e) {
				throw new RuntimeException("incorrect address", e);
			}
		}
		
		return addresses;
	}
	
}
