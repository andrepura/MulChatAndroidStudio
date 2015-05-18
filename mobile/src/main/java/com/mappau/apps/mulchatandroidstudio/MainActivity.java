package com.mappau.apps.mulchatandroidstudio;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.util.UUID;


@EActivity(R.layout.main)
public class MainActivity extends AppCompatActivity implements Websocket.WebSocketCallback, DrawerUpdate{
    public static final String TAG = "MAINACTIVITY";
    Sender sender;
    Websocket websocket;

    LinearLayoutManager layoutManager;

    @ViewById
    RecyclerView sideMenu;

    @ViewById
    DrawerLayout drawer;

    ActionBarDrawerToggle drawerToggle;

//    @Pref
//    MyPrefs_ myPrefs;

    @ViewById
    EditText editText;

    @ViewById
    Button fabbutton;

    @ViewById
    Toolbar toolbar;

    @FragmentById
    ChatListFragment chatlist;

    @Bean
    MenuEntryAdapter adapter;



    @AfterViews
    void connectToWebsocket() {
        Log.d(TAG, "connect to webserver ...");
//        sender = new Sender(UUID.randomUUID().toString(), myPrefs.name().getOr("andre"));
        sender = new Sender(UUID.randomUUID().toString(), "andre");

        chatlist.setSender(sender);
        websocket = new Websocket(this, "http://mul.pura.at");
        try {
            websocket.init();
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.toString());
        }
    }

    @AfterViews
    void initToolbar() {
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        sideMenu.setLayoutManager(layoutManager);
        sideMenu.setAdapter(adapter);

        setSupportActionBar(toolbar);

        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        adapter.addElement(new MenuEntryAdapter.MenuEntry("Settings", R.drawable.ic_launcher));
        adapter.addElement(new MenuEntryAdapter.MenuEntry("Irgendwas", R.drawable.ic_launcher));

        adapter.setDrawerCallback(this);
    }

    @UiThread
    void showMsg(String msg) {
        Log.i(TAG, "Toast: " + msg);
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Click
    void fabbutton() {
        Log.d(TAG, "click");
        try {
            JSONObject obj = new JSONObject();
            obj.put("sender", sender.toJSONObj());
            obj.put("msg", editText.getText().toString());
            websocket.emitChatMessage(obj);
            editText.setText("");
        } catch (Exception e) {
            Log.e(TAG, "ERROR: " + e.toString());
        }
    }

    void changeName(String name) {
        sender.setName(name);
        //myPrefs.name().put(name);
    }



    @Override
    public void onChatEntryReceived(Object chatObj) {
        Log.d(TAG, "Entry received");
        try {
            ChatEntry e = ChatEntry.parseChatEntry(chatObj);
            chatlist.newChatEntry(e);
            chatlist.scrollDown();
        } catch (Exception e1) {
            showMsg("ERROR: " + e1.toString());
            Log.e(TAG, "ERROR: " + e1.toString());
        }
    }

    @Override
    public void onConnect() {
        showMsg("connected");
    }

    @Override
    public void onDisconnect() {
        showMsg("disconnected");
    }

    @Override
    public void onChatError(Object chatObj) {
        JSONObject obj = (JSONObject) chatObj;
        showMsg("ERROR: " + obj.toString());
        Log.e(TAG, "ERROR: " + obj.toString());
    }

    @Override
    public void onConnectionError() {
        showMsg("ERROR:connection");
        Log.e(TAG, "ERROR: connection");
    }

    @Override
    public void closeDrawer() {
        drawer.closeDrawer(sideMenu);
    }

    @Override
    public void openDrawer() {
        drawer.openDrawer(sideMenu);
    }
}
