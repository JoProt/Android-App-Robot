package com.example.piinterface;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
public class MainActivity extends AppCompatActivity {

    Button up, left, right, down, close, open, circLeft, circRight;
    ImageView connecStat;

    private static final String TAG = "Button Clicked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //refrenz Variables
        up = findViewById(R.id.buttonUP);
        left = findViewById(R.id.buttonLEFT);
        right =findViewById(R.id.buttonRIGHT);
        down =findViewById(R.id.buttonDOWN);
        close = findViewById(R.id.buttonCLOSE);
        open = findViewById(R.id.buttonOPEN);
        circLeft = findViewById(R.id.buttonCIRCLEFT);
        circRight = findViewById(R.id.buttonCIRCRIGHT);
        connecStat = findViewById(R.id.CONNSTST);
        //--------------------------------------------


        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Up");
                connecStat.setColorFilter(Color.GREEN);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Right");
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Left");
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Down");
                connecStat.setColorFilter(Color.RED);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Close");
            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Open");
            }
        });
        circRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Circle Right");
            }
        });
        circLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Circle Left");
            }
        });

    }
}
