package com.example.home_launcher;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {

    private Context context;

    public ItemAdapter(@NonNull Context context, int resource, List<Item> items) {
        super(context, resource);
        this.context = context;
    }


    private class ViewHolder {

        TextView tv_name, tv_pack;
        ImageView icon;
    }

    @NonNull
    public View getView(int position, View converterview, @NonNull ViewGroup parent) {

        ViewHolder holder;
        Item rowItem=getItem(position);
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (converterview==null){
            converterview=layoutInflater.inflate(R.layout.item,null);
            holder=new ViewHolder();
            holder.tv_name=converterview.findViewById(R.id.tv_name);
            holder.tv_pack=converterview.findViewById(R.id.tv_pkgname);
            holder.icon=converterview.findViewById(R.id.iv_icon);
            converterview.setTag(holder);
        }else {
            holder=(ViewHolder) converterview.getTag();
            if (rowItem!=null){
                holder.tv_name.setText(rowItem.getName());
                holder.tv_pack.setText(rowItem.getPack());
                holder.icon.setImageDrawable(rowItem.getIcon());
            }
        }
        return converterview;
    }
}
