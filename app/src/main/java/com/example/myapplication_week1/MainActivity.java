package com.example.myapplication_week1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.provider.ContactsContract;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Base64;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Button;
import android.view.ViewGroup;

//import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.*;
//import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.os.Message;
import android.os.Handler;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.logging.LogRecord;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //ImageView image1, image2,image3,image4, image5, image6, image7, image8, image9, image10, image11, image12, image13, image14, image15, image16, image17, image18, image19, image20, image21;
    static GridView gridView;
    static ImageAdapter imageAdapter;
    private final int GET_GALLERY_IMAGE=200;


    Button button, tab3_btn, tab3_1, tab3_2, tab3_3, tab3_4, tab3_5, name_btn ;
    Button tab3_1_dst, tab3_2_dst, tab3_3_dst, tab3_4_dst, tab3_5_dst;
    Button result_btn;
    LinearLayout ladder, names, dsts;
    //ImageView image;

    //CONTACT
    PbAdapter adapter=null;
    static ArrayList<Phonebook> list = new ArrayList<Phonebook>();
    ArrayList<Phonebook> tmp1=new ArrayList<Phonebook>();
    static ArrayList<String> tmp2=new ArrayList<String>();
    int total=0;
    static SwipeMenuListView listView;
    //Handler mdHandler, mlHandler, mrHandler;
    Handler mHandler;
    int ct=0;
    final int[][] path=new int[7][5];
    private static String id;

    private String host = "http://192.249.19.243:9780";
    static String str = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        System.out.println(id);
        setContentView(R.layout.activity_main);
        TextView t = (TextView)findViewById(R.id.id);
        t.setText(id);

        final TabHost host=(TabHost)findViewById(R.id.host);
        host.setup();


        TabHost.TabSpec spec = host.newTabSpec("tab1");
        spec.setIndicator("CONTACT");
        spec.setContent(R.id.tab_content1);
        host.addTab(spec);

        HttpGetAsyncTask httpTask = new HttpGetAsyncTask(MainActivity.this);
        httpTask.execute("http://192.249.19.243:9780/api/phonebooks/idnum/"+id);

//        if(str!=null) jsonParsing(str);
//        Collections.sort(list);

        //LISTVIEW
