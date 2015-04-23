package com.mappau.apps.mulchatandroidstudio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by guru on 23.04.2015.
 */
@EBean
public class MenuEntryAdapter extends RecyclerView.Adapter<MenuViewWrapper>  {

    private static final String TAG = "MenuEntryAdapter";

    static class MenuEntry{
        String name;
        int icon;
        public MenuEntry(String name, int icon){
            this.name = name;
            this.icon = icon;
        }
    }
    interface ClickCallback{
        void click();
    }


    DrawerUpdate drawerCallback;
    ArrayList<MenuEntry> items = new ArrayList<>();

    @RootContext
    Context context;

    @Override
    public MenuViewWrapper onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(context).
                inflate(R.layout.menuitem,
                        parent ,
                        false);
        MenuViewWrapper holder = new MenuViewWrapper(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MenuViewWrapper holder, final int position) {
        MenuEntry e = items.get(position);
        holder.bind(e.name, e.icon, new ClickCallback() {
            @Override
            public void click() {
                showMsg("Click "+position);
                drawerCallback.closeDrawer();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setDrawerCallback(DrawerUpdate callback){
        this.drawerCallback = callback;
    }

    public void addElement(MenuEntry e){
        items.add(e);
    }
    @UiThread
    void showMsg(String msg) {
        Log.i(TAG, "Toast: " + msg);
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
