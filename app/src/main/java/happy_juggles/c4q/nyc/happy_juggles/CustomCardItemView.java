package happy_juggles.c4q.nyc.happy_juggles;

import android.content.Context;
import android.util.AttributeSet;

import com.dexafree.materialList.model.CardItemView;

/**
 * Created by s3a on 6/30/15.
 */
public class CustomCardItemView extends CardItemView <WeatherCard> {


    


    public CustomCardItemView(Context context) {
        super(context);
    }

    public CustomCardItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCardItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void build(WeatherCard card) {
        super.build(card);
    }
}
