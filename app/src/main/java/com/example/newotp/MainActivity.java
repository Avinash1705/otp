package com.example.newotp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
   private EditText otp,number;
    private  Button getotp,sign;
    private String motp,mnumber,VerificatinCode;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
      //  updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        init();
//            motp=otp.getText().toString();
//            mnumber=number.getText().toString();

        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                VerificatinCode=s;
                Toast.makeText(getApplicationContext(),"COde Send TO number",Toast.LENGTH_SHORT).show();

            }
        };

    }

    private void init(){
         otp=findViewById(R.id.otp);number=findViewById(R.id.number);
         getotp=findViewById(R.id.getotp);sign=findViewById(R.id.signin);
    }
    public void sendSms(View view){
        mnumber="+91"+number.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mnumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    public void SignInWithPhone(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(getApplicationContext(),"USer Success SignIN",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Verify(View v){
      String  input_code=otp.getText().toString();
        verifyPhoneNumber(VerificatinCode,input_code);

//        if(VerificatinCode.equals("")){
//            }
    }

    private void verifyPhoneNumber(String verificatinCode, String input_code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificatinCode,input_code);
        SignInWithPhone(credential);
    }

}
