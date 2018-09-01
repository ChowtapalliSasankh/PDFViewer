package com.example.home.pdfviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class about_app extends AppCompatActivity {
    ImageView i;
    TextView t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        i = (ImageView)findViewById(R.id.ap1);
        t1 = (TextView)findViewById(R.id.ap2);
        t2 = (TextView)findViewById(R.id.ap3);
        t3 = (TextView)findViewById(R.id.ap4);
        t4 = (TextView)findViewById(R.id.ap5);
    }
}