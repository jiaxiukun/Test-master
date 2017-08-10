package com.isabella.test;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Bean> list = new ArrayList<>();
    private Button bt1;
    private Button bt2;
    private PopupWindow pw;
    private MyAdapter adapter;
    private TextView textView;
    private int i;
    private View view;
    private int heightPixels;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //弹出simple popupWindow
        bt1 = (Button) findViewById(R.id.button);
        //弹出悬浮标签popupWindow
        bt2 = (Button) findViewById(R.id.button1);
        //获取屏幕高度
        heightPixels = getResources().getDisplayMetrics().heightPixels;
        //默认设置popupWindow的高度为屏幕的一半(用于simple popupWindow)
        i = heightPixels / 2;
        //初始化popupWindow
        initPopuWindow();
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //默认listView滑动到顶部
                //因为布局复用,如果打开 悬浮标签的,滑动到第一个可见条目是6的,那么再打开 simple的时候,也会在第一可见条目为6的地方,这是不合适的
                lv.setSelection(0);
                //屏幕高度的二分之一
                i = heightPixels / 2;
                //设置为popupWindow的高度
                pw.setHeight(i);
                //初始化simple数据
                init();
                //设置 最上方显示的 悬浮标签为隐藏状态
                if (textView.getVisibility() != View.GONE) {
                    textView.setVisibility(View.GONE);
                }
                //设置view的背景 (圆角)
                view.setBackgroundResource(R.drawable.sp_popup_1);
                //展示在界面上, 在屏幕最下方
                pw.showAtLocation(MainActivity.this.findViewById(R.id.line1), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setSelection(0);
                //屏幕高度的五分之三
                i = heightPixels * 3 / 5;
                pw.setHeight(i);
                //初始化悬浮标签的数据
                initData();
                //默认为显示状态
                if (textView.getVisibility() != View.VISIBLE) {
                    textView.setVisibility(View.VISIBLE);
                }
                view.setBackgroundResource(R.drawable.sp_popup);
                pw.showAtLocation(MainActivity.this.findViewById(R.id.line1), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    List<Integer> intList = new ArrayList<>();
    int a=0;

    private void initPopuWindow() {
        /**
         * 第一个参数是view
         * 第二个参数是PopupWindow的宽
         * 第三个参数是PopupWindow的高
         */


        view = View.inflate(this, R.layout.popup_view, null);
        view.setBackgroundResource(R.drawable.sp_popup);
        textView = (TextView) view.findViewById(R.id.textview);
        Button pope_bt = (Button) view.findViewById(R.id.popup_bt);
        lv = (ListView) view.findViewById(R.id.lv);


        adapter = new MyAdapter(this, list);
        lv.setAdapter(adapter);

        //创建PopupWindow

        pw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, i);
        pw.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.popup_color)));
        //点击PopupWindow的外部消失PopupWindow
        pw.setOutsideTouchable(true);
        //设置动画
        pw.setAnimationStyle(R.style.Animation);
        //点击返回键让PopupWindow消失
        pw.setFocusable(true);
        pope_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pw.dismiss();

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取当前点击条目
                Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                pw.dismiss();
            }
        });
        //滑动监听
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }


            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //当总条目不为0的时候说明有数据
                if (totalItemCount != 0) {
                    //当传入的标签名不为null说明是悬浮标签的popupWindow,走下面的代码块
                    if (list.get(firstVisibleItem).getGroup() != null) {
                        //设置为当前标签名
                        textView.setText(list.get(view.getFirstVisiblePosition()).getGroup());
                        //当不是第一条时
                        if (!list.get(view.getFirstVisiblePosition()).isTemp()) {
                            if (textView.getVisibility() != View.VISIBLE) {
                                textView.setVisibility(View.VISIBLE);
                            }


                            if (a != firstVisibleItem) {
                                if (intList.size() != 0) {
                                    a = firstVisibleItem;
                                    //判断是否是第一条的数据,因为下方改变了temp的值
                                    //所以需要这个值来判断
                                    //用于在数据倒着滑的时候显示第一条的标签
                                    for (int i1 = 0; i1 < intList.size(); i1++) {
                                        Log.d("MainActivity", "i1:" + intList.get(i1));
                                        Log.d("MainActivity", "lv.getFirstVisiblePosition():" + lv.getFirstVisiblePosition());
                                        Log.d("MainActivity", "intList.get(i1) == lv.getFirstVisiblePosition():" + (intList.get(i1) == lv.getFirstVisiblePosition()));
                                        if (intList.get(i1) == lv.getFirstVisiblePosition()) {

                                            list.get(lv.getFirstVisiblePosition()).setTemp(true);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }


                            }

                        } else {
                            //标记位,用于标记同一个条目只能走一遍
                            if (a != firstVisibleItem) {
                                //把是true的position添加到iniList里面,方便返回去的时候查询使用
                                intList.add(view.getFirstVisiblePosition());
                                Log.d("MainActivity", "list.get(lv.getFirstVisiblePosition()).isTemp():" + list.get(view.getFirstVisiblePosition()).isTemp());
                                //用于数据在正着滑的时候隐藏标签
                                //把temp设置为false  因为这里变成false,没有判断的依据了.所以需要添加一个新的标记 iniList
                                list.get(view.getFirstVisiblePosition()).setTemp(false);
                                a = firstVisibleItem;
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        });


    }

    //初始化有悬浮标签 popupwindow listview数据
    private void initData() {
        //先清空
        list.clear();
        //模拟数据
        //设置为true表示为每个相同标签的第一条数据
        // 三个参数依次为 条目显示的内容 条目标签内容 是否为该标签的第一条数据
        for (int i = 0; i < 5; i++) {
            list.add(new Bean("Test" + i, "投诉", false));
        }
        list.add(new Bean("Test" + 5, "意见", true));
        for (int i = 6; i < 13; i++) {
            list.add(new Bean("Test" + i, "意见", false));
        }
        list.add(new Bean("Test" + 13, "举报", true));
        for (int i = 14; i < 21; i++) {
            list.add(new Bean("Test" + i, "举报", false));
        }
        //必须刷新
        adapter.notifyDataSetChanged();
    }

    //初始化 simple popupwindow listview 数据
    private void init() {
        //必须先清空,因为用的是同一个集合,如果不清空会造成数据的重复  如果不需要复用布局,可以写两个集合,就不需要清空了
        list.clear();
        for (int i = 0; i < 10; i++) {
            list.add(new Bean("Test" + i, null, false));
        }
        //必须刷新 ,因为复用布局和设置popupwindow属性,所以需要先设置适配器再添加数据,所以需要刷新一下,(网络请求同理)注意adapter中返回的count值
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pw != null) {
            pw = null;
        }
    }
}
