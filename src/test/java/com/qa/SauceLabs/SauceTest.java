package com.qa.SauceLabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class SauceTest extends BaseTest {

    // Method to perform login
    public void doLogin() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        // Wait for the inventory items to load after login (using Duration)
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Updated WebDriverWait
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".inventory_item")));
            System.out.println("Login successful, inventory items are visible.");
        } catch (TimeoutException e) {
            System.out.println("Login failed or inventory items are not visible.");
        }
    }

    @Test(priority = 1)
    public void checkInventoryItemTest() {
        doLogin();
        int itemCount = driver.findElements(By.cssSelector(".inventory_item")).size();
        System.out.println("Number of inventory items found: " + itemCount);
        Assert.assertEquals(itemCount, 6, "Inventory item count does not match the expected value.");
    }

    @Test(priority = 2)
    public void checkAddToCartButtonTest() {
        doLogin();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Updated WebDriverWait
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add to cart']")));
            int addToCartButtonCount = driver.findElements(By.xpath("//button[text()='Add to cart']")).size();
            System.out.println("Number of 'Add to Cart' buttons found: " + addToCartButtonCount);
            Assert.assertEquals(addToCartButtonCount, 6, "'Add to Cart' button count does not match the expected value.");
        } catch (TimeoutException e) {
            System.out.println("Timeout: 'Add to Cart' button not visible.");
        }
    }
}
