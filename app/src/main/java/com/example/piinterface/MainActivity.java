package com.example.piinterface;


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
import java.net.Socket;



public class MainActivity extends AppCompatActivity implements Settings_Dialog.Settings_Dialog_Listener{
// Variablen ----------------------------------------------------

    int msgCode = 0;
    static String ipadress = "";
    static int portnumber = 0;
    ImageView connStat;
    android.support.v7.widget.Toolbar toolbar;
    boolean connected = false;
    // own classes
    SharedPrefs sharedPreferences;
    Connection connection;

    //--------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sharedPreferences = new SharedPrefs(this);
        setContentView(R.layout.activity_main);
        connStat = findViewById(R.id.CONNSTST);
        toolbar =  findViewById(R.id.my_toolbar);
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
    Toast.makeText(this,"Data Saved " + " IP: " + ipadress + " | Port: "+ portnumber, Toast.LENGTH_SHORT).show();
}

public void Load(){
        ipadress = sharedPreferences.getIP();
        portnumber = sharedPreferences.getPort();
        Toast.makeText(this, "ip: "+ ipadress +" port: "+ portnumber, Toast.LENGTH_SHORT).show();
}

public void initConnection(View v){
/* Verbindungsaufbau

FIRST: send an Ping to the Server to check if server is reachable
if reachable: continue with SECOND
if !reachable: Toast.makeText("Server nicht erreichbar")

SECOND: Start Thread and establish Connection to Socket

 */
//FIRST:


//SECOND:
    connection = new Connection(ipadress, portnumber);

    if(!connected) {

        connected = connection.open();  //establish the connection
        connStat.setBackgroundResource(R.drawable.ic_connected_24dp);
        System.out.println(connected);

    }
    else {
        connected = connection.close();
        connStat.setBackgroundResource(R.drawable.ic_disconnected_24dp);
        System.out.println(connected);
    }

    }

// Buttons OnClick ----------------------------------------------------
    public void OnClickUp(View v){
        connection.send(1);
    }
    public void OnClickRight(View v){
        connection.send(2);
    }
    public void OnClickLeft(View v){
        connection.send(3);
    }
    public void OnClickDown(View v){
        connection.send(4);
    }
    public void OnClickOpen(View v){
        connection.send(5);
    }
    public void OnClickClose(View v){
        connection.send(6);
    }
    public void OnClickCirlceLeft(View v){
        connection.send(7);
    }
    public void OnClickCirlceRight(View v){
        connection.send(8);
    }
// ----------------------------------------------------------------------

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // socket wieder freigeben
        connection.close();
    }
}
