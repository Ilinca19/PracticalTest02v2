package ro.pub.cs.systems.eim.practicaltest02v2.network;

import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.practicaltest02v2.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02v2.general.Utilities;

public class CommunicationThread extends Thread {
    private final ServerThread serverThread;
    private final Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket == null) {
            Log.e(Constants.TAG, "[Communication Thread] Socket is null!");
            return;
        }
        try {
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            Log.i(Constants.TAG, "[Communication Thread] Waiting for parameters from client (operand1 / operand2 / operator)!");
            String request = bufferedReader.readLine();
            if (request == null) {
                Log.e(Constants.TAG, "[Communication Thread] Error receiving parameters from client (operand1 / operand2 / operator)!");
                return;
            }
            String[] requestParameters = request.split(",");
            if (requestParameters.length != 3) {
                Log.e(Constants.TAG, "[Communication Thread] Invalid parameters from client (operand1 / operand2 / operator)!");
                return;
            }
            String operator = requestParameters[0];
            Integer operand1 = Integer.parseInt(requestParameters[1]);
            Integer operand2 = Integer.parseInt(requestParameters[2]);
            String result;
            switch (operator) {
                case Constants.ADDITION:
                    try {
                        result = String.valueOf(operand1 + operand2);
                    } catch (Exception e) {
                        result = "overwrite";
                    }

                    break;
                case Constants.MULTIPLICATION:
                    Thread.sleep(2000);
                    try {
                        result = String.valueOf(operand1 * operand2);
                    } catch (Exception e) {
                        result = "overwrite";
                    }

                    break;
                default:
                    result = "[COMMUNICATION THREAD] Invalid operator!";
            }

            printWriter.println(result);
            printWriter.flush();
        } catch (IOException | RuntimeException | InterruptedException e) {
            Log.e(Constants.TAG, "[Communication Thread] An exception has occurred: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e(Constants.TAG, "[Communication Thread] An exception has occurred: " + e.getMessage());
            }
        }
    }
}
