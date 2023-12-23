package car;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EMAIL {
	
	        public static boolean sendEmail(String to, String subject, String body) {
	        	boolean flag=false;
	        final String username = "carsoftware49@gmail.com"; 
	        final String kk = "hlpv fxrk fxtj ydtu"; 

	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com"); 
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.ssl.trust", "*");
	        Session session = Session.getInstance(props,
	                new Authenticator() {
	                    @Override
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, kk);
	                    }
	                });

	        try {
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	            message.setSubject(subject);
	            message.setText(body);

	            Transport.send(message);
	            flag=true;
	           
	        }
	        catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	        return flag;
	    }
	}
