package selenium;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import props.Props;

import java.util.Properties;

@Getter
public enum Instances {

    INSTANCE;

    private Properties propsProperties;
    private Properties userProperties;
    private WebDriver webDriver;

    Instances() {
        this.propsProperties = Props.builder().fileName("props.properties").build().getProps();
        this.userProperties = Props.builder().fileName("users.properties").build().getProps();
        this.webDriver = Driver.builder().props(propsProperties).build().getWebDriver();
    }

    /*private Properties getPropsProperties() {

        if (propsProperties == null) {
            return this.propsProperties = Props.builder().fileName("props.properties").build().getProps();
        }
        return this.propsProperties;
    }*/
}
