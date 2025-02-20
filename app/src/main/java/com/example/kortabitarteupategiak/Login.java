package com.example.kortabitarteupategiak;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import viewmodel.UserViewModel;

import com.example.kortabitarteupategiak.databinding.FragmentLoginBinding;
import com.google.common.hash.Hashing;


public class Login extends Fragment {

    FragmentLoginBinding binding;
    NavController navController;
    UserViewModel userViewModel;
    Executor executor;
    HashFunction hf;
    private boolean pwdShown = false;

    public Login() {
        // Required empty public constructor
    }

    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_login, container, false);
        return (binding = FragmentLoginBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        executor = Executors.newSingleThreadExecutor();
        Log.d("ProductName","nikolas");

        binding.showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdShown = !pwdShown;

                if (pwdShown) {
                    binding.passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    binding.passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                binding.passwordText.setSelection(binding.passwordText.getText().length());
            }
        });

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_login_to_signUp);
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameInput = binding.usernameText.getText().toString();
                String pwdInput = binding.passwordText.getText().toString();

                HashCode pwdIn = Hashing.sha256().hashString(pwdInput, Charsets.UTF_8);

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        String hashedDb = userViewModel.getPasswordLogin(usernameInput);
                        if (pwdIn.toString().equals(hashedDb)) {
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("username", usernameInput);
                                    navController.navigate(R.id.action_login_to_codeVerification, bundle);
                                }
                            });
                        } else {
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast t = Toast.makeText(getContext(), "Berrikusi kredentzialak eta saiatu berriro", Toast.LENGTH_LONG);
                                    t.show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
