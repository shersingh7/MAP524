package com.jk.firedemo.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.jk.firedemo.models.Friend;
import com.jk.firedemo.repository.FriendRepository;

import java.util.List;

/**
 * FireDemo Created by jkp on 2021-05-28.
 */
public class FriendViewModel extends AndroidViewModel {
    private final String TAG = this.getClass().getCanonicalName();
    private static FriendViewModel ourInstance;

    private final FriendRepository friendRepository = new FriendRepository();
    public MutableLiveData<List<Friend>> allFriends;

    public static FriendViewModel getInstance(Application application){
        if (ourInstance == null){
            ourInstance = new FriendViewModel(application);
        }
        return ourInstance;
    }

    public FriendRepository getFriendRepository(){
        return friendRepository;
    }

    private FriendViewModel(Application application){
        super(application);
        this.friendRepository.getAllFriends();
        this.allFriends = this.friendRepository.allFriends;
    }

    public void addFriend(Friend newFriend){
        this.friendRepository.addFriend(newFriend);
    }

    public void searchFriendByName(String friendName){
        this.friendRepository.searchFriendByName(friendName);
    }

    public void removeFriend(String docID){
        this.friendRepository.removeFriend(docID);
    }

    public void updateFriend(Friend friend){
        this.friendRepository.updateFriend(friend);
    }
}
