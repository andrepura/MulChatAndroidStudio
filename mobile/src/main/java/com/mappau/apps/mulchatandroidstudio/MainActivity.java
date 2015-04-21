package com.mappau.apps.mulchatandroidstudio;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.UiThread;

import java.net.URISyntaxException;


@EActivity(R.layout.activity_main)
@Fullscreen
public class MainActivity extends ActionBarActivity {


    private Socket mSocket;
    {
        try{
            mSocket = IO.socket("http://mul.pura.at:1337");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @AfterInject
    void connect(){
        mSocket.on("msg", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            }
        });
        mSocket.connect();
    }

    @UiThread
    void updateUI(String msg){

    }
}
