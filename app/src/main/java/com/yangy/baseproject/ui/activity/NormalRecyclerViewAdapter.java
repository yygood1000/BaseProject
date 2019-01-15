package com.yangy.baseproject.ui.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangy.baseproject.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NormalRecyclerViewAdapter extends RecyclerView.Adapter<NormalRecyclerViewAdapter.NormalTextViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ArrayList<String> mTitles;

    public NormalRecyclerViewAdapter(Context context, ArrayList<String> titles) {
        mContext = context;
        mTitles = titles;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_text, parent, false));
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        holder.tv.setText(mTitles.get(position));
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    class NormalTextViewHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public NormalTextViewHolder(View view) {
            super(view);
            tv=(TextView) view.findViewById(R.id.text_view);
        }

    }
}