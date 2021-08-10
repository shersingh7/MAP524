package com.jk.firedemo.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.jk.firedemo.models.Friend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FireDemo Created by jkp on 2021-05-28.
 */
public class FriendRepository {
    private final String TAG = this.getClass().getCanonicalName();
    private final FirebaseFirestore db;
    private final String COLLECTION_NAME = "Friends";
    private final String KEY_NAME = "name";
    public MutableLiveData<List<Friend>> allFriends = new MutableLiveData<>();
    public MutableLiveData<String> foundStatus = new MutableLiveData<>();
    public MutableLiveData<Friend> friendFromDB = new MutableLiveData<>();

    public FriendRepository(){
        this.db = FirebaseFirestore.getInstance();
    }

    public void addFriend(Friend friend){
        try {
            Map<String, Object> data = new HashMap<>();
            data.put(KEY_NAME, friend.getName());
            data.put("phoneNumber", friend.getPhoneNumber());
            data.put("dob", friend.getBirthDate());

            this.db.collection(COLLECTION_NAME).add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "onSuccess: Document created with ID : " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Document can't be created" + e.getLocalizedMessage() );
                        }
                    });

        }catch (Exception ex){
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }

    public void getAllFriends(){
        try{
            this.db.collection(COLLECTION_NAME)
                    .orderBy(KEY_NAME, Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable  FirebaseFirestoreException error) {

                            if (error != null){
                                Log.e(TAG, "onEvent: listening to collection for document changes failed" + error );
                                return;
                            }

                            List<Friend> tempFriendList = new ArrayList<>();

                            if (snapshot != null){
                                Log.d(TAG, "onEvent: Current Data : " + snapshot.getDocumentChanges());

                                for (DocumentChange docChange : snapshot.getDocumentChanges()){

                                    Friend currentFriend = docChange.getDocument().toObject(Friend.class);
                                    currentFriend.setId(docChange.getDocument().getId());
                                    Log.e(TAG, "onEvent: Current Friend : " + currentFriend.toString() );

                                    switch (docChange.getType()){
                                        case ADDED:
                                            tempFriendList.add(currentFriend);
                                            break;
                                        case MODIFIED:
                                            //TASK: find the existing object and replace it with newer one
                                            break;
                                        case REMOVED:
                                            tempFriendList.remove(currentFriend);
                                            break;
                                    }
                                }

                                allFriends.postValue(tempFriendList);
                            }
                        }
                    });

        }catch(Exception ex){
            Log.e(TAG, ex.getLocalizedMessage());
            Log.e(TAG, ex.toString());
        }
    }

    public void searchFriendByName(String nameToSearch){
        this.foundStatus.postValue("SEARCHING");

        try{
            this.db.collection(COLLECTION_NAME).whereEqualTo(KEY_NAME, nameToSearch).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                if (task.getResult().getDocuments().size() != 0){
                                    //matching friend found
                                    Friend matchedFriend = (Friend) task.getResult().getDocuments().get(0).toObject(Friend.class);
                                    matchedFriend.setId(task.getResult().getDocuments().get(0).getId());

                                    Log.e(TAG, "onComplete: Matching Friend " + matchedFriend.toString() );
                                    friendFromDB.postValue(matchedFriend);
                                    foundStatus.postValue("SUCCESS");

                                }else{
                                    Log.e(TAG, "onComplete: No friend with given name found");
                                    foundStatus.postValue("FAILURE");
                                }
                            }
                        }
            });


        }catch (Exception ex){
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
            this.foundStatus.postValue("FAILURE");
        }
    }

    public void removeFriend(String docID){
        try{
            this.db.collection(COLLECTION_NAME).document(docID).delete()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.e(TAG, "onSuccess: Document successfully deleted");
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull  Exception e) {
                    Log.e(TAG, "onFailure: Document couldn't be deleted successfully" + e.getLocalizedMessage() );
                }
            });

        }catch (Exception ex){
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }

    public void updateFriend(Friend friend){
        try{
            Map<String, Object> updateInfo = new HashMap<>();
            updateInfo.put(KEY_NAME, friend.getName());
            updateInfo.put("phoneNumber", friend.getPhoneNumber());

            this.db.collection(COLLECTION_NAME).document(friend.getId())
                    .update(updateInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.e(TAG, "onSuccess: Document Successfully updated" );
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Error updating document " + e.getLocalizedMessage() );
                        }
                    });

        }catch (Exception ex){
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }
}
