import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class TestWidthdraw {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.demo.guru99.com/V4/");

        // Login (Adjust selectors as per the page structure)
        WebElement userIdField = driver.findElement(By.name("uid"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));

        userIdField.sendKeys("mngr596148");
        passwordField.sendKeys("123456");
        loginButton.click();

        // Navigate to Withdraw page
        WebElement withdrawalLink = driver.findElement(By.linkText("Withdrawal"));
        withdrawalLink.click();

        // Test cases for Withdraw form
        String[][] testCases = {
                {"", "2000", "test", "Account Number must not be blank"},
                {"12", "", "test", "Amount must not be blank"},
                {"12", "2000", "", "Description must not be blank"},
                {"12", "2000@", "test", "Special characters are not allowed"},
                {"", "", "", "Please fill all fields"},
                {"122", "2000", "test", "Account does not exist"},
                {"122", "2000", "test", "Submit successfully"} // Adjust expected result based on actual result
        };

        for (String[] testCase : testCases) {
            String accountNo = testCase[0];
            String amount = testCase[1];
            String description = testCase[2];
            String expectedMessage = testCase[3];

            WebElement accountNoField = driver.findElement(By.name("accountno"));
            WebElement amountField = driver.findElement(By.name("ammount"));
            WebElement descriptionField = driver.findElement(By.name("desc"));
            WebElement submitButton = driver.findElement(By.name("AccSubmit"));
            WebElement resetButton = driver.findElement(By.name("res"));

            // Fill in fields based on test case
            accountNoField.clear();
            accountNoField.sendKeys(accountNo);
            amountField.clear();
            amountField.sendKeys(amount);
            descriptionField.clear();
            descriptionField.sendKeys(description);

            if (!accountNo.isEmpty() && !amount.isEmpty() && !description.isEmpty()) {
                submitButton.click();
            } else {
                System.out.println("Executing test without submit action for empty field validation.");
            }

            // Capture and verify the displayed message
            WebElement messageElement = driver.findElement(By.cssSelector("selector_for_error_message")); // Adjust selector
            String actualMessage = messageElement.getText();

            System.out.println("Expected: " + expectedMessage);
            System.out.println("Actual: " + actualMessage);

            if (actualMessage.contains(expectedMessage)) {
                System.out.println("Test passed for account: " + accountNo + ", amount: " + amount + ", description: " + description);
            } else {
                System.out.println("Test failed for account: " + accountNo + ", amount: " + amount + ", description: " + description);
            }

            resetButton.click();
        }

        driver.quit();
    }
}
