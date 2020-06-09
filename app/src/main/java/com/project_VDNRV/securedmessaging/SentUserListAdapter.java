package com.project_VDNRV.securedmessaging;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SentUserListAdapter extends RecyclerView.Adapter<SentUserListAdapter.MyViewHolder> {



    private LayoutInflater mInflater;
    private List<String> userList = new ArrayList<String>();
    private SentUserListAdapter.OnItemClickListener itemClickListener;



    public SentUserListAdapter(List<String> userList, OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.userList = userList;
    }



    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }


    @Override
    public SentUserListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.activity_user_name, parent, false);

        return new SentUserListAdapter.MyViewHolder(view,itemClickListener);
    }





    @Override
    public void onBindViewHolder(SentUserListAdapter.MyViewHolder holder, final int listPosition) {

        String name =  userList.get(listPosition);
        holder.item.setText(name);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView item;
        SentUserListAdapter.OnItemClickListener itemClickListener;
        public MyViewHolder(View itemView, SentUserListAdapter.OnItemClickListener itemClickListener) {
            super(itemView);
            item = (TextView)itemView.findViewById(R.id.userName);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onitemClickListener(getAdapterPosition());

        }
    }
    public interface OnItemClickListener{
        void onitemClickListener(int position);
    }
}
