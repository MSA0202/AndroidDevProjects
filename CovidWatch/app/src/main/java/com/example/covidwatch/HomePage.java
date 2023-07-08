package com.example.covidwatch;

import android.os.Bundle;
import java.io.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends Fragment{

    TextView txtSymptoms, txtQuote;
    String Symptoms,Quote;
    String[] ArrToDos = {"Covid-19 affects different people in different ways","Seek medical attention if you have serious symptoms",
            "People with mild symptoms should manage their symptoms at home","Wear a mask. Save lives, Symptoms will usually take 6-14 days to show",
            "Properly fitted masks can help prevent the spread of COVID-19","Always call before visiting your doctor or health facility",
            "Remember to clean your hands often", "Cover your nose or mouth with bent elbow or tissue when you cough or sneeze"};
    String[] ArrSymptoms = {"Fever", "Cough","Tiredness","Loss of taste or smell","Sore throat", "Headache","Diarrhoea",
            "Aches and Pains","Difficulty breathing","Loss of speech or mobility"};
    String myEmail;


    public HomePage(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_home_page, container, false);

        txtSymptoms = v.findViewById(R.id.txtSymptoms);
        txtQuote = v.findViewById(R.id.Tipsoftheday);



        Runnable text = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int rand = random.nextInt(ArrSymptoms.length);
                int rand2 = random.nextInt(ArrToDos.length);
                txtSymptoms.setText(ArrSymptoms[rand]);
                txtQuote.setText(ArrToDos[rand2]);
            }
        };
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(text,0, 5, TimeUnit.SECONDS);

        return v;
    }
}
