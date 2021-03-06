package happy_juggles.c4q.nyc.happy_juggles_jj;

// MAIN ACTIVITY- WHERE WE PUT THE CARDS!!!

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.dexafree.materialList.cards.BasicImageButtonsCard;
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


public class MainActivity extends ActionBarActivity {

    Location location;
    String filename = "myfile";
    public static final String PREFS_NAME = "MyPrefsFile";

    private Context mContext;
    private MaterialListView mListView;

    String googleStaticMap;

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
                    Log.d("CARD_TYPE", view.getTag().toString());
                }

                @Override
                public void onItemLongClick(CardItemView view, int position) {
                    Log.d("LONG_CLICK", view.getTag().toString());
                }
            });

    }





    //cards--------------------------------------------------------------------------

    private void fillArray() {
        for (int i = 0; i < 8; i++) {
            Card card = getRandomCard(i);
            mListView.add(card);
        }
    }

    private Card getRandomCard(final int position) {


        int type = position % 8;

        final SimpleCard card;

        switch (type) {

            case 0:  //Welcome Card

                card = new WelcomeCard(this);
                card.setTitle("Welcome to Happy Juggles");
                card.setDescription("Created by H(Hoshiko) and 2Js(Janneisy & Joshelyn)");
                card.setDescriptionColor(Color.rgb(0, 172, 230));
                card.setTag("WELCOME CARD");

                ((WelcomeCard) card).setSubtitle("Swipe Us Cards!");
                ((WelcomeCard) card).setButtonText("Okay! Press Me!");
                ((WelcomeCard) card).setOnButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
                        Toast.makeText(mContext, "Welcome!", Toast.LENGTH_SHORT).show();
                        mListView.remove(card);

                    }
                });

                ((WelcomeCard) card).setBackgroundColor(Color.rgb(55, 62, 64));
                card.setDismissible(true);
                return card;


            case 1: //Hoshiko's Map Card

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

    //
    //                String[] latandlng =  result.split(",");
    //                double latitude = Double.parseDouble(latandlng[0]);
    //                double longitude = Double.parseDouble(latandlng[1]);

//                googleStaticMap = "http://maps.google.com/maps/api/staticmap?center="+result+"&zoom=17&size=1800x500&sensor=&maptype=roadmap" +
//                        "&markers=color:blue%7Clabel:S%7C"+result;
//                card.setDrawable(googleStaticMap);
//                card.setTag("BIG_IMAGE_CARD");
//                return card;

            case 2:  //Hoshiko's Big Card
                BigImageButtonsCard bigcard1 = new BigImageButtonsCard(this);
                bigcard1.setDescription("Get Directions");
                bigcard1.setTitle("Direction Card");
//                String photoPath = Environment.getExternalStorageDirectory()+"/map_images/map.jpg";
//                Bitmap bitmap1 = BitmapFactory.decodeFile(photoPath);
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
//                card.setDrawable("http://maps.google.com/maps/api/staticmap?center=40.6900081,-73.9771259&zoom=15&size=300x300&sensor=false");

//                bigcard.setBitmap(bitmap1);

                bigcard1.setTag("MAP CARD");
                bigcard1.setRightButtonText("Directions");
                bigcard1.setLeftButtonText("Add Map Card");
                bigcard1.setTitleColor(Color.rgb(0,172,230));

                if (position % 2 == 0)
                    bigcard1.setDividerVisible(true);

                bigcard1.setOnRightButtonPressedListener(new OnButtonPressListener() {
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

                bigcard1.setOnLeftButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
                        mListView.addAtStart(generateMapCard());
                    }
                });


                bigcard1.setDismissible(true);

                return bigcard1;


            case 3:  //To Do List Card
                card = new WelcomeCard(this);
                card.setTitle("ToDo List");
