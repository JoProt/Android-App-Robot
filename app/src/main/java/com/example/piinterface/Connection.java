package com.example.piinterface;

import android.app.Application;
import android.content.Context;
import android.system.ErrnoException;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection {
    //Variablen ----------------------------------------------------
    private static Socket socket;
    private static PrintWriter printWriter;
    static String ip = "";
    static int port = 0;

    public boolean isConnected() {
        return connected;
    }

    public boolean isReachable() {
        return isReachable;
    }

    boolean connected = false;
    boolean isReachable = false;
    // ----------------------------------------------------------

    public Connection(String ipadresse, int portnummer) {
        this.ip = ipadresse;
        this.port = portnummer;
    }


    public boolean open() {

        if (connected == false) {
            connected = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket = new Socket(ip, port);
                        socket.setKeepAlive(true);
                        connected = true;
                        printWriter = new PrintWriter(socket.getOutputStream());
                    } catch (IOException io) {
                        connected = false;

                        io.printStackTrace();
                    } catch (Exception e) {
                        connected = false;
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {  //connected == true -> click on image to disconnect connection
            try {
                printWriter.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connected = false;
            }
        }
        return connected;

    }

    public void send(final int msg) {

        /*
         * msg == 1 -> UP
         * msg == 2 -> TOWARDS
         * msg == 3 -> AWAY
         * msg == 4 -> DOWN
         * msg == 5 -> OPEN
         * msg == 6 -> CLOSE
         * msg == 7 -> CIRLCE LEFT
         * msg == 8 -> CIRLCE RIGHT
         */
        if (connected) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (connected == true) {
                            printWriter.write(String.valueOf(msg));
                            printWriter.flush();
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }).start();
        }
    }

    public void close() {
        try {
            printWriter.close();
            socket.close();
            connected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}