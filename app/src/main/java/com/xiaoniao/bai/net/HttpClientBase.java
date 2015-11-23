package com.xiaoniao.bai.net;

import android.util.Log;

import com.xiaoniao.bai.utils.AppConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by bai on 2015/11/9.
 */
public class HttpClientBase {
    HttpURLConnection httpURLConnection = null ;
    private static HttpClientBase me = null;
    private BufferedReader mReader;
    private Writer mWriter;

    private HttpClientBase(){}
    public static HttpClientBase getInstance(){
        if ( me == null )
            me = new HttpClientBase();
        return me;
    }
    private void Init(){
        CookieManager manager = new CookieManager();
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);
        CookieStore cookieJar = manager.getCookieStore();
        List<HttpCookie> cookies = cookieJar.getCookies();
        for (HttpCookie cookie : cookies) {
            Log.i( "MyLog", "name="+cookie.getName()+",str="+cookie.toString() );
        }
    }
    public boolean startup(String url){
        boolean bStartupOk = true;
        try {
            URL oURL = new URL(url);
            httpURLConnection = (HttpURLConnection) oURL.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(5 * 1000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            //httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.connect();
            //Init();

            createWriter();
            write( NetPacket.getInstance().GetSendPacket() );
            createReader();
        } catch (MalformedURLException e) {
            Log.i("MyLog", "startup.MalformedURLException error:" + e.getMessage());
            bStartupOk = false;
        } catch (IOException e) {
            Log.i("MyLog","startup.IOException error:"+e.getMessage());
            bStartupOk = false;
        }
        return bStartupOk;
    }
    public boolean isConnented(){
        if(httpURLConnection!=null ){

        }
        return  false;
    }
    public void disconnect(){
        if (httpURLConnection!=null){
            try {
                mWriter.close();
                mReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpURLConnection.disconnect();
            httpURLConnection = null;
            mWriter = null;
            mReader = null;
        }
    }
    public String read(){
        if (httpURLConnection==null)
            return null;
        if (mReader==null){
            createReader();
        }
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            while ((tempLine = mReader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
        } catch (IOException e) {
            Log.i("MyLog", "read.IOException error:" + e.getMessage());
            return null;
        }
        return resultBuffer.toString();
    }
    public boolean write(String msg){
        if (httpURLConnection==null || msg==null || msg=="")
            return false;
        if (mWriter==null){
            createWriter();
        }
        try {
            //mWriter.write(String.valueOf(msg.getBytes()));
            mWriter.write(msg);
            mWriter.flush();
//            Init();
//            if( httpURLConnection.getResponseCode()==200 )
//               return true;
        } catch (IOException e) {
            Log.i("MyLog", "write.IOException error:" + e.getMessage());
            return false;
        }
        return true;
    }
    private void createReader(){
        InputStream inputStream = null;
        try {
            inputStream = httpURLConnection.getInputStream();
        } catch (IOException e) {
            Log.i("MyLog", "read.IOException error:" + e.getMessage());
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        mReader = new BufferedReader(inputStreamReader);
    }
    private void createWriter(){
        OutputStream outputStream = null;
        try {
            outputStream = httpURLConnection.getOutputStream();
        } catch (IOException e) {
            Log.i("MyLog", "write.IOException error:" + e.getMessage());
        }
        //mWriter = new OutputStreamWriter(outputStream);
        mWriter = new PrintWriter(outputStream);
    }
}
