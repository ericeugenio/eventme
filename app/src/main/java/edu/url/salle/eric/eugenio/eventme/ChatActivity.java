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

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import edu.url.salle.eric.eugenio.eventme.adapter.EventAdapter;
import edu.url.salle.eric.eugenio.eventme.adapter.UserAdapter;
import edu.url.salle.eric.eugenio.eventme.adapter.MessageAdapter;
import edu.url.salle.eric.eugenio.eventme.api.ApiAdapter;
import edu.url.salle.eric.eugenio.eventme.model.Event;
import edu.url.salle.eric.eugenio.eventme.model.Message;
import edu.url.salle.eric.eugenio.eventme.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private static final String EXTRA_FRIEND_POS = "FRIEND_POS";
    private static final String EXTRA_FRIEND_ID = "FRIEND_ID";

    private static final long REFRESH_TIME = 2000L;
    private Timer mTimer;

    // Toolbar
    private Toolbar mToolbar;
    private ActionBar mActionBar;

    // Recycler view
    private RecyclerView mMessageRecycler;
    private MessageAdapter mMessageAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private int mNumMessages;

    // Widgets
    private ShapeableImageView mProfileImage;
    private TextView mFriendName;
    private EditText mInputText;

    private User mFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        MessageAdapter.clearMessages();

        configureView();
        configureToolbar();
        configureRecycleView();
    }

    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }

    public static Intent newIntent(Context packageContext, int friendPos, long friendId) {
        Intent intent = new Intent(packageContext, ChatActivity.class);
        intent.putExtra(EXTRA_FRIEND_POS, friendPos);
        intent.putExtra(EXTRA_FRIEND_ID, friendId);
        return intent;
    }

    private void configureView() {
        long friendId = getIntent().getLongExtra(EXTRA_FRIEND_ID, 0);

        if (friendId == -1) {
            int position = getIntent().getIntExtra(EXTRA_FRIEND_POS, 0);
            mFriend = UserAdapter.getFriends().get(position);
            loadFriendData();
            configureListener();
        }
        else {
            getUser(friendId);
        }
    }

    private void loadFriendData() {
        String name = mFriend.getName() + ' ' + mFriend.getLastName();

        mProfileImage = findViewById(R.id.individual_chat_image);
        Glide.with(this)
                .load(mFriend.getImage())
                .placeholder(R.drawable.img_placeholder_event)
                .error(R.drawable.img_default_profile)
                .into(mProfileImage);

        mFriendName = findViewById(R.id.individual_chat_name);
        mFriendName.setText(name);

        mInputText = findViewById(R.id.individual_chat_text);
    }

    public void onCLickSendMessage(View view) {
        String input = mInputText.getText().toString();

        if (!input.isEmpty()) {
            sendMessage(input);
            mInputText.setText("");
        }
    }

    private void configureListener() {
        // Check for new messages every 'REFRESH_TIME' seconds
        mTimer = new Timer();
        mTimer.schedule(new TimerTask()
        {
            @Override
            public void run() {
                getMessages();
            }
        }, 0, REFRESH_TIME);
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

//        // ---Provisional--------------------------------------------------------------------
//
//        List<Message> messages = new ArrayList<>();
//        messages.add(new Message("Hello Marc", 1, 0, new Date()));
//        messages.add(new Message("How are you doing?", 1, 0, new Date()));
//        messages.add(new Message("Hey Jordi! I'm fine", 0, 1, new Date()));
//        messages.add(new Message("What about you?", 0, 1, new Date()));
//        messages.add(new Message("Working in the app bro...", 1, 0, new Date()));
//        messages.add(new Message("I was wondering if u would like to go out for an ice cream", 1, 0, new Date()));
//        messages.add(new Message("Of course! why not", 0, 1, new Date()));
//        messages.add(new Message("Nice, it is okay in an hour?", 1, 0, new Date()));
//        messages.add(new Message("Sure bro! see you then!", 0, 1, new Date()));
//
//        // ----------------------------------------------------------------------------------

        mMessageAdapter = new MessageAdapter();
        mMessageRecycler.setAdapter(mMessageAdapter);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mMessageRecycler.setLayoutManager(mLinearLayoutManager);
    }

    // ----------------------------------------------
    // API LOGIC
    // ----------------------------------------------

    private void getMessages() {
        String token = User.getUser().getToken();
        long userId = mFriend.getId();

        ApiAdapter.getInstance().getMessages(token, userId).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > mNumMessages) {
                        MessageAdapter.setMessages(response.body());
                        mNumMessages = response.body().size();
                        mMessageAdapter.notifyDataSetChanged();
                        mLinearLayoutManager.scrollToPosition(MessageAdapter.getMessages().size() - 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    private void sendMessage(String input) {
        String token = User.getUser().getToken();
        long senderId = User.getUser().getId();
        long receiverId = mFriend.getId();

        ApiAdapter.getInstance().sendMessage(token, new Message(input, senderId, receiverId, new Date())).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    MessageAdapter.getMessages().add(new Message(input, 1, mFriend.getId(), new Date()));
                    mLinearLayoutManager.scrollToPosition(MessageAdapter.getMessages().size() - 1);
                    mMessageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }

    private void getUser(long friendId) {
        String token = User.getUser().getToken();
        ApiAdapter.getInstance().getUser(token, friendId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    mFriend = response.body().get(0);
                    loadFriendData();
                    configureListener();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}