package com.example.kortabitarteupategiak;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kortabitarteupategiak.databinding.FragmentKatalogoViewHolderBinding;
import com.example.kortabitarteupategiak.databinding.FragmentKatalogoRecyclerBinding;

import java.util.List;

import javax.annotation.Nullable;

import model.Produktua;
import viewmodel.ProduktuaViewModel;

public class KatalogoRecycler extends Fragment {

    FragmentKatalogoRecyclerBinding binding;
    private NavController navController;
    private ProduktuaViewModel produktuaViewModel;

    public KatalogoRecycler() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentKatalogoRecyclerBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        produktuaViewModel = new ViewModelProvider(requireActivity()).get(ProduktuaViewModel.class);
        navController = Navigation.findNavController(view);
        KatalogoAdapter katalogoAdapter = new KatalogoAdapter();

        binding.ProductRecycler.setAdapter(katalogoAdapter);
        binding.ProductRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        produktuaViewModel.get().observe(getViewLifecycleOwner(), new Observer<List<Produktua>>() {
            @Override
            public void onChanged(List<Produktua> produktuak) {
                katalogoAdapter.establishList(produktuak);
            }
        });
    }

    class KatalogoViewHolder extends RecyclerView.ViewHolder {
        private final FragmentKatalogoViewHolderBinding binding;
        public KatalogoViewHolder(FragmentKatalogoViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class KatalogoAdapter extends RecyclerView.Adapter<KatalogoViewHolder> {
        List<Produktua> produktuak;

        @NonNull
        @Override
        public KatalogoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new KatalogoViewHolder(FragmentKatalogoViewHolderBinding.inflate(getLayoutInflater(), parent, false));
        }

        public Produktua getProduktua(int position) {
            return produktuak.get(position);
        }

        @Override
        public void onBindViewHolder(@NonNull KatalogoViewHolder holder, int position) {
            Produktua produktua = produktuak.get(position);

            Bitmap bitmap= BitmapFactory.decodeResource(getActivity().getResources(), produktua.getImagePath());
            holder.binding.productImg.setImageBitmap(bitmap);
            holder.binding.prIzena.setText(produktua.getIzena());

            holder.binding.infoProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("produktua", produktua);

                    navController.navigate(R.id.action_katalogoRecycler_to_produktuXehetasunak, bundle);
                }
            });

        }

        @Override
        public int getItemCount() {
            return produktuak != null ? produktuak.size() : 0;
        }

        public void establishList(List<Produktua> produktuak) {
            this.produktuak = produktuak;
            notifyDataSetChanged();
        }
    }
}