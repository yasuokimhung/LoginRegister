package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Admin extends AppCompatActivity {
    TextInputEditText textInputEditTextFullName, textInputEditTextPassword, textInputEditTextEmail, textInputEditTextUsername;
    Button buttonUpdate;
    TextView textViewLogout;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextFullName = findViewById(R.id.fullname);
        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        textViewLogout = findViewById(R.id.logout);
        progressBar = findViewById(R.id.progress);


        //Nut logout
        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        //Nut update
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fullname, email, password, username;
                username = String.valueOf(textInputEditTextUsername.getText());
                fullname = String.valueOf(textInputEditTextFullName.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                email = String.valueOf(textInputEditTextEmail.getText());

                if (!fullname.equals("") && !password.equals("") && !email.equals("") && !username.equals("")) {

                    progressBar.setVisibility(View.VISIBLE);

                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";

                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;

                            PutData putData = new PutData("http://192.168.1.8:8080/LoginRegister/update.php", "POST", field, data);
                            if (putData.startPut()) {

                                progressBar.setVisibility(View.GONE);

                                if (putData.onComplete()) {

                                    String result = putData.getResult();

                                    if (result.equals("Update Success")) {
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                    //End ProgressBar (Set visibility to GONE)
//                        Log.i("PutData", result);
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All field required!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

