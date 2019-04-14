package listners;

import base.BaseUITest;
import org.apache.log4j.Logger;
import org.testng.*;

public class CustomListener implements IInvokedMethodListener {
    private Logger log = Logger.getLogger(this.getClass());

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        Reporter.setCurrentTestResult(result);
        if (result.getInstance() instanceof BaseUITest) {
            BaseUITest baseTest = (BaseUITest) result.getInstance();
            ITestNGMethod testNgMethod = method.getTestMethod();
            if (result.getStatus() == ITestResult.FAILURE) {
                String methodName = testNgMethod.getMethodName();
                log.warn(String.format("'%s' method is failed, saving screenshot", methodName));
                baseTest.takeScreenShot(methodName);
            }
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult result) {
    }
}
