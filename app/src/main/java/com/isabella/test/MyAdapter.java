package com.isabella.test;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * description
 * Created by 张芸艳 on 2017/7/20.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    List<Bean> list;

    public MyAdapter(Context context, List<Bean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.popup_item, null);
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
            holder.item = (TextView) convertView.findViewById(R.id.item_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(list.get(position).getContent());
        //如果group不为空说明是悬浮标签的popupWindow
        if (list.get(position).getGroup() != null) {
            //标签是组中第一个的时候
            if (list.get(position).isTemp()) {
                //设置为显示状态
                holder.item.setText(list.get(position).getGroup());
                if (holder.item.getVisibility() != View.VISIBLE) {
                    holder.item.setVisibility(View.VISIBLE);
                }
            } else {
                if (holder.item.getVisibility() != View.GONE) {
                    holder.item.setVisibility(View.GONE);
                }
            }
            //否则就说明是simple的popupWindow , 就把item全部隐藏
        } else {
            if (holder.item.getVisibility() != View.GONE) {
                holder.item.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tv, item;
    }
}
