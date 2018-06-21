package com.example.one;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by liuhuan1 on 2018/6/7.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {//适配器的泛型必须是RecyclerView.ViewHolder类或者其子类

    List<ContactData> contactDataList;

    LayoutInflater layoutInflater;

    static ContactData contactDataToFragmentTwo=null;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView contactName,phoneNumber;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {//在这里实现对Item里面布局控件的初始化
            super(itemView);
            contactName=itemView.findViewById(R.id.contactName);
            phoneNumber=itemView.findViewById(R.id.phoneNumber);
            linearLayout=itemView.findViewById(R.id.linearLayoutRecyclerViewItem);
            Log.d("huan","ViewHolder对象的生成次数");
        }
    }

    public MyRecyclerViewAdapter(List<ContactData> contactDataList){
        this.contactDataList=contactDataList;
        Log.d("RecyclerAdapter","构造方法");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//在这里返回泛型类型,对控件的监听一般也是写在这
        View view=layoutInflater.from(parent.getContext()).inflate(R.layout.list_itme_view,parent,false);//from里面的参数无法用getContext,其实确实应该使用传过来的视图parent的Context()
        final ViewHolder holder=new ViewHolder(view);//这里就是把Item的布局传过去初始化
        Log.d("huan","检查onCreateViewHolder循环次数");
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Listener","RecyclerViewItemListener");
                int nowPosition=holder.getAdapterPosition();//holder是不会变的，但是它里面的TextView会不断的变化
                contactDataToFragmentTwo=contactDataList.get(nowPosition);
//                Toast.makeText(view.getContext(),"我点击的是"+contactDataList.get(nowPosition).contactName,Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override//这个方法会不断循环，直到完成每个Item的构建
    public void onBindViewHolder(ViewHolder holder, int position) {//在这就把List里面的数据设置到相应的Item里面去
        ContactData contactDataTemp=contactDataList.get(position);
        //这里使用在这不使用全局的TextView变量，而是使用Holder里面的是因为，如果那样就会让后一组覆盖前一组的值好处是方便对每一个Item操作
        holder.contactName.setText(contactDataTemp.contactName);//两个contactName,前一个是控件名，后一个是String变量名
        holder.phoneNumber.setText(contactDataTemp.phoneNumber);//同上
    }

    @Override
    public int getItemCount() {
        return contactDataList.size();
    }
}
