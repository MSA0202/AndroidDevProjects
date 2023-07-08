package com.example.databaseexample2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {

	com.example.databaseexample2.DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB=new com.example.databaseexample2.DatabaseHelper(this, "app");
        setContentView(R.layout.activity_main);
    }
        
    @SuppressLint({"SetTextI18n", "Range"})
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
		EditText ins = (EditText)findViewById(R.id.listhere);
		String sins = ins.getText().toString();
    	String[] vals = sins.split(" ");
    	myDB.doUpdate("Insert into students(name, age) values (?,?);", vals);
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
