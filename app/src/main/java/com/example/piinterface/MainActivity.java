package com.example.piinterface;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class MainActivity extends AppCompatActivity implements Settings_Dialog.Settings_Dialog_Listener {
// Variablen ----------------------------------------------------
    private static Socket socket;
    private static PrintWriter printWriter;
    int msgCode = 0;
    static String ipadress = "";
    static int portnumber = 0;
    boolean socketisAlive = false;
    ImageView connStat;
    TextView connection;
    android.support.v7.widget.Toolbar toolbar;

    private final static String SHARED_PREF = "Shared Prefs";
    private final static String IP_ADRESSE_KEY = "ip_adresse_key";
    private final static String PORT_NUMBER_KEY = "port_number_key";

    //--------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connStat = (ImageView) findViewById(R.id.CONNSTST);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Settings_Dialog dialog = new Settings_Dialog();
        dialog.show(getSupportFragmentManager(), "My Dialog");

        return super.onOptionsItemSelected(item);
    }
//---------------------------------------------------------------------

// Interface Dialog
    @Override
    public void applySettings(String ip, String port) {
    ipadress = ip;
    if (port == ""){
        portnumber = 0;
    }
    else {
        portnumber = Integer.parseInt(port);
    }
    Save();

}
//---------------------------------------------------------------------
public void Save(){
    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
    SharedPreferences.Editor sEditor = sharedPreferences.edit();

    sEditor.putString(IP_ADRESSE_KEY, String.valueOf(ipadress));
    sEditor.putInt(PORT_NUMBER_KEY, portnumber);
    sEditor.apply();
    Toast.makeText(this,"Data Saved " + " IP: " + ipadress + " | Port: "+ portnumber, Toast.LENGTH_SHORT).show();
}
public void Load(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        ipadress = sharedPreferences.getString(IP_ADRESSE_KEY,"");
        portnumber = sharedPreferences.getInt(PORT_NUMBER_KEY, 0);
        Toast.makeText(this, "ip: "+ ipadress +" port: "+ portnumber, Toast.LENGTH_SHORT).show();

}
// Verbindungsaufbau
    public void initConnection(View v){

        if(!socketisAlive) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket = new Socket(String.valueOf(ipadress), portnumber);
                        socket.setKeepAlive(true);
                        socketisAlive = true;
                        connStat.setBackgroundResource(R.drawable.ic_connected_24dp);
                        printWriter = new PrintWriter(socket.getOutputStream());


                    } catch (IOException io) {
                        connStat.setBackgroundResource(R.drawable.ic_disconnected_24dp);
                        socketisAlive= false;
                        io.printStackTrace();
                    }
                }
            }).start();
        }
        else{
            try {
                printWriter.close();
                socket.close();
                connStat.setBackgroundResource(R.drawable.ic_disconnected_24dp);
                socketisAlive = false;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }





    }
//---------------------------------------------------------------------

// Buttons OnClick ----------------------------------------------------
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
// ----------------------------------------------------------------------


    private void SendToSocket(int code){
        if (socketisAlive == true){
            printWriter.flush();
            printWriter.write(code);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        // verbindungsabbau
        try {
            printWriter.close();
            socket.close();
            connStat.setBackgroundResource(R.drawable.ic_disconnected_24dp);
            socketisAlive = false;


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
