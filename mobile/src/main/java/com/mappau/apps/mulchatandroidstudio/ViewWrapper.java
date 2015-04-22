package com.mappau.apps.mulchatandroidstudio;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by guru on 22.04.2015.
 */

public class ViewWrapper extends RecyclerView.ViewHolder {

    public TextView txtName;
    public TextView txtMsg;


    public ViewWrapper(View itemView) {
        super(itemView);
        txtName = (TextView) itemView.findViewById(R.id.txtCardName);
        txtMsg = (TextView) itemView.findViewById(R.id.txtCardMsg);
    }

    public void bind(ChatEntry entry){
        txtName.setText(entry.getSender().getName());
        txtMsg.setText(entry.getMsg());
    }
}
