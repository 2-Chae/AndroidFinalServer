package com.example.hyeon.finalserv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    private ProgressDialog dialog;
    private Button loginBtn;
    private EditText idText, pwdText;
    private List<NameValuePair> nameValuePairs;
    private String respon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        loginBtn = (Button) findViewById(R.id.loginBtn);
        idText = (EditText) findViewById(R.id.editTextID);
        pwdText = (EditText) findViewById(R.id.editTextPwd);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("로그인 중입니다..");
                dialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        login();
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        confirmHandler.sendEmptyMessage(0);
                        dialog.dismiss();
                    }
                }).start();
            }
        });
    }

    void login(){
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.1.160/logcheck.php");
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id",idText.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("pwd",pwdText.getText().toString()));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            respon = httpClient.execute(httpPost, responseHandler);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Handler confirmHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //완료 후 실행할 처리 삽입
            if(respon.equalsIgnoreCase("User Found")){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Login.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            }else{
                Toast.makeText(Login.this,"로그인 실패",Toast.LENGTH_SHORT).show();
            }
        }
    };
}
