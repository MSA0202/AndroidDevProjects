package com.example.covidwatch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.io.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TrackCovid extends Fragment{
    public TrackCovid(){

    }

    String[] Locations = {"Matrix","Great Hall","TW Kambule","Solomon Mahlangu","Chamber Of Mines" };
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    TextView ChosenLocation, OutputLocation, NumInfections;
    String location;
    Button Track;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_track_covid, container, false);

        autoCompleteTxt  = v.findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(getActivity(),R.layout.list_locations,Locations);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String item  = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        Track = v.findViewById(R.id.btnSearch);
        Track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChosenLocation = v.findViewById(R.id.auto_complete_txt);
                OutputLocation = v.findViewById(R.id.txtViewTrackLocation);
                location = ChosenLocation.getText().toString();
                if(location.equals("")){
                    Toast.makeText(getActivity(),"Please select location",Toast.LENGTH_SHORT).show();
                }else{
                    String TrackLocationURL = "https://lamp.ms.wits.ac.za/~s2328024/track_location.php";

                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody =  new FormBody.Builder()
                            .add("location", location)
                            .build();

                    Request request = new Request.Builder()
                            .url(TrackLocationURL)
                            .post(formBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            // ... check for failure using `isSuccessful` before proceeding

                            // Read data on the worker thread
                            String responseData = response.body().string();

                            // Run view-related code back on the main thread
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    JSONObject jsonObject;
                                    try {
                                        jsonObject = new JSONObject(responseData);
                                        String num = jsonObject.getString("NUM");
                                        NumInfections = (TextView) v.findViewById(R.id.txtTrackNumInfections);
                                        NumInfections.setText(num);
                                        OutputLocation.setText(location);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        }
                    });
                }

            }
        });

        return v;
    }
}