package com.example.piinterface;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


public class Settings_Dialog extends AppCompatDialogFragment{
    private EditText ipdialog;
    private EditText portdialog;
    private String ip = "";
    private int port = 0;
    private Settings_Dialog_Listener listener;
    private final static String SHARED_PREF = "Shared Prefs";
    private final static String IP_ADRESSE_KEY = "ip_adresse_key";
    private final static String PORT_NUMBER_KEY = "port_number_key";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        ipdialog =  view.findViewById(R.id.ipadress);
        portdialog =  view.findViewById(R.id.portnummer);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        ip = sharedPreferences.getString(IP_ADRESSE_KEY,"");
        port = sharedPreferences.getInt(PORT_NUMBER_KEY,0);
        ipdialog.setText(ip);
        portdialog.setText(String.valueOf(port));
        builder.setView(view)
                .setTitle("Settings")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ipshipper = ipdialog.getText().toString();
                        String portshipper = portdialog.getText().toString();
                        if (portshipper.isEmpty()){
                            portshipper = "0";
                        }
                        listener.applySettings(ipshipper,portshipper);
                    }
                });


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (Settings_Dialog_Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "implementiere Settings Dialog Interface");
        }

    }



      public interface Settings_Dialog_Listener{
        void applySettings(String ip, String port);
    }
}
