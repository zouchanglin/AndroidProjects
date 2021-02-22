package cn.tim.contentresoverdemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class StuInfoAdapter extends BaseAdapter {
    private List<StudentInfo> stuList;
    private Activity context;

    public StuInfoAdapter(Activity context, List<StudentInfo> stuList) {
        this.stuList = stuList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return stuList.size();
    }

    @Override
    public Object getItem(int position) {
        return stuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.stu_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.tv_item_name);
            viewHolder.tvId = convertView.findViewById(R.id.tv_item_id);
            viewHolder.tvAge = convertView.findViewById(R.id.tv_item_age);
            viewHolder.tvSex = convertView.findViewById(R.id.tv_item_sex);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        StudentInfo studentInfo = stuList.get(position);
        viewHolder.tvId.setText(String.valueOf(studentInfo.id));
        viewHolder.tvName.setText(studentInfo.name);
        viewHolder.tvSex.setText(studentInfo.sex);
        viewHolder.tvAge.setText(String.valueOf(studentInfo.age));
        return convertView;
    }

    // ViewHolder
    static class ViewHolder{
        TextView tvId;
        TextView tvName;
        TextView tvSex;
        TextView tvAge;
    }
}
