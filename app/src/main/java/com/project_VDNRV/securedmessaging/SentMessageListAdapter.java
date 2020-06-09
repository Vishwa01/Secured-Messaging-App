package com.project_VDNRV.securedmessaging;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SentMessageListAdapter extends RecyclerView.Adapter<SentMessageListAdapter.MyViewHolder> {




    List<Message> messagePack = new ArrayList<Message>();

    private SentMessageListAdapter.OnItemClickListener itemClickListener;


    public SentMessageListAdapter (List<Message> messagePack, OnItemClickListener itemClickListener )
    {
        this.messagePack = messagePack;
        this.itemClickListener = itemClickListener;
    }
    private LayoutInflater mInflater;


    @NonNull
    @Override
    public SentMessageListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mInflater = LayoutInflater.from(parent.getContext());
        final View view = mInflater.inflate(R.layout.activity_recieved_meassage_layout, parent, false);
        return new SentMessageListAdapter.MyViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SentMessageListAdapter.MyViewHolder holder, int position) {
        Message name =  messagePack.get(position);
        holder.item1.setText(name.MESSAGE);
        holder.item2.setText(name.QUESTION1);
        if(name.STATUS==false)
        {
            holder.item1.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return messagePack.size();
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView item1, item2;
        SentMessageListAdapter.OnItemClickListener itemClickListener;
        public MyViewHolder(View itemView, SentMessageListAdapter.OnItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
            item1 = itemView.findViewById(R.id.user);
            item2 = itemView.findViewById(R.id.que);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition());
        }


    }
    public interface OnItemClickListener  {
        void onItemClick(int position);
    }
}
