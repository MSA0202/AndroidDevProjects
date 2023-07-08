package com.example.covidwatch;

import static android.content.Intent.getIntent;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

public class CheckIn extends Fragment {
    public CheckIn() {

    }
    private String myEmail;
    String[] Locations = {"Matrix", "Great Hall", "TW Kambule", "Solomon Mahlangu", "Chamber Of Mines"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    Button check;
    EditText dp1;
    Calendar calendar;
    EditText in;
    EditText email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_check_in, container, false);
        autoCompleteTxt = v.findViewById(R.id.auto_complete_txt);
        dp1 = v.findViewById(R.id.DatePicker);
        adapterItems = new ArrayAdapter<String>(getActivity(), R.layout.list_locations, Locations);
        autoCompleteTxt.setAdapter(adapterItems);
        in = v.findViewById(R.id.editTxtCovidStatus);
        check = v.findViewById(R.id.btnCheckInAdd);
        email=v.findViewById(R.id.txtEmail);
        String insert = "https://lamp.ms.wits.ac.za/~s2328024/VisitInsert.php";

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity activity =(HomeActivity) getActivity();
                myEmail = activity.email.toString();

                String infected = in.getText().toString();
                String location = autoCompleteTxt.getText().toString();
                String date = dp1.getText().toString();
                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .add("email", myEmail)
                        .add("location", location)
                        .add("date", date)
                        .add("infected", infected)
                        .build();

                Request request = new Request.Builder()
                        .url(insert)
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

                            CheckIn.this.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CheckIn.this.getActivity(), myResponse, Toast.LENGTH_SHORT).show();
                                    //tx.setText(myResponse);
                                }
                            });
                        }
                    }
                });
            }
        });
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }

            private void updateCalendar() {
                String Format = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);

                dp1.setText(sdf.format(calendar.getTime()));
            }
        };
        dp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });


        return v;

    }


}