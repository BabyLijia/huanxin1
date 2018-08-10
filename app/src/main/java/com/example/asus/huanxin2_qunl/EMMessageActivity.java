package com.example.asus.huanxin2_qunl;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;


public class EMMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_txt;
    private Button bt_go;
    private EditText ed_neir;
    private Handler handler;
    private EMMessageListener msgListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emmessage);
        initView();
        init();
    }

    private void init() {
        //收到消息
        //收到透传消息
        //消息状态变动
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                String result = messages.get(0).getBody().toString();
                final String msgReceived = result.substring(5, result.length() - 1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EMMessageActivity.this, msgReceived, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);

    }

    private void initView() {
        ed_txt = (EditText) findViewById(R.id.ed_txt);
        bt_go = (Button) findViewById(R.id.bt_go);

        bt_go.setOnClickListener(this);
        ed_neir = (EditText) findViewById(R.id.ed_neir);
        ed_neir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_go:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String txt = ed_txt.getText().toString().trim();
        if (TextUtils.isEmpty(txt)) {
            Toast.makeText(this, "txt不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage txtSendMessage = EMMessage.createTxtSendMessage(ed_neir.getText().toString(),
                ed_txt.getText().toString());
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(txtSendMessage);
        Toast.makeText(this, "执行了", Toast.LENGTH_SHORT).show();
    }
}
