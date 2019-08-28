package regressionTests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.RetryFailedCases;
import driver.DriverEngine;

public class RegressionCases {

	public DriverEngine driverEngine = null;
	SoftAssert softAssert = new SoftAssert();

	@BeforeMethod

	public void beforeMethod(Method method) {
		System.out.println("Current Executing Test  :" + method.getName());
	}

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

	@Test(priority = 1, retryAnalyzer = RetryFailedCases.class, enabled = false)
	// First test to validate the CSV after engine run
	public void Verify_DownloadedCSV_For_Existing_Version() {

		Assert.assertTrue(driverEngine.mainMethod("VerifyCSVForExistingVersion"));

		// TODO Auto-generated catch block

	}

	@Test(priority = 2, retryAnalyzer = RetryFailedCases.class)

	public void Verify_AdaptiveModel_For_Negative_Response() {
		Assert.assertTrue(driverEngine.mainMethod("AdpativeModelForUnsubscribe"));
	}

	@Test(priority = 3, retryAnalyzer = RetryFailedCases.class, enabled = false)
	// Second case to verify the event API integration
	public void Verify_DownloadedCSV_For_New_version() {

		Assert.assertTrue(driverEngine.mainMethod("VerifyCSVForNewVersion"));

	}

	@Test(priority = 4, retryAnalyzer = RetryFailedCases.class, enabled = false)
	public void Verify_Deleted_Offer_Is_Not_Present_In_CSV() {
		Assert.assertTrue(driverEngine.mainMethod("VerifyDeleteOffer"));

	}

	@Test(priority = 5, retryAnalyzer = RetryFailedCases.class, enabled = false)
	public void Verify_IH_Deleted_For_All() {
		Assert.assertTrue(driverEngine.mainMethod("VerifyIHDeleted"));

	}

	@Test(priority = 6, retryAnalyzer = RetryFailedCases.class, enabled = false)
	public void Verify_Offer_Rename_Is_Successfull() {

		Assert.assertTrue(driverEngine.mainMethod("RenameOffer"));
	}

	@Test(priority = 7, retryAnalyzer = RetryFailedCases.class)
	public void Verify_RealTimeEvent_API_Response_For_NewEvent() {
		Assert.assertTrue(driverEngine.mainMethod("VerifyEventAPI"));

	}

	@Test(priority = 8, retryAnalyzer = RetryFailedCases.class, enabled = false)
	public void Verify_NewEmailTemp_ByCloning() {
		Assert.assertTrue(driverEngine.mainMethod("EmailTempWithNewAttribute"));

	}

	@Test(priority = 9, retryAnalyzer = RetryFailedCases.class, enabled = false)
	public void Verify_Creating_NewMicrositeTemp_ByAdding_Existing_Attribute() {
		Assert.assertTrue(driverEngine.mainMethod("MicrositeTempWithExistingAttrib"));

	}

	@Test(priority = 10, retryAnalyzer = RetryFailedCases.class, enabled = false)
	public void Verify_NewProperty_Added_Is_Available_in_DB() {
		Assert.assertTrue(driverEngine.mainMethod("VerifyDBForNewAgreementModel"));

	}

	@Test(priority = 11, retryAnalyzer = RetryFailedCases.class, enabled = false)
	public void Verify_NewProperty_Added_Is_Available_in_PegaClass() {
		Assert.assertTrue(driverEngine.mainMethod("VerifyAgreementClassFornewProp"));

	}

	@Test(priority = 12, retryAnalyzer = RetryFailedCases.class)
	public void Verify_Discard_Version_Is_Successful() {
		Assert.assertTrue(driverEngine.mainMethod("VerifyDiscardVersioIsSuccessful"));

	}

	@Test(priority = 13, retryAnalyzer = RetryFailedCases.class, enabled = false)
	public void Verify_Rollback_Is_Working_As_Expected() {

		Assert.assertTrue(driverEngine.mainMethod("EnsureRollbackIsSuccessful"));
	}

	@Test(priority = 14, retryAnalyzer = RetryFailedCases.class)
	public void Verify_RealtimeSpine_Api() {
		Assert.assertTrue(driverEngine.mainMethod("VerifyRealtimeSpineAPI"));

	}

	@Test(priority = 15, retryAnalyzer = RetryFailedCases.class)
	public void Ensure_Only_Expected_Channels_Are_Selected() {
		Assert.assertTrue(driverEngine.mainMethod("VerifyChannelSelection"));

	}

	@Test(priority = 16, retryAnalyzer = RetryFailedCases.class)
	public void Ensure_PickBestChannel_Works_With_Channel_Heirarchy() {

		Assert.assertTrue(driverEngine.mainMethod("PickBestChannel"));

	}

}
