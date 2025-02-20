package com.example.kortabitarteupategiak;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kortabitarteupategiak.databinding.FragmentMenuaBinding;
import viewmodel.UserViewModel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.Manifest;

public class Menua extends Fragment {

    NavController navController;
    UserViewModel userViewModel;
    Executor executor;
    FragmentMenuaBinding binding;

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean isGranted) {
                    if (isGranted) {
                        openLocationInMap();
                    } else {
                        Toast.makeText(getContext(), "Ubikazio baimenak beharrezkoak dira mapa zabaltzeko", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    public Menua() {
        // Required empty public constructor
    }

    public static Menua newInstance(String param1, String param2) {
        Menua fragment = new Menua();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        executor = Executors.newSingleThreadExecutor();

        Bundle bundle = getArguments();
        String usr = bundle.getString("username");

        assert binding.katalogoBtn != null;
        binding.katalogoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_menua_to_katalogoRecycler);
            }
        });

        binding.locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    openLocationInMap();
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                }
            }
        });

        binding.kontuaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_menua_to_accountSettings, bundle);
            }
        });

        if (binding != null) {
            String username = bundle.getString("username");
            if (username != null) {
                binding.ongiEtorriText.setText("Ongi etorri, " + username);
            }
        } else {
            Toast.makeText(getContext(), "Errore bat gertatu da", Toast.LENGTH_SHORT).show();
        }
    }

    private void openLocationInMap() {
        String locationUrl = "http://maps.google.com/maps?q=" + "bierzo";
        Uri getLoc = Uri.parse(locationUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, getLoc);
        Log.d("URL_DEBUG", getLoc.toString());
        startActivity(intent);
    }
}
