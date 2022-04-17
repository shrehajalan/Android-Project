package com.example.vhsince81;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.PrivateKey;

public class Signup_form extends AppCompatActivity {

    EditText editTextUserName;
    EditText editTextEmail;
    EditText editTextPassword;

    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;
    TextInputLayout textInputLayoutUserName;

    Button buttonSubmit;
    boolean boolean_submit = true;

    private FirebaseAuth vhAuth;

    //SqliteHelper sqliteHelper;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        initViews();

        vhAuth = FirebaseAuth.getInstance();

      //  sqliteHelper = new SqliteHelper(this);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (boolean_submit) {
                    if (validate()) {
                        final String UserName = editTextUserName.getText().toString();
                        final String Email = editTextEmail.getText().toString();
                        final String Password = editTextPassword.getText().toString();

                        vhAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();

                                    User user = new User(UserName, Email, Password);
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                    mDatabase.child("Users").child(task.getResult().getUser().getUid()).setValue(user);
                                    Intent intent = new Intent(Signup_form.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    boolean_submit = false;
                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                        Toast.makeText(getApplicationContext(), "Already user exist with this Email ID", Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });

               /*     if (!sqliteHelper.isEmailExists(Email)) {
                        sqliteHelper.addUser(new User(null, UserName, Email, Password));


                        Snackbar.make(buttonSubmit, "User created s" + "uccessfully! Please Login ", Snackbar.LENGTH_LONG).show();
                        Intent intent = new Intent(Signup_form.this, MainActivity.class);
                        startActivity(intent);
                    } else {

                        Snackbar.make(buttonSubmit, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }*/
                    }
                }
            }
        });
    }

    private void initViews()
    {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutUserName = (TextInputLayout )findViewById(R.id.textInputLayoutUserName);
        buttonSubmit =(Button)findViewById(R.id.buttonSubmit);

    }

    public boolean validate (){

        boolean valid = false;
        String UserName = editTextUserName.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        if (UserName.isEmpty()) {
            valid = false;
            textInputLayoutUserName.setError("Username is required!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                textInputLayoutUserName.setError(null);
            } else {
                valid = false;
                textInputLayoutUserName.setError("Username is to short!");
            }
        }

        if(Email.isEmpty())
        {
            valid = false;
            textInputLayoutEmail.setError("Email is required!");
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            textInputLayoutEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            textInputLayoutEmail.setError(null);
        }

        if (Password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setError("Password is required!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                textInputLayoutPassword.setError(null);
            } else {
                valid = false;
                textInputLayoutPassword.setError("Password is to short!");
            }
        }
        return valid;
    }
    @CallSuper
    protected void onResume()
    {
        super.onResume();
        boolean_submit = true;
    }


}
