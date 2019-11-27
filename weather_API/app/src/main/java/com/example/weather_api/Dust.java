package com.example.weather_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Dust extends AppCompatActivity {
    TextView textView1, textView2;
    String address, location;
    Document document = null;
    GetXMLTask getXMLTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dust);
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        location = intent.getStringExtra("location");
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getXMLTask = new GetXMLTask();
        getXMLTask.execute();
    }

    private class GetXMLTask extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... strings) {
            try{
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                document = documentBuilder.parse(address);
                document.getDocumentElement().normalize();

                NodeList nodeList = document.getElementsByTagName("row");
                Node node = nodeList.item(0);
                Element element = (Element)node;
                NodeList msrste_nm = element.getElementsByTagName("MSRSTE_NM");

                if(msrste_nm.item(0).getFirstChild().getNodeValue().equals(location)){
                    NodeList pm10 = element.getElementsByTagName("PM10");
                    NodeList pm25 = element.getElementsByTagName("PM25");
                    textView1.setText(pm10.item(0).getFirstChild().getNodeValue());
                    textView2.setText(pm25.item(0).getFirstChild().getNodeValue());
                }
                if(textView1.getText().toString() == "")
                    excep();
            } catch (Exception e) {
                excep();
                e.printStackTrace();
            }
            return null;
        }
        public void excep(){
            textView1.setText("없음");
            textView2.setText("없음");
        }
    }
}
