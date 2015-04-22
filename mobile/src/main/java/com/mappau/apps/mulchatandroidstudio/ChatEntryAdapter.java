package com.mappau.apps.mulchatandroidstudio;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by guru on 22.04.2015.
 */
@EBean
public class ChatEntryAdapter extends BaseAdapter{
    public static final String TAG = "ChatEntryAdapter";

    List<ChatEntry> entries;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter(){
        entries = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Object getItem(int i) {
        return entries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatEntryView chatEntryView;
        if(view == null){
            chatEntryView = ChatEntryView_.build(context);
        } else {
            chatEntryView = (ChatEntryView) view;
        }

        chatEntryView.bind(entries.get(i));

        return chatEntryView;
    }

    public void addElement(ChatEntry entry){
        Log.d(TAG, "addElement: " + entry.getMid());
        for(ChatEntry e : entries){
            if(e.getMid().equals(entry.getMid())){
                Log.d(TAG, "element in array");
                return;
            }
        }
        entries.add(entry);
        Collections.sort(entries, new Comparator<ChatEntry>() {
            @Override
            public int compare(ChatEntry entry, ChatEntry t1) {
                return (int) (entry.getTimestamp().getTime() - t1.getTimestamp().getTime());
            }
        });
        notifyDataSetChanged();
    }
}