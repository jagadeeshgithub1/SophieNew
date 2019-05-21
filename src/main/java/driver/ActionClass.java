/**
 * This is the action class , where all the functional methods are defined.
 * While adding a new action method to this library , please provide the documentation to understand the pupose of the method
 * 
 * 
 */
package driver;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.get;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.opencsv.CSVReader;

import base.TestBaseClass;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import utilities.ExcelUtils;
import utilities.GeneralUtilities;

/**
 * @author deepa
 *
 */
public class ActionClass extends TestBaseClass {

	public static WebDriver driver = null;
	public static String downloadFilepath = null;
	// public static ChromeDriverService driverService = null;
	// public static ChromeDriver driver = null;
	public String osName = System.getProperty("os.name");
	// public GeneralUtilities generalUtilities = new GeneralUtilities();
	// public static HtmlUnitDriver driver;

	public ActionClass() throws Exception {

		super();
		// initialization();

		// TODO Auto-generated constructor stub
	}

	public boolean login() {
		/*
		 * @author :Deepa Panikkaveetil
		 * 
		 * @date :3/19/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :Method used for login to the website , by taking the parameters from
		 * the Properties file 'Properties'
		 * 
		 * @Parameters:locators of username , Password, and data for username and
		 * password from properties file
		 * 
		 * 
		 */
		boolean flag = false;

		try {
			System.out.println("in login>>>>>>>>>>>>>>>>>>>");
			System.out.println("driver>> " + driver.toString());
			System.out.println("title of the page:" + driver.getTitle());
			System.out.println(prop.getProperty("username"));
			System.out.println(prop.getProperty("password"));

			WebElement userName = driver.findElement(By.xpath(prop.getProperty("txtUserName")));
			if (userName.isDisplayed())
				driver.findElement(By.xpath(prop.getProperty("txtUserName"))).sendKeys(prop.getProperty("username"));
			WebElement pwd = driver.findElement(By.xpath(prop.getProperty("txtPassword")));
			if (pwd.isDisplayed())
				driver.findElement(By.xpath(prop.getProperty("txtPassword"))).sendKeys(prop.getProperty("password"));
			driver.findElement(By.xpath(prop.getProperty("btnLogin"))).click();// here we get the type error for
																				// getExtension
			// if(driver.findElement(By.id("error")).isDisplayed()) {
			// System.out.println(driver.findElement(By.id("error")).getText());

			System.out.println("title of the page:" + driver.getTitle());

			// PrintStream printStream= new PrintStream("./PageSource.txt");
			// System.setOut(printStream);

			// System.out.println("PageSource of DesinerStudio>>>>"+driver.getPageSource());

			// System.out.println("---------------end-----------------");

			flag = true;

		} catch (Exception e) {
			flag = false;
			System.out.println("login failed..");

			e.printStackTrace();
			// TODO: handle exception
		}
		return flag;
	}

