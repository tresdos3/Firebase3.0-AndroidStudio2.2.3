package com.example.tresdos.firebaseapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText mTxTEma;
    private EditText mTxTPass;
    private Button mIniciar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mTxTEma = (EditText) findViewById(R.id.txtEmail);
        mTxTPass = (EditText)findViewById(R.id.txtPassword);
        mIniciar = (Button)findViewById(R.id.btnIniciar);
        mIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSignin();;
            }
        });
        mAuthList = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
    }
    @Override
    protected  void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthList);
    }
    private void StartSignin(){
        String email = mTxTEma.getText().toString();
        String password = mTxTPass.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Los Campos Estan Vacios", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Falla :v", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(LoginActivity.this, "Haz iniciado Session :D", Toast.LENGTH_SHORT).show();
                }
            });
//            para crear usuarios
//            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (!task.isSuccessful()){
//                        Toast.makeText(LoginActivity.this, "Falla :v", Toast.LENGTH_SHORT).show();
//                    }
//                    Toast.makeText(LoginActivity.this, "Haz iniciado Session :D", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
