package com.example.weather_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    final String address = "http://openapi.seoul.go.kr:8088/4e614b43496b696e38386c56476e55/xml/DailyAverageAirQuality/1/5/";
    TextView date_textView, location_textView, location_textView2;
    DateFormat dateFormat_year = new SimpleDateFormat("yyyy");
    DateFormat dateFormat_month = new SimpleDateFormat("MM");
    DateFormat dateFormat_day = new SimpleDateFormat("dd");
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date_textView = (TextView)findViewById(R.id.date_textView);
        location_textView = (TextView)findViewById(R.id.location_textView);
        location_textView2 = (TextView)findViewById(R.id.location_textView2);
    }

    public void onClick1(View view) {
        Intent intent = new Intent(this, Weather.class);
        String date = date_textView.getText().toString();
        date = get_date(date);
        String address2 = address;
        address2 += date + "/" + location_textView.getText().toString();
        intent.putExtra("address", address2);
        intent.putExtra("location", location_textView.getText().toString());
        startActivity(intent);
    }

    public void onClick2(View view){
        Intent intent = new Intent(this, Dust.class);
        String str = dateFormat.format(date);
        str = str.replaceAll("[^0-9]", "");
        String address2 = address;
        address2 += (str + "/" + location_textView2.getText().toString());
        intent.putExtra("address", address2);
        intent.putExtra("location", location_textView2.getText().toString());
        startActivity(intent);
    }

    public String get_date(String date){
        String str = "";
        String temp = "";
        for(int i=0; i<date.length(); i++){
            if(date.charAt(i) == '년'){
                str += temp;
                temp = "";
                continue;
            }else if(date.charAt(i)=='월'){
                if(temp.length() == 1){
                    str += ("0" + temp);
                }else{
                    str += temp;
                }
                temp = "";
                continue;
            }else if(date.charAt(i) == '일'){
                if(temp.length() == 1){
                    str += ("0" + temp);
                }else{
                    str += temp;
                }
                temp = "";
                continue;
            }
            temp += date.charAt(i);
        }
        return str;
    }
}
