package cn.tim.imooc_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.List;

public class ResponseDataAdapter extends BaseAdapter {
    private static final String TAG = "ResponseDataAdapter";
    private List<ResponseData> responseDataList;
    private Context context;

    public ResponseDataAdapter(List<ResponseData> responseDataList, Context context) {
        this.responseDataList = responseDataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return responseDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return responseDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_imooc_list, null);
            viewHolder = new ViewHolder();
            viewHolder.iconIv = convertView.findViewById(R.id.icon_iv_item);
            viewHolder.titleTv = convertView.findViewById(R.id.title_tv_item);
            viewHolder.contentTv = convertView.findViewById(R.id.content_tv_item);
            viewHolder.learnTv = convertView.findViewById(R.id.learn_tv_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ResponseData responseData = responseDataList.get(position);
        //viewHolder.iconIv.setImageURI(new Uri(""));
        viewHolder.iconIv.setImageUrl(responseData.picBig);
        viewHolder.titleTv.setText(responseData.name);
        viewHolder.contentTv.setText(responseData.description);
        viewHolder.learnTv.setText(responseData.learner + "人学习");
        return convertView;
    }

    private static class ViewHolder {
        public SmartImageView iconIv;
        public TextView titleTv;
        public TextView contentTv;
        public TextView learnTv;
    }
}