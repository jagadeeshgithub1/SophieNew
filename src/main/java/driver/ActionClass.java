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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
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
import io.restassured.specification.RequestSpecification;
import utilities.ExcelUtils;
import utilities.GeneralUtilities;

/**
 * @author deepa
 *
 */
public class ActionClass extends TestBaseClass {

	public static WebDriver driver = null;
	public static String downloadFilepath = null;
	public ChromeOptions options = null;
	public String osName = System.getProperty("os.name");
	Actions actions = null;
	public static String OfferName = null;

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

			System.out.println("title of the page:" + driver.getTitle());

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
			boolean ele = false;
			try {
				ele = new WebDriverWait(driver, 600).until(ExpectedConditions.and(
						ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty(object))),
						ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty(object)))));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				flag = false;

				System.out.print("Click failed>>" + e.getMessage());
				Reporter.log("Click failed>>" + e.getMessage());
				return flag;

			}

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
			System.out.println("exception" + e.getMessage());
			flag = false;
			e.printStackTrace();

			// TODO: handle exception
		}

		// TODO: handle exception
		return flag;
	}

	public boolean clickUsingJScript(String object) {
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
			boolean ele = new WebDriverWait(driver, 600).until(ExpectedConditions.and(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty(object))),
					ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty(object)))));

			if (ele == true) {

				WebElement element = driver.findElement(By.xpath(prop.getProperty(object)));

				// Actions action = new Actions(driver);
				System.out.println("is this the element???>>" + ele);

				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", element);

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

			boolean ele = new WebDriverWait(driver, 1800).until(ExpectedConditions.and(
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
			WebElement ele = new WebDriverWait(driver, 120)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(object))));

			ele.clear();
			Thread.sleep(5000);
			ele.sendKeys(data.trim());

			ele.sendKeys(Keys.TAB);
			flag = true;
		} catch (Exception e) {
			flag = false;
			System.out.println("Failed in entering the details");
			System.out.println(e.getMessage());
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

	public boolean rollBackToBaselineVersion() {

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

		boolean flag = false;

		String versionId = prop.getProperty("BaselineversionId");

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
			System.out.println("Rollback version is not located");
			return flag;
		}
		// code to the button of the baseline version tab
		try {
			btnRollbackTab.click();
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			System.out.println("Rollback tab didnt click");
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
			Thread.sleep(10000);
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			System.out.println("Rollback button is not clicked" + btnRollback);
		}

		WebElement rollbackExceptionele = null;
		try {
			rollbackExceptionele = new WebDriverWait(driver, 50).until(
					ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("spanRollbackException"))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element is not present");
		}

		if (rollbackExceptionele != null) {

			if (rollbackExceptionele.isDisplayed()) {

				System.out.println("Rollback failed!! with the error message as " + rollbackExceptionele.getText());

				Reporter.log("Rollback failed!! with the error message as " + rollbackExceptionele.getText());
				flag = false;

			}

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

		// ChromeOptions options = null;
		try {

			if (osName.equalsIgnoreCase("Linux")) {
				System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver"); //
				// added the new path for linux
				// System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
				// WebDriverManager.chromedriver()
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
			// options.setPageLoadStrategy(PageLoadStrategy.NONE);

			// added the below 2 lines on 5/2/19
			// options.addArguments("--remote-debugging-port=9222");
			// options.addArguments("--no-sandbox");
			// options.addArguments("--disable-dev-shm-usage");
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
			// driver.manage().window().setSize(new Dimension(1920, 1080));
			driver.manage().window().maximize();
			// driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(200, TimeUnit.SECONDS);

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
			Thread.sleep(5000);
			driver.close();
			driver.quit();
			System.out.println("Driver instance is  quitting ");
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
		 * @USED_FOR:Method used for selecting the radio buttons
		 * 
		 * @Parameter:Passing the xpath of the web elements
		 */

		WebElement ele = null;
		boolean flag = false;

		try {

			ele = new WebDriverWait(driver, 20)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(object))));

		} catch (Exception e) {
			// TODO Auto-generated catch block

			Reporter.log("Element" + object + "is not present" + e.getMessage());
			System.out.println("Element" + object + "is not present" + e.getMessage());

		}

		if (ele != null) {

			if (!driver.findElement(By.xpath(prop.getProperty(object))).isSelected()) {

				driver.findElement(By.xpath(prop.getProperty(object))).click();

			}
			flag = true;
		}
		return flag;
	}

	// Below code dynamically creates the xpath for the trigger check box during the
	// event creation
	public boolean checkEntitiesOfEvent(String titleOftheEvent, int index) {
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
			chkTrigger = new WebDriverWait(driver, 40).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"(//tr/td/div[@title='" + titleOftheEvent.trim() + "']/following::td/input)[" + index + "]")));
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
			Thread.sleep(7000);
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

	public boolean InProgress_ValidateBatchDecisionOutputCSV(String testCaseID) {

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
		boolean flag = false;
		boolean engineStatus = false;

		// Adding the run engine trigger section ,
		// verify the engine run done successfully or not,if successfull proceed, or
		// return fail

		click("btnRunEngine");

		try {
			engineStatus = new WebDriverWait(driver, 300).until(ExpectedConditions.textToBePresentInElementValue(
					By.xpath("//span[@data-test-id='SelectedEngineStatus']"), "FAILED!  Please check with support"));
			flag = false;
			return flag;
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			flag = true;
			System.out.println("Engine run didn't fail so far.." + e2.getMessage());

		}

		try {
			engineStatus = new WebDriverWait(driver, 300).until(ExpectedConditions
					.textToBePresentInElementValue(By.xpath("//span[@data-test-id='SelectedEngineStatus']"), "Paused"));
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			flag = false;

			System.out.println("Engine run didnt complete with the status Paused" + e2.getMessage());
			return flag;
		}

		// if "Paused" continue..

		if (engineStatus == true) {
			click("lnkClickToCheckData");

			if (driver.getPageSource().equalsIgnoreCase("Data Set BatchDecisionOutput is empty")) {
				flag = false;
				System.out.println("Dataset is empty");
				return flag;
			}

			click("lnkDownloadCSV");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			flag = false;
			return flag;
		}

		click("btnResumeRun");

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

	public boolean RealtimeEventGetAPiold(int partyID, String eventName) {

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

	public boolean RealtimeEventGetAPi(String offerName, String eventName) {

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

		String PartyID = pickEligiblePartyFromGrid(offerName);

		// clearing the ih

		try {
			switchToParent();
			click("toggleNavigation");
			Thread.sleep(3000);
			click("lnkTechAdmin");
			switchToFrameByIndex(2);
			selectRadioButton("radBtnAllForIH");
			Thread.sleep(5000);
			click("btnClearIH");
			click("btnResetEngStat");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("clearing the ih failed before calling the eventAPI");
		}

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

	public boolean switchToFrameByIndex(int frameIdx) {
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
			driver.switchTo().frame(frameIdx);

			int numOfbuttons = driver.findElements(By.tagName("button")).size();
			System.out.println("number of buttons in framebyindex :" + numOfbuttons);

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
			Thread.sleep(5000);
			// driver.switchTo().defaultContent();
			driver.switchTo().parentFrame();
			System.out.println("switched to parent>>>");

			// int numOfbuttons = driver.findElements(By.tagName("button")).size();
			// System.out.println("number of buttons in parent :" + numOfbuttons);

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

	public boolean ensureDataflowIsInProgress(String dataflowName) {

		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :5/27/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to ensure the df is inprogress
		 * 
		 * @Parameters:Passing the data flow name
		 * 
		 * 
		 * 
		 */
		boolean flag = false;
		WebElement dfStatus = null;

		try {
			dfStatus = new WebDriverWait(driver, 50)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text()," + dataflowName
							+ ")]//ancestor::td[@data-attribute-name='Data flow']//following-sibling::td[@data-attribute-name='Status']//a")));

			if (dfStatus != null) {

				// check the status of the dfStatus
				if (dfStatus.getText().trim().equalsIgnoreCase("Paused")
						|| dfStatus.getText().trim().equalsIgnoreCase("Stopped")) {
					Reporter.log("Df status is Paused");
					System.out.println("Df status is Paused");

					boolean Action = new WebDriverWait(driver, 20).until(ExpectedConditions.and(
							ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text()," + dataflowName
									+ ")]//ancestor::td[@data-attribute-name='Data flow']//following-sibling::td[@data-attribute-name='Action']//a")),
							ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text()," + dataflowName
									+ ")]//ancestor::td[@data-attribute-name='Data flow']//following-sibling::td[@data-attribute-name='Action']//a"))));

					if (Action == true) {

						driver.findElement(By.xpath("//a[contains(text()," + dataflowName
								+ ")]//ancestor::td[@data-attribute-name='Data flow']//following-sibling::td[@data-attribute-name='Action']//a"))
								.click();

						Thread.sleep(5000);

						WebElement spanResume = new WebDriverWait(driver, 20)
								.until(ExpectedConditions.presenceOfElementLocated(
										By.xpath("//span[@class='menu-item-title' and  text()='Resume']")));

						spanResume.click();// choosing resume
						Thread.sleep(30000);

						WebElement btnRefresh = new WebDriverWait(driver, 30).until(ExpectedConditions
								.elementToBeClickable(By.xpath("//Button[@type='button' and text()='Refresh']")));

						btnRefresh.click();// clicking refresh button

						Thread.sleep(20000);

						dfStatus = new WebDriverWait(driver, 20).until(ExpectedConditions
								.presenceOfElementLocated(By.xpath("//a[contains(text()," + dataflowName
										+ ")]//ancestor::td[@data-attribute-name='Data flow']//following-sibling::td[@data-attribute-name='Status']//a")));

						System.out.print("current status>>" + dfStatus.getText());

						if (dfStatus.getText().trim().equalsIgnoreCase("In progress")) {

							System.out.println("Data flow is in progress");

							Reporter.log("Data flow is in progress");

							flag = true;

						} else {
							System.out.println("Data flow is not in progress");

							Reporter.log("Data flow is not in progress");

							flag = false;
							return flag;

						}

					}

				} else {
					flag = true;
				}

			} else {
				Reporter.log(dfStatus + " is not present");
				flag = false;
				return flag;
			}
		} catch (Exception e) {

			System.out.println("Dataflow status change failed");

			Reporter.log("Dataflow status change failed");
			// TODO Auto-generated catch block
			e.printStackTrace();

			flag = false;
		}

		return flag;

	}

	public boolean closeTab(String tabName) {

		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :5/27/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to close the open tab
		 * 
		 * @Parameters:Passing the tab name
		 * 
		 * 
		 * 
		 */

		boolean flag = false;

		try {
			WebElement eleTab = new WebDriverWait(driver, 50).until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//table[@role='presentation'] //span[contains(text(),'"
							+ tabName + "')]/parent::td/following-sibling::td/span")));

			if (eleTab != null) {
				actions = new Actions(driver);

				actions.moveToElement(eleTab).build().perform();

				eleTab.click();

				flag = true;

			} else {
				flag = false;
				return flag;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Reporter.log("Closing tab failed");

			System.out.println("Closing tab failed");
			e.printStackTrace();
			flag = false;
		}

		return flag;

	}

	public boolean switchOnToggleButton(String object) {

		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :5/27/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to switch on the toggle button if not
		 * 
		 * @Parameters:Passing the objectName
		 * 
		 * 
		 * 
		 */

		boolean flag = false;

		try {
			WebElement ele = new WebDriverWait(driver, 50)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(object))));

			if (ele.getAttribute("class").equalsIgnoreCase("btn btn-toggle btn-lg")) {

				ele.click();

			}

			if (ele.getAttribute("class").equalsIgnoreCase("btn btn-toggle btn-lg active")) {

				flag = true;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			Reporter.log("Toggle button on failed");
			e.printStackTrace();
		}

		return flag;

	}

	public boolean switchOffToggleButton(String object) {

		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :5/27/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to switch off the toggle button if not
		 * 
		 * @Parameters:Passing the objectName
		 * 
		 * 
		 * 
		 */
		boolean flag = false;

		try {
			WebElement ele = new WebDriverWait(driver, 50)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(object))));

			if (ele.getAttribute("class").equalsIgnoreCase("btn btn-toggle btn-lg active")) {

				ele.click();

			}

			if (ele.getAttribute("class").equalsIgnoreCase("btn btn-toggle btn-lg")) {

				flag = true;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			Reporter.log("Toggle button off failed");
			e.printStackTrace();
		}

		return flag;

	}

	public boolean verifyLoadType(String object, String data) {

		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :5/27/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to verify the loadType with the 'data' passed
		 * 
		 * @Parameters:Passing the objectName
		 * 
		 * 
		 * 
		 */

		boolean flag = false;

		try {
			WebElement ele = new WebDriverWait(driver, 60)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(object))));

			if (ele.getAttribute("value").equalsIgnoreCase(data)) {

			} else {
				ele.clear();
				ele.sendKeys(data);

				WebElement btnUpdate = new WebDriverWait(driver, 60)
						.until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty("btnUpdate"))));

				btnUpdate.click();
			}

			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block

			flag = false;

			Reporter.log("Loadtype verification failed");
			System.out.println("Loadtype verification failed");
			e.printStackTrace();
		}

		return flag;

	}

	public boolean realtimeSpineAPIPostRequest() {
		/*
		 * jagadish Gopireddy
		 * 
		 * @date :5/27/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to do post api call for the spine records
		 * 
		 * @Parameters:NA
		 * 
		 * 
		 * 
		 */

		boolean flag = false;

		try {
			ExcelUtils excelUtil = null;

			String SpineType = null;
			String SpineDataset = null;
			String SpineData = null;

			if (osName.equalsIgnoreCase("Linux")) {
				excelUtil = new ExcelUtils("TestDataAndResults/TestData/RealTimeAutoAPI.xlsx");

			}

			else {
				excelUtil = new ExcelUtils("TestDataAndResults\\TestData\\RealTimeAutoAPI.xlsx");

			}

			int colCount = excelUtil.getColumnCount("RealtimeSpineData");

			int rowCount = excelUtil.getRowCount("RealtimeSpineData");

			for (int i = 1; i <= rowCount; i++) {
				JSONObject jsobj = new JSONObject();
				System.out.println(jsobj.toJSONString());
				// Row headerRow = sheet.getRow(0);

				// Row dataRow = sheet.getRow(i);
				for (int k = 0; k < colCount; k++) {
					System.out.println(excelUtil.getCellData(i, k, "RealtimeSpineData"));
					if (k == 2) {
						String abc = excelUtil.getCellData(i, k, "RealtimeSpineData");
						System.out.println("String value" + abc);
						Base64.Encoder encoder = Base64.getEncoder();
						String str = encoder.encodeToString(abc.getBytes());
						System.out.println("Encoded value" + str);

						jsobj.put(excelUtil.getCellData(0, k, "RealtimeSpineData"), str);

					} else {
						jsobj.put(excelUtil.getCellData(0, k, "RealtimeSpineData"),
								excelUtil.getCellData(i, k, "RealtimeSpineData"));
					}
				}
				RestAssured.authentication = RestAssured.preemptive().basic(prop.getProperty("apiUser"),
						prop.getProperty("apiPasswd"));
				RequestSpecification request = RestAssured.given();
				request.headers("Content-Type", "application/json");
				RequestSpecification before = request.body(jsobj.toJSONString());
				// System.out.println(before);

				Response res = request.post("http://auto-test-mavis74.adqura.com:7003/stream/RTStreamDataSet");

				if (res.getStatusCode() == 202) {

					flag = true;
					System.out.println("Post sucessfully complted and status code is" + res.getStatusCode());

				} else {
					System.out.println("Failed due and status code is  " + res.getStatusCode());
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

			flag = false;
			e.printStackTrace();
		}

		return flag;

	}

	public String pickEligiblePartyFromGrid(String offerName) {

		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :5/27/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to pick the eligible party for the offer
		 * 
		 * @Parameters:Passing the offer name
		 * 
		 * 
		 * 
		 */

		String partyId = null;

		WebElement eleOffer = new WebDriverWait(driver, 90).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//td[@data-attribute-name='Offer Name']//span")));

		if (eleOffer != null) {
			if (eleOffer.getText().equalsIgnoreCase(offerName + "_Email")) {

				WebElement eleParty = new WebDriverWait(driver, 90)
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='" + offerName
								+ "_Email']//ancestor::td[@data-attribute-name='Offer Name']//preceding-sibling::td[@data-attribute-name='CustomerID']//span")));
				if (eleParty != null) {

					partyId = eleParty.getText().trim();
					Reporter.log("Eligible Party for the offer" + offerName + "is " + partyId);
					System.out.println("Eligible Party for the offer" + offerName + "is " + partyId);

				}

			} else {
				partyId = null;
				Reporter.log("No parties eligible for " + offerName);
				System.out.println("No parties eligible for " + offerName);
				return partyId;
			}
		}
		return partyId;

	}

	public boolean verifyLabelText(String expectedText) {

		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :6/07/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to verify the label text
		 * 
		 * @Parameters:Passing the expected text in the element
		 * 
		 * 
		 * 
		 */

		boolean flag = false;

		try {
			// WebElement element =
			// driver.findElement(By.xpath("//div[@class='col-md-6']//label[text()='"+expectedText+"']"));
			WebElement element = new WebDriverWait(driver, 100).until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class='col-md-6']//label[text()='" + expectedText + "']")));

			if (element != null) {
				flag = true;
				System.out.println("New attribute " + expectedText + " is added in the template");
			} else {
				System.out.println("New attribute " + expectedText + " is not added in the template");

				Reporter.log("New attribute " + expectedText + " is not added in the template");
				flag = false;
				return flag;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;

			System.out.println("Exception while verifyLabelText " + e.getMessage());
			Reporter.log("Exception while verifyLabelText " + e.getMessage());
			// e.printStackTrace();
			return flag;
		}
		return flag;
	}

	public boolean verfyInputTextValue(String expectedValue) {

		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :6/07/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :The method is to verify the input text value
		 * 
		 * @Parameters:Passing the expected text in the element
		 * 
		 * 
		 * 
		 */

		boolean flag = false;

		WebElement element = new WebDriverWait(driver, 100).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//div[@class='col-md-6']//input[@value='" + expectedValue + "']")));

		if (element != null) {

			flag = true;
			System.out.println("New attribute has " + expectedValue + " in the template");

		} else {
			System.out.println("New attribute doesn't have  " + expectedValue + " in the template");

			Reporter.log("New attribute " + expectedValue + " is not added in the template");
			flag = false;
			return flag;

		}

		return flag;
	}

	public boolean pageScrollDownToView() {

		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :6/19/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :scroll down the page if require to see the element
		 * 
		 * @Parameters:NA
		 * 
		 * 
		 * 
		 */
		boolean flag = false;

		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("window.scrollBy(0,1000)");

			flag = true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	public boolean pageScrollUpToView() {

		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :6/25/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :scroll Up the page if require to see the element
		 * 
		 * @Parameters:NA
		 * 
		 * 
		 * 
		 */
		boolean flag = false;

		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("window.scrollBy(0,-7000)"); // positve on ynum will scroll down , -ve will scroll up

			flag = true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	public boolean verifyDBSpecificColumnExistsOrNot(String sql, String colName) {

		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :6/20/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :to perform the db validation to check specific column added
		 * 
		 * @Parameters:passing the sqlstring and column to be verified
		 * 
		 * 
		 * 
		 */

		// String SQLstr = sql;

		ResultSet resultSet = null;
		ResultSetMetaData metaData = null;
		boolean exist = false, flag = false;
		int numCol = 0;
		try {
			DBConnectivity();
			resultSet = statement.executeQuery(sql);
			metaData = resultSet.getMetaData();
			numCol = metaData.getColumnCount();
			for (int i = 1; i <= numCol; i++) {

				if (metaData.getColumnName(i).equalsIgnoreCase(colName)) {
					exist = true;
					break;

				}

			}
			if (exist == true) {
				System.out.println(colName + " exists in the db");
				Reporter.log(colName + " exists in the db");
				flag = true;
			} else {
				System.out.println(colName + " doesn't exist in the db");
				Reporter.log(colName + " doesn't exist in the db");
				flag = false;
				return flag;

			}
		} catch (SQLException e) {

			System.out.println("Failed in db validation" + e.getMessage());
			// TODO Auto-generated catch block
			Reporter.log("Failed in db validation" + e.getMessage());
			flag = false;
		}
		return flag;

	}

	public boolean verifyColumnRemovedFromDB(String sql, String colName) {

		/*
		 * @author :DeepaPanikkavetil
		 * 
		 * @date :6/27/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :to perform the validation to check the db doesn't specified column
		 * 
		 * @Parameters:sql and specific column name
		 * 
		 */

		ResultSet resultSet = null;
		ResultSetMetaData metaData = null;
		boolean exist = false, flag = false;
		int numCol = 0;
		try {
			DBConnectivity();
			resultSet = statement.executeQuery(sql);
			metaData = resultSet.getMetaData();
			numCol = metaData.getColumnCount();
			for (int i = 1; i <= numCol; i++) {

				if (metaData.getColumnName(i).equalsIgnoreCase(colName)) {
					exist = true;
					break;

				}

			}
			if (exist == true) {
				System.out.println(colName + " exists in the db");
				Reporter.log(colName + " exists in the db");
				flag = false;
				return flag;
			} else {
				System.out.println(colName + " doesn't exist in the db");
				Reporter.log(colName + " doesn't exist in the db");
				flag = true;

			}
		} catch (SQLException e) {

			System.out.println("Failed in db validation" + e.getMessage());
			// TODO Auto-generated catch block
			Reporter.log("Failed in db validation" + e.getMessage());
			flag = false;
		}
		return flag;

	}

	public boolean verifyColumnInPegaClass(String colName) {

		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :6/24/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :to perform the validation to check the pegaClass has new column
		 * added enabled
		 * 
		 * @Parameters:NA
		 * 
		 * 
		 * 
		 */

		boolean flag = false;

		WebElement ele = null;
		try {
			ele = new WebDriverWait(driver, 200)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='" + colName + "']")));
		} catch (Exception e) {
			flag = false;
			// TODO Auto-generated catch block
			Reporter.log("New column " + colName + " is added to the class" + e.getMessage());
			System.out.println("New column " + colName + " is added to the class");
			return flag;
		}

		if (ele != null) {
			flag = true;
			System.out.println("New column " + colName + " is added to the class");
		}
		return flag;

	}

	public boolean discardCurrentVersionIfCheckedOut() {

		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :6/26/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :to perform the validation to check the 'Discard' button is not
		 * enabled, if, discard
		 * 
		 * @Parameters:NA
		 * 
		 * 
		 * 
		 */

		boolean flag = false;
		WebElement ele = null;

		try {
			ele = new WebDriverWait(driver, 40)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("btnDiscard"))));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// System.out.println("No version is checked out");
			flag = true;
			System.out.println("No verison is checked out");
		}

		if (ele != null) {
			System.out.println("Version is checked out!! lets discard");
			ele.click();
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flag = true;

		} else {
			System.out.println("Version is not  checked out!! Good to go");
			flag = true;

		}
		return flag;

	}

	public boolean verifyOfferisNotPresentInCurrentVersion(String offerName, String expectedResult)

	{
		/*
		 * @author :Deepa Panikkavetil
		 * 
		 * @date :6/26/2019
		 * 
		 * @modified by:
		 * 
		 * @modified date:
		 * 
		 * @USEFOR :verify the offer is not present
		 * 
		 * @Parameters:NA
		 * 
		 * 
		 * 
		 */

		boolean flag = false;

		WebElement ele = null;
		try {
			ele = new WebDriverWait(driver, 50)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@title='" + offerName + "']")));

			if (expectedResult.equalsIgnoreCase("Yes")) {
				flag = true;
			} else {
				flag = false;
				System.out.println("Offer" + offerName + " is not present");
				Reporter.log("Offer" + offerName + " is not present");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (expectedResult.equalsIgnoreCase("No")) {
				flag = true;
			}

		}

		return flag;

	}

	public boolean verifyGenerateEngineCompleted() {

		boolean flag = false;

		try {
			WebElement ele = new WebDriverWait(driver, 600)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[text()='Load/Read From:']")));
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;

			System.out.println("Engine generation didnt complete!!" + e.getMessage());
			return flag;
		}
		return flag;

	}

	public boolean verifyExceptionIsNotThrown(String object) {
		boolean flag = true;

		try {
			WebElement exception = new WebDriverWait(driver, 60).until(
					ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("spanNewversionException"))));
			flag = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = true;
		}

		return flag;

	}

	public boolean rollbackToVersionByName(String versionName) {

		boolean flag = false;
		WebElement rollbackVersionTab = null;
		WebElement btnRollback = null;

		try {
			rollbackVersionTab = new WebDriverWait(driver, 60).until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("(//button[contains(text(),'" + versionName + "')])[1]")));
			rollbackVersionTab.click();
			btnRollback = new WebDriverWait(driver, 60)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//a[text()=' Rollback To '])[1]")));
			btnRollback.click();

			flag = true;

		} catch (Exception e) {
			flag = false;
			// TODO Auto-generated catch block
			System.out.println("Rollback version didnt locate" + e.getMessage());
			Reporter.log("Rollback version didnt locate" + e.getMessage());
			return flag;

		}

		return flag;
	}

	public boolean unSubscribeEmail() {

		boolean flag = false;

		WebElement offerNameSpan = null, Btnunsubscribe = null;
		WebElement lnkOpen = null, unSubLink = null, radBtnUnsubscribe = null;

		try {
			offerNameSpan = new WebDriverWait(driver, 60).until(
					ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("spanForFirstOffer"))));
		} catch (Exception e) {

			flag = false;
			// TODO Auto-generated catch block

			System.out.println("Outbound Email grid is empty??" + e.getMessage());

			Reporter.log("Outbound Email grid is empty??" + e.getMessage());

			return flag;

		}

		OfferName = offerNameSpan.getText();// getting the first row offer name

		try {
			lnkOpen = new WebDriverWait(driver, 60)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("lnkOpenOffer"))));
		} catch (Exception e) {

			flag = false;
			// TODO Auto-generated catch block
			System.out.println("Open link is not present??" + e.getMessage());

			Reporter.log("Open link is not present??" + e.getMessage());

			return flag;

		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		lnkOpen.click();

		try {
			unSubLink = new WebDriverWait(driver, 60)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("lnkUnsubscribe"))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			// TODO Auto-generated catch block
			System.out.println("UnSubscription link is not seen " + e.getMessage());

			Reporter.log("UnSubscription link is not seen" + e.getMessage());

			return flag;
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		unSubLink.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switchToNewWindow(3);// switching to a new window

		switchToFrame("MarketingMicrositeIfr");

		try {
			radBtnUnsubscribe = new WebDriverWait(driver, 60).until(
					ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("radBtnUnsubscribe"))));
		} catch (Exception e) {
			flag = false;
			// TODO Auto-generated catch block
			System.out.println("Usubscribe radio button is not visible " + e.getMessage());

			Reporter.log("Usubscribe radio button is not visible" + e.getMessage());

			return flag;
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		radBtnUnsubscribe.click();// select unsub radio button

		try {
			Btnunsubscribe = new WebDriverWait(driver, 60)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("Btnunsubscribe"))));
		} catch (Exception e) {

			flag = false;
			// TODO Auto-generated catch block
			System.out.println("Usubscribe  button is not visible " + e.getMessage());

			Reporter.log("Usubscribe  button is not visible" + e.getMessage());

			return flag;
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Btnunsubscribe.click();

		flag = true;

		return flag;

	}

	public boolean verifyAdaptiveModelForNegResponse(String response) {

		boolean flag = false;
		WebElement proposition = null, outCome = null;

		switchToParent();

		switchToFrameByIndex(1);

		try {
			proposition = new WebDriverWait(driver, 60).until(
					ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("divPropositionName"))));
		} catch (Exception e) {
			flag = false;
			// TODO Auto-generated catch block

			System.out.println("Proposition element is not located" + e.getMessage());
			Reporter.log("Proposition element is not located" + e.getMessage());
			return flag;
		}

		if (proposition.getText().trim().equalsIgnoreCase(OfferName)) {
			flag = true;
		} else {
			flag = false;
			System.out.println(
					"latest response is of different proposition  " + OfferName + "\tand" + proposition.getText());
			Reporter.log("latest response is of different proposition  " + OfferName + "\tand" + proposition.getText());
			return flag;
		}

		try {
			outCome = new WebDriverWait(driver, 60)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("divOutcome"))));
		} catch (Exception e) {

			flag = false;
			// TODO Auto-generated catch block
			System.out.println("Outcome is not present?? " + e.getMessage());

			Reporter.log("Outcome is not present?? " + e.getMessage());

			return flag;
		}

		if (outCome.getText().trim().equalsIgnoreCase(response)) {
			flag = true;

			System.out.println("Reponse matches" + outCome.getText() + "\tand" + response);
		} else {
			flag = false;
			System.out.println("latest response is not matching  " + outCome.getText() + "\tand" + response);
			Reporter.log("latest response is not matching  " + outCome.getText() + "\tand" + response);
			return flag;

		}

		return flag;

	}

	public boolean verifyIHRecordsAreAvailable(String expected) {

		boolean flag = false;
		WebElement ihTable = null;

		try {
			ihTable = new WebDriverWait(driver, 60)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//table[@id='bodyTbl_right'])[2]")));
		} catch (Exception e) {

			flag = false;

			System.out.println("IH table is not located" + e.getMessage());
			Reporter.log("IH table is not located" + e.getMessage());

			return flag;
			// TODO Auto-generated catch block

		}

		List<WebElement> tableRows = driver.findElements(By.xpath("(//table[@id='bodyTbl_right'])[2]//tr"));

		if (tableRows.size() > 2) {

			if (expected.equalsIgnoreCase("Yes")) {
				flag = true;
			} else {
				flag = false;
				System.out.println("IH is empty");
				Reporter.log("IH is empty");
				return flag;
			}

		} else {
			if (expected.equalsIgnoreCase("No")) {
				flag = true;

			} else {
				flag = false;
				System.out.println("IH should be empty");
				Reporter.log("IH should be empty");
				return flag;

			}

		}

		return flag;
	}

	public boolean enableOverride(String offerName) {

		boolean flag = false;
		WebElement chkOverride = null;
		try {
			chkOverride = new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//table[@class='offer-table table table-bordered table-hover dataTable']//td[text()='"
							+ offerName + "']//parent::tr//td//input[@type='checkbox']")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			System.out.println("Override check box is not located" + e.getMessage());
			Reporter.log("Override check box is not located" + e.getMessage());
			return flag;
		}

		if (!chkOverride.isSelected()) {
			chkOverride.click();
			flag = true;
		}

		return flag;

	}

	public boolean increaseWeightIfRequired(String offerName) {
		boolean flag = false;

		WebElement overridetable = null;
		int rowNumberOfOffer = 0;

		try {
			overridetable = new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//table[@class='override-table table table-bordered table-hover dataTable']")));
		} catch (Exception e) {
			flag = false;
			// TODO Auto-generated catch block

			System.out.println("override table is not located" + e.getMessage());

			Reporter.log("override table is not located" + e.getMessage());

			return flag;

		}

		// getting the number of rows from the table

		int numRows = 0;
		List<WebElement> rows = driver.findElements(
				By.xpath("//table[@class='override-table table table-bordered table-hover dataTable']//tr//td[2]"));

		numRows = rows.size();

		System.out.println("Number of rows>>" + numRows);

		if (numRows == 2) {
			flag = true;
		} else if (numRows > 2) {
			for (int i = 0; i < rows.size(); i++) {
				if (rows.get(i).getText().equalsIgnoreCase(offerName)) {
					rowNumberOfOffer = i + 1;

				}

			}

			Actions actions = new Actions(driver);
			WebElement To = null;

			try {
				To = new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By
						.xpath("//table[@class='override-table table table-bordered table-hover dataTable']//tr[2]")));
			} catch (Exception e) {

				flag = false;
				// TODO Auto-generated catch block
				System.out.println("Drop to element is not located" + e.getMessage());

				Reporter.log("Drop to element is not located" + e.getMessage());

				return flag;
			}
			WebElement From = null;
			try {
				From = new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//table[@class='override-table table table-bordered table-hover dataTable']//tr["
								+ rowNumberOfOffer + "]")));
			} catch (Exception e) {
				flag = false;
				// TODO Auto-generated catch block
				System.out.println("Drop From element is not located" + e.getMessage());

				Reporter.log("Drop From element is not located" + e.getMessage());

				return flag;
			}

			// actions.dragAndDrop(From, To).build().perform();
			actions.clickAndHold(From).moveToElement(To).release(To).build().perform();

		}

		return flag;

	}

}
