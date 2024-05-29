package ro.pub.cs.systems.eim.practicaltest02v2.network;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.RequiredArgsConstructor;
import ro.pub.cs.systems.eim.practicaltest02v2.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02v2.general.Utilities;

@RequiredArgsConstructor
public class ClientThread extends Thread {
    private final String address;
    private final int port;
    private final String operand1;
    private final String operand2;
    private final String operator;
    private final TextView resultTextView;

    private Socket socket;

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            String request = operator + "," + operand1 + "," + operand2 + "\n";
            printWriter.println(request);
            printWriter.flush();
            String result;
            if ((result = bufferedReader.readLine()) != null) {
                final String finalizedResult = result;
                resultTextView.post(() -> resultTextView.setText(finalizedResult));
            }
        } catch (Exception e) {
            Log.e(Constants.TAG, "[ClientThread] An exception has occurred: " + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    Log.e(Constants.TAG, "[ClientThread] An exception has occurred: " + e.getMessage());
                }
            }
        }
    }
}
