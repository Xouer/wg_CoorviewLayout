package com.wangg.wg_coorviewlayout.bean;

/**
 * Created by Administrator on 2016/3/16.
 */
public class MessageContentBean {

    private String messagetext;   //消息内容
    private boolean isComMeg = true;   //是否是对方发来的信息

    public MessageContentBean(String messagetest, boolean isComMeg){
        this.messagetext = messagetest;
        this.isComMeg = isComMeg;
    }

    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }

    public boolean isComMeg() {
        return isComMeg;
    }

    public void setComMeg(boolean comMeg) {
        isComMeg = comMeg;
    }
}
