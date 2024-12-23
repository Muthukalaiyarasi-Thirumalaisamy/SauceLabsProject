package com.qa.SauceLabs;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {

    WebDriver driver; 

    @Parameters({"browser", "platform", "version"})
    @BeforeMethod
    public void setUp(String browserName, String platformName, String versionName, Method method) throws MalformedURLException {

        System.out.println("Browser name is: " + browserName);
        String testName = method.getName(); 

        // Configure Chrome Options
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName(platformName); // Use the parameter for platform
        browserOptions.setBrowserVersion(versionName); // Use the parameter for version

        // Set Sauce Labs options
        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("username", "oauth-muthukalai2018-dcae0");
        sauceOptions.put("accessKey", "196d8329-2f78-4cbb-9660-7ee3b3bc4e10");
        sauceOptions.put("build", "Java-W3C-Examples");
        sauceOptions.put("name", testName); // Set test name dynamically
        sauceOptions.put("tags", new String[]{"w3c-chrome-tests", "regression"}); // Optional tags for categorization

        browserOptions.setCapability("sauce:options", sauceOptions);

        URL url = new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub");
        driver = new RemoteWebDriver(url, browserOptions);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit(); 
        }
    }
}
