package com.example.kortabitarteupategiak;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kortabitarteupategiak.databinding.FragmentAccountSettingsBinding;
import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import model.User;
import viewmodel.UserViewModel;

public class AccountSettings extends Fragment {

    NavController navController;
    UserViewModel userViewModel;
    Executor executor;
    FragmentAccountSettingsBinding binding;

    public AccountSettings() {
        // Required empty public constructor
    }

    public static AccountSettings newInstance(String param1, String param2) {
        AccountSettings fragment = new AccountSettings();
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
        return (binding = FragmentAccountSettingsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        executor = Executors.newSingleThreadExecutor();

        Bundle bundle = getArguments();
        String usr = bundle.getString("username");

        executor.execute(() -> {
            String kor = userViewModel.getEmail(usr);
            String pwd = userViewModel.getPasswordLogin(usr);

            User usrObj = new User(usr, pwd, kor);

            requireActivity().runOnUiThread(() -> {
                if (binding != null && usr != null) {
                    binding.usrText.setText(usr);
                    binding.korrText.setText(usrObj.getEmail());
                }
            });
        });

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwdKomp = binding.pwdZaharra.getText().toString();
                HashCode pwdIn = Hashing.sha256().hashString(pwdKomp, Charsets.UTF_8);

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        String hshPwd = userViewModel.getPasswordLogin(usr);

                        if (pwdIn.toString().equals(hshPwd)) {
                            String pwdBer = binding.pwdBerria.getText().toString();
                            String korBer = binding.korrText.getText().toString();

                            HashCode newHashed = Hashing.sha256().hashString(pwdBer, Charsets.UTF_8);

                            int idObj = userViewModel.getId(usr);

                            User usrObj = new User(idObj, usr, newHashed.toString(), korBer);
                            userViewModel.update(usrObj);

                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast t = Toast.makeText(getContext(), "Zure Kontua eguneratu da", Toast.LENGTH_LONG);
                                    t.show();
                                }
                            });
                        } else {
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast t = Toast.makeText(getContext(), "Pasahitz zaharra okerra da", Toast.LENGTH_LONG);
                                    t.show();
                                }
                            });
                        }
                    }
                });
                //binding.pwdZaharra.setText("");
                //binding.pwdBerria.setText("");
                navController.navigate(R.id.action_accountSettings_to_menua, bundle);
            }
        });
    }
}