package com.example.internetrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
//import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Objects;

//import javax.security.auth.callback.Callback;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Call;
import okhttp3.Callback;

    public class MainActivity extends AppCompatActivity {

        TextView mR;
        Button b1;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mR= (TextView)findViewById(R.id.k);
            b1 = (Button)findViewById(R.id.b1);



            String url = "https://lamp.ms.wits.ac.za/mc/test2.php"; //THE REQUEST USED ex5.2
            //String url = "https://lamp.ms.wits.ac.za/mc/test.php"; //ex5.1 url
            //String url = "https://lamp.ms.wits.ac.za/~s2328024/cars.php"; //lab6.1
            //String url = "https://lamp.ms.wits.ac.za/~s2328024/cars2.php?brand=Toyota"; //lab6.2

            b1.setOnClickListener(new View.OnClickListener() { //b1 displays
                @Override
                public void onClick(View v) {

                    OkHttpClient client = new OkHttpClient();

                    RequestBody formBody = new FormBody.Builder() //excercise 5.2
                            .add("username", "pravesh")
                            .build();

                    Request request = new Request.Builder()
                            .url(url)
                            .post(formBody)//ex5.2 lab5
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

                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mR.setText(myResponse);
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }

    }