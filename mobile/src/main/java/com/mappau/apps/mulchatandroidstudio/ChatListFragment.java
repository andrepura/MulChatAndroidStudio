package com.mappau.apps.mulchatandroidstudio;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by guru on 23.04.2015.
 */
@EFragment(R.layout.chatlistfragment)
public class ChatListFragment extends Fragment {
    LinearLayoutManager layoutManager;

    @ViewById
    RecyclerView recycleView;

    @Bean
    ChatEntryAdapter adapter;

    @AfterViews
    void bindAdapter() {

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(adapter);
    }

    @UiThread
    public void scrollDown(){
        recycleView.scrollToPosition(adapter.getItemCount() - 1);
    }

    public void setSender(Sender sender){
        adapter.setSender(sender);
    }

    @UiThread
    void newChatEntry(ChatEntry entry) {
        adapter.addElement(entry);
    }
}
