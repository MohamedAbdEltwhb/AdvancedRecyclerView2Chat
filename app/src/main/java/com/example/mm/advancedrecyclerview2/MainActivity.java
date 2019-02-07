package com.example.mm.advancedrecyclerview2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ArrayList<MessageModel> chatlist = null;
    ChatAdapter adapter = null;
    RecyclerView chat_list;
    Button receive_button, send_button;
//    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chat_list = (RecyclerView) findViewById(R.id.chat_list);
        send_button = (Button) findViewById(R.id.send_button);
        receive_button = (Button) findViewById(R.id.receive_button);
        send_button.setOnClickListener(this);
        receive_button.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        /*
         * When stackFromEnd is true the list fills its content
         * starting from the bottom of the view.
         */
        layoutManager.setStackFromEnd(true);
        chat_list.setLayoutManager(layoutManager);
        chatlist = new ArrayList<>();
        adapter = new ChatAdapter(this, chatlist);
        chat_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        EditText editText = findViewById(R.id.texttosend);
        String text = null;
        if (editText != null) {
            text = editText.getText().toString().trim();
        }

//        count will give us the position(last) where we will insert item
        int count = adapter.getItemCount();
        if (text != null && text.length() != 0) {
            MessageModel model;
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
            String time = dateFormat.format(calendar.getTime());
            model = new MessageModel();
            model.setMessage(text);
            model.setSender(true);
            model.setTime(time);

            if (id == R.id.send_button) {
                model.setSender(true);
            } else if (id == R.id.receive_button) {
                model.setSender(false);
            }

            chatlist.add(model);
            adapter.notifyItemInserted(count);
            /*
             * void scrollToPosition(int position) tells layout manager to scroll recyclerView
             * to given position
             */
            chat_list.scrollToPosition(count);
            editText.setText("");
        }
    }
}
