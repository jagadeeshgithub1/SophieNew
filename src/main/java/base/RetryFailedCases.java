package base;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedCases implements IRetryAnalyzer {

	private int retryCount = 0;
	private int MaxCount = 1;

	public RetryFailedCases() {
		// TODO Auto-generated constructor stub
	}

	public boolean retry(ITestResult result) {

		if (retryCount < MaxCount) {

			System.out.println("Retrying the method" + result.getName() + "for" + retryCount + "times..");

			retryCount++;
			return true;

		}

		// TODO Auto-generated method stub
		return false;
	}

}
