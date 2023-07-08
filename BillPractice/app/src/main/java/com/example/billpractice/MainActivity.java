package com.example.billpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void doOption(View v){
        EditText lightsuse = (EditText)findViewById(R.id.lu);
        String luu = lightsuse.getText().toString();

        EditText lightshours = (EditText)findViewById(R.id.hu);
        String lhh = lightshours.getText().toString();

        EditText lightsuse2 = (EditText)findViewById(R.id.lu2);
        String luu2 = lightsuse2.getText().toString();

        EditText lightshours2 = (EditText)findViewById(R.id.hu2);
        String lhh2 = lightshours2.getText().toString();


        TextView energy = (TextView)findViewById(R.id.Energyout);
        TextView original = (TextView)findViewById(R.id.Originalout);

        double en = EnergyLights(luu,lhh)+EnergyAircon(luu2,lhh2);
        double ori = OriginalLights(luu,lhh)+OriginalAircon(luu2,lhh2);

        energy.setText("The energy saving option will cost: "+en);
        original.setText("The original option will cost: "+ori);
    }

    public double EnergyLights(String many, String hours){
        double lps = 0.1;
        double replacementlps = 30.0;

        int days = 250;

        double manyi = Double.parseDouble(many);
        double hoursi = Double.parseDouble(hours);

        double firstSum = manyi*hoursi*lps*days;
        double secondSum = manyi*replacementlps;
        return firstSum+secondSum;
    }

    public double EnergyAircon(String many, String hours){

        double aircon = 1.0;
        double replacementaircon = 2000.0;
        int days = 250;

        double manyi = Double.parseDouble(many);
        double hoursi = Double.parseDouble(hours);

        double firstSum = manyi*hoursi*aircon*days;
        double secondSum = manyi*replacementaircon;
        return firstSum+secondSum;
    }

    public double OriginalLights(String many, String hours){
        double lps = 0.5;
        double replacementlps = 10.0;

        int days = 250;

        double manyi = Double.parseDouble(many);
        double hoursi = Double.parseDouble(hours);

        double firstSum = manyi*hoursi*lps*days;
        double secondSum = manyi*replacementlps;
        return firstSum+secondSum;
    }


    public double OriginalAircon(String many, String hours){
        double aircon = 1.2;
        double replacementaircon = 1500.0;
        int days = 250;

        double manyi = Double.parseDouble(many);
        double hoursi = Double.parseDouble(hours);

        double firstSum = manyi*hoursi*aircon*days;
        double secondSum = manyi*replacementaircon;
        return firstSum+secondSum;
    }


}