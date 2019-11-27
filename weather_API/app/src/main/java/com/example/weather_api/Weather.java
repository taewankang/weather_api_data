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
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Weather extends AppCompatActivity {
    TextView textView1, textView2, textView3, textView4, textView5, textView6;
    String address, location;
    GetXMLTask getXMLTask;
    Document document = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        location = intent.getStringExtra("location");
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getXMLTask = new GetXMLTask();
        getXMLTask.execute();
    }

    private class GetXMLTask extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... strings) {

            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                document = documentBuilder.parse(address);
                document.getDocumentElement().normalize();

                NodeList nodeList = document.getElementsByTagName("row");
                Node node = nodeList.item(0);
                org.w3c.dom.Element element = (Element) node;
                NodeList msrste_nm = element.getElementsByTagName("MSRSTE_NM");
                if (msrste_nm.item(0).getFirstChild().getNodeValue().equals(location)) {
                    NodeList no2 = element.getElementsByTagName("NO2");
                    textView1.setText(no2.item(0).getFirstChild().getNodeValue());

                    NodeList o3 = element.getElementsByTagName("O3");
                    textView2.setText(o3.item(0).getFirstChild().getNodeValue());

                    NodeList co = element.getElementsByTagName("CO");
                    textView3.setText(co.item(0).getFirstChild().getNodeValue());

                    NodeList so2 = element.getElementsByTagName("SO2");
                    textView4.setText(so2.item(0).getFirstChild().getNodeValue());

                    NodeList pm10 = element.getElementsByTagName("PM10");
                    textView5.setText(pm10.item(0).getFirstChild().getNodeValue());

                    NodeList pm25 = element.getElementsByTagName("PM25");
                    textView6.setText(pm25.item(0).getFirstChild().getNodeValue());
                }
                if (textView1.getText().toString() == "")
                    excep();
            } catch (Exception e) {
                excep();
                e.printStackTrace();
            }
            return null;
        }

        public void excep() {
            if (textView1.getText().toString() == "")
                textView1.setText("없음");
            if (textView2.getText().toString() == "")
                textView2.setText("없음");
            if (textView3.getText().toString() == "")
                textView3.setText("없음");
            if (textView4.getText().toString() == "")
                textView4.setText("없음");
            if (textView5.getText().toString() == "")
                textView5.setText("없음");
            if (textView6.getText().toString() == "")
                textView6.setText("없음");
        }
    }
}