package com.example.covidwatch;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CovidCheck extends Fragment {


    String[] Locations = {"Matrix","Great Hall","TW Kambule","Solomon Mahlangu","Chamber Of Mines" };
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    EditText dp1;
    Calendar calendar;

    String location; String datee; Button bCheck;

    String url = "https://lamp.ms.wits.ac.za/~s2328024/CovidCheck.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_covid_check, container, false);

        autoCompleteTxt  = v.findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(getActivity(),R.layout.list_locations,Locations);
        autoCompleteTxt.setAdapter(adapterItems);

        TextView tx = v.findViewById(R.id.txtViewRiskLevel);

        dp1 = v.findViewById(R.id.DatePicker);
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }
            private void updateCalendar(){
                String Format = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);

                dp1.setText(sdf.format(calendar.getTime()));



            }
        };
        dp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog x = new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                x.show();

            }
        });
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String item  = parent.getItemAtPosition(position).toString();

                //location = item;
                //if(location.equals("")){location="";}
                Toast.makeText(getActivity(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }

        });

        //button
        bCheck = v.findViewById(R.id.btnRiskCheck);
        bCheck.setOnClickListener(new View.OnClickListener() { //b1 displays
            @Override
            public void onClick(View v) {

                datee = dp1.getText().toString();
                location = autoCompleteTxt.getText().toString();
                OkHttpClient client = new OkHttpClient();


                RequestBody formBody = new FormBody.Builder()

                        .add("location", location)
                        .add("datee", datee)

                        .build();

                Request request = new Request.Builder()
                        .url(url)
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
                            String[] spl = myResponse.split("%");


                            CovidCheck.this.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if(myResponse.equals("NULL")){
                                        tx.setText("Nothing recorded at the moment");

                                    }
                                    else if(myResponse.equals("N")){
                                        tx.setText("Not at Risk");


                                    }
                                    else if(myResponse.equals("Y")){
                                        tx.setText("At Risk");

                                    }
                                    else{
                                        Toast.makeText(CovidCheck.this.getActivity(), myResponse, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });



        return v;
    }
}