package ro.pub.cs.systems.eim.practicaltest02v2.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ro.pub.cs.systems.eim.practicaltest02v2.R;
import ro.pub.cs.systems.eim.practicaltest02v2.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02v2.network.ClientThread;
import ro.pub.cs.systems.eim.practicaltest02v2.network.ServerThread;

public class PracticalTest02v2MainActivity extends AppCompatActivity {
    private EditText serverPortEditText;
    private Button connectButton;
    private EditText clientAddressEditText;
    private EditText clientPortEditText;
    private EditText operand1EditText;
    private EditText operand2EditText;
    private Button addButton;
    private Button multiplyButton;
    private TextView resultTextView;

    private ServerThread serverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Constants.TAG, "[Main Activity] onCreate() callback method was invoked");
        setContentView(R.layout.activity_practical_test02v2_main);

        serverPortEditText = (EditText) findViewById(R.id.server_port_edit_text);
        connectButton = (Button) findViewById(R.id.connect_button);
        clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);
        clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
        operand1EditText = (EditText)findViewById(R.id.operand1_edit_text);
        operand2EditText = (EditText)findViewById(R.id.operand2_edit_text);
        addButton = (Button)findViewById(R.id.add_button);
        multiplyButton = (Button)findViewById(R.id.multiply_button);
        resultTextView = (TextView)findViewById(R.id.result_text_view);

        connectButton.setOnClickListener(view -> {
            Log.i(Constants.TAG, "[Main Activity] Connect button was pressed");
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[Main Activity] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e(Constants.TAG, "[Main Activity] Could not create server thread!");
                return;
            }
            serverThread.start();
        });

        addButton.setOnClickListener(view -> {
            Log.i(Constants.TAG, "[Main Activity] Add button was pressed");
            String clientAddress = clientAddressEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();
            if (clientAddress.isEmpty() || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[Main Activity] Client address and port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[Main Activity] Server thread is not running!", Toast.LENGTH_SHORT).show();
                return;
            }
            String operand1 = operand1EditText.getText().toString();
            String operand2 = operand2EditText.getText().toString();
            String operator = "add";
            if (operand1.isEmpty() || operand2.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[Main Activity] Operands should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            resultTextView.setText("");
            ClientThread clientThread = new ClientThread(
                    clientAddress, Integer.parseInt(clientPort), operand1, operand2, operator, resultTextView);
            clientThread.start();
        });

        multiplyButton.setOnClickListener(view -> {
            Log.i(Constants.TAG, "[Main Activity] Multiply button was pressed");
            String clientAddress = clientAddressEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();
            if (clientAddress.isEmpty() || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[Main Activity] Client address and port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[Main Activity] Server thread is not running!", Toast.LENGTH_SHORT).show();
                return;
            }
            String operand1 = operand1EditText.getText().toString();
            String operand2 = operand2EditText.getText().toString();
            String operator = "mul";
            if (operand1.isEmpty() || operand2.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[Main Activity] Operands should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            resultTextView.setText("");
            ClientThread clientThread = new ClientThread(
                    clientAddress, Integer.parseInt(clientPort), operand1, operand2, operator, resultTextView);
            clientThread.start();
        });
    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, "[Main Activity] onDestroy() callback method was invoked");
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }
}