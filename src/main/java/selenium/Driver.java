package selenium;

import constants.ErrorMessage;
import file.Files;
import lombok.Builder;
import lombok.Getter;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import props.Props;

import java.io.IOException;
import java.nio.file.Paths;
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
            options.addArguments("start-maximized");

            //System.setProperty("webdriver.chrome.logfile", "C:\\chromedriver.log");
            //System.setProperty("webdriver.chrome.verboseLogging", "true");
            String pathToProfile = props.getProperty("chromeProfile");

            if (pathToProfile != null) {
                options.addArguments("profile", "user-data-dir=" + pathToProfile);
            }
            webDriver = new ChromeDriver(options);

            if (cookie != null) {
                webDriver.manage().addCookie(cookie);
            }

        } else {
            throw new RuntimeException(ErrorMessage.E100.getMessage());
        }
    }


    public static void main(String[] args) throws IOException {
        Driver.builder().props(Props.builder().fileName("props.properties").build().getProps()).build().getWebDriver();
    }

}

