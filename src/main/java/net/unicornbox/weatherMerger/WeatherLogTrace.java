package net.unicornbox.weatherMerger;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherLogTrace implements Comparable<WeatherLogTrace>
{
    private Date weatherDate;

    private float weatherScore;

    public WeatherLogTrace(Date weatherDate, float weatherScore)
    {
        super();
        this.weatherDate = weatherDate;
        this.weatherScore = weatherScore;
    }

    public WeatherLogTrace(String logLine)
    {
        super();
        String date = logLine.split("WARN")[0].trim();
        weatherDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").parse(date, new ParsePosition(0));
        String[] split = logLine.split(",");
        weatherScore = Math.abs(Float.parseFloat(split[split.length - 1]));
    }

    public int compareTo(WeatherLogTrace o)
    {
        return weatherDate.compareTo(o.getWeatherDate());
    }

    public Date getWeatherDate()
    {
        return weatherDate;
    }

    public void setWeatherDate(Date weatherDate)
    {
        this.weatherDate = weatherDate;
    }

    public float getWeatherScore()
    {
        return weatherScore;
    }

    public void setWeatherScore(float weatherScore)
    {
        this.weatherScore = weatherScore;
    }

}
