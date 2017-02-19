package backend.service;

import java.io.File;

public interface EmailService {

	void sendEmail(String subject, String content, String[] to, String[] cc, String[] bcc, File[] files, String[] fileNames);
	
}
