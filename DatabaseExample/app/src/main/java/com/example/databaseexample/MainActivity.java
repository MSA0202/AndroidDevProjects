package com.example.databaseexample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {

	DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB=new DatabaseHelper(this, "app");
        setContentView(R.layout.activity_main);
    }
        
    @SuppressLint("Range")
	public void doList(View v){
    	LinearLayout l = (LinearLayout)findViewById(R.id.mainView);
		Cursor c = myDB.doQuery("SELECT name, age from students");
		while (c.moveToNext()){
			TextView t = new TextView(this);
			t.setText(c.getString(c.getColumnIndex("name"))+","+c.getLong(c.getColumnIndex("age")));
			l.addView(t);
		}
		c.close();
    }
    
    public void doInsert(View v){
		EditText et = (EditText)findViewById(R.id.edittext);
		String val = et.getText().toString();
		String[] arrofstr = val.split(" ",2);
    	myDB.doUpdate("Insert into students(name, age) values (?,?);", arrofstr);
    }

	@SuppressLint("Range")
	public void doQuery(View v){
		Cursor c = myDB.doQuery("SELECT name, age from students");
		while (c.moveToNext()){
				System.out.println(c.getString(c.getColumnIndex("name"))+","+c.getLong(c.getColumnIndex("age")));
		}
		c.close();
	}
}
