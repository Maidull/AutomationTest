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

public class TestDeposit {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testDepositValidation() {
        driver.get("https://www.demo.guru99.com/V4/");

        // Đăng nhập
        driver.findElement(By.name("uid")).sendKeys("mohit");
        driver.findElement(By.name("password")).sendKeys("mohit123");
        driver.findElement(By.name("btnLogin")).click();

        // Chuyển đến trang gửi tiền
        driver.findElement(By.linkText("Deposit")).click();

        // T48: Account No must not be blank
        driver.findElement(By.name("btnSubmit")).click();
        String errorMessage = driver.findElement(By.xpath("//label[contains(text(), 'Account No must not be blank')]")).getText();
        Assertions.assertEquals("Account No must not be blank", errorMessage);

        // T49: Special characters are not allowed
        driver.findElement(By.name("accountno")).sendKeys("!@#$%");
        driver.findElement(By.name("btnSubmit")).click();
        errorMessage = driver.findElement(By.xpath("//label[contains(text(), 'Special characters are not allowed')]")).getText();
        Assertions.assertEquals("Special characters are not allowed", errorMessage);

        // T50: Characters are not allowed
        driver.findElement(By.name("accountno")).clear();
        driver.findElement(By.name("accountno")).sendKeys("abcde"); // Giả sử đây là ký tự không hợp lệ
        driver.findElement(By.name("btnSubmit")).click();
        errorMessage = driver.findElement(By.xpath("//label[contains(text(), 'Characters are not allowed')]")).getText();
        Assertions.assertEquals("Characters are not allowed", errorMessage);

        // T51: Amount field must not be blank
        driver.findElement(By.name("accountno")).clear();
        driver.findElement(By.name("accountno")).sendKeys("123456"); // Nhập số tài khoản hợp lệ
        driver.findElement(By.name("btnSubmit")).click(); // Gửi mà không nhập số tiền
        errorMessage = driver.findElement(By.xpath("//label[contains(text(), 'Amount field must not be blank')]")).getText();
        Assertions.assertEquals("Amount field must not be blank", errorMessage);

        // T52: Special characters are not allowed in Amount
        driver.findElement(By.name("amount")).sendKeys("!@#$%");
        driver.findElement(By.name("btnSubmit")).click();
        errorMessage = driver.findElement(By.xpath("//label[contains(text(), 'Special characters are not allowed')]")).getText();
        Assertions.assertEquals("Special characters are not allowed", errorMessage);

        // T53: Characters are not allowed in Amount
        driver.findElement(By.name("amount")).clear();
        driver.findElement(By.name("amount")).sendKeys("abcde"); // Giả sử đây là ký tự không hợp lệ
        driver.findElement(By.name("btnSubmit")).click();
        errorMessage = driver.findElement(By.xpath("//label[contains(text(), 'Characters are not allowed')]")).getText();
        Assertions.assertEquals("Characters are not allowed", errorMessage);

        // T54: Description must not be blank
        driver.findElement(By.name("amount")).clear();
        driver.findElement(By.name("amount")).sendKeys("1000"); // Nhập số tiền hợp lệ
        driver.findElement(By.name("btnSubmit")).click(); // Gửi mà không nhập mô tả
        errorMessage = driver.findElement(By.xpath("//label[contains(text(), 'Description must not be blank')]")).getText();
        Assertions.assertEquals("Description must not be blank", errorMessage);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}