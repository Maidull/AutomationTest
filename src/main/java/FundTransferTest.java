import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class FundTransferTest {
    public static void main(String[] args) {
        // Set up WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Configure window and timeouts
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Navigate to the fund transfer page (assuming the login is already completed)
        driver.get("https://www.demo.guru99.com/V4/");

        // Sample locators, adjust based on actual IDs or names on the page
        WebElement payersAccountField = driver.findElement(By.id("payers_account"));
        WebElement payeesAccountField = driver.findElement(By.id("payees_account"));
        WebElement amountField = driver.findElement(By.id("amount"));
        WebElement descriptionField = driver.findElement(By.id("description"));
        WebElement submitButton = driver.findElement(By.name("submit"));
        WebElement resetButton = driver.findElement(By.name("reset"));

        // Test Cases for Fund Transfer
        String[][] testCases = {
                {"", "200023", "20000", "test", "Payers Account Number must not be blank"},
                {"200323", "", "20000", "test", "Payees Account Number must not be blank"},
                {"200323", "200023", "", "test", "Amount field must not be blank"},
                {"200323", "200023", "20000", "", "Description cannot be blank"},
                {"200323@", "200023", "20000", "test", "Special characters are not allowed"},
                {"200323", "200023@", "20000", "test", "Special characters are not allowed"},
        };

        // Execute each test case
        for (String[] testCase : testCases) {
            String payersAccount = testCase[0];
            String payeesAccount = testCase[1];
            String amount = testCase[2];
            String description = testCase[3];
            String expectedErrorMessage = testCase[4];

            // Clear fields
            payersAccountField.clear();
            payeesAccountField.clear();
            amountField.clear();
            descriptionField.clear();

            // Enter test data
            if (!payersAccount.isEmpty()) payersAccountField.sendKeys(payersAccount);
            if (!payeesAccount.isEmpty()) payeesAccountField.sendKeys(payeesAccount);
            if (!amount.isEmpty()) amountField.sendKeys(amount);
            if (!description.isEmpty()) descriptionField.sendKeys(description);

            // Submit the form
            submitButton.click();

            // Verify error message
            WebElement errorMessageElement = driver.findElement(By.id("error_message")); // Adjust ID based on actual page
            String actualErrorMessage = errorMessageElement.getText();
            if (actualErrorMessage.contains(expectedErrorMessage)) {
                System.out.println("Test passed: " + expectedErrorMessage);
            } else {
                System.out.println("Test failed. Expected: " + expectedErrorMessage + ", but got: " + actualErrorMessage);
            }

            // Reset the form after each test case
            resetButton.click();
        }

        // Close the browser
        driver.quit();
    }
}
