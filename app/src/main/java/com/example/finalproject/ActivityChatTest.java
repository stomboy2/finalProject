package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ActivityChatTest extends AppCompatActivity {

    TextView chattesttxt;
    EditText chattestedit;
    Button chattestBtn;

//    String titleTmp = MainActivity.vodTitle;
    String titleTmp = "tester44";

    Handler handler;
    String data;
    SocketChannel socketChannel;
    private static final String HOST = "15.164.219.152";
    private static final int PORT = 5001;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_test);

        chattesttxt = findViewById(R.id.chattesttxt);
        chattestedit = findViewById(R.id.chattestEdit);
        chattestBtn = findViewById(R.id.chattestbtn);

        handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socketChannel = SocketChannel.open();
                    socketChannel.configureBlocking(true);
                    socketChannel.connect(new InetSocketAddress(HOST, PORT));
                } catch (Exception ioe) {
                    Log.d("asd", ioe.getMessage() + "a");
                    ioe.printStackTrace();

                }
                checkUpdate.start();
            }
        }).start();

        chattestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String return_msg = chattestedit.getText().toString();
                    if (!TextUtils.isEmpty(return_msg)) {
                        new SendmsgTask().execute(titleTmp+","+"tester2: "+return_msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class SendmsgTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try {
                socketChannel
                        .socket()
                        .getOutputStream()
//                        .write(strings[0].getBytes("EUC-KR")); // 서버로
                        .write(strings[0].getBytes("UTF-8")); // 서버로
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    chattestedit.setText("");
                }
            });
        }
    }

    void receive() {
        while (true) {
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                //서버가 비정상적으로 종료했을 경우 IOException 발생
                Log.e("들어는 오는군","ㅎㅎ");
                int readByteCount = socketChannel.read(byteBuffer); //데이터받기
                Log.d("readByteCount", readByteCount + "");
                Log.e("readByteCount",readByteCount +"");
                //서버가 정상적으로 Socket의 close()를 호출했을 경우
                if (readByteCount == -1) {
                    throw new IOException();
                }

                byteBuffer.flip(); // 문자열로 변환
                Charset charset = Charset.forName("UTF-8");
                data = charset.decode(byteBuffer).toString();
                Log.d("receive", "msg :" + data);
                Log.e("receive","msg = " + data);
                handler.post(showUpdate);
            } catch (IOException e) {
                Log.d("getMsg", e.getMessage() + "");
                try {
                    socketChannel.close();
                    break;
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
        }
    }

    private Thread checkUpdate = new Thread() {

        public void run() {
            try {
                String line;
                Log.e("정상작동 하니","안하는거 같은데");
                receive();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable showUpdate = new Runnable() {

        public void run() {
            String receive = data;
            Log.e("넘어온 값", data);
            chattesttxt.setText(receive);
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            socketChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