//        final ListView listview = (ListView)findViewById(R.id.pb_listview);
//
//        adapter = new PbAdapter(this,R.layout.pb_item, list);
//        adapter.notifyDataSetChanged();
//        listView.setAdapter(adapter);

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView adapterView, View view, int position, long id) {
//                Intent intent=new Intent(getApplicationContext(), Next.class);
//
//                intent.putExtra("name", list.get(position).getName());
//                intent.putExtra("number", list.get(position).getNumber());
//                list.get(position).setFriendly(list.get(position).getFriendly()+1);
//                intent.putExtra("friendly", list.get(position).getFriendly());
//                total++;
//                intent.putExtra("total",total);
//                intent.putExtra("index", position);
//                startActivity(intent);
//            }
//        });

        //SWIPE



        SwipeMenuCreator creator=new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem item=new SwipeMenuItem(getApplicationContext());
                item.setBackground(new ColorDrawable(Color.rgb(0xFF,0x71,0x71)));
                item.setWidth(200);
                // set item title
                item.setTitle("MODIFY");
                // set item title fontsize
                item.setTitleSize(15);
                // set item title font color
                item.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(item);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF,
                        0xFF, 0xFF)));
                // set item width
                deleteItem.setWidth(200);
                // set item title
                deleteItem.setTitle("DELETE");
                // set item title fontsize
                deleteItem.setTitleSize(15);
                // set item title font color
                deleteItem.setTitleColor(Color.rgb(0xFF,0x71,0x71));
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };




        listView=findViewById(R.id.pb_listview);
        adapter = new PbAdapter(this,R.layout.pb_item, list);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setMenuCreator(creator);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long id) {
                System.out.println(list);
                list.clear();
                list.addAll(tmp1);
                Intent intent=new Intent(getApplicationContext(), Next.class);
                Log.d("indexNumber",Integer.toString(position));
                System.out.println(list);
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("number", list.get(position).getNumber());
                list.get(position).setFriendly(Integer.toString(Integer.parseInt(list.get(position).getFriendly())+1));
                intent.putExtra("friendly", list.get(position).getFriendly());
                total++;
                HttpUpdateAsyncTask httpTask = new HttpUpdateAsyncTask(MainActivity.this);
                httpTask.execute("http://192.249.19.243:9780/api/phonebooks/specialkey/"+MainActivity.id+list.get(position).getNumber(),  list.get(position).getName(), list.get(position).getNumber(), list.get(position).getFriendly());
                intent.putExtra("total",total);
                intent.putExtra("index", position);
                intent.putExtra("id",MainActivity.id);
                startActivity(intent);
            }
        });

        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                listView.smoothOpenMenu(position);
            }

            @Override
            public void onSwipeEnd(int position) {
                listView.smoothOpenMenu(position);
            }
        });
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        Intent intent=new Intent(getApplicationContext(), Modify.class);
                        intent.putExtra("index", position);
                        intent.putExtra("id",id);
                        startActivity(intent);
                        break;
                    case 1:
                        AlertDialog.Builder del=new AlertDialog.Builder(MainActivity.this);
                        del.setTitle("DELETE");
                        del.setMessage("해당 연락처를 영구적으로 삭제하시겠습니까?");

                        del.setNegativeButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String delNum = list.get(position).getNumber();
                                list.remove(position);
                                tmp1.clear();
                                tmp1.addAll(list);

                                JSONObject obj = new JSONObject();
                                JSONArray arr = new JSONArray();
                                try {
                                    for (int j = 0; j < MainActivity.list.size(); j++) {
                                        JSONObject tmp = new JSONObject();
                                        tmp.put("name", MainActivity.list.get(j).getName());
                                        tmp.put("number", MainActivity.list.get(j).getNumber());
                                        tmp.put("friendly", MainActivity.list.get(j).getFriendly());
                                        arr.put(tmp);
                                    }
                                    obj.put("Phonebook", arr);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                String str = obj.toString();
                                HttpDelAsyncTask httpTask = new HttpDelAsyncTask(MainActivity.this);
                                httpTask.execute("http://192.249.19.243:9780/api/phonebooks/specialkey/"+id+delNum);
                                /*
                                SharedPreferences.Editor edit = getSharedPreferences("contact", MODE_PRIVATE).edit();
                                edit.putString("phone", str);
                                edit.commit();
                                 */
                                Toast.makeText(getBaseContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                adapter.notifyDataSetChanged();
                            }
                        });
                        del.setPositiveButton("아니오 뚱인데요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        del.create().show();
                        break;
                }
                return false;
            }
        });
        //

        //SEARCH
        EditText editText=(EditText)findViewById(R.id.serach);
        tmp1.addAll(list);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable edit) {
                String text = edit.toString();
                search(text);
            }
        });
        //

        //BUTTON-ADD
        final Button addbt=(Button)findViewById(R.id.add);
        addbt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                list.clear();
                list.addAll(tmp1);
                Intent intent=new Intent(getApplicationContext(), Add.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        spec = host.newTabSpec("tab2");
        spec.setIndicator("GALLERY");
        spec.setContent(R.id.tab_content2);
        host.addTab(spec);
        final int[] viewnum = new int[21];
        final int[] mostviewNum = {0};
        final int[] secondNum = {0};
        final int[] thirdNum = {0};
        final int[] mostviewImg = {0};
        final int[] secondImg = {0};
        final int[] thirdImg = {0};
//        final ImageView mostViewd = (ImageView)findViewById(R.id.mostView);
//        final ImageView second = (ImageView)findViewById(R.id.second);
//        final ImageView third = (ImageView)findViewById(R.id.third);
//        final SharedPreferences sp2 = getSharedPreferences("gallery",MODE_PRIVATE);


        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });



//        imageAdapter.addItem(new ImageItem(R.drawable.image1));
//        imageAdapter.addItem(new ImageItem(R.drawable.image2));
//        imageAdapter.addItem(new ImageItem(R.drawable.image3));
//        imageAdapter.addItem(new ImageItem(R.drawable.image4));
//        imageAdapter.addItem(new ImageItem(R.drawable.image5));
//        imageAdapter.addItem(new ImageItem(R.drawable.image6));
//        imageAdapter.addItem(new ImageItem(R.drawable.image7));
//        imageAdapter.addItem(new ImageItem(R.drawable.image8));
//        imageAdapter.addItem(new ImageItem(R.drawable.image9));
//        imageAdapter.addItem(new ImageItem(R.drawable.image10));
//        imageAdapter.addItem(new ImageItem(R.drawable.image11));
//        imageAdapter.addItem(new ImageItem(R.drawable.image12));
//        imageAdapter.addItem(new ImageItem(R.drawable.image13));
//        imageAdapter.addItem(new ImageItem(R.drawable.image14));
//        imageAdapter.addItem(new ImageItem(R.drawable.image15));
//        imageAdapter.addItem(new ImageItem(R.drawable.image16));
//        imageAdapter.addItem(new ImageItem(R.drawable.image17));
//        imageAdapter.addItem(new ImageItem(R.drawable.image18));
//        imageAdapter.addItem(new ImageItem(R.drawable.image19));
//        imageAdapter.addItem(new ImageItem(R.drawable.image20));
    //        imageAdapter.addItem(new ImageItem(R.drawable.image21));
    //        gridView.setAdapter(imageAdapter);
