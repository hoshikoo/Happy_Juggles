package happy_juggles.c4q.nyc.happy_juggles;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dexafree.materialList.cards.BasicListCard;
import com.dexafree.materialList.cards.BigImageButtonsCard;
import com.dexafree.materialList.cards.BigImageCard;
import com.dexafree.materialList.cards.OnButtonPressListener;
import com.dexafree.materialList.cards.SimpleCard;
import com.dexafree.materialList.cards.WelcomeCard;
import com.dexafree.materialList.controller.OnDismissCallback;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.Card;
import com.dexafree.materialList.model.CardItemView;
import com.dexafree.materialList.view.MaterialListView;

import happy_juggles.c4q.nyc.happy_juggles.db.TaskContract;
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

            // Save a reference to the context
            mContext = this;

            // Bind the MaterialListView to a variable
            mListView = (MaterialListView) findViewById(R.id.material_listview);

            // Fill the array with mock content
            fillArray();

            // Set the dismiss listener
            mListView.setOnDismissCallback(new OnDismissCallback() {
                @Override
                public void onDismiss(Card card, int position) {

                    // Recover the tag linked to the Card
                    String tag = card.getTag().toString();

                    // Show a toast
                    Toast.makeText(mContext, "You have dismissed a " + tag, Toast.LENGTH_SHORT).show();
                }
            });

            // Add the ItemTouchListener
            mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(CardItemView view, int position) {
//                    Log.d("CARD_TYPE", view.getTag().toString());

//                            Toast.makeText(MainActivity.this, "test todo", Toast.LENGTH_SHORT).show();
//                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                            builder.setTitle("Add a task");
//                            builder.setMessage("What do you want to do?");
//                            final EditText inputField = new EditText(MainActivity.this);
//                            builder.setView(inputField);
//                            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    String task = inputField.getText().toString();
//
//                                    helper = new TaskDBHelper(MainActivity.this);
//                                    SQLiteDatabase db = helper.getWritableDatabase();
//                                    ContentValues values = new ContentValues();
//
//                                    values.clear();
//                                    values.put(TaskContract.Columns.TASK, task);
//
//                                    db.insertWithOnConflict(TaskContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
//                                    updateUI();
////                                showNotification();
//                                }
//                            });
//
//                            builder.setNegativeButton("Cancel", null);
//
//                            builder.create().show();
                        }


                @Override
                public void onItemLongClick(CardItemView view, int position) {
                    Log.d("LONG_CLICK", view.getTag().toString());
                }
            });
    }


//map----------------------------------------------------------------------------------------------------

//    public static Bitmap getGoogleMapThumbnail(String result){
//        String URL = "http://maps.google.com/maps/api/staticmap?center="+result+"&zoom=17&size=1800x500&sensor=&maptype=roadmap" +
//                "&markers=color:blue%7Clabel:S%7C"+result;
//        Bitmap bmp = null;
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpGet request = new HttpGet(URL);
//
//        InputStream in = null;
//        try {
//            in = httpclient.execute(request).getEntity().getContent();
//            bmp = BitmapFactory.decodeStream(in);
//            in.close();
//        } catch (IllegalStateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return bmp;
//    }
//
//    private void SaveImage(Bitmap finalBitmap) {
//
//        String root = Environment.getExternalStorageDirectory().toString();
//        File myDir = new File(root + "/map_images");
//        myDir.mkdirs();
//        String fname = "map.jpg";
//        File file = new File (myDir, fname);
//        if (file.exists ()) file.delete ();
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    //map----------------------------------------------------------------------------------------------------


    //cards--------------------------------------------------------------------------

    private void fillArray() {
        for (int i = 0; i < 35; i++) {
            Card card = getRandomCard(i);
            mListView.add(card);
        }
    }

    private Card getRandomCard(final int position) {
        String title = "Card number " + (position + 1);
        String description = "Lorem ipsum dolor sit amet";

        int type = position % 5;

        SimpleCard card;
        Drawable icon;

        switch (type) {

            case 0:

                card = new WelcomeCard(this);
                card.setTitle("Welcome to Happy Juggles");
                card.setDescription("created by H(oshiko) and 2Js(Janneisy & Joshelyn)");
                card.setTag("WELCOME_CARD");

                ((WelcomeCard) card).setSubtitle("Swipe through the cards!");
                ((WelcomeCard) card).setButtonText("Okay!");
                ((WelcomeCard) card).setOnButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
                        Toast.makeText(mContext, "Welcome!", Toast.LENGTH_SHORT).show();
                        mListView.remove(card);

                    }
                });

                ((WelcomeCard) card).setBackgroundColor(Color.rgb(255,153,51));
                card.setDismissible(true);
                return card;


            case 1:
                BigImageButtonsCard bigcard = new BigImageButtonsCard(this);
                bigcard.setDescription("Map of the saved address");
                bigcard.setTitle("Map Card");
                String result = null;
                try {

                    SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    // Writing data to SharedPreferences
                    SharedPreferences.Editor editor = preferences.edit();
                    result = preferences.getString("LatLngStr", "");


                } catch (Exception e) {
                    e.printStackTrace();
                }
                googleStaticMap = "http://maps.google.com/maps/api/staticmap?center=" + result + "&zoom=17&size=600x600&sensor=&maptype=roadmap" +
                        "&markers=color:blue%7Clabel:S%7C" + result;
                bigcard.setDrawable(googleStaticMap);

                bigcard.setTag("BASIC_IMAGE_BUTTON_CARD");
                bigcard.setRightButtonText("Check directions");
                bigcard.setLeftButtonText("Add Big map Card");
                bigcard.setTitleColor(Color.rgb(0,172,230));

                if (position % 2 == 0)
                    bigcard.setDividerVisible(true);

                bigcard.setOnRightButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {

                        if (!haveNetworkConnection()){

                            Intent noInternetIntent = new Intent(MainActivity.this, NoInternetActivity.class);
                            MainActivity.this.startActivity(noInternetIntent);

                        }else {
                            Intent mapIntent = new Intent(MainActivity.this, MapActivity.class);
                            MainActivity.this.startActivity(mapIntent);
                        }
                    }
                });

                bigcard.setOnLeftButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
                        mListView.addAtStart(generateMapCard());
                    }
                });


                bigcard.setDismissible(true);

                return bigcard;


            case 2:
