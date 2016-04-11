package com.baifan.gridviewandviewpager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baifan.gridviewandviewpager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 九宫格适配器
 * Created by baifan on 16/2/18.
 */
public class GriMenuAdapter extends BaseAdapter{
    /**
     * 数据集
     */
    private List<String> mList = new ArrayList<>();

    private Context mContext;

    public GriMenuAdapter(Context context){
        mContext = context;
    }

    public void setList(List<String> list){
        mList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid, null);
            vh.mTvItem = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        vh.mTvItem.setText(mList.get(position));

        return convertView;
    }

    class ViewHolder{
        TextView mTvItem;
    }
}
