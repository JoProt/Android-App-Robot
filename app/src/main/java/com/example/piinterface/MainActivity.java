package com.example.piinterface;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;


public class MainActivity extends AppCompatActivity {

    private static Socket socket;
    private static PrintWriter printWriter;
    int msgCode = 0;
    private static String ip = "192.168.178.40";
    int port = 5555;
    boolean socketisAlive = false;
    ImageView connStat;
    TextView connection;
    android.support.v7.widget.Toolbar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection = (TextView) findViewById(R.id.connection);
        connStat = (ImageView) findViewById(R.id.CONNSTST);
        bar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(bar);
    }


    public void initConnection(View v){
        if(connection.getText().equals("Verbunden")){
            try {
                printWriter.close();
                socket.close();
                connStat.setColorFilter(Color.RED);
                connection.setText("Nicht Verbunden");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket = new Socket(ip, port);
                        socket.setKeepAlive(true);
                        socketisAlive = true;
                        connStat.setColorFilter(Color.GREEN);
                        connection.setText("Verbunden");

                        printWriter = new PrintWriter(socket.getOutputStream());


                    } catch (IOException io) {
                        connStat.setColorFilter(Color.RED);
                        connection.setText("Nicht Verbunden");
                        io.printStackTrace();
                    }
                }
            }).start();



    }






    public void OnClickUp(View v){
        SendToSocket(1);
    }
    public void OnClickRight(View v){
        SendToSocket(2);
    }
    public void OnClickLeft(View v){
        SendToSocket(3);
    }
    public void OnClickDown(View v){
        SendToSocket(4);
    }
    public void OnClickOpen(View v){
        SendToSocket(5);
    }
    public void OnClickClose(View v){
        SendToSocket(6);
    }
    public void OnClickCirlceLeft(View v){
        SendToSocket(7);
    }
    public void OnClickCirlceRight(View v){
        SendToSocket(8);
    }

    private void SendToSocket(int code){
        if (socketisAlive == true){
            printWriter.flush();
            printWriter.write(code);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            printWriter.close();
            socket.close();
            connStat.setColorFilter(Color.RED);
            connection.setText("Nicht Verbunden");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
