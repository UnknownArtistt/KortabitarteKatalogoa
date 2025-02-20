package com.example.kortabitarteupategiak;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kortabitarteupategiak.databinding.FragmentKatalogoViewHolderBinding;

class KatalogoViewHolder extends RecyclerView.ViewHolder {
    final FragmentKatalogoViewHolderBinding binding;
    public KatalogoViewHolder(FragmentKatalogoViewHolderBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}