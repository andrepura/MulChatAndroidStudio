package com.mappau.apps.mulchatandroidstudio;

import android.app.Activity;
import android.graphics.Outline;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.util.UUID;


@EActivity(R.layout.activity_main)
@Fullscreen
public class MainActivity extends Activity implements Websocket.WebSocketCallback {
    public static final String TAG = "MAINACTIVITY";
    Sender sender;
    Websocket websocket;
    LinearLayoutManager layoutManager;

    @ViewById
    RecyclerView recycleView;

    @ViewById
    EditText editText;

    @ViewById
    Button fabbutton;

    @Bean
    ChatEntryAdapter adapter;

    @AfterViews
    void connectToWebsocket() {
        Log.d(TAG, "connect to webserver ...");
        sender = new Sender(UUID.randomUUID().toString(), "andre");
        adapter.setSender(sender);
        websocket = new Websocket(this, "http://mul.pura.at");
        try {
            websocket.init();
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.toString());
        }
    }

    @AfterViews
    void bindAdapter() {
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(adapter);

    }

    @UiThread
    void updateUI(String msg) {

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

    @UiThread
    void newChatEntry(ChatEntry entry) {
        adapter.addElement(entry);

    }

    @Override
    public void onChatEntryReceived(Object chatObj) {
        Log.d(TAG, "Entry received");
        try {
            ChatEntry e = ChatEntry.parseChatEntry(chatObj);
            newChatEntry(e);
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
}
