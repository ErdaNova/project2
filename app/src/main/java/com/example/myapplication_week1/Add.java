package com.example.myapplication_week1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public class Add extends Activity implements View.OnClickListener {

    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_click);

        Button okaybt=(Button)findViewById(R.id.okay1);
        okaybt.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EditText editname=(EditText)findViewById(R.id.add_name);
                EditText editnum=(EditText)findViewById(R.id.add_number);

                String uname=editname.getText().toString();
                String unum=editnum.getText().toString();

                if(uname.equals("") && unum.equals("")) {
                    Toast.makeText(getBaseContext(), "다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    int ch=0;
                    for(int i=0; i<MainActivity.tmp2.size(); i++){
                        if(unum.equals(MainActivity.tmp2.get(i))){
                            ch=1;
                            break;
                        }
                    }

                    if(ch==1){
                        Toast.makeText(getBaseContext(), "이미 존재하는 번호입니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        JSONObject obj = new JSONObject();
                        JSONArray arr = new JSONArray();
                        try {
                            for (int i = 0; i < MainActivity.list.size(); i++) {
                                JSONObject tmp = new JSONObject();
                                tmp.put("name", MainActivity.list.get(i).getName());
                                tmp.put("number", MainActivity.list.get(i).getNumber());
                                tmp.put("friendly", MainActivity.list.get(i).getFriendly());
                                arr.put(tmp);
                            }

                            JSONObject tmp = new JSONObject();
                            tmp.put("name", uname);
                            tmp.put("number", unum);
                            tmp.put("friendly", 0);
                            arr.put(tmp);
                            obj.put("Phonebook", arr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String str = obj.toString();
                        SharedPreferences.Editor edit = getSharedPreferences("contact", MODE_PRIVATE).edit();
                        edit.putString("phone", str);
                        edit.commit();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}