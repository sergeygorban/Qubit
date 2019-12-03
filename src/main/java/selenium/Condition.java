package selenium;

import lombok.extern.java.Log;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Log
public class Condition {





    public static ExpectedCondition<Boolean> tableContainsText(WebElement element) {

        return driver -> element.findElements(By.xpath(".//tr"))
                .stream()
                .anyMatch(row -> row.getText() != null && !row.getText().isEmpty());
    }


    public static boolean isElementPresent(WebElement element) {
        try {
            Instances.INSTANCE.getWebDriver().findElement(By.xpath(getXpathFromWebElement(element)));
            return true;
        } catch (NoSuchElementException e) {
            log.info(e.getMessage());
            return false;
        }
    }

    // Waiting while html element will not be present in the DOM
    public static ExpectedCondition<Boolean> isElementNotPresent(WebElement element) {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    driver.findElement(By.xpath(getXpathFromWebElement(element)));
                    return false;
                } catch (NoSuchElementException e) {
                    return true;
                }
            }
        };
    }

    // Waiting while html element will not be present in the DOM
    public static ExpectedCondition<Boolean> isElementNotPresent(By by) {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    driver.findElement(by);
                    return false;
                } catch (NoSuchElementException e) {
                    return true;
                }
            }
        };
    }


    public static ExpectedCondition<Boolean> elementContainsText(WebElement element) {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return element.getText() != null && !element.getText().isEmpty();
            }
        };
    }


    public static boolean isAlertPresent() {

        try {
            new WebDriverWait(Instances.INSTANCE.getWebDriver(), 15).until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static ExpectedCondition<Boolean> elementIsNotContainsAttribute(WebElement element, String attribute) {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return element.getAttribute(attribute) == null;
                } catch (Exception e) {
                    return driver.findElement(By.xpath(getXpathFromWebElement(element))).getAttribute(attribute) == null;
                }
            }
        };
    }



    public static boolean isElementPresent(By locator) {
        try {
            Instances.INSTANCE.getWebDriver().findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    // Checking loading image on the page
    public static boolean isImageLoaded(By locator) {

        return (Boolean) ((JavascriptExecutor)Instances.INSTANCE.getWebDriver())
                .executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' " +
                        "&& arguments[0].naturalWidth > 0", Instances.INSTANCE.getWebDriver().findElement(locator));
    }

    public static boolean isImageLoaded(WebElement element) {

        return (Boolean) ((JavascriptExecutor)Instances.INSTANCE.getWebDriver())
                .executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' " +
                        "&& arguments[0].naturalWidth > 0",
                        Instances.INSTANCE.getWebDriver().findElement(By.xpath(getXpathFromWebElement(element))));
    }




    private static String getXpathFromWebElement(WebElement element) {

        try {

            Object proxyOrigin = FieldUtils.readField(element, "h", true);
            Object locator = FieldUtils.readField(proxyOrigin, "locator", true);
            Object findBy = FieldUtils.readField(locator, "by", true);

            return findBy.toString().substring(9);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void ajaxComplete() {
        ((JavascriptExecutor) Instances.INSTANCE.getWebDriver())
                .executeScript("var callback = arguments[arguments.length - 1];"
                + "var xhr = new XMLHttpRequest();" + "xhr.open('GET', '/Ajax_call', true);"
                + "xhr.onreadystatechange = function() {" + "  if (xhr.readyState == 4) {"
                + "    callback(xhr.responseText);" + "  }" + "};" + "xhr.send();");
    }

    // Waiting for jQuery is loaded
    public static ExpectedCondition<Boolean> isJQueryLoaded() {

        if ((Boolean) ((JavascriptExecutor) Instances.INSTANCE.getWebDriver())
                .executeScript("return jQuery !== undefined")) {

            return driver -> (Long) ((JavascriptExecutor) Instances.INSTANCE.getWebDriver())
                    .executeScript("return jQuery.active") == 0;
        }

        return driver -> true;

    }

    public static ExpectedCondition<Boolean> isJSLoaded() {
        return driver -> ((JavascriptExecutor) Instances.INSTANCE.getWebDriver())
                .executeScript("return document.readyState").toString().equals("complete");
    }
}
