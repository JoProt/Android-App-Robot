package com.example.piinterface;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Button Clicked";
    private static Socket socket;
    private static PrintWriter printWriter;
    int msgCode = 0;
    private static String ip = "192.168.178.40";
    int port = 5555;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        this.msgCode = code;

        MSGTask msgTask = new MSGTask();
        msgTask.execute();
    }

    class MSGTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                socket = new Socket(ip, port);
                printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.write(msgCode);
                printWriter.flush();
                printWriter.close();

                socket.close();

            }
            catch (IOException io){
                io.printStackTrace();
            }
            return null;
        }
    }
}
