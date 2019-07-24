package base;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class InvokationListner implements IInvokedMethodListener {

	public InvokationListner() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

		// TODO Auto-generated method stub

	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

		System.out.println("Current Invocation Count is >>>" + method.getTestMethod().getCurrentInvocationCount());
		System.out.println(method.getTestResult().getMethod().getInvocationCount());
		// TODO Auto-generated method stub

	}
}
