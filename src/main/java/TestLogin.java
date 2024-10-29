import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class TestLogin {
    public static void main(String[] args) {
        WebDriver driver;
        driver = new ChromeDriver();
        WebDriverManager.chromedriver().setup();

        // Thiết lập cửa sổ và thời gian chờ
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.demo.guru99.com/V4/");

        // Truy cập các trường nhập liệu và nút
        WebElement userIdField = driver.findElement(By.id("userId"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login"));
        WebElement resetButton = driver.findElement(By.id("reset"));

        // Mảng dữ liệu thử nghiệm
        String[][] testCases = {
                {"username", "password", "Đã nhập UserID và Password, nhấn Login"},
                {"username", "", "Đã nhập UserID nhưng không nhập Password"},
                {"", "password", "Không nhập UserID nhưng nhập Password"},
                {"username", "", "Đã nhập UserID, không nhập Password và nhấn Login"},
                {"", "password", "Không nhập UserID, chỉ nhập Password và không nhấn Login"},
                {"username", "", "Đã nhập UserID, không nhập Password và không nhấn Login"}
        };

        // Thực hiện kiểm thử cho từng trường hợp
        for (String[] testCase : testCases) {
            String userId = testCase[0];
            String password = testCase[1];
            String testCaseDescription = testCase[2];

            userIdField.clear();
            userIdField.sendKeys(userId);
            userIdField.sendKeys(Keys.TAB);

            passwordField.clear();
            passwordField.sendKeys(password);


            if (!userId.isEmpty() && !password.isEmpty()) {
                loginButton.click();
                System.out.println("Thực hiện kiểm thử: " + testCaseDescription);
            } else {
                System.out.println("Thực hiện kiểm thử không đăng nhập: " + testCaseDescription);
            }

            resetButton.click();
            if (userIdField.getText().isEmpty() && passwordField.getText().isEmpty()) {
                System.out.println("Reset thành công: Tất cả các trường đã được xóa.");
            } else {
                System.out.println("Reset không thành công: Các trường chưa được xóa.");
            }
        }

        driver.quit();
    }
}
