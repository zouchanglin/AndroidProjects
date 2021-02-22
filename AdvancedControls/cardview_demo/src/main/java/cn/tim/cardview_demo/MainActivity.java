package cn.tim.cardview_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("CardView测试");
        setContentView(R.layout.activity_main);
        ListView msgListView = findViewById(R.id.id_listview_msg_list);

        List<Msg> messageList = Arrays.asList(
                new Msg(1, R.drawable.img01, "如何才能不错过人工智能的时代?", "下一个时代就是机器学习的时代，与你一起预见未来!"),
                new Msg(2, R.drawable.img02, "关于你的面试、实习心路历程", "奖品丰富，更设有参与奖，随机抽取5名幸运用户，获得付费面试课程中的任意一门!"),
                new Msg(3, R.drawable.img03, "狗粮不是你想吃，就能吃的!", "你的朋友圈开始了吗？一半秀恩爱，一半扮感伤!不怕，陪你坚强地走下去!"),
                new Msg(4, R.drawable.img04, "前端跳槽面试那些事儿~", "工作有几年了，项目偏简单有点拿不出手怎么办？目前还没毕业，正在自学前端，请问可以找到一份前端工作吗，我该怎么办？"),
                new Msg(5, R.drawable.img05, "图解程序员怎么过七夕?", "图解程序员怎么过七夕，哈哈哈哈，活该单身25年!")
        );

        msgListView.setAdapter(new MoocAdapter(messageList));
    }

    class MoocAdapter extends BaseAdapter {
        private List<Msg> msgList;

        public MoocAdapter(List<Msg> msgList) {
            this.msgList = msgList;
        }

        @Override
        public int getCount() {
            return msgList.size();
        }

        @Override
        public Object getItem(int position) {
            return msgList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(MainActivity.this, R.layout.item_msg, null);
                //convertView = getLayoutInflater().inflate(R.layout.item_msg, null);
                viewHolder = new ViewHolder();
                viewHolder.iconImageView = convertView.findViewById(R.id.item_icon_iv);
                viewHolder.titleTextView = convertView.findViewById(R.id.item_title_tv);
                viewHolder.contentTextView = convertView.findViewById(R.id.item_content_tv);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Msg msg = msgList.get(position);
            viewHolder.contentTextView.setText(msg.getContent());
            viewHolder.titleTextView.setText(msg.getTitle());
            viewHolder.iconImageView.setImageResource(msg.getImgResId());

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView iconImageView;
        TextView titleTextView;
        TextView contentTextView;
    }
}

class Msg {
    private int id;
    private int imgResId;
    private String title;
    private String content;

    public Msg(int id, int imgResId, String title, String content) {
        this.id = id;
        this.imgResId = imgResId;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}