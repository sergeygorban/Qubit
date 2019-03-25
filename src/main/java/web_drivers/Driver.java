package web_drivers;

import constants.ErrorMessage;
import lombok.Builder;
import lombok.Getter;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Properties;

@Getter
public class Driver {

    private WebDriver webDriver;
    private Properties props;
    private Cookie cookie;


    @Builder
    private Driver(Properties props, Cookie cookie) {
        this.props = props;
        createWebDriver();
    }


    private void createWebDriver() {

        if (props.getProperty("driver").equals("Chrome")) {
            System.setProperty("webdriver.chrome.driver", props.getProperty("chrome_driver_path"));
            ChromeOptions options = new ChromeOptions();
            String pathToProfile = props.getProperty("chromeProfile");

            if (pathToProfile != null) {
                options.setCapability("profile", "--user-data-dir=" + pathToProfile);
            }
            webDriver = new ChromeDriver(options);

            if (cookie != null) {
                webDriver.manage().addCookie(cookie);
            }
            webDriver.manage().window().maximize();

        } else {
            throw new RuntimeException(ErrorMessage.E100.getMessage());
        }
    }
}

