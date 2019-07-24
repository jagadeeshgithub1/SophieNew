package base;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedCases implements IRetryAnalyzer {

	private int retryCount;
	private int MaxCount = 1;

	public RetryFailedCases() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	public boolean retry(ITestResult result) {

		for (retryCount = 0; retryCount < MaxCount; retryCount++) {

			System.out.println("Retrying the method" + result.getName() + "for" + retryCount + "times..");
			return true;

		}

		// TODO Auto-generated method stub
		return false;
	}

}
