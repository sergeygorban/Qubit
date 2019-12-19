package mail;

import lombok.Builder;
import lombok.extern.java.Log;

import javax.mail.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
public class Email {

    private Store store;
    private Folder emailFolder;

    @Builder
    public Email(String email, String password) {
        this.store = createStore(email, password);
    }

    public List<Message> getAllMessages() {

        try {
            emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);
            return Arrays.stream(emailFolder.getMessages()).collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteAllMessages() {
        try {
            getAllMessages().forEach(message -> {
                try {
                    message.setFlag(Flags.Flag.DELETED, true);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            });
            emailFolder.close(true);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public boolean isMessageReceived() {

        LocalDateTime start = LocalDateTime.now();
        Stream.generate(this::getAllMessages)
                .peek(messages -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .takeWhile(list -> list.size() == 0 )
                .takeWhile(webElement -> Duration.between(start, LocalDateTime.now()).toSeconds() < 180)
                .forEach(webElement -> {});

        return getAllMessages().size() > 0;
    }

    public boolean isMessageReceived(String messageName) {

        LocalDateTime start = LocalDateTime.now();

        Stream.generate(this::getAllMessages)
                .peek(messages -> {
                    try {
                        log.info("Waiting letter");
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .takeWhile(list -> list.stream()
                        .filter(message -> {
                            try {
                                return message.getSubject().equals(messageName);
                            } catch (Exception e) {
                                throw new RuntimeException(e.getMessage());
                            }
                        }).collect(Collectors.toList()).size() == 0)
                .takeWhile(webElement -> Duration.between(start, LocalDateTime.now()).toSeconds() < 180)
                .forEach(messages -> {});

        return getAllMessages().size() > 0;
    }




    private Store createStore(String email, String password) {

        Properties properties = new Properties();
        properties.put("mail.pop3.host", "imap.ukr.net");
        properties.put("mail.pop3.port", "993");
        properties.put("mail.pop3.startssl.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);

        try {
            store = emailSession.getStore("imap");
            store.connect("imap.ukr.net", email, password);
            return store;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