//                card = new WelcomeCard(this);
//                card.setTitle("ToDo List Card");
//                card.setDescription("You can add the card below");
//                card.setTag("LIST_CARD");
//
//                ((WelcomeCard) card).setButtonText("Go to Todo List");
//
//                ((WelcomeCard) card).setBackgroundColor(Color.rgb(0, 172, 230));
//
//
//
//                ((WelcomeCard) card).setOnButtonPressedListener(new OnButtonPressListener() {
//                    @Override
//                    public void onButtonPressedListener(View view, Card card) {
//                        Toast.makeText(mContext, "Let's edit the card!", Toast.LENGTH_SHORT).show();
//
//                                        Intent todoIntent = new Intent(MainActivity.this, ToDoList.class);
//                                        MainActivity.this.startActivity(todoIntent);
//
//                    }
//                });
//
//
//                card.setDismissible(true);
//
//                return card;

                TodoCard todocard = new  TodoCard(this);
                todocard.setTitle("ToDo List Card");
                todocard.setTag("TODO_CARD");

//                Button addButton = (Button)findViewById(R.id.add_button);
                ((TodoCard) todocard).setButtonText("Add");


                ((TodoCard) todocard).setOnButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {


//                  Toast.makeText(mContext, "test todo", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("Add a task");
                        builder.setMessage("What do you want to do?");
                        final EditText inputField = new EditText(mContext);
                        builder.setView(inputField);
                        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                task = inputField.getText().toString();

                                helper = new TaskDBHelper(mContext);
                                SQLiteDatabase db = helper.getWritableDatabase();
                                ContentValues values = new ContentValues();

                                values.clear();
                                values.put(TaskContract.Columns.TASK, task);

                                db.insertWithOnConflict(TaskContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);


                                helper = new TaskDBHelper(mContext);
                                SQLiteDatabase sqlDB = helper.getReadableDatabase();
                                Cursor cursor = sqlDB.query(TaskContract.TABLE,
                                        new String[]{TaskContract.Columns._ID, TaskContract.Columns.TASK},
                                        null, null, null, null, null);

                                listAdapter = new SimpleCursorAdapter(
                                        mContext,
                                        R.layout.task_view,
                                        cursor,
                                        new String[]{TaskContract.Columns.TASK},
                                        new int[]{R.id.todoTaskTV},
                                        0
                                );

                                ListView todolistView = (ListView) findViewById(R.id.todolist);
                                todolistView.setAdapter(listAdapter);

                                showNotification();
                            }
                        });

                        builder.setNegativeButton("Cancel", null);

                        builder.create().show();
                    }
                });





                return todocard;


            case 3:

                card = new WelcomeCard(this);
                card.setTitle("Check the weather forecast");
                card.setDescription("");
                card.setTag("Weather");

                ((WelcomeCard) card).setSubtitle("");
                ((WelcomeCard) card).setButtonText("Check Forecast");
                ((WelcomeCard) card).setOnButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {

                        Uri uri = Uri.parse("http://forecast.io/#/f/40.7792,-73.9070");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        Toast.makeText(mContext, " Weather!", Toast.LENGTH_SHORT).show();
                    }
                });

