package web_drivers;
import constants.ErrorMessage;
import lombok.Builder;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Properties;

@Getter
public class Driver {

    private WebDriver webDriver;
    private Properties props;


    @Builder
    public Driver(Properties props) {
        this.props = props;
        createWebDriver();
    }


    private void createWebDriver() {

        if (props.getProperty("driver").equals("Chrome")) {
            System.setProperty("webdriver.chrome.driver", props.getProperty("chrome_driver_path"));
            this.webDriver = new ChromeDriver();
            webDriver.manage().window().maximize();

        } else {
            throw new RuntimeException(ErrorMessage.E100.getMessage());
        }
    }
}
