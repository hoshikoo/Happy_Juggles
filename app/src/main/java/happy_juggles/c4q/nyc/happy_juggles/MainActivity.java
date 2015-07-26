package happy_juggles.c4q.nyc.happy_juggles;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListAdapter;

import com.dexafree.materialList.view.MaterialListView;

import happy_juggles.c4q.nyc.happy_juggles.db.TaskDBHelper;


public class MainActivity extends ActionBarActivity {

    Location location;
    String filename = "myfile";
    public static final String PREFS_NAME = "MyPrefsFile";

    private Context mContext;
    private MaterialListView mListView;

    String googleStaticMap;


    private ListAdapter listAdapter;
    private TaskDBHelper helper;
    String task;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CardViewFragment fragment = new CardViewFragment();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();

    }


}

