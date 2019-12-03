package selenium;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface Waiting {


    static void expect(long timeOutInMillis) {
        try {
            Thread.sleep(timeOutInMillis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default WebDriverWait waiting() {
        return new WebDriverWait(Instances.INSTANCE.getWebDriver(), 15);
    }
}
