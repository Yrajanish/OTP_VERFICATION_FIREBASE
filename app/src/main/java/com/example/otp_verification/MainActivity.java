package com.example.otp_verification;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
  EditText enternumber;
  Button otpbutton;
  ProgressBar progressBar;
  FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         enternumber = findViewById(R.id.inputnumber);
         otpbutton = findViewById(R.id.otpbutton);
         progressBar = findViewById(R.id.progressbar_sendingotp);

        mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                progressBar.setVisibility(GONE);
                otpbutton.setVisibility(VISIBLE);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressBar.setVisibility(GONE);
                otpbutton.setVisibility(VISIBLE);
                Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(backendotp, forceResendingToken);
                Intent intent = new Intent(MainActivity.this,verifyotp.class);
                intent.putExtra("mobile",enternumber.getText().toString());
                intent.putExtra("backendotp",backendotp);
                startActivity(intent);


            }
        };
        otpbutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String number = enternumber.getText().toString();
                 if(!enternumber.getText().toString().trim().isEmpty()){
                     if((enternumber.getText().toString().trim().length()==10)){
                         progressBar.setVisibility(VISIBLE);
                         otpbutton.setVisibility(view.INVISIBLE);

                         PhoneAuthOptions options =
                                 PhoneAuthOptions.newBuilder(firebaseAuth)
                                         .setPhoneNumber("+91"+number)       // Phone number to verify
                                         .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                         .setActivity(MainActivity.this)                 // Activity (for callback binding)
                                         .setCallbacks(mCallbacks)// OnVerificationStateChangedCallbacks
                                         .build();
                         PhoneAuthProvider.verifyPhoneNumber(options);
                     }
                  else{
                         Toast.makeText(MainActivity.this, "Enter correct Mobile Number", Toast.LENGTH_SHORT).show();
                     }
                 }
                 else{
                     Toast.makeText(MainActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                 }
             }
         });

    }
}