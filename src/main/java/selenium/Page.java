package selenium;
import constants.ErrorMessage;
import file.Files;
import io.qameta.allure.Attachment;
import lombok.Getter;
import lombok.extern.java.Log;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import props.Props;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Getter
@Log
public abstract class Page implements Waiting {

    private WebDriver driver = Instances.INSTANCE.getWebDriver();

    public Page() {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,20),this);
    }


    public void click(WebElement element) {

        //addingScreenshotToReport();

        try {

            // isDisplayed; isEnabled
            waiting().until(ExpectedConditions.elementToBeClickable(element));
            element.click();

        } catch (Exception e) {
            Waiting.expect(3000);
            try {
                element.click();
            } catch (Exception ex) {
                findWebElement(By.xpath(getXpathFromWebElement(element))).click();
            }
        }
    }

    public WebElement findWebElement(By by) {

        try {

            waiting().until(ExpectedConditions.presenceOfElementLocated(by));
            return driver.findElement(by);

        } catch (Exception e) {
            return null;
        }
    }

    public WebElement findWebElement(WebElement element) {

        try {
            waiting().until(ExpectedConditions.
                    presenceOfElementLocated(By.xpath(getXpathFromWebElement(element))));
            return driver.findElement(By.xpath(getXpathFromWebElement(element)));

        } catch (Exception e) {
            return null;
        }
    }

    public WebElement findWebElementInsideWebElement(WebElement element, By by) {

        try {

            waiting().until(ExpectedConditions.presenceOfElementLocated(by));
            return element.findElement(by);

        } catch (Exception e) {
            return null;
        }
    }

    public List<WebElement> findWebElements(WebElement element, By by) {

        try {
            waiting().until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));

            // Only for table
            if (element.getTagName() != null && element.getTagName().equals("table")) {
                waiting().until(Condition.isJQueryLoaded());
            }
            return element.findElements(by);

        } catch (Exception e) {
            log.info("!!!!!!!!!!!!!!!!!!!!!!!!!");
            log.info(e.getMessage());
            return List.of();
        }
    }

    public String getXpathFromWebElement(WebElement element) {

        try {

            Object proxyOrigin = FieldUtils.readField(element, "h", true);
            Object locator = FieldUtils.readField(proxyOrigin, "locator", true);
            Object findBy = FieldUtils.readField(locator, "by", true);

            return findBy.toString().substring(9);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Setting value in the field
    public void setValue(WebElement element, String value) {
        click(element);
        element.clear();
        element.sendKeys(value);
    }

    // Deleting value in the field
    public void deleteValue(WebElement element) {
        click(element);
        element.clear();
    }

    // Selecting an element from list
    public void selectElement(List<WebElement> webElementList, String value) {

        webElementList.stream()
                .filter(elem -> elem.getText().contains(value))
                .findFirst()
                .ifPresentOrElse(this::click, () -> new RuntimeException("!!!!!!!!!!!"));
    }

    // Selecting an element if tag = select.
    // "element" it is tag "Select" or its parameters
    public void selectElement(WebElement element, String value) {
        click(element);
        new Select(element).selectByVisibleText(value);
        click(element);

    }

    // Going to frame
    public void goToIframe(WebElement iframe) {
        Waiting.expect(5000);
        driver.switchTo().frame(iframe);
    }

    // Exit from frame
    public void exitFromIframe() {
        driver.switchTo().defaultContent();
    }

    public void goToModalWindow(WebElement element) {
        waiting().until(ExpectedConditions.visibilityOf(element));
        driver.switchTo().activeElement();
    }

    // Going to active tab
    public void goToActiveTab(int tabNumber) {
        driver.switchTo().window(driver.getWindowHandles().toArray()[tabNumber].toString());
    }

    // Closing tab
    public Page closeTab() {
        driver.close();
        return this;
    }

    // Opening new tab
    public void openNewTab() {
        ((JavascriptExecutor)driver).executeScript("window.open()");

        List<String> listWindows = new ArrayList<>(driver.getWindowHandles());
        listWindows.stream().reduce((first, last) -> last)
                .ifPresentOrElse(window -> driver.switchTo().window(window), null);
    }

    public void closeAllTadExceptActive() {

        Object[] listTads = driver.getWindowHandles().toArray();

        Arrays.stream(listTads).limit(listTads.length - 1)
                .forEach(win -> {
                    driver.switchTo().window(win.toString());
                    closeTab();
        });
        goToActiveTab(0);
    }

    // Refreshing page
    public void refreshPage() {
        driver.navigate().refresh();
    }

    // Getting entered value from HTML 'input' element
    public String getEnteredValue(WebElement element) {
        return element.getAttribute("value");
    }

    // Getting default value from HTML 'input' element
    public String getDefaultValue(WebElement element) {
        return element.getAttribute("placeholder");
    }

    // Getting selected value from HTML 'select' element
    public String getSelectedValue(WebElement select) {

        return findWebElements(select, By.xpath(".//option"))
                .stream()
                .filter(WebElement::isSelected)
                .map(WebElement::getText)
                .findFirst()
                .orElse(null);
    }

    // Checking that checkbox is selected
    public boolean isSelectedCheckBox(WebElement element) {
        return element.isSelected();
    }

    // Confirmation of the browser alert
    public void clickOkForBrowserAlert() {

        waiting().until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        //Waiting.expect(3000);
    }

    // Getting text from browser alert
    public String getTextFromBrowserAlert() {
        waiting().until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }

    // Creating screenshot and message for Allure report
    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] addingScreenshotToReport() {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    // Adding screenshot to Allure report after error
    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] addingScreenshotToReportAfterError(ITestResult iTestResult) {

        if (iTestResult.getStatus() == ITestResult.FAILURE) {
            return addingScreenshotToReport();
        }
        return null;
    }


    // Adding message to Allure report
    @Attachment(value = "Message", type = "text/plain")
    public String addingMessageToReport(String message) {
        return message;
    }


    // Getting text from WebElement
    public String getTextFromWebElement(WebElement element) {

        try {
            return element.getText().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getting text from WebElement that is hidden (display: none)
    public String getTextFromHiddenWebElement(WebElement element) {

        try {

            String text = element.getAttribute("textContent").trim();
            return text.isEmpty() ? element.getAttribute("value").trim() : text;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getting text from WebElement
    public String getTextFromWebElement(By element) {
        WebElement elem = findWebElement(element);
        return elem != null ? elem.getText() : "";
    }

    // Getting text from List WebElement
    public List<String> getTextFromWebElement(List<WebElement> element) {
        return element.stream().map(WebElement::getText).collect(Collectors.toList());
    }


    // Creating list of cells for each rows without column names
    public List<List<WebElement>> getListCellsInRow(WebElement table) {

        return findWebElements(table, By.xpath(".//tr"))
                .stream()
                .skip(1)
                .map(row -> findWebElements(row, By.xpath(".//td | .//th")))
                .collect(Collectors.toList());

    }

    // Creating list of cells for each rows with column names
    public List<List<WebElement>> getListCellsInRowWithColumnNames(WebElement table) {

        return findWebElements(table, By.xpath(".//tr"))
                .stream()
                .map(row -> findWebElements(row, By.xpath(".//td")))
                .collect(Collectors.toList());

    }




    // columnNameAndValue - First value should be column name; Second value should be value in the column
    public List<List<WebElement>> getListRowsFromTable(WebElement table, String...columnNameAndValue) {

        if (columnNameAndValue.length % 2 != 0) {
            throw new RuntimeException("Incorrect number of values for the 'columnNameAndValue' parameter. " +
                    "There must be an even number of values.\n");
        }

        // Getting only column names from columnNameAndValue
        List<String> columnNames = Stream.iterate(0, n -> n + 1)
                .limit(columnNameAndValue.length)
                .filter(elem -> elem % 2 == 0)
                .map(elem -> columnNameAndValue[elem])
                .collect(Collectors.toList());

        Map<String, Integer> columnNamesFromTable = createAndVerifyColumnNamesForTable(table, columnNames);

        return table.findElements(By.xpath(".//tr"))
                .stream()
                .skip(1)
                .map(row -> row.findElements(By.xpath(".//td")))
                .filter(listCellsForRows -> {

                    if (columnNameAndValue.length != 0) {
                        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                        AtomicInteger item = new AtomicInteger(0);


                        Stream.iterate(0, n -> 1)
                                .limit(columnNameAndValue.length/2)
                                .forEach(elem -> {
                                    atomicBoolean.set(
                                            listCellsForRows.get(columnNamesFromTable.get(columnNameAndValue[item.get()])).getText()
                                                    .equals(columnNameAndValue[item.get() + 1]));
                                    item.addAndGet(2);
                                });
                        return atomicBoolean.get();
                    } else {
                        return true;
                    }
                })
                .map(ArrayList::new)
                .collect(Collectors.toList());
    }





    // Creating and verifying column names from table
    private Map<String, Integer> createAndVerifyColumnNamesForTable(WebElement table, List<String> inputColumnNames) {

        waiting().until(Condition.tableContainsText(table));

        // Creating column names
        Map<String, Integer> columnNamesForTable = createColumnName(table);

        // Verification of the column names
        List<String> listInputColumnNames = inputColumnNames.stream()
                .filter(columnNamesForTable::containsKey)
                .collect(Collectors.toList());
        assertThat(listInputColumnNames.size()).as("One or all column names do not exist")
                .isEqualTo(inputColumnNames.size());

        return columnNamesForTable;
    }


    // Creating column name
    private Map<String, Integer> createColumnName(WebElement table) {

        waiting().until(Condition.tableContainsText(table));

        // Creating column names
        List<String> columnNames = table.findElements(By.xpath(".//th"))
                .stream()
                .map(webElement -> webElement.getText().trim())
                .collect(Collectors.toList());

        // Creating map from column names
        // Key - column names; Value - column number
        Map<String, Integer> columnNamesForTable = IntStream.range(0, columnNames.size())
                .boxed()
                .collect(Collectors.toMap(columnNames::get, Function.identity()));
        assertThat(columnNamesForTable.size()).as("Column names are not created").isGreaterThan(0);
        return columnNamesForTable;
    }


    // Checking that all files was deleted in the directory
    public boolean isFileDownloadedToDirectory() {

        String dir = Props.builder().fileName("props.properties").build().getProps()
                .getProperty("directory_for_downloaded_files");

        AtomicBoolean isFile = new AtomicBoolean(false);

        Stream.generate(() -> new Files().getAllFilesFromDir(dir))
                .limit(5)
                .peek(list -> {
                    if (list.size() == 0) {
                        Waiting.expect(10000);
                    } else {
                        isFile.getAndSet(true);
                    }
                })
                .takeWhile(list -> list.size() == 0)
                .forEach(list -> {});

        return isFile.get();
    }

    // Checking downloading file in the directory
    public boolean isFileDownloadedToDirectory(String fileName) {

        String dir = Props.builder().fileName("props.properties").build().getProps()
                .getProperty("directory_for_downloaded_files");

        AtomicBoolean isFile = new AtomicBoolean(false);

        Stream.generate(() -> new Files().getAllFilesFromDir(dir))
                .peek(list -> {
                    if (list.size() == 0) {
                        Waiting.expect(20000);
                    }
                })
                .limit(5)
                .flatMap(Collection::stream)
                .peek(file -> {
                    if (!file.getFileName().toString().equals(fileName)) {
                        Waiting.expect(20000);
                    } else {
                        isFile.getAndSet(true);
                    }

                })
                .takeWhile(file -> !file.getFileName().toString().contains(fileName))
                .forEach(list -> {});

        return isFile.get();
    }

    // Checking that element is enabled
    public boolean isElementEnabled(WebElement element) {
        return element.isEnabled();
    }

    // Deleting all files in the directory
    public void deleteAllFilesInDirectory() {

        String dir = Props.builder().fileName("props.properties").build().getProps()
                .getProperty("directory_for_downloaded_files");

        new Files().deleteAllFilesInDir(dir);
    }


    // Uploading file
    public void uploadFile(WebElement input, String filePath) {
        input.sendKeys(filePath);
    }

    // Getting page title
    public String getPageName() {
        return driver.getTitle();
    }


    // Moving cursor to element
    public Actions goToElement(WebElement element) {
        return new Actions(getDriver()).moveToElement(element);
    }

    // Getting HTML5 validation message from input field
    public String getValidationMessage(WebElement element) {
        return element.getAttribute("validationMessage");
    }

}
