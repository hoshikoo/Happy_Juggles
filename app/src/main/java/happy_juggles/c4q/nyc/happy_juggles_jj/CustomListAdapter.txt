package happy_juggles.c4q.nyc.happy_juggles;

/**
 * JOSHELYN'S SPORT CARD
 *
 * Created by c4q-joshelynvivas on 7/1/15.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Sports> sportsItems;

    public CustomListAdapter(Activity activity, List<Sports> sportsItems) {
        this.activity = activity;
        this.sportsItems = sportsItems;
    }

    @Override
    public int getCount() {
        return sportsItems.size();
    }

    @Override
    public Object getItem(int location) {
        return sportsItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.sports_list_row, null);

        TextView country = (TextView) convertView.findViewById(R.id.country);
        TextView wins = (TextView) convertView.findViewById(R.id.wins);

        // getting movie data for the row
        Sports m = sportsItems.get(position);

        // COUNTRY
        country.setText(m.getCountry());

        // WINS
        wins.setText(String.valueOf(m.getWins()));

        return convertView;
    }
}
