/**
 * 
 */
package driver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import base.TestBaseClass;
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
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "login":
						if (classAction.login()) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "enterText":
						if (classAction.enterText(PageObject, TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "click":
						if (classAction.click(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;

					case "csvDownloadclick":
						if (classAction.csvDownloadclick(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "RunOrResumeEngineclick":
						if (classAction.RunOrResumeEngineclick(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "mouseOver":
						if (classAction.mouseOver(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "SwitchNclick":
						if (classAction.SwitchNclick(PageObject))

						{
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "ToggleButtonClick":
						if (classAction.ToggleButtonClick(PageObject))

						{
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "SelectListItem":
						if (classAction.SelectListItem(PageObject, TestData))

						{
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;

					case "checkboxSelect":

						if (classAction.checkboxSelect(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "checkboxUncheck":

						if (classAction.checkboxUncheck(PageObject)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "sleepTime":

						if (classAction.sleepTime((long) (Float.parseFloat(TestData.trim())))) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "gettingHandle":

						if (classAction.gettingHandle()) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "elementPropertyCheck":
						if (classAction.elementPropertyCheck(PageObject, TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}

						break;
					case "rollBackToBaselineVersion":
						if (classAction.rollBackToBaselineVersion(TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "VerifyEngineStatus":
						if (classAction.elementPropertyCheck(PageObject, TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}
						break;
					case "ValidateBatchDecisionOutputCSV":
						if (classAction.ValidateBatchDecisionOutputCSV(TestCaseID)) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}

						break;
					case "keyPressEnter":
						if (classAction.keyPressEnter(PageObject)) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}

						break;
					case "checkTriggerEvent":
						if (classAction.checkTriggerEvent(TestData)) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}

						break;
					case "byPassArbitrationSelection":
						if (classAction.byPassArbitrationSelection(TestData, Argument1)) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}

						break;
					case "RealtimeEventGetAPi":
						if (classAction.RealtimeEventGetAPi(((int) (Float.parseFloat(TestData))), Argument1)) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}

						break;
					case "switchToParent":
						if (classAction.switchToParent()) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}

						break;
					case "switchToFrame":
						if (classAction.switchToFrame(TestData)) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}

						break;
					case "switchToNewWindow":
						if (classAction.switchToNewWindow((int) Float.parseFloat(TestData))) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}

						break;
					case "quitBrowser":
						if (classAction.quitBrowser()) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}

						// excelUtils.saveFile("TestDataAndResults\\SophieAutomationResults.xlsx");

						break;
					case "closeBrowser":
						if (classAction.closeBrowser()) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}

						// excelUtils.saveFile("TestDataAndResults\\SophieAutomationResults.xlsx");

						break;
					case "maximizeWindow":
						if (classAction.maximizeWindow()) {

							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");

						} else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}

						// excelUtils.saveFile("TestDataAndResults\\SophieAutomationResults.xlsx");

						break;
					case "hiddenClick":
						if (classAction.hiddenClick(TestData)) {
							excelUtils.setCellData(sheetName, "Results", Irow, "Pass");
						}

						else {
							excelUtils.setCellData(sheetName, "Results", Irow, "Fail");
						}

					default:
						break;
					}

					// String finalResult=excelUtils.getCellData(sheetName, Irow, "Results");
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
					method[i].invoke(ActionKeyWord, PageObject, TestData);
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
