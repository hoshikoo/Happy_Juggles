package happy_juggles.c4q.nyc.happy_juggles;

/**
 * Created by s3a on 6/25/15.
 */
public class DailyWeather extends CurrentWeather {

    private String mSummary;
    private String mIcon;

    @Override
    public String getSummary() {
        return mSummary;
    }

    @Override
    public void setSummary(String summary) {
        mSummary = summary;
    }

    @Override
    public String getIcon() {
        return mIcon;
    }

    @Override
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

}

