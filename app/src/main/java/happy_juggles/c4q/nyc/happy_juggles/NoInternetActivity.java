package happy_juggles.c4q.nyc.happy_juggles;

/**
 * HOSHIKO'S NO INTERNET ACTIVITY
 *
 * Created by Hoshiko on 7/1/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoInternetActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nointernet);

        Button checkInternetButton = (Button)findViewById(R.id.check_internet_button);

        checkInternetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(NoInternetActivity.this, MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
            }
        });
    }

}
