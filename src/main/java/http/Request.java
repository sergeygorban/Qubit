package http;

public interface Request {

    int getResponseCode();
    String getCookieValue(String cookieName);

    default void expect(long millis) {

        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
