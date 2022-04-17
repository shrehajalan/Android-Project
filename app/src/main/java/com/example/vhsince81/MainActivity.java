package com.example.vhsince81;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textservice.TextInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    EditText editTextEmail;
    EditText editTextPassword;

    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    Button buttonLoginIn;
    Button buttonSignUp;
    FirebaseAuth vhAuth;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;
    String login_username;
    ProgressDialog progress;
    boolean boolean_signin = true;
    boolean boolean_signup = true;

   // SqliteHelper sqliteHelper;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        FirebaseApp.initializeApp(this);
        vhAuth = FirebaseAuth.getInstance();

        buttonLoginIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(boolean_signin) {


                    if (validate()) {

                        progress = new ProgressDialog(MainActivity.this);
                        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progress.show();

                        final String Email = editTextEmail.getText().toString().trim();
                        String Password = editTextPassword.getText().toString().trim();

                        vhAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Query query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("email");
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                String mail = snapshot.child("email").getValue().toString();
                                                if (mail.equals(Email)) {
                                                    login_username = snapshot.child("username").getValue().toString();

                                                    preferences = getSharedPreferences("login", MODE_PRIVATE);
                                                    edit = preferences.edit();
                                                    edit.clear();
                                                    edit.putString("Email", Email);
                                                    edit.putBoolean("islogged", true);
                                                    edit.putString("username", login_username);
                                                    edit.commit();
                                                    break;
                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });


                                    if (Email.equals(getString(R.string.admin_mail))) {

                                        preferences = getSharedPreferences("login", MODE_PRIVATE);
                                        edit = preferences.edit();
                                        edit.clear();
                                        edit.putString("Email", getString(R.string.admin_mail));
                                        edit.putBoolean("islogged", true);
                                        edit.putString("username", getString(R.string.admin_username));
                                        edit.commit();

                                        Intent intent_main = new Intent(MainActivity.this, AdminHomePage.class);
                                        progress.dismiss();
                                        startActivity(intent_main);
                                        finish();
                                        boolean_signin = false;

                                    } else {
                                        Intent intent_main = new Intent(MainActivity.this, HomePage.class);
                                        progress.dismiss();
                                        startActivity(intent_main);
                                        finish();
                                        boolean_signin = false;
                                    }

                                } else
                                {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    progress.dismiss();
                                }


                            }
                        });
                    }
                }
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (boolean_signup) {
                    Intent intent = new Intent(MainActivity.this, Signup_form.class);
                    startActivity(intent);
                    boolean_signup = false;
                }
            }

        });
    }


        private void initViews ()
        {
            editTextEmail = (EditText) findViewById(R.id.editTextEmailLogin);
            editTextPassword = (EditText) findViewById(R.id.editTextPasswordLogin);
            textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmailLogin);
            textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPasswordLogin);
            buttonLoginIn = (Button) findViewById(R.id.buttonLoginIn);
            buttonSignUp = (Button) findViewById(R.id.buttonSignUp);

        }
        public boolean validate (){

            boolean valid = false;
            String Email = editTextEmail.getText().toString();
            String Password = editTextPassword.getText().toString();

            if(Email.isEmpty())
            {
                valid = false;
                textInputLayoutEmail.setError("Email is required!");
            }
            else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
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

}