//                if (position % 2 == 0)
                ((WelcomeCard) card).setBackgroundColorRes(R.color.background_material_dark);
                card.setDismissible(true);
                return card;

            case 4:

                card = new WelcomeCard(this);
                card.setTitle("Sports Card");
                card.setDescription("");
                card.setTag("Alarm");

                ((WelcomeCard) card).setSubtitle("");
                ((WelcomeCard) card).setButtonText("Check Game Result!");
                ((WelcomeCard) card).setOnButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {

                    }
                });


                ((WelcomeCard) card).setBackgroundColorRes(R.color.background_material_dark);
                card.setDismissible(true);
                return card;

            default:
                card = new WelcomeCard(this);
                card.setTitle("Welcome to Happy Juggles");
                card.setDescription("created by H(oshiko) and 2Js(Janneisy & Joshelyn)");
                card.setTag("WELCOME_CARD");

                ((WelcomeCard) card).setSubtitle("Swipe through the cards!");
                ((WelcomeCard) card).setButtonText("Okay!");
                ((WelcomeCard) card).setOnButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
                        Toast.makeText(mContext, "Welcome!", Toast.LENGTH_SHORT).show();
                        mListView.remove(card);

                    }
                });

                ((WelcomeCard) card).setBackgroundColor(Color.rgb(255, 153, 51));
                card.setDismissible(true);
                return card;

        }

   }
//
private Card generateMapCard() {
    BigImageCard card = new BigImageCard(this);
    card.setTitle("Big Map Card");
    card.setTitleColor(Color.rgb(0, 172, 230));
    card.setDescription("The map of your saved address");

    String result = null;
    try {

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = preferences.edit();
        result = preferences.getString("LatLngStr", "");


    } catch (Exception e) {
        e.printStackTrace();
    }


    googleStaticMap = "http://maps.google.com/maps/api/staticmap?center="+result+"&zoom=17&size=1800x500&sensor=&maptype=roadmap" +
            "&markers=color:blue%7Clabel:S%7C"+result;
    card.setDrawable(googleStaticMap);
    card.setTag("BIG_IMAGE_CARD");

    card.setDismissible(true);

    return card;
}
    private Card generateNewCard() {
        BasicListCard card = new BasicListCard(this);
        card.setTitle("ToDo List Card");
        card.setDescription("Please click on the card to edit the list!");
        BasicListAdapter adapter = new BasicListAdapter(this);
        adapter.add("Text1");
        adapter.add("Text2");
        adapter.add("Text3");
        card.setTag("LIST_CARD");

        card.setAdapter(adapter);

        card.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "Let's edit the card!", Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

//                // set title
//                alertDialogBuilder.setTitle("Edit the todo list");
//
//                // set dialog message
//                alertDialogBuilder
//                        .setMessage("Click yes to exit!")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // if this button is clicked, close
//                                // current activity
//                                MainActivity.this.finish();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // if this button is clicked, just close
//                                // the dialog box and do nothing
//                                dialog.cancel();
//                            }
//                        });
//
//                // create alert dialog
//                AlertDialog alertDialog = alertDialogBuilder.create();
//
//                // show it
//                alertDialog.show();

            }
        });

        card.setDismissible(true);


        return card;
    }

    private void addMockCardAtStart(){

        BasicListCard card = new BasicListCard(this);
        card.setTitle("Simple ToDo List Card");
        card.setDescription("Please click on the card to edit the list!");
        BasicListAdapter adapter = new BasicListAdapter(this);
        adapter.add("Text1");
        adapter.add("Text2");
        adapter.add("Text3");
        card.setTag("LIST_CARD");

        card.setAdapter(adapter);

        card.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "Let's edit the card!", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                // set title
                alertDialogBuilder.setTitle("Edit the todo list");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        card.setDismissible(true);

        mListView.addAtStart(card);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_clear:
                mListView.clear();
                break;
            case R.id.action_add_at_start:
                mListView.addAtStart(generateMapCard());
                break;

            case R.id.action_add_all_cards:
                fillArray();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //cards--------------------------------------------------------------------------


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    private void updateUI() {
        helper = new TaskDBHelper(this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns._ID, TaskContract.Columns.TASK},
                null, null, null, null, null);

        listAdapter = new SimpleCursorAdapter(
                getBaseContext(),
                R.layout.task_view,
                cursor,
                new String[]{TaskContract.Columns.TASK},
                new int[]{R.id.todoTaskTV},
                0
        );

        ListView todolistView = (ListView) findViewById(R.id.todolist);
        todolistView.setAdapter(listAdapter);

    }

    public void onDoneButtonClick(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.todoTaskTV);
        String task = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                TaskContract.TABLE,
                TaskContract.Columns.TASK,
                task);


        helper = new TaskDBHelper(this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }

    //NOTIFICATION----------------------------

    public static final int NOTIFICATION_ID = 1234;

    private void showNotification() {
        updateNotification("New task added in ToDo List", task);
    }

    private void updateNotification(String titletext, String contentText){


        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setAutoCancel(true);

        builder.setContentTitle(titletext);
        builder.setSmallIcon(R.drawable.ic_stat_action_assignment);
        builder.setContentText(contentText);

        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);


        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);


    }

}

