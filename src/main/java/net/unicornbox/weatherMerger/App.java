package net.unicornbox.weatherMerger;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args) throws IOException
    {
        String[] files = new File(args[0]).list(new FilenameFilter()
        {

            public boolean accept(File dir, String name)
            {
                if (!name.startsWith("arender-rendition-weather"))
                {
                    return false;
                }
                return true;
            }
        });
        if (files == null)
        {
            System.err.println("Cannot read folder " + args[0]);
        }
        List<WeatherLogTrace> data = new ArrayList<WeatherLogTrace>();
        if (files.length > 0)
        {
            parseFile(args, data, "arender-rendition-weather.log");
            for (int i = 1; i < Integer.MAX_VALUE; i++)
            {
                try
                {
                    parseFile(args, data, "arender-rendition-weather.log" + "." + i);
                }
                catch (Exception e)
                {
                    // reached end of files
                    break;
                }
            }
        }
        Collections.sort(data);

        List<Date> xData = new ArrayList<Date>();
        List<Float> yData = new ArrayList<Float>();

        for (WeatherLogTrace wlt : data)
        {
            xData.add(wlt.getWeatherDate());
            yData.add(wlt.getWeatherScore());
        }

        XYChart chart = getChart(xData, yData);
        new SwingWrapper<XYChart>(chart).displayChart();
        BitmapEncoder.saveBitmapWithDPI(chart, "./weather", BitmapFormat.PNG, 300);
    }

    public static XYChart getChart(List<Date> xData, List<Float> yData)
    {

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Time")
                .yAxisTitle("Score (0 to 4, higher is better)").build();

        // Customize Chart
        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.GREY));
        chart.getStyler().setPlotGridLinesColor(new Color(255, 255, 255));
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        chart.getStyler().setLegendBackgroundColor(Color.PINK);
        chart.getStyler().setChartFontColor(Color.MAGENTA);
        chart.getStyler().setChartTitleBoxBackgroundColor(new Color(0, 222, 0));
        chart.getStyler().setChartTitleBoxVisible(true);
        chart.getStyler().setChartTitleBoxBorderColor(Color.BLACK);
        chart.getStyler().setPlotGridLinesVisible(false);

        chart.getStyler().setAxisTickPadding(20);

        chart.getStyler().setAxisTickMarkLength(15);

        chart.getStyler().setPlotMargin(20);

        chart.getStyler().setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        chart.getStyler().setLegendFont(new Font(Font.SERIF, Font.PLAIN, 18));
        chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
        chart.getStyler().setLegendSeriesLineLength(12);
        chart.getStyler().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
        chart.getStyler().setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 11));
        chart.getStyler().setDatePattern("dd-MMM");
        chart.getStyler().setDecimalPattern("#0.000");
        chart.getStyler().setLocale(Locale.FRENCH);

        // Series
        XYSeries series = chart.addSeries("Weather score", xData, yData);
        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.ORANGE);
        series.setMarker(SeriesMarkers.NONE);
        series.setLineStyle(SeriesLines.SOLID);

        return chart;
    }

    private static void parseFile(String[] args, List<WeatherLogTrace> data, String filename)
            throws FileNotFoundException, IOException
    {
        File firstLog = new File(args[0] + "/" + filename);
        BufferedReader br = new BufferedReader(new FileReader(firstLog));
        String line;
        while (((line = br.readLine()) != null))
        {
            data.add(new WeatherLogTrace(line));
        }
        br.close();
    }
}
