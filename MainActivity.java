package com.mycompany.socks;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.io.*;
import java.net.*;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		Thread serverThread = new Thread(new ServerThread());
		serverThread.start();
		
		Button out = findViewById(R.id.mainButton1);
		out.setOnClickListener(new OnClickListener(){
		public void onClick(View view){
			TextView show = findViewById(R.id.mainTextView1);
			EditText input = findViewById(R.id.mainEditText1);
			String edit = input.getText().toString();
			if(edit == null || edit.isEmpty()){
				show.setText("Error !");
				show.setVisibility(View.VISIBLE);
			}
			else{
				show.setText("Address > 127.0.0.1:"+edit+"\n online server ...");
				show.setVisibility(View.VISIBLE);
			}
		}
		});
    }
    private class ServerThread implements Runnable {
        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(8080);
				while (true){
                    Socket client = serverSocket.accept();
                    OutputStream out = client.getOutputStream();
                    out.write("HTTP/1.1 200 OK\r\n".getBytes());
                    out.write("Content-Type: text/html\r\n\r\n".getBytes());
                    out.write("<html><meta name='viewport' content='width=device-width, initial-scale=1.0, shrink-to-fit=no'><body bgcolor='black' style='text-align:center'><code><img src='https://biaupload.com/do.php?imgf=org-24a2b4df3a981.jpg' style='border-radius:20px; height:200px; width:200;'><h1 style='color:red;'>127.0.0.1</h1><br><p style='font-weight:900; color:gray;'>online client</p></code></body></html>".getBytes());
                    out.flush();
                    out.close();
                    client.close();
            }
			}
			catch (IOException e) {
                e.printStackTrace();
            }
			}
    }
}
