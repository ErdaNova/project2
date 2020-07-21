package com.example.myapplication_week1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.provider.ContactsContract;

import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Base64;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
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

//import java.util.List;


import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import android.graphics.Color;
import android.os.Handler;
import android.util.Log;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;


import java.util.List;
import android.widget.Button;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.UiSettings;



public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    //ImageView image1, image2,image3,image4, image5, image6, image7, image8, image9, image10, image11, image12, image13, image14, image15, image16, image17, image18, image19, image20, image21;
    static GridView gridView;
    static ImageAdapter imageAdapter;
    private final int GET_GALLERY_IMAGE=200;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    Button button;


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

    private GoogleMap map;
    private MapView mapView = null;
    private UiSettings uiSettings;
    private static final String SELECTED_STYLE = "selected_style";
    Button style;
    private int count=0;
    Button marker_control;
    private int location_status=-2;
    private String name="";

    private String host = "http://192.249.19.243:9780";
    static String str = "";
    static String[] location= new String[100];
    static String[] username= new String[100];

    public CountDownTimer CDT = new CountDownTimer(500 * 10000, 10000) {
        public void onTick(long millisUntilFinished) {
            GpsTracker gpsTracker = new GpsTracker(MainActivity.this);
            double lat=gpsTracker.getLatitude();
            double longi=gpsTracker.getLongitude();
            final Geocoder g = new Geocoder(MainActivity.this);
            int length=0;

            //JSONObject my_location= new JSONObject();

            HttpUpdateAsyncTask httplocAsyncTask = new HttpUpdateAsyncTask(MainActivity.this);
            httplocAsyncTask.execute("http://192.249.19.243:9780/api/phonebooks/name/"+name ,name, Double.toString(lat) +","+ Double.toString(longi),Integer.toString(location_status));



            List<Address> addresses = null;
            map.clear();

            HttpGetAsyncTask3 getloc = new HttpGetAsyncTask3(MainActivity.this);
            getloc.execute("http://192.249.19.243:9780/api/phonebooks");

            for (int i=0; i<username.length; i++) {
                if (username[i] == null) {
                    length = i;
                    break;
                }
            }


            for (int x = 0; x<length; x++) {

                String[] eachloc = location[x].split(",");
                String latitude = eachloc[0];
                String longitude = eachloc[1];


                try {
                    List<Address> resultList = g.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 10);
                    MarkerOptions mOptions = new MarkerOptions();
                    mOptions.title(username[x]);
                    mOptions.snippet(resultList.get(0).getAddressLine(0));
                    mOptions.position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
                    map.addMarker(mOptions);
                } catch (IOException e) {

                }
            }








        }
        public void onFinish() {
            map.clear();
            CDT.start();

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        System.out.println(id);
        setContentView(R.layout.activity_main);




        final TabHost host=(TabHost)findViewById(R.id.host);
        host.setup();


        TabHost.TabSpec spec = host.newTabSpec("tab1");
        spec.setIndicator("CONTACT");
        spec.setContent(R.id.tab_content1);
        host.addTab(spec);

        HttpGetAsyncTask httpTask = new HttpGetAsyncTask(MainActivity.this);
        httpTask.execute("http://192.249.19.243:9780/api/phonebooks/idnum/"+id);


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
                        del.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
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




        HttpGetAsyncTask2 httpTask2 = new HttpGetAsyncTask2(MainActivity.this);
        httpTask2.execute("http://192.249.19.243:9780/api/phonebooks/idnum/"+id);



        spec = host.newTabSpec("tab3");
        spec.setIndicator("OBSERVER");
        spec.setContent(R.id.tab_content3);
        host.addTab(spec);

        GpsTracker gpsTracker = new GpsTracker(MainActivity.this);
        double lat=gpsTracker.getLatitude();
        double longi=gpsTracker.getLongitude();
        final Button okaybt=(Button) findViewById(R.id.okaybt);
        style=(Button) findViewById(R.id.button2);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        okaybt.setOnClickListener(new View.OnClickListener() {

                                      public void onClick(View view) {
                                          EditText editname = (EditText) findViewById(R.id.naming);
                                          name = editname.getText().toString();
                                          if(name.length() == 0){
                                              name="Noname"+id.substring(0,3);
                                          }

                                      }

                                  });



        style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=count+1;
                if (count%2==1) {
                    if(name.length() == 0){
                        name="Noname"+id.substring(0,3);
                    }
                    location_status=-3;
                    CDT.start();
                    okaybt.setEnabled(false);

                }
                else{
                    location_status=-2;

                    CDT.cancel();
//                    HttpUpdateAsyncTask httplocAsyncTask = new HttpUpdateAsyncTask(MainActivity.this);
//                    httplocAsyncTask.execute("http://192.249.19.243:9780/api/phonebooks/name/"+name ,name, "",Integer.toString(location_status));
                    HttpDelAsyncTask httpdel = new HttpDelAsyncTask(MainActivity.this);
                    httpdel.execute("http://192.249.19.243:9780/api/phonebooks/name/"+name);
                    okaybt.setEnabled(true);
                    map.clear();
                }
            }
        });



    }

    public static String GET(String url) {
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";


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
                            item.setBackground(new ColorDrawable(Color.BLACK));
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
                            deleteItem.setBackground(new ColorDrawable(Color.WHITE));
                            // set item width
                            deleteItem.setWidth(200);
                            // set item title
                            deleteItem.setTitle("DELETE");
                            // set item title fontsize
                            deleteItem.setTitleSize(15);
                            // set item title font color
                            deleteItem.setTitleColor(Color.BLACK);
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

    class HttpGetAsyncTask3 extends AsyncTask<String, Void, String> {

        private MainActivity mainAct;
        HttpGetAsyncTask3(MainActivity mainActivity) {this.mainAct = mainActivity;}
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            location= new String[100];
            username= new String[100];
            str = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //mainAct.tvResponse.setText(strJson);
                    try{
//            JSONObject jobj=new JSONObject(json);
//            JSONArray jarray=jobj.getJSONArray("Phonebook");
                        JSONArray jarray = new JSONArray(str);

                        int personnum=0;
                        for(int i=0; i<jarray.length(); i++) {
                            JSONObject pobj = jarray.getJSONObject(i);
                            if(Integer.parseInt(pobj.getString("friendy"))==-3) {
                                MainActivity.username[personnum] = pobj.getString("name");
                                MainActivity.location[personnum] = pobj.getString("num");
                                personnum+=1;
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

            intent.putExtra("id",MainActivity.id);
            intent.putExtra("ct",Integer.toString(ct));
            intent.putExtra("image", selectedImageUri.toString());
            startActivity(intent);

        }
    }




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

            JSONArray jarray = new JSONArray(json);
            for(int i=0; i<jarray.length(); i++) {
                JSONObject pobj = jarray.getJSONObject(i);

                if (Integer.parseInt(pobj.getString("friendy")) >=0) {
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

    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        // [END maps_check_location_permission]
    }






    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        GpsTracker gpsTracker = new GpsTracker(MainActivity.this);
        double lat=gpsTracker.getLatitude();
        double longi=gpsTracker.getLongitude();
        final Geocoder g = new Geocoder(this);

        Handler mHandler = new Handler();

        List<Address> addresses = null;
        try{
            googleMap.clear();
            List<Address> resultList = g.getFromLocation(lat,longi,10);
            MarkerOptions mOptions = new MarkerOptions();

            mOptions.title("위치");
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, longi)));

            mOptions.position(new LatLng(lat,longi));



        } catch (IOException e) {

        }
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        uiSettings = map.getUiSettings();
        map.setOnMyLocationClickListener(this);
        enableMyLocation();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);




    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {



    }




}