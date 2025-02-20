package com.example.kortabitarteupategiak;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kortabitarteupategiak.databinding.FragmentCodeVerificationBinding;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import viewmodel.UserViewModel;

public class CodeVerification extends Fragment {

    NavController navController;
    UserViewModel userViewModel;
    Executor executor;
    FragmentCodeVerificationBinding binding;

    private int generatedVerificationCode;
    private String enteredCode;

    public CodeVerification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentCodeVerificationBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        executor = Executors.newSingleThreadExecutor();

        Bundle bundle = getArguments();
        String usr = bundle.getString("username");

        binding.atzeraBotoia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_codeVerification_to_login);
            }
        });

        Handler mainHandler = new Handler(getContext().getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String userEmail = userViewModel.getEmail(usr);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        generateAndSendVerificationCode(userEmail, mainHandler);
                    }
                });
            }
        });

        binding.berbidaliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        String emaila = userViewModel.getEmail(usr);
                        generateAndSendVerificationCode(emaila, mainHandler);
                    }
                });
            }
        });

        binding.aurreraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredCode = binding.kodeaText.getText().toString();
                if (enteredCode.isEmpty()) {
                    Toast.makeText(getContext(), "Sartu kodea", Toast.LENGTH_SHORT).show();
                } else {
                    verifyCode(enteredCode, usr);
                }
            }
        });
    }

    private void generateAndSendVerificationCode(String userEmail, Handler mainHandler) {
        Random random = new Random();
        generatedVerificationCode = 100000 + random.nextInt(900000);

        String subject = "Berifikazio kodea";
        String body = "Zure berifikazioa kodea hau da: " + generatedVerificationCode;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{userEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(intent, "Korreoa bidaltzen..."));

            // Show toast on the main thread
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "Korreoa bidali egin da", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (android.content.ActivityNotFoundException ex) {
            // Show toast on the main thread for error
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "Errorea kodea bidaltzean", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void verifyCode(String enteredCode, String usrBundle) {
        if (enteredCode.equals(String.valueOf(generatedVerificationCode))) {
            Toast.makeText(getContext(), "Kodea era egokian egiaztatu da", Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("username", usrBundle);
            navController.navigate(R.id.action_codeVerification_to_menua, bundle);
        } else {
            Toast.makeText(getContext(), "Kodea ez da zuzena, eman berbidali botoian beste kode bat jasotzeko", Toast.LENGTH_SHORT).show();
        }
    }
}
