package com.example.piinterface;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class MainActivity extends AppCompatActivity implements SettingsFragment.Settings_Dialog_Listener {
// Variablen ----------------------------------------------------
    static String ipadress = "";
    static int portnumber = 0;
    ImageView connStat;
    android.support.v7.widget.Toolbar toolbar;
    Switch grapSwitch;
    // own classes
    SharedPrefs sharedPreferences;
    Connection connection;
    Button up,down,left,right,cLeft,cRight;
    //--------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sharedPreferences = new SharedPrefs(this);
        setContentView(R.layout.activity_main);
        connStat = findViewById(R.id.CONNSTST);
        toolbar = findViewById(R.id.my_toolbar);
        grapSwitch = findViewById(R.id.grapSwitch);
        setSupportActionBar(toolbar);
        Load();

        up = findViewById(R.id.buttonUP);
        down = findViewById(R.id.buttonDOWN);
        left = findViewById(R.id.buttonLEFT);
        right = findViewById(R.id.buttonRIGHT);
        cLeft = findViewById(R.id.buttonCIRCLEFT);
        cRight = findViewById(R.id.buttonCIRCRIGHT);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.show(getSupportFragmentManager(), "My Dialog");

        return super.onOptionsItemSelected(item);
    }
//---------------------------------------------------------------------

    // Interface Dialog
    @Override
    public void applySettings(String ip, String port) {
        ipadress = ip;
        if (port.equals("")) {
            portnumber = 0;
        } else {
            portnumber = Integer.parseInt(port);
        }
        Toast.makeText(this, "Data Saved " + " IP: " + ipadress + " | Port: " + portnumber, Toast.LENGTH_SHORT).show();
    }

    public void Load() {
        ipadress = sharedPreferences.getIP();
        portnumber = sharedPreferences.getPort();
        Toast.makeText(this, "ip: " + ipadress + " port: " + portnumber, Toast.LENGTH_SHORT).show();
    }

    public void initConnection(View v) {
        if(Connection.connected == false) {
            connection = new Connection(ipadress, portnumber);
        }
        if (connection.isConnected() == false) {   //connection not opend
            connection.open();
            if(connection.isConnected()) {  //establish the connection. if successfull return true
                connStat.setBackgroundResource(R.drawable.ic_connected_24dp);
                EnableButtons(true);
            }
            else{
                Toast.makeText(this, "Login: Server nicht erreichbar", Toast.LENGTH_SHORT).show();
                connStat.setBackgroundResource(R.drawable.ic_disconnected_24dp);
                EnableButtons(false);
            }
        } else {
            try {
                connection.close();
                connStat.setBackgroundResource(R.drawable.ic_disconnected_24dp);
                EnableButtons(false);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void EnableButtons(boolean b) {
        up.setEnabled(b);
        down.setEnabled(b);
        left.setEnabled(b);
        right.setEnabled(b);
        cLeft.setEnabled(b);
        cRight.setEnabled(b);
        grapSwitch.setEnabled(b);
    }

    // Buttons OnClick ----------------------------------------------------
    public void OnClickUp(View v) {
        if (connection.isConnected()) {
            connection.send(1);
        }
        else{
            Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
            grapSwitch.setChecked(false);
        }
    }

    public void OnClickTowards(View v) {
        if (connection.isConnected()) {
            connection.send(3);
        }
        else{
            Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
            grapSwitch.setChecked(false);
        }
    }

    public void OnClickAway(View v) {
        if (connection.isConnected()) {
            connection.send(2);
        }
        else{
            Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
            grapSwitch.setChecked(false);
        }
    }

    public void OnClickDown(View v) {
        if (connection.isConnected()) {
            connection.send(4);
        }
        else{
            Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
            grapSwitch.setChecked(false);
        }
    }

    public void GrapMethod(View view) {

        if(grapSwitch.isChecked()==false){
            if (connection.isConnected()) {
                connection.send(6);
            }
            else{
                Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
                grapSwitch.setChecked(false);
            }
        }
        else{
            if (connection.isConnected()) {
                connection.send(5);
            }
            else{
                Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
                grapSwitch.setChecked(false);
            }
        }

    }

    public void OnClickCirlceLeft(View v) {
        if (connection.isConnected()) {
            connection.send(8);
        }
        else{
            Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
            grapSwitch.setChecked(false);
        }
    }

    public void OnClickCirlceRight(View v) {
        if (connection.isConnected()) {
            connection.send(7);
        }
        else{
            Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
            grapSwitch.setChecked(false);
        }
    }
// ----------------------------------------------------------------------


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // socket wieder freigeben
        connection.close();
    }


}
