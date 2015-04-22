package com.mappau.apps.mulchatandroidstudio;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.util.UUID;


@EActivity(R.layout.activity_main)
@Fullscreen
public class MainActivity extends ActionBarActivity implements Websocket.WebSocketCallback{
    public static final String TAG = "MAINACTIVITY";
    Sender sender;
    Websocket websocket;


    @ViewById
    ListView listView;

    @Bean
    ChatEntryAdapter adapter;

    @AfterViews
    void connectToWebsocket(){
        Log.d(TAG,"connect to webserver ...");
        sender = new Sender(UUID.randomUUID().toString(), "andre");
        websocket = new Websocket(this, "http://mul.pura.at");
        try {
            websocket.init();
        } catch (Exception e) {
            Log.e(TAG,"Exception: "+e.toString());
        }
    }
    @AfterViews
    void bindAdapter(){
        listView.setAdapter(adapter);
    }

    @ItemClick
    void listViewItemClicked(ChatEntry entry){
        showMsg("CLICK"+entry.getMid());
    }

    @UiThread
    void updateUI(String msg){

    }

    @UiThread
    void showMsg(String msg){
        Log.i(TAG, "Toast: " + msg);
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    @UiThread
    void newChatEntry(ChatEntry entry){
        adapter.addElement(entry);
    }

    @Override
    public void onChatEntryReceived(Object chatObj) {
        Log.d(TAG, "Entry received");
        try {
            ChatEntry e = ChatEntry.parseChatEntry(chatObj);
            newChatEntry(e);
        } catch (Exception e1) {
            showMsg("ERROR: "+e1.toString());
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
        JSONObject obj = (JSONObject)chatObj;
        showMsg("ERROR: "+obj.toString());
        Log.e(TAG, "ERROR: "+obj.toString());
    }

    @Override
    public void onConnectionError() {

        showMsg("ERROR:connection");
        Log.e(TAG, "ERROR: connection");
    }
}
