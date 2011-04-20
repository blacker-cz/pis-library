/*
 */
package org.fit.pis.library.back;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Mailing class
 * @author Lukáš Černý <cerny.l@gmail.com>
 */
public class Mailer {

	/**
	 * Public constructor
	 */
	public Mailer() {
	}

	/**
	 * Send email
	 * @param to		Recipient
	 * @param subject	Subject
	 * @param body		Body
	 * @return true if email was sent; false otherwise
	 */
	public Boolean send(String to, String subject, String body) {

		Session session = Session.getDefaultInstance(new Properties(), null);
		MimeMessage message = new MimeMessage(session);
		
		try {
			message.setFrom( new InternetAddress("no-reply@localhost") );
			message.addRecipient(
					Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} catch (MessagingException ex) {
			return false;
		}
		
		return true;
	}
}
