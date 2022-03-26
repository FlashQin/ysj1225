package com.kalacheng.live.component.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.listener.OnItemClickListener;
import com.kalacheng.live.R;

import java.util.List;

public class OpenLiveShareAdpater extends RecyclerView.Adapter {
    private List<String> mList;
    private LayoutInflater mInflater;
    private View.OnClickListener mOnClickListener;
    private OpenLiveShareCallBack callBack;
    private int selection=-1;

    public OpenLiveShareAdpater(Context context, List<String> list) {
        mList = list;
        mInflater = LayoutInflater.from(context);

    }
    //选中
    public void getSelection(int poistion){
        this.selection = poistion;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OpenLiveShareCallBack onItemClickListener) {
        this.callBack = onItemClickListener;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.item_open_live_share, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((Vh)holder).setData(mList.get(position),position);

        ((Vh) holder).mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClick(position,selection);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Vh extends RecyclerView.ViewHolder {

        ImageView mIcon;
        TextView mName;

        public Vh(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);

        }

        void setData(String bean,int position) {

            itemView.setTag(bean);
           /* switch (selection){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }*/
            if (selection == position){
                if (mList.get(selection).equals("1")){
                    mIcon.setBackgroundResource(R.mipmap.qq_two);
                }else if(mList.get(selection).equals("2")){
                    mIcon.setBackgroundResource(R.mipmap.qqzone_two);
                }else if(mList.get(selection).equals("3")){
                    mIcon.setBackgroundResource(R.mipmap.wechat_two);
                }else if(mList.get(selection).equals("4")){
                    mIcon.setBackgroundResource(R.mipmap.wchatfirend_two);
                }
            }else {
                if (bean.equals("1")){
                    mIcon.setBackgroundResource(R.mipmap.qq_default);
                }else if(bean.equals("2")){
                    mIcon.setBackgroundResource(R.mipmap.zone_default);
                }else if(bean.equals("3")){
                    mIcon.setBackgroundResource(R.mipmap.wx_default);
                }else if(bean.equals("4")){
                    mIcon.setBackgroundResource(R.mipmap.frends_default);
                }
            }

        }
    }
    public interface OpenLiveShareCallBack{
        //lastposition 上一次点击的position
        public void onClick(int position,int lastposition);
    }

}
