package com.example.taxiapp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.taxiapp.MainActivityTaxiMenu;
import com.example.taxiapp.R;
import com.example.taxiapp.databinding.DatosusuaBinding;
import com.example.taxiapp.subir_foto;
import com.example.taxiapp.ui.ayuda.ayudaViewModel;


public class datos extends Fragment {

    private DatosusuaBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = DatosusuaBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