//                card.setDescription("You can add the card below");
                card.setTag("TODO LIST CARD");

                ((WelcomeCard) card).setButtonText("Press to Enter a Task");

                ((WelcomeCard) card).setBackgroundColor(Color.rgb(0, 172, 230));



                ((WelcomeCard) card).setOnButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {
                        Toast.makeText(mContext, "Let's be productive!", Toast.LENGTH_SHORT).show();

                                        Intent todoIntent = new Intent(MainActivity.this, ToDoList.class);
                                        MainActivity.this.startActivity(todoIntent);

                    }
                });


                card.setDismissible(true);

                return card;


            case 4:   //Janneisy's Weather

                card = new BasicImageButtonsCard(this);
                card.setDescription("Click below for weather updates");
                card.setTitle("Weather");
                card.setTitleColor(Color.rgb(0, 172, 230));
                card.setDrawable(R.drawable.ic_launcher);
                card.setTag("WEATHER CARD");
                ((BasicImageButtonsCard) card).setLeftButtonText("Current");
                ((BasicImageButtonsCard) card).setRightButtonText("Daily");

                if (position % 2 == 0)
                    ((BasicImageButtonsCard) card).setDividerVisible(true);

                ((BasicImageButtonsCard) card).setOnLeftButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {

                        Uri uri = Uri.parse("http://forecast.io/#/f/40.7792,-73.9070");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                        Toast.makeText(mContext, "Displaying the weather forecast", Toast.LENGTH_SHORT).show();
                    }
                });

                ((BasicImageButtonsCard) card).setOnRightButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {

                        Intent weatherIntent= new Intent(MainActivity.this,WeatherActivity.class);
                        startActivity(weatherIntent);

                        Toast.makeText(mContext, "Displaying current weather", Toast.LENGTH_SHORT).show();
                    }
                });
                card.setDismissible(true);


                return card;


            case 5:  //Joshelyn's Sports Card

                card = new BasicImageButtonsCard(this);
                card.setDescription("Click for more information");
                card.setTitle("FIFA Women's World Cup");
                card.setTitleColor(Color.rgb(0, 172, 230));
                card.setDrawable(R.drawable.smallestfifa);
                card.setTag("Sports");
                ((BasicImageButtonsCard) card).setLeftButtonText("Site");
                ((BasicImageButtonsCard) card).setRightButtonText("Stats");

                if (position % 2 == 0)
                    ((BasicImageButtonsCard) card).setDividerVisible(true);

                ((BasicImageButtonsCard) card).setOnLeftButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {

                        Uri uri = Uri.parse("http://m.fifa.com/womensworldcup/");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                        Toast.makeText(mContext, "Displaying FIFA Stats", Toast.LENGTH_SHORT).show();
                    }
                });

                ((BasicImageButtonsCard) card).setOnRightButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(View view, Card card) {

                        Intent weatherIntent= new Intent(MainActivity.this,SportsActivity.class);
                        startActivity(weatherIntent);

                        Toast.makeText(mContext, "FIFA WOMEN'S WORLD CUP STATS", Toast.LENGTH_SHORT).show();
                    }
                });
                card.setDismissible(true);


                return card;





//            case 6:  //To Do List Card
//                card = new BasicListCard(this);
//                card.setTitle("ToDo List Card");
//                card.setDescription("Please click on the card to edit the list!");
//
//                BasicListAdapter adapter = new BasicListAdapter(this);
//                adapter.add("Text1");
//                adapter.add("Text2");
//                adapter.add("Text3");
//                card.setTag("LIST_CARD");
//
//                ((BasicListCard) card).setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                        Toast.makeText(mContext, "Let's edit the card!", Toast.LENGTH_SHORT).show();
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
//
//                        // set title
//                        alertDialogBuilder.setTitle("Edit the todo list");
//
//                        // set dialog message
//                        alertDialogBuilder
//                                .setMessage("Click yes to exit!")
//                                .setCancelable(false)
//                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,int id) {
//                                        // if this button is clicked, close
//                                        // current activity
//                                        MainActivity.this.finish();
//                                    }
//                                })
//                                .setNegativeButton("No",new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,int id) {
//                                        // if this button is clicked, just close
//                                        // the dialog box and do nothing
//                                        dialog.cancel();
//                                    }
//                                });
//
//                        // create alert dialog
//                        AlertDialog alertDialog = alertDialogBuilder.create();
//
//                        // show it
//                        alertDialog.show();
//
//                    }
//                });
//
//                card.setDismissible(true);
//
//                return card;


            default:
                card = new WelcomeCard(this);
                card.setTitle("Welcome to Happy Juggles");
                card.setDescription("created by H(oshiko) and 2Js(Janneisy & Joshelyn)");
                card.setTag("WELCOME CARD");

                ((WelcomeCard) card).setSubtitle("Swipe Us!");
                ((WelcomeCard) card).setButtonText("Okay! Press Me!");
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

    //Hoshiko's Map Card
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

}

