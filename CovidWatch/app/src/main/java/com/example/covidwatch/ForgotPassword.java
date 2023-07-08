package com.example.covidwatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ForgotPassword extends AppCompatActivity {
    String email; String password;
    Button blogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        EditText emal = (EditText)findViewById(R.id.inputEmail);
        EditText pwd = (EditText)findViewById(R.id.inputPassword);


        //php to send request
        String login = "https://lamp.ms.wits.ac.za/~s2328024/ForgotPassword.php";

        blogin = (Button)findViewById(R.id.btnLogin);
        blogin.setOnClickListener(new View.OnClickListener() { //b1 displays
            @Override
            public void onClick(View v) {

                email= emal.getText().toString();
                password = pwd.getText().toString();


                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()

                        .add("email", email)
                        .add("password", password)

                        .build();

                Request request = new Request.Builder()
                        .url(login)
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String myResponse = response.body().string();


                            ForgotPassword.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ForgotPassword.this, myResponse, Toast.LENGTH_LONG).show();

                                }
                            });
                        }
                    }
                });
            }
        });

        TextView btnToLogin = findViewById(R.id.textToLogin);
        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
            }
        });
    }
}
