import jna.Mouse;
import org.apache.commons.lang3.RandomUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.stream.Stream;


public class Main {

    public static void main(String[] args) throws GeneralSecurityException, IOException, MessagingException {


        Stream.iterate(0, n -> n + 1).forEach(n -> {

            Mouse.mouseLeftClick();
            try {
                Thread.sleep(RandomUtils.nextInt(7000, 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });













/*        Gmail gmail = new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), new Auth().getCredentialForGmail(Const.PATH_TO_SERVICE_ACCOUNT_KEY.getValue())).build();

        Main.sendMessage(gmail, "alprtest2@ukr.net", Main.createEmail("alprtest2@ukr.net", "alprtest@gmail.com", "1111", "2222"));*/


/*
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");


        Session session = Session.getInstance(properties, null);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom("alprtest@gmail.com");
            message.setRecipients(javax.mail.Message.RecipientType.TO, "alprtest2@ukr.net");
            message.setSubject("Test sending messages");
            //message.setSentDate(new Date());
            message.setText("Hello! It is test message");

            // Создать пароль для приложения Гугл
            Transport.send(message, "alprtest@gmail.com", "nhxziwufhhwksgfy");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
*/


/*        // Укр нет

        try { properties.put("mail.pop3.host", "imap.ukr.net");
        properties.put("mail.pop3.port", "993");
        properties.put("mail.pop3.startssl.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);

        //create the POP3 store object and connect with the pop server
        Store store = emailSession.getStore("imap");

        store.connect("imap.ukr.net", "alprtest2@ukr.net", "zxc123456789zxc");

        //create the folder object and open it
        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_ONLY);

        // retrieve the messages from the folder in an array and print it
        javax.mail.Message[] messages = emailFolder.getMessages();
        System.out.println("messages.length---" + messages.length);

        for (int i = 0, n = messages.length; i < n; i++) {
            Message message = messages[i];
            System.out.println("---------------------------------");
            System.out.println("Email Number " + (i + 1));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("Text: " + message.getContent().toString());

        }

        //close the store and folder objects
        emailFolder.close(false);
        store.close();

    } catch (NoSuchProviderException e) {
        e.printStackTrace();
    } catch (MessagingException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }*/


     /*   try {

            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imaps.host", "imap.gmail.com");
            properties.put("mail.imaps.port", "993");
            properties.put("mail.imaps.startssl.enable", "true");
            //properties.setProperty("mail.imap.connectiontimeout", "50000");
            //properties.setProperty("mail.imap.timeout", "50000");
            Session emailSession = Session.getDefaultInstance(properties);


            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("imaps");

            store.connect("imap.googlemail.com","alprtest@gmail.com", "nhxziwufhhwksgfy");

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);


            // retrieve the messages from the folder in an array and print it
            javax.mail.Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());
                System.out.println();

            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
}

/*
        GoogleCredential googleCredential = new GoogleCredential.Builder()
                .setTransport(GoogleNetHttpTransport.newTrustedTransport())
                .setJsonFactory(JacksonFactory.getDefaultInstance())
                .setServiceAccountId("test-860@magnetic-rite-225819.iam.gserviceaccount.com")
                .setServiceAccountPrivateKeyFromP12File(new File("D:\\Git\\Modules\\src\\main\\java\\google\\magnetic-rite-225819-62f90087a0c6.p12"))
                .setServiceAccountScopes(Arrays.asList(GmailScopes.MAIL_GOOGLE_COM))
                .setServiceAccountUser("alprtest@gmail.com").build()

        googleCredential.refreshToken();
*/




/*        final String CREDENTIALS_FILE_PATH = "/credentials.json";

        InputStream in = Main.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets,  Arrays.asList(GmailScopes.MAIL_GOOGLE_COM))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");


        Gmail gmail = new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential).build();


        MimeMessage email;

        try {
            email = new MimeMessage(Session.getDefaultInstance(new Properties(), null));
            email.setFrom("alprtest@gmail.com");
            email.setRecipients(javax.mail.Message.RecipientType.TO, "alprtest2@ukr.net");
            email.setSubject("Test sending messages");
            email.setText("Hello! It is test message");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);

        gmail.users().messages().send("me", message).execute();

        dzgdfhg*/

}
