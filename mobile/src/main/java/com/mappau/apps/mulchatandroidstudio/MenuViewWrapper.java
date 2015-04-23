package com.mappau.apps.mulchatandroidstudio;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;

/**
 * Created by guru on 22.04.2015.
 */

public class MenuViewWrapper extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtName;
    public ImageView imgIcon;
    MenuEntryAdapter.ClickCallback callback;
    int position;

    public MenuViewWrapper(View itemView) {
        super(itemView);
        txtName = (TextView) itemView.findViewById(R.id.txtMenuEntry);
        imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
        itemView.setOnClickListener(this);
    }

    public void bind(String txt, int icon, MenuEntryAdapter.ClickCallback callback){
        txtName.setText(txt);
        imgIcon.setImageResource(icon);
        this.callback = callback;
    }

    @Override
    public void onClick(View view) {
        callback.click();
    }

}
