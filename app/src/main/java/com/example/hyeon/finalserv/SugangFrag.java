package com.example.hyeon.finalserv;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SugangFrag extends Fragment {

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "num";
    private static final String TAG_NAME = "name";
    private static final String TAG_PROF = "prof";

    JSONArray sugangs = null;

    private ProgressDialog dialog;
    private Button comDBbtn;
    private Button gyoDBbtn;
    private Spinner spinner;
    private GetSyInfo getSyInfo;
    private ListView listView;
    private ArrayList<HashMap<String, String>> sugangList;
    ListAdapter adapter;

    public SugangFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sugang, container, false);
        getSyInfo = new GetSyInfo();
        dialog = new ProgressDialog(this.getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        comDBbtn = (Button) view.findViewById(R.id.sugang_comDB_update_btn);
        comDBbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("컴퓨터학부 강의목록 업데이트");
                dialog.setMessage("DB정보 업데이트 중..");
                dialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //시간이 걸리는 처리
                        getSyInfo.loadSysInfo_COM();
                        confirmHandler.sendEmptyMessage(0);
                        dialog.dismiss();
                    }
                }).start();
            }
        });

        gyoDBbtn = (Button) view.findViewById(R.id.sugang_gyoDB_update_btn);
        gyoDBbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("일반교양 강의목록 업데이트");
                dialog.setMessage("DB정보 업데이트 중..");
                dialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getSyInfo.loadSysInfo_GYO();
                        confirmHandler.sendEmptyMessage(0);
                        dialog.dismiss();
                    }
                }).start();
            }
        });


        listView = (ListView) view.findViewById(R.id.listView);
        sugangList = new ArrayList<HashMap<String, String>>();

        String[] spinOption = getResources().getStringArray(R.array.sugang_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_dropdown_item_1line, spinOption);
        spinner = (Spinner) view.findViewById(R.id.sugang_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        myJSON = null;
                        sugangs = null;

                        sugangList.clear();
                        ((ArrayAdapter)adapter).notifyDataSetChanged();

                        if (position == 1) //컴학정보면
                            getData("http://192.168.1.160/get_COM_sugang.php");
                        else if (position == 2) //교양정보면
                            getData("http://192.168.1.160/get_GYO_sugang.php");


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );


        return view;
    }

    private Handler confirmHandler = new Handler() {
        //완료 후 실행할 처리
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getContext(), "DB 업데이트 완료", Toast.LENGTH_SHORT).show();
        }
    };

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            sugangs = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < sugangs.length(); i++) {
                JSONObject c = sugangs.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String prof = c.getString(TAG_PROF);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_ID, id);
                persons.put(TAG_NAME, name);
                persons.put(TAG_PROF, prof);

                sugangList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    this.getContext(), sugangList, R.layout.sugang_list,
                    new String[]{TAG_ID, TAG_NAME, TAG_PROF},
                    new int[]{R.id.list_num, R.id.list_name, R.id.list_prof}
            );

            listView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*    public void getData(String url){
            class GetDataJSON extends AsyncTask<String, Void, String> {

                @Override
                protected String doInBackground(String... params) {

                    String uri = params[0];

                    BufferedReader bufferedReader = null;
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        StringBuilder sb = new StringBuilder();

                        bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        String json;
                        while((json = bufferedReader.readLine())!= null){
                            sb.append(json+"\n");
                        }

                        return sb.toString().trim();

                    }catch(Exception e){
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(String result){
                    myJSON=result;
                    showList();
                }
            }
            GetDataJSON g = new GetDataJSON();
            g.execute(url);
            AsyncTask.cancle();
        }*/
    public void getData(String urll) {

        String result = null;
        String uri = urll;

        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String json;
            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json + "\n");
            }

            result = sb.toString().trim();

        } catch (Exception e) {

        }

        myJSON = result;
        showList();

    }
}
