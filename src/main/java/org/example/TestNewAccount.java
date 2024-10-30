package main.java.org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class TestNewAccount {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Thiết lập WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        // Thiết lập cửa sổ và thời gian chờ
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testCustomerIdValidation() {
        driver.get("https://www.demo.guru99.com/V4/");

        // Đăng nhập
        driver.findElement(By.name("uid")).sendKeys("mohit");
        driver.findElement(By.name("password")).sendKeys("mohit123");
        driver.findElement(By.name("btnLogin")).click();

        // Tạo tài khoản mới
        driver.findElement(By.linkText("New Account")).click();

        // T1: Customer Id is required
        driver.findElement(By.name("btnSubmit")).click();
        String errorMessage = driver.findElement(By.xpath("//label[contains(text(), 'Customer ID is required')]")).getText();
        Assertions.assertEquals("Customer ID is required", errorMessage);

        // T2: Special characters are not allowed
        driver.findElement(By.name("cusid")).sendKeys("!@#$%");
        driver.findElement(By.name("btnSubmit")).click();
        errorMessage = driver.findElement(By.xpath("//label[contains(text(), 'Special characters are not allowed')]")).getText();
        Assertions.assertEquals("Special characters are not allowed", errorMessage);

        // T3: Characters are not allowed
        driver.findElement(By.name("cusid")).clear();
        driver.findElement(By.name("cusid")).sendKeys("abcde"); // Giả sử đây là ký tự không hợp lệ
        driver.findElement(By.name("btnSubmit")).click();
        errorMessage = driver.findElement(By.xpath("//label[contains(text(), 'Characters are not allowed')]")).getText();
        Assertions.assertEquals("Characters are not allowed", errorMessage);

        // T3.1: First character cannot have space
        driver.findElement(By.name("cusid")).clear();
        driver.findElement(By.name("cusid")).sendKeys(" 12345"); // Bắt đầu bằng khoảng trắng
        driver.findElement(By.name("btnSubmit")).click();
        errorMessage = driver.findElement(By.xpath("//label[contains(text(), 'First character cannot have space')]")).getText();
        Assertions.assertEquals("First character cannot have space", errorMessage);
    }

    @AfterEach
    public void tearDown() {
        // Đóng trình duyệt
        if (driver != null) {
            driver.quit();
        }
    }
}