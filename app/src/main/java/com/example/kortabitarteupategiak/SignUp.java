package com.example.kortabitarteupategiak;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import model.User;
import viewmodel.UserViewModel;
import com.example.kortabitarteupategiak.databinding.FragmentSignUpBinding;
import com.google.common.hash.Hashing;

public class SignUp extends Fragment {

    String username, password, email, passwordHashed;
    FragmentSignUpBinding binding;
    NavController navController;
    UserViewModel userViewModel;
    Executor executor;
    HashFunction hf;
    private boolean pwdShown = false;

    public SignUp() {
        // Required empty public constructor
    }

    public static SignUp newInstance(String param1, String param2) {
        SignUp fragment = new SignUp();
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
        //return inflater.inflate(R.layout.fragment_sign_up, container, false);
        return (binding = FragmentSignUpBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        executor = Executors.newSingleThreadExecutor();

        binding.showSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdShown = !pwdShown;

                if (pwdShown) {
                    binding.pwdSignUp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    binding.pwdSignUp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                binding.pwdSignUp.setSelection(binding.pwdSignUp.getText().length());
            }
        });

        binding.atzeraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signup_to_login);
            }
        });

        binding.erregistratuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    username = binding.usernameSignUp.getText().toString();
                    password = binding.pwdSignUp.getText().toString();
                    email = binding.emailSignUp.getText().toString();

                    /* deprecao
                    HashFunction hf = Hashing.sha256();
                    HashCode code = hf.newHasher()
                            .putString(password, Charsets.UTF_8)
                            .hash();
                    */
                    HashCode code = Hashing.sha256().hashString(password, Charsets.UTF_8);
                    passwordHashed = code.toString();
                    User user = new User(username, passwordHashed, email);
                    userViewModel.add(user);
                    navController.navigate(R.id.action_signup_to_login);

                    Toast t = Toast.makeText(getContext(), "Zure kontua era egokian sortu da", Toast.LENGTH_LONG);
                    t.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}