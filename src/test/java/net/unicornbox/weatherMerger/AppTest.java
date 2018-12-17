package net.unicornbox.weatherMerger;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public AppTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        String logLine = "2017-04-04 08:40:31,064 WARN [pool-1-thread-1] com.arondor.viewer.rendition.performance.AWeatherRImpl:106 - Soleil,2.0490038132667543";
        WeatherLogTrace wlt = new WeatherLogTrace(logLine);
        Assert.assertEquals("Tue Apr 04 08:40:31 CEST 2017", wlt.getWeatherDate().toString());
        Assert.assertEquals("2.0490038", wlt.getWeatherScore() + "");
    }
}
