package com.an1metall.gb_a_database;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.an1metall.gb_a_database.databinding.RvItemBinding;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolder> {

    List<Purchase> items;

    public RVAdapter(List<Purchase> items) {
        this.items = items;
    }

    @Override
    public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RvItemBinding binding = RvItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RVViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RVViewHolder holder, int position) {;
        holder.binding.setPurchase(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RVViewHolder extends RecyclerView.ViewHolder {

        RvItemBinding binding;

        private RVViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
        
    }
}
