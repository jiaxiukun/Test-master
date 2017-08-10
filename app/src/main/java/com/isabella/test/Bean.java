package com.isabella.test;

/**
 * description
 * Created by 张芸艳 on 2017/7/20.
 */

public class Bean {
    private  String content; //条目内容
    private boolean temp=false; //是否是组的第一个标签
    private String group; //组名

    public Bean(String content,String group, boolean temp) {
        this.content = content;
        this.temp = temp;
        this.group=group;
    }

    public Bean() {
    }

    public String getContent() {
        return content;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isTemp() {
        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }
}
