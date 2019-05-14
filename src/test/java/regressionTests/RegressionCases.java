package regressionTests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import driver.DriverEngine;

public class RegressionCases {

	public DriverEngine driverEngine = null;
	SoftAssert softAssert = new SoftAssert();

	@BeforeClass
	public void setUp() {

		String osName = System.getProperty("os.name").trim();
		// osName = osName.trim();
		System.out.println("Machine os:" + osName);
		File srcFile = null;
		File destDir = null;

		srcFile = new File("TestDataAndResults\\TestData\\SophieAutomation.xlsx");
		destDir = new File("TestDataAndResults\\Run1\\");
		// srcFile = new File("TestDataAndResults/TestData/SophieAutomation.xlsx");
		// destDir = new File("TestDataAndResults/Run1/");

		if (osName.equalsIgnoreCase("Linux")) {
			try {
				srcFile = new File("TestDataAndResults/TestData/SophieAutomation.xlsx");
				destDir = new File("TestDataAndResults/Run1/");
				System.out.println("path found");
			} catch (Exception e) { // TODO Auto-generated catch block
				System.out.println("Path not found" + srcFile + destDir);
			}

		}

		System.out.println("source file:" + srcFile);
		System.out.println("dest dir:" + destDir);

		try {
			driverEngine = new DriverEngine();
		} catch (Exception e1) {

			e1.printStackTrace();
			// TODO Auto-generated catch block
			System.out.println("failed to instantiate the driver class");
		}
		try {
			FileUtils.copyFileToDirectory(srcFile, destDir);

		} catch (IOException e) {

			System.out.println("file copy failed");
			// TODO Auto-generated catch block

		}
	}

	@Test(priority = 1, enabled = false)
	// First test to validate the CSV after engine run
	public void Verify_DownloadedCSV_For_Existing_Version() {

		/*
		 * if (!driverEngine.mainMethod("VerifyCSVForExistingVersion")) {
		 * Assert.assertTrue(false); Reporter.log("failed to find test sheet"); }
		 */

		Assert.assertTrue(driverEngine.mainMethod("VerifyCSVForExistingVersion"));

		// TODO Auto-generated catch block

	}

	@Test(priority = 2)
	// Second case to verify the event API integration
	public void Verify_DownloadedCSV_For_New_version() {

		/*
		 * if (!driverEngine.mainMethod("VerifyCSVForNewVersion")) {
		 * 
		 * // TODO Auto-generated catch block
		 * 
		 * Assert.assertTrue(false); Reporter.log("failed to find the testsheet"); }
		 */

		Assert.assertTrue(driverEngine.mainMethod("VerifyCSVForNewVersion"));

	}

	@Test(priority = 3, enabled = false)
	public void Verify_RealTimeEvent_API_Response_For_NewEvent() {
		if (!driverEngine.mainMethod("VerifyEventAPI")) {

			// TODO Auto-generated catch block
			Assert.assertTrue(false);
			Reporter.log("failed to find the testsheet");
		}

	}

	@Test(priority = 4, enabled = false)
	public void Verify_Deleted_Offer_Is_Not_Present_In_CSV() {
		if (!driverEngine.mainMethod("VerifyDeleteOffer")) {

			// TODO Auto-generated catch block
			Assert.assertTrue(false);
			Reporter.log("failed to find the testsheet");
		}

	}

}
