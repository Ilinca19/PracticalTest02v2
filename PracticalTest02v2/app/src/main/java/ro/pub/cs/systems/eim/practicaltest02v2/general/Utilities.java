package ro.pub.cs.systems.eim.practicaltest02v2.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Utilities {
    public static BufferedReader getReader(Socket socket) {
        try {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrintWriter getWriter(Socket socket) {
        try {
            return new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
