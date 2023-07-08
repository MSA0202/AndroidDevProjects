package com.example.jasonpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    LinearLayout l;//= (LinearLayout)findViewById(R.id.ll);
    String url = "https://lamp.ms.wits.ac.za/~s2328024/practice.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l = (LinearLayout)findViewById(R.id.ll2);
    }


    public void doView(View v){
        //TextView t = (TextView)findViewById(R.id.textview);

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder() //excercise 5.2
                .add("username", "pravesh")
                .build();

        Request request = new Request.Builder()
                .url(url)
                //.post(formBody)
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
                            //t.setText(myResponse);
                            processBody(myResponse);
                        }
                    });
                }
            }
        });
    }

    public void processBody(String response){
        l.removeAllViews();

        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);

        TextView idHText = new TextView(this);
        idHText.setText("id");
        header.addView(idHText);

        TextView senderHText = new TextView(this);
        senderHText.setText("sender");
        header.addView(senderHText);

        TextView receiverHText = new TextView(this);
        receiverHText.setText("receiver");
        header.addView(receiverHText);

        TextView productHText = new TextView(this);
        productHText.setText("product");
        header.addView(productHText);

        l.addView(header);
        try {
            JSONArray ja = new JSONArray(response);
            for (int i=0; i<ja.length(); i++){
                JSONObject jo = (JSONObject)ja.get(i);

                int id=jo.getInt("id");
                String sender=(jo.getString("sender"));
                String receiver=(jo.getString("receiver"));
                String product=(jo.getString("product"));

                LinearLayout item = new LinearLayout(this);
                item.setOrientation(LinearLayout.HORIZONTAL);

                TextView idText = new TextView(this);
                idText.setText(id);
                item.addView(idText);

                TextView senderText = new TextView(this);
                senderText.setText(sender);
                item.addView(senderText);

                TextView receiverText = new TextView(this);
                receiverText.setText(receiver);
                item.addView(receiverText);

                TextView productText = new TextView(this);
                productText.setText(product);
                item.addView(productText);

                l.addView(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}