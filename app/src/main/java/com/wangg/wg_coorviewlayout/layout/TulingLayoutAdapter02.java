package com.wangg.wg_coorviewlayout.layout;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangg.wg_coorviewlayout.R;
import com.wangg.wg_coorviewlayout.bean.MessageContentBean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/18.
 */
// 采用listview进行布局
public class TulingLayoutAdapter02 extends BaseAdapter{

    private  List<MessageContentBean> dataList;
    private MessageContentBean messageContentBean;
    private static final int ITEMCOUNT = 2;// 消息类型的总数

    public TulingLayoutAdapter02(List<MessageContentBean> dataList){
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        messageContentBean = dataList.get(position);
        if(messageContentBean.isComMeg()){
            return IMsgViewType.MSG_ME;
        }else{
            return IMsgViewType.MSG_TO;
        }
    }

    @Override
    public int getViewTypeCount() {
        return ITEMCOUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        boolean isComMsg = dataList.get(position).isComMeg();
        if(convertView == null){
            if(isComMsg){
                convertView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.dialogui_right_item_layout, parent, false);
            }else{
                convertView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.dialogui_left_item_layout, parent, false);
            }

            holder = new ViewHolder();
            holder.right_text = (TextView)convertView.findViewById(R.id.text_right);
            holder.userHead_iv = (ImageView)convertView.findViewById(R.id.iv_userhead);
            holder.isComMsg = isComMsg;
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.right_text.setText(dataList.get(position).getMessagetext());
        holder.right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("wg_log", "点击文字");
            }
        });

        return convertView;
    }

    public static class ViewHolder{
        private TextView right_text;
        private ImageView userHead_iv;
        public boolean isComMsg = true;
    }

    public static interface IMsgViewType{
        int MSG_ME = 1;    //自己发出的消息
        int MSG_TO = 0;   //收到对方发出的消息
    }
}
