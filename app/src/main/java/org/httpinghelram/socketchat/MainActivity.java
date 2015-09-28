package org.httpinghelram.socketchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String SERVER_URL = "http://ddbe-chat.eu-gb.mybluemix.net/";

    private Socket mSocket;
    private EditText mInputMessageView;
    //private RecyclerView mRecyclerview;
    //private TextView tv_text;
    private Button btn_sendMessage;
    private CustomChatAdapter mAdapter;

    private ArrayList<ChatData> data = new ArrayList<>();

    {
        try {
            mSocket = IO.socket(SERVER_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputMessageView = (EditText) findViewById(R.id.mInputMessageView);
        //tv_text = (TextView) findViewById(R.id.tv_text);
        RecyclerView mRecyclerview = (RecyclerView) findViewById(R.id.rv_chatmessages);
        //data = new ArrayList<>();
        mAdapter = new CustomChatAdapter(data);
        mRecyclerview.setAdapter(mAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mRecyclerview.setLayoutManager(mLayoutManager);

        btn_sendMessage = (Button) findViewById(R.id.btn_sendMessage);

        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });
        mSocket.on("package", onNewMessage); //package = definded by server
        mSocket.connect();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("chatlist", data);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<ChatData> mydata = savedInstanceState.getParcelableArrayList("chatlist");
        data.addAll(mydata);
        mAdapter.updateData();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    private void attemptSend() {
        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            return;
        }

        mInputMessageView.setText("");
        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("text", message);
            jsonMessage.put("name", "Pieter");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.send(jsonMessage);
    }

    private void addMessage(String username, String message, boolean ownText) {
        //tv_text.setText(tv_text.getText() + "\n" + username + ": " + message);
        ChatData chatData = new ChatData(ownText, message, username);
        data.add(0, chatData);
        mAdapter.updateData();
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("user");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }

                    // add the message to view
                    boolean ownText = username.equals("Pieter");
                    addMessage(username, message, ownText);
                }
            });
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("package", onNewMessage);
    }
}
