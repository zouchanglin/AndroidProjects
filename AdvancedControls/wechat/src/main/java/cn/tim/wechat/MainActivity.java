package cn.tim.wechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.main_lv);
        List<ChatMessage> chatMessages = Arrays.asList(
                new ChatMessage(1, 2, "Tim", "08:20", "I'm Tim.", true),
                new ChatMessage(1, 2, "Tim", "08:25", "Jone, how are you?", true),
                new ChatMessage(2, 1, "Jone", "08:30", "I'm fine, thinks", false),
                new ChatMessage(1, 2, "Tim", "08:31", "No thinks.", true),
                new ChatMessage(2, 1, "Jone", "08:32", "What can I do for you ?", false),
                new ChatMessage(1, 2, "Tim", "08:59", "Please give me some money.", true)
        );
        listView.setAdapter(new ChatMessageAdapter(chatMessages, MainActivity.this));

    }

    static class ChatMessage {
        public int mId;
        public int mFriendId;
        public String mName;
        public String mDate;
        public String mContent;
        public boolean mIsComeMessage;

        public ChatMessage(int mId, int mFriendId,
                           String mName, String mDate,
                           String mContent, boolean mIsComeMessage) {
            this.mId = mId;
            this.mFriendId = mFriendId;
            this.mName = mName;
            this.mDate = mDate;
            this.mContent = mContent;
            this.mIsComeMessage = mIsComeMessage;
        }
    }

    static class ChatMessageAdapter extends BaseAdapter {
        List<ChatMessage> chatMessages;
        Context context;

        interface IMessageViewType {
            int COM_MESSAGE = 1;
            int TO_MESSAGE = 2;
        }

        public ChatMessageAdapter(List<ChatMessage> chatMessages, Context context) {
            this.chatMessages = chatMessages;
            this.context = context;
        }

        @Override
        public int getCount() {
            return chatMessages.size();
        }

        @Override
        public Object getItem(int position) {
            return chatMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ChatMessage chatMessage = chatMessages.get(position);
            if(convertView == null){
                if(chatMessage.mIsComeMessage){
                    convertView = layoutInflater.inflate(R.layout.item_left_chat, null);
                }else{
                    convertView = layoutInflater.inflate(R.layout.item_right_chat, null);
                }
                TextView timeTv = convertView.findViewById(R.id.time_tv);
                ImageView iconIv = convertView.findViewById(R.id.icon_iv);
                TextView nameTv = convertView.findViewById(R.id.name_tv);
                TextView contentTv = convertView.findViewById(R.id.content_tv);
                timeTv.setText(chatMessage.mDate);
                nameTv.setText(chatMessage.mName);
                contentTv.setText(chatMessage.mContent);
            }
            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            ChatMessage chatMessage = chatMessages.get(position);
            return chatMessage.mIsComeMessage ? IMessageViewType.COM_MESSAGE : IMessageViewType.TO_MESSAGE;
        }

        @Override
        public int getViewTypeCount() {
            return 2; // IMessageViewType 两种类型
        }
    }
}
