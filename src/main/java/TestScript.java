import java.awt.AWTException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestScript {

	WebDriver driver;
	private final String finalText = "test";

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", "E:\\selenium\\jar file\\chrome77\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void test01LoginPage() {
		driver.get("https://dev.trackwalkins.com/");
		driver.findElement(By.xpath("//input[@placeholder='Enter email address']")).click();
		driver.findElement(By.xpath("//input[@placeholder='Enter email address']")).sendKeys("demo@mailinator.com");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).click();
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("demo123");
		driver.findElement(By.xpath("//button[@class='mat-raised-button mat-primary mat-button']")).click();
	}

	// verify user avalible on dashbord page
	@Test
	public void test02DashboardPage() {
		((WebElement) new WebDriverWait(driver, 60L).until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[@class='color-blue-a800 header-title f-family-sans f-weight-bold']")))).isDisplayed();
		Assert.assertEquals(driver
				.findElement(By.xpath("//span[@class='color-989898 header-title-note f-family-sans']")).isDisplayed(),
				true, "Check text below Dashboard ");
		Assert.assertEquals(driver.findElement(By.xpath("//img[@class='logo']")).isDisplayed(), true,
				"Check Class Logo");
	}

	@Test
	public void test03SendSMSPage() throws InterruptedException {

		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By
				.xpath("//mat-expansion-panel-header[@id='mat-expansion-panel-header-2']//mat-icon[@class='sidebar-icon svg mat-icon ng-star-inserted']")))
				.build().perform();

		Thread.sleep(500);

		driver.findElement(By.xpath("//a[contains(text(),'Communication Management')]")).click();
		driver.findElement(By.xpath("//a[text()='Send SMS']")).click();

		// providing explicit wait check User is on Dashbord page

		((WebElement) new WebDriverWait(driver, 30L).until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[@class='color-blue-a800 header-title f-family-sans f-weight-bold']")))).isDisplayed();

		Thread.sleep(10000);

		driver.findElement(By.xpath("//div[contains(text(),'Picture')]")).click();

	}

	@Test
	public void test04SendSMSPage() throws AWTException, InterruptedException {

		driver.findElement(By.xpath("//*[contains(text(),' Select picture')]")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@class='side-inner-wrapper']//input[@type='file']"))
				.sendKeys("C:\\Users\\Aashish\\Desktop\\Pic2.jpg");

		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[contains(text(),'select')]")).click();

		// Manual Test

		((WebElement) new WebDriverWait(driver, 30L).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Manual Post')]"))))
						.isDisplayed();

		Thread.sleep(5000);

		driver.findElement(By.xpath("//div[contains(text(),'Manual Post')]")).click();
		((WebElement) new WebDriverWait(driver, 30L).until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//textarea[@placeholder='Enter Mobile Number']")))).isDisplayed();
		driver.findElement(By.xpath("//textarea[@placeholder='Enter Mobile Number']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//textarea[@placeholder='Enter Mobile Number']")).sendKeys("7058881822");

		Thread.sleep(2000);

		driver.findElement(By.xpath("//button[contains(text(),'ADD NUMBERS')]")).click();
		Thread.sleep(2000);

		// drop town Testing
		Select country = new Select(driver.findElement(
				By.xpath("//div[@class='form-control']//div[@class='form-control']//select[@id='country']")));
		country.selectByIndex(1);

		// send Massage   //button[@class='swal2-cancel swal-cancel-button-class swal2-styled']

		driver.findElement(By.xpath("//textarea[@formcontrolname='message']")).clear();
		driver.findElement(By.xpath("//textarea[@formcontrolname='message']"))
				.sendKeys(new CharSequence[] { finalText });

		((WebElement) new WebDriverWait(driver, 30L).until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='row']//div//div//button[@class='mat-raised-button mat-primary mat-button']"))))
						.isDisplayed();

		driver.findElement(
				By.xpath("//div[@class='row']//div//div//button[@class='mat-raised-button mat-primary mat-button']"))
				.click();

		Thread.sleep(10000);

		SoftAssert soft = new SoftAssert();
		
		soft.assertEquals(driver.findElement(By.xpath("//div[@class='swal2-popup swal2-modal swal-popup-class-confirm swal2-show']"))
				.isDisplayed(), true, "Check Tab message");
		Thread.sleep(10000);
		driver.findElement(By.xpath("//button[@class='swal2-cancel swal-cancel-button-class swal2-styled']")).click();

		((WebElement) new WebDriverWait(driver, 60L)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='SMS Report']"))))
						.isDisplayed();

		((WebElement) new WebDriverWait(driver, 30L)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[3]//div[1]//div[1]"))))
						.isDisplayed();

		Assert.assertEquals(driver.findElement(By.xpath("//img[@class='logo']")).isDisplayed(), true,
				"Check Logo is Displayed");

		String text = driver.findElement(By.xpath("//tr[1]//td[3]//div[1]//div[1]")).getText();
		System.out.println(text);
		Assert.assertEquals(finalText.equals(text), true, "SMS text mis-matched. Hence, Escalate");
		System.out.println("Task Complate");

	}
	
	@AfterClass
	public void reportSend() throws AddressException, MessagingException{
		TestMail test = new TestMail();
		test.send();
		System.out.println("Mail send done");
	}

	@AfterTest
	public void afterTest() {
		driver.close();

	}
}
