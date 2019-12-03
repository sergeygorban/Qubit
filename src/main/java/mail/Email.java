package mail;

import lombok.Builder;

import javax.mail.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Email {

    private Store store;
    private Folder emailFolder;

    @Builder
    public Email(String email, String password) {
        this.store = createStore(email, password);
    }

    public List<Message> getAllMessages() {

        //Create the folder object
        try {
            this.emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);
            return Arrays.stream(emailFolder.getMessages()).collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteAllMessages() {

        getAllMessages().forEach(message -> {

            try {
                message.setFlag(Flags.Flag.DELETED, true);
                emailFolder.close(true);

            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }




    private Store createStore(String email, String password) {

        Properties properties = new Properties();
        properties.put("mail.pop3.host", "imap.ukr.net");
        properties.put("mail.pop3.port", "993");
        properties.put("mail.pop3.startssl.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);

        //create the POP3 store object and connect with the pop server
        Store store = null;
        try {
            store = emailSession.getStore("imap");
            store.connect("imap.ukr.net", email, password);
            return store;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
