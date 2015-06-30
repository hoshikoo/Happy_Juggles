package happy_juggles.c4q.nyc.happy_juggles;

import android.content.Context;
import android.graphics.Color;

import com.dexafree.materialList.cards.OnButtonPressListener;
import com.dexafree.materialList.cards.SimpleCard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by s3a on 6/30/15.
 */
public class WeatherCard extends SimpleCard {
    private String subtitle;
    private String buttonText;



    private OnButtonPressListener mListener;

    public OnButtonPressListener getListener() {
        return mListener;
    }

    private int subtitleColor = Color.WHITE;
    private int dividerColor = Color.parseColor("#608DFA");
    private int buttonTextColor = Color.WHITE;

    private CurrentWeather mCurrentWeather;
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public void setListener(OnButtonPressListener listener) {
        mListener = listener;
    }

    public void setSubtitleColor(int subtitleColor) {
        this.subtitleColor = subtitleColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
    }

    public void setButtonTextColor(int buttonTextColor) {
        this.buttonTextColor = buttonTextColor;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        mCurrentWeather = currentWeather;
    }



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

    public String getFormattedTime(int temperature_label){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        Date dateTime = new Date(getTime() *1000);
        String timeString = formatter.format(dateTime);

        return timeString;
    }

    public void setTime(Long time) {
        mTime = time;
    }

    public int getTemperature(int temperature_label) {
        return (int) Math.round(mTemperature);
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

    public String getPrecipChance() {
        double precipPercentage = mPrecipChance * 100;
        return ((int)Math.round(precipPercentage )+ "%");
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





    public WeatherCard(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.my_custom_card;
    }


    public void setOnButtonPressedListener(OnButtonPressListener onButtonPressListener) {
    }
}
