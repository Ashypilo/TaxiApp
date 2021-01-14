package com.ashypilov.taxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PassengerSignInActivity extends AppCompatActivity {

    private static final String TAG = "PassengerSignInActivity";

    private TextInputLayout textInputEmailPassenger;
    private TextInputLayout textInputNamePassenger;
    private TextInputLayout textInputPasswordPassenger;
    private TextInputLayout textInputConfirmPasswordPassenger;

    private Button loginSignUpButtonPassenger;
    private TextView toggleLoginSignUpTextViewPassenger;
    private boolean isLoginModeActive;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_sign_in);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(PassengerSignInActivity.this, PassengerMapsActivity.class));
        }

        textInputEmailPassenger = findViewById(R.id.textInputEmailPassenger);
        textInputNamePassenger = findViewById(R.id.textInputNamePassenger);
        textInputPasswordPassenger = findViewById(R.id.textInputPasswordPassenger);
        textInputConfirmPasswordPassenger = findViewById(R.id.textInputConfirmPasswordPassenger);

        loginSignUpButtonPassenger = findViewById(R.id.loginSignUpButtonPassenger);
        toggleLoginSignUpTextViewPassenger = findViewById(R.id.toggleLoginSignUpTextViewPassenger);
    }

    private boolean validateEmail() {
        String emailInput = textInputEmailPassenger.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmailPassenger.setError("Please input your email");
            return false;
        }
        else {
            textInputEmailPassenger.setError("");
            return true;
        }
    }

    private boolean validateName() {
        String nameInput = textInputNamePassenger.getEditText().getText().toString().trim();

        if (nameInput.isEmpty()) {
            textInputNamePassenger.setError("Please input your name");
            return false;
        }
        else if (nameInput.length() > 15) {
            textInputNamePassenger.setError("Name length have to be less than 15");
            return false;
        }
        else {
            textInputNamePassenger.setError("");
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPasswordPassenger.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPasswordPassenger.setError("Please input your password");
            return false;
        }
        else if (passwordInput.length() < 7) {
            textInputPasswordPassenger.setError("Name length have to be more than 6");
            return false;
        }
        else {
            textInputPasswordPassenger.setError("");
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String passwordInput = textInputPasswordPassenger.getEditText().getText().toString().trim();
        String confirmPasswordInput = textInputConfirmPasswordPassenger.getEditText().getText().toString().trim();

        if (!passwordInput.equals(confirmPasswordInput)) {
            textInputPasswordPassenger.setError("Password have to match");
            return false;
        }
        else {
            textInputPasswordPassenger.setError("");
            return true;
        }
    }

    public void loginSignUpUser(View view) {
        if (!validateEmail() | !validateName() | !validatePassword()) {
            return;
        }

        if (isLoginModeActive) {
            auth.signInWithEmailAndPassword(
                    textInputEmailPassenger.getEditText().getText().toString().trim(),
                    textInputPasswordPassenger.getEditText().getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                startActivity(new Intent(PassengerSignInActivity.this,
                                        PassengerMapsActivity.class));
                                FirebaseUser user = auth.getCurrentUser();
//                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(PassengerSignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                                // ...
                            }

                            // ...
                        }
                    });
        }
        else {
            if (!validateEmail() | !validateName() | !validatePassword() | !validateConfirmPassword()) {
                return;
            }
            auth.createUserWithEmailAndPassword(
                    textInputEmailPassenger.getEditText().getText().toString().trim(),
                    textInputPasswordPassenger.getEditText().getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                startActivity(new Intent(PassengerSignInActivity.this,
                                        PassengerMapsActivity.class));
                                FirebaseUser user = auth.getCurrentUser();
                                //                            updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(PassengerSignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //                            updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }

    public void toggleLoginSignUpPassenger(View view) {
        if (isLoginModeActive) {
            isLoginModeActive = false;
            loginSignUpButtonPassenger.setText("Sign Up");
            toggleLoginSignUpTextViewPassenger.setText("Or, log in");
            textInputConfirmPasswordPassenger.setVisibility(View.VISIBLE);
        }
        else {
            isLoginModeActive = true;
            loginSignUpButtonPassenger.setText("Sign Up");
            toggleLoginSignUpTextViewPassenger.setText("Or, log in");
            textInputConfirmPasswordPassenger.setVisibility(View.GONE);
        }
    }
}
