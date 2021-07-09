package com.example.home_launcher;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemData extends RecyclerView.Adapter<ItemData.Items> {
    Context context;
    List<Item> itemList;
    private ItemClickListener listener;

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public ItemData(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemData.Items onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, null);
        return new Items(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemData.Items holder, final int position) {

        holder.tv_name.setText(itemList.get(position).getName());
        holder.tv_pack.setText(itemList.get(position).getPack());
        holder.tv_code.setText(itemList.get(position).getV_code());
        holder.tv_versionname.setText(itemList.get(position).getV_name());
        holder.icon.setImageDrawable(itemList.get(position).getIcon());


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onItemClick(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<Item> filterList(String text) {

        List<Item> tempAllList = itemList;

        List<Item> filetredList = new ArrayList<Item>();

        if (!TextUtils.isEmpty(text)) {

            for (Item retailer : tempAllList) {
                if (retailer.getName().toLowerCase().contains(text)) {
                    filetredList.add(retailer);
                }
            }

            if (itemList.size() >= 1) {

                tempAllList = filetredList;

            }
        } else {
            tempAllList = itemList;

        }


        return tempAllList;
    }


    public class Items extends RecyclerView.ViewHolder {

        TextView tv_name, tv_pack, tv_code, tv_versionname;
        ImageView icon;
        LinearLayout linearLayout;

        public Items(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.lin_layout);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_pack = itemView.findViewById(R.id.tv_pkgname);
            tv_code = itemView.findViewById(R.id.tv_code);
            tv_versionname = itemView.findViewById(R.id.tv_versionname);
            icon = itemView.findViewById(R.id.iv_icon);

        }
    }

    public interface ItemClickListener{
        //Achieve the click method, passing the subscript.
        void onItemClick(int position);
    }
}