	public boolean click(String object) {
		/*
		 * @author :Deepa Panikkaveetil
		 *
		 * 
		 * @date :3/19/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to clik on any web element
		 * 
		 * @Parameters:Passing the xpath locator from the properties file
		 * 
		 * 
		 * 
		 */
		boolean flag = false;
		try {

			System.out.println("object name:" + object);

			// WebElement ele = new WebDriverWait(driver, 90)
			// .until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty(object))));
			boolean ele = new WebDriverWait(driver, 90).until(ExpectedConditions.and(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty(object))),
					ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty(object)))));

			if (ele == true) {

				WebElement element = driver.findElement(By.xpath(prop.getProperty(object)));

				// Actions action = new Actions(driver);
				System.out.println("is this the element???>>" + ele);
				// action.moveToElement(element).click(element).build().perform();
				/*
				 * try { Thread.sleep(5000); } catch (InterruptedException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 */
				// action.moveToElement(element).sendKeys(Keys.RETURN);
				element.click();

				// System.out.println(driver.getPageSource());

				flag = true;
			} else {
				System.out.println("Element" + object + "is not present");
				flag = false;

			}

		} catch (TimeoutException e) {

			System.out.println("Are you sure the element is present");
			flag = false;
			// TODO: handle exception
		} catch (Exception e) {
			System.out.println("Stale element exception");
			flag = false;
			e.printStackTrace();

			// TODO: handle exception
		}

		// TODO: handle exception
		return flag;
	}

	public boolean csvDownloadclick(String object) {
		/*
		 * @author :Deepa Panikkaveetil
		 *
		 * 
		 * @date :3/19/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to clik on any web element
		 * 
		 * @Parameters:Passing the xpath locator from the properties file
		 * 
		 * 
		 * 
		 */
		boolean flag = false;
		try {

			boolean ele = new WebDriverWait(driver, 600).until(ExpectedConditions.and(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty(object))),
					ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty(object)))));
			if (ele == true) {

				WebElement element = driver.findElement(By.xpath(prop.getProperty(object)));

				// Actions action = new Actions(driver);
				System.out.println("is this the element???>>" + element);
				element.click();
				// action.moveToElement(element).click(element).build().perform();

				Thread.sleep(5000);

				// action.moveToElement(element).sendKeys(Keys.RETURN);
				// driver.findElement(By.xpath(prop.getProperty(object))).click();
				if (driver.getPageSource().equalsIgnoreCase("Data Set BatchDecisionOutput is empty")) {
					flag = false;
					System.out.println("Dataset is empty");
					return flag;
				}
				flag = true;
			} else {
				System.out.println("run engine is not clicked..");
				flag = false;
				return flag;
			}

		} catch (TimeoutException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Time out..Are you sure the element is present");
			flag = false;
			return flag;

			// TODO: handle exception
		} catch (Exception e) {
			System.out.println("element is not clicked" + " " + e.getMessage());
			flag = false;
			return flag;
			// TODO: handle exception
		}

		// TODO: handle exception
		return flag;
	}

	public boolean RunOrResumeEngineclick(String object) {
		/*
		 * @author :Deepa Panikkaveetil
		 *
		 * 
		 * @date :3/19/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to clik on any web element
		 * 
		 * @Parameters:Passing the xpath locator from the properties file
		 * 
		 * 
		 * 
		 */
		boolean flag = false;
		try {

			boolean ele = new WebDriverWait(driver, 800).until(ExpectedConditions.and(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty(object))),
					ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty(object)))));
			if (ele == true) {

				WebElement element = driver.findElement(By.xpath(prop.getProperty(object)));

				Actions action = new Actions(driver);
				System.out.println("is this the element???>>" + element);
				action.moveToElement(element).click(element).build().perform();
				Thread.sleep(5000);
				// action.moveToElement(element).sendKeys(Keys.RETURN);
				// driver.findElement(By.xpath(prop.getProperty(object))).click();

				flag = true;
			} else {
				System.out.println("run engine is not clicked..");
				flag = false;
			}

		} catch (TimeoutException e) {

			System.out.println("Are you sure the element is present");
			flag = false;
			// TODO: handle exception
		} catch (Exception e) {
			System.out.println("element is not present");
			flag = false;
			// TODO: handle exception
		}

		// driver.findElement(By.xpath(prop.getProperty(object))).click();

		// using action class
		/*
		 * WebElement element = driver.findElement(By.xpath(prop.getProperty(object)));
		 * Actions action = new Actions(driver);
		 * action.moveToElement(element).click().perform();
		 */

		/*
		 * using javascript execurtor WebElement element=
		 * driver.findElement(By.xpath(prop.getProperty(object)));
		 * 
		 * JavascriptExecutor executor = (JavascriptExecutor) driver;
		 * executor.executeScript("arguments[0].click();", element);
		 */

		// TODO: handle exception
		return flag;
	}

	public boolean ToggleButtonClick(String object) {
		/*
		 * @author :Deepa Panikkaveetil
		 * 
		 * @date :3/19/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :used for clicking the toggleButton
		 * 
		 * @Parameters:Passing the xpath locator from the properties file
		 * 
		 * 
		 * 
		 */

		boolean flag = false;
		try {
			WebElement ele = new WebDriverWait(driver, 30)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(object))));
			ele.click();
			flag = true;
		} catch (Exception e) {
			flag = false;
			// TODO: handle exception
		}
		return flag;

	}

	public boolean SwitchNclick(String object) {

		boolean flag = false;

		/*
		 * @author :Deepa Panikkaveetil
		 * 
		 * @date :3/19/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to clik on any web element after switching back to
		 * default frame inside a frame, separated from the click method , because the
		 * 'switch to frame' is doing inside this method
		 * 
		 * @Parameters:Passing the xpath locator from the properties file
		 * 
		 * 
		 * 
		 */
		driver.switchTo().parentFrame();
		// Object ele=object;
		try {
			new WebDriverWait(driver, 300)
					.until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty(object))));
			driver.findElement(By.xpath(prop.getProperty(object))).click();
			flag = true;
		} catch (Exception e) {

			flag = false;
			// TODO: handle exception
		}
		return flag;
	}

	public boolean enterText(String object, String data) {

		boolean flag = false;

		/*
		 * @author:Deepa Panikkaeetil
		 * 
		 * @date:3/20/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for typing the data in the web element/input text
		 * 
		 * @Parameter:Passing the web element xpath, and the data to be typed
		 */
		try {
			WebElement ele = new WebDriverWait(driver, 90)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(object))));
			ele.clear();
			ele.sendKeys(data.trim());
			flag = true;
		} catch (Exception e) {
			flag = false;
			// TODO: handle exception
		}

		return flag;
	}

	public boolean SelectListItem(String object, String item) {

		boolean flag = false;

		try {
			WebElement ele = new WebDriverWait(driver, 30)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(object))));

			Select selectItem = new Select(ele);

			selectItem.selectByVisibleText(item.trim());
			flag = true;
		} catch (Exception e) {
			flag = false;
			// TODO: handle exception
		}

		return flag;

	}

	public boolean rollBackToBaselineVersion(String versionId) {

		boolean flag = false;

		// below code switch to the frame

		try {
			// driver.switchTo().frame("PegaGadget2Ifr");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			flag = false;
			return flag;
		}
		// driver.switchTo().frame("PegaGadget3Ifr");
		WebElement btnRollbackTab = null;

		// code to identify the tab with the version id
		try {
			btnRollbackTab = new WebDriverWait(driver, 40).until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//div[@id='version-container']/div[@versionid='"
							+ versionId.trim() + "']/preceding-sibling::button[@type='button']")));

			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			System.out.println("Rollback button is not present");
			return flag;
		}
		// code to the button of the baseline version tab
		try {
			btnRollbackTab.click();
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			System.out.println("Rollback button didnt click");
			return flag;
		}

		// locating the Rollback button

		WebElement btnRollback = null;

		try {
			btnRollback = new WebDriverWait(driver, 40).until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@versionid='" + versionId.trim() + "']//a[contains(text(),'Rollback To')]")));
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;

			System.out.println("Rollback button is not present");
			return flag;
		}

		try {
			btnRollback.click();
			Thread.sleep(5000);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			System.out.println("Rollback button is not clicked" + btnRollback);
		}

		return flag;

	}

	public boolean openURL() {
		/*
		 * @author:Deepa Panikkaeetil
		 * 
		 * @date:3/20/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for launching the web url , the url has be specified in
		 * the properties file
		 * 
		 * @Parameter:NA
		 * 
		 * 
		 */
		// initialization();
		boolean flag = false;
		String osName = System.getProperty("os.name").trim();
		String downloadFilepath = null;

		ChromeOptions options = null;
		try {

			if (osName.equalsIgnoreCase("Linux")) {
				// System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver"); //
				// added the new path for linux
				System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
				downloadFilepath = System.getProperty("user.dir") + "/Downloads";
				options = new ChromeOptions();
				// options.addArguments("--headless");
			} else {
				System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
				downloadFilepath = System.getProperty("user.dir") + "\\Downloads";
				options = new ChromeOptions();

			}
			System.out.println("download path " + downloadFilepath);

			// String downloadFilepath = prop.getProperty("DOWNLOADPATH");

			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			// ChromeOptions options = new ChromeOptions();

			options.setExperimentalOption("prefs", chromePrefs);

			// added the below 2 lines on 5/2/19

			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			// options.addArguments("--headless");
			// options.addArguments("window-size=1200x600");
			options.addArguments("--disable-gpu");

			try {
				driver = new ChromeDriver(options);// some exception is coming hre

			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("while driver = new ChromeDriver(options)");
				e.printStackTrace();
			}
			// driver.manage().window().setSize(new Dimension(1920, 1200));
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();

			driver.get(prop.getProperty("url"));

			// System.out.println("getpageSource of Login>>" + driver.getPageSource());

			flag = true;
		} catch (TimeoutException te) {

			flag = false;
			Reporter.log("Failed in openURL");
			return flag;

		} catch (Exception e) {
			flag = false;
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	public boolean quitBrowser() {

		boolean flag = false;

		/*
		 * @author:Deepa Panikkaeetil
		 * 
		 * @date:3/20/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for quiting the browser
		 * 
		 * @Parameter:NA
		 * 
		 * 
		 * 
		 */
		try {
			driver.quit();
			System.out.println("Driver quit");
			flag = true;
		} catch (Exception e) {
			flag = false;
			// TODO: handle exception
		}
		return flag;
	}

	public boolean closeBrowser() {

		boolean flag = false;

		/*
		 * @author:Deepa Panikkaeetil
		 * 
		 * @date:4/25/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for closing the browser
		 * 
		 * @Parameter:NA
		 * 
		 * 
		 * 
		 */
		try {
			driver.close();
			flag = true;
		} catch (Exception e) {
			flag = false;
			// TODO: handle exception
		}
		return flag;
	}

	public boolean gettingHandle() {

		/*
		 * @author:Deepa Panikkaeetil
		 * 
		 * @date:3/20/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for getting the current handle of the browser to switch
		 * the driver instance
		 * 
		 * @Parameter:NA
		 * 
		 * 
		 * 
		 */

		boolean flag = false;
		try {

			Set<String> handles = driver.getWindowHandles();
			String currentHandle = driver.getWindowHandle();

			System.out.println(currentHandle);

			for (String handle : handles) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// System.out.println(handle);

				if (!handle.equals(currentHandle)) {
					driver.switchTo().window(handle);
					System.out.println(handle);
				}
			}
			flag = true;
		} catch (Exception e) {

			flag = false;
			// TODO: handle exception
		}
		return flag;
	}

	public boolean sleepTime(long time) {

		boolean flag = true;
		/*
		 * @author:Deepa Panikkaeetil
		 * 
		 * @date:3/20/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for putting the wait during the run
		 * 
		 * @Parameter:NA
		 * 
		 * 
		 * 
		 */
		try {
			Thread.sleep(time);
			flag = true;
		} catch (InterruptedException e) {
			flag = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deSelectCheckboxes(String object) {
		/*
		 * @author:Deepa Panikkaveetil
		 * 
		 * @date:4/05/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for unchecking the check boxes
		 * 
		 * @Parameter:Passing the xpath of the web elements
		 */

		List<WebElement> checkBoxes = driver.findElements(By.xpath(prop.getProperty(object)));
		int limit = checkBoxes.size();
		boolean flag = false;

		// below code leaves the OBCC checked as its mandatory to keep one checked for
		// the channel
		try {
			for (int i = 1; i < limit; i++) {
				if (checkBoxes.get(i).isSelected()) {
					checkBoxes.get(i).click();
				}
			}
			flag = true;
		} catch (Exception e) {

			// TODO Auto-generated catch block
			flag = false;
		}

		return flag;

	}

	public boolean checkboxSelect(String object) {
		/*
		 * @author:Deepa Panikkaveetil
		 * 
		 * @date:4/05/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for unchecking the check boxes
		 * 
		 * @Parameter:Passing the xpath of the web elements
		 */
		boolean flag = false;

		WebElement chkBox = null;
		try {
			chkBox = new WebDriverWait(driver, 50)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(object))));
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
		}

		if (!chkBox.isSelected()) {
			chkBox.click();
			flag = true;
		}

		return flag;
	}

	public boolean checkboxUncheck(String object) {
		/*
		 * @author:Deepa Panikkaveetil
		 * 
		 * @date:4/05/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for unchecking the check boxes
		 * 
		 * @Parameter:Passing the xpath of the web elements
		 */
		boolean flag = false;

		WebElement chkBox = null;
		try {
			chkBox = new WebDriverWait(driver, 50)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(object))));
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
		}

		if (chkBox.isSelected()) {
			chkBox.click();
			flag = true;
		}

		return flag;

	}

	public boolean selectRadioButton(String object) {
		/*
		 * @author:Deepa Panikkaveetil
		 * 
		 * @date:4/05/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for unchecking the check boxes
		 * 
		 * @Parameter:Passing the xpath of the web elements
		 */

		Boolean ele = false;
		boolean flag = false;

		for (int i = 0; i < 2; i++) {
			try {
				ele = new WebDriverWait(driver, 200).until(ExpectedConditions
						.and(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty(object)))));

			} catch (Exception e) {
				// TODO Auto-generated catch block

				Reporter.log("Element" + object + "is not present");
				System.out.println("Element" + object + "is not present");

			}
		}

		if (ele == true) {

			if (!driver.findElement(By.xpath(prop.getProperty(object))).isSelected()) {

				driver.findElement(By.xpath(prop.getProperty(object))).click();
				flag = true;

			}
		} else {
			flag = false;
			return flag;
		}
		return flag;
	}

	// Below code dynamically creates the xpath for the trigger check box during the
	// event creation
	public boolean checkTriggerEvent(String titleOftheEvent) {
		/*
		 * @author:Deepa Panikkaveetil
		 * 
		 * @date:4/05/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for unchecking the check boxes
		 * 
		 * @Parameter:Passing the xpath of the web elements
		 */
		boolean flag = false;
		WebElement chkTrigger = null;
		try {
			chkTrigger = new WebDriverWait(driver, 40).until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("(//tr/td/div[@title='" + titleOftheEvent.trim() + "']/following::td/input)[1]")));
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			System.out.println("element is not located" + chkTrigger);
			return flag;
		}
		if (!chkTrigger.isSelected()) {
			chkTrigger.click();
			flag = true;
		}

		return flag;

	}

	public boolean keyPressEnter(String object) {
		/*
		 * @author:Deepa Panikkaveetil
		 * 
		 * @date:4/05/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for keyPressEnter
		 * 
		 * @Parameter:Passing the xpath of the web elements
		 */
		boolean flag = false;
		try {
			driver.findElement(By.xpath(prop.getProperty(object))).sendKeys(Keys.ENTER);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("KeyPressEnter didn't complete");
			flag = false;
		}

		return flag;

	}

	public boolean byPassArbitrationSelection(String offerName, String eventName) {

		/*
		 * @author:Deepa Panikkaveetil
		 * 
		 * @date:4/05/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for keyPressEnter
		 * 
		 * @Parameter:Passing the xpath of the web elements
		 */

		boolean flag = false;

		// below code locate the toggle button for arbitration
		WebElement btnArbitration = null;
		try {
			btnArbitration = new WebDriverWait(driver, 30)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td[contains(text(),'"
							+ offerName.trim() + "')]//following::div/button[@id='bypass-arb'])[1]")));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			flag = false;
			return flag;

		}
		if (btnArbitration.getAttribute("autocomplete").equals("off")) {
			btnArbitration.click();
			flag = true;
		}

		// below code locate the inputbox for the event to select

		WebElement txtEvent = null;
		try {
			txtEvent = new WebDriverWait(driver, 30)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("((//h3[contains(text(),'" + offerName
							+ " Arbitration')]/parent::div/parent::div)/following-sibling::div//input)[3]")));
			flag = true;
		} catch (Exception e) {
			flag = false;
			System.out.println("input text is not located:" + txtEvent);
			return flag;

		}

		// below code select the event name

		try {
			txtEvent.sendKeys(eventName);
			txtEvent.sendKeys(Keys.ENTER);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			System.out.println("Entering event failed");
			return flag;
		}

		return flag;

	}

	public boolean elementPropertyCheck(String object, String expProperty) {

		/*
		 * @author:Deepa Panikkaveetil
		 * 
		 * @date:3/20/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for checking the text in the web element
		 * 
		 * @Parameter:Passing the xpath of the web element , and the expected text in
		 * the element
		 */
		boolean flag = false;
		try {
			boolean stat = new WebDriverWait(driver, 300)
					.until(ExpectedConditions.textToBe(By.xpath(prop.getProperty(object)), expProperty.trim()));
			if (stat == true) {
				flag = true;
			} else {
				flag = false;
				return flag;
			}
		} catch (TimeoutException e) {
			flag = false;
			System.out.println("Are you sure the element text has changed to??" + expProperty);
			// TODO: handle exception
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			// TODO: handle exception
		}

		return flag;
	}

	public boolean ValidateBatchDecisionOutputCSV(String testCaseID) {

		/*
		 * @author:Deepa Panikkaveetil
		 * 
		 * @date:3/20/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for validating the downloded csv
		 * 
		 * @Parameter:Passing the xpath of the web element , and the expected text in
		 * the element
		 */

		GeneralUtilities generalUtilities = null;
		String ColumnToBeValidated = null;
		String ValueToBeChecked = null;
		String ExpectedResult = null;
		String osName = System.getProperty("os.name").trim();
		try {
			generalUtilities = new GeneralUtilities();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExcelUtils ObjTestdataFile = null;
		try {
			if (osName.equalsIgnoreCase("Linux")) {
				ObjTestdataFile = new ExcelUtils("TestDataAndResults/Run1/SophieAutomation.xlsx");
			} else {

				ObjTestdataFile = new ExcelUtils("TestDataAndResults\\Run1\\SophieAutomation.xlsx");// for windows
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean flag = false;
		System.out.println("This is the csv validation");

		File file = generalUtilities.getLatestFileFromDir("Downloads", "BatchDecisionOutput");
		if (file == null) {

			return false;
		} else {
			int CsvColumnCount = 0;

			// Below code is to get the column count for the downloaded csv
			try {
				CsvColumnCount = generalUtilities.toGetTheNumberOfFieldsInCSV(file);
				System.out.println(CsvColumnCount);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			CSVReader csvReader = null;
			try {
				csvReader = new CSVReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<String[]> records = null;
			try {
				records = csvReader.readAll();
				csvReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// try {
			int iTestStart = 0;
			int iTestEnd = 0;

			// for (int it = 4; it <=
			// ObjTestdataFile.getRowCount("BatchDecisionOutputValidations"); it++) {

			try {
				iTestStart = ObjTestdataFile.getRowContains(testCaseID, "TestCaseID", "BatchDecisionOutputValidations");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("method getRowContains fails ");
			}
			try {
				iTestEnd = ObjTestdataFile.getTestStepsCount("BatchDecisionOutputValidations", testCaseID, iTestStart,
						"TestCaseID");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("method getTestStepsCount fails ");
			}
			// }
			for (int i = iTestStart; i <= iTestEnd; i++) {
				ColumnToBeValidated = ObjTestdataFile.getCellData("BatchDecisionOutputValidations", i,
						"ColumnToBeValidated");

				ValueToBeChecked = ObjTestdataFile.getCellData("BatchDecisionOutputValidations", i, "ValueToBeChecked");
				ExpectedResult = ObjTestdataFile.getCellData("BatchDecisionOutputValidations", i, "Expected");

				for (int j = 0; j < CsvColumnCount; j++) {

					if (records.get(0)[j].equals(ColumnToBeValidated.trim())) {
						for (int k = 1; k < records.size(); k++) {
							if (records.get(k)[j].equals(ValueToBeChecked.trim())) {
								// ObjTestdataFile.setCellData("BatchDecisionOutput_Test", "Actual", i, "Pass");
								if (ExpectedResult.trim().equalsIgnoreCase("Available")) {
									flag = true;
								} else {
									flag = false;
								}
								break;
							} else {
								// ObjTestdataFile.setCellData("BatchDecisionOutput_Test", "Actual", i, "Fail");
								if (ExpectedResult.trim().equalsIgnoreCase("Not Available")) {
									flag = true;
								} else {
									flag = false;
								}

							}
						}

						break;
					}
				}

				if (flag == true) {
					ObjTestdataFile.setCellData("BatchDecisionOutputValidations", "Actual", i, "Pass");
				} else {
					ObjTestdataFile.setCellData("BatchDecisionOutputValidations", "Actual", i, "Fail");
				}
			}

			// The below code will move the downloaded CSV from Downloads to

			try {

				// here should do the code to move the file to Archive
				File dest = null;
				if (osName.equalsIgnoreCase("Linux")) {
					dest = new File("Downloads/Archive/");
				} else {
					dest = new File("Downloads\\Archive\\");
				}

				if (generalUtilities.CopyFile(file, dest)) {
					System.out.println("File moved to archive");
				} else {
					System.err.println("File move failed");
				}

				// return true;
			}

			catch (Exception e) {

				// return false;
				// TODO: handle exception
			}

			return true;
		}

		// catch (Exception e) {

		// return false;
		// TODO: handle exception
		// }

	}

	public boolean RealtimeEventGetAPi(int partyID, String eventName) {

		/*
		 * @author:Jagadish Reddy
		 * 
		 * @date:4/23/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USED_FOR:Method used for calling the event API
		 * 
		 * @Parameter:Passing the partyID and eventName
		 */

		boolean flag = false;
		// String URL = null;

		String PartyID = Integer.toString(partyID);
		// String PartyID = "123461";
		// String eventName1 = "regression_event";

		RestAssured.authentication = RestAssured.preemptive().basic(prop.getProperty("apiUser"),
				prop.getProperty("apiPasswd"));

		String URL = prop.getProperty("eventAPIURL") + PartyID + "&context=event=" + eventName;

		// String URL
		// ="http://auto-test-mavis74.adqura.com:80/prweb/PRRestService/AdquraNBAServices/1/triggerevent?partyid="+PartyID+"&context=event="+Event_name;

		RestAssured.baseURI = URL;

		Response response = get(baseURI);

		JsonPath jsonPathEvaluator = response.jsonPath();
		if (response.getStatusCode() == 200) {

			String Data_reponse = response.asString();

			System.out.println(Data_reponse);
			ResponseBody body = response.getBody();
			String responseBody = body.asString();
			// JsonPath jsonPathEvaluator1 = response.jsonPath();
			if (jsonPathEvaluator.get("Message") == null) {
				System.out.println(
						"Trigger event sucess with Message with  IsFailed is " + jsonPathEvaluator.get("IsFailed"));

			}

			else {
				flag = false;

				System.out.println(
						"Trigger event Fail with  Message with IsFailed is " + jsonPathEvaluator.get("IsFailed"));
				Reporter.log("Response has message" + responseBody);
				return flag;

			}
			flag = true;

		} else {
			flag = false;
			System.out.println("Get request fail due to  status code " + response.getStatusCode());
			return flag;
		}
		return flag;

	}

	public boolean switchToFrame(String frameID) {
		/*
		 * @author :Deepa Panikkaveetil
		 * 
		 * @date :4/25/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to switch to the frame if the element is in different
		 * frame
		 * 
		 * @Parameters:Passing the frameId/name
		 * 
		 * 
		 * 
		 */
		boolean flag = false;

		try {
			Thread.sleep(5000);
			driver.switchTo().frame(frameID);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			return flag;
		}

		return flag;
	}

	public boolean switchToChildFrame(String mainFrame, String childFrame) {
		/*
		 * @author :Deepa Panikkaveetil
		 * 
		 * @date :4/26/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to switch to the frame if the element is in different
		 * frame
		 * 
		 * @Parameters:Passing the frameId/name
		 * 
		 * 
		 * 
		 */
		boolean flag = false;
		try {
			driver.switchTo().frame("childFrame.0.mainFrame");
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			return flag;
		}

		return flag;
	}

	public boolean switchToParent() {
		/*
		 * @author :Deepa Panikkaveetil
		 * 
		 * @date :4/26/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to switch to the frame if the element is in different
		 * frame
		 * 
		 * @Parameters:Passing the frameId/name
		 * 
		 * 
		 * 
		 */
		boolean flag = false;
		try {
			driver.switchTo().defaultContent();
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			return flag;
		}

		return flag;
	}

	public boolean switchToNewWindow(int windowNumber) {

		/*
		 * @author :Deepa Panikkaveetil
		 * 
		 * @date :4/25/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to switch to the new window
		 * 
		 * @Parameters:Passing the window number 1,2,3....
		 * 
		 * 
		 * 
		 */

		boolean flag = false;

		try {
			Set<String> s = driver.getWindowHandles();
			Iterator<String> ite = s.iterator();
			int i = 1;
			while (ite.hasNext() && i < 10) {
				String popupHandle = ite.next().toString();
				driver.switchTo().window(popupHandle);
				System.out.println("Window title is : " + driver.getTitle());
				if (i == windowNumber)
					break;
				i++;
			}
			flag = true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			return flag;
		}
		return flag;
	}

	public boolean mouseOver(String object) {
		/*
		 * @author :Deepa Panikkaveetil
		 * 
		 * @date :4/26/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to perform the mouseover on element
		 * 
		 * @Parameters:Passing the window the element locator
		 * 
		 * 
		 * 
		 */
		boolean flag = false;
		Actions actions = new Actions(driver);

		WebElement ele;
		try {
			ele = new WebDriverWait(driver, 50)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(object))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			return false;
		}

		if (ele != null) {
			actions.moveToElement(ele).build().perform();
			flag = true;

		}
		return flag;
	}

	public boolean maximizeWindow() {
		/*
		 * @author :Deepa Panikkaveetil
		 * 
		 * @date :4/26/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to maximize the window
		 * 
		 * @Parameters:NA
		 * 
		 * 
		 */
		boolean flag = false;
		try {
			driver.manage().window().maximize();
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			return flag;
		}
		return flag;
	}

	public boolean hiddenClick(String TestData) {
		/*
		 * @author :jagadeesh
		 * 
		 * @date :4/26/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to perform the click on hidden element
		 * 
		 * @Parameters:Passing the object the element locator
		 * 
		 * 
		 * 
		 */
		boolean flag = false;
		Actions actions = new Actions(driver);

		WebElement ele;
		try {
			String xpathfor_delete = "//li[@title=\'" + TestData + "\']//span[@title=\"Edit/Delete Offer\"]";

			WebElement element = driver.findElement(By.xpath(xpathfor_delete));
			System.out.println("Hidden element find and it is " + element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
			flag = true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("unable to click due to" + e);
			flag = false;

		}

		return flag;
	}

}