//        gridView.setAdapter(imageAdapter);
        HttpGetAsyncTask2 httpTask2 = new HttpGetAsyncTask2(MainActivity.this);
        httpTask2.execute("http://192.249.19.243:9780/api/phonebooks/idnum/"+id);

//        mostviewImg[0]=sp2.getInt("most",0);
//        secondImg[0]=sp2.getInt("second",0);
//        thirdImg[0]=sp2.getInt("third",0);
//        if(mostviewImg[0]!=0) {
//            int k = getResources().getIdentifier("image" + mostviewImg[0], "drawable", getPackageName());
//            mostViewd.setImageResource(k);
//        }
//        if(secondImg[0]!=0) {
//            int k = getResources().getIdentifier("image" + secondImg[0], "drawable", getPackageName());
//            second.setImageResource(k);
//        }
//        if(thirdImg[0]!=0) {
//            int k = getResources().getIdentifier("image" + thirdImg[0], "drawable", getPackageName());
//            third.setImageResource(k);
//        }
//        final SharedPreferences.Editor editor = sp2.edit();
//
//
//        Button reset = (Button)findViewById(R.id.reset);
//        reset.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                for(int a=0; a<21; a++){
//                    editor.putInt(Integer.toString(a),0);
//                }
//                mostviewImg[0] = 0;
//                secondImg[0] = 0;
//                thirdImg[0] = 0;
//                editor.putInt("most",mostviewImg[0]);
//                editor.putInt("second",secondImg[0]);
//                editor.putInt("third",thirdImg[0]);
//                editor.commit();
//                mostViewd.setImageResource(0);
//                second.setImageResource(0);
//                third.setImageResource(0);
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        mostViewd.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                int k = getResources().getIdentifier("image" + mostviewImg[0], "drawable", getPackageName());
//                Intent intent = new Intent(getApplicationContext(), ImageClicked.class);
//                intent.putExtra("image",Integer.toString(k));
//                startActivity(intent);
//            }
//        });
//        second.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                int k = getResources().getIdentifier("image" + secondImg[0], "drawable", getPackageName());
//                Intent intent = new Intent(getApplicationContext(), ImageClicked.class);
//                intent.putExtra("image",Integer.toString(k));
//                startActivity(intent);
//            }
//        });
//
//        third.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                int k = getResources().getIdentifier("image" + thirdImg[0], "drawable", getPackageName());
//                Intent intent = new Intent(getApplicationContext(), ImageClicked.class);
//                intent.putExtra("image",Integer.toString(k));
//                startActivity(intent);
//            }
//        });

        spec = host.newTabSpec("tab3");
        spec.setIndicator("LADDER");
        spec.setContent(R.id.tab_content3);
        host.addTab(spec);



    }

    public static String GET(String url) {
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";

            // build jsonObject
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.accumulate("name", phonenum.getName());
//            jsonObject.accumulate("num", phonenum.getNum());
//            jsonObject.accumulate("mail", phonenum.getMail());

            // convert JSONObject to JSON to String
//            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // Set some headers to inform server about the type of the content
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            httpCon.setRequestMethod("GET");
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            // receive response as inputStream
            try {
                is = httpCon.getInputStream();
                // convert inputstream to string
                if(is != null)
                    result = convertInputStreamToString(is);
                else
                    result = "Did not work!";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                httpCon.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }


    public static String DEL(String url) {
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";

            // build jsonObject
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.accumulate("name", phonenum.getName());
//            jsonObject.accumulate("num", phonenum.getNum());
//            jsonObject.accumulate("mail", phonenum.getMail());

            // convert JSONObject to JSON to String
//            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // Set some headers to inform server about the type of the content
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            httpCon.setRequestMethod("DELETE");

            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            // receive response as inputStream
            try {
                is = httpCon.getInputStream();
                // convert inputstream to string
                if(is != null)
                    result = convertInputStreamToString(is);
                else
                    result = "Did not work!";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                httpCon.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    public static String UPDATE(String url, Phonebook phonenum){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("idnum", id);
            jsonObject.accumulate("name", phonenum.getName());
            jsonObject.accumulate("num", phonenum.getNumber());
            jsonObject.accumulate("friendy", phonenum.getFriendly());
            jsonObject.accumulate("specialkey",id+phonenum.getNumber());

            // convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // Set some headers to inform server about the type of the content
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("PUT");

            OutputStream os = httpCon.getOutputStream();
            os.write(json.getBytes("utf-8"));
            os.flush();
            // receive response as inputStream
            try {
                is = httpCon.getInputStream();
                // convert inputstream to string
                if(is != null)
                    result = convertInputStreamToString(is);
                else
                    result = "Did not work!";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                httpCon.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    public static String POST(String url, Phonebook phonenum){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("idnum", id);
            jsonObject.accumulate("name", phonenum.getName());
            jsonObject.accumulate("num", phonenum.getNumber());
            jsonObject.accumulate("friendy", phonenum.getFriendly());

            jsonObject.accumulate("specialkey", id + phonenum.getNumber());

            // convert JSONObject to JSON to String
            json = jsonObject.toString();
            System.out.println(json);

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // Set some headers to inform server about the type of the content
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            OutputStream os = httpCon.getOutputStream();
            os.write(json.getBytes("utf-8"));
            os.flush();
            // receive response as inputStream
            try {
                is = httpCon.getInputStream();
                // convert inputstream to string
                if(is != null)
                    result = convertInputStreamToString(is);
                else
                    result = "Did not work!";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                httpCon.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private class HttpGetAsyncTask extends AsyncTask<String, Void, String> {

        private MainActivity mainAct;
        HttpGetAsyncTask(MainActivity mainActivity) {this.mainAct = mainActivity;}
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            str = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //mainAct.tvResponse.setText(strJson);
                    if(str!=null) jsonParsing(str);
                    Collections.sort(list);
                    SwipeMenuCreator creator=new SwipeMenuCreator() {
                        @Override
                        public void create(SwipeMenu menu) {

                            SwipeMenuItem item=new SwipeMenuItem(getApplicationContext());
                            item.setBackground(new ColorDrawable(Color.rgb(0xFF,0x71,0x71)));
                            item.setWidth(200);
                            // set item title
                            item.setTitle("MODIFY");
                            // set item title fontsize
                            item.setTitleSize(15);
                            // set item title font color
                            item.setTitleColor(Color.WHITE);
                            // add to menu
                            menu.addMenuItem(item);

                            // create "delete" item
                            SwipeMenuItem deleteItem = new SwipeMenuItem(
                                    getApplicationContext());
                            // set item background
                            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF,
                                    0xFF, 0xFF)));
                            // set item width
                            deleteItem.setWidth(200);
                            // set item title
                            deleteItem.setTitle("DELETE");
                            // set item title fontsize
                            deleteItem.setTitleSize(15);
                            // set item title font color
                            deleteItem.setTitleColor(Color.rgb(0xFF,0x71,0x71));
                            // add to menu
                            menu.addMenuItem(deleteItem);
                        }
                    };

                    listView=findViewById(R.id.pb_listview);
                    adapter = new PbAdapter(mainAct,R.layout.pb_item, list);
                    adapter.notifyDataSetChanged();
                    MainActivity.listView.setAdapter(adapter);
                    MainActivity.listView.setMenuCreator(creator);
                    tmp1.addAll(list);
                }
            });

        }
    }

    class HttpGetAsyncTask2 extends AsyncTask<String, Void, String> {

        private MainActivity mainAct;
        HttpGetAsyncTask2(MainActivity mainActivity) {this.mainAct = mainActivity;}
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            str = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //mainAct.tvResponse.setText(strJson);
                    try{
//            JSONObject jobj=new JSONObject(json);
//            JSONArray jarray=jobj.getJSONArray("Phonebook");
                        JSONArray jarray = new JSONArray(str);
                        System.out.println(str);
                        gridView = (GridView)findViewById(R.id.gridView);
                        imageAdapter = new ImageAdapter();
                        int imgnum=0;
                        for(int i=0; i<jarray.length(); i++) {
                            JSONObject pobj = jarray.getJSONObject(i);
                            if(Integer.parseInt(pobj.getString("friendy"))==-1) {
                                Bitmap newimg = StringToBitMap(pobj.getString("num"));
                                //Bitmap resized = Bitmap.createScaledBitmap(newimg, 300, 300, true);
                                MainActivity.HttpUpdateAsyncTask httpTask2 = new MainActivity.HttpUpdateAsyncTask(mainAct);
                                httpTask2.execute("http://192.249.19.243:9780/api/phonebooks/name/"+pobj.getString("name"), "image"+Integer.toString((imgnum))+MainActivity.id, BitMapToString(newimg), "-1");
                                imgnum+=1;
                                imageAdapter.addItem(new ImageItem(newimg));
                                gridView.setAdapter(imageAdapter);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(getApplicationContext(), ImageClicked.class);
                            intent.putExtra("id",MainActivity.id);
                            intent.putExtra("ct",Integer.toString(i));
                            intent.putExtra("image", BitMapToString((imageAdapter.getItem(i).getImage())));
                            startActivity(intent);

                        }
                    });
                }
            });

        }
    }

    static class HttpDelAsyncTask extends AsyncTask<String, Void, String> {

        private MainActivity mainAct;
        HttpDelAsyncTask(MainActivity mainActivity) {this.mainAct = mainActivity;}
        @Override
        protected String doInBackground(String... urls) {

            return DEL(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            str = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //mainAct.tvResponse.setText(strJson);

                }
            });

        }
    }

    static class HttpUpdateAsyncTask extends AsyncTask<String, Void, String> {

        private MainActivity mainAct;
        HttpUpdateAsyncTask(MainActivity mainActivity) {this.mainAct = mainActivity;}
        @Override
        protected String doInBackground(String... urls) {
            Phonebook phonenum = new Phonebook();
            phonenum.setName(urls[1]);
            phonenum.setNumber(urls[2]);
            phonenum.setFriendly(urls[3]);
            return UPDATE(urls[0], phonenum);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            str = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });

        }
    }

    public static class HttpAsyncTask extends AsyncTask<String, Void, String> {

        private   MainActivity mainAct;

        HttpAsyncTask(MainActivity mainActivity) {
            this.mainAct = mainActivity;
        }
        @Override
        protected String doInBackground(String... urls) {

            Phonebook phonenum = new Phonebook();
            phonenum.setName(urls[1]);
            phonenum.setNumber(urls[2]);
            phonenum.setFriendly(urls[3]);

            return POST(urls[0],phonenum);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            str = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Intent intent = new Intent(getApplicationContext(), ImageGet.class);
            Uri selectedImageUri = data.getData();
            String imagePath = selectedImageUri.getPath();
            ct = MainActivity.imageAdapter.getCount();
            //String imagePath = getRealPathFromURI(selectedImageUri);
            /*String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bm = BitmapFactory.decodeFile(picturePath);
            */
            intent.putExtra("id",MainActivity.id);
            intent.putExtra("ct",Integer.toString(ct));
            intent.putExtra("image", selectedImageUri.toString());
            startActivity(intent);

        }
    }


    /*
    public String getBase64String(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }

     */

