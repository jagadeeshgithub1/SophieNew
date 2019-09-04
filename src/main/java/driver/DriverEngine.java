/**
 * 
 */
package driver;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;

import org.testng.Reporter;

import base.TestBaseClass;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import utilities.ExcelUtils;

/**
 * @author
 *
 */
public class DriverEngine extends TestBaseClass {

	public static Method method[];
	public static String ActionKeyWord = null;
	public static String PageObject = null;
	public static String TestData = null;
	static ActionClass classAction = null;
	public ExcelUtils excelUtils = null;

	public DriverEngine() throws Exception {

		// method=actionClass.getClass().getMethods();
		// TODO Auto-generated constructor stub

	}

	/**
	 * 
	 * @param args
	 */
	public boolean mainMethod(String sheetName) {

		boolean Finalflag = true;

		int iEnd = 0;
		int iStart = 0;
		String RunMode = null;
		String TestCaseID = null;
		String Argument1 = null;
		int iTestStart = 0;
		int iTestEnd = 0;
		String osName = null;

		// ExcelUtils excelUtils = null;

		// actionClass classAction = null;
		/*
		 * try { classAction = new actionClass(); } catch (Exception e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */

		/*
		 * try { excelUtils = new
		 * ExcelUtils("TestDataAndResults\\SophieAutomation.xlsx"); } catch (Exception
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		// ExcelUtils excelUtils = null;

		osName = System.getProperty("os.name").trim();
		if (osName.equalsIgnoreCase("Linux")) {
			try {
				excelUtils = new ExcelUtils("TestDataAndResults/Run1/SophieAutomation.xlsx");
				// excelUtils = new
				// ExcelUtils("TestDataAndResults\\Run1\\SophieAutomation.xlsx");
			} catch (Exception e) {
				System.out.println("File didnt find in linux machine");
				Finalflag = false;

				return Finalflag;
			}
		}

		else

		{
			// excelUtils = new
			try {
				excelUtils = new ExcelUtils("TestDataAndResults\\Run1\\SophieAutomation.xlsx");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Finalflag = false;
				System.out.println("File didnt find in windows machine");
				return Finalflag;
			} // for windows
		}

		// TODO Auto-generated catch block
		// e.printStackTrace();

		// iTotalCases = excelUtils.getTotalScenarios("DriverSheet", 4, "TestCaseID");
		// iTotalCases = excelUtils.getRowCount("DriverSheet");
		try {
			iStart = excelUtils.getRowContains(sheetName, "Module", "DriverSheet");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			System.out.println("failed to get the driver sheet start" + iStart);
		}
		try {
			iEnd = excelUtils.getTestStepsCount("DriverSheet", sheetName, iStart, "Module");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			System.out.println("failed to get the driver sheet end" + iEnd);
		}

		System.out.println("beginging and end of the steps >>" + iStart + iEnd);

		for (int IdriverRow = iStart; IdriverRow <= iEnd; IdriverRow++) {

			RunMode = excelUtils.getCellData("DriverSheet", IdriverRow, "RunMode");
			TestCaseID = excelUtils.getCellData("DriverSheet", IdriverRow, "TestCaseID");
			System.out.println("Run Mode>>" + RunMode + "Case id>>" + TestCaseID);

			if (RunMode.equalsIgnoreCase("Y")) {
				try {
					iTestStart = excelUtils.getRowContains(TestCaseID, "TestCaseID", sheetName);
					System.out.println("Start>>" + iTestStart);
				} catch (Exception e) { // TODO Auto-generated catch block //

					System.out.println("Test start is not getting>>");
					e.printStackTrace();
					Finalflag = false;
					return Finalflag;
				}

				try {
					iTestEnd = excelUtils.getTestStepsCount(sheetName, TestCaseID, iTestStart, "TestCaseID");
					System.out.println("TestEnd>>" + iTestEnd);
				} catch (Exception e1) {
					System.out.println("Test end is not getting>>");
					// TODO Auto-generated catch block //
					excelUtils.setCellData("DriverSheet", "Results", IdriverRow, "Fail");
					Finalflag = false;
					return Finalflag;
				}

				// for (int Irow = 4; Irow <= excelUtils.getRowCount(sheetName); Irow++) {
				for (int Irow = iTestStart; Irow <= iTestEnd; Irow++) {

					if (Irow == 77) {
						System.out.println("start debugging");
					}
					try {
						classAction = new ActionClass();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						System.err.println("failed to instanciate the action class");
						Finalflag = false;
						return Finalflag;
					}
					System.out.println("data sheet reading >>" + Irow);
					ActionKeyWord = excelUtils.getCellData(sheetName, Irow, "ActionKeyword");
					PageObject = excelUtils.getCellData(sheetName, Irow, "PageObject");
					TestData = excelUtils.getCellData(sheetName, Irow, "TestData");
					TestCaseID = excelUtils.getCellData(sheetName, Irow, "TestCaseID");
					Argument1 = excelUtils.getCellData(sheetName, Irow, "Argument1");

					System.out.println(ActionKeyWord);
					System.out.println(PageObject);
					System.out.println(TestData);
					System.out.println(Argument1);
					// executeReflectionActions(PageObject,ActionKeyWord,TestData);
					// executeReflectionActions();

					switch (ActionKeyWord) {

					case "openURL":
						if (classAction.openURL()) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "login":
						if (classAction.login()) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "enterText":
						if (classAction.enterText(PageObject, TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "click":
						if (classAction.click(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "clickUsingJScript":
						if (classAction.clickUsingJScript(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;

					case "csvDownloadclick":
						if (classAction.csvDownloadclick(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;

					case "RunOrResumeEngineclick":
						if (classAction.RunOrResumeEngineclick(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "mouseOver":
						if (classAction.mouseOver(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "SwitchNclick":
						if (classAction.SwitchNclick(PageObject))

						{
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "ToggleButtonClick":
						if (classAction.ToggleButtonClick(PageObject))

						{
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "SelectListItem":
						if (classAction.SelectListItem(PageObject, TestData))

						{
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {

							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;

					case "checkboxSelect":

						if (classAction.checkboxSelect(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "checkboxUncheck":

						if (classAction.checkboxUncheck(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "sleepTime":

						if (classAction.sleepTime((long) (Float.parseFloat(TestData.trim())))) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "gettingHandle":

						if (classAction.gettingHandle()) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "elementPropertyCheck":
						if (classAction.elementPropertyCheck(PageObject, TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						break;
					case "rollBackToBaselineVersion":
						if (classAction.rollBackToBaselineVersion()) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "selectRadioButton":
						if (classAction.selectRadioButton(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "VerifyEngineStatus":
						if (classAction.elementPropertyCheck(PageObject, TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "ValidateBatchDecisionOutputCSV":
						if (classAction.ValidateBatchDecisionOutputCSV(TestCaseID)) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						break;
					case "keyPressEnter":
						if (classAction.keyPressEnter(PageObject)) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						break;
					case "checkEntitiesOfEvent":
						if (classAction.checkEntitiesOfEvent(TestData, (int) Float.parseFloat(Argument1))) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						break;
					case "byPassArbitrationSelection":
						if (classAction.byPassArbitrationSelection(TestData, Argument1)) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						break;
					case "RealtimeEventGetAPi":
						if (classAction.RealtimeEventGetAPi(TestData, Argument1)) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						break;
					case "switchToParent":
						if (classAction.switchToParent()) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						break;
					case "switchToFrame":
						if (classAction.switchToFrame(TestData)) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						break;
					case "switchToFrameByIndex":
						if (classAction.switchToFrameByIndex((int) Float.parseFloat(TestData))) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						break;
					case "switchToNewWindow":
						if (classAction.switchToNewWindow((int) Float.parseFloat(TestData))) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						break;
					case "quitBrowser":
						if (classAction.quitBrowser()) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						// excelUtils.saveFile("TestDataAndResults\\SophieAutomationResults.xlsx");

						break;
					case "closeBrowser":
						if (classAction.closeBrowser()) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						// excelUtils.saveFile("TestDataAndResults\\SophieAutomationResults.xlsx");

						break;
					case "maximizeWindow":
						if (classAction.maximizeWindow()) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						// excelUtils.saveFile("TestDataAndResults\\SophieAutomationResults.xlsx");

						break;
					case "hiddenClick":
						if (classAction.hiddenClick(TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;

					case "ensureDataflowIsInProgress":
						if (classAction.ensureDataflowIsInProgress(TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "verifyLabelText":
						if (classAction.verifyLabelText(TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "verfyInputTextValue":
						if (classAction.verfyInputTextValue(TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}
						break;
					case "switchOnToggleButton":
						if (classAction.switchOnToggleButton(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}

						break;
					case "switchOffToggleButton":
						if (classAction.switchOffToggleButton(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}

						break;

					case "verifyLoadType":
						if (classAction.verifyLoadType(PageObject, TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						break;
					case "closeTab":
						if (classAction.closeTab(TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}

						break;
					case "realtimeSpineAPIPostRequest":
						if (classAction.realtimeSpineAPIPostRequest()) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "pageScrollDownToView":
						if (classAction.pageScrollDownToView()) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}
						break;
					case "pageScrollUpToView":
						if (classAction.pageScrollUpToView()) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "verifyDBSpecificColumnExistsOrNot":
						if (classAction.verifyDBSpecificColumnExistsOrNot(TestData, Argument1)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "verifyColumnRemovedFromDB":
						if (classAction.verifyColumnRemovedFromDB(TestData, Argument1)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}

						break;
					case "verifyColumnInPegaClass":
						if (classAction.verifyColumnInPegaClass(TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "discardCurrentVersionIfCheckedOut":
						if (classAction.discardCurrentVersionIfCheckedOut()) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "verifyOfferisNotPresentInCurrentVersion":
						if (classAction.verifyOfferisNotPresentInCurrentVersion(TestData, Argument1)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");

							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}
						break;
					case "rollbackToVersionByName":
						if (classAction.rollbackToVersionByName(TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");

							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}
						break;
					case "verifyGenerateEngineCompleted":
						if (classAction.verifyGenerateEngineCompleted()) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");

							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "unSubscribeEmail":
						if (classAction.unSubscribeEmail()) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;

						}
						break;
					case "verifyAdaptiveModelForNegResponse":
						if (classAction.verifyAdaptiveModelForNegResponse(TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							Finalflag = false;
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");

							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}
						break;
					case "verifyIHRecordsAreAvailable":
						if (classAction.verifyIHRecordsAreAvailable(TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							Finalflag = false;
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");

							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}
						break;
					case "enableOverride":
						if (classAction.enableOverride(TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							Finalflag = false;
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");

							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}
						break;
					case "increaseWeightIfRequired":
						if (classAction.increaseWeightIfRequired(PageObject, TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							Finalflag = false;
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");

							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}
						break;
					case "changeChannelPriorityIfRequired":
						if (classAction.changeChannelPriorityIfRequired(PageObject, TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							Finalflag = false;
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");

							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}
						break;
					case "verifyOnlyExpectedChannelIsSelected":
						if (classAction.verifyOnlyExpectedChannelIsSelected(TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							Finalflag = false;
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");

							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}
						break;
					case "verifyEligibleChannelForOfferInDB":
						if (classAction.verifyEligibleChannelForOfferInDB(TestData, Argument1)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							Finalflag = false;
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");

							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}
						break;
					case "verifyDataTypeForNewSubChannels":
						if (classAction.verifyDataTypeForNewSubChannels(TestData, Argument1)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							Finalflag = false;
							try {
								Thread.sleep(10000);
								if (osName.equalsIgnoreCase("Linux")) {
									FullPageScreenShot("ScreenShots/", TestCaseID + "_" + ActionKeyWord + "_" + ".png");

								} else {

									FullPageScreenShot("ScreenShots\\",
											TestCaseID + "_" + ActionKeyWord + "_" + ".png");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Reporter.log("Failed in " + ActionKeyWord);
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");

							Finalflag = false;
							classAction.quitBrowser();
							return Finalflag;
						}
						break;
					default:
						break;
					}

				}
			}
		}
		Finalflag = finalResult(sheetName);
		return Finalflag;

	}

	public boolean finalResult(String sheetName) {

		boolean finalResult = true;
		String Result = null;

		for (int i = 4; i <= excelUtils.getRowCount(sheetName); i++) {

			Result = excelUtils.getCellData(sheetName, i, "Results");
			System.out.println(Result);
			if (Result == null || Result.equalsIgnoreCase("Fail")) {

				finalResult = false;

				return finalResult;

			}

		}
		return finalResult;

	}

	public void FullPageScreenShot(String path, String filename) throws Exception {

		// ActionClass actionClass = new ActionClass();
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(ActionClass.driver);

		ImageIO.write(fpScreenshot.getImage(), "PNG", new File(path + filename));

	}

	public static void executeReflectionActions() {
		// This is a loop which will run for the number of actions in the Action Keyword
		// class
		// method variable contain all the method and method.length returns the total
		// number of methods
		for (int i = 0; i < method.length; i++) {
			// This is now comparing the method name with the ActionKeyword value got from
			// excel
			if (method[i].getName().equals(ActionKeyWord)) {
				// In case of match found, it will execute the matched method
				try {
					method[i].invoke(classAction);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Once any method is executed, this break statement will take the flow outside
				// of for loop
				break;
			}
		}

	}

}
