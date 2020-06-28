package webDriverManager;

import static org.testng.Assert.assertTrue;
import java.io.FileInputStream;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverManagerTest {

	WebDriver driver;
	Properties prop;

	@BeforeClass
	public void setupBrowser() throws Exception {
		readPropertyFile();
		String browserName = (String) prop.get("browser");
		setBrowser(browserName);
		driver.manage().window().maximize();
		System.out.println(String.format("%s browser launched successfully...", browserName));
	}

	@Test
	public void launchAmazon() {
		driver.get("http://www.amazon.com");
		String title = driver.getTitle();
		String expectedTitle = "Amazon.com";
		assertTrue(title.contains(expectedTitle),
				String.format("Actual Title '%s' does not contains expected title was '%s'.", title, expectedTitle));
		System.out.println("Test passed successfully...");
	}

	@AfterClass
	public void closeBrowser() {
		if (driver != null) {
			driver.close();
			driver.quit();
			System.out.println("Browser closed successfully...");
		}
	}

	/**
	 * This method will set the Browser
	 * 
	 * @param browser
	 * @throws Exception
	 */
	public void setBrowser(String browser) throws Exception {
		switch (browser) {
		case "Chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "Firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "Edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		case "IE":
			WebDriverManager.iedriver().arch32().setup();
			driver = new InternetExplorerDriver();
			break;
		default:
			throw new Exception("Unknown browser - " + browser);
		}
	}

	/**
	 * This method will read the property file
	 * 
	 * @throws Exception
	 */
	public void readPropertyFile() throws Exception {
		prop = new Properties();
		try {
			prop.load(new FileInputStream("./src/test/resources/Config.properties"));
		} catch (Exception e) {
			System.err.println("Unable to read property file...");
			throw e;
		}
	}

}
