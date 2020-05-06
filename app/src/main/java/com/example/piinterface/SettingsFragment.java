package com.example.piinterface;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;



public class SettingsFragment extends AppCompatDialogFragment{
    private EditText ipdialog;
    private EditText portdialog;
    private String ip = "";
    private int port = 0;
    private Settings_Dialog_Listener listener;

    private SharedPrefs sharedPreferences;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_settings, null);
        sharedPreferences = new SharedPrefs(getActivity());
        ipdialog =  view.findViewById(R.id.ipadress);
        portdialog =  view.findViewById(R.id.portnummer);

        ip = sharedPreferences.getIP();
        port = sharedPreferences.getPort();
        ipdialog.setText(ip);
        portdialog.setText(String.valueOf(port));
        builder.setView(view)
                .setTitle("Settings")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ipshipper = ipdialog.getText().toString();
                        String portshipper = portdialog.getText().toString();
                        sharedPreferences.setIP(ipshipper);
                        sharedPreferences.setPort(Integer.parseInt(portshipper));

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
