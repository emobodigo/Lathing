package com.example.djoha.lathing.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.djoha.lathing.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MethodFirebase {
    private static final String TAG = "MethodFirebase";
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;

    private Context context;
    private String userID;

    public MethodFirebase(Context context) {
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        this.context = context;

        if (mAuth.getCurrentUser()!= null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public boolean checkUsernameExist(String username, DataSnapshot dataSnapshot){
        User user = new User();

        for (DataSnapshot ds: dataSnapshot.child(userID).getChildren()){
            user.setUsername(ds.getValue(User.class).getUsername());
            if (user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public void Register( final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        } else if (task.isSuccessful()){
                            userID = mAuth.getCurrentUser().getUid();
                        }

                        // ...
                    }
                });
    }

    public void addUser(String username, String Nama, String email, String alamat, String kontak, String password, String foto, int pos){
        User user = new User(userID,username,Nama,email,alamat,kontak,password,foto,0);
        databaseReference.child("user").child(userID).setValue(user);
    }

    public User deploydata(DataSnapshot dataSnapshot){
        User user = new User();

        user.setUsername(dataSnapshot.child("username").getValue().toString());
        user.setNama(dataSnapshot.child("nama").getValue().toString());
        user.setKontak(dataSnapshot.child("kontak").getValue().toString());
        user.setAlamat(dataSnapshot.child("alamat").getValue().toString());
        user.setPost(Integer.parseInt(dataSnapshot.child("post").getValue().toString()));
        user.setFoto(dataSnapshot.child("foto").getValue().toString());

        return user;
    }

}
