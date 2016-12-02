package com.example.hyeon.finalserv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.util.Log;
/**
 * Created by HYEON on 2016-11-28.
 */

public class PHPRequest {
    private URL url;

    public PHPRequest(String url) throws MalformedURLException { this.url = new URL(url); }

    private String readStream(InputStream in) throws IOException {
        StringBuilder jsonHtml = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = null;

        while((line = reader.readLine()) != null)
            jsonHtml.append(line);

        reader.close();
        return jsonHtml.toString();
    }

    public String deleteInfo()
    {
        try {
            String postData="";
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        }
        catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    public String PhPtestCOM(final String data1, final String data2, final String data3,
                          final String data4, final String data5, final String data6,
                          final String data7, final String data8, final String data9,
                          final String data10, final String data11, final String data12,
                          final String data13, final String data14, final String data15) {
        try {
            String postData = "grade=" + data1 + "&" + "div=" +  new String(data2.getBytes("UTF-8")) + "&" + "num=" + new String(data3.getBytes("UTF-8")) + "&"
                    + "name=" + new String(data4.getBytes("UTF-8")) + "&" + "credit=" + data5 + "&" + "lec=" + data6 + "&" + "prac=" + data7 + "&"
                    + "prof=" + new String(data8.getBytes("UTF-8")) + "&" + "time=" + new String(data9.getBytes("UTF-8")) + "&" + "place=" + new String(data10.getBytes("UTF-8")) + "&" + "max=" + data11+ "&"
                    + "sign=" + data12 + "&" + "suggu=" + data13 + "&" + "suggubool=" + new String(data14.getBytes("UTF-8")) + "&" + "note=" + new String(data15.getBytes("UTF-8"));

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        }
        catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    public String PhPtestGYO(final String data1, final String data2, final String data3,
                             final String data4, final String data5, final String data6,
                             final String data7, final String data8, final String data9,
                             final String data10, final String data11, final String data12,
                             final String data13, final String data14, final String data15, final String data16) {
        try {
            String postData = "grade=" + data1 + "&" + "div=" +  new String(data2.getBytes("UTF-8")) + "&" + "est=" + new String(data3.getBytes("UTF-8")) + "&" + "num=" + new String(data4.getBytes("UTF-8")) + "&"
                    + "name=" + new String(data5.getBytes("UTF-8")) + "&" + "credit=" + data6 + "&" + "lec=" + data7 + "&" + "prac=" + data8 + "&"
                    + "prof=" + new String(data9.getBytes("UTF-8")) + "&" + "time=" + new String(data10.getBytes("UTF-8")) + "&" + "place=" + new String(data11.getBytes("UTF-8")) + "&" + "max=" + data12+ "&"
                    + "sign=" + data13 + "&" + "suggu=" + data14 + "&" + "suggubool=" + new String(data15.getBytes("UTF-8")) + "&" + "note=" + new String(data16.getBytes("UTF-8"));

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        }
        catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }
}
