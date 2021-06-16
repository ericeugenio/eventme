package edu.url.salle.eric.eugenio.eventme;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.url.salle.eric.eugenio.eventme.adapter.FriendAdapter;
import edu.url.salle.eric.eugenio.eventme.adapter.MessageAdapter;
import edu.url.salle.eric.eugenio.eventme.model.Friend;
import edu.url.salle.eric.eugenio.eventme.model.Message;

public class ChatActivity extends AppCompatActivity {

    private static final String EXTRA_FRIEND_ID = "FRIEND_ID";

    // Toolbar
    private Toolbar mToolbar;
    private ActionBar mActionBar;

    // Recycler view
    private RecyclerView mMessageRecycler;
    private MessageAdapter mMessageAdapter;
    private LinearLayoutManager mLinearLayoutManager;


    // Widgets
    private ShapeableImageView mProfileImage;
    private TextView mFriendName;
    private EditText mInputText;

    private Friend mFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        configureView();
        configureToolbar();
        configureRecycleView();
    }

    public static Intent newIntent(Context packageContext, int friendID) {
        Intent intent = new Intent(packageContext, ChatActivity.class);
        intent.putExtra(EXTRA_FRIEND_ID, friendID);
        return intent;
    }

    private void configureView() {
        int position = getIntent().getIntExtra(EXTRA_FRIEND_ID, 0);
        mFriend = FriendAdapter.getFriends().get(position);
        String name = mFriend.getName() + ' ' + mFriend.getLastName();

        mProfileImage = findViewById(R.id.individual_chat_image);
        mFriendName = findViewById(R.id.individual_chat_name);
        mFriendName.setText(name);

        mInputText = findViewById(R.id.individual_chat_text);
    }

    public void onCLickSendMessage(View view) {
        String input = mInputText.getText().toString();

        if (!input.isEmpty()) {
            MessageAdapter.getMessages().add(new Message(input, 1, mFriend.getFriendId(), new Date()));
            mLinearLayoutManager.scrollToPosition(MessageAdapter.getMessages().size() - 1);
            mMessageAdapter.notifyDataSetChanged();

            // TODO: PUT message on API

            mInputText.setText("");
        }
    }

    // ----------------------------------------------
    // TOOLBAR
    // ----------------------------------------------

    private void configureToolbar() {
        mToolbar = findViewById(R.id.individual_chat_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mActionBar = getSupportActionBar();
        if (mActionBar != null) mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    // ----------------------------------------------
    // RECYCLER VIEW
    // ----------------------------------------------

    private void configureRecycleView() {
        mMessageRecycler = findViewById(R.id.individual_chat_recyclerview);
        mMessageRecycler.setHasFixedSize(true);

        // ---Provisional--------------------------------------------------------------------

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("Hello Marc", 1, 0, new Date()));
        messages.add(new Message("How are you doing?", 1, 0, new Date()));
        messages.add(new Message("Hey Jordi! I'm fine", 0, 1, new Date()));
        messages.add(new Message("What about you?", 0, 1, new Date()));
        messages.add(new Message("Working in the app bro...", 1, 0, new Date()));
        messages.add(new Message("I was wondering if u would like to go out for an ice cream", 1, 0, new Date()));
        messages.add(new Message("Of course! why not", 0, 1, new Date()));
        messages.add(new Message("Nice, it is okay in an hour?", 1, 0, new Date()));
        messages.add(new Message("Sure bro! see you then!", 0, 1, new Date()));

        // ----------------------------------------------------------------------------------

        mMessageAdapter = new MessageAdapter(messages);
        mMessageRecycler.setAdapter(mMessageAdapter);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mMessageRecycler.setLayoutManager(mLinearLayoutManager);
    }
}