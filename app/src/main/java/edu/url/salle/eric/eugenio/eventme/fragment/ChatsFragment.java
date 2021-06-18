package edu.url.salle.eric.eugenio.eventme.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.url.salle.eric.eugenio.eventme.ChatActivity;
import edu.url.salle.eric.eugenio.eventme.R;
import edu.url.salle.eric.eugenio.eventme.adapter.UserAdapter;
import edu.url.salle.eric.eugenio.eventme.api.ApiAdapter;
import edu.url.salle.eric.eugenio.eventme.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatsFragment extends Fragment {

    // Recycler view
    private RecyclerView mUserRecycler;
    private UserAdapter mUserAdapter;

    // Search view
    private SearchView mSearchView;

    private ChatsFragment() {
        // Required empty private constructor
    }

    public static ChatsFragment newInstance() {
        return new ChatsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        configureRecycleView(view);
        configureSearchView(view);

        return view;
    }

    // ----------------------------------------------
    // RECYCLER VIEW
    // ----------------------------------------------

    private void configureRecycleView(View view) {
        mUserRecycler = view.findViewById(R.id.chat_recyclerview);

//        // ---Provisional--------------------------------------------------------------------
//
//        List<Friend> friends = new ArrayList<>();
//        for (int i = 1; i <= 3; i++) {
//            friends.add(new Friend(i, "Friend", " " + i, "friend" + i + "@mail.com"));
//        }
//
//        // ----------------------------------------------------------------------------------

        mUserAdapter = new UserAdapter(this);
        mUserAdapter.setListener(this::onCLickStartChatActivity);

        mUserRecycler.setAdapter(mUserAdapter);
        mUserRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        getChats();
    }

    private void onCLickStartChatActivity(int position) {
        if (isAdded()) {
            Intent intent = ChatActivity.newIntent(getActivity(), position, -1);
            getActivity().startActivity(intent);
        }
    }

    // ----------------------------------------------
    // SEARCH VIEW
    // ----------------------------------------------

    private void configureSearchView(View view) {
        mSearchView = view.findViewById(R.id.chat_searchView);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchUser(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                   getChats();
                }
                return false;
            }
        });
    }

    // ----------------------------------------------
    // API LOGIC
    // ----------------------------------------------

    private void searchUser(String query) {
        String token = User.getUser().getToken();
        ApiAdapter.getInstance().searchUser(token, query).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    UserAdapter.setFriends(response.body());
                    mUserAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    private void getChats() {
        String token = User.getUser().getToken();
        ApiAdapter.getInstance().getFriends(token).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    UserAdapter.setFriends(response.body());
                    mUserAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}