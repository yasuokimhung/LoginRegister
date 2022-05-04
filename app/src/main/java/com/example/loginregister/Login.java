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

public class Login extends AppCompatActivity {

    TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;

    public String getUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progress);

        //Nut quay ve cho dang ky
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        //Nut dang nhap
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username, password;

                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());


                if(!username.equals("") && !password.equals("")) {

                    progressBar.setVisibility(View.VISIBLE);

                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";


                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;

                            PutData putData = new PutData("http://192.168.1.8:8080/LoginRegister/login.php", "POST", field, data);
                            if (putData.startPut()) {

                                progressBar.setVisibility(View.GONE);

                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Login Success")){
                                        String admin = String.valueOf(textInputEditTextUsername.getText());
                                        //lay username de update
//                                        getUsername = username;
                                        if(admin.equals("admin")){
                                            Intent intent = new Intent(getApplicationContext(), Admin.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Intent intent = new Intent(getApplicationContext(), Admin.class);
                                            intent.putExtra("getUsername", username);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }else{
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                    //End ProgressBar (Set visibility to GONE)
//                        Log.i("PutData", result);
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "All field required!!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}