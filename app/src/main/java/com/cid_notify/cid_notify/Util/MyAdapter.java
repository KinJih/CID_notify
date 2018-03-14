package com.cid_notify.cid_notify.Util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.cid_notify.cid_notify.Model.Record;
import com.cid_notify.cid_notify.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements FastScrollRecyclerView.SectionedAdapter,Filterable{
    private ArrayList<Record> mData;
    private ArrayList<Record> mFilterData;
    private String charString="";

    public MyAdapter(ArrayList<Record> mData) {
        this.mData = mData;
        mFilterData = mData;
    }
    public ArrayList<Record> getmFilterData(){
        return mFilterData;//let stickyDecoration can change with Filter
    }
    @NonNull
    @Override
    public String getSectionName(int position) {
        return mFilterData.get(position).getDate();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    mFilterData = mData;
                } else {
                    ArrayList<Record> filteredList = new ArrayList<>();
                    for (Record record : mData) {
                        if (record.getPhoneNum().toLowerCase().contains(charString) || record.getNumber_info().toLowerCase().contains(charString)) {
                            filteredList.add(record);
                        }
                    }
                    mFilterData = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterData;
                filterResults.count=mFilterData.size();
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterData=(ArrayList<Record>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_style, parent, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Record record = mFilterData.get(position);
        holder.setValues(record);

        if (record.getNumber_info().contains("市場")||record.getNumber_info().contains("推銷")){
           holder.mImageView.setImageResource(R.drawable.ic_cid_service_red);
        }else{
            holder.mImageView.setImageResource(R.drawable.ic_cid_service_green);
        }

        String num = record.getPhoneNum().toLowerCase();
        if (num.contains(charString)) {
            int startPos = num.indexOf(charString);
            int endPos = startPos + charString.length();
            Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.mTextNumber.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.mTextNumber.setText(spanString);
        }
        String name = record.getNumber_info().toLowerCase();
        if (name.contains(charString)) {
            int startPos = name.indexOf(charString);
            int endPos = startPos + charString.length();
            Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.mTextPerson.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.mTextPerson.setText(spanString);
        }
    }

    @Override
    public int getItemCount() {
        return mFilterData==null?0:mFilterData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextTime;
        private TextView mTextNumber;
        private TextView mTextPerson;
        private ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextTime = (TextView) v.findViewById(R.id.text_time);
            mTextNumber = (TextView) v.findViewById(R.id.text_phone_number);
            mTextPerson = (TextView) v.findViewById(R.id.text_person);
            mImageView = (ImageView) v.findViewById(R.id.cid_image);
        }

        public void setValues(Record record) {
            mTextNumber.setText(record.getPhoneNum());
            mTextPerson.setText(record.getNumber_info());
            mTextTime.setText(record.getTime());
        }
    }
}