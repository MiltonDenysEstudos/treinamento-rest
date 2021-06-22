package page;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.datatable.DataTable;

public class BasePage {
	
	protected static String url = "https://www.discourse.org";
	protected static List<Map<String, String>> issue_info;
	public static WebDriver driver;
	protected static List<WebElement> listOfElements;

	public static void openHomePage(DataTable data) {
		issue_info = data.asMaps(String.class, String.class);

		// WebDriver driver;
		System.setProperty("webdriver.chrome.driver",
				"E:\\workplace_eclipse\\TestQA\\src\\test\\resources\\chromedriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	public WebDriver getDriver() {
		return driver;
	}

	public static void quitDriver() {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.quit();

		} catch (Exception e) {

		}
	}

	public static void preencher(String xpath, String value) {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement Element = driver.findElement(By.xpath(xpath));
		Element.sendKeys(value);

	}

	public static void clicar(String xpath) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement Element = driver.findElement(By.xpath(xpath));
		Element.click();

	}
}
