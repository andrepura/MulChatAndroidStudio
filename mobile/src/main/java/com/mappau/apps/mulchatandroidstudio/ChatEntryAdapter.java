package com.mappau.apps.mulchatandroidstudio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by guru on 22.04.2015.
 */
@EBean
public class ChatEntryAdapter extends RecyclerView.Adapter<ViewWrapper>{

    public static int TYPE_ME = 0;
    public static int TYPE_OTHER = 1;

    public static final String TAG = "ChatEntryAdapter";

    @RootContext
    Context context;

    Sender sender;

    ArrayList<ChatEntry> items = new ArrayList<>();

    public void addElement(ChatEntry entry){
        Log.d(TAG, "addElement: " + entry.getMid());
        for(ChatEntry e : items){
            if(e.getMid().equals(entry.getMid())){
                Log.d(TAG, "element in array");
                return;
            }
        }
        items.add(entry);
        Collections.sort(items);

        notifyDataSetChanged();
    }

    public void  setSender(Sender sender){
        this.sender = sender;
    }

    @Override
    public ViewWrapper onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(context).
                inflate(viewType == TYPE_OTHER ? R.layout.carditem : R.layout.carditemme,
                        parent ,
                        false);
        ViewWrapper holder = new ViewWrapper(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewWrapper holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        ChatEntry entry = items.get(position);
        if(sender == null) return  TYPE_OTHER;
        if(entry.getSender().getSid().equals(sender.getSid())){
            return TYPE_ME;
        }
        return TYPE_OTHER;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
