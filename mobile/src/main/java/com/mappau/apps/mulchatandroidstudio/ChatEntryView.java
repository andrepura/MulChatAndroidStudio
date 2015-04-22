package com.mappau.apps.mulchatandroidstudio;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by guru on 22.04.2015.
 */
@EViewGroup(R.layout.item)
public class ChatEntryView extends LinearLayout{
    @ViewById
    TextView txtName;

    @ViewById
    TextView txtMsg;

    public ChatEntryView(Context context){
        super(context);
    }

    public void bind(ChatEntry entry){
        txtName.setText(entry.sender.getName());
        txtMsg.setText(entry.msg);
    }
}
