import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class TestNewCustomer {
    public static void main(String[] args) {
        WebDriver driver;
        driver = new ChromeDriver();
        WebDriverManager.chromedriver().setup();

        // Thiết lập cửa sổ và thời gian chờ
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.demo.guru99.com/V4/manager/addcustomerpage.php");

        // Truy cập các trường nhập liệu
        WebElement customerName = driver.findElement(By.id("customerName"));
        WebElement address = driver.findElement(By.id("address"));
        WebElement city = driver.findElement(By.id("city"));
        WebElement state = driver.findElement(By.id("state"));
        WebElement pin = driver.findElement(By.id("pin"));
        WebElement telephone = driver.findElement(By.id("telephone"));
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.id("submit"));
        WebElement resetButton = driver.findElement(By.id("reset"));

        // Mảng dữ liệu thử nghiệm
        String[][] testData = {
                {"12345", "Customer Name không được nhập số"},
                {"@!#%^", "Customer Name không được nhập ký tự đặc biệt"},
                {"", "Customer Name không được để trống"},
                {"John Doe", "Customer Name hợp lệ"},
                {"", "Address không được để trống"},
                {"123 Main St", "Address hợp lệ"},
                {"@!#%^", "Address không được nhập ký tự đặc biệt"},
                {"@!#%^", "City không được nhập ký tự đặc biệt"},
                {"", "City không được để trống"},
                {"12345", "City không được nhập số"},
                {"Hanoi", "City hợp lệ"},
                {"12345", "State không được nhập số"},
                {"", "State không được để trống"},
                {"@!#%^", "State không được nhập ký tự đặc biệt"},
                {"California", "State hợp lệ"},
                {"ABCD", "PIN không được nhập ký tự"},
                {"", "PIN không được để trống"},
                {"@!#%^", "PIN không được nhập ký tự đặc biệt"},
                {"123456", "PIN hợp lệ"},
                {"", "Telephone không được để trống"},
                {"@!#%^", "Telephone không được nhập ký tự đặc biệt"},
                {"ABCD", "Telephone không được nhập ký tự"},
                {"0987654321", "Telephone hợp lệ"},
                {"", "Email không được để trống"},
                {"test@example.com", "Email hợp lệ"},
        };

        // Thực hiện kiểm thử cho từng trường hợp
        for (String[] data : testData) {
            customerName.clear();
            customerName.sendKeys(data[0]);
            customerName.sendKeys(Keys.TAB);

            System.out.println("Đang kiểm thử: " + data[1]);
        }

        resetButton.click();

        if (customerName.getText().isEmpty() && address.getText().isEmpty() && city.getText().isEmpty()
                && state.getText().isEmpty() && pin.getText().isEmpty() && telephone.getText().isEmpty()
                && email.getText().isEmpty() && password.getText().isEmpty()) {
            System.out.println("Reset thành công: Tất cả các trường đã được xóa.");
        } else {
            System.out.println("Reset không thành công: Các trường chưa được xóa.");
        }

        customerName.sendKeys("John Doe");
        address.sendKeys("123 Main St");
        city.sendKeys("Hanoi");
        state.sendKeys("California");
        pin.sendKeys("123456");
        telephone.sendKeys("0987654321");
        email.sendKeys("test@example.com");
        password.sendKeys("Password123");

        submitButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.quit();
    }
}
