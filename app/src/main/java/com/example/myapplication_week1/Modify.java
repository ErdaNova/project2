package com.example.myapplication_week1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Modify extends Activity implements View.OnClickListener{

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_click);

        TextView prename=(TextView)findViewById(R.id.pre_name);
        TextView prenumber=(TextView)findViewById(R.id.pre_number);

        Intent intent=getIntent();
        final int idx=intent.getIntExtra("index",0);
        final String id = intent.getStringExtra("id");

        final String oldnum = MainActivity.list.get(idx).getNumber();
        final String friendy = MainActivity.list.get(idx).getFriendly();
        prename.setText(MainActivity.list.get(idx).getName());
        prenumber.setText(MainActivity.list.get(idx).getNumber());
        System.out.println(MainActivity.list.get(idx).getName());

        Button okaybt=(Button)findViewById(R.id.okay2);
        okaybt.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EditText editname=(EditText)findViewById(R.id.md_name);
                EditText editnum=(EditText)findViewById(R.id.md_number);

                final String uname=editname.getText().toString();
                final String unum=editnum.getText().toString();


                AlertDialog.Builder ch=new AlertDialog.Builder(Modify.this);
                ch.setTitle("MODIFY");
                ch.setMessage("해당 연락처를 수정하시겠습니까?");

                ch.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!uname.equals("")) MainActivity.list.get(idx).setName(uname);
                        if(!unum.equals("")) MainActivity.list.get(idx).setNumber(unum);

                        JSONObject obj=new JSONObject();
                        JSONArray arr=new JSONArray();
                        try {
                            for(int j=0; j<MainActivity.list.size(); j++){
                                JSONObject tmp=new JSONObject();
                                tmp.put("name",MainActivity.list.get(j).getName());
                                tmp.put("number",MainActivity.list.get(j).getNumber());
                                tmp.put("friendly", MainActivity.list.get(j).getFriendly());
                                arr.put(tmp);
                            }
                            obj.put("Phonebook", arr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
/*
                        String str=obj.toString();
                        SharedPreferences.Editor edit=getSharedPreferences("contact",MODE_PRIVATE).edit();
                        edit.putString("phone",str);
                        edit.commit();
*/
                        MainActivity mainAct = new MainActivity();
                        MainActivity.HttpUpdateAsyncTask httpTask = new MainActivity.HttpUpdateAsyncTask(mainAct);
                        httpTask.execute("http://192.249.19.243:9780/api/phonebooks/specialkey/"+id+oldnum,  uname, unum, friendy);

                        Toast.makeText(getBaseContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                        finishAffinity();
                    }
                });
                ch.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                ch.create().show();
            }
        });
    }
    @Override
    public void onClick(View view) {

    }
}
