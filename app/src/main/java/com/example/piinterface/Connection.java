package com.example.piinterface;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    //Variablen ----------------------------------------------------
    private static Socket socket;
    private static PrintWriter printWriter;
    static String ip = "";
    static int port = 0;
    boolean connectionStatus = false;
    // ----------------------------------------------------------

    public Connection(String ipadresse, int portnummer) {
        this.ip = ipadresse;
        this.port = portnummer;
    }

    public boolean open(){
        if(!connectionStatus){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket = new Socket(ip, port);
                        socket.setKeepAlive(true);
                        connectionStatus = true;
                        printWriter = new PrintWriter(socket.getOutputStream());


                    } catch (IOException io) {
                        connectionStatus = false;
                        io.printStackTrace();
                    }
                }
            }).start();
            connectionStatus = true;
        }
        else{
            try {
                printWriter.close();
                socket.close();
                connectionStatus = false;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return connectionStatus;
    }

    public void send(int msg) {
        if (connectionStatus == true) {
            printWriter.flush();
            printWriter.write(msg);
        }
    }

    public boolean close() {
        try {
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionStatus = false;
       return connectionStatus;

    }

}