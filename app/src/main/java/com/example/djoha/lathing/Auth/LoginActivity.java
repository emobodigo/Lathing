package com.example.djoha.lathing.Auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.djoha.lathing.Home.MainActivity;
import com.example.djoha.lathing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity{
    private static final String TAG = "LoginActivity";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context context;
    private ProgressBar pg;
    private TextView tvbuatakun;
    private EditText email, password;
    private Button btn1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pg = (ProgressBar)findViewById(R.id.progressbarlogin);
        email = (EditText)findViewById(R.id.inputEmail);
        password = (EditText)findViewById(R.id.inputpassword);
        context = LoginActivity.this;

        pg.setVisibility(View.GONE);

        setupFirebaseAuth();
        init();
    }

    private void init(){
        btn1 = (Button)findViewById(R.id.btn_login);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails = email.getText().toString();
                String passwords = password.getText().toString();

                if (isStringNull(emails) && isStringNull(passwords)){
                    Toast.makeText(context,"Email dan Password tidak boleh kosong",Toast.LENGTH_LONG).show();
                } else {
                    pg.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(emails, passwords)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()){
                                        Log.w(TAG, "LoginFailed: ",task.getException() );
                                        Toast.makeText(context,"Authentication fail",Toast.LENGTH_LONG).show();
                                        pg.setVisibility(View.GONE);
                                    } else {
                                        try {
                                            if (mAuth.getCurrentUser() != null){
                                                Log.d(TAG, "LoginSukses: ");
                                                Toast.makeText(context,"Authentication succed",Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(context,MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        } catch (NullPointerException e){
                                            Log.e(TAG, "Error: " + e.getMessage() );
                                        }


                                    }
                                }
                            });
                }
            }
        });
        tvbuatakun = (TextView)findViewById(R.id.signuptext);
        tvbuatakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SignUpActivity.class);
                startActivity(intent);
            }
        });

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
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    //user masuk
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
