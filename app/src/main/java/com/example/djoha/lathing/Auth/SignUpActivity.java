package com.example.djoha.lathing.Auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.djoha.lathing.R;
import com.example.djoha.lathing.Utils.MethodFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private EditText username, nama, email, alamat, hp, password;
    private String usernames, namas, emails, alamats, hps, passwords;
    private Context context;
    private Button daftar;
    private TextView login;
    ProgressBar pg ;
    MethodFirebase fb;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String append = "";


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = SignUpActivity.this;
        fb = new MethodFirebase(context);
        widget();
        setupFirebaseAuth();
        init();
    }

    private void init(){
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernames = username.getText().toString();
                namas = nama.getText().toString();
                emails = email.getText().toString();
                alamats = alamat.getText().toString();
                hps = hp.getText().toString();
                passwords = password.getText().toString();

                if (checkInput(usernames,namas,emails,alamats,hps,passwords)){
                    pg.setVisibility(View.VISIBLE);
                    fb.Register(emails,passwords);

                }
            }
        });
        login = (TextView)findViewById(R.id.logintext);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean checkInput(String usernamee, String namaa, String emaill, String alamatt, String hpp, String passwordd){
        if (usernamee.equals("")||namaa.equals("")||emaill.equals("")||alamatt.equals("")||hpp.equals("")||passwordd.equals("")){
            Toast.makeText(context,"Semua kolom harus diisi",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void widget(){
        username = (EditText)findViewById(R.id.daftarinputusername);
        nama = (EditText) findViewById(R.id.inputNamadaftar);
        email = (EditText)findViewById(R.id.inputemail);
        alamat = (EditText)findViewById(R.id.inputalamat);
        hp = (EditText)findViewById(R.id.inputphone);
        password = (EditText)findViewById(R.id.inputpasswordDaftar);
        daftar = (Button)findViewById(R.id.btn_daftar);
        pg = (ProgressBar)findViewById(R.id.progressbarregister);
        pg.setVisibility(View.GONE);
    }

    private boolean isStringNull(String string){
        if (string.equals("")){
            return true;
        } else {
            return false;
        }
    }

    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (fb.checkUsernameExist(usernames,dataSnapshot)){
                                append = databaseReference.push().getKey().substring(3,10);
                            }
                            usernames = usernames + append;

                            fb.addUser(usernames,namas,emails,alamats,hps,passwords,"",0);
                            Toast.makeText(context,"Sign-up Sukses",Toast.LENGTH_LONG).show();
                            mAuth.signOut();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    finish();
                } else {
                    //user keluar
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
