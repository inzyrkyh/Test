package com.test.test.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.test.R;

import java.util.List;

/**
 * Created by Hyk on 2015/11/23.
 */
public class GroupAdapter extends BaseAdapter {

    private Context context;

    private List<Group> list;

    public GroupAdapter(Context context, List<Group> list) {

        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {


        ViewHolder holder;
        if (convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.group_item, null);
            holder=new ViewHolder();

            convertView.setTag(holder);

            holder.groupName=(TextView) convertView.findViewById(R.id.id_group_name);

        }
        else{
            holder=(ViewHolder) convertView.getTag();
        }
        holder.groupName.setText(list.get(position).GetGName());

        return convertView;
    }

    static class ViewHolder {
        TextView groupName;
        ImageView groupIcon;
    }
}
