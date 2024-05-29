package ro.pub.cs.systems.eim.practicaltest02v2.network;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


import lombok.Getter;
import ro.pub.cs.systems.eim.practicaltest02v2.general.Constants;


public class ServerThread extends Thread {
    @Getter
    private ServerSocket serverSocket = null;

    public ServerThread(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            Log.e(Constants.TAG, "An exception has occurred: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.i(Constants.TAG, "[Server Thread] Waiting for a client invocation...");
                Socket socket = serverSocket.accept();
                Log.i(Constants.TAG, "[Server Thread] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
                CommunicationThread communicationThread = new CommunicationThread(this, socket);
                communicationThread.start();
            }
        } catch (IOException e) {
            Log.e(Constants.TAG, "An exception has occurred: " + e.getMessage());
        }
    }

    public void stopThread() {
        interrupt();
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                Log.e(Constants.TAG, "[SERVER THREAD] An exception has occurred: " + e.getMessage());
            }
        }
    }
}
