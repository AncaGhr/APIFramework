package Hooks;

import LoggerUtility.LoggerUtility;
import org.junit.Before;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.util.logging.Logger;

public class Hooks {

    public String testName;

    @BeforeMethod
    public void setUp(){
        testName = this.getClass().getSimpleName();
        LoggerUtility.startTestCase(testName);
    }
    @AfterMethod
    public void tearDown(){
        LoggerUtility.endTestCase(testName);
    }

    @AfterSuite

    public void mergeLoggs(){
        LoggerUtility.mergeLogsIntoOne();
    }
}
