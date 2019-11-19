import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class TestMail {

	public void send() throws AddressException, MessagingException {

	
			
		
		monitoringMail mail = new monitoringMail();
		mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, TestConfig.messageBody, TestConfig.attachmentPath, TestConfig.attachmentName);
		}
		}

