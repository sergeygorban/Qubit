package excel;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Created by Admin on 16.04.2018.
 */
public class SendEmail {

    private String host = "smtp.gmail.com";
    private String port = "587";
    private String username = "sergey.gorban.cc@gmail.com";
    private String password = "zxczxc19867150";
    private String to = "sergey.gorban@privatbank.ua";

    public void sendEmail(Path filePath) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Test Cases");

            MimeBodyPart mimeBodyPart1 = new MimeBodyPart();
            mimeBodyPart1.setContent("Hello, this is a new Test Cases", "text/plain");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart1);

            MimeBodyPart mimeBodyPart2 = new MimeBodyPart();
            mimeBodyPart2.setDataHandler(new DataHandler(new FileDataSource(filePath.toFile())));
            mimeBodyPart2.setFileName(filePath.getFileName().toString());
            multipart.addBodyPart(mimeBodyPart2);

            message.setContent(multipart);
            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
