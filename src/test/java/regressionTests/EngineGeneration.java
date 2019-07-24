package regressionTests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.InvokationListner;
import driver.DriverEngine;

@Listeners(InvokationListner.class)
public class EngineGeneration {

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

	@Test(invocationCount = 30)
	// First test to validate the CSV after engine run
	public void VerifyGenerateEngineCompletesWithoutFail() {

		Assert.assertTrue(driverEngine.mainMethod("EngineGeneration"));

		// TODO Auto-generated catch block

	}

}
