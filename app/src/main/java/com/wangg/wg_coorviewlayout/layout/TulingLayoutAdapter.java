package com.wangg.wg_coorviewlayout.layout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangg.wg_coorviewlayout.R;
import com.wangg.wg_coorviewlayout.bean.MessageContentBean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
//采用RecyclerView  布局
public class TulingLayoutAdapter extends RecyclerView.Adapter<TulingLayoutAdapter.MyViewHolder>{

    private List<MessageContentBean> dataList;
    private MessageContentBean messageContentBean;

    public  TulingLayoutAdapter(List<MessageContentBean> dataList){
        this.dataList = dataList;
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                if(messageContentBean.isComMeg()){
                    View view = LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.dialogui_right_item_layout, parent, false);
                    MyViewHolder holder = new MyViewHolder(view);
                    return holder;
                }else{
                    View view = LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.dialogui_left_item_layout, parent, false);
                    MyViewHolder holder = new MyViewHolder(view);
                    return holder;
                }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.right_text.setText(dataList.get(position).getMessagetext());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private  TextView right_text;
        private  ImageView userHead_iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            userHead_iv =  (ImageView) itemView.findViewById(R.id.iv_userhead);
            right_text =  (TextView) itemView.findViewById(R.id.text_right);
        }
    }

    public static interface IMsgViewType{
        int MSG_ME = 1;    //自己发出的消息
        int MSG_TO = 0;   //收到对方发出的消息
    }



}
