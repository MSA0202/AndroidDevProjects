package com.example.airtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doContract(View v){
        EditText e1 = (EditText)findViewById(R.id.et1);
        EditText e2 = (EditText)findViewById(R.id.et2);
        EditText e3 = (EditText)findViewById(R.id.et3);
        TextView e4 = (TextView)findViewById(R.id.et4);

        String e11 = e1.getText().toString();
        String e22 = e2.getText().toString();
        String e33 = e3.getText().toString();

        e4.setText("The best contract available to you is "+dataE(e11,e22,e33));

    }
    public String dataE(String data, String call, String sms){
        int idata = Integer.parseInt(data);
        int icall = Integer.parseInt(call);
        int isms = Integer.parseInt(sms);

        int speakup= 3*idata + 1*icall + 2*isms;
        int messager= 4*idata + 3*icall + 1*isms;
        int down= 1*idata + 4*icall + 3*isms;

        ArrayList<Integer> x = new ArrayList<Integer>();
        x.add(speakup);
        x.add(messager);
        x.add(down);

        Collections.sort(x);

        if(x.get(0)==speakup){
            return "SpeakUp";
        }
        else if(x.get(0)==messager){
            return "Messager";
        }
        else{
            return "Downloader";
        }
    }


}