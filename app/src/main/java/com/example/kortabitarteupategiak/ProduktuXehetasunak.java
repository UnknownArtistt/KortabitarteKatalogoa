package com.example.kortabitarteupategiak;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kortabitarteupategiak.databinding.FragmentKatalogoRecyclerBinding;
import com.example.kortabitarteupategiak.databinding.FragmentProduktuXehetasunakBinding;

import model.Produktua;
import viewmodel.ProduktuaViewModel;

public class ProduktuXehetasunak extends Fragment {

    FragmentProduktuXehetasunakBinding binding;
    NavController navController;
    ProduktuaViewModel produktuaViewModel;

    public ProduktuXehetasunak() {
        // Required empty public constructor
    }

    public static ProduktuXehetasunak newInstance(String param1, String param2) {
        ProduktuXehetasunak fragment = new ProduktuXehetasunak();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentProduktuXehetasunakBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        produktuaViewModel = new ViewModelProvider(requireActivity()).get(ProduktuaViewModel.class);
        navController = Navigation.findNavController(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Produktua produktua = (Produktua) bundle.getSerializable("produktua");

            if (produktua != null) {
                Bitmap bitmap= BitmapFactory.decodeResource(getActivity().getResources(), produktua.getImagePath());
                binding.imageView3.setImageBitmap(bitmap);
                binding.nombrePrd.setText(produktua.getIzena());
                binding.ekoizlePrd.setText(produktua.getEkoizlea());
                binding.descPrd.setText(produktua.getDeskripzioa());
                binding.prezPrd.setText(String.valueOf(produktua.getPrezioa()) + "€");
            } else {
                Toast.makeText(getContext(), "Ez da produktua aurkitu", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Ez da produktua aurkitu", Toast.LENGTH_SHORT).show();
        }

        binding.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String produktuaIzena = binding.nombrePrd.getText().toString();
                String produktuaEkoizlea = binding.ekoizlePrd.getText().toString();
                String produktuaDeskripzioa = binding.descPrd.getText().toString();
                String produktuaPrezioa = binding.prezPrd.getText().toString();

                String shareText = "KORTABITARTE UPATEGIAK\n" +
                        "----------------------------------------------\n" +
                        "Izena: " + produktuaIzena + "\n" +
                        "Ekoizlea: " + produktuaEkoizlea + "\n" +
                        "Deskripzioa: " + produktuaDeskripzioa + "\n" +
                        "Prezioa: " + produktuaPrezioa + "\n\n" +
                        "Egin zure eskaria!";

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, shareText);
                intent.setType("text/plain");

                Intent prt = Intent.createChooser(intent, null);
                startActivity(prt);
            }
        });
    }
}