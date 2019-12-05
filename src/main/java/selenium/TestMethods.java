package selenium;

import org.testng.ITestContext;
import org.testng.ITestResult;

import java.util.List;
import java.util.stream.Collectors;

public class TestMethods {

    // The method returns test data that is passed to the test.
    public static Object getTestDataForTest(ITestContext iTestContext, String methodName) {

        List<Object> list = iTestContext.getSuite().getAllInvokedMethods()
                .stream()
                .filter(method -> method.getTestMethod().getMethodName().equals(methodName))
                .map(method -> method.getTestResult().getParameters()[0])
                .collect(Collectors.toList());

        return list.get(list.size() - 1);
    }

    public static Object getTestDataForTest(ITestResult iTestResult) {

        return iTestResult.getParameters()[0];
    }


}
