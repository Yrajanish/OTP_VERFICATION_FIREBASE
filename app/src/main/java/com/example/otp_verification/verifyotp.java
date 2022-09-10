package com.example.otp_verification;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verifyotp extends AppCompatActivity {
   Button verifybutton;
   EditText input1,input2,input3,input4,input5,input6;
   TextView mobile;
   String otpbackend;
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyotp);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        input4 = findViewById(R.id.input4);
        input5 = findViewById(R.id.input5);
        input6 = findViewById(R.id.input6);
        verifybutton = findViewById(R.id.verifybutton);
        mobile = findViewById(R.id.mobile);
        ProgressBar progressBar = findViewById(R.id.progressbar_verifingotp);

        mobile.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")
        ));
        otpbackend = getIntent().getStringExtra("backendotp");

        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!input1.getText().toString().trim().isEmpty()&&!input2.getText().toString().trim().isEmpty()){
                    String enterotp = input1.getText().toString()+input2.getText().toString()+input3.getText().toString()+input4.getText().toString()
                            +input5.getText().toString()+input6.getText().toString();
                    if(!otpbackend.isEmpty()){
                         verifybutton.setVisibility(view.INVISIBLE);
                         progressBar.setVisibility(view.VISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                otpbackend,enterotp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(view.GONE);
                                verifybutton.setVisibility(view.VISIBLE);
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(verifyotp.this,dashboard.class);
                                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                }
                                else{
                                    Toast.makeText(verifyotp.this, "Enter correct otp", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });

                    }else{
                        Toast.makeText(verifyotp.this, "Check your Interner connection", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(verifyotp.this, "OTP VERIFIED", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(verifyotp.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                }

            }
        });

        numberotpmovie();

                }

    private void numberotpmovie() {
     input1.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             input2.requestFocus();
         }

         @Override
         public void afterTextChanged(Editable editable) {

         }
     });
        input5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                input6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                input3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                input4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                input5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}