/*
    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }
 */

    class ImageAdapter extends BaseAdapter{
        ArrayList<ImageItem> items = new ArrayList<ImageItem>();

        @Override
        public int getCount(){
            return items.size();
        }
        public void addItem(ImageItem imageItem){
            items.add(imageItem);
        }
        @Override
        public ImageItem getItem(int i){
            return items.get(i);
        }
        @Override
        public long getItemId(int i){
            return i;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup){
            ImageViewer imageViewer = new ImageViewer(getApplicationContext());
            imageViewer.setImage(items.get(i));
            return imageViewer;
        }

    }


    private void jsonParsing(String json){
        list.clear();
        try{
//            JSONObject jobj=new JSONObject(json);
//            JSONArray jarray=jobj.getJSONArray("Phonebook");
            JSONArray jarray = new JSONArray(json);
            for(int i=0; i<jarray.length(); i++) {
                JSONObject pobj = jarray.getJSONObject(i);

                if (Integer.parseInt(pobj.getString("friendy")) != -1) {
                    Phonebook pb = new Phonebook();
                    pb.setName(pobj.getString("name"));
                    pb.setNumber(pobj.getString("num"));
                    pb.setFriendly(pobj.getString("friendy"));

                    total += Integer.parseInt(pobj.getString("friendy"));
                    list.add(pb);

                    tmp2.add(pb.getNumber());
                }



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String BitMapToString(Bitmap bitmap){

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);

        return temp;

    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(tmp1);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < tmp1.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (tmp1.get(i).getName().toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(tmp1.get(i));
                }
                if (tmp1.get(i).getNumber().toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(tmp1.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    @Override
    public void onClick(View view) {

    }
    public int dpToPx(int sizeInDP){
        int pxVal = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDP, getResources().getDisplayMetrics());
        return pxVal;
    }




}