package com.example.hyeon.finalserv;

import android.os.StrictMode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by HYEON on 2016-11-27.
 */

public class GetSyInfo {

    private URL url;

    public GetSyInfo() {

    }

    public void loadSysInfo_COM() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://my.knu.ac.kr/stpo/stpo/cour/listLectPln/list.action?search_open_crse_cde=1O02&sub=1O");
        try {
            httpClient.execute(httpget, new BasicResponseHandler() {
                @Override
                public String handleResponse(HttpResponse response) throws HttpResponseException, IOException {
                    String res = "";
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        res = EntityUtils.toString(resEntity, "UTF-8");
                    }
                    Document doc = Jsoup.parse(res);
                    Elements rows = doc.select("#viewPlans table.courTable tbody tr");
                    rows.remove(0);

                    //기존 데이터 삭제
                    PHPRequest requestD = new PHPRequest("http://192.168.1.160/COM_sugangD.php");
                    requestD.deleteInfo();

                    PHPRequest request = new PHPRequest("http://192.168.1.160/COM_sugangI.php");
                    for (Element row : rows) {
                        Iterator<Element> iterElem = row.getElementsByTag("td").iterator();

                            request.PhPtestCOM(iterElem.next().text(),iterElem.next().text(),iterElem.next().text(),
                                iterElem.next().text(),iterElem.next().text(),iterElem.next().text(),iterElem.next().text(),
                                iterElem.next().text(),iterElem.next().text(),iterElem.next().text(),iterElem.next().text(),
                                iterElem.next().text(),iterElem.next().text(),iterElem.next().text(),iterElem.next().text());
                    }
                    return res;
                }
            });
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSysInfo_GYO() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://my.knu.ac.kr/stpo/stpo/cour/listLectPln/list.action?search_subj_area_cde=13");
        try {
            httpClient.execute(httpget, new BasicResponseHandler() {
                @Override
                public String handleResponse(HttpResponse response) throws HttpResponseException, IOException {
                    String res = "";
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        res = EntityUtils.toString(resEntity, "UTF-8");
                    }
                    Document doc = Jsoup.parse(res);
                    Elements rows = doc.select("#viewPlans table.courTable tbody tr");
                    rows.remove(0);

                    //기존 데이터 삭제
                    PHPRequest requestD = new PHPRequest("http://192.168.1.160/GYO_sugangD.php");
                    requestD.deleteInfo();

                    PHPRequest request = new PHPRequest("http://192.168.1.160/GYO_sugangI.php");
                    for (Element row : rows) {
                        Iterator<Element> iterElem = row.getElementsByTag("td").iterator();

                        request.PhPtestGYO(iterElem.next().text(),iterElem.next().text(),iterElem.next().text(),
                                iterElem.next().text(),iterElem.next().text(),iterElem.next().text(),iterElem.next().text(),
                                iterElem.next().text(),iterElem.next().text(),iterElem.next().text(),iterElem.next().text(),
                                iterElem.next().text(),iterElem.next().text(),iterElem.next().text(),iterElem.next().text(),iterElem.next().text());
                    }
                    return res;
                }
            });
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
