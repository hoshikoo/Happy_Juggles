package happy_juggles.c4q.nyc.happy_juggles;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by s3a on 6/25/15.
 */
public class CurrentWeather {
    private String mIcon;
    private Long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;
    private String mTimezone;

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId(){

        int iconId = R.drawable.clear_day;
        if (mIcon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (mIcon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (mIcon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (mIcon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (mIcon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (mIcon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (mIcon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (mIcon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (mIcon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (mIcon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;
    }

    public Long getTime() {
        return mTime;
    }

    public String getFormattedTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        Date dateTime = new Date(getTime() *1000);
        String timeString = formatter.format(dateTime);

        return timeString;
    }

    public void setTime(Long time) {
        mTime = time;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public double getPrecipChance() {
        return mPrecipChance;
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